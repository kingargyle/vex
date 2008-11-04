/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.validator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IValidator;

/**
 * Partial implementation of the Validator interface.
 */
public abstract class AbstractValidator implements IValidator {

	/**
	 * @see IValidator#isValidSequence
	 */
	public boolean isValidSequence(String element, List<String> seq1,
			List<String> seq2, List<String> seq3, boolean partial) {

		List list = new ArrayList();
		for (int i = 0; i < seq1.size(); i++) {
			list.add(seq1.get(i));
		}
		if (seq2 != null) {
			for (int i = 0; i < seq2.size(); i++) {
				if (i == 0 && seq2.get(i).equals(IValidator.PCDATA)
						&& list.size() > 0
						&& list.get(list.size() - 1).equals(IValidator.PCDATA)) {
					// Avoid consecutive PCDATA's
					continue;
				}
				list.add(seq2.get(i));
			}
		}
		if (seq3 != null) {
			for (int i = 0; i < seq3.size(); i++) {
				if (i == 0 && seq3.get(i).equals(IValidator.PCDATA)
						&& list.size() > 0
						&& list.get(list.size() - 1).equals(IValidator.PCDATA)) {
					// Avoid consecutive PCDATA's
					continue;
				}
				list.add(seq3.get(i));
			}
		}

		String[] nodes = (String[]) list.toArray(new String[list.size()]);
		return this.isValidSequence(element, nodes, partial);
	}

}
