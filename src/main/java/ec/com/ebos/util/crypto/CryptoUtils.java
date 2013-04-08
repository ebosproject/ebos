package ec.com.ebos.util.crypto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.security.MessageDigest;

/**
 * Utilidades para encriptar passwords
 *
 * @author Eduardo Plua Alay
 * @see http://www.rgagnon.com/javadetails/java-0400.html
 * @see http://en.wikipedia.org/wiki/Cryptographic_hash_function
 */
public class CryptoUtils {

    public static void main(String arg[]) {
        try {
            // get input from the keyboard
            Reader r = new BufferedReader(new InputStreamReader(System.in));
            StreamTokenizer Input = new StreamTokenizer(r);
            //
            System.out.print("Input your secret password : ");
            Input.nextToken();
            String hash = CryptoUtils.computeHashSHA256(Input.sval);
            System.out.println("the computed hash (hex string) : " + hash + " - " + hash.length() + " chars");
            boolean ok = true;
            String inputHash = "";
            while (ok) {
                System.out.print("Now try to enter a password : ");
                Input.nextToken();
                inputHash = CryptoUtils.computeHashSHA256(Input.sval);
                if (hash.equals(inputHash)) {
                    System.out.println("You got it!");
                    ok = false;
                } else {
                    System.out.println("Wrong, try again...!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param string
     * @return MD5 hash - 32 chars
     */
    public static String computeHashMD5(String string) {
        try {
            return byteArrayToHexString(CryptoUtils.computeHash(string, "MD5"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param string
     * @return SHA-256 hash - 64 chars
     */
    public static String computeHashSHA256(String string) {
        try {
            return byteArrayToHexString(CryptoUtils.computeHash(string, "SHA-256"));
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] computeHash(String x, String algorithm) throws Exception {
        MessageDigest d = null;
        d = MessageDigest.getInstance(algorithm);
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
}
