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
 * The Original Code is QuerySource.java.
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

package org.terrier.applications.batchquerying;

import java.util.Iterator;

/** This interface denotes a source of queries for batch evaluation 
 * @since 3.0
 */
public interface QuerySource extends Iterator<String> {
	/** 
	 * Returns the query identifier of the last query
	 * fetched, or the first one, if none has been
	 * fetched yet.
	 * @return String the query number of a query.
	 */
	String getQueryId();
	
	/** Resets the query source back to the first query. */
	void reset();
	
	/** Return information about the query source */
	String[] getInfo();
}
