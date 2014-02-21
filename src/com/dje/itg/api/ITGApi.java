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

import com.sun.jna.ptr.PointerByReference;

public class ITGApi {

    private ITGApiInterface apiInterface = ITGApiInterface.INSTANCE;

    public ITGApi() {
		apiInterface = ITGApiInterface.INSTANCE;
    }

	/**
	 * Send an D-ITG command to a sender
	 * 
	 * @param sender	The IP address/hostname of the sender
	 * @param command	The command the sender should run
	 * 
	 * @return 0	Command successfully sent
	 * @return -1	Otherwise
	 */
    public int sendCmd(String sender, String command) {
		return apiInterface._Z8DITGsendPcS_(sender, command);
    }

	/**
	 * Receive a message from the sender
	 * 
	 * This method is non-blocking. The type attribute of {@link ITGCatchMessage}
	 * represents whether a message was actually received and if so what type of
	 * message it is
	 * 
	 * @return An {@link ITGCatchMessage} object representing the message
	 */
    public ITGCatchMessage catchMsg() {
		PointerByReference senderPointerRef = new PointerByReference(),
			commandPointerRef = new PointerByReference();
		int type = apiInterface._Z15catchManagerMsgPPcS0_(senderPointerRef, commandPointerRef);

		return new ITGCatchMessage(type, senderPointerRef, commandPointerRef);
    }

}
