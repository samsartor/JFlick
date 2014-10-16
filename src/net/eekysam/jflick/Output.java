package net.eekysam.jflick;

public abstract class Output
{
	public static enum EnumOutType
	{
		RAW,
		HEX,
		DEC,
		CAR;
	}
	
	public EnumOutType type;
	
	public abstract void write(byte b);
}
