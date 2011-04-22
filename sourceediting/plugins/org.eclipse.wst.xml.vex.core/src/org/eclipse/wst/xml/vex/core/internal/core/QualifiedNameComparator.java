/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.core;

import java.util.Comparator;

import org.eclipse.core.runtime.QualifiedName;

/**
 * @author Florian Thienel
 */
public class QualifiedNameComparator implements Comparator<QualifiedName> {

	public int compare(QualifiedName name1, QualifiedName name2) {
		return name1.toString().compareTo(name2.toString());
	}
}
