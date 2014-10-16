package net.eekysam.jflick.io;

import java.util.ArrayDeque;
import java.util.Scanner;

import net.eekysam.jflick.Config;
import net.eekysam.jflick.Input;

public class InputFromStringConsole extends Input
{
	Scanner input;
	private ArrayDeque<Character> ibuf = new ArrayDeque<Character>();
	public boolean addReturn = false;
	
	private long readTime;

	public InputFromStringConsole(boolean addReturn)
	{
		this.input = new Scanner(System.in);
		this.addReturn = addReturn;
	}

	public byte read()
	{
		if (this.ibuf.isEmpty())
		{
			long ns = System.nanoTime();
			char[] chars = this.input.nextLine().toCharArray();
			for (char c : chars)
			{
				this.ibuf.add(c);
			}
			if (this.addReturn)
			{
				this.ibuf.add(Config.enter);
			}
			ns = System.nanoTime() - ns;
			this.readTime += ns;
		}
		return (byte) (char) this.ibuf.poll();
	}
	
	public long delayedTime()
	{
		return this.readTime;
	}
}
