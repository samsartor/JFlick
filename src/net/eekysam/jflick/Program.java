package net.eekysam.jflick;

import java.util.ArrayList;

import net.eekysam.jflick.ProgramReader.BrainfuckFormatException;

public class Program
{
	protected int appPoint = 0;
	public Tape mem = new Tape();
	public Input in;
	public Output out;
	public int memPoint = 0;
	public long waitTime = 0;
	
	public ArrayList<Command> app;
	
	public Program(String program, Input in, Output out) throws BrainfuckFormatException
	{
		this(new ProgramReader(program), in, out);
	}
	
	public Program(ProgramReader reader, Input in, Output out) throws BrainfuckFormatException
	{
		this(new ArrayList<Command>(), in, out);
		reader.read();
		this.app = reader.app;
		this.app.trimToSize();
	}
	
	public Program(ArrayList<Command> app, Input in, Output out)
	{
		this.app = app;
		this.in = in;
		this.out = out;
	}
	
	public void run()
	{
		System.out.printf("---BEGIN---%n");
		long ops = 0;
		long ns = System.nanoTime();
		while (this.appPoint < this.app.size())
		{
			Command com = this.app.get(this.appPoint);
			this.appPoint = com.run(this.appPoint, this);
			ops++;
		}
		ns = System.nanoTime() - ns;
		ns -= waitTime;
		System.out.printf("%n----END----%n%n");
		System.out.printf("Ran %,d abbreviated commands in %.2fs at %.1fns per command%n", ops, ns / 1000000000.0F, (float) ns / ops);
		this.mem.printInfo();
	}
}
