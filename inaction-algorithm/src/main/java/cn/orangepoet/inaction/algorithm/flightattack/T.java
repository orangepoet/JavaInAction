package cn.orangepoet.inaction.algorithm.flightattack;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhi
 * @date 2021/03/23
 */
public class T {
    public static void main(String[] args) {
        List<Long> userIds = Arrays.asList(2200796016645L, 1666691124L, 2200744281414L, 42593203L, 2201296173218L,2206218786633L);

        List<String> result = new ArrayList<>();
        for (Long userId : userIds) {
            String eData = encrypt(String.valueOf(userId), "22cjoiaon2018(&*)08d");
            result.add(eData);
        }

        System.out.println("encrypt:" + result);
    }

    public static String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "SunJCE");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(getByte16(key), "AES");
            cipher.init(1, keyspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return getHexString(encrypted);
        } catch (Exception var9) {
            return null;
        }
    }

    private static byte[] getByte16(String tk) {
        byte[] ks = tk.getBytes();
        byte[] key = new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < key.length; ++i) {
            if (i < ks.length) {
                key[i] = ks[i];
            }
        }

        return key;
    }

    private static String getHexString(byte[] b) throws Exception {
        String result = "";

        for (int i = 0; i < b.length; ++i) {
            result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
        }

        return result;
    }
}
