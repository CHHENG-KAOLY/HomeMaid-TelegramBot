package processesDisp;


import java.io.*;

public class MegaKillaSupaOvner {

    public static void kill(String prName) throws IOException {

        Runtime.getRuntime().exec("taskkill /F /T /im " + prName);
}

}
