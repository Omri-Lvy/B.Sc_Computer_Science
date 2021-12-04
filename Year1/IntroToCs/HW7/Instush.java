import java.awt.Color;
import java.util.concurrent.TimeUnit;
import javax.swing.plaf.ColorUIResource;

/**
 * A library of image processing functions.
 */
public class Instush {

	public static void main(String[] args) {
		// Color[][] image = read(args[0]);
		// Color[][] target = read(args[1]);
		// // Test read
		// print(image);
		// show(image);
		// // Test flip horizontal
		// print(flippedHorizontally(image));
		// show(flippedHorizontally(image));
		// // Test flip vertical
		// print(flippedVertically(image));
		// show(flippedVertically(image));
		// // Test flip gray scale
		// print(greyscaled(image));
		// show(greyscaled(image));
		// // Test flip scale
		// print(scaled(image, Integer.parseInt(args[1]), Integer.parseInt(args[2])));
		// show(scaled(image, Integer.parseInt(args[1]), Integer.parseInt(args[2])));
		// // Test flip morph
		// print(morph(image, target,50);
		// show(morph(image, target,50);
	}

	/**
	 * Returns an image created from a given PPM file. SIDE EFFECT: Sets standard
	 * input to the given file.
	 * 
	 * @return the image, as a 2D array of Color values
	 */
	public static Color[][] read(String filename) {
		StdIn.setInput(filename);
		// Reads the PPM file header (ignoring some items)
		StdIn.readString();
		int numRows = StdIn.readInt();
		int numCols = StdIn.readInt();
		StdIn.readInt();
		// Creates the image
		Color[][] image = new Color[numCols][numRows];
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				Color pixelColor = new Color(StdIn.readInt(), StdIn.readInt(), StdIn.readInt());
				image[i][j] = pixelColor;
			}
		}
		return image;
	}

	/**
	 * Prints the pixels of a given image. Each pixel is printed as a triplet of
	 * (r,g,b) values. For debugging purposes.
	 * 
	 * @param image - the image to be printed
	 */
	public static void print(Color[][] image) {
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				int r = image[i][j].getRed();
				int g = image[i][j].getGreen();
				int b = image[i][j].getBlue();
				System.out.print("(");
				System.out.printf("%4s", r + ",");
				System.out.printf("%4s", g + ",");
				System.out.printf("%3s", b);
				System.out.print(") ");
			}
			System.out.printf("%1s", "\n");
		}
		System.out.printf("%1s", "\n");
	}

	/**
	 * Returns an image which is the horizontally flipped version of the given
	 * image.
	 * 
	 * @param image - the image to flip
	 * @return the horizontally flipped image
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] flippedImage = new Color[image.length][image[0].length];
		for (int i = 0; i < flippedImage.length; i++) {
			for (int j = 0; j < flippedImage[0].length; j++) {
				flippedImage[i][j] = image[i][image[i].length - 1 - j];
			}
		}
		return flippedImage;
	}

	/**
	 * Returns an image which is the vertically flipped version of the given image.
	 * 
	 * @param image - the image to flip
	 * @return the vertically flipped image
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		Color[][] flippedImage = new Color[image.length][image[0].length];
		for (int i = 0; i < flippedImage.length; i++) {
			for (int j = 0; j < flippedImage[0].length; j++) {
				flippedImage[i][j] = image[image.length - 1 - i][j];
			}
		}
		return flippedImage;
	}

	/**
	 * Returns the average of the RGB values of all the pixels in a given image.
	 * 
	 * @param image - the image
	 * @return the average of all the RGB values of the image
	 */
	public static double average(Color[][] image) {
		// Replace the following statement with your code
		return 0.0;
	}

	/**
	 * Returns the luminance value of a given pixel. Luminance is a weighted average
	 * of the RGB values of the pixel, given by 0.299 * r + 0.587 * g + 0.114 * b.
	 * Used as a shade of grey, as part of the greyscaling process.
	 * 
	 * @param pixel - the pixel
	 * @return the greyscale value of the pixel, as a Color object (r = g = b = the
	 *         greyscale value)
	 */
	public static Color luminance(Color pixel) {
		int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color greyPixel = new Color(lum, lum, lum);
		return greyPixel;
	}

	/**
	 * Returns an image which is the greyscaled version of the given image.
	 * 
	 * @param image - the image
	 * @return rhe greyscaled version of the image
	 */
	public static Color[][] greyscaled(Color[][] image) {
		Color[][] greyScaled = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				greyScaled[i][j] = luminance(image[i][j]);
			}
		}
		return greyScaled;
	}

	/**
	 * Returns an umage which is the scaled version of the given image. The image is
	 * scaled (resized) to be of the given width and height.
	 * 
	 * @param image  - the image
	 * @param width  - the width of the scaled image
	 * @param height - the height of the scaled image
	 * @return - the scaled image
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaledImage = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				scaledImage[i][j] = image[(int) (i * ((double) image.length / height))][(int) (j
						* ((double) image[0].length / width))];
			}
		}
		return scaledImage;
	}

	/**
	 * Returns a blended color which is the linear combination of two colors. Each
	 * r, g, b, value v is calculated using v = (1 - alpha) * v1 + alpha * v2.
	 * 
	 * @param pixel1 - the first color
	 * @param pixel2 - the second color
	 * @param alpha  - the linear combination parameter
	 * @return the blended color
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red = (int) ((c1.getRed() * alpha) + (c2.getRed() * (1 - alpha)));
		int green = (int) ((c1.getGreen() * alpha) + (c2.getGreen() * (1 - alpha)));
		int blue = (int) ((c1.getBlue() * alpha) + (c2.getBlue() * (1 - alpha)));
		Color newColor = new Color(red, green, blue);
		return newColor;
	}

	/**
	 * Returns an image which is the blending of the two given images. The blending
	 * is the linear combination of (1 - alpha) parts the first image and (alpha)
	 * parts the second image. The two images must have the same dimensions.
	 * 
	 * @param image1 - the first image
	 * @param image2 - the second image
	 * @param alpha  - the linear combination parameter
	 * @return - the blended image
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blended = new Color[image1.length][image1[0].length];
		for (int i = 0; i < image1.length; i++) {
			for (int j = 0; j < image1[i].length; j++) {
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * The target image is an image which is scaled to be a version of the target
	 * image, scaled to have the width and height of the source image.
	 * 
	 * @param source - source image
	 * @param target - target image
	 * @param n      - number of morphing steps
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		for (double i = n; i >= 0; i--) {
			Color[][] blended = blend(source, target, i / n);
			show(blended);
		}
	}

	/**
	 * Renders (displays) an image on the screen, using StdDraw.
	 * 
	 * @param image - the image to show
	 */
	public static void show(Color[][] image) {
		StdDraw.setCanvasSize(image[0].length, image.length);
		int width = image[0].length;
		int height = image.length;
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		StdDraw.show(25);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the color of the pixel
				StdDraw.setPenColor(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue());
				// Draws the pixel as a tiny filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}
