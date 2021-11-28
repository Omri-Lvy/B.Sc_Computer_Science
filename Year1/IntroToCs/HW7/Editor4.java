import java.awt.Color;

/**
 * The program recieves two command-line arguments: the name of a PPM file that
 * represents the source image (a string), and the number of morphing steps (an
 * int). For example: java Editor4 ironman.ppm 300
 */
public class Editor4 {

    public static void main(String[] args) {
        String sourceFile = args[0];
        Color[][] source = Instush.read(sourceFile);
        Color[][] target = Instush.greyscaled(source);
        if (source.length != target.length || source[0].length != target[0].length) {
            target = Instush.scaled(target, source[0].length, source.length);
        }
        Instush.morph(source, target, Integer.parseInt(args[1]));
    }
}
