package net.eekysam.jflick;

public abstract class Command
{
	public final int loc;
	
	public Command(int loc)
	{
		this.loc = loc;
	}

	public abstract int run(int point, Program app);
	
	public abstract String expand();
}
