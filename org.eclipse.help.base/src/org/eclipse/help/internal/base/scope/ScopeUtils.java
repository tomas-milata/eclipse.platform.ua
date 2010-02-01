/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.help.internal.base.scope;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.help.IIndexEntry;
import org.eclipse.help.IIndexEntry2;
import org.eclipse.help.IIndexSee;
import org.eclipse.help.IToc;
import org.eclipse.help.ITopic;
import org.eclipse.help.IUAElement;
import org.eclipse.help.base.AbstractHelpScope;
import org.eclipse.help.internal.UAElement;
import org.eclipse.help.internal.index.Index;
import org.eclipse.help.internal.index.IndexSee;

/**
 * Utilities to test for enabled topics, index entries etc. 
 */

public class ScopeUtils {
	

	/*
	 * Function to determine whether a topic should be shown in the toc.
	 * For hierarchical scopes the element itself must be in scope.
	 * For non hierarchical scopes if any child is in scope the element should show.
	 * A toc with no topics does not display
	 */
	public static boolean showInTree(IToc toc, AbstractHelpScope scope) {
		return (scope.inScope(toc) || !scope.isHierarchicalScope()) && hasInScopeDescendent(toc, scope);
	}

	/*
	 * Function to determine whether a topic should be shown in the toc.
	 * For hierarchical scopes the element itself must be in scope.
	 * For non hierarchical scopes if any child is in scope the element should show.
	 */
	public static boolean showInTree(ITopic topic, AbstractHelpScope scope) {
		if (scope.inScope(topic)) {
			return (topic.getHref() != null) || hasInScopeDescendent(topic, scope);
		}
		return !scope.isHierarchicalScope() && hasInScopeDescendent(topic, scope);
	}
	
	/*
	 * Function to determine whether an entry should be shown in the index.
	 * For hierarchical scopes the element itself must be in scope.
	 * For non hierarchical scopes if any child is in scope the element should show.
	 * An entry with no topic descendants does not display
	 */
	public static boolean showInTree(IIndexEntry entry, AbstractHelpScope scope) {
		return (scope.inScope(entry) || !scope.isHierarchicalScope()) && hasInScopeDescendent(entry, scope);
	}

	public static boolean hasInScopeDescendent(ITopic topic, AbstractHelpScope scope) {
		ITopic[] subtopics = topic.getSubtopics();
		for (int i = 0; i < subtopics.length; i++) {
			if (showInTree(subtopics[i], scope)) {  
				return true;
			}
		}
		return false;
    }
	
	public static boolean hasInScopeDescendent(IToc toc, AbstractHelpScope scope) {
		ITopic[] topics = toc.getTopics();
		for (int i = 0; i < topics.length; i++) {
			if (showInTree(topics[i], scope)) {  
				return true;
			}
		}
		return false;
    }
	
	public static boolean hasInScopeDescendent(IIndexEntry entry,
			AbstractHelpScope scope) {
		ITopic[] topics = entry.getTopics();
		for (int t = 0; t < topics.length; t++) {
			if (showInTree(topics[t], scope)) {
				return true;
			}
		}
		IIndexEntry[] entries = entry.getSubentries();
		for (int e = 0; e < entries.length; e++) {
			if (showInTree(entries[e], scope)) {
				return true;
			}
		}
		if (entry instanceof IIndexEntry2) {
			IIndexSee[] sees = ((IIndexEntry2)entry).getSees();
			for (int s = 0; s < sees.length; s++) {
				if (scope.inScope(sees[s]) && hasInScopeTarget(sees[s], scope)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasInScopeTarget(IIndexSee see, AbstractHelpScope scope) {
		if (see instanceof IndexSee) {
			IndexSee indexSee = (IndexSee)see;
			UAElement ancestor = indexSee.getParentElement();
			while (!(ancestor instanceof Index)) {
				if (ancestor == null) {
					return true;
				}
				ancestor = ancestor.getParentElement();
			}
			IIndexEntry target = ((Index)ancestor).getSeeTarget(indexSee);
			if (target == null) {
				return false;
			}
			return scope.inScope(target) || hasInScopeDescendent(target, scope);	
		}
		return false;
	}

	/**
	 * Filter out any disabled entries from an array
	 * @param entries an array of entries
	 * @param scope 
	 * @return an array containing only those entries which are enabled
	 */
	public static IIndexEntry[] inScopeEntries(IIndexEntry[] entries, AbstractHelpScope scope) {
		for (int i=0;i<entries.length;++i) {
			if (!scope.inScope(entries[i])) {
				List list = new ArrayList(entries.length);
				for (int j=0;j<entries.length;++j) {
					if (j < i || scope.inScope(entries[j])) {
						list.add(entries[j]);
					}
				}
				return (IIndexEntry[])list.toArray(new IIndexEntry[list.size()]);
			}
		}
		return entries;
	}

	/**
	 * Filter out any disabled topics from an array
	 * @param topics an array of topics
	 * @param scope 
	 * @return an array containing only those topics which are enabled
	 */
	public static ITopic[] inScopeTopics(ITopic[] topics, AbstractHelpScope scope) {
		for (int i=0;i<topics.length;++i) {
			if (!scope.inScope(topics[i])) {
				List list = new ArrayList(topics.length);
				for (int j=0;j<topics.length;++j) {
					if (j < i || scope.inScope(topics[j])) {
						list.add(topics[j]);
					}
				}
				return (ITopic[])list.toArray(new ITopic[list.size()]);
			}
		}
		return topics;
	}

	public static boolean hasInScopeChildren(IUAElement element,
			AbstractHelpScope scope) {
		if (element instanceof IToc) {
			return hasInScopeDescendent((IToc)element, scope);
		}
		if (element instanceof ITopic) {
			return hasInScopeDescendent((ITopic)element, scope);
		}
		if (element instanceof IIndexEntry) {
			return hasInScopeDescendent((IIndexEntry) element, scope);
		}
		if (element instanceof IIndexSee) {
			return hasInScopeTarget((IIndexSee) element, scope);
		}
		return false;
	}

}