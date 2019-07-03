import java.io.*;
import java.util.regex.Pattern;

public class Parser1 {

    static public void main(String[] argv) throws IOException {
        myAssert(argv.length == 3, "usage: [reg] [file] [nameResFile]");

        File pathTo = new File(argv[1]);
        Pattern pattern = Pattern.compile(argv[0]);
        FileWriter fileToWrite = new FileWriter(argv[2]);

        if (pathTo.isDirectory()) {     // если папка с файлами
            File[] fileEntries = pathTo.listFiles();
            assert fileEntries != null;
            for (File fileEntry : fileEntries) {
                ReadFileAndWriteResultToFile.readFileAndWriteResultToFile(
                        argv[1] + "/" + fileEntry.getName(), fileToWrite, pattern);
            }
        } else if (pathTo.isFile()) {   // если файл
            ReadFileAndWriteResultToFile.readFileAndWriteResultToFile(
                        argv[1], fileToWrite, pattern);
        } else {                        // если не верный путь
            myAssert(false, "You pointed wrong path to file(s)");
        }
        System.out.println("Log: " + ReadFileAndWriteResultToFile.getCountLog());
        fileToWrite.close();
    }

    private static void myAssert(boolean b, String s) {
        if (!b) {
            System.out.println(s);
            System.exit(1);
        }
    }


}
