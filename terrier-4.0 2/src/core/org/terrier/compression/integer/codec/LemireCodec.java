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
 * The Original Code is LemireCodec.java.
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *  Matteo Catena
 *  Craig Macdonald <craig.macdonald@glasgow.ac.uk> 
 */

package org.terrier.compression.integer.codec;

import java.io.IOException;
import java.nio.ByteBuffer;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;

import org.terrier.compression.integer.ByteIn;
import org.terrier.compression.integer.ByteOut;
import org.terrier.utility.ArrayUtils;

/**
 * Generic IntegerCodec implementation which wraps any IntegerCODEC from the JavaFastPFOR package.
 * A generic set of IntegerCODECs can also be specified using String parameters to the constructor.
 * 
 * @author Matteo Catena and Craig Macdonald
 * @since 4.0
 */
public class LemireCodec extends IntegerCodec {

	private final IntWrapper inpos;
	private final IntWrapper outpos;
	private final IntegerCODEC codec;

	public LemireCodec(String[] params) throws Exception {
		this(getCODEC(params));
	}
	
	public LemireCodec(IntegerCODEC codec) {

		this.codec = codec;
		inpos = new IntWrapper(0);
		outpos = new IntWrapper(0);
	}

	@Override
	public final void compress(final int[] in, final int len, final ByteOut out) throws IOException {

		inpos.set(0);
		outpos.set(0);

		supportArray = ArrayUtils.growOrCreate(supportArray, len * 4 + 1024);
		codec.compress(in, inpos, len, supportArray, outpos);
		
 		int bytes = outpos.get() * 4;
		if (outBuffer.capacity() < bytes) 
			outBuffer = ByteBuffer.allocate(bytes * 2);
		else
			outBuffer.position(0);

		outBuffer.asIntBuffer().put(supportArray, 0, outpos.get());
		out.writeVInt(outpos.get());
		out.write(outBuffer.array(), 0, bytes);
	}

	@Override
	public final void decompress(final ByteIn in, final int[] out, final int num) throws IOException {

		final int ints = in.readVInt();
		final int len = ints*4;
		//System.err.println("reading " + len + " bytes");
		assert len >= 0;
		
		inpos.set(0);
		outpos.set(0);
				
		if (inBuffer.capacity() < len)
			inBuffer = ByteBuffer.allocate(len * 2);
		else
			inBuffer.position(0);
		in.readFully(inBuffer.array(), 0, len);
		
		supportArray = ArrayUtils.growOrCreate(supportArray, ints);
		inBuffer.asIntBuffer().get(supportArray, 0, ints);

		codec.uncompress(supportArray, inpos, ints, out, outpos, num);
	}

	@Override
	public final void skip(final ByteIn in) throws IOException {

		in.skipBytes(in.readVInt()*4);
	}
	
	public static IntegerCODEC getCODEC(String[] params) throws Exception
	{
		IntegerCODEC integerCodec;
		// 1. get IntegerCODEC
		String integerCodecClassName = params[0];
		assert integerCodecClassName != null;
		
		if (! integerCodecClassName.contains("."))
			integerCodecClassName = IntegerCODEC.class.getPackage().getName() +"." + integerCodecClassName;
		
		Class<? extends IntegerCODEC> integerCodecClass = Class.forName(integerCodecClassName).asSubclass(IntegerCODEC.class);
		
		if (!Composition.class.isAssignableFrom(integerCodecClass)) {
		
			integerCodec = integerCodecClass.newInstance();
		
		} else {
			// 1. get primary codec
			String primaryCodecClassName = params[1];
			assert primaryCodecClassName != null;	
			if (! primaryCodecClassName.contains("."))
				primaryCodecClassName = IntegerCODEC.class.getPackage().getName() +"." + primaryCodecClassName;
			Class<? extends IntegerCODEC> primaryCodecClass = Class.forName(primaryCodecClassName).asSubclass(IntegerCODEC.class);
			// 2. get secondary codec
			String secondaryCodecClassName = params[2];
			assert secondaryCodecClassName != null;
			if (! secondaryCodecClassName.contains("."))
				secondaryCodecClassName = IntegerCODEC.class.getPackage().getName() +"." + secondaryCodecClassName;
			
			
			Class<? extends IntegerCODEC> secondaryCodecClass = Class.forName(secondaryCodecClassName).asSubclass(IntegerCODEC.class);
		
			integerCodec = (Composition) integerCodecClass.getConstructor(
					IntegerCODEC.class, IntegerCODEC.class).newInstance(
					primaryCodecClass.newInstance(),
					secondaryCodecClass.newInstance());
		}
		return integerCodec;
	}
}
