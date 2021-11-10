package bashar.astifan.ismart.smart.services.gcm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import bashar.astifan.ismart.Util.iGS;
import bashar.astifan.ismart.smart.mobi.iProjectTools;

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
abstract  public class GCMTools {

	public static boolean isLog=false;

	public void Register(final Context CTX,final String SERVER_URL,final String GCMGROUP){
		if (iProjectTools.isOnline(CTX)) {
			FirebaseMessaging.getInstance().setAutoInitEnabled(true);
			FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
				@Override
				public void onComplete(@NonNull Task<InstanceIdResult> task) {
					if (!task.isSuccessful()) {
						Log.e("is Registered", task.getException().toString());
						return;
					}
					String regIda = task.getResult().getToken();
					if(isLog)Log.v("------GCM-----", "FCM Token : " + regIda);

					RequestParams params = new RequestParams();
					params.put("regId",regIda.equals("")?regIda:regIda);
					if(GCMGROUP.length()>0)params.put("gcm_group",GCMGROUP);
					AsyncHttpClient client = new AsyncHttpClient();
					client.post(SERVER_URL, params, new AsyncHttpResponseHandler() {

						@Override
						public void onStart() {
							// called before request is started
							onRegisterStart();
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] response) {
							// called when response HTTP status is "200 OK"
							String res = iGS.bytesToString(response);
							iProjectTools.SharedPrefrences_SAVE(CTX,
									new String[] { "id" }, new String[] { regIda }, "fcm_token");
							onRegisterComplete(res);
						}

						@Override
						public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
							String res = iGS.bytesToString(errorResponse);
							onRegisterFailed(res);
							onRegisterComplete(res);
						}

						@Override
						public void onRetry(int retryNo) {
							// called when request is retried
						}
					});
				}
			});

		}
	}

	public void onRegisterComplete(String response) {

	}

	abstract public  void onRegisterStart();
	abstract public  void onRegisterFailed(String errorResponse);


	public static String getGCMID(Context ctx){
		String email = iProjectTools.SharedPrefrences_GET(ctx, "fcm_token",
				new String[] { "id" }).get(0);
		if(email == null || email.length() == 0){

		}
		return email;
	}

	public void isRegister(Context ctx,String checkURL){

		final String id = getGCMID(ctx);

		final RequestParams params = new RequestParams();
		params.put("key","x_o_X_O");
		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(checkURL + id, params, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started

			}
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
				// called when response HTTP status is "200 OK"
				String res = iGS.bytesToString(response);
				boolean state=false;
				if (res.trim().equals("false") | id == null | id == "") {
					Log.e("is Registered", "false");
					state= false;
				} else{
					Log.e("is Registered"+res.trim(), "true : " + id);
					state= true;
				}
				onCheckFinish(state);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
				onCheckFinish(false);
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}
		});

	}
	abstract public void onCheckFinish(boolean state);
}
