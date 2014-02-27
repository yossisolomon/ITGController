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

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;

public interface ITGApiInterface extends Library {
	ITGApiInterface INSTANCE = (ITGApiInterface) Native.loadLibrary("ITG", ITGApiInterface.class);
	
	/* int DITGsend(char *sender, char *message) */
	int _Z8DITGsendPcS_(String sender, String message);
	
	/* int catchManagerMsg(char **senderIP, char **msg) */
	int _Z15catchManagerMsgPPcS0_(PointerByReference senderIP, PointerByReference msg);
}
