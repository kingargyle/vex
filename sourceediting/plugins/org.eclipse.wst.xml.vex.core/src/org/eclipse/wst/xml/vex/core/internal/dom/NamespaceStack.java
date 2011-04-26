/*******************************************************************************
 * Copyright (c) 2011 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Florian Thienel
 */
public class NamespaceStack {
	private static final String DEFAULT_PREFIX = null;

	private final Map<String, List<String>> stacks = new HashMap<String, List<String>>();

	public String peekDefault() {
		return peek(DEFAULT_PREFIX);
	}

	public String peek(final String prefix) {
		return peek(getStack(prefix));
	}

	private List<String> getStack(final String prefix) {
		final List<String> result = stacks.get(prefix);
		if (result != null)
			return result;
		return putNewStack(prefix);
	}

	private List<String> putNewStack(final String prefix) {
		final List<String> result = new ArrayList<String>();
		stacks.put(prefix, result);
		return result;
	}

	private String peek(final List<String> stack) {
		if (stack.isEmpty())
			return null;
		return stack.get(stack.size() - 1);
	}

	private void removeTop(final List<String> stack) {
		if (stack.isEmpty())
			return;
		stack.remove(stack.size() - 1);
	}

	public void pushDefault(final String namespaceUri) {
		push(DEFAULT_PREFIX, namespaceUri);
	}

	public void push(final String prefix, final String namespaceUri) {
		getStack(prefix).add(namespaceUri);
	}

	public String popDefault() {
		return pop(DEFAULT_PREFIX);
	}

	public String pop(final String prefix) {
		final List<String> stack = getStack(prefix);
		final String result = peek(stack);
		removeTop(stack);
		return result;
	}

	public void clear() {
		stacks.clear();
	}
	
	public Collection<String> getPrefixes() {
		final ArrayList<String> result = new ArrayList<String>();
		for (String key : stacks.keySet())
			if (key != DEFAULT_PREFIX)
				result.add(key);
		return result;
	}

}
