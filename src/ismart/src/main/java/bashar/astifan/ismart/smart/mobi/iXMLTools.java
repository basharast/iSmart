package bashar.astifan.ismart.smart.mobi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cz.msebera.android.httpclient.Header;
import bashar.astifan.ismart.Util.iGS;
import bashar.astifan.ismart.Util.helpers.XMLfunctions;

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
public class iXMLTools {


		boolean isLog = false;


	public ArrayList<String> GET_TAG(Context ctx, String xml, String mainTAG,
			String customTAG) {
		if(isLog)Log.d("----------", " Start");
		// Needed for the listItems
		ArrayList<String> ImageList = new ArrayList<String>();
		// Get the xml string from the server
		if(isLog)Log.d("-------*******--------", xml);
		if (xml != "Wrong URL") {
			Document doc = XMLfunctions.XMLfromString(xml);
			if (mainTAG != "custom") {
				int numResults = XMLfunctions.numResults(doc, mainTAG);
				if ((numResults <= 0)) {
					Toast.makeText(ctx, "No DATA", Toast.LENGTH_LONG).show();
				}
			}
			NodeList nodes = doc.getElementsByTagName(mainTAG);
			// fill in the list items from the XML document
			ImageList.clear();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element e = (Element) nodes.item(i);
				ImageList.add(XMLfunctions.getValue(e, customTAG.toString()));

				if(isLog)Log.d(" Item " + i, "*** " + ImageList.get(i).toString()
						+ " ***");
			}
			return ImageList;
		} else {
			if(isLog)Log.d("URL STATE", "*** Wrong URL ***");
			return null;
		}
	}

	public String GET_XML_FILE(Context ctx, String url, boolean isCache,
			boolean MD5Check) throws IOException {
		final String[] file = {null};
		final String sdName;
		final iFileManager mfile = new iFileManager();
		if ((sdName = iCacheManager.CHECK_CACHE(url, iCacheManager.DEFAUL_TSESSION)) != null && isCache) {
			if (!iCacheManager.isDatabase) {
				if (isLog)
					Log.i("File SD Found", "Checking file .. ");
				File f = new File(sdName);
				if (f.exists()) {
					if (MD5Check) {
						iCacheManager.CHECK_MD5_TEXT(url);
					}
					if (isLog)
						Log.i("File SD Found", "Found : " + sdName);
					file[0] = mfile.GetTextContentFromSDWithOutEnvironment(sdName);
				} else {
					if (isLog)
						Log.e("File Not Found", "Not Found : " + sdName);
					if (isLog)
						Log.e("File Not Found", "Redownload File : " + sdName);
					if (iProjectTools.isOnline(ctx)) {
						RequestParams params = new RequestParams();
						AsyncHttpClient client = new AsyncHttpClient();
							client.get(url,params, new AsyncHttpResponseHandler() {

								@Override
								public void onStart() {
									// called before request is started

								}

								@Override
								public void onSuccess(int statusCode, Header[] headers, byte[] response) {
									// called when response HTTP status is "200 OK"
									file[0] = iGS.bytesToString(response);
									//Register.add_to_log("RESULT DATA: "+data);
									String filename = sdName.replace(
											iCacheManager.Environmentpath + "/"
													+ iCacheManager.ThumbsFolder, "");
									if (isLog)
										Log.e("Saving File", "Save File : " + filename);
									mfile.SaveText(file[0], iCacheManager.ThumbsFolder,
											filename);
								}

								@Override
								public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

								}

								@Override
								public void onRetry(int retryNo) {
									// called when request is retried
								}
							});


					} else {
						if (isLog)
							Log.e("No Internet",
									"Can't Fix file with out connection : "
											+ sdName);
					}
				}
			} else {
				if (MD5Check) {
					iCacheManager.CHECK_MD5_TEXT(url);
				}
				HashMap<String, String> cont = iDatabaseManager.GetRecord(
						"cache", new String[] { "content" }, "code='" + sdName
								+ "'");
				file[0] = cont.get("content");
			}
		} else {
			RequestParams params = new RequestParams();
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url,params, new AsyncHttpResponseHandler() {

				@Override
				public void onStart() {
					// called before request is started

				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] response) {
					// called when response HTTP status is "200 OK"
					file[0] =iGS.bytesToString(response);

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

				}
				@Override
				public void onRetry(int retryNo) {
					// called when request is retried
				}
			});
			if (isCache) {
				iCacheManager.SaveToCache(iCacheManager.DEFAUL_TSESSION,
						url, file[0]);
			}
		}
		return file[0];
	}

	public String FIX_XML(String xml) {
		return replaceXMLChars(xml);
	}

	public String FIX_XML_TOSAVE(String xml) {
		return replaceXML(xml);
	}

	public ArrayList<String> FIX_XML(ArrayList<String> xmlArray) {
		return replaceXMLCharsArrayList(xmlArray);
	}

	public boolean IS_EXSIST(ArrayList<String> arr, String checkText) {

		for (int i = 0; i < arr.size(); i++) {
			if (isLog)
				Log.e("URL TO CHECK", FIX_XML(arr.get(i)));
			if (FIX_XML(arr.get(i)).equals(checkText)) {
				iCacheManager.CacheINDEX = i;
				return true;
			}
		}
		return false;

	}


	public static String replaceXMLChars(String str) {
		if (str.contains("&amp;")) {
			str = str.replace("&amp;", "&");
		}
		if (str.contains("&lt;")) {
			str = str.replace("&lt;", "<");
		}
		if (str.contains("&gt;")) {
			str = str.replace("&gt;", ">");
		}
		if (str.contains("&apos;")) {
			str = str.replace("&apos;", "'");
		}
		if (str.contains("&quot;")) {
			str = str.replace("&quot;", "\"");

		}
		if (str.contains("quot;")) {
			str = str.replace("quot;", "\"");
		}
		if (str.contains("&quo")) {
			str = str.replace("&quo", "\"");
		}
		return str;
	}
	public static String replaceXML(String str) {
		if (str.contains("&")) {
			str = str.replace("&", "&amp;");
		}
		return str;
	}
	public static ArrayList<String> replaceXMLCharsArrayList(
			ArrayList<String> XMLArrayList) {
		ArrayList<String> FinalXMLList = new ArrayList<String>();
		for (int i = 0; i < XMLArrayList.size(); i++) {
			String str = XMLArrayList.get(i);
			if (str.contains("&amp;")) {
				str = str.replace("&amp;", "&");
			}
			if (str.contains("&lt;")) {
				str = str.replace("&lt;", "<");
			}
			if (str.contains("&gt;")) {
				str = str.replace("&gt;", ">");
			}
			if (str.contains("&apos;")) {
				str = str.replace("&apos;", "'");
			}
			if (str.contains("&quot;")) {
				str = str.replace("&quot;", "\"");

			}
			if (str.contains("quot;")) {
				str = str.replace("quot;", "\"");
			}
			if (str.contains("&quo")) {
				str = str.replace("&quo", "\"");
			}
			FinalXMLList.add(str);
		}
		return FinalXMLList;
	}
}
