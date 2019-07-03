import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ReplaceSeparatorInFragment {
    private static int countLog = 0;
    private static int countResLine = 0;

    private static String generalPart = "([0-9\\.]{10})\\s*([0-9:]{8}\\.\\d{3})\\s*([A-Z]+)\\s*[:]?\\s*(.+?)[\\t|(]";
    private static Pattern gen = Pattern.compile(generalPart);
    private static Matcher matcher;

    static void replaceSeparatorInFragment(StringBuilder fragment, String separator, FileWriter filew) throws IOException {
        StringBuilder res = new StringBuilder();
        ++countLog;
        matcher = gen.matcher(fragment);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); ++i) {
                res.append('"').append(matcher.group(i)).append('"').append(separator);
            }
            String lastMatch = matcher.group(matcher.groupCount());
            int indexValues = fragment.indexOf(lastMatch) + lastMatch.length();
            if (indexValues < fragment.length()) {
                res.append('"').append(fragment.substring(indexValues)).append('"').append(separator);
            }
            ++countResLine;
            filew.write(res.append("\r\n").toString());
        }
        fragment.setLength(0);
    }

    public static int getCountLog() {
        return countLog;
    }

    public static int getCountResLine() {
        return countResLine;
    }
}
