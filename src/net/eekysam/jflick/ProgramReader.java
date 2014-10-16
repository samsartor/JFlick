package net.eekysam.jflick;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

import net.eekysam.jflick.command.CommandAdd;
import net.eekysam.jflick.command.CommandBack;
import net.eekysam.jflick.command.CommandFindZero;
import net.eekysam.jflick.command.CommandJump;
import net.eekysam.jflick.command.CommandMove;
import net.eekysam.jflick.command.CommandRead;
import net.eekysam.jflick.command.CommandWrite;
import net.eekysam.jflick.command.CommandZero;

public class ProgramReader
{
	public static class BrainfuckFormatException extends IOException
	{
		private static final long serialVersionUID = -3761013214295395893L;

		BrainfuckFormatException()
		{
			super();
		}

		BrainfuckFormatException(String msg)
		{
			super(msg);
		}
	}

	private CharSequence chars;
	private int loc = 0;
	private int num = 0;
	private int ignored = 0;
	public boolean ignoreInvalid = true;
	public ArrayList<Command> app = new ArrayList<Command>();

	public ProgramReader(CharSequence chars)
	{
		this.chars = chars;
	}

	public void read() throws BrainfuckFormatException
	{
		long ns = System.nanoTime();

		this.readBase();
		this.simplifyZero();
		this.simplifyFindZero();
		this.updateLocs();
		this.num = this.app.size();

		ns = System.nanoTime() - ns;
		int length = this.chars.length();
		System.out.printf("Finished parsing a %,d character program in %.2fms containing %,d commands%n", length, ns / 1000000.0F, length - this.ignored);
		System.out.printf("Abbreviation reduced the program to %,d commands (%.0f%%)%n%n", this.num, (this.num * 100.0F) / (length - this.ignored));

	}

	private void simplifyZero()
	{
		for (int i = 0; i < this.app.size() - 3; i++)
		{
			Command a = this.app.get(i);
			Command b = this.app.get(i + 1);
			Command c = this.app.get(i + 2);
			if (a instanceof CommandJump && b instanceof CommandAdd && c instanceof CommandBack)
			{
				if (((CommandAdd) b).num == -1)
				{
					this.app.remove(i);
					this.app.remove(i);
					this.app.remove(i);
					this.app.add(i, new CommandZero(a.loc));
				}
			}
		}
	}

	private void simplifyFindZero()
	{
		for (int i = 0; i < this.app.size() - 3; i++)
		{
			Command a = this.app.get(i);
			Command b = this.app.get(i + 1);
			Command c = this.app.get(i + 2);
			if (a instanceof CommandJump && b instanceof CommandMove && c instanceof CommandBack)
			{
				this.app.remove(i);
				this.app.remove(i);
				this.app.remove(i);
				this.app.add(i, new CommandFindZero(((CommandMove) b).num, a.loc));
			}
		}
	}

	private void readBase()
	{
		while (this.loc < this.chars.length())
		{
			Command com = this.readPart();
			if (com == null)
			{
				this.ignored++;
			}
			else
			{
				this.app.add(com);
			}
			this.loc++;
		}
	}

	private void updateLocs()
	{
		ArrayDeque<CommandJump> jumps = new ArrayDeque<CommandJump>();
		ArrayDeque<Integer> locs = new ArrayDeque<Integer>();
		for (int i = 0; i < this.app.size(); i++)
		{
			Command com = this.app.get(i);
			if (com instanceof CommandJump)
			{
				jumps.push((CommandJump) com);
				locs.push(i);
			}
			else if (com instanceof CommandBack)
			{
				CommandJump jump = jumps.pop();
				int loc = locs.pop();
				CommandBack back = (CommandBack) com;
				back.to = loc;
				jump.to = i;
			}
		}
	}

	private Command readPart()
	{
		switch (this.chars.charAt(this.loc))
		{
			case '>':
			case '<':
				return this.readMoves();
			case '+':
			case '-':
				return this.readAdd();
			case '[':
				return new CommandJump(this.loc);
			case ']':
				return new CommandBack(this.loc);
			case '.':
				return new CommandWrite(this.loc);
			case ',':
				return new CommandRead(this.loc);
			default:
				return null;
		}
	}

	public CommandAdd readAdd()
	{
		int start = this.loc;
		int num = 0;
		while (this.loc < this.chars.length())
		{
			char c = this.chars.charAt(this.loc);
			if (c == '+')
			{
				num++;
			}
			else if (c == '-')
			{
				num--;
			}
			else
			{
				this.loc--;
				break;
			}
			this.loc++;
		}
		return new CommandAdd(num, start);
	}

	public CommandMove readMoves()
	{
		int start = this.loc;
		int num = 0;
		while (this.loc < this.chars.length())
		{
			char c = this.chars.charAt(this.loc);
			if (c == '>')
			{
				num++;
			}
			else if (c == '<')
			{
				num--;
			}
			else
			{
				this.loc--;
				break;
			}
			this.loc++;
		}
		return new CommandMove(num, start);
	}
}