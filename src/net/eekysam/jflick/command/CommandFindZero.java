package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandFindZero extends Command
{	
	int move;
	
	public CommandFindZero(int move, int loc)
	{
		super(loc);
		this.move = move;
	}

	public int run(int point, Program app)
	{
		while (app.mem.get(app.memPoint) != 0)
		{
			app.memPoint += this.move;
		}
		return point + 1;
	}

	@Override
	public String expand()
	{
		char op = Config.right;
		if (this.move < 0)
		{
			op = Config.left;
		}
		int n = Math.abs(this.move);
		String com = "" + Config.jump;
		for (int i = 0; i < n; i++)
		{
			com += op;
		}
		return com + Config.back;
	}
}
