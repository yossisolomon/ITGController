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

package com.dje.itg.api;

import java.net.InetAddress;

public class ITGCatchMessage {

	/* Message types */
	public final static int
		CATCH_START = 1,
		CATCH_END = 2;
		
	/* Byte offsets for message buffer */
	public final static int
		MSG_TYPE_OFFSET	= 0,
		MSG_LENGTH_OFFSET = 4,
		MSG_OFFSET = 8;

	private int type;
	private String sender, message;

	public ITGCatchMessage(InetAddress sender, byte[] buffer) {
		this.type = buffer[MSG_TYPE_OFFSET];
		this.sender = sender.getHostName();
		this.message = new String(buffer, MSG_OFFSET, buffer[MSG_LENGTH_OFFSET]);
	}
	
	/**
	 * Get the message type
	 * 
	 * @return The type of message
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Get the message sender
	 * 
	 * @return The sender of the message
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Get the message contents
	 * 
	 * @return The content of the message
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		String out = "[";

		switch (type) {
			case CATCH_START:
				out += "Start";
				break;
		
			case CATCH_END:
				out += "End";
				break;
		}

		out += " message from " + sender + "] " + message;
		return out;
	}

}
