package net.eekysam.jflick;

public abstract class Input
{
	public abstract byte read();
	
	public long delayedTime()
	{
		return 0;
	}
}
