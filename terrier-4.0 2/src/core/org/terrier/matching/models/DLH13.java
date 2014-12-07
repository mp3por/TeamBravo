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
 * The Original Code is DLH13.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Gianni Amati <gba{a.}fub.it> (original author)
 *   Ben He <ben{a.}dcs.gla.ac.uk> 
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk>
 */
package org.terrier.matching.models;


/**
 * This class implements the DLH13 weighting model. This is a parameter-free
 * weighting model. Even if the user specifies a parameter value, it will <b>NOT</b>
 * affect the results. It is highly recomended to use the model with query expansion. 
 * <p><b>References</b>
 * <ol>
 * <li>University of Glasgow at TREC 2005: Experiments in Terabyte and Enterprise 
 * Tracks with Terrier. Craig Macdonald, Ben He, Vassilis Plachouras and Iadh Ounis.
 * In Proceedings of TREC 2005.</li>
 * <li>Frequentist and Bayesian approach to  Information Retrieval. G. Amati. In 
 * Proceedings of the 28th European Conference on IR Research (ECIR 2006). 
 * LNCS vol 3936, pages 13--24.</li>
 * </ol>
 * @author Gianni Amati, Ben He, Vassilis Plachouras
  */
public class DLH13 extends WeightingModel {
	private static final long serialVersionUID = 1L;
	private double k = 0.5d;
	/** 
	 * A default constructor.
	 */
	public DLH13() {
		super();
	}
	
	/**
	 * Returns the name of the model.
	 * @return the name of the model
	 */
	public final String getInfo() {
		return "DLH13";
	}
	/**
	 * Uses DLH13 to compute a weight for a term in a document.
	 * @param tf The term frequency in the document
	 * @param docLength the document's length
	 * @return the score assigned to a document with the given 
	 *         tf and docLength, and other preset parameters
	 */
	public final double score(double tf, double docLength) {
		double f  = tf/docLength ;
  		return 
			 keyFrequency
			* (tf*WeightingModelLibrary.log ((tf* averageDocumentLength/docLength) *
					( numberOfDocuments/termFrequency) )
			   + 0.5d* WeightingModelLibrary.log(2d*Math.PI*tf*(1d-f)))
			   /(tf + k);
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
	public final double score(
		double tf,
		double docLength,
		double n_t,
		double F_t,
		double keyFrequency) {
		double f  = tf/docLength ;
  		return 
			 keyFrequency
			* (tf*WeightingModelLibrary.log ((tf* averageDocumentLength/docLength) *( numberOfDocuments/F_t) )
			   + 0.5d* WeightingModelLibrary.log(2d*Math.PI*tf*(1d-f)))
			   /(tf + k);
	}
}
