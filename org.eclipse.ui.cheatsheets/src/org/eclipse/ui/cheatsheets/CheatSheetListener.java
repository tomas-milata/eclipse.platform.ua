/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.cheatsheets;

/**
 * For monitoring the execution of a cheat sheet.
 * <p>
 * This class is used in conjuction with the "listener" attribute on the
 * "cheatsheet" element in the cheat sheet content file. Clients should declare
 * a subclass that implements {@link #cheatSheetEvent(ICheatSheetEvent)}. The
 * listener subclass must be public, and have a public 0-arg constructor. The
 * listener subclass is instantiated as the cheat sheet is opened, and discarded
 * after the cheat sheet is closed.
 * </p>
 * 
 * @since 3.0
 */
public abstract class CheatSheetListener {
	
	/**
	 * Creates a new cheat sheet listener.
	 */
	public CheatSheetListener() {
		// do nothing
	}

	/**
	 * Notifies this listener of the given cheat sheet event.
	 * 
	 * @param event the cheat sheet event
	 */
	 public abstract void cheatSheetEvent(ICheatSheetEvent event);
}