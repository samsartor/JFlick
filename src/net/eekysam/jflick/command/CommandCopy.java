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
		com += this.expandPart(this.move, Config.right, Config.left);
		com += this.expandPart(this.mult, Config.inc, Config.dec);
		com += this.expandPart(this.move, Config.left, Config.right);
		com += "-]";
		return com;
	}
}
