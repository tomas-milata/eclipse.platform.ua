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
import java.io.*;
import java.util.*;

import org.eclipse.core.boot.*;
import org.eclipse.core.runtime.*;
import org.eclipse.help.internal.appserver.*;

/**
 * Help application.
 * Starts webserver and help web application for use
 * by infocenter and stand-alone help.
 * Application takes a parameter "mode", that can take values:
 *  "infocenter" - when help system should run as infocenter,
 *  "standalone" - when help system should run as standalone.
 */
public class HelpApplication
	implements IPlatformRunnable, IExecutableExtension {
	private static final int STATUS_EXITTING = 0;
	private static final int STATUS_RESTARTING = 2;
	private static final int STATUS_RUNNING = 1;
	private static int status = STATUS_RUNNING;
	/**
	 * Causes help service to stop and exit
	 */
	public static void stop() {
		status = STATUS_EXITTING;
	}
	/**
	 * Causes help service to exit and start again
	 */
	public static void restart() {
		if (status != STATUS_EXITTING) {
			status = STATUS_RESTARTING;
		}
	}
	/**
	 * Runs help service application.
	 */
	public Object run(Object args) throws Exception {
		if (!BaseHelpSystem.ensureWebappRunning()) {
			System.out.println(
				"Help web application could not start.  Check log file for details.");
			return EXIT_OK;
		}
		writeHostAndPort();
		// main program loop
		while (status == STATUS_RUNNING) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ie) {
				break;
			}
		}

		if (status == STATUS_RESTARTING) {
			return EXIT_RESTART;
		} else {
			return EXIT_OK;
		}
	}
	/**
	 * @see IExecutableExtension
	 */
	public void setInitializationData(
		IConfigurationElement configElement,
		String propertyName,
		Object data) {
		String value = (String) ((Map) data).get("mode");
		if ("infocenter".equalsIgnoreCase(value)) {
			BaseHelpSystem.setMode(BaseHelpSystem.MODE_INFOCENTER);
		} else if ("standalone".equalsIgnoreCase(value)) {
			BaseHelpSystem.setMode(BaseHelpSystem.MODE_STANDALONE);
		}
	}
	private void writeHostAndPort() throws IOException {
		Properties p = new Properties();
		p.put("host", WebappManager.getHost());
		p.put("port", "" + WebappManager.getPort());

		File workspace = Platform.getLocation().toFile();
		File hostPortFile = new File(workspace, ".metadata/.connection");
		hostPortFile.deleteOnExit();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(hostPortFile);
			p.store(out, null);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe2) {
				}
			}
		}

	}
}