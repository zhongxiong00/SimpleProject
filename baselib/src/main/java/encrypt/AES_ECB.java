package encrypt;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： AES加解密,ECB模式
 **/
public class AES_ECB {
    private static final String algorithmStr = "AES/ECB/PKCS5Padding";

    private static KeyGenerator keyGen;
    private Cipher mCipherEncrypt, mCipherDecrypt;
    private static String AES_KEY;
    private static AES_ECB mHttpAes;
    private static byte[] keyStr;
    private SecretKeySpec keySpec;

    public static void init(String key) {
        AES_KEY = key;
        getAes();
    }

    public static AES_ECB getAes() {
        if (mHttpAes == null) {
            synchronized (AES_CBC.class) {
                if (mHttpAes == null) {
                    mHttpAes = new AES_ECB(AES_KEY);
                }
            }
        }
        return mHttpAes;
    }

    public AES_ECB(String key) {
        keyStr = getKey(key);
        keySpec = new SecretKeySpec(keyStr, "AES");
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化此密钥生成器，使其具有确定的密钥长度。
        keyGen.init(128); //128位的AES加密
        try {
            // 生成一个实现指定转换的 Cipher 对象。
            mCipherEncrypt = Cipher.getInstance(algorithmStr);
            mCipherDecrypt = Cipher.getInstance(algorithmStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private byte[] encrypt(String content) {
        try {
            byte[] byteContent = content.getBytes("utf-8");
            mCipherEncrypt.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = mCipherEncrypt.doFinal(byteContent);
            return result;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decrypt(byte[] content) {
        try {
            mCipherDecrypt.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = mCipherDecrypt.doFinal(content);
            return result;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password != null) {
            rByte = password.getBytes(Charset.defaultCharset());
        } else {
            rByte = new byte[24];
        }
        return rByte;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 加密
     */
    public static String encode(String content) {
        if (TextUtils.isEmpty(AES_KEY)) {
            throw new RuntimeException("请先初始化加密");
        }
        //加密之后的字节数组,转成16进制的字符串形式输出
        return parseByte2HexStr(getAes().encrypt(content));
    }

    /**
     * 解密
     */
    public static String decode(String content) {
        if (TextUtils.isEmpty(AES_KEY)) {
            throw new RuntimeException("请先初始化加密");
        }
        byte[] b = getAes().decrypt(parseHexStr2Byte(content));
        return new String(b, Charset.defaultCharset());
    }


}
