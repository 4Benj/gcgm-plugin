package com.benj4.gcgm.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;

public class WebUtil {
    private static final String WINDOWS_FILE_SEPARATOR = "\\";
    private static final String UNIX_FILE_SEPARATOR = "/";
    private static final String FILE_EXTENSION = ".";

    public static String getFileExtensionImproved(String fileName) {

        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null!");
        }

        String extension = "";

        int indexOfLastExtension = fileName.lastIndexOf(FILE_EXTENSION);

        // check last file separator, windows and unix
        int lastSeparatorPosWindows = fileName.lastIndexOf(WINDOWS_FILE_SEPARATOR);
        int lastSeparatorPosUnix = fileName.lastIndexOf(UNIX_FILE_SEPARATOR);

        // takes the greater of the two values, which mean last file separator
        int indexOflastSeparator = Math.max(lastSeparatorPosWindows, lastSeparatorPosUnix);

        // make sure the file extension appear after the last file separator
        if (indexOfLastExtension > indexOflastSeparator) {
            extension = fileName.substring(indexOfLastExtension + 1);
        }

        return extension;
    }

    public static void responseStream(HttpExchange t, File file, String contentType) throws IOException {
        t.sendResponseHeaders(200, file.length());
        t.getResponseHeaders().put("Content-Type", Collections.singletonList(contentType));
        OutputStream outputStream=t.getResponseBody();
        Files.copy(file.toPath(), outputStream);
        outputStream.close();
    }
}
