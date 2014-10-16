package net.eekysam.jflick.io;

import java.util.ArrayDeque;
import java.util.Scanner;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Input;

public class InputFromConsole extends Input
{
	Scanner input;
	public boolean addReturn = false;

	private ArrayDeque<Byte> ibuf = new ArrayDeque<Byte>();
	
	private long readTime;

	public InputFromConsole(boolean addReturn)
	{
		this.input = new Scanner(System.in);
		this.addReturn = addReturn;
	}

	public byte read()
	{
		while (true)
		{
			try
			{
				return this.doRead();
			}
			catch (NumberFormatException e)
			{
				System.out.printf("Input was not valid.%nUse ~# for decimal, ~0x# for hex, and ~'*' for characters.%n");
			}
		}
	}

	private byte doRead()
	{
		if (this.ibuf.isEmpty())
		{
			long ns = System.nanoTime();
			System.out.print("~");
			String in = this.input.nextLine();
			if (in.startsWith("0x"))
			{
				in = in.substring(2);
				this.ibuf.add((byte) Integer.parseInt(in, 16));
			}
			else if (in.startsWith("'") && in.endsWith("'") && in.length() == 3)
			{
				this.ibuf.add((byte) in.charAt(1));
			}
			else if (!in.isEmpty())
			{
				this.ibuf.add((byte) Integer.parseInt(in));
			}
			if (this.addReturn)
			{
				this.ibuf.add((byte) Config.enter);
			}
			ns = System.nanoTime() - ns;
			this.readTime += ns;
		}
		return this.ibuf.poll();
	}
	
	public long delayedTime()
	{
		return this.readTime;
	}
}
