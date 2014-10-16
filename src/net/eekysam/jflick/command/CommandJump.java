package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandJump extends Command
{
	public int to = -1;
	
	public CommandJump(int loc)
	{
		super(loc);
	}
	
	public int run(int point, Program app)
	{
		if (app.mem.get(app.memPoint) == 0)
		{
			point = this.to;
		}
		return point + 1;
	}

	@Override
	public String expand()
	{
		return "" + Config.jump;
	}
}
