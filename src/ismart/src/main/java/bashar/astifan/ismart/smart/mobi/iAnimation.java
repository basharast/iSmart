package bashar.astifan.ismart.smart.mobi;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
public class iAnimation {
	private final Handler handler = new Handler();
	private Object obj;
	private String[] Data = null;
	private Bitmap[] IData = null;
	private int[] InData = null;
	private int Data_ID = 0;
	private int progress = 0;
	private Context CTX;
	private int in = 0;
	private int out = 0;
	private int time = 0;

	public void ObjectAnimation(Context ctx, Object obj, String[] DATA,
			int AnimationIN, int AninmationOUT, int Time) {
		CTX = ctx;
		Data = DATA;
		Animiator(AnimationIN, AninmationOUT, Time, obj);
	}

	public void ObjectAnimation(Context ctx, Object obj, Bitmap[] DATA,
			int AnimationIN, int AninmationOUT, int Time) {
		CTX = ctx;
		IData = DATA;
		Animiator(AnimationIN, AninmationOUT, Time, obj);
	}
	public void ObjectAnimation(Context ctx, Object obj, int[] DATA,
			int AnimationIN, int AninmationOUT, int Time) {
		CTX = ctx;
		InData = DATA;
		Animiator(AnimationIN, AninmationOUT, Time, obj);
	}
	private void Animiator(int Ain, int Aout, int Atime, Object Aobj) {
		in = Ain;
		out = Aout;
		time = Atime;
		obj = Aobj;
		handler.removeCallbacks(InHandler);
		handler.postDelayed(InHandler, 1 * 700);
		handler.removeCallbacks(OutHandler);
		handler.postDelayed(OutHandler, 1 * 1000);

	}

	private Runnable OutHandler = new Runnable() {

		public void run() {

			if (progress != ((time / 10) + (time / 100))) {
				// Log.solutions.ismart.libraryismart.i("", "Progress : "+progress);
				progress += 1;
				if (progress == ((time / 10) - (time / 100))) {
					Animation fadout = AnimationUtils.loadAnimation(CTX, out);
					fadout.setDuration(time);
					((View) obj).startAnimation(fadout);
					((View) obj).setVisibility(View.INVISIBLE);
				}
			} else {
				progress = 0;
				handler.postDelayed(InHandler, 1 * 50); // 1 sec1200000
			}
			handler.postDelayed(this, 1 * 50); // 1 sec1200000

		}

	};
	private Runnable InHandler = new Runnable() {
		public void run() {
			if (Data != null) {
				((TextView) obj).setText(Data[Data_ID]);
			} else if (IData != null) {
				((ImageView) obj).setImageBitmap(IData[Data_ID]);
			}else if(InData!=null){
				((ImageView) obj).setImageResource(InData[Data_ID]);
			}
			((View) obj).setVisibility(View.VISIBLE);
			Animation fadin = AnimationUtils.loadAnimation(CTX, in);
			fadin.setDuration(time);
			((View) obj).startAnimation(fadin);
			if (Data != null) {
				if (Data_ID < Data.length - 1) {
					Data_ID += 1;
				} else {
					Data_ID = 0;
				}
			} else if (IData != null) {
				if (Data_ID < IData.length - 1) {
					Data_ID += 1;
				} else {
					Data_ID = 0;
				}
			} else if (InData != null) {
				if (Data_ID < InData.length - 1) {
					Data_ID += 1;
				} else {
					Data_ID = 0;
				}
			}
		}

	};

	public void Stop() {
		handler.removeCallbacks(InHandler);
		handler.removeCallbacks(OutHandler);
	}
}
