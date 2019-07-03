import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

class WriteFragmentToFile {

    private static int count = 0;
    private static int countRes = 0;
    private static String str;
    private static StringBuilder fragment = new StringBuilder();
    private static Pattern pattern = Pattern.compile("[0-9.]{10} [0-9:]{8}\\.\\d{3}");
    // дата-время как начало фрагмента

    static void writeFragmentToFile(String nameFileWrite, BufferedReader buffer,
                                    int sizeFile) throws IOException {

        FileWriter fileToWrite = new FileWriter(nameFileWrite);
        for (int size = 0; (str = buffer.readLine()) != null || fragment.length() != 0; ) {
            if (str == null) {
                fileToWrite.write(fragment.toString());
                ++countRes;
                break;
            }
            size += str.length() + 2; //два "\r\n"
            if ((pattern.matcher(str)).find()) {
                ++count;
                if (fragment.length() != 0) {
                    fileToWrite.write(fragment.toString());
                    fragment.setLength(0);
                    ++countRes;
                }
                if (size > sizeFile) {
                    fragment.append(str).append("\r\n");
                    break;
                }
            }
            fragment.append(str).append("\r\n");
        }
        fileToWrite.close();
    }

    static int getCount() {
        return count;
    }

    static int getCountRes() {
        return countRes;
    }
}