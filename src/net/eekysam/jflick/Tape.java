package net.eekysam.jflick;

import java.io.IOException;

import org.omg.CORBA_2_3.portable.OutputStream;

public class Tape
{
	private byte[] data;
	private int offset = 0;
	
	public Tape(int size, int offset)
	{
		this.data = new byte[size];
		this.offset = offset;
	}
	
	public void add(int index, int ammount)
	{
		this.data[index + this.offset] += ammount;
	}
	
	public void set(int index, byte value)
	{
		this.data[index + this.offset] = value;
	}
	
	public byte get(int index)
	{
		return this.data[index + this.offset];
	}

	public int save(OutputStream out) throws IOException
	{
		out.write(this.data);
		return this.offset * 256;
	}
}
