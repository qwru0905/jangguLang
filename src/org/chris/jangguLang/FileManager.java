package org.chris.jangguLang;

public class FileManager {
    private static final FileManager INSTANCE = new FileManager();

    static {
        try {
            System.loadLibrary("fileManager");
        } catch (Exception e) {
            System.err.println("Native library load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public native String selectFile();
    public native String saveFile(String content, String defaultFileName);

    public static FileManager getInstance() {
        return INSTANCE;
    }
}
