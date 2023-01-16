import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

public class JackAnalyzer {
    public static void main (String[] args) throws IOException {
        FileFilter fileFilter = pathname -> pathname.getName().endsWith(".jack");
        File[] filesList = new File(args[0]).isDirectory() ? new File(args[0]).listFiles(fileFilter) : new File[]{ new File(args[0]) };
        for (File file : filesList) {
            FileWriter outputFile = new FileWriter(file.getName().replace(".jack", ".xml"), false);
            CompilationEngine compilationEngine = new CompilationEngine (file,outputFile);
            compilationEngine.compileClass();
            outputFile.close();
        }
    }
}