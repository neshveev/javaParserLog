import java.io.*;
import java.util.regex.Pattern;

public class splitFile {

    static private void myAssert(boolean b) {
        if (!b) {
            System.out.println("usage: [logFile] [nameResFile]");
            System.exit(1);
        }
    }

    public static void main(String[] argv) throws IOException {
        myAssert(argv.length == 2);

        InputStream file = new FileInputStream(argv[0]);
        BufferedReader buffer = new BufferedReader(new InputStreamReader((file)));
        int sizeFile = file.available() / 10;   // из расчета, что это приложение будет разбивать логи
                                                // по смысловым фрагментам, поэтому точность ориентировочная


        File dir = new File(argv[1] + "FOLDER");   // папка для файлов
        dir.mkdir();

        for (int numFile = 1; numFile <= 10; ++numFile) {
            WriteFragmentToFile.writeFragmentToFile(argv[1] + "FOLDER/" + argv[1] + numFile,
                                                                            buffer, sizeFile);
        }

        System.out.println("Log: " + WriteFragmentToFile.getCount() + "\nWriteLog: " + WriteFragmentToFile.getCountRes()
                + "\nYou lose : " + (WriteFragmentToFile.getCount() - WriteFragmentToFile.getCountRes()));
    }


}

