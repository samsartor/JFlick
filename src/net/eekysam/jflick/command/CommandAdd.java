package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandAdd extends Command
{
	public int num;
	
	public CommandAdd(int num, int loc)
	{
		super(loc);
		this.num = num;
	}
	
	public int run(int point, Program app)
	{
		app.mem.add(app.memPoint, num);
		return point + 1;
	}

	@Override
	public String expand()
	{
		String com = "";
		char op = Config.inc;
		if (this.num < 0)
		{
			op = Config.dec;
		}
		for (int i = 0; i < Math.abs(this.num); i++)
		{
			com += op;
		}
		return com;
	}
}
