/** Draws the Koch curve and the the Koch snowflake fractal. */
public class Koch {

	public static void main(String[] args) {

		// Uncomment the first code block to test the curve function.
		// Uncomment the second code block to test the snowflake function.
		// Uncomment only one block in each test, and remember to compile
		// the class whenever you change the test.
		/*
		 * // Tests the curve function:
		 * // Gets n, x1, y1, x2, y2,
		 * // and draws a Koch curve of depth n from (x1,y1) to (x2,y2).
		 */
		// curve(Integer.parseInt(args[0]),
		// Double.parseDouble(args[1]), Double.parseDouble(args[2]),
		// Double.parseDouble(args[3]), Double.parseDouble(args[4]));

		/*
		 * // Tests the snowflake function:
		 * // Gets n, and draws a Koch snowflake of n edges in the standard canvass.
		 */ snowFlake(Integer.parseInt(args[0]));
	}

	/**
	 * Gets n, x1, y1, x2, y2,
	 * and draws a Koch curve of depth n from (x1,y1) to (x2,y2).
	 */
	public static void curve(int n, double x1, double y1, double x2, double y2) {
		if (n == 0) {
			return;
		}
		StdDraw.line(x1, y1, x2, y2);
		double p1x = x1 + (x2 - x1) / 3;
		double p1y = y1 + (y2 - y1) / 3;
		double p2x = x2 - (x2 - x1) / 3;
		double p2y = y2 - (y2 - y1) / 3;
		double p3x = ((Math.sqrt(3) / 6) * (y1 - y2)) + ((x1 + x2) / 2);
		double p3y = ((Math.sqrt(3) / 6) * (x2 - x1)) + ((y1 + y2) / 2);
		// * Drawing the new shape
		StdDraw.line(p1x, p1y, p3x, p3y);
		StdDraw.line(p3x, p3y, p2x, p2y);
		// * Removing the original segment
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.line(p1x, p1y, p2x, p2y);
		StdDraw.setPenColor(StdDraw.BLACK);

		curve(n - 1, x1, y1, p1x, p1y);
		curve(n - 1, p1x, p1y, p3x, p3y);
		curve(n - 1, p3x, p3y, p2x, p2y);
		curve(n - 1, p2x, p2y, x2, y2);
	}

	/** Gets n, and draws a Koch snowflake of n edges in the standard canvass. */
	public static void snowFlake(int n) {
		// A little tweak that makes the drawing look better
		StdDraw.setYscale(0, 1.1);
		StdDraw.setXscale(0, 1.1);
		// Draws a Koch snowflake of depth n
		curve(n, 0.0, 0.85, 1.0, 0.85);
		curve(n, 0.5, 0.0, 0.0, 0.85);
		curve(n, 1.0, 0.85, 0.5, 0.0);
	}
}
