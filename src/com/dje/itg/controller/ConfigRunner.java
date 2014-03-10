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
	
	public ConfigRunner(Config config,MessageReceiver messageReceiver, ITGApi itgApi) {
		this.config = config;
		this.itgApi = itgApi;
		this.messageReceiver = messageReceiver;
	}

	public void run() {
		HashMap<String, List<String>> hostCommandsMap = config.getHostCommandsMap();
		
		int status, successCmds = 0;
		for (String sender : hostCommandsMap.keySet()) {
			List<String> commands = hostCommandsMap.get(sender);
			for (String command : commands) {
				status = itgApi.sendCmd(sender, command);
				if (status == ITGApi.SEND_SUCCESS)
					messageReceiver.incrMessageSentNum(++successCmds);
			}
		}
	}
	
}
