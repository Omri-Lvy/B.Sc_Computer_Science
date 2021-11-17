/*
 *  Prints a few values of the Sine function
 */
public class Sine {
	public static void main (String[] args) {
		// Set pi into a variable
		double pi = Math.PI;
		// print the results
		System.out.println("sine(0)     = " + Math.sin(0 * pi));
		System.out.println("sine(1/4 PI) = " + Math.sin(0.25 * pi));
		System.out.println("sine(2/4 PI) = " + Math.sin(0.5 * pi));
		System.out.println("sine(3/4 PI) = " + Math.sin(0.75 * pi));
		System.out.println("sine(4/4 PI) = " + Math.sin(1 * pi));
		System.out.println("sine(5/4 PI) = " + Math.sin(1.25 * pi));
		System.out.println("sine(6/4 PI) = " + Math.sin(1.5 * pi));
		System.out.println("sine(7/4 PI) = " + Math.sin(1.75 * pi));
		System.out.println("sine(8/4 PI) = " + Math.sin(2 * pi));
	}
}