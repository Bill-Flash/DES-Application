package com.example.des.util;

public class TransformUtil {

    // padding the data if necessary, the data length is padding to 8n if data length
    // is between [8(n-1), 8n-1]
    public static byte[] paddingData(byte[] data) {
        int len = data.length;
        int padding = 8 - (len % 8);
        int result_length = len + padding;
        byte[] result_data = new byte[result_length];
        // copy the data and padding the data
        System.arraycopy(data, 0, result_data, 0, len);
        for (int i = len; i < result_length; i++) result_data[i] = (byte) padding;

        return result_data;
    }

    // transform bits into byte int
    public static byte[] BinInt2Int(int[] data) {
        int i;
        int j;
        byte[] value = new byte[8];
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                value[i] += (data[(i << 3) + j] << (7 - j)); // sum of bits
            }
        }
        for (i = 0; i < 8; i++) {
            value[i] %= 256;
        }
        return value;
    }

    // transform byte data into bit
    public static int[] Int2BinInt(byte[] intdata) {
        int[] int_byte = new int[8];
        int[] int_bits = new int[64];
        for (int i = 0; i < 8; i++) {
            int_byte[i] = intdata[i];
            // if the byte value is negative
            if (int_byte[i] < 0) {
                int_byte[i] += 256;
                int_byte[i] %= 256;
            }
        }
        // mod 2, from the low to high bit
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int_bits[((i * 8) + 7) - j] = int_byte[i] % 2;
                int_byte[i] = int_byte[i] / 2;
            }
        }
        return int_bits;
    }

}
