package net.eekysam.jflick.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.eekysam.jflick.Input;

public class InputFromFile extends Input
{
	FileInputStream input;

	public InputFromFile(File file) throws FileNotFoundException
	{
		this.input = new FileInputStream(file);
	}

	@Override
	public byte read()
	{
		try
		{
			return (byte) this.input.read();
		}
		catch (IOException e)
		{
			return 0;
		}
	}

}
