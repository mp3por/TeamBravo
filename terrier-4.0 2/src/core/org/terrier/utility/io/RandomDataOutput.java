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
 * The Original Code is RandomDataInput.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Craig Macdonald (craigm{at}dcs.gla.ac.uk)
 */
package org.terrier.utility.io;
import java.io.IOException;
import java.io.DataOutput;

/** This interface represents an interface on the writing behaviour of a RandomAccessFile.
 * @since 2.2
 * @author Craig Macdonald
 */
public interface RandomDataOutput extends DataOutput, RandomDataInput
{
	/** Sets the length of this file.  */
	void setLength(long newLength) throws IOException;
}
