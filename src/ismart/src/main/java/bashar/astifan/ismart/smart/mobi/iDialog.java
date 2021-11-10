package bashar.astifan.ismart.smart.mobi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import bashar.astifan.ismart.R;

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
public class iDialog {
	public static boolean isLog = false;

	public static class ismartListDialog {
		int layout = 0;
		int ico = 0;
		int title = 0;
		int list = 0;
		ListView dg_list = null;
		String stitle = "Dialog";
		int resico = 0;
		BaseAdapter adp = null;
		Context ctx;
		Typeface tf = null;
		Dialog dg;
		ImageView darkback = null;

		public ismartListDialog(Context ctx) {
			this.ctx = ctx;
		}

		public ismartListDialog(Context ctx, int layout, int ico, int title,
				int list) {
			this.ctx = ctx;
			this.layout = layout;
			this.ico = ico;
			this.title = title;
			this.list = list;
		}

		public void setTitle(String title) {
			this.stitle = title;
		}

		public void setIcon(int icon) {
			this.resico = icon;
		}

		public void setTextTypeface(Typeface tf) {
			this.tf = tf;
		}

		public void setTransBack(ImageView view) {
			this.darkback = view;
		}

		public void setAdapter(BaseAdapter adp) {
			this.adp = adp;
		}

		private void hide_back() {
			if (darkback != null) {
				android.view.animation.Animation animation = AnimationUtils
						.loadAnimation(ctx, R.anim.fade_out);
				darkback.setAnimation(animation);
				darkback.setVisibility(View.INVISIBLE);
			}
		}

		private void dialog_creater() {
			LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
			if (isLog)
				Log.d("CDialog", "CDialog 0");
			View dialoglayout = inflater.inflate(layout, null);
			if (isLog)
				Log.d("CDialog", "CDialog 1");
			dg = new Dialog(ctx);
			dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dg.setContentView(dialoglayout);
			if (isLog)
				Log.d("CDialog", "CDialog 2");
			TextView txt_title = (TextView) dialoglayout.findViewById(title);
			txt_title.setText(stitle);
			if (tf != null)
				txt_title.setTypeface(tf);
			if (isLog)
				Log.d("CDialog", "CDialog 3");
			ImageView dg_ico = (ImageView) dialoglayout.findViewById(ico);
			dg_ico.setImageResource(resico);
			if (isLog)
				Log.d("CDialog", "CDialog 4");
			dg_list = (ListView) dialoglayout.findViewById(list);
			if (adp != null)
				dg_list.setAdapter(adp);
			if (isLog)
				Log.d("CDialog", "CDialog 5");
			// -----------
			dg.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface arg0) {
					// TODO Auto-generated method stub
					hide_back();
				}
			});
			dg.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					hide_back();
				}
			});
		}

		public ListView getListView() {
			return dg_list;
		}

		public void show() {
			dialog_creater();
			dg.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dg.show();
			if (darkback != null) {
				android.view.animation.Animation animation = AnimationUtils
						.loadAnimation(ctx, R.anim.fade_in);
				darkback.setAnimation(animation);
				darkback.setVisibility(View.VISIBLE);
			}
		}

		public void dismiss() {
			dg.dismiss();
		}
	}

	public static class ismartDialog {
		int layout = 0;
		int ico = 0;
		int title = 0;
		int text = 0;
		int b1 = 0;
		int textb1 = 0;
		int b2 = 0;
		int textb2 = 0;
		boolean isedit=false;
		EditText edit_text;
		String stitle = "Dialog", sques = "Did You..?", sb1 = "Yes",
				sb2 = "No";
		OnClickListener cb1, cb2;
		int resico = 0;
		Context ctx;
		Typeface tf = null;
		Dialog dg;
		ImageView darkback = null;

		public ismartDialog(Context ctx) {
			this.ctx = ctx;
			inis_clicks();
		}

		private void inis_clicks() {
			cb1 = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dg.dismiss();
					hide_back();
				}
			};
			cb2 = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dg.dismiss();
					hide_back();
				}
			};
			if (isLog)
				Log.d("CDialog", "CDialog Clicks Done");
		}
		public String getEditTextValue(){
			return edit_text.getText().toString();
		}
           public void setTextToEdit(){
	          isedit=true;
              }
		public ismartDialog(Context ctx, int layout, int ico, int title,
				int text, int b1, int b1text, int b2, int b2text) {
			this.ctx = ctx;
			this.layout = layout;
			this.ico = ico;
			this.title = title;
			this.text = text;
			this.b1 = b1;
			this.textb1 = b1text;
			this.b2 = b2;
			this.textb2 = b2text;
			inis_clicks();
		}

		public void setTitle(String title) {
			this.stitle = title;
		}

		public void setIcon(int icon) {
			this.resico = icon;
		}

		public void setText(String text) {
			this.sques = text;
		}

		public void setButton1(String Text, OnClickListener click) {
			this.sb1 = Text;
			this.cb1 = click;
		}

		public void setButton1(String Text) {
			this.sb1 = Text;
		}

		public void setButton2(String Text, OnClickListener click) {
			this.sb2 = Text;
			this.cb2 = click;
		}

		public void setButton2(String Text) {
			this.sb2 = Text;
		}

		public void setTextTypeface(Typeface tf) {
			this.tf = tf;
		}

		public void setTransBack(ImageView view) {
			this.darkback = view;
		}

		private void hide_back() {
			if (darkback != null) {
				android.view.animation.Animation animation = AnimationUtils
						.loadAnimation(ctx, R.anim.fade_out);
				darkback.setAnimation(animation);
				darkback.setVisibility(View.INVISIBLE);
			}
		}

		private void dialog_creater() {
			LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
			if (isLog)
				Log.d("CDialog", "CDialog 0");
			View dialoglayout = inflater.inflate(layout, null);
			if (isLog)
				Log.d("CDialog", "CDialog 1");
			dg = new Dialog(ctx);
			dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dg.setContentView(dialoglayout);
			if (isLog)
				Log.d("CDialog", "CDialog 2");
			TextView txt_title = (TextView) dialoglayout.findViewById(title);
			txt_title.setText(stitle);
			if (tf != null)
				txt_title.setTypeface(tf);
			if (isLog)
				Log.d("CDialog", "CDialog 3");
			if(isedit){
				edit_text = (EditText) dialoglayout.findViewById(text);
				edit_text.setText(sques);
				edit_text.setTypeface(tf);
			}else
			{
				TextView txt_questin = (TextView) dialoglayout.findViewById(text);
				txt_questin.setText(sques);
				txt_questin.setTypeface(tf);
			}
			if (tf != null)
				
			if (isLog)
				Log.d("CDialog", "CDialog 4");
			ImageView dg_ico = (ImageView) dialoglayout.findViewById(ico);
			dg_ico.setImageResource(resico);
			if (isLog)
				Log.d("CDialog", "CDialog 5");
			ImageView btn_ys = (ImageView) dialoglayout.findViewById(b1);
			btn_ys.setVisibility(View.VISIBLE);
			TextView txt_b1 = (TextView) dialoglayout.findViewById(textb1);
			txt_b1.setText(sb1);
			if (tf != null)
				txt_b1.setTypeface(tf);
			if (isLog)
				Log.d("CDialog", "CDialog 6");
			btn_ys.setOnClickListener(cb1);
			ImageView btn_no = (ImageView) dialoglayout.findViewById(b2);
			btn_no.setVisibility(View.VISIBLE);
			btn_no.setOnClickListener(cb2);
			TextView txt_b2 = (TextView) dialoglayout.findViewById(textb2);
			txt_b2.setText(sb2);
			if (tf != null)
				txt_b2.setTypeface(tf);
			if (isLog)
				Log.d("CDialog", "CDialog 7");

			// -----------
			dg.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface arg0) {
					// TODO Auto-generated method stub
					hide_back();
				}
			});
			dg.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					hide_back();
				}
			});
		}

		public void show() {
			dialog_creater();
			dg.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dg.show();
			if (darkback != null) {
				android.view.animation.Animation animation = AnimationUtils
						.loadAnimation(ctx, R.anim.fade_in);
				darkback.setAnimation(animation);
				darkback.setVisibility(View.VISIBLE);
			}
		}

		public void dismiss() {
			dg.dismiss();
		}
	}

	public static class Alert {
		public Builder AlertDialog(Context ctx, String TitleText,
				String MessageText, int icon) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
			alertbox.setTitle(TitleText);
			alertbox.setIcon(icon);
			// set the message to display
			alertbox.setMessage(MessageText);
			return alertbox;

		}

		public Builder AlertDialog(Context ctx, ListAdapter adapter,
				DialogInterface.OnClickListener ClickListen) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
			alertbox.setAdapter(adapter, ClickListen);
			// alertbox.show();
			// set the message to display
			return alertbox;

		}
	}


}
