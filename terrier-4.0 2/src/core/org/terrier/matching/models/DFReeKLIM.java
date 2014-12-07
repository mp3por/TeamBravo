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
 * The Original Code is DFReeKLIM.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Gianni Amati <gba{a.}fub.it> (Original author)
 */
package org.terrier.matching.models;
/**
 * This class implements the DFReeKLIM weighting model. DFReeKLIM stands for a DFR model free from parameters.  DFReeKLIM is the inner product of two KL Information Measures.
 *
 * Appeared in the paper:
 * 
 * FUB, IASI-CNR, UNIVAQ at Microblogging Track of TREC 2011
 * G. Amati, G. Amodeo, M. Bianchi, G. Marcone, Fondazione Ugo Bordoni
 * G.Amodeo, C. Gaibisso, G. Gambosi, IASI-CNR
 * A. Celi, C. De Nicola, M. Flammini, Univ. dell' Aquila
 * 
 * @since 4.0
 */
public class DFReeKLIM extends WeightingModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** model name */
	private static final String name = "DFReeKLIM";


	/** 
	 * A default constructor to make this model.
	 */
	public DFReeKLIM() {
		super();
	}
	public DFReeKLIM(double c) {
		super();
	}
	/**
	 * Returns the name of the model, in this case "DFReeKLIM"
	 * @return the name of the model
	 */

	public final String getInfo() {
		return name ;
	}
	/**
	 * Uses DFReeKLIM to compute a weight for a term in a document.
	 * @param tf The term frequency of the term in the document
	 * @param docLength the document's length
	 * @return the score assigned to a document with the given 
	 *         tf and docLength, and other preset parameters
	 */
		public final double score(double tf, double docLength) {
		 return    score (tf, docLength,documentFrequency , termFrequency , keyFrequency) ;
	}

	
	/**
	 * Uses DFReeKLIM to compute a weight for a term in a document.
	 * @param tf The term frequency of the term in the document
	 * @param docLength the document's length
	 * @param documentFrequency The document frequency of the term (ignored)
	 * @param termFrequency the term frequency in the collection (ignored)
	 * @param keyFrequency the term frequency in the query (ignored).
	 * @return the score assigned by the weighting model DFReeKLIM.
	 */
	public final double score(
		double tf,
		double docLength,
		double documentFrequency,
		double termFrequency,
		double keyFrequency) 
	{
	    /**
	     * The DFReeKLIM model. Obtaining any Dfree model by combining disfferent information measures. 
	     * (KL is not really a metric or a measure in the sense of the mathematical definition).
	     */
	    
	    //The two neighbouring distributions, the true and the smoothed one:
	    double trueDocumentProbability = tf/docLength;                                 //the true probability of the message
	    double smoothedTrueDocumentProbability  = (tf  +1d)/(docLength +1d);         //Polya's urn model to smooth the true probability of the message
	    //  The prior distribution:
	    double collectionPrior = termFrequency/numberOfTokens;
	    //The two additional coding costs of one query token:
	    double KL1 = WeightingModelLibrary.log(smoothedTrueDocumentProbability/trueDocumentProbability)  ; 
	    double KL2 = WeightingModelLibrary.log ( trueDocumentProbability/collectionPrior)      ;
	    //The average of the two additional coding costs of the query term over the Twitter's message :
	    
 	    return keyFrequency  *   tf* KL1 * KL2;      
	}
}
