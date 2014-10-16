package net.eekysam.jflick.command;

import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandWrite extends Command
{
	public CommandWrite(int loc)
	{
		super(loc);
	}

	public int run(int point, Program app)
	{
		long ns = System.nanoTime();
		app.out.write(app.mem.get(app.memPoint));
		ns = System.nanoTime() - ns;
		app.waitTime += ns;
		return point + 1;
	}
}
