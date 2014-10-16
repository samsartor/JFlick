package net.eekysam.jflick.command;

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
}
