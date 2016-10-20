package com.ec.module.countrycodemodule.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * @author EC
 *         Date on 16-7-20.
 */
public class IOUtils {
    private static final int BUFFER_SIZE = 4096;

    public IOUtils() {
    }

    public static String readStreamAsString(InputStream in, String charset) throws IOException {
        if (in == null) {
            return "";
        } else {
            BufferedReader reader = null;
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];

            try {
                reader = new BufferedReader(new InputStreamReader(in, charset));

                int n;
                while ((n = reader.read(buffer)) > 0) {
                    writer.write(buffer, 0, n);
                }

                String result = writer.toString();
                return result;
            } finally {
                in.close();
                if (reader != null) {
                    reader.close();
                }

                if (writer != null) {
                    writer.close();
                }

            }
        }
    }

    public static byte[] readStreamAsBytesArray(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        } else {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];

            int len;
            while ((len = in.read(buffer)) > -1) {
                output.write(buffer, 0, len);
            }

            output.flush();
            return output.toByteArray();
        }
    }

    public static byte[] readStreamAsBytesArray(InputStream in, int readLength) throws IOException {
        if (in == null) {
            return new byte[0];
        } else {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];

            int len;
            for (long readed = 0L; readed < (long) readLength && (len = in.read(buffer, 0, Math.min(2048, (int) ((long) readLength - readed)))) > -1; readed += (long) len) {
                output.write(buffer, 0, len);
            }

            output.flush();
            return output.toByteArray();
        }
    }

    public static void safeClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var2) {
                ;
            }
        }

    }

    public static void readStreamToFile(InputStream in, File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[4096];

        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        out.flush();
        out.close();
    }

    public static void safeClose(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException var2) {
            }
        }

    }
}