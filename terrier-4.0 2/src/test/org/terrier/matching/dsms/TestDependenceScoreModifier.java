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
 * The Original is in 'TestDependenceScoreModifier.java'
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Craig Macdonald <craigm{a.}dcs.gla.ac.uk>
 *   Richard McCreadie <richard.mccreadie@glasgow.ac.uk>
 */
package org.terrier.matching.dsms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.terrier.indexing.IndexTestUtils;
import org.terrier.matching.CollectionResultSet;
import org.terrier.matching.MatchingQueryTerms;
import org.terrier.matching.ResultSet;
import org.terrier.matching.models.InL2;
import org.terrier.structures.Index;
import org.terrier.tests.ApplicationSetupBasedTest;
import org.terrier.utility.ApplicationSetup;

public class TestDependenceScoreModifier extends ApplicationSetupBasedTest {

	
	@Test public void testSingleDocumentDFR() throws Exception {
		ApplicationSetup.setProperty("proximity.dependency.type", "SD");
		testSingleDocument(new DFRDependenceScoreModifier());
		
		ApplicationSetup.setProperty("proximity.dependency.type", "FD");
		testSingleDocument(new DFRDependenceScoreModifier());
	}
	
	@Test public void testSingleDocumentMRF() throws Exception {
		ApplicationSetup.setProperty("proximity.dependency.type", "SD");
		testSingleDocument(new MRFDependenceScoreModifier());
		
		ApplicationSetup.setProperty("proximity.dependency.type", "FD");
		testSingleDocument(new MRFDependenceScoreModifier());
	}
	
	void testSingleDocument(DependenceScoreModifier dsm) throws Exception {
		Index index1 = IndexTestUtils.makeIndexBlocks(
				new String[]{"doc1"}, 
				new String[]{"The quick brown fox jumps over the lazy dog"});
		ResultSet r;
		MatchingQueryTerms mqt;
		
		mqt = new MatchingQueryTerms();
		mqt.setDefaultTermWeightingModel(new InL2());
		mqt.setTermProperty("quick");
		mqt.setTermProperty("dog");		
		r = new CollectionResultSet(1);
		r.getScores()[0] = 1.0d;
		dsm.modifyScores(index1, mqt, r);
		//TODO: MRF reduces the  scores of non-matching documents?
		assertEquals(1.0d, r.getScores()[0], 0.2d);
		
		mqt = new MatchingQueryTerms();
		mqt.setDefaultTermWeightingModel(new InL2());
		mqt.setTermProperty("brown");
		mqt.setTermProperty("fox");
		r = new CollectionResultSet(1);
		r.getScores()[0] = 1.0d;
		dsm.modifyScores(index1, mqt, r);
		assertTrue(r.getScores()[0] > 1.0d);
	}
	
}
