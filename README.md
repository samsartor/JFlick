JFlick
======

A high-speed brainfuck interpreter written in Java

Usage
-----
``java -jar jflick.jar [-options...]``

Where options include:

###-F &lt;brainfuck code&gt;
Run code from the command line. Either this option or ``-f`` is required.

	java -jar jflick.jar -F ">,[>,]<[.<]" -s
###-f &lt;brainfuck code file&gt;
Run code from a file. Either this option or ``-F`` is required.

	java -jar jflick.jar -f "/examplefolder/program.bf"
###-i &lt;input file&gt;
Specify a file to use as the input. The file will be read as raw binary, and passed byte-by-byte. If the file contains no more bytes, then any input commands will give ``0x00``.

	java -jar jflick.jar -F ">,[>,]<[.<]" -i "input.dat"
###-o &lt;input file&gt;
Specify a file to use as the output. Instead of showing the brainfuck program's output in console, it is redirected to the specified file and formatted based on the options given.

	java -jar jflick.jar -F ">,[>,]<[.<]" -s -o "out.txt"
###-s
Take input from the console as raw strings. By default, the user is prompted for individual bytes (assuming ``-i`` is not given) and can use a variety of formats. If this option is given, the user will give the input as raw text, and is only prompted if the buffer of entered text runs out. This will match the functionality of most existing interpretors, and is recommended for most programs.
###-r \[return char number in hex\]
Add return character to the end of every console input. By default, after a user input is given and submitted by pressing the 'enter' key, the return character is excluded. If this option is given, a character (the default is ``0x0D``) is appended to any user input. If you do not wish to use ``0x0D`` as this return character, then you must specify the intended character's hexadecimal value.
###-d
This option enables speed debugging (performance) info such as operations per second, abbreviation ratio, and run-time (excluding the time spent waiting for user input).
###-m &lt;memory size&gt;
This option sets the total size of the memory in bytes (the default is 2048 bytes). If an ``java.lang.ArrayIndexOutOfBoundsException`` is thrown, more space might be necessary.
###-mo &lt;offset&gt;
Set the number of bytes left of the starting memory (the default is 64). This is only necessary for programs that require the array to be extended to the left as well as to the right. If an ``java.lang.ArrayIndexOutOfBoundsException`` is thrown with a negative index, then this may need to be larger.
###-c
If this option is given, the application displays the program's raw brainfuck code at completion. In addition to the removal of all non-command characters, this code is generated using the abbreviated program commands, so it may differ slightly from the code provided.
###-C &lt;save file&gt;
If this option is given, the application writes the program's raw brainfuck code to the given file. In addition to the removal of all non-command characters, this code is generated using the abbreviated program commands, so it may differ slightly from the code provided.
###-RAW
This option specifies that all outputs should not be formatted, returning the raw data produced by the program. The application runs this way by default.

i.e. 

	this is
	example    text

###-HEX
This option specifies that all outputs should be formatted as non delineated hexadecimal.

i.e. 

	746869732069730d6578616d706c650974657874

###-DEC
This option specifies that all outputs should be formatted as space delineated decimal text.

i.e.

	116 104 105 115 32 105 115 13 101 120 97 109 112 108 101 9 116 101 120 116
	
###-CAR
This option specifies that all outputs should be formatted as sterilized, line delineated characters.

i.e.

	t
	h
	i
	s
	SPACE
	i
	s
	CARRIAGE RETURN (CR)
	e
	x
	a
	m
	p
	l
	e
	CHARACTER TABULATION
	t
	e
	x
	t

Input
-----
Assuming that ``-i`` or ``-s`` are not set, the user can give inputs in a variety of formats:

- By default any input is treated as the decimal value of the byte.
- If the input is prefixed with ``0x``, then the value is treated as hexadecimal.
- If the input is surrounded by single quotes, then the character within the quotes is used as the input.

Method
------
JFlick's performance is achieved using a variety of systems. The program is run by iterating over an array of command objects. Some of these 'abbreviated' commands can preform more complicated operations than are available in traditional brainfuck, resulting in much faster speeds.
###Preliminary Abbreviation
When JFlick is parsing a brainfuck program, it treats repetitive commands as a single operation. A sequence of ``+``s and ``-``s is converted to a single operation that adds or subtracts. The same system is used with the ``>`` and ``<`` operations.
###Pattern Abbreviation
Many code patterns appear frequently in brainfuck code. Before running, JFlick searches for these patterns and replaces them with faster Java implementations. The following code patterns are currently supported by abbreviation (note that JFlick can run any brainfuck code that complies with the original specification, but these particular patterns are optimized):

####Set To Zero (``[-]``)
This is pretty self-explanatory, it sets the current byte to zero.

####Set To Value (i.e. ``[-]+``)
If a ``[-]`` is followed by any number of ``+`` or ``-`` commands, then the two abbreviated commands are simplified to a single set command.

####Find Zero (i.e. ``[>]``)
If a loop is surrounding a move (any number of ``<`` or ``>`` operations) only, then it can be simplified to a while loop that stops on a 0 byte.

####Copy (i.e. ``[>+<-]`` or ``[->+<]``)
This code effectively sets some byte to a multiple of the current byte. As long as the loop starts or ends with a ``-``, and the first move and second move cancel out, then any add/subtract between the moves and any size of a move is supported.

####Long Copy (i.e ``[>>+>--<<<->++<]``)
Any loop containing any sequence of moves, adds, or subtracts which starts and ends on the same byte (all the moves cancel out) and subtracts 1 from that byte can be abbreviated. This is effectively a copy pattern acting on multiple bytes.

###Stored Jump Locations
Before running, JFlick provides each ``[``/``]`` with the location of it's matching operation, allowing for fast jumps, loops, and ifs.
