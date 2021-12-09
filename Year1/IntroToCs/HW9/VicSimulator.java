/** The main driver of the Java implementation of the Vic computer.
 *  Gets from the command-line the name of a program file (mandatory),
 *  and the name of an input file (optional). Constructs a Vic computer,
 *  loads the program into its memory, and executes the program. */

public class VicSimulator {
	public static void main(String[] args) {
		// Constructs a Vic computer, loads the given program, and executes it.
		Computer vic = new Computer();  
		vic.loadProgram(args[0]);   // args[0] = name of a file containing a Vic program
		if (args.length > 1) {      // args[1] = name of a file containing the program's input
			vic.loadInput(args[1]);
		}
		vic.run();
		// Prints the state of the computer at the end of the program's execution.
		System.out.println(vic);	
	}
}