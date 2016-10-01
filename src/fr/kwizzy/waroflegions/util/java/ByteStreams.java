package fr.kwizzy.waroflegions.util.java;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteStreams {

    public static final int BUF_SIZE = 8192;

    private ByteStreams() {}

    public static void copy(InputStream from , OutputStream to) throws IOException {
        int read;
        byte[] buff = new byte[BUF_SIZE];

        while ((read = from.read(buff, 0, buff.length)) != -1)to.write(buff, 0, read);
        to.flush();
    }

    public static byte[] toByteArray(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream(Math.max(32, in.available()));
        copy(in , out);
        return out.toByteArray();
    }
}
