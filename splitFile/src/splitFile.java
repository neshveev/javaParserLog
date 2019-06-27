import java.io.*;
import java.util.regex.Pattern;

public class splitFile {
    private static int count = 0;
    private static int countRes = 0;
    static private void myAssert(boolean b) {
        if (!b) {
            System.out.println("usage: [logFile] [nameResFile]");
            System.exit(1);
        }
    }

    public static void main(String[] argv) throws IOException {
        myAssert(argv.length == 2);
        InputStream file = new FileInputStream(argv[0]);
        int sizeFile = file.available() / 10; // из расчета что это приложение будет разбивать логи
        // по строкам, поэтому точность не важна
        BufferedReader buffer = new BufferedReader(new InputStreamReader((file)));
        File dir = new File("res");
        dir.mkdir();
        Pattern pattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}.\\d{2}.\\d{2}.\\d{2}\\.\\d{3}");
        // дата время как начало строки лога
        String str;
        StringBuilder fragment = new StringBuilder();

        for (int numFile = 1; numFile <= 10; ++numFile) {
            String nameFileWrite = "res/" + argv[1] + numFile;
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
        System.out.println("Log: " + count + "\nWriteLog: " + countRes + "\nYou lose : " + (count - countRes));
    }
}

