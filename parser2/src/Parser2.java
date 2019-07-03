import java.io.*;

public class Parser2 {
    static void myAssert(boolean b, String message) {
        if (!b) {
            System.out.println(message);
            System.exit(1);
        }
    }

    static public void main(String[] argv) throws IOException {
        myAssert(argv.length == 3, "usage: [separator] [file(s) path] [nameResFile]");
        File pathTo = new File(argv[1]);
        FileWriter fileToWrite = new FileWriter(argv[2] + ".csv");
        if (pathTo.isDirectory()) {
            File[] fileEntries = pathTo.listFiles();
            assert fileEntries != null;
            for (File fileEntry : fileEntries) {
                ReadFileAndWriteResultToFile.readFileAndWriteResultToFile(
                        argv[1] + "/" + fileEntry.getName(), fileToWrite, argv[0]);
            }
        } else if (pathTo.isFile()) {
            ReadFileAndWriteResultToFile.readFileAndWriteResultToFile(argv[1], fileToWrite, argv[0]);
        } else {
            myAssert(false, "You pointed wrong path to file(s)");
        }
        System.out.println("Log: " + ReplaceSeparatorInFragment.getCountLog() + "\nResLine " +
                ReplaceSeparatorInFragment.getCountResLine() + "\nYou lose " +
                (ReplaceSeparatorInFragment.getCountLog() - ReplaceSeparatorInFragment.getCountResLine()));
        fileToWrite.close();
    }
}

