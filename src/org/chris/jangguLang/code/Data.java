package org.chris.jangguLang.code;

import java.util.logging.Logger;

public class Data {
    private static final Data INSTANCE = new Data();
    private final Logger logger = Logger.getLogger(Data.getInstance().getName());

    @SuppressWarnings("SameReturnValue")
    public String getName() {
        return "장구랭";
    }

    public static Data getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }
}
