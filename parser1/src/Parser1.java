import java.io.*;
import java.util.regex.Pattern;

public class Parser1 {
    private static int countLog = 0;
    private static boolean isPrint = false;

    static public void main(String[] argv) throws IOException {
        if (argv.length != 3) {
            System.out.println("usage: [reg] [file] [nameResFile]");
            System.exit(1);
        }
        File pathTo = new File(argv[1]);
        Pattern pattern = Pattern.compile(argv[0]);
        FileWriter fileToWrite = new FileWriter(argv[2]);
        Pattern fragmentPattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}.\\d{2}.\\d{2}.\\d{2}\\.\\d{3}");
        // дата время как начало строки лога
        if (pathTo.isDirectory()) {
            File[] fileEntries = pathTo.listFiles();
            assert fileEntries != null;
            for (File fileEntry : fileEntries) {
                readFileAndWriteResultToFile(argv[1] + "/" + fileEntry.getName(), fileToWrite,
                        fragmentPattern, pattern);
            }
        } else if (pathTo.isFile()) {
            readFileAndWriteResultToFile(argv[1], fileToWrite, fragmentPattern, pattern);
        } else {
            System.out.println("You pointed wrong path to file(s)");
            System.exit(1);
        }
        System.out.println("Log: " + countLog);
        fileToWrite.close();
    }

    private static void readFileAndWriteResultToFile(String nameFile, FileWriter fileToWrite,
                                                     Pattern fragmentPattern, Pattern pattern) throws IOException {
        String str;
        StringBuilder fragment = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(nameFile)));
        while ((str = bufferedReader.readLine()) != null || isPrint) {
            if ((str != null && fragmentPattern.matcher(str).find()) || str == null) {
                if (isPrint) {
                        fileToWrite.write(fragment.toString());
                        ++countLog;
                        fragment.setLength(0);
                        isPrint = false;
                }
                if (str != null && pattern.matcher(str).find()) {
                    isPrint = true;
                }
            }
            if (isPrint && str != null) {
                fragment.append(str).append("\r\n");
            }
        }
    }
}
