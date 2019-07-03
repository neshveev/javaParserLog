import java.io.*;
import java.util.regex.Pattern;

class ReadFileAndWriteResultToFile {
    private static Pattern fragmentPattern = Pattern.compile("[0-9.]{10} [0-9:]{8}\\.\\d{3}");
    // дата время как начало строки лога
    private static String str;

    static void readFileAndWriteResultToFile(String nameFile, FileWriter filew,
                                             String separator) throws IOException {
        StringBuilder fragment = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(
                        nameFile)));
        while ((str = bufferedReader.readLine()) != null || fragment.length() != 0) {

            if (str != null && (fragmentPattern.matcher(str)).find()) {
                if (fragment.length() != 0) {
                    ReplaceSeparatorInFragment.replaceSeparatorInFragment(fragment, separator, filew);
                }
            }
            else if (str == null) {
                ReplaceSeparatorInFragment.replaceSeparatorInFragment(fragment, separator, filew);
                break;
            }
            fragment.append(str).append(" ");
        }
    }
}
