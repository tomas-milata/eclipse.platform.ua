/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.help.internal.webapp.data;

/**
 * This class calls eclipse API's directly, so it should only be instantiated in
 * the workbench scenario, not in the infocenter.
 */
public class ToolbarButton {
	private String name;
	private String tooltip;
	private String image;
	private String action;
	private String param;
	private String styleClass;
	private boolean state;
	private boolean isSeparator;

	public ToolbarButton() {
		isSeparator = true;
	}

	public ToolbarButton(String name, String tooltip, String image,
			String action, String param, String state) {
		this.name = name;
		this.tooltip = tooltip;
		this.image = image;
		this.action = action;
		this.param = param;
		this.state = state.equalsIgnoreCase("on")?true:false; //$NON-NLS-1$
		if (state.startsWith("hid")) //$NON-NLS-1$
			this.styleClass = "buttonHidden"; //$NON-NLS-1$
		else
			this.styleClass = state.equalsIgnoreCase("on")?"buttonOn":"button";   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public boolean isSeparator() {
		return isSeparator;
	}

	public String getName() {
		return name;
	}

	public String getTooltip() {
		return tooltip;
	}

	/**
	 * Returns the enabled gray image
	 * 
	 * @return String
	 */
	public String getImage() {
		int i = image.lastIndexOf('/');
		return image.substring(0, i) + "/e_" + image.substring(i + 1); //$NON-NLS-1$
	}

	/**
	 * Returns the image when selected
	 * 
	 * @return String
	 */
	public String getOnImage() {
		return getImage();
	}

	public String getAction() {
		return action;
	}

	public String getParam() {
		return param;
	}
	
	public boolean isOn() {
		return state;
	}
	
	public String getStyleClass() {
		return styleClass;
	}
}
