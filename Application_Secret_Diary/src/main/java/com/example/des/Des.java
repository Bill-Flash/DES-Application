package com.example.des;

import com.example.des.model.EnumMode;
import com.example.des.util.PermutationTable;
import com.example.des.util.FileUtil;
import com.example.des.util.TransformUtil;

import java.io.*;
import java.util.Base64;

public class Des {
    private static byte[] key;

    private Des() {}

    private static Des instance;

    // to protect the instance, Singleton pattern
    public static synchronized Des getInstance() {
        if (instance == null) {
            instance = new Des();
        }
        return instance;
    }

    public void setKey(String strKey){key = strKey.getBytes();
    }

    public static final int[] KeySchedule = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    // the count of circular to generate keys
    private static final int CIRCULAR_COUNT = 16;
    // the index of decipher
    private static final int EN_COUNT = 16;
    // the index of decipher
    private static final int DE_COUNT = 15;
    static int[] keydata = new int[64];// key of bits
    static int[] data_bits = new int[64]; // data of bits
    static int[][] keyArray;
    static int[] K0 = new int[56];// initial key

    // left and right sub keys for temporary storage
    static int[] leftKey_0 = new int[28];
    static int[] rightKey_0 = new int[28];
    static int[] lefttKey_1 = new int[28];
    static int[] rightKey_1 = new int[28];

    // the variables that are used in the wheel function
    static int[] L0 = new int[32];
    static int[] R0 = new int[32];
    static int[] L1 = new int[32];
    static int[] R1 = new int[32];
    static int[] RE = new int[48];
    static int[][] S = new int[8][6];
    static int[] sBoxDecimalData = new int[8];
    static int[] sValue = new int[32];
    static int[] RP = new int[32];

    // Encrypt algorithm according to the byte data
    public static byte[] Encrypt(byte[] data) {
        // format data
        byte[] format_key = TransformUtil.paddingData(key);
        byte[] format_data = TransformUtil.paddingData(data);
        // generalize key array

        return getByteResult(EnumMode.ENCRYPTION, format_key, format_data);
    }

    // Decrypt algorithm according to the byte data, and return the byte data
    public static byte[] Decrypt(byte[] data) {
        // format data
        byte[] format_key = TransformUtil.paddingData(key);
        byte[] format_data = TransformUtil.paddingData(data);
        byte[] result_data = getByteResult(EnumMode.DECRYPTION, format_key, format_data);
        // get rid of the padding bits
        int delete_len = result_data[format_data.length - 8 - 1];

        delete_len = ((delete_len >= 1) && (delete_len <= 8)) ? delete_len : 0;
        byte[] data_decrypted = new byte[format_data.length - delete_len - 8];
        System.arraycopy(result_data, 0, data_decrypted, 0, format_data.length - delete_len - 8);
        return data_decrypted;
    }

    // gives the result of byte
    public static byte[] getByteResult(EnumMode mode, byte[] format_key, byte[] format_data) {
        int blocks = format_data.length / 8;
        byte[] key_temp = new byte[8];
        byte[] data_temp = new byte[8];
        byte[] result_data = new byte[format_data.length];
        // only the first 8 bytes of key is valid
        System.arraycopy(format_key, 0, key_temp, 0, 8);
        keyArray = generateKeyArray(key_temp);

        for (int i = 0; i < blocks; i++) {
            // 8 bytes per block
            System.arraycopy(format_data, i * 8, data_temp, 0, 8);// get 8 bytes
            System.arraycopy(generateResult(data_temp, mode), 0, result_data, i * 8, 8); // use DES
        }
        return result_data;
    }
    
    // get the result of byte of 64 bits
    public static byte[] generateResult(byte[] data, EnumMode mode) {
        data_bits = TransformUtil.Int2BinInt(data);// transform it into bits

        // after the wheel function that contains 16 Circular function, we get the result byte
        return WheelFunction(data_bits, mode, keyArray);
    }
    
    // generate the key array
    public static int[][] generateKeyArray(byte[] key) {
        keydata = TransformUtil.Int2BinInt(key);// get the bits of key

        int[][] keyList = new int[16][48];
        // Permutation Choice 1
        Permutation(keydata, PermutationTable.PC_1, K0);
        for (int i = 0; i < CIRCULAR_COUNT; i++) {
            // left circular shift on K0
            LeftCircularShift(K0, KeySchedule[i]);
            // generate the sub keys within the keyList
            Permutation(K0, PermutationTable.PC_2, keyList[i]);
        }
        return keyList;
    }

    // Circular shift according to the move step (offset)
    public static void LeftCircularShift(int[] keyData, int step) {
        int i;
        // initialize the left and right key
        for (i = 0; i < 28; i++) {
            leftKey_0[i] = keyData[i];
            rightKey_0[i] = keyData[i + 28];
        }
        if (step == 1) {
            for (i = 0; i < 27; i++) { // left shift 1 bit by circular
                lefttKey_1[i] = leftKey_0[i + 1];
                rightKey_1[i] = rightKey_0[i + 1];
            }
            // padding the remaining part
            lefttKey_1[27] = leftKey_0[0];
            rightKey_1[27] = rightKey_0[0];
        } else if (step == 2) {
            for (i = 0; i < 26; i++) { // left shift 1 bit by circular
                lefttKey_1[i] = leftKey_0[i + 2];
                rightKey_1[i] = rightKey_0[i + 2];
            }
            // padding the remaining part
            lefttKey_1[26] = leftKey_0[0];
            rightKey_1[26] = rightKey_0[0];
            lefttKey_1[27] = leftKey_0[1];
            rightKey_1[27] = rightKey_0[1];
        }
        for (i = 0; i < 28; i++) {
            keyData[i] = lefttKey_1[i];
            keyData[i + 28] = rightKey_1[i];
        }
    }

    // Wheel Function
    public static byte[] WheelFunction(int[] data, EnumMode mode, int[][] keyarray) {
        // have an Initial Permutation
        Permutation(data, PermutationTable.IP);
        if (mode.equals(EnumMode.ENCRYPTION)) {
            // 16 iterations of CipherFunction
            for (int i = 0; i < EN_COUNT; i++) {
                data = CipherFunction(data, i, 1, keyarray[i]);
            }
        } else if (mode.equals(EnumMode.DECRYPTION)) {
            // 16 iterations of CipherFunction
            for (int i = DE_COUNT; i >= 0; i--) {
                data = CipherFunction(data, i, 0, keyarray[i]);
            }
        }
        // have an Inverse Initial Permutation
        Permutation(data, PermutationTable.INVER_IP);

        // transform it into int
        return TransformUtil.BinInt2Int(data);
    }

    // permute data parameter using the provided table
    public static void Permutation(int[] data, int[] table){
        int[] temp = data;
        data = new int[table.length];
        for (int i = 0; i < table.length; i++) {
            data[i] = temp[table[i] - 1]; // get the permutation table to swap
        }
    }

    // permute data parameter using the provided table, and return a int array
    public static void Permutation(int[] data, int[] table, int[] store_data){
        for (int i = 0; i < table.length; i++) {
            store_data[i] = data[table[i] - 1]; // get the permutation table to swap
        }
    }

    // cipher function
    public static int[] CipherFunction(int[] data, int times, int flag,int[] key) {
        int i;
        int j;
        // initialize the Left and Right bit part
        for (i = 0; i < 32; i++) {
            L0[i] = data[i];
            R0[i] = data[i + 32];
        }
        // R0 is processed according to the E BIT-SELECTION TABLE: 32 bits --> 48 bits (RE)
        // RE is then encrypted by the corresponding sub key
        for (i = 0; i < 48; i++) {
            RE[i] = R0[PermutationTable.Ext_Per_E[i] - 1];
            RE[i] = RE[i] ^ key[i]; // Xor Function
        }
        // S-Box
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 6; j++) {
                // Devide into 8 boxes
                S[i][j] = RE[(i * 6) + j]; // 48 bits --> 8 SBox * 6 bits
            }
            // The first and last bits of B represent in base 2 a number in the range 0 to 3.
            // The middle 4 bits of B represent in base 2 a number in the range 0 to 15.
            // the First value is the row, and the second is the column
            // now, we get a decimal int
            sBoxDecimalData[i] = PermutationTable.S_Boxes[i][(S[i][0] << 1) + S[i][5]][(S[i][1] << 3) + (S[i][2] << 2) + (S[i][3] << 1) + S[i][4]];
            // convert the Decimal int into Binary int (bits), low to high bit
            for (j = 0; j < 4; j++) {
                sValue[((i * 4) + 3) - j] = sBoxDecimalData[i] % 2;
                sBoxDecimalData[i] = sBoxDecimalData[i] / 2;
            }
        }
        //sValue --> 32 bits permutation and then Xor function
        for (i = 0; i < 32; i++) {
            RP[i] = sValue[PermutationTable.P[i] - 1]; // P Permutation
            L1[i] = R0[i]; // exchange the part
            R1[i] = L0[i] ^ RP[i]; // Xor function
            // if it is the last time, we use the double exchange to keep unchange
            if (((flag == 0) && (times == 0)) || ((flag == 1) && (times == 15))) {
                data[i] = R1[i];
                data[i + 32] = L1[i];
            } else {
                data[i] = L1[i];
                data[i + 32] = R1[i];
            }
        }
        return data;
    }

    // use the file
    public static void DesFile(String filepath, EnumMode mode){
        // get current file path
        String dirPath = System.getProperty("user.dir");

        File file = new File(filepath);
        // transform the file into byte
        byte[] fileByte = FileUtil.transformFileByte(filepath);
        if (mode.equals(EnumMode.ENCRYPTION)){
            File fileDir = new File(dirPath+"/Encryption");
            if (!fileDir.exists()) fileDir.mkdir();
            // Encryption
            FileUtil.generateFile(Encrypt(fileByte), fileDir.getAbsolutePath(), "cipherText_" + file.getName());
        } else if (mode.equals(EnumMode.DECRYPTION)) {
            File fileDir = new File(dirPath+"/Decryption");
            if (!fileDir.exists()) fileDir.mkdir();
            // Decryption
            FileUtil.generateFile(Decrypt(fileByte), fileDir.getAbsolutePath(), "plainText_" + file.getName());
        }
    }

    // use the text
    public static String DesText(String text, EnumMode mode) {
        String resultstr = null;
        if (mode.equals(EnumMode.ENCRYPTION)) {
            byte[] byteArray = text.getBytes();
            // Encryption
            resultstr = Base64.getEncoder().encodeToString(Encrypt(byteArray));
        } else if (mode.equals(EnumMode.DECRYPTION)) {
            try{
                // Decryption
                resultstr = new String(Decrypt(Base64.getDecoder().decode(text)));
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                resultstr = "false";
            }
        }
        return resultstr;
    }
}


