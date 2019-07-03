import java.io.*;
import java.util.regex.Pattern;

class ReadFileAndWriteResultToFile {

    private static int countLog = 0;
    private static boolean isPrint = false;
    private static String str;
    private static StringBuilder fragment = new StringBuilder();
    private static Pattern fragmentPattern = Pattern.compile("[0-9.]{10} [0-9:]{8}\\.\\d{3}");
    // дата время как начало строки лога

    public static void readFileAndWriteResultToFile(String nameFile, FileWriter fileToWrite
                                                    , Pattern pattern) throws IOException {
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

    public static int getCountLog() {
        return countLog;
    }
}
