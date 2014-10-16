package net.eekysam.jflick.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.eekysam.jflick.Output;

public class OutputFromFile extends Output
{
	protected FileOutputStream out;

	public OutputFromFile(File file, EnumOutType type) throws FileNotFoundException
	{
		this.type = type;
		this.out = new FileOutputStream(file);
	}

	@Override
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
		try
		{
			switch (this.type)
			{
				case RAW:
					this.out.write((byte) c);
					return;
				case HEX:
					this.out.write(hex.getBytes());
					return;
				case DEC:
					System.out.print(b & 0xFF);
					System.out.print(" ");
					return;
				case CAR:
					if (t)
					{
						this.out.write((byte) c);
						this.out.write((byte) '\n');
					}
					else
					{
						this.out.write(Character.getName(c).getBytes());
						this.out.write((byte) '\n');
					}
					return;
			}
		}
		catch (IOException e)
		{

		}

	}
}
