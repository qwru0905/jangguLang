package org.chris.jangguLang;

import org.chris.jangguLang.code.Data;

import java.util.logging.Level;

public class FileManager {
    private static final FileManager INSTANCE = new FileManager();

    static {
        try {
            System.loadLibrary("fileManager");
        } catch (Exception e) {
            Data.getInstance().getLogger().log(Level.SEVERE, "Native library load failed: " + e.getMessage(), e);
        }
    }

    public native String selectFile();
    public native String saveFile(String content, String defaultFileName);

    public static FileManager getInstance() {
        return INSTANCE;
    }
}
