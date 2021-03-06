/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ua.tests.cheatsheet.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.ua.tests.intro.performance.OpenIntroTest;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.OpenCheatSheetAction;
import org.eclipse.ui.internal.cheatsheets.ICheatSheetResource;

public class OpenCheatSheetTest extends PerformanceTestCase {
	
	/*
	 * Returns an instance of this Test.
	 */
	public static Test suite() {
		return new TestSuite(OpenCheatSheetTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		OpenIntroTest.closeIntro();
	}
	
	public void testOpenSimpleCheatSheet() throws Exception {
		tagAsSummary("Open simple cheat sheet", Dimension.ELAPSED_PROCESS);

		// warm-up
		for (int i=0;i<3;++i) {
			closeCheatSheet();
			openSimpleCheatSheet();
		}
		
		// run the tests
		for (int i=0;i<50;++i) {
			closeCheatSheet();
			startMeasuring();
			openSimpleCheatSheet();
			stopMeasuring();
		}
		
		commitMeasurements();
		assertPerformance();
	}

	public void testOpenCompositeCheatSheet() throws Exception {
		tagAsSummary("Open composite cheat sheet", Dimension.ELAPSED_PROCESS);

		// warm-up
		for (int i=0;i<3;++i) {
			closeCheatSheet();
			openCompositeCheatSheet();
		}
		
		// run the tests
		for (int i=0;i<50;++i) {
			closeCheatSheet();
			startMeasuring();
			openCompositeCheatSheet();
			stopMeasuring();
		}
		
		commitMeasurements();
		assertPerformance();
	}

	private void openSimpleCheatSheet() throws Exception {
		OpenCheatSheetAction action = new OpenCheatSheetAction("org.eclipse.ua.tests.cheatsheet.performance.simple");
		action.run();
		flush();
	}

	private void openCompositeCheatSheet() throws Exception {
		OpenCheatSheetAction action = new OpenCheatSheetAction("org.eclipse.ua.tests.cheatsheet.performance.composite");
		action.run();
		flush();
	}

	private void closeCheatSheet() throws Exception {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView(ICheatSheetResource.CHEAT_SHEET_VIEW_ID);
		page.hideView(view);
		flush();
	}
	
	private static void flush() {
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
		}
	}
}
