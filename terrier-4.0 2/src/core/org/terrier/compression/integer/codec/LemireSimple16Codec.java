package org.terrier.compression.integer.codec;
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
 * The Original Code is LemireOptPFDVBCodec.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *  Matteo Catena
 *  Craig Macdonald <craig.macdonald@glasgow.ac.uk> 
 */

/** Simple16 codec, as implemented by JavaFastPFOR package
 * 
 * @author Matteo Catena
 * @since 4.0
 * */
public class LemireSimple16Codec extends LemireCodec {

	public LemireSimple16Codec() throws Exception {
		super(new String[]{"Simple16"});
	}

}
