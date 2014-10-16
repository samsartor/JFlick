package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
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
		app.out.write(app.mem.get(app.memPoint));
		return point + 1;
	}
	
	@Override
	public String expand()
	{
		return "" + Config.write;
	}
}
