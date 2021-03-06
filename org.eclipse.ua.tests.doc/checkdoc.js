/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
 
 function ua_test_doc_record_links() {
    liveAction('org.eclipse.ua.tests.doc', 'org.eclipse.ua.tests.doc.internal.actions.VisitPageAction' ,window.location); 
    var lnk = document.links;
    for (var i = 0; i != lnk.length; i++) {
        liveAction('org.eclipse.ua.tests.doc', 'org.eclipse.ua.tests.doc.internal.actions.CheckLinkAction' ,document.links[i].href); 
    }  
}
         
function ua_test_doc_complete() {  
    liveAction('org.eclipse.ua.tests.doc', 'org.eclipse.ua.tests.doc.internal.actions.CheckLinkAction', 'ALL_PAGES_LOADED' );
}   
     
function ua_test_doc_check_links() {  
    liveAction('org.eclipse.ua.tests.doc', 'org.eclipse.ua.tests.doc.internal.actions.CheckLinkAction', 'CHECK_LINKS' );
}
