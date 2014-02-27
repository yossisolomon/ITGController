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

import com.dje.itg.api.ITGCatchMessage;
import com.dje.itg.api.ITGApi;

public class MessageReceiver extends Thread {

	private final static int CATCH_SLEEP = 1000;
	
	private ITGApi itgApi;
	private int messageSentNum = -1, messageRecvNum;

	public MessageReceiver(ITGApi itgApi) {
		this.itgApi = itgApi;
	}

	@Override
	public void run() {
		ITGCatchMessage message;
		int messageType;

		while (true) {
			/* Attempt message retrieval */
			message = itgApi.catchMsg();
			messageType = message.getType();
			
			/* Print a valid message */
			if (messageType != ITGCatchMessage.CATCH_NOMSG)
				System.out.println(message);
				
			/* Increase count of end messages received */
			if (messageType == ITGCatchMessage.CATCH_END)
				messageRecvNum++;

			/* Stop looping if informed of expected num of end messages
			 * and we have received the expected num of end messages */
			if (messageSentNum != -1 && messageRecvNum == messageSentNum)
				break;

			try {
				Thread.sleep(CATCH_SLEEP);
			} catch (Exception e) {}
		}

		System.out.println("\nQuit");
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
		if (messageSentNum == -1);
			messageSentNum = 0;
		
		messageSentNum += num;
		System.out.println("Now expecting " + messageSentNum + " end message(s)");
	}

}
