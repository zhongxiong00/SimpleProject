package encrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/3
 * 描述： AES加解密,CBC模式
 **/

public class AES_CBC {
    private static AES_CBC mHttpAes; // 接口请求加密
    private static String KEY = ""; //配置key
    private final String IV = "csc-api-iv-param";
    private final String WAY = "AES/CBC/PKCS5Padding";
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher mCipherEncrypt, mCipherDecrypt;

    public static void init(String key) {
        KEY = key;
        getHttpAes();
    }

    private AES_CBC(String key) {
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = key.getBytes();
        byte[] initParam = IV.getBytes();
        ivParameterSpec = new IvParameterSpec(initParam);
        // 指定加密的算法、工作模式和填充方式
        secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        try {
            mCipherEncrypt = Cipher.getInstance(WAY);
            mCipherDecrypt = Cipher.getInstance(WAY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static AES_CBC getHttpAes() {
        if (mHttpAes == null) {
            synchronized (AES_CBC.class) {
                if (mHttpAes == null) {
                    mHttpAes = new AES_CBC(KEY);
                }
            }
        }
        return mHttpAes;
    }


    private String encryptAES(String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] byteContent = content.getBytes("utf-8");
            mCipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec,
                    ivParameterSpec);
            encryptedBytes = mCipherEncrypt.doFinal(byteContent);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        // 同样对加密后数据进行 base64 编码
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    private String decryptAES(String content) {
        // base64 解码
        byte[] encryptedBytes = Base64.decode(content, Base64.DEFAULT);
        byte[] result = new byte[0];
        try {
            mCipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec,
                    ivParameterSpec);
            result = mCipherDecrypt.doFinal(encryptedBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String re = new String(result, Charset.forName("utf-8"));
        return re;
    }

    public static String encryptHttp(String content) { //加密接口数据
        return getHttpAes().encryptAES(content);
    }

    public static String decryptHttp(String content) { //解密接口数据
        return getHttpAes().decryptAES(content);
    }

}
