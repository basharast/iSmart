package bashar.astifan.ismart.smart.mobi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import bashar.astifan.ismart.Util.UIHelper;

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
public class iProjectTools {


	public static boolean isLog = false;
	public static boolean isOnline(Context ctx) {
		ConnectivityManager connec = (ConnectivityManager)
				ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
				connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED )
		{
			return true;
		}
		else if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  )
		{
			return false;
		}

		return false;
	}
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}


	public static boolean isOnline(Context ctx, String[] Sessions,
			String[][] CahceURLs) throws IOException {
		if (isOnline(ctx)) {
			if (isLog)
				Log.i("Internet State : ", "Internet Working");
			return true;
		} else {
			if (isLog)
				Log.e("Internet State : ", "Internet off check cache file");
			if (iCacheManager.IS_OFFLINE(Sessions, CahceURLs)) {
				if (isLog)
					Log.i("Cache State : ",
							"Cache OK ,app can run in offline mode");
				return true;
			} else {
				if (isLog)
					Log.e("Internet State : ", "no internet & cache files");
				return false;
			}
		}
	}
public static void HideKeyboard(Context ctx,EditText edt){
	InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
}
	public static boolean isServiceRun(Context ctx, String ServiceIntent) {
		ActivityManager manager = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (ServiceIntent.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
public static void OpenActivity(Context ctx,Class<?> cls){
	Intent j = new Intent(ctx, cls);
	ctx.startActivity(j);
}

	public static void Notify(Context ctx, String Text, String Title,
			Bitmap Largicon, int Smallicon) {

	}

	public static void Exit() {
		UIHelper.killApp(true);
	}

	public static void ExitOnHome() {
		UIHelper.checkHomeKeyPressed(true);
	}

	public static void SharedPrefrences_SAVE(Context ctx, String[] Varibles,
			String[] Values, String SharedName) {
		SharedPreferences sha = ctx.getSharedPreferences(SharedName, 0);
		SharedPreferences.Editor editor = sha.edit();
		for (int i = 0; i < Varibles.length; i++) {
			editor.putString(Varibles[i], Values[i]);
		}
		editor.commit();

	}

	public static ArrayList<String> SharedPrefrences_GET(Context ctx,
			String SharedName, String[] Varibles) {
		SharedPreferences sha = ctx.getSharedPreferences(SharedName, 0);
		ArrayList<String> Results = new ArrayList<String>();
		for (int i = 0; i < Varibles.length; i++) {
			Results.add(sha.getString(Varibles[i], ""));
		}
		return Results;
	}

	public static void ToastShow(Context ctx, String text) {
		Toast toast = Toast.makeText(ctx, text, Toast.LENGTH_LONG);
		toast.show();
	}

	public static void RunService(Context ctx, Class<?> ServiceClass,
			String ServiceIntent) {
		Intent myIntent = new Intent(ctx.getApplicationContext(), ServiceClass);
		if (!isServiceRun(ctx, ServiceIntent)) {
			ctx.startService(myIntent);
		} else {
			if (isLog)
				Log.i("Service State:", "Service is Running");
		}
		ctx.bindService(myIntent, mServerConn, Context.BIND_AUTO_CREATE);
	}

	protected static ServiceConnection mServerConn = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder binder) {
			if (isLog)
				Log.d("", "onServiceConnected");
		}
		public void onServiceDisconnected(ComponentName name) {
			if (isLog)
				Log.d("", "onServiceDisconnected");
		}
	};
	public static void unbindService(Context ctx) {
		ctx.unbindService(mServerConn);
	}

	public static void FullScreen(Context ctx, boolean state) {
		if (state) {
			((Activity) ctx).getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			((Activity) ctx).getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);

		} else {
			((Activity) ctx).getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			((Activity) ctx).getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		}
	}

	public static void ShareIntent(Context ctx, String TextToShare, String Title) {

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, TextToShare);
		ctx.startActivity(Intent.createChooser(sharingIntent, Title));
	}

	public static void OpenExternalIntent(Context ctx, String URL) {
		String furl = URL;
		Intent fI = new Intent(Intent.ACTION_VIEW);
		fI.setData(Uri.parse(furl));
		ctx.startActivity(fI);
	}
	
	public static String getTime(String format) {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();

		return today.format(format);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getDate(String format){
		SimpleDateFormat today = new SimpleDateFormat(format);
		return  today.format(new Date());
	}
	public static Typeface getTypeFace(Context ctx,String fontPath) {
		return Typeface.createFromAsset(((Activity)ctx).getAssets(), fontPath);
	}
	public static int getResourcesIdByName(String name,Context ctx){
		return (((Activity)ctx)).getResources().getIdentifier(name, "drawable", ctx.getPackageName());
	}
	public static String getTime() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		return today.format("%k:%M:%S");
	}
	public static String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

	public static String getCurrentDateTime(){
		return System.currentTimeMillis()+"";
	}
	public static void restartApplication(Context ctx,Class<?> mainClass){
		PackageManager packageManager = ctx.getPackageManager();
		Intent intent = packageManager.getLaunchIntentForPackage(ctx.getPackageName());
		ComponentName componentName = intent.getComponent();
		Intent mainIntent = Intent.makeRestartActivityTask(componentName);
		ctx.startActivity(mainIntent);
		Runtime.getRuntime().exit(0);
	}

	public class LayoutToImage extends iProjectTools {
		View _view;
		Context _context;
		Bitmap bMap;
		public LayoutToImage(Context context, View view)
		{
			this._context=context;
			this._view =view;
		}
		public Bitmap convert_layout()
		{
			View v1 = _view.getRootView();
			v1.setDrawingCacheEnabled(true);
			bMap = Bitmap.createBitmap(v1.getDrawingCache());
			v1.setDrawingCacheEnabled(false);
			return bMap;
		}
	}
}
