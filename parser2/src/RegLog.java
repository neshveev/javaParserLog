import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegLog {
    private String[] regs = new String[4];
    private String[] typeLog = {"DEBUG", "ERROR", "INFO", "WARN"};

    RegLog() {
        regs[0] = "(20\\d{2}.\\d{2}.\\d{2})\\s*(\\d{2}.\\d{2}.\\d{2}.\\d{3})\\s*(DEBUG)\\s*([0-9]+)\\s*(.+?)\\s*\\[(.+?)\\]\\s*(.+?) \\s*:\\s*(.+?)[\\[|:]\\s*([a-z]+=.+?);\\s*([a-z]+=.+?);\\s*([a-z]+=.+?);\\s*([a-z]+=.+?);\\s*([a-z]+=.+?)}";
        regs[1] = "(20\\d{2}.\\d{2}.\\d{2})\\s*(\\d{2}.\\d{2}.\\d{2}.\\d{3})\\s*(ERROR)\\s*([0-9]+)\\s*(.+?)\\s*\\[(.+?)]\\s*(.+?) \\s*:\\s*(.+)\\s*\\s\\s(.+)";
        regs[2] = "(20\\d{2}.\\d{2}.\\d{2})\\s*(\\d{2}.\\d{2}.\\d{2}.\\d{3})\\s*(INFO)\\s*([0-9]+)\\s*(.+?)\\s*\\[(.+?)\\]\\s*(.+?) \\s*:\\s*(.+)";
        regs[3] = "(20\\d{2}.\\d{2}.\\d{2})\\s*(\\d{2}.\\d{2}.\\d{2}.\\d{3})\\s*(WARN)\\s*([0-9]+)\\s*(.+?)\\s*\\[(.+?)\\]\\s*(.+?) \\s*:\\s*(.+?):\\s*(.+)";
    }

    private String getReg(String str) {
        for (int i = 0; i < typeLog.length; ++i) {
            if (str.contains(typeLog[i])) {
                return regs[i];
            }
        }
        return null;
    }

    Matcher getMatcher(String str) {
        String reg;
        if ((reg = getReg(str)) == null) {
            return null;
        }
        Pattern debug = Pattern.compile(reg);
        return debug.matcher(str);
    }
}
