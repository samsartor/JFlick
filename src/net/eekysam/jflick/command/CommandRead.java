package net.eekysam.jflick.command;

import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandRead extends Command
{
	public CommandRead(int loc)
	{
		super(loc);
	}

	public int run(int point, Program app)
	{
		long ns = System.nanoTime();
		app.mem.set(app.memPoint, app.in.read());
		ns = System.nanoTime() - ns;
		app.waitTime += ns;
		return point + 1;
	}
}
