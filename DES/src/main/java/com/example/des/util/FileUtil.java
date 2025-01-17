package com.example.des.util;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

// the class used for handling the file
public class FileUtil {
    
    // file is transformed into byte according to the file path
    public static byte[] transformFileByte(String path) {
        FileChannel fc = null;
        byte[] resultByte = null;
        try {
            // read the file from the file stream
            fc = new RandomAccessFile(path, "r").getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).load();
            resultByte = new byte[(int) fc.size()];
            // in case there is no flush
            if (buffer.remaining() > 0) {
                buffer.get(resultByte, 0, buffer.remaining());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fc != null;
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultByte;
    }

    // file is generated according to the file byte stream
    public static void generateFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
