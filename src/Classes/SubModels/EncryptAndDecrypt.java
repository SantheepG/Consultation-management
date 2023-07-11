package Classes.SubModels;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncryptAndDecrypt {
    /**
     * This method is used to encrypt the image.
     * First it converts the image into byte array.
     * Then it performs XOR operation on each value of byte array.
     * From that every value of image will be changed.
     * Then it writes the new byte array value to the image.
     * Save the image with the same name and same directory.
     *
     * @param filePath the image to be encrypted
     * @param key      the key to encrypt the image
     * @throws FileNotFoundException if the file is not found
     * @throws IOException           if there is an error in reading the file
     */
    public static void encryptImage(String filePath, int key) throws FileNotFoundException, IOException {
        FileInputStream inputFile = new FileInputStream(filePath);
        byte[] data = new byte[inputFile.available()];
        inputFile.read(data);
        int i = 0;

        for (byte b : data) {
            data[i] = (byte) (b ^ key);
            i++;
        }

        FileOutputStream encryptedFile = new FileOutputStream(filePath);
        encryptedFile.write(data);

        encryptedFile.close();
        inputFile.close();
    }

    /**
     * This method is used to decrypt the image.
     * First it converts the image into byte array.
     * Then it performs XOR operation on each value of byte array.
     * From that every value of image will be changed.
     * Then it writes the new byte array value to the image.
     * Save the decrypted image with the same name and same directory.
     *
     * @param filePath the image to be decrypted
     * @param key      the key to decrypt the image
     * @throws FileNotFoundException if the file is not found
     * @throws IOException           if there is an error in reading the file
     */
    public static void decryptImage(String filePath, int key) throws FileNotFoundException, IOException {
        FileInputStream inputFile = new FileInputStream(filePath);
        byte[] data = new byte[inputFile.available()];
        inputFile.read(data);
        int i = 0;

        for (byte b : data) {
            data[i] = (byte) (b ^ key);
            i++;
        }
        FileOutputStream fos = new FileOutputStream(filePath);

        fos.write(data);
        fos.close();
        inputFile.close();
    }

    /**
     * This method is used to encrypt the text.
     * It changes the character of the text by adding the key to it.
     *
     * @param text the text to be encrypted
     * @param key  the key to encrypt the text
     * @return the encrypted text
     */
    public static String encryptText(String text, int key) {
        String encryptedText = "";
        for (int i = 0; i < text.length(); i++) {
            encryptedText += (char) (text.charAt(i) ^ key);
        }
        return encryptedText;
    }

    /**
     * This method is used to decrypt the text.
     * It changes the character of the text by subtracting the key from it.
     *
     * @param text the text to be decrypted
     * @param key  the key to decrypt the text
     * @return the decrypted text
     */
    public static String decryptText(String text, int key) {
        String decryptedText = "";
        for (int i = 0; i < text.length(); i++) {
            decryptedText += (char) (text.charAt(i) ^ key);
        }
        return decryptedText;
    }
}
