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

import java.util.List;
import java.util.HashMap;

import com.dje.itg.api.ITGApi;

public class ConfigRunner {

	private ITGApi itgApi;
	private Config config;
	private MessageReceiver messageReceiver;
	
	/* Send commands for a single host */
	private class HostCommandRunner implements Runnable {
		private String sender;
		private List<String> commands;

		public HostCommandRunner(String sender, List<String> commands) {
			this.sender = sender;
			this.commands = commands;
		}

		@Override
		public void run() {
			int successCommands = 0;
			
			/* Send all commands to the sender */
			for (String command : commands) {
				try {
					itgApi.sendCmd(sender, command);
					successCommands++;
				} catch (Exception e) {
					System.err.println("[Send failure to " + sender + "] " + command);
				}
			}
			
			/* Inform receiver of amount of successful command sent */
			messageReceiver.incrMessageSentNum(successCommands);
		}
	};

	public ConfigRunner(Config config, MessageReceiver messageReceiver, ITGApi itgApi) {
		this.config = config;
		this.itgApi = itgApi;
		this.messageReceiver = messageReceiver;
	}

	/**
	 * Run commands from the config
	 */
	public void run() {
		HashMap<String, List<String>> hostCommandsMap = config.getHostCommandsMap();
		
		/* Send commands for each sender in a new thread */
		for (String sender : hostCommandsMap.keySet()) {
			List<String> commands = hostCommandsMap.get(sender);
			new Thread(new HostCommandRunner(sender, commands)).start();
		}
	}
	
}
