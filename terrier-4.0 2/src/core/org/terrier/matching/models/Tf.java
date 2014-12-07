/*
 * Terrier - Terabyte Retriever 
 * Webpage: http://ir.dcs.gla.ac.uk/terrier 
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.gla.ac.uk/
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is TF_IDF.java.
 *
 * The Original Code is Copyright (C) 2004-2010 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Ben He <ben{a.}dcs.gla.ac.uk> (original author)
 *   Gianni Amati <gba{a.}fub.it> 
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk>
 */
package org.terrier.matching.models;
/**
 * This class implements a simple Tf weighting model.
 * @author Craig Macdonald
 * @since 4.0
 */
public class Tf extends WeightingModel {
	private static final long serialVersionUID = 1L;
	/** model name */
	private static final String name = "Tf";

	public Tf() {
		super();
	}

	public Tf(double b) {
		this();
	}

	public final String getInfo() {
		return name;
	}

	public final double score(double tf, double docLength) {
		return tf;
	}

	public final double score(
		double tf,
		double docLength,
		double documentFrequency,
		double termFrequency,
		double keyFrequency) 
	{
		return tf;
	}

	/**
	 * Sets the b parameter to ranking formula
	 * @param b the b parameter value to use.
	 */
	public void setParameter(double b) {
	}


	/**
	 * Returns the b parameter to the ranking formula as set by setParameter()
	 */
	public double getParameter() {
		return 0;
	}
}
