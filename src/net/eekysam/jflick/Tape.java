package net.eekysam.jflick;

import java.io.IOException;
import java.util.ArrayList;

import org.omg.CORBA_2_3.portable.OutputStream;

public class Tape
{
	private ArrayList<byte[]> data;
	private int offset = 0;
	private long ops = 0;
	private int min = 0;
	private int max = 0;
	
	public Tape()
	{
		this.data = new ArrayList<byte[]>();
		this.data.add(this.newBuffer());
	}
	
	public void add(int index, int ammount)
	{
		int i = index >> 8;
		i += this.offset;
		int j = index & 0xFF;
		while (i < 0)
		{
			this.offset += 1;
			i += 1;
			this.data.add(0, this.newBuffer());
		}
		while (i >= this.data.size())
		{
			this.data.add(this.newBuffer());
		}
		this.data.get(i)[j] += ammount;
		this.ops++;
		this.updateRange(index);
	}
	
	public void set(int index, byte value)
	{
		int i = index >> 8;
		i += this.offset;
		int j = index & 0xFF;
		while (i < 0)
		{
			this.offset += 1;
			i += 1;
			this.data.add(0, this.newBuffer());
		}
		while (i >= this.data.size())
		{
			this.data.add(this.newBuffer());
		}
		this.data.get(i)[j] = value;
		this.ops++;
		this.updateRange(index);
	}
	
	public byte get(int index)
	{
		int i = index >> 8;
		i += this.offset;
		int j = index & 0xFF;
		if (i < 0 || i >= this.data.size())
		{
			return (byte) 0;
		}
		this.ops++;
		this.updateRange(index);
		return this.data.get(i)[j]; 
	}
	
	private byte[] newBuffer()
	{
		return new byte[256];
	}
	
	private void updateRange(int loc)
	{
		if (loc < this.min)
		{
			this.min = loc;
		}
		if (loc > this.max)
		{
			this.max = loc;
		}
	}
	
	public void printInfo()
	{
		System.out.printf("Preformed %,d memory operations across a range of %,d bytes%n", this.ops, this.max - this.min);
	}
	
	public int save(OutputStream out) throws IOException
	{
		for (byte[] b : this.data)
		{
			out.write(b);
		}
		return this.offset * 256;
	}
}
