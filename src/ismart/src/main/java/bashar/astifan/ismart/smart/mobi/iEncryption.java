package bashar.astifan.ismart.smart.mobi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.os.Environment;
import android.util.Base64;
/**
 *
 *
 * @author Bashar Astifan <br>
 *         <a href=
 *         "astifan.online"
 *         >Read More</a> <br>
 * @version 2.0
 *
 */
public class iEncryption {
	static String secret = "1234567890abcdef";
	private final static String HEX = "0123456789ABCDEF";
	private static SecretKeySpec skeySpec;
	private static int BLOCK_SIZE = 16;
	private static char PADDING = '|';

	public static void setSecretKey(String key){secret=key;}
	public static void setSkeySpec(final SecretKeySpec skeySpec) {
		iEncryption.skeySpec = skeySpec;
	}
	
	static String nullPadString(final String original) {
		String output = original;
		int remain = 0;
		try {
			remain = output.getBytes("UTF8").length % BLOCK_SIZE;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (remain != 0) {
			remain = BLOCK_SIZE - remain;
			for (int i = 0; i < remain; i++) {
				output += PADDING;
			}
		}
		return output;
	}
	public static String encryptStrings(final String RAWDATA, boolean ENCODE)
			throws Exception { // This was a custom

		String encrypted = null;
		byte[] encryptedBytes = null;
		byte[] key;
		key = secret.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		// Init the cypher
		javax.crypto.Cipher cipher = null;
		try {
			String input = Integer.toString(RAWDATA.length()) + PADDING
					+ RAWDATA;
			cipher = javax.crypto.Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, skeySpec);
			encryptedBytes = cipher.doFinal(nullPadString(input).getBytes(
					"UTF8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ENCODE) {
			// NO_WRAP to eliminate new line character at the end of string
			encrypted = new String(
					Base64.encode(encryptedBytes, Base64.NO_WRAP));
		} else {
			encrypted = new String(encryptedBytes);
		}
		return encrypted;
	}
	// Decrypt with base 64 decode
		public static String decryptStrings(final String ENCRYPTEDDATA,
				final boolean DECODE) throws Exception {
			String raw = null;
			byte[] rawBytes = null;
			byte[] encryptedBytes;
			if (DECODE) {
				encryptedBytes = Base64.decode(ENCRYPTEDDATA.getBytes(),
						Base64.DEFAULT);
			} else {
				encryptedBytes = ENCRYPTEDDATA.getBytes();
			}
			byte[] key;
			key = secret.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			// Cipher init
			javax.crypto.Cipher cipher = null;
			try {
				cipher = javax.crypto.Cipher.getInstance("AES/ECB/NoPadding");
				cipher.init(javax.crypto.Cipher.DECRYPT_MODE, skeySpec);
				rawBytes = cipher.doFinal(encryptedBytes);
			} catch (Exception e) {
				e.printStackTrace();
			}
			raw = new String(rawBytes, "UTF8");
			int delimiter = raw.indexOf(PADDING);
			int length = Integer.valueOf(raw.substring(0, delimiter));
			raw = raw.substring(delimiter + 1, length + delimiter + 1);
			return raw;
		}
		public static String encrypt(final String cleartext) throws Exception {
			if (skeySpec == null) {
				init();
			}
			byte[] result = encrypt(cleartext.getBytes("UTF8"));
			return toHex(result);
		}
		public static String decrypt(final String encrypted) throws Exception {
			if (skeySpec == null) {
				init();
			}
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(enc);
			return new String(result, "UTF8");
		}
		public static void init() {
			try {
				skeySpec = new SecretKeySpec(getRawKey(secret.getBytes()), "AES");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private static byte[] getRawKey(final byte[] seed) throws Exception {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(seed);
			kgen.init(128, sr); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			return raw;
		}
		public static byte[] encrypt(final byte[] clear) throws Exception {
			javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
			cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(clear);
			return encrypted;
		}
		public static byte[] decrypt(final byte[] encrypted) throws Exception {
			javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
			cipher.init(javax.crypto.Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(encrypted);
			return decrypted;
		}
		public static String toHex(final String txt) {
			return toHex(txt.getBytes());
		}
		public static String fromHex(final String hex) {
			return new String(toByte(hex));
		}
		public static byte[] toByte(final String hexString) {
			int len = hexString.length() / 2;
			byte[] result = new byte[len];
			for (int i = 0; i < len; i++) {
				result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
						16).byteValue();
			}
			return result;
		}
		public static String toHex(final byte[] buf) {
			if (buf == null) {
				return "";
			}
			StringBuffer result = new StringBuffer(2 * buf.length);
			for (int i = 0; i < buf.length; i++) {
				appendHex(result, buf[i]);
			}
			return result.toString();
		}
		private static void appendHex(final StringBuffer sb, final byte b) {
			sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
		}
		public static final String md5(final String s) {
			try {
				// Create MD5 Hash
				MessageDigest digest = java.security.MessageDigest
						.getInstance("MD5");
				digest.update(s.getBytes());
				byte[] messageDigest = digest.digest();

				// Create Hex String
				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < messageDigest.length; i++) {
					String h = Integer.toHexString(0xFF & messageDigest[i]);
					while (h.length() < 2) {
						h = "0" + h;
					}
					hexString.append(h);
				}
				return hexString.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return "";
		}
		public static byte[] convertFileToByteArray(String path)
	    {
		File f = new File(Environment.getExternalStorageDirectory() + File.separator + path);
	    byte[] byteArray = null;
	    try
	    {
	    @SuppressWarnings("resource")
		InputStream inputStream = new FileInputStream(f);
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    byte[] b = new byte[1024*8];
	    int bytesRead =0;
	    
	    while ((bytesRead = inputStream.read(b)) != -1)
	    {
	    bos.write(b, 0, bytesRead);
	    }
	    
	    byteArray = bos.toByteArray();
	    }
	    catch (IOException e)
	    {
	    e.printStackTrace();
	    }
	    return byteArray;
	    }
		public static void encryptTextFile(String path) throws Exception{
		    	File file = new File(Environment.getExternalStorageDirectory() + File.separator + path);
		    	String filesHex = null;
		    	filesHex = encrypt(toHex(convertFileToByteArray(path)));
		    	FileWriter writer = new FileWriter(file);
				writer.append(filesHex);
				writer.flush();
				writer.close();
		}
	
}
