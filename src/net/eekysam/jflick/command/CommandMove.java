package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandMove extends Command
{
	public int num;

	public CommandMove(int num, int loc)
	{
		super(loc);
		this.num = num;
	}

	public int run(int point, Program app)
	{
		app.memPoint += num;
		return point + 1;
	}

	@Override
	public String expand()
	{
		return this.expandPart(this.num, Config.right, Config.left);
	}
}
