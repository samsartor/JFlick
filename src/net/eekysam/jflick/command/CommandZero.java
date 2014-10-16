package net.eekysam.jflick.command;

import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandZero extends Command
{	
	public CommandZero(int loc)
	{
		super(loc);
	}

	public int run(int point, Program app)
	{
		app.mem.set(app.memPoint, (byte) 0);
		return point + 1;
	}
}
