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

import com.dje.itg.api.ITGMessage;
import com.dje.itg.api.ITGApi;

public class MessageReceiver extends Thread {
	
	private ITGApi itgApi;
	private int messageSentNum = -1, endMessageRecvNum, startMessageRecvNum;

	public MessageReceiver(ITGApi itgApi) {
		this.itgApi = itgApi;
	}

	@Override
	public void run() {
		ITGMessage message;
		int messageType;

		while (true) {
			/* Attempt message retrieval */
			try {
				message = itgApi.catchMsg();
				messageType = message.getType();
			
				/* Print message */
				System.out.println(message);
				
				/* Increase count of start messages received */
				if (messageType == ITGMessage.START_MSG)
					startMessageRecvNum++;
				
				/* Increase count of end messages received */
				if (messageType == ITGMessage.END_MSG)
					endMessageRecvNum++;
					
				/* Confirm commands started if informed of expected num of start messages
				* and we have received the expected num of start messages */
				if (messageSentNum != -1 && startMessageRecvNum == messageSentNum) {
					System.out.println("All sent commands started");
					startMessageRecvNum = -1;
				}

				/* Stop looping if informed of expected num of end messages
				* and we have received the expected num of end messages */
				if (messageSentNum != -1 && endMessageRecvNum == messageSentNum)
					break;
			} catch (Exception e) {
				System.err.println("Failed to catch message");
				System.exit(1);
			}
		}

		itgApi.close();

		System.out.println("All sent commands ended");
		System.exit(0);
	}

	/**
	 * Increase the number of messages that have been sent
	 * 
	 * This corresponds to the number of end messages that are expected
	 * 
	 * @param num The number of messages to increase count by
	 */
	public void incrMessageSentNum(int num) {
		/* Set to 0 if this is the first increase */
		if (messageSentNum == -1)
			messageSentNum = 0;
		
		messageSentNum += num;
	}

}
