package file;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.text.TextUtils;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import utils.CommonUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： apk操作
 **/
public class ApkUtils {

    private static String verName;
    private static int verCode = -1;

    public static void installApk(Context c, String apkDir) { //安装apk
        //安装应用
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkDir)),
                "application/vnd.android.package-archive");
        if (c != null) {
            c.startActivity(intent);
        }
    }

    /**
     * 获取应用的签名
     *
     * @return
     */
    public static String getCertificateSHA1Fingerprint(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();

        //获取当前要获取 SHA1 值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数 Context 应该是对应包的上下文。
        String packageName = context.getPackageName();

        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;

        PackageInfo packageInfo = null;

        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();

        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);

        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;

        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //X509 证书，X.509 是一种非常通用的证书格式
        X509Certificate c = null;

        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hexString = null;

        try {
            //加密算法的类，这里的参数可以使 MD4,MD5 等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");

            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());

            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16 进制转换
    private static String byte2HexFormatted(byte[] arr) {

        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * 检测签名是否正确
     *
     * @return true 签名正常 false 签名不正常
     */
    public static boolean check(Context context, String realCer) {
        String cer = getCertificateSHA1Fingerprint(context);
        if (!TextUtils.isEmpty(cer)) {
            cer = cer.trim();
            realCer = realCer.trim();
            if (TextUtils.equals(cer, realCer)) {
                return true;
            }
        }
        return false;
    }


    public static String getVersionName() { //获取版本名
        if (!TextUtils.isEmpty(verName)) { //缓存
            return verName;
        }
        String pkName = CommonUtils.getApp().getPackageName();
        try {
            verName = CommonUtils.getApp().getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static int getVersionCode() { //获取版本号
        if (verCode != -1) {
            return verCode;
        }
        String pkName = CommonUtils.getApp().getPackageName();
        try {
            verCode = CommonUtils.getApp().getPackageManager().getPackageInfo(pkName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

}
