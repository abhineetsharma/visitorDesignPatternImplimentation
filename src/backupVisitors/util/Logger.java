package backupVisitors.util;


public class Logger {

    public enum DebugLevel {
        CONSTRUCTOR,
        FILE_PROCESSOR,
        NONE,
        INFO
    }

    private static DebugLevel debugLevel;


    public static void setDebugValue(int levelIn) {
        switch (levelIn) {
            case 1:
                debugLevel = DebugLevel.FILE_PROCESSOR;
                break;
            case 3:
                debugLevel = DebugLevel.INFO;
                break;
            case 2:
                debugLevel = DebugLevel.CONSTRUCTOR;
                break;
            default:
                debugLevel = DebugLevel.NONE;
                break;
        }
    }

//    public static void setDebugValue(DebugLevel levelIn) {
//        debugLevel = levelIn;
//    }

    public static void writeMessage(String message, DebugLevel levelIn) {

        if (levelIn == debugLevel )
            System.out.println(message);
    }

    @Override
    public String toString() {
        return "The debug level has been set to the following " + debugLevel;
    }

    public static void addTextSeparator(DebugLevel levelIn) {
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < 72; i++) {
            sbr.append("-");
        }
        writeMessage(sbr.toString(), levelIn);
    }
}


