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

package org.eclipse.ui.intro.internal.model;

import org.eclipse.core.runtime.*;

/**
 * An Intro Part home page. A home page is special because it is the page that
 * decides whether the OOBE pages are dynamic or static.
 */
public class IntroHomePage extends AbstractIntroPage {

	private static final String URL_ATTRIBUTE = "url";
	private static final String STANDBY_URL_ATTRIBUTE = "standby-url";
	private static final String STANDBY_STYLE_ATTRIBUTE = "standby-style";
	private static final String STANDBY_ALT_STYLE_ATTRIBUTE =
		"standby-alt-style";

	private String url;
	private String standby_url;
	private String standby_style;
	private String standby_alt_style;

	private boolean isDynamic = false;

	/**
	 *  
	 */
	IntroHomePage(IConfigurationElement element) {
		super(element);
		url = element.getAttribute(URL_ATTRIBUTE);
		if (url == null) {
			// if we do not have a URL attribute, then we have dynamic content.
			isDynamic = true;
			standby_style = element.getAttribute(STANDBY_STYLE_ATTRIBUTE);
			standby_alt_style =
				element.getAttribute(STANDBY_ALT_STYLE_ATTRIBUTE);

			// Resolve standby styles. The ALT style need not be resolved.
			standby_style =
				IntroModelRoot.getPluginLocation(standby_style, element);
		} else {
			// check the url/standby-url attributes and update accordingly.
			url = IntroModelRoot.resolveURL(url, element);
			standby_url = element.getAttribute(STANDBY_URL_ATTRIBUTE);
			standby_url = IntroModelRoot.resolveURL(standby_url, element);
		}
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return Returns the standby_url.
	 */
	public String getStandbyUrl() {
		return standby_url;
	}

	/**
	 * Returns true if this is a dynamic model or not. This is based on whether
	 * this root page has a URL attribute or not.
	 * 
	 * @return Returns the isDynamic.
	 */
	public boolean isDynamic() {
		return isDynamic;
	}

	/**
	 * @return Returns the standby_style.
	 */
	public String getStandbyStyle() {
		return standby_style;
	}

	/**
	 * @return Returns the standby_alt_style.
	 */
	public String getStandbyAltStyle() {
		return standby_alt_style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.intro.internal.model.IntroElement#getType()
	 */
	public int getType() {
		return IntroElement.HOME_PAGE;
	}

}