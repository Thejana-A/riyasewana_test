package page_object_model.utilities;

import java.io.*;

public class LoggerUtility {

    private PrintStream printStream;

    public void startLogging(String filePath) {
        try {
            printStream = new PrintStream(new File(filePath));
            System.setOut(printStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopLogging() {
        if (printStream != null) {
            printStream.close();
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out))); // Restore original System.out
        }
    }
}
