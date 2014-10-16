package net.eekysam.jflick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import net.eekysam.jflick.ProgramReader.BrainfuckFormatException;

public class Program
{
	protected int appPoint = 0;
	public int memPoint = 0;
	
	public Tape mem;
	public Input in;
	public Output out;

	public ArrayList<Command> app;

	public Program(String program, Input in, Output out, int size, int off) throws BrainfuckFormatException
	{
		this(new ProgramReader(program), in, out, size, off);
	}

	public Program(ProgramReader reader, Input in, Output out, int size, int off) throws BrainfuckFormatException
	{
		this(new ArrayList<Command>(), in, out, size, off);
		reader.read();
		this.app = reader.app;
		this.app.trimToSize();
	}

	public Program(ArrayList<Command> app, Input in, Output out, int size, int off)
	{
		this.app = app;
		this.in = in;
		this.out = out;
		this.mem = new Tape(size, off);
	}

	public void run()
	{
		if (Config.debug)
		{
			System.out.printf("---BEGIN---%n");
			long ops = 0;
			long ns = System.nanoTime();
			int num = this.app.size();
			while (this.appPoint < num)
			{
				this.appPoint = this.app.get(this.appPoint).run(this.appPoint, this);
				ops++;
			}
			ns = System.nanoTime() - ns;
			ns -= this.in.delayedTime();
			ns -= this.out.delayedTime();
			System.out.printf("%n----END----%n%n");
			System.out.printf("Ran %,d abbreviated commands in %.2fs at %.2fns per command%n", ops, ns / 1000000000.0F, (float) ns / ops);
		}
		else
		{
			while (this.appPoint < this.app.size())
			{
				this.appPoint = this.app.get(this.appPoint).run(this.appPoint, this);
			}
		}
	}
	
	public void saveCode(File save)
	{
		String code = "";
		for (Command com : this.app)
		{
			code += com.expand();
		}
		if (save != null)
		{
			try
			{
				save.createNewFile();
				Files.write(save.toPath(), code.getBytes());
			}
			catch (IOException e)
			{
				System.out.println("Could not save raw code");
			}
		}
		else
		{
			System.out.printf("%n---CODE---%n");
			System.out.printf("%s%n", code);
		}
	}
}
