/*
 * Terrier - Terabyte Retriever 
 * Webpage: http://terrier.org 
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - School of Computing Science
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
 * The Original Code is DFI0.java.
 *
 * The Original Code is Copyright (C) 2004-2013 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   B.T. Dincer (original author)
 *   Craig Macdonald <craigm{a.}dcs.gla.ac.uk>
 *   
 */
package org.terrier.matching.models;

import org.apache.log4j.Logger;
/** Divergence From Independence model based on Standardization 
 * (i.e., standardized distance from independence in term frequency tf).
 * <p>
 * For more information: 
 * <p>
 * A Nonparametric Term Weighting Method for Information Retrieval Based on Measuring the Divergence from Independence. 
 * Kocabas, Dincer & Karaoglan, International Journal of Information Retrieval, (to appear), 2013. 
 * doi: 10.1007/s10791-013-9225-4.
 * <p>
 * IRRA at TREC 2012: Index Term Weighting based on Divergence From Independence Model. Dincer, Kocabas & Karaoglan,
 * NIST Special Publication, Proc. of  the 20th Text Retrieval Conference, TREC'12, Gaitersburg, MD, USA, 2012. 
 * <p>
 * @author Bekir Taner Dincer & Craig Macdonald
 * @since 3.5
 */

public class DFIZ extends WeightingModel {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getRootLogger();

	private static final long serialVersionUID = 1L;

	private String name = "DFIZ";

	@Override
	public String getInfo() {
		return name;
	}

	@Override
	public double score(double tf, double docLength) {

		double  e_ij = (termFrequency * docLength) / numberOfTokens;

		// Condition 1
		if ( tf <= e_ij ) return 0D;

		double zScore = ((tf - e_ij)/Math.sqrt(e_ij)) + 1;

		return keyFrequency * WeightingModelLibrary.log(zScore);
	}

	/**
	 * This method provides the contract for implementing weighting models.
	 * 
	 * As of Terrier 3.6, the 5-parameter score method is being deprecated
	 * since it is not used. The two parameter score method should be used
	 * instead. Tagged for removal in a later version.
	 * 
	 * @param tf The term frequency in the document
	 * @param docLength the document's length
	 * @param n_t The document frequency of the term
	 * @param F_t the term frequency in the collection
	 * @param keyFrequency the term frequency in the query
	 * @return the score returned by the implemented weighting model.
	 */
	@Deprecated
	@Override
	public double score(double tf, double docLength, double n_t, double F_t,
			double keyFrequency) {
		return score(tf, docLength);
	}

}
