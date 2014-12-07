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
 * The Original Code is KamikazePForDeltaVBCodec.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *  Matteo Catena
 *   
 */

package org.terrier.compression.integer.codec;

/** PForDelta - as implemented by LinkedIn's Kamikaze library, 
 *  falling back to VariableByte 
 * 
 *  @author Matteo Catena
 *  @since 4.0
 * 
 */
public class KamikazePForDeltaVBCodec extends LemireCodec {

	public KamikazePForDeltaVBCodec() throws Exception {
		super(new String[]{"Composition", "Kamikaze", "VariableByte"});
	}

}
