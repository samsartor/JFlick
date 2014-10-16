package net.eekysam.jflick.command;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Program;
import net.eekysam.jflick.Command;

public class CommandCopyLarge extends Command
{
	public int[] mult;
	public int[] offs;
	
	public CommandCopyLarge(int loc)
	{
		super(loc);
	}

	public int run(int point, Program app)
	{
		int val = app.mem.get(app.memPoint) & 0xFF;
		for (int i = 0; i < this.offs.length; i++)
		{
			app.mem.add(app.memPoint + this.offs[i], val * this.mult[i]);
		}
		app.mem.set(app.memPoint, (byte) 0);
		return point + 1;
	}
	
	@Override
	public String expand()
	{
		int off = 0;
		String com = "" + Config.jump;
		for (int i = 0; i < this.offs.length; i++)
		{
			com += this.expandPart(this.offs[i] - off, Config.right, Config.left);
			off = this.offs[i];
			com += this.expandPart(this.mult[i], Config.inc, Config.dec);
		}
		com += this.expandPart(-off, Config.right, Config.left);
		com += Config.dec;
		com += "]";
		return com;
	}
}
