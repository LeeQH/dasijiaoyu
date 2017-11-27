package com.lqh.dasi.commen;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.inject.New;

/**
 * 对文本进行AES加密
 * 
 * @author LiQuanhui
 * @date 2017年11月27日 下午4:35:08
 */
public class SecurityAES {
	/** AES加密时的秘钥 */
	private final static String AES_KEY = "mykeyismylove";
	/** AES专用秘钥 */
	private static SecretKeySpec key = null;

	/**
	 * key因为在没改变AES_KEY时是不变的，所以进行一次初始化操作
	 */
	static {
		// 创建AES的秘钥生成器
		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance("AES");
			// 利用AES_KEY的随机数，初始化刚刚创建的秘钥生成器
			kgen.init(128, new SecureRandom(AES_KEY.getBytes()));
			// 生成一个秘钥
			SecretKey secretKey = kgen.generateKey();
			// 获取二进制的秘钥
			byte[] enCodeFormat = secretKey.getEncoded();
			// 根据二进制秘钥，获取AES专用秘钥
			key = new SecretKeySpec(enCodeFormat, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密文本
	 * 
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午3:30:51
	 * @param content
	 *            明文
	 * @return
	 */
	public static String encrypt(String content) {
		try {
			// 创建AES密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 将文本进行utf-8的编码
			byte[] byteContent = content.getBytes("utf-8");
			// 初始化AES密码器为加密器
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 进行AES加密
			byte[] result = cipher.doFinal(byteContent);
			// 将byte数组转为十六进制的字符串
			// 因为AES加密算法中，密文是16位的倍数，直接new string(result)会乱码，不能进行传输
			// 但可以在后台中以byte[]的形式传递，因为我要进行前后端的传输所以转成16进制的字符串
			return parseByte2HexStr(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
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

	/**
	 * 解码文本
	 * 
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:23:38
	 * @param encryptContent
	 *            密文（16进制）
	 * @return
	 */
	public static String decrypt(String encryptContent) {
		try {
			// 将十六进制的加密文本转换为byte数组
			byte[] content = parseHexStr2Byte(encryptContent);
			// 创建AES密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 初始化AES密码器为解密器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			// 进行AES解密
			byte[] result = cipher.doFinal(content);
			// 将二进制转为字符串
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将byte数组转换成16进制的字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
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
	 * 将16进制的字符串转换为二进制 数组
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	// 测试
	public static void main(String[] args) throws Exception {

		long now = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			String content = "123";
			// 加密
			System.out.println("加密前：" + content);
			String encryptResult = encrypt(content);
			System.out.println("加密后：" + encryptResult);
			// 解密
			String decryptResult = decrypt(encryptResult);
			System.out.println("解密后：" + decryptResult);
		}
		System.out.println("SecurityAES.java\t" + (System.currentTimeMillis() - now));
	}

}
