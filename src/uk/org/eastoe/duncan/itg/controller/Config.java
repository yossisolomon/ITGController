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

package uk.org.eastoe.duncan.itg.controller;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;

import uk.org.eastoe.duncan.itg.api.ITGApi;

public class Config {
	
	/* Regular expressions */
	public final static String
		HOST_BLOCK = "\\s*Host\\s+[a-zA-Z.\\-0-9]+\\s+\\{\\s*",
		ANY_LEAD_WHITESPACE = "^\\s*",
		END_HOST_BLOCK = "\\s*}\\s*";
	
	private ITGApi itgApi;
	private File file;
	private HashMap<String, Set<String>> hostCommandsMap;
	
	public Config(File file, ITGApi itgApi) {
		this.file = file;
		this.itgApi = itgApi;
		hostCommandsMap = new HashMap<String, Set<String>>();
		
		parseFile();
	}
	
	/**
	 * Retrieve the map of sender to commands for that sender
	 * 
	 * @return The HashMap of sender to a list of commands
	 */
	public HashMap<String, Set<String>> getHostCommandsMap() {
		return hostCommandsMap;
	}
	
	/**
	 * Parse the config file
	 */
	private void parseFile() {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			int lineNum = 1, cmdNum = 0;
			String line, sender = null;
			Set<String> commands = null;
			
			/* Loop through file */
			while ((line = fileReader.readLine()) != null) {
				if (! line.matches(ANY_LEAD_WHITESPACE)) {
					line = line.replaceFirst(ANY_LEAD_WHITESPACE, "");
				
					if (line.matches(HOST_BLOCK)) {
						if (commands != null)
							throw new ParserException(lineNum, "Host blocks cannot be nested");
							
						sender = getHostBlockName(line);
						
						if ((commands = hostCommandsMap.get(sender)) == null) {
							commands = new LinkedHashSet<String>();
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
							System.out.println("Adding cmd: " + line);
							if (! commands.add(line)) {
								System.err.println("Duplicate command ignored: " +
									line + " <Line " + lineNum + ">");
							}
							cmdNum++;
						}
					}
					
				}
				lineNum++;
			}
			
			fileReader.close();
			
			if (cmdNum == 0)
				throw new ParserException("No commands loaded");
				
		} catch (Exception e) {
			System.err.println("Failed to read config " + file + "\n" + e);
			System.exit(1);
		}	
	}
	
	/**
	 * Parse a Host block line to get the sender address/host
	 * 
	 * @param input The Host block line to parse
	 * 
	 * @return The sender address/host name
	 */
	private String getHostBlockName(String input) {
		String out = input.split("\\s*Host\\s+")[1];
		out = out.split("\\s+")[0];	
		
		return out;
	}
	
}
