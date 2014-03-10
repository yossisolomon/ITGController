/*
*	This file is part of ITGController.
* 
*	Copyright 2014 Duncan Eastoe <duncaneastoe@gmail.com>
*
*   ITGController is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   ITGController is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with ITGController.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.dje.itg.controller;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.dje.itg.api.ITGApi;

public class Config {
	
	public final static String
		HOST_BLOCK = "\\s*Host\\s+\\w+\\s+\\{\\s*",
		ANY_WHITESPACE = "\\s*",
		END_HOST_BLOCK = "\\s*}\\s*";	
	
	private ITGApi itgApi;
	private File file;
	private HashMap<String, List<String>> hostCommandsMap;
	
	public Config(File file, ITGApi itgApi) {
		this.file = file;
		this.itgApi = itgApi;
		hostCommandsMap = new HashMap<String, List<String>>();
		
		parseFile();
	}
	
	public HashMap<String, List<String>> getHostCommandsMap() {
		return hostCommandsMap;
	}
	
	private void parseFile() {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			int lineNum = 1, cmdNum = 0;
			String line, sender = null;
			List<String> commands = null;
			
			/* Loop through file */
			while ((line = fileReader.readLine()) != null) {
				line = stripLeadingWhitespace(line);
					
				if (! line.matches(ANY_WHITESPACE)) {
				
					if (line.matches(HOST_BLOCK)) {
						if (commands != null)
							throw new ParserException(lineNum, "Host blocks cannot be nested");
							
						sender = getHostBlockName(line);
						
						if ((commands = hostCommandsMap.get(sender)) == null) {
							commands = new ArrayList<String>();
							hostCommandsMap.put(sender, commands);
						}
					}
					else if (line.matches(END_HOST_BLOCK)) {
						if (commands == null)
							throw new ParserException(lineNum, "No Host block");
						else
							commands = null;
					}
					else if (! line.contains("#")) { /* Ignore lines containing a # */
						if (commands == null) {
							throw new ParserException(lineNum, "Not found in Host block:\n" + line);
						}
						else {
							commands.add(line);
							cmdNum++;
						}
					}
					
					lineNum++;
				}
			}
			
			fileReader.close();
			
			if (cmdNum == 0)
				throw new ParserException("No commands loaded");
				
		} catch (Exception e) {
			System.err.println("Failed to read file " + file + "\n" + e);
			System.exit(1);
		}	
	}
	
	private String stripLeadingWhitespace(String input) {
		String[] stripLeadingWhitespace = input.split(ANY_WHITESPACE, 2);
		
		if (stripLeadingWhitespace.length == 1)
			input = stripLeadingWhitespace[0];
		else
			input = stripLeadingWhitespace[1];
			
		return input;
	}
	
	public String getHostBlockName(String input) {
		String out = input.split("\\s*Host\\s+")[1];
		out = out.split("\\s+")[0];	
		
		return out;
	}
	
}
