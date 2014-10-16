package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
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
		app.mem.set(app.memPoint, app.in.read());
		return point + 1;
	}

	@Override
	public String expand()
	{
		return "" + Config.read;
	}
}
