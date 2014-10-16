package net.eekysam.jflick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Arrays;

import net.eekysam.jflick.Output.EnumOutType;
import net.eekysam.jflick.ProgramReader.BrainfuckFormatException;
import net.eekysam.jflick.io.InputFromConsole;
import net.eekysam.jflick.io.InputFromFile;
import net.eekysam.jflick.io.InputFromStringConsole;
import net.eekysam.jflick.io.OutputFromConsole;
import net.eekysam.jflick.io.OutputFromFile;

public class Main
{
	public static void main(String[] args)
	{
		String program = null;
		File inf = null;
		File outf = null;
		EnumOutType outt = EnumOutType.RAW;
		boolean str = false;
		boolean rtrn = false;
		int size = 2048;
		int off = 64;
		
		boolean save = false;
		File outs = null;
		
		ArrayDeque<String> q = new ArrayDeque<String>();
		q.addAll(Arrays.asList(args));

		if (args.length < 2)
		{
			printHelp();
			return;
		}

		try
		{
			while (!q.isEmpty())
			{
				String item = q.poll();
				if (item.startsWith("-"))
				{
					switch (item)
					{
						case "-F":
							program = q.poll();
							break;
						case "-f":
							try
							{
								program = new String(Files.readAllBytes((new File(q.poll())).toPath()));
							}
							catch (IOException e)
							{
								System.out.printf("Could not read code file%n");
								return;
							}
							break;
						case "-i":
							inf = new File(q.poll());
							break;
						case "-o":
							outf = new File(q.poll());
							break;
						case "-s":
							str = true;
							break;
						case "-r":
							rtrn = true;
							if (q.peek() != null && !q.peek().startsWith("-"))
							{
								Config.enter = (char) Integer.parseInt(q.poll(), 16);
							}
							break;
						case "-m":
							size = Integer.parseInt(q.poll());
							break;
						case "-mo":
							off = Integer.parseInt(q.poll());
							break;
						case "-d":
							Config.debug = true;
							break;
						case "-C":
							outs = new File(q.poll());
							save = true;
							break;
						case "-c":
							save = true;
							break;
						default:
							try
							{
								outt = EnumOutType.valueOf(item.substring(1));
							}
							catch (IllegalArgumentException e)
							{
								printHelp();
								return;
							}
							break;
					}
				}
				else
				{
					printHelp();
					return;
				}
			}
		}
		catch (Exception e)
		{
			printHelp();
			return;
		}

		Output out;
		if (outf == null)
		{
			out = new OutputFromConsole(outt);
		}
		else
		{
			try
			{
				outf.createNewFile();
				out = new OutputFromFile(outf, outt);
			}
			catch (IOException e)
			{
				System.out.printf("Invalid output file: %s%n", outf.getName());
				return;
			}
		}

		Input in;
		if (inf == null)
		{
			if (str)
			{
				in = new InputFromStringConsole(rtrn);
			}
			else
			{
				in = new InputFromConsole(rtrn);
			}
		}
		else
		{
			try
			{
				in = new InputFromFile(inf);
			}
			catch (FileNotFoundException e)
			{
				System.out.printf("Invalid input file: %s%n", inf.getName());
				return;
			}
		}

		if (program == null)
		{
			printHelp();
			return;
		}

		Program app;

		try
		{
			app = new Program(program, in, out, size, off);
		}
		catch (BrainfuckFormatException e)
		{
			System.out.printf("Invalid brainfuck code: %s%n", e.getMessage());
			return;
		}

		app.run();
		
		if (save)
		{
			app.saveCode(outs);
		}
	}

	public static void printHelp()
	{
		System.out.println("Usage: ... [-options]");
		System.out.println("where options include:");
		System.out.println("\t-F <brainfuck code>");
		System.out.println("\t\trun code from the command line");
		System.out.println("\t-f <brainfuck code file>");
		System.out.println("\t\trun code from a file");
		System.out.println("\t-i <input file>");
		System.out.println("\t\tspecify a file to use as the input");
		System.out.println("\t-o <input file>");
		System.out.println("\t\tspecify a file to use as the output");
		System.out.println("\t-s");
		System.out.println("\t\ttake input from console as raw strings");
		System.out.println("\t-r [return char number in hex]");
		System.out.println("\t\tadd return character to the end of every colsole input (default: 0D)");
		System.out.println("\t-d");
		System.out.println("\t\tenable speed debug info");
		System.out.println("\t-m <memory size>");
		System.out.println("\t\tset the size of the memory (default: 2048)");
		System.out.println("\t-mo <offset>");
		System.out.println("\t\tset the number of bytes left of the starting memory (default: 64)");
		System.out.println("\t-c");
		System.out.println("\t\tshow raw code in output");
		System.out.println("\t-C <save file>");
		System.out.println("\t\tsave raw code to file");
		System.out.println("\t-RAW");
		System.out.println("\t\toutput raw data (default)");
		System.out.println("\t-HEX");
		System.out.println("\t\toutput data as hexadecimal");
		System.out.println("\t-DEC");
		System.out.println("\t\toutput data as space delimited decimal");
		System.out.println("\t-CAR");
		System.out.println("\t\toutput data as sterilized, line delimited characters");
	}
}
