package testing;

import java.util.Random;
import java.util.Scanner;

public class Testing {

       public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter a PlainText: ");
        String plainText = in.nextLine();
        Random R = new Random();
        String key = "";
        for (int i = 0; i < 4; i++) {
            key += R.nextInt(2);
        }
        System.out.println("Random Key: " + key);
        String cipherText = encrypt(plainText, key);
        System.out.println("Binary CipherText: " + cipherText);
        String stringCipherText = binaryToString(cipherText);
        System.out.println("String CipherText: " + stringCipherText);
        String decryptedText = decrypt(cipherText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }
    public static String encrypt(String plainText, String key) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            String binaryCH = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            String LH = binaryCH.substring(0, 4);
            String RH = binaryCH.substring(4, 8);
            for (int j = 0; j < 4; j++) {
                String temp = LH;
                LH = RH;
                RH = XOR(RH, key);
                RH = XOR(RH, temp);
                key = invertKey(key);
            }
            cipherText.append(LH).append(RH);
        }
        return cipherText.toString();
    }
    public static String decrypt(String cipherText, String key) {
        key = invertKey(key);
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i += 8) {
            String binary = cipherText.substring(i, i + 8);
            String LD = binary.substring(0, 4);
            String RD = binary.substring(4, 8);
            for (int j = 0; j < 4; j++) {
                String temp = RD;
                RD = LD;
                LD = XOR(LD, key);
                LD = XOR(LD, temp);
                key = invertKey(key);
            }
            int value = Integer.parseInt(LD + RD, 2);
            char character = (char) value;
            plainText.append(character);
        }
        return plainText.toString();
    }
    public static String binaryToString(String binaryCipher) {
        StringBuilder stringCipher = new StringBuilder();
        for (int i = 0; i < binaryCipher.length(); i += 8) {
            String binary = binaryCipher.substring(i, i + 8);
            int value = Integer.parseInt(binary, 2);
            char character = (char) value;
            stringCipher.append(character);
        }
        return stringCipher.toString();
    }
    public static String XOR(String s1, String s2) {
        int length = Math.max(s1.length(), s2.length());
        s1 = String.format("%" + length + "s", s1).replace(' ', '0');
        s2 = String.format("%" + length + "s", s2).replace(' ', '0');
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            result.append(c1 == c2 ? '0' : '1');
        }
        return result.toString();
    }
    public static String invertKey(String key) {
        StringBuilder invertedKey = new StringBuilder();
        for (char c : key.toCharArray()) {
            invertedKey.append(c == '0' ? '1' : '0');
        }
        return invertedKey.toString();
    }
}