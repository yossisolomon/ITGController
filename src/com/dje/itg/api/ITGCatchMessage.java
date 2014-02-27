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
import com.sun.jna.Pointer;

public class ITGCatchMessage {

	/* Message types */
	public final static int
		CATCH_START = 1,
		CATCH_END = 2,
		CATCH_NOMSG = -1;

	public int type;
	public String sender, message;

	public ITGCatchMessage(int type, PointerByReference senderPointerRef,
			   PointerByReference messagePointerRef) {
		this.type = type;

		/* Attempt to retrieve sender and message content */
		Pointer senderPointer = senderPointerRef.getValue();
		if (senderPointer != null)
			sender = senderPointer.getString(0);

		Pointer messagePointer = messagePointerRef.getValue();
		if (messagePointer != null)
			message = messagePointer.getString(0);
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
