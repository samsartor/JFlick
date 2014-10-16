package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
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
	
	@Override
	public String expand()
	{
		String com = "";
		com += Config.jump;
		com += Config.dec;
		com += Config.back;
		return com;
	}
}
