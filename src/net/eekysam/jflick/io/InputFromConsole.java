package net.eekysam.jflick.io;

import java.util.Scanner;

import net.eekysam.jflick.Input;

public class InputFromConsole extends Input
{
	Scanner input;

	public InputFromConsole()
	{
		this.input = new Scanner(System.in);
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
		System.out.print("~");
		String in = this.input.next();
		if (in.startsWith("0x"))
		{
			in = in.substring(2);
			return (byte) Integer.parseInt(in, 16);
		}
		else if (in.startsWith("'") && in.endsWith("'") && in.length() == 3)
		{
			return (byte) in.charAt(1);
		}
		else
		{
			return (byte) Integer.parseInt(in);
		}
	}
}
