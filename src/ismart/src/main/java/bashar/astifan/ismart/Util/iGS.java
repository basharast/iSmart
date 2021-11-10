package bashar.astifan.ismart.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
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
public class iGS {

	public static String getFromResponse(HttpResponse response) throws ParseException, IOException{
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
	public static String bytesToString(byte[] bytes){
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} // for UTF-8 encoding
	}
}
