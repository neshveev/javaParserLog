import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser2 {

    private static int countLog = 0;
    private static int countResLine = 0;
    private static RegLog regLog = new RegLog();

    static public void main(String[] argv) throws IOException {
        Helper.myAssert(argv.length == 3, "usage: [separator] [file(s) path] [nameResFile]");
        File pathTo = new File(argv[1]);
        FileWriter fileToWrite = new FileWriter(argv[2] + ".csv");
        Pattern fragmentPattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}.\\d{2}.\\d{2}.\\d{2}.\\d{3}");
        // дата время как начало строки лога
        if (pathTo.isDirectory()) {
            File[] fileEntries = pathTo.listFiles();
            assert fileEntries != null;
            for (File fileEntry : fileEntries) {
                readFileAndWriteResultToFile(argv[1] + "/" + fileEntry.getName(), fileToWrite,
                        fragmentPattern, argv[0]);
            }
        } else if (pathTo.isFile()) {
            readFileAndWriteResultToFile(argv[1], fileToWrite, fragmentPattern, argv[0]);
        } else {
            Helper.myAssert(false, "You pointed wrong path to file(s)");
        }
        System.out.println("Log: " + countLog + "\nResLine " + countResLine + "\nYou lose " + (countLog - countResLine));
        fileToWrite.close();
    }

    private static void readFileAndWriteResultToFile(String nameFile, FileWriter filew,
                                                     Pattern fragmentPattern, String separator) throws IOException {
        String str;
        StringBuilder fragment = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(
                        nameFile)));
        while ((str = bufferedReader.readLine()) != null || fragment.length() != 0) {

            if (str != null && (fragmentPattern.matcher(str)).find()) {
                if (fragment.length() != 0) {
                    replaceSeparatorInFragment(fragment, separator, filew);
                }
            }
            else if (str == null) {
                replaceSeparatorInFragment(fragment, separator, filew);
                break;
            }
            fragment.append(str).append(" ");
        }
    }

    static private void replaceSeparatorInFragment(StringBuilder fragment, String separator, FileWriter filew) throws IOException {
        Matcher matcher;

        ++countLog;
        if ((matcher = regLog.getMatcher(fragment.toString())) == null) {
            System.out.println(fragment);
            fragment.setLength(0);
            return;
        }
        if (matcher.find()) {
            StringBuilder res = new StringBuilder();
            for (int i = 1; i <= matcher.groupCount(); ++i) {
                String group = matcher.group(i);
                if (group.contains("headers")) {
                    Helper.specialDebug(res, group, matcher, separator, i);
                    break;
                }
                res.append('"').append(group).append('"').append(separator);
            }
            ++countResLine;
            filew.write(res.append("\r\n").toString());
            fragment.setLength(0);
            return;
        }
        System.out.println(fragment);
        fragment.setLength(0);
    }
}

