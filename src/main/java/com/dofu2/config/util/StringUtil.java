package com.dofu2.config.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtil {

    public static String getExceptionAsString(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
