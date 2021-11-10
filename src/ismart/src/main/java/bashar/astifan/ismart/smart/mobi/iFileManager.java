package bashar.astifan.ismart.smart.mobi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class iFileManager {
	public static String Environmentpath = Environment
			.getExternalStorageDirectory().toString() + "/";

	/**
	 * 
	 * 
	 * @param path
	 * @return boolean
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static boolean DeleteFile(String path) {
		File file = new File(Environmentpath + path);
		return file.delete();

	}

	/**
	 * 
	 * 
	 * @param path
	 * @return boolean
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static boolean DeleteFolder(String path) {

		File cachedir = new File(Environmentpath + path);
		if (cachedir.isDirectory()) {
			String[] children = cachedir.list();
			for (int i = 0; i < children.length; i++) {
				new File(cachedir, children[i]).delete();
			}
		}
		return isExsistFolder(Environmentpath + path);

	}

	/**
	 * 
	 * 
	 * @param path
	 * @param newName
	 * @return boolean
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static boolean RenameFile(String path, String newName) {
		File file = new File(Environmentpath + path);
		File newfile = new File(Environmentpath + "/" + newName);
		return file.renameTo(newfile);

	}

	/**
	 * 
	 * 
	 * @param path
	 * @return boolean
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static boolean isExsistFile(String path) {
		File file = new File(Environmentpath + path);
		return file.exists();

	}

	/**
	 * 
	 * 
	 * @param path
	 * @return boolean
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static boolean isExsistFolder(String path) {
		File dir = new File(Environmentpath + path);
		return dir.isDirectory();

	}

	/**
	 * 
	 * 
	 * @param path
	 * @return long
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static long FileSize(String path) {
		File file = new File(Environmentpath + path);
		return file.length();

	}

	/**
	 * 
	 * 
	 * @param path
	 * @return long
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static long FolderSize(String path) {
		File file = new File(Environmentpath + path);
		return dirSize(file);

	}

	/**
	 * 
	 * 
	 * @param path
	 * @return String
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 */
	public static String FileMD5(String path) {

		return fileToMD5(Environmentpath + path);
	}

	/**
	 * 
	 * 
	 * @param path
	 * @param fname
	 * @param Content
	 * @return boolean
	 * 
	 *         <b>use simple path without Environmentpath</b>
	 * 
	 */
	public static boolean TextCreator(String path, String fname, String Content) {

		SaveText(Content, Environmentpath + path, fname);
		return isExsistFile(Environmentpath + path + fname);

	}

	public static void openFile(Context ctx,String types,int code){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(types);
        ((Activity) ctx).startActivityForResult(intent,code);
	}
	
	private static String fileToMD5(String filePath) {
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

	public static void SaveText(String Content, String Path, String name) {
		try {
			String path = Environment.getExternalStorageDirectory().toString()
					+ "/" + Path;
			File myDir = new File(path);
			if (myDir.isDirectory() && myDir.exists()) {
				Log.d("Dir exist : ", path);
			} else {
				File wallpaperDirectory = new File(path);
				wallpaperDirectory.mkdirs();
				Log.d("Create To : ", path);
			}
			String fname = name;
			File gpxfile = new File(path, fname);
			FileWriter writer = new FileWriter(gpxfile);
			writer.append(Content);
			;
			writer.flush();
			writer.close();
		} catch (IOException e) {

		}
	}


	public static String GetTextContentFromSDWithOutEnvironment(String fileName) {
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, fileName);
		Log.e("File Path", file.getPath());
		// Read text from file
		StringBuilder text = new StringBuilder();

		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			// You'll need to add proper error handling here
		}
		return text.toString();
	}

	/**
	 * Check if an asset exists. This will fail if the asset has a size < 1
	 * byte.
	 * 
	 * @param context
	 * @param path
	 * @return TRUE if the asset exists and FALSE otherwise
	 */
	public static boolean isAssets(Context context, String path) {
		boolean bAssetOk = false;
		try {
			InputStream stream = context.getAssets().open(path);
			stream.close();
			bAssetOk = true;
		} catch (FileNotFoundException e) {
			Log.w("IOUtilities", "assetExists failed: " + e.toString());
		} catch (IOException e) {
			Log.w("IOUtilities", "assetExists failed: " + e.toString());
		}
		return bAssetOk;
	}
}
