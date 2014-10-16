package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandZeroSet extends Command
{
	public int value;
	
	public CommandZeroSet(int loc)
	{
		super(loc);
	}

	public int run(int point, Program app)
	{
		app.mem.set(app.memPoint, (byte) value);
		return point + 1;
	}
	
	@Override
	public String expand()
	{
		String com = "";
		com += Config.jump;
		com += Config.dec;
		com += Config.back;
		char op = Config.inc;
		if (this.value < 0)
		{
			op = Config.dec;
		}
		for (int i = 0; i < Math.abs(this.value); i++)
		{
			com += op;
		}
		return com;
	}
}
