package bashar.astifan.ismart.smart.mobi;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class iJSON {
	static boolean isLog = false;

	public HashMap<String, ArrayList<String>> get_json_object_data(
			String json_string, String main_tag) {
		HashMap<String, ArrayList<String>> array = new HashMap<String, ArrayList<String>>();
		try {
			JSONObject json = new JSONObject(json_string);
			JSONArray json_array = json.getJSONArray(main_tag);

			for (int i = 0; i < json_array.length(); i++) {
				for (int j = 0; j < json_array.getJSONObject(i).names()
						.length(); j++) {
					String key = json_array.getJSONObject(i).names()
							.getString(j);
					String val = json_array.getJSONObject(i).getString(key);
					if(isLog)Log.i("Add To Key (" + j + ")", key + "  -  Value : " + val);
					if(isLog)Log.i("STATE", "Add To LIST");
					if (array != null) {
						if(isLog)Log.i("STATE", "Not Null");
						if (array.get(key) != null && array.get(key).size() > 0) {
							ArrayList<String> results = new ArrayList<String>();
							results.addAll(array.get(key));
							results.add(val);
							if(isLog)Log.i("STATE", "Key Found");
							array.put(key, results);
						} else {
							if(isLog)Log.i("STATE", "New Key Set");
							ArrayList<String> results = new ArrayList<String>();
							results.add(val);
							array.put(key, results);
						}
					}
				}
			}

		} catch (JSONException e) {
			if(isLog)Log.e("JSON ERROR", e.toString());
		}
		return array;

	}

	public HashMap<String, ArrayList<String>> get_json_array_data(
			String json_string) {
		HashMap<String, ArrayList<String>> array = new HashMap<String, ArrayList<String>>();
		try {
			JSONArray json_array = new JSONArray(json_string);
			for (int i = 0; i < json_array.length(); i++) {
				for (int j = 0; j < json_array.getJSONObject(i).names()
						.length(); j++) {
					String key = json_array.getJSONObject(i).names()
							.getString(j);
					String val = json_array.getJSONObject(i).getString(key);
					if(isLog)Log.i("Add To Key (" + j + ")", key + "  -  Value : " + val);
					if(isLog)Log.i("STATE", "Add To LIST");
					if (array != null) {
						if(isLog)Log.i("STATE", "Not Null");
						if (array.get(key) != null && array.get(key).size() > 0) {
							ArrayList<String> results = new ArrayList<String>();
							results.addAll(array.get(key));
							results.add(val);
							if(isLog)Log.i("STATE", "Key Found");
							array.put(key, results);
						} else {
							if(isLog)Log.i("STATE", "New Key Set");
							ArrayList<String> results = new ArrayList<String>();
							results.add(val);
							array.put(key, results);
						}
					}
				}
			}
		} catch (JSONException e) {
			if(isLog)Log.e("JSON ERROR", e.toString());
		}
		return array;
	}

	public String get_json_simple_object_data(String json_string, String element) {
		String val = "";
		try {
			JSONObject json = new JSONObject(json_string);
			val = json.getString(element);

		} catch (JSONException e) {
			val = json_string;
			if(isLog)Log.e("JSON ERROR", e.toString());
		}
		return val;
	}

	public ArrayList<String> get_json_simple_array_data(String json_string) {
		ArrayList<String> array = new ArrayList<String>();
		try {
			JSONArray json_array = new JSONArray(json_string);
			for (int i = 0; i < json_array.length(); i++) {
				array.add(json_array.getString(i));
			}
		} catch (JSONException e) {
			if(isLog)Log.e("JSON ERROR", e.toString());
		}
		return array;
	}

	public String get_map_route_data(String json_content) {
		try {
			final JSONObject json = new JSONObject(json_content);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			return overviewPolylines.getString("points");
		} catch (JSONException e) {
			if(isLog)Log.e("JSON ERROR", e.toString());
			return "error";
		}
	}

}
