package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandCopy extends Command
{
	public int mult;
	public int move;
	
	public CommandCopy(int loc)
	{
		super(loc);
	}

	public int run(int point, Program app)
	{
		int val = app.mem.get(app.memPoint) & 0xFF;
		val *= this.mult;
		app.mem.set(app.memPoint, (byte) 0);
		app.mem.add(app.memPoint + this.move, val);
		return point + 1;
	}
	
	@Override
	public String expand()
	{
		String com = "[";
		char op = Config.right;
		if (this.move < 0)
		{
			op = Config.left;
		}
		for (int i = 0; i < Math.abs(this.move); i++)
		{
			com += op;
		}
		op = Config.inc;
		if (this.mult < 0)
		{
			op = Config.dec;
		}
		for (int i = 0; i < Math.abs(this.mult); i++)
		{
			com += op;
		}
		op = Config.left;
		if (this.move < 0)
		{
			op = Config.right;
		}
		for (int i = 0; i < Math.abs(this.move); i++)
		{
			com += op;
		}
		com += "-]";
		return com;
	}
}
