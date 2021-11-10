package bashar.astifan.ismart.smart.mobi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

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
public class iCacheManager {
	public static void Setup(Context ctx) {
		pk = ctx.getApplicationContext().getPackageName();
		if (isLog)
			Log.i("PackageName", "" + pk);
		CTX = ctx;
		ThumbsFolder = "ismart/" + pk + "/thumbs/";
		CacheFolder = "ismart/" + pk + "/cache/";
		root = new File(Environment.getExternalStorageDirectory(), "ismart/"
				+ pk + "/cache");
		iiVroot = new File(Environment.getExternalStorageDirectory(), "ismart/"
				+ pk);
	}

	public static boolean isLog = false;
	private static String DBNAME = "ismartCache";
	public static boolean CACHE_ENABLE = false;
	public static boolean isDatabase = false;
	public static String DEFAUL_SESSION = "";
	public static String DEFAUL_TSESSION = "";
	public static int CacheINDEX = 0;
	public static Context CTX;
	private static String pk;
	public static String Environmentpath = Environment
			.getExternalStorageDirectory().toString();
	private static String CacheFolder;
	public static String ThumbsFolder;
	private static String header = "cache";
	static iXMLTools xml = new iXMLTools();
	private static String FinalSD_Path = "";
	static File root;
	static File iiVroot;
	private static iFileManager fileM=new iFileManager();
	public static void setDBNAME(String dbname) {
		DBNAME = dbname;

	}

	public static void Create_CACHE_Session(String sessionname)
			throws IOException {
		if (!isDatabase) {
			String path = Environment.getExternalStorageDirectory().toString()
					+ "/" + CacheFolder;
			String thumpath = Environment.getExternalStorageDirectory()
					.toString() + "/" + ThumbsFolder;
			File myDir = new File(path);
			if (myDir.isDirectory() && myDir.exists()) {
				if (isLog)
					Log.d("Dir exist : ", path);
			} else {
				File wallpaperDirectory = new File(path);
				wallpaperDirectory.mkdirs();
				if (isLog)
					Log.d("Create To : ", path);
			}
			File thumDir = new File(thumpath);
			if (thumDir.isDirectory() && thumDir.exists()) {
				if (isLog)
					Log.d("Dir exist : ", thumpath);
			} else {
				File wallpaperDirectory = new File(thumpath);
				wallpaperDirectory.mkdirs();
				if (isLog)
					Log.d("Create To : ", thumpath);
			}

			File gpxfile = new File(root, sessionname + ".xml");
			if (gpxfile.isFile()) {
				if (isLog)
					Log.e("This Session exsist : ", sessionname);
			} else {
				if (isLog)
					Log.e("Create Session : ", sessionname);
				FileWriter writer = new FileWriter(gpxfile);
				writer.append("<" + header + ">");
				writer.append("\n");
				writer.append("<item>");
				writer.append("\n");
				writer.append("<link>null</link>");
				writer.append("\n");
				writer.append("<sd>null</sd>");
				writer.append("\n");
				writer.append("</item>");
				writer.append("\n");
				writer.flush();
				writer.close();
			}
		} else {
			// Database Code
			iDatabaseManager.Setup(CTX, DBNAME, "guid", new String[] {
					"session_name", "url", "code" }, new String[] {
					iDatabaseManager.VARCHAR, iDatabaseManager.VARCHAR,
					iDatabaseManager.VARCHAR }, "1");
			iDatabaseManager
					.CreateTable(
							"cache",
							new String[] { "session_name", "code", "file",
									"content", "md5" },
							new String[] {
									iDatabaseManager.VARCHAR,
									iDatabaseManager.VARCHAR,
									iDatabaseManager.BLOB,
									iDatabaseManager.TEXT,
									iDatabaseManager.VARCHAR
											+ ",  FOREIGN KEY(code) REFERENCES guid (code) " });
		}

	}

	public static void SaveToCache(String Session, String url, Bitmap bmp) {
		if (!isDatabase) {
			String xmlfile = fileM.GetTextContentFromSDWithOutEnvironment(CacheFolder + Session + ".xml");
			if (isLog)
				Log.e("xml", xmlfile);
			ArrayList<String> urls = xml.GET_TAG(CTX, xmlfile + "</" + header
					+ ">", "item", "link");
			if (xml.IS_EXSIST(urls, url)) {
				if (isLog)
					Log.e("iIV Cache State : ", "Saved Before");
			} else {
				if (isLog)
					Log.e("iIV Cache State : ", "Saving..");
				SaveIamge(CTX, bmp, ThumbsFolder);
				SaveCache(Session + ".xml", FinalSD_Path, url, xmlfile);
			}
		} else {
			// Database Code
			Random generator = new Random();
			int code = 4901064;
			code = generator.nextInt(code);
			HashMap<String, String> ch = iDatabaseManager.GetRecord("guid",
					new String[] { "code" }, "code='" + code + "'");
			while (ch.get("code") != null) {
				code = generator.nextInt(code);
				ch = iDatabaseManager.GetRecord("guid",
						new String[] { "code" }, "code='" + code + "'");
			}
			iDatabaseManager.InsertRecord("guid", new String[] {
					"session_name", "url", "code" }, new String[] { Session,
					url, code + "" });
			iDatabaseManager.InsertRecord("cache", new String[] {
					"session_name", "code", "file" }, new String[] { Session,
					code + "" }, iImageTools.getBitmapAsByteArray(bmp));
		}

	}

	public static void SaveToCache(String Session, String url,
			String textContent) {
		if (!isDatabase) {
			String xmlfile = fileM.GetTextContentFromSDWithOutEnvironment(CacheFolder + Session + ".xml");
			if (isLog)
				Log.e("xml", xmlfile);
			ArrayList<String> urls = xml.GET_TAG(CTX, xmlfile + "</" + header
					+ ">", "item", "link");
			if (xml.IS_EXSIST(urls, url)) {
				if (isLog)
					Log.e("iIV Cache State : ", "Saved Before");
			} else {
				SaveText(textContent, ThumbsFolder);
				SaveCache(Session + ".xml", FinalSD_Path, url, xmlfile);
				if (isLog)
					Log.e("iIV Cache State : ", "Saving..");
			}
			urls.clear();
			xmlfile = "";
		} else {
			// Database Code
			Random generator = new Random();
			int code = 4901064;
			code = generator.nextInt(code);
			HashMap<String, String> ch = iDatabaseManager.GetRecord("guid",
					new String[] { "code" }, "code='" + code + "'");
			while (ch.get("code") != null) {
				code = generator.nextInt(code);
				ch = iDatabaseManager.GetRecord("guid",
						new String[] { "code" }, "code='" + code + "'");
			}
			String URLMD5 = "fileM.GET_TEXT_FILE(CTX, iStatics.GET_MD5 + url)";
			iDatabaseManager.InsertRecord("guid", new String[] {
					"session_name", "url", "code" }, new String[] { Session,
					url, code + "" });
			iDatabaseManager.InsertRecord("cache", new String[] {
					"session_name", "code", "content", "md5" }, new String[] {
					Session, code + "", textContent, URLMD5 });
		}
	}

	public static void ClEAR_CACHE(String Session) {
		if (!isDatabase) {
			String xmlfile = fileM.GetTextContentFromSDWithOutEnvironment(CacheFolder + Session + ".xml");
			ArrayList<String> sd = xml.GET_TAG(CTX, xmlfile + "</" + header
					+ ">", "item", "sd");
			for (int i = 0; i < sd.size(); i++) {
				File file = new File(sd.get(i));
				if (file.delete()) {
					if (isLog)
						Log.i("Delete File : " + sd.get(i), "Deleted");
				} else {
					if (isLog)
						Log.e("Delete File : " + sd.get(i), "Faild To Del");
				}

			}
			String fullP = Environmentpath + "/" + CacheFolder + Session
					+ ".xml";
			File file = new File(fullP);
			if (file.delete()) {
				if (isLog)
					Log.i("Delete Sesstion : " + fullP, "Deleted");
			} else {
				if (isLog)
					Log.e("Delete Sesstion : " + fullP, "Faild To Del");
			}
		} else {
			// Database Code
			iDatabaseManager.NonQuery("DELETE FROM guid WHERE session_name='"
					+ Session + "'");
			iDatabaseManager.NonQuery("DELETE FROM cache WHERE session_name='"
					+ Session + "'");
		}

	}

	/**
	 * <b>Clear All Cache Sessions</b>
	 */
	public static void ClEAR_CACHE() {
		if (!isDatabase) {
			String path = Environment.getExternalStorageDirectory().toString()
					+ "/" + CacheFolder;
			String thumpath = Environment.getExternalStorageDirectory()
					.toString() + "/" + ThumbsFolder;
			File cachedir = new File(path);
			if (cachedir.isDirectory()) {
				String[] children = cachedir.list();
				for (int i = 0; i < children.length; i++) {
					new File(cachedir, children[i]).delete();
				}
			}
			if (isLog)
				Log.i("Delete Sesstions ", "All Sessions Deleted");
			File imgdir = new File(thumpath);
			if (imgdir.isDirectory()) {
				String[] children = imgdir.list();
				for (int i = 0; i < children.length; i++) {
					new File(imgdir, children[i]).delete();
				}
			}
			if (isLog)
				Log.i("Delete images ", "All images Deleted");
		} else {
			// Database Code
			iDatabaseManager.NonQuery("DELETE FROM guid");
			iDatabaseManager.NonQuery("DELETE FROM cache");
		}
	}

	public static String CHECK_CACHE(String url, String Session)
			throws IOException {
		if (!isDatabase) {
			Create_CACHE_Session(Session);
			String xmlfile = fileM.GetTextContentFromSDWithOutEnvironment(CacheFolder + Session + ".xml");
			if (isLog)
				Log.e("xml", xmlfile);
			ArrayList<String> urls = xml.GET_TAG(CTX, xmlfile + "</" + header
					+ ">", "item", "link");
			ArrayList<String> sd = xml.GET_TAG(CTX, xmlfile + "</" + header
					+ ">", "item", "sd");
			String SDfile = "";
			if (xml.IS_EXSIST(urls, url)) {
				SDfile = sd.get(CacheINDEX);
				if (isLog)
					Log.e("iIV Cache State : ", "Found SD File : ");
			} else {
				if (isLog)
					Log.e("iIV Cache State : ", "No SD Cache");
				SDfile = null;
			}
			xmlfile = "";
			urls.clear();
			sd.clear();
			return SDfile;
		} else {
			// Database Code
			HashMap<String, String> check = iDatabaseManager.GetRecord("guid",
					new String[] { "code" }, "session_name='" + Session
							+ "' AND url='" + url + "'");
			if (isLog)
				Log.e("iIV Cache State : ", "Code is :" + check.get("code"));

			if (check.get("code") != null) {
				if (isLog)
					Log.e("iIV Cache State : ", "Found Database File Code : "
							+ check.get("code"));
				return check.get("code");
			} else {
				if (isLog)
					Log.e("iIV Cache State : ", "No Database Cache");
				return null;
			}
		}
	}

	private static void SaveCache(String sFileName, String sBody, String url,
			String oldXML) {
		if (!isDatabase) {
			try {

				if (!root.exists()) {
					root.mkdirs();
				}
				File gpxfile = new File(root, sFileName);
				FileWriter writer = new FileWriter(gpxfile);
				writer.append(oldXML);
				writer.append("<item><link>" + xml.FIX_XML_TOSAVE(url)
						+ "</link>");
				writer.append("\n");
				writer.append("<sd>" + sBody + "</sd></item>");
				writer.append("\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {

			}
		} else {
			// Database Code
		}
	}

	private static void SaveText(String Content, String Path) {
		try {
			String path = Environment.getExternalStorageDirectory().toString()
					+ "/" + Path;
			File myDir = new File(path);
			if (myDir.isDirectory() && myDir.exists()) {
				if (isLog)
					Log.d("Dir exist : ", path);
			} else {
				File wallpaperDirectory = new File(path);
				wallpaperDirectory.mkdirs();
				if (isLog)
					Log.d("Create To : ", path);
			}
			// myDir.mkdirs();
			Random generator = new Random();
			int n = 1000000;
			n = generator.nextInt(n);
			String fname = "ismartcachet-" + n;
			File gpxfile = new File(path, fname);
			FileWriter writer = new FileWriter(gpxfile);
			writer.append(Content);
			;
			writer.flush();
			writer.close();
			FinalSD_Path = path + fname;
		} catch (IOException e) {

		}
	}

	private static void SaveIamge(Context ctx, Bitmap finalBitmap, String Path) {

		String path = Environment.getExternalStorageDirectory().toString()
				+ "/" + Path;
		File myDir = new File(path);
		if (myDir.isDirectory() && myDir.exists()) {
			if (isLog)
				Log.d("Dir exist : ", path);
		} else {
			File wallpaperDirectory = new File(path);
			wallpaperDirectory.mkdirs();
			if (isLog)
				Log.d("Create To : ", path);
		}
		// myDir.mkdirs();
		Random generator = new Random();
		int n = 1000000;
		n = generator.nextInt(n);
		String fname = "ismartcache-" + n;
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://" + Environment.getExternalStorageDirectory())));
		FinalSD_Path = path + fname;

	}

	/**
	 * Return the size of a directory in bytes
	 */
	public static void CHECK_CACHE_SIZE(long MaxSize) {
		if (!isDatabase) {
			if (iiVroot.isDirectory()) {
				long sizeDir = dirSize(iiVroot);
				if (isLog)
					Log.e("Dir Size : " + iiVroot, "" + sizeDir);
				if (sizeDir > ((MaxSize * 1024) * 1024)) {
					if (isLog)
						Log.i("Cach Reach Maximum Size", "Size is More Than : "
								+ MaxSize);
					ClEAR_CACHE();
				}
			}
		} else {
			// Database Code
			File f = CTX.getDatabasePath(DBNAME);
			long dbSize = f.length();
			if (isLog)
				Log.e("Database Data Size : ", "" + dbSize);
			if (dbSize > ((MaxSize * 1024) * 1024)) {
				if (isLog)
					Log.i("Cach Reach Maximum Size", "Size is More Than : "
							+ MaxSize + " MB");
				ClEAR_CACHE();
			}
		}
	}

	private static long dirSize(File dir) {

		if (dir.exists()) {
			long result = 0;
			File[] fileList = dir.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// Recursive call if it's a directory
				if (fileList[i].isDirectory()) {
					result += dirSize(fileList[i]);
				} else {
					// Sum the file size in bytes
					result += fileList[i].length();
				}
			}
			return result; // return the file size
		}
		return 0;
	}

	public static void CHECK_MD5_TEXT(String url) throws IOException {
		String sdFile;
		if (iProjectTools.isOnline(CTX)
				&& (sdFile = CHECK_CACHE(url, DEFAUL_TSESSION)) != null) {
			if (!isDatabase) {
				String fileMD5 = fileToMD5(sdFile);
				if (isLog)
					Log.e("SD MD5", "MD5 SD File : " + fileMD5);
				String URLMD5 = "fileM.GET_TEXT_FILE(CTX, iStatics.GET_MD5 + url)";
				if (isLog)
					Log.e("URL MD5", "MD5URL File: " + URLMD5);
				if (!fileMD5.equals(URLMD5.trim())) {
					// if(isLog)Log.e("SD File MD5 Check : ",
					// "File Content Changed : "+fileToMD5(sdFile));
					String file = "GetContent_FromHttpUrl.Get_TextFileContent(url, 8000, 9000, 0, false)";
					String filename = sdFile.replace(
							iCacheManager.Environmentpath + "/"
									+ iCacheManager.ThumbsFolder, "");
					if (isLog)
						Log.e("Saving File", "Save File : " + filename);
					fileM.SaveText(file, iCacheManager.ThumbsFolder, filename);
				} else {
					if (isLog)
						Log.e("SD File MD5 Check : ", "No Change In file : "
								+ fileToMD5(sdFile));
				}
			} else {
				HashMap<String, String> ch = iDatabaseManager.GetRecord(
						"cache", new String[] { "md5" }, "code='" + sdFile
								+ "'");
				String fileMD5 = ch.get("md5");
				if (isLog)
					Log.e("Database MD5", "MD5 Database File : " + fileMD5);
				String URLMD5 = "";
				if (isLog)
					Log.e("URL MD5", "MD5URL File: " + URLMD5);
				if (!fileMD5.trim().equals(URLMD5.trim())) {
					if (isLog)
						Log.e(" MD5 Check : ", "Found New Changes In File");
					String file = "GetContent_FromHttpUrl.Get_TextFileContent(url, 8000, 9000, 0, false)";
					iDatabaseManager.UpdateTable("cache", new String[] { "content" }, new String[] { file }, "code='" + sdFile + "'");
				} else {
					if (isLog)
						Log.e("MD5 Check : ", "No Change In file ");
				}
			}
		} else {
			if (isLog)
				Log.e("No File : ",
						"this url don't have cache file or there is no internet to check");
		}
	}

	public static String fileToMD5(String filePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			MessageDigest digest = MessageDigest.getInstance("MD5");
			int numRead = 0;
			while (numRead != -1) {
				numRead = inputStream.read(buffer);
				if (numRead > 0)
					digest.update(buffer, 0, numRead);
			}
			byte[] md5Bytes = digest.digest();
			return convertHashToString(md5Bytes);
		} catch (Exception e) {
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private static String convertHashToString(byte[] md5Bytes) {
		String returnVal = "";
		for (int i = 0; i < md5Bytes.length; i++) {
			returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16)
					.substring(1);
		}
		return returnVal;
	}

	public static boolean IS_OFFLINE(String[] Sessions, String[][] urls)
			throws IOException {
		if (isDatabase) {
			for (int j = 0; j < Sessions.length; j++) {
				for (int i = 0; i < urls.length; i++) {
					HashMap<String, String> ch = iDatabaseManager.GetRecord(
							"guid", new String[] { "url" }, "url='"
									+ urls[j][i] + "'");
					if (ch.get("url") == null) {
						return false;
					}
				}
			}
		} else {
			for (int j = 0; j < Sessions.length; j++) {
				for (int i = 0; i < urls.length; i++) {
					if (CHECK_CACHE(urls[j][i], Sessions[j]) == null) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
