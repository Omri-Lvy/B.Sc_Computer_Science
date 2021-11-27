
import java.awt.Color;

public class Test {

    public static void main(String[] args) {
        String sourceFile = args[0];
        Color[][] source = Instush.read(sourceFile);
        String targetFile = args[1];
        Color[][] target = Instush.read(targetFile);
        if (source.length != target.length || source[0].length != target[0].length) {
            target = Instush.scaled(target, source[0].length, source.length);
        }
        int steps = Integer.parseInt(args[2]);
        for (int i = steps; i >= 0; i--) {
            StdDraw.show(1000);
            Instush.show(blend(source, target, i / steps));
        }
        // Color c1 = new Color(100, 40, 100);
        // Color c2 = new Color(200, 20, 40);
        // blending(c1, c2, 0.25);

    }

    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        Color[][] blended = new Color[image1.length][image1[0].length];
        for (int i = 0; i < image1.length; i++) {
            for (int j = 0; j < image1[i].length; j++) {
                blended[i][j] = Instush.blend(image1[i][j], image2[i][j], alpha);
            }
        }
        return blended;
    }

    public static void read(String filename) {
        StdIn.setInput(filename);
        // Reads the PPM file header (ignoring some items)
        StdIn.readString();
        int numRows = StdIn.readInt();
        int numCols = StdIn.readInt();
        StdIn.readInt();
        Color[][] image = new Color[numCols][numRows];
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                int red = StdIn.readInt();
                int green = StdIn.readInt();
                int blue = StdIn.readInt();
                Color pixelColor = new Color(red, green, blue);
                image[i][j] = pixelColor;
            }
        }
        Instush.show(image);
    }

    public static void blending(Color c1, Color c2, double alpha) {
        System.out.println(Instush.blend(c1, c2, alpha));
    }

}
