import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class IDandTokenSaver {
    public static File file = new File("src\\InputData.txt");
    public static int lines = 0;
    public static boolean isEmpty(){
        Scanner scanner = null;
        if (!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

