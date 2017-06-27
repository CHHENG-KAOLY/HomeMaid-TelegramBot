import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IDandTokenSaver {
    public static File file = new File("src/InputData.txt");
    public static int lines = 0;
    public static boolean isEmpty(){
        Scanner scanner = null;
        if (!file.canRead()){
            file.mkdirs();
        }
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        while (scanner.hasNext()){
            lines++;
            scanner.nextLine();
        }
        if (lines < 2) return true;
        else return false;
    }

}
