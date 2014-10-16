package net.eekysam.jflick.io;

import net.eekysam.jflick.Output;

public class OutputFromConsole extends Output
{
	public OutputFromConsole(EnumOutType type)
	{
		this.type = type;
	}
	
	public void write(byte b)
	{
		char c = (char) b;
		boolean t = !(Character.isWhitespace(c) || Character.isISOControl(c));
		String hex = Long.toHexString(b & (long) 0xFF);
		hex = hex.toUpperCase();
		if (hex.length() < 2)
		{
			hex = "0" + hex;
		}
		switch (this.type)
		{
			case RAW:
				System.out.print(c);
				return;
			case HEX:
				System.out.print(hex);
				return;
			case DEC:
				System.out.print(b & 0xFF);
				System.out.print(" ");
				return;
			case CAR:
				if (t)
				{
					System.out.println(c);
				}
				else
				{
					System.out.println(Character.getName(c));
				}
				return;
		}
	}
}
