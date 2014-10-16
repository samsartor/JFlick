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
	
	protected String expandPart(int val, char pos, char neg)
	{
		String com = "";
		char op = pos;
		if (val < 0)
		{
			op = neg;
		}
		for (int i = 0; i < Math.abs(val); i++)
		{
			com += op;
		}
		return com;
	}
}
