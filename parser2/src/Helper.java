import java.util.regex.Matcher;

class Helper {
    static void specialDebug(StringBuilder res, String group, Matcher matcher, String separator, int i) {
        switch (i) {
            case (11):
                res.append(separator).append(separator);
                res.append('"').append(group).
                        append(matcher.group(i + 1)).
                        append(matcher.group(i + 2)).
                        append('"').append(separator);
                break;
            case (12):
                res.append(separator);
                res.append('"').append(group).
                        append(matcher.group(i + 1)).
                        append('"').append(separator);
                break;
            default:
                res.append('"').append(group).append('"').append(separator);
        }
    }

    static void myAssert(boolean b, String message) {
        if (!b) {
            System.out.println(message);
            System.exit(1);
        }
    }
}