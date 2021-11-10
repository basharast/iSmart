package bashar.astifan.ismart.smart.mobi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class iImageTools {
	public static boolean isLog = false;


	public static Bitmap DecodeImage(Context ctx, int resID) {
		return BitmapFactory.decodeResource(ctx.getResources(), resID);
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@SuppressWarnings("deprecation")
	public static Bitmap getBitmapFromAsset(Context context, String strName) {
		AssetManager assetManager = context.getAssets();

		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(strName);
			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inTempStorage = new byte[16 * 512];
			options.inDither = false; // Disable Dithering mode

			options.inPurgeable = true; // Tell to gc that whether it needs free
										// memory, the Bitmap can be cleared

			options.inInputShareable = true; // Which kind of reference will be
												// used to recover the Bitmap
												// data after being clear, when
												// it will be used in the future

			options.inPreferredConfig = Bitmap.Config.RGB_565;
			bitmap = BitmapFactory.decodeStream(istr, null, options);
		} catch (IOException e) {
			return null;
		}

		return bitmap;
	}


	@SuppressWarnings("resource")
	public static Bitmap decodeFile(Context ctx, String Path, boolean isAssets)
			throws IOException {
		try {

			File f = null;
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			InputStream istr = null;
			if (isAssets) {
				AssetManager assetManager = ctx.getAssets();
				istr = assetManager.open(Path);
			} else {
				f = new File(Path);
			}
			BitmapFactory.decodeStream(isAssets ? istr : new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(isAssets ? istr
					: new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {

			if (isLog)
				Log.e("", e.toString());
		}
		return null;
	}
	public static void CropImage(Context ctx, Uri picUri, int intentcode) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 350);
			cropIntent.putExtra("outputY", 350);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			((Activity) ctx).startActivityForResult(cropIntent, intentcode);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			if (isLog)
				Log.e("", errorMessage);
		}
	}
	public static class iMask {
		private boolean issircle = false;
		private int iMask = 0;
		private int imargin = 0;

		public iMask(int Mask, int margin) {
			iMask = Mask;
			imargin = margin;
		}
		public iMask(boolean isCircle) {
			issircle = isCircle;

		}
		public iMask(int Rounded) {
			imargin = Rounded;
		}
		public boolean isCircle() {
			return issircle;
		}
		public int getMask() {
			return iMask;
		}
		public int getMargin() {
			return imargin;
		}

	}
	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
				sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);
		return output;
	}

	public static void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}
}
