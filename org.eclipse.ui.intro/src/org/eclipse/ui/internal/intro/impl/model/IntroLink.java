/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal.intro.impl.model;

import org.osgi.framework.Bundle;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * An intro Link. This model class is responsible for parsing and creating an
 * IntroURL class instance if the URL happens to be a valid intro url.
 */
public class IntroLink extends AbstractTextElement {

    protected static final String TAG_LINK = "link"; //$NON-NLS-1$

    private static final String ATT_LABEL = "label"; //$NON-NLS-1$
    private static final String ATT_URL = "url"; //$NON-NLS-1$
    private static final String TAG_IMG = "img"; //$NON-NLS-1$

    private String label;
    private String url;
    private IntroImage img;
    private IntroURL introURL;

    /**
     * @param element
     */
    IntroLink(Element element, Bundle bundle) {
        super(element, bundle);
        url = getAttribute(element, ATT_URL);
        label = getAttribute(element, ATT_LABEL);

        url = ModelUtil.resolveURL(url, bundle);
        if (url != null) {
            // check the URL.
            IntroURLParser parser = new IntroURLParser(url);
            if (parser.hasIntroUrl())
                introURL = parser.getIntroURL();
        }

        // There should be at most one img element.
        NodeList imgElements = element.getElementsByTagName(TAG_IMG);
        if (imgElements.getLength() > 0) {
            img = new IntroImage((Element) imgElements.item(0), getBundle());
            img.setParent(this);
        }
    }

    /**
     * @return Returns the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Retruns an IntroURL instance if link has a valid intro url. Returns null
     * otherwise.
     * 
     * @return Returns the introURL.
     */
    public IntroURL getIntroURL() {
        return introURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.internal.intro.impl.model.IntroElement#getType()
     */
    public int getType() {
        return AbstractIntroElement.LINK;
    }

    /**
     * @return Returns the img.
     */
    public IntroImage getImg() {
        return img;
    }

    /**
     * Deep copy since class has mutable objects.
     */
    public Object clone() throws CloneNotSupportedException {
        IntroLink clone = (IntroLink) super.clone();
        if (img != null) {
            IntroImage cloneIntroImage = (IntroImage) img.clone();
            cloneIntroImage.setParent(clone);
            clone.img = cloneIntroImage;
        }
        // no need to clobe IntroURL.
        return clone;
    }
}
