package bashar.astifan.ismart.smart.services.uploader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

@SuppressLint("NewApi")
public abstract class iUploader {
	String dialog_text="Uploading file...";
	Context ctx;
	boolean islog=true;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public void uploadFile(Context ctx,String ServerUrl,String sourceFileUri,String GET_VALUES) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		this.ctx=ctx;
		new upload().execute(new String[]{ServerUrl,sourceFileUri,GET_VALUES});
       }
	
	class upload extends AsyncTask<String, String, String>{
		
		int serverResponseCode = 0;
		String serverResponseMessage="";
		 String jsonString = "";
		String upLoadServerUri ;
	    String fileName ;
	    HttpURLConnection conn = null;
	    DataOutputStream dos = null;  
	    String lineEnd = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "*****";
	    int bytesRead, bytesAvailable, bufferSize;
	    byte[] buffer;
	    int maxBufferSize = 1 * 1024 * 1024; 
	    File sourceFile = null;
		boolean noErrors=true;
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	onStart();
	super.onPreExecute();
}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			upLoadServerUri =params[0]+(params[2].length()>0?"?"+params[2]:"");
			 if(islog)Log.i("uploadFile", "upLoadServerUri :  "+upLoadServerUri);
			fileName=params[1];
			 sourceFile = new File(fileName); 
			    if (!sourceFile.isFile()) {
			    if(islog) Log.e("uploadFile", "Source File Does not exist");
			    }else{
			 if(islog)Log.i("uploadFile", "fileName :  "+fileName);
			 
			try { // open a URL connection to the Servlet
	             FileInputStream fileInputStream = new FileInputStream(sourceFile);
	             URL url = new URL(upLoadServerUri);
	             conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
	             conn.setDoInput(true); // Allow Inputs
	             conn.setDoOutput(true); // Allow Outputs
	             conn.setUseCaches(false); // Don't use a Cached Copy
	             conn.setRequestMethod("POST");
	             conn.setRequestProperty("Connection", "Keep-Alive");
	             conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	             conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	             conn.setRequestProperty("uploaded_file", fileName); 
	             dos = new DataOutputStream(conn.getOutputStream());
	   
	             dos.writeBytes(twoHyphens + boundary + lineEnd); 
	             dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
	             dos.writeBytes(lineEnd);
	   
	             bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
	   
	             bufferSize = Math.min(bytesAvailable, maxBufferSize);
	             buffer = new byte[bufferSize];
	   
	             // read file and write it into form...
	             bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	               
	             while (bytesRead > 0) {
	               dos.write(buffer, 0, bufferSize);
	               bytesAvailable = fileInputStream.available();
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);               
	              }
	   
	             // send multipart form data necesssary after file data...
	             dos.writeBytes(lineEnd);
	             dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	   
	             // Responses from the server (code and message)
	             serverResponseCode = conn.getResponseCode();
	             serverResponseMessage = conn.getResponseMessage();
	              
	             if(islog)Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
	             if(serverResponseCode == 200){
	            	 
	            	  if(islog) Log.e("Upload State","File Uploaded Done ");  
	             }    
	             
	             InputStream in = conn.getInputStream();

	             byte data[] = new byte[1024];
	             int counter = -1;
	            
	             while( (counter = in.read(data)) != -1){
	                  jsonString += new String(data, 0, counter);
	              }
	             
	             noErrors=true;
	             //close the streams //
	             fileInputStream.close();
	             dos.flush();
	             dos.close();
	              
	        } catch (MalformedURLException ex) {
	            ex.printStackTrace();
	            noErrors=false;
	           // Toast.makeText(UploadImageDemo.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	            if(islog) Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	        } catch (Exception e) {
	            e.printStackTrace();
	            noErrors=false;
	          //  Toast.makeText(UploadImageDemo.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
	            if(islog)   Log.e("Upload Exception", "Exception : " + e.getMessage(), e);
	        }
			    }
			return null;
			
		} 
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if(noErrors)
			{
			 onCompleted(serverResponseCode,serverResponseMessage,jsonString);
			}else{
				
			}
			Finish();
			super.onPostExecute(result);
		}
	}
	
	public void isLog(boolean state){
		islog=state;
	}
	public void setDialogText(String Text){
		dialog_text=Text;
	}
		
    public abstract void onCompleted(final int serverResponseCode, final String serverResponseMessage,final String serverResult);
	public abstract void onStart();
	public abstract void Finish();
	
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @author paulburke
	 */
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {

	        // Return the remote address
	        if (isGooglePhotosUri(uri))
	            return uri.getLastPathSegment();

	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
