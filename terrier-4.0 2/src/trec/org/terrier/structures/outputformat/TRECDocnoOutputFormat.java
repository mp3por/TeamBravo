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
 * The Original Code is TRECDocnoOutputFormat.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Gianni Amati <gba{a.}fub.it> (original author)
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk>
 *   Ben He <ben{a.}dcs.gla.ac.uk>
 *   Craig Macdonald <craigm{a.}dcs.gla.ac.uk>
 */

package org.terrier.structures.outputformat;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.terrier.matching.ResultSet;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;
import org.terrier.utility.ApplicationSetup;


/** Standard OutputFormat for writing TREC runs */
public class TRECDocnoOutputFormat implements OutputFormat {
	Index index;
	/** The logger used */
	protected static final Logger logger = Logger.getLogger(TRECDocnoOutputFormat.class);
	
	/** Create a TRECDocnoOutputFormat using the specified index for
	 * looking up the docnos */
	public TRECDocnoOutputFormat(Index _index) {
		//System.err.println("new TRECDocnoOutputFormat created");
		this.index = _index;
	}
	
	/** method which extracts the docnos for the prescribed resultset */
	protected String[] obtainDocnos(final String metaIndexDocumentKey, final SearchRequest q, final ResultSet set) throws IOException
	{
		String[] docnos;
		if (set.hasMetaItems(metaIndexDocumentKey)) {
			docnos = set.getMetaItems(metaIndexDocumentKey);
		} else {
			final MetaIndex metaIndex = index.getMetaIndex();
			docnos = metaIndex.getItems(metaIndexDocumentKey, set.getDocids());
		}
		return docnos;
	}

	/**
	 * Prints the results for the given search request, using the specified
	 * destination.
	 * 
	 * @param pw
	 *            PrintWriter the destination where to save the results.
	 * @param q
	 *            SearchRequest the object encapsulating the query and the
	 *            results.
	 */
	public void printResults(final PrintWriter pw, final SearchRequest q,
			String method, String iteration, int _RESULTS_LENGTH) throws IOException {
		final ResultSet set = q.getResultSet();
		final String metaIndexDocumentKey = ApplicationSetup.getProperty("trec.querying.outputformat.docno.meta.key", "docno");
		final double[] scores = set.getScores();
		if (set.getResultSize() == 0) {
			logger.warn("No results retrieved for query " + q.getQueryID());
			return;
		}
		String[] docnos = obtainDocnos(metaIndexDocumentKey, q, set);
		
		final int maximum = _RESULTS_LENGTH > set.getResultSize()
				|| _RESULTS_LENGTH == 0 ? set.getResultSize()
				: _RESULTS_LENGTH;
		logger.debug("Writing " + maximum + " results for query " + q.getQueryID());
				
		// if the minimum number of documents is more than the
		// number of documents in the results, aw.length, then
		// set minimum = aw.length

		// if (minimum > set.getResultSize())
		// minimum = set.getResultSize();
		//final String iteration = ITERATION + "0";
		final String queryIdExpanded = q.getQueryID() + " " + iteration
				+ " ";
		final String methodExpanded = " " + method + ApplicationSetup.EOL;
		StringBuilder sbuffer = new StringBuilder();
		// the results are ordered in desceding order
		// with respect to the score.
		int limit = 10000;
		int counter = 0;
		for (int i = 0; i < maximum; i++) {
			if (scores[i] == Double.NEGATIVE_INFINITY)
				continue;
			sbuffer.append(queryIdExpanded);
			sbuffer.append(docnos[i]);
			sbuffer.append(" ");
			sbuffer.append(i);
			sbuffer.append(" ");
			sbuffer.append(scores[i]);
			sbuffer.append(methodExpanded);
			counter++;
			if (counter % limit == 0) {
				pw.write(sbuffer.toString());
				sbuffer = null;
				sbuffer = new StringBuilder();
				pw.flush();
			}
		}
		pw.write(sbuffer.toString());
		pw.flush();
	}
}
