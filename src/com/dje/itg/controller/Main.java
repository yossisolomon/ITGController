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
import java.io.IOException;

import com.dje.itg.api.ITGCatchMessage;
import com.dje.itg.api.ITGApi;

public class Main {
	
	private ITGApi itgApi;
	private MessageReceiver messageReceiver;
	
	private String sender;
	private File script;

	public Main(String sender, File script) {
		itgApi = new ITGApi();
		this.sender = sender;
		this.script = script;

		/* Start the thread to receive response messages */
		messageReceiver = new MessageReceiver(itgApi);
		messageReceiver.start();
		
		runScript();
	}
	
	/**
	 * Parse and send commands to the sender from the provided script
	 */
	private void runScript() {
		int successCmds = 0;
		
		try {
			BufferedReader scriptReader = new BufferedReader(new FileReader(script));
			String line;
			int status;
			
			/* Loop through script */
			while ((line = scriptReader.readLine()) != null) {
				if (! line.contains("#")) { /* Ignore lines containing a # */
					status = itgApi.sendCmd(sender, line);
					if (status == ITGApi.SEND_SUCCESS)
						successCmds++;
				}
			}
			
			scriptReader.close();
		} catch (Exception e) {
			System.err.println("Failed to read script " + script + "\n" + e);
			System.exit(1);
		}
		
		/* Inform receiver of expected number of end messages */
		messageReceiver.incrMessageSentNum(successCmds);
	}
	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		try {
			new Main(args[0], new File(args[1]));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ITGController\n" +
					   "-------------\n\n" + 
					   "Arguments: <sender> <script>\n\n" +
					   "<sender>	Host running ITGSend in daemon mode\n" +
					   "<script>	Path to D-ITG script");
			System.exit(1);
		}
	}
	
}
