/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.help.internal.base;

/**
 * @since 3.0
 */
public interface IHelpActivitySupport {

	/**
	 * Checks if href is matches an enabled activity.
	 * If it does not match any defined activites, it is assumed to be enabled.
	 * 
	 * @param href
	 * @return
	 */
	public boolean isEnabled(String href);

	/**
	 * Enables activities with patterns matching the href
	 * 
	 * @param href
	 */
	public void enableActivities(String href);
}