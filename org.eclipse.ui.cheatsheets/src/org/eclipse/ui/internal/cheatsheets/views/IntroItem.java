/*
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2000, 2003.
 * All Rights Reserved. 
 * Note to U.S. Government Users Restricted Rights:  Use, duplication or disclosure restricted by GSA ADP  schedule Contract with IBM Corp. 
*/

package org.eclipse.ui.internal.cheatsheets.views;

import java.net.URL;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.internal.cheatsheets.*;
import org.eclipse.ui.internal.cheatsheets.data.ContentItem;

public class IntroItem extends ViewItem {
	private Image startButtonImage;
	private Image restartButtonImage;

	private Button startButton;

	/**
	 * Constructor for IntroItem.
	 * @param parent
	 * @param contentItem
	 */
	public IntroItem(Composite parent, ContentItem contentItem, Color itemColor, CheatSheetView theview) {
		super(parent, contentItem, itemColor, theview);

	}

	protected void init() {
		super.init();
		IPluginDescriptor mydesc = CheatSheetPlugin.getPlugin().getDescriptor();

		String imageFileName = "icons/full/clcl16/start_cheatsheet.gif"; //$NON-NLS-1$
		URL imageURL = mydesc.find(new Path(imageFileName));
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(imageURL);
		startButtonImage = imageDescriptor.createImage();

		imageFileName = "icons/full/clcl16/restart_cheatsheet.gif"; //$NON-NLS-1$
		imageURL = mydesc.find(new Path(imageFileName));
		imageDescriptor = ImageDescriptor.createFromURL(imageURL);
		restartButtonImage = imageDescriptor.createImage();
	}

	/*package*/ void setStartImage() {
		startButton.setImage(startButtonImage);
		startButton.setToolTipText(CheatSheetPlugin.getResourceString(ICheatSheetResource.START_CHEATSHEET_TOOLTIP));
		startButton.setFAccessibleName(startButton.getToolTipText());
	}

	/*package*/ void setRestartImage() {
		startButton.setImage(restartButtonImage);
		startButton.setToolTipText(CheatSheetPlugin.getResourceString(ICheatSheetResource.RESTART_CHEATSHEET_TOOLTIP));
		startButton.setFAccessibleName(startButton.getToolTipText());
	}

	/**
	 * @see org.eclipse.ui.internal.cheatsheets.data.ViewItem#handleButtons(Composite)
	 */
	/*package*/ void handleButtons(Composite bodyWrapperComposite) {
		buttonComposite = new Composite(bodyWrapperComposite, SWT.NONE);
		GridLayout buttonlayout = new GridLayout(4, false);
		buttonlayout.marginHeight = 2;
		buttonlayout.marginWidth = 2;
		buttonlayout.verticalSpacing = 2;

		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
		buttonData.grabExcessHorizontalSpace = true;

		buttonComposite.setLayout(buttonlayout);
		buttonComposite.setLayoutData(buttonData);
		buttonComposite.setBackground(itemColor);
		Label filllabel = new Label(buttonComposite, SWT.NULL);
		filllabel.setBackground(itemColor);
		GridData filldata = new GridData();
		filldata.widthHint = 16;
		filllabel.setLayoutData(filldata);

		startButton = new Button(buttonComposite, SWT.NULL, startButtonImage);
		startButton.setData(this);
		startButton.setBackground(itemColor);
		startButton.setToolTipText(CheatSheetPlugin.getResourceString(ICheatSheetResource.START_CHEATSHEET_TOOLTIP));
		startButton.setFAccessibleName(startButton.getToolTipText());
		startButton.setFAccessibleDescription(bodyText.getText());
		// addHandListener(startLabel);
		startButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				theview.advanceIntroItem();
			}
		});
		buttonCompositeList.add(buttonComposite);
	}

	/**
	 * @see org.eclipse.ui.internal.cheatsheets.views.ViewItem#disposeOfStuff()
	 */
	public void dispose() {
		super.dispose();
		if (startButtonImage != null)
			startButtonImage.dispose();
		if (restartButtonImage != null)
			restartButtonImage.dispose();
	}

}