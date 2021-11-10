package bashar.astifan.ismart.smart.world;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import java.util.HashMap;

import bashar.astifan.ismart.smart.objects.gpuimage.GPUImage;
import bashar.astifan.ismart.smart.objects.gpuimage.GPUImageSepiaFilter;
import bashar.astifan.ismart.smart.objects.gpuimage.GPUImageView;

/**
 *
 *
 * @author Bashar Astifan <br>
 *         <a href=
 *         "astifan.online"
 *         >Read More</a> <br>
 * @version 1.0
 *
 */
public class iWorld {

	boolean Logger=false;
	public class iScene{
		public void addGround(){
			
		}
		public void addCamera(){
			
		}
	}
	public class iGround{
		public void addObject(){
			
		}
	
	}
	public class iObject{
		public void addMenu(){
			
		}
		public void addParticles(){
			
		}
	}
	public class iCamera{
		public void addCamera(){
			
		}
	}
	public class iMenu{
		public void addItem(){
			
		}
	}
	public class iParticles{
		public void addParticle(){
			
		}
	}
	public class iSounder extends iWorld{
		private Context ctx;
		private SoundPool sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		MediaPlayer mp=null;
		private HashMap<Integer,Integer> sounds=new HashMap<>();
		private HashMap<Integer,Integer> music=new HashMap<>();
		public iSounder(Context context){
			ctx=context;

		}
		public void addSound(int id,int src){
			sounds.put(id,sp.load(ctx,src,1));
		}
		public void addMusic(int id,int src){
			music.put(id,src);
		}
		public void playSound(int id){
			if(sounds.get(id)!=null)sp.play(sounds.get(id), 1, 1, 1, 0, 1.0f);else Log.e("iSounder","Sound not found!");
		}
		public void stopSound(int id){
			if(sounds.get(id)!=null)sp.stop(sounds.get(id));else Log.e("iSounder", "Sound not found!");
		}
		public void clearSounds(){
			sp.release();
		}


		private int tempid=0;
		public void playMusic(int id,boolean isLoop){
			if(tempid!=id) {
				if (mp != null && mp.isPlaying()) mp.stop();
				mp = MediaPlayer.create(ctx, music.get(id));
				mp.setLooping(isLoop);
				mp.start();
			}else{
				Log.e("iSounder", "Sound Playing!");
			}
		}
		public void stopMusic(int id){
			if(mp!=null&&mp.isPlaying())mp.stop();
		}
		public void clearMusic(){
			if(mp!=null&&mp.isPlaying())mp.stop();
			if(mp!=null)mp.release();
		}
	}
	public class iTasks{
		public void addTask(){
			
			
		}
	}

	public abstract class iFramesAnimator extends iWorld {
		private int duration;
		private ImageView handlerImage;
		private GPUImageView ghandlerImage=null;
		private Context ctx;
		private int currframe=0;
		private boolean AnimatorWorking=false;
		private boolean loopAnimator=false;
		private boolean stopAnimator=false;
		private boolean spritesheet=false;
		private int FRAME= 0;
		// frame width
		private   int FRAME_W = 0;
		// frame height
		private   int FRAME_H = 0;
		// number of frames
		private   int NB_FRAMES = 0;
		// nb of frames in x
		private   int COUNT_X = 0;
		// nb of frames in y
		private   int COUNT_Y = 0;


		protected iFramesAnimator(ImageView handler,int frameDuration) {
			handlerImage=handler;
			duration=frameDuration;
		}
		protected iFramesAnimator(GPUImageView handler,int frameDuration) {
			ghandlerImage=handler;
			duration=frameDuration;
		}
		public void startAnimation(){
			if(spritesheet){
				stopAnimator=false;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					new SpriteAnimation().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					new SpriteAnimation().execute();
				}
			}

		}


		public void enableLoop(boolean loop){loopAnimator=loop;}

		public void addSpriteFrame(Context context,int frame,int frame_w,int frame_h,int nb_frames,int count_x,int count_y){
			ctx=context;
			spritesheet=true;
			FRAME=frame;
			Resources r = context.getResources();
			FRAME_W = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, frame_w, r.getDisplayMetrics());
			FRAME_H = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, frame_h, r.getDisplayMetrics());
			NB_FRAMES=nb_frames;
			COUNT_X=count_x;
			COUNT_Y=count_y;
		}

		public void stopAnimation(){stopAnimator=true;}



		public boolean isRunning(){
			return AnimatorWorking;
		}
		public abstract void onAnimationFinish();
		public abstract void onAnimationStart();
		public abstract void onAnimationUpdate(Drawable drawable,int currentFrame);
		private class SpriteAnimation extends AsyncTask<Void, Bitmap, Void> {

			@Override
			protected Void doInBackground(Void... params) {

				Bitmap spriteFrame = BitmapFactory.decodeResource(ctx.getResources(), FRAME);
					// cut bitmaps from bird bmp to array of bitmaps
					Bitmap currentSFrame;
					int currentFrame = 0;
					for (int i = 0; i < COUNT_Y; i++) {
						for (int j = 0; j < COUNT_X; j++) {
							currentSFrame = Bitmap.createBitmap(spriteFrame, FRAME_W * j, FRAME_H * i, FRAME_W, FRAME_H);
							// apply scale factor
							//currentSFrame = Bitmap.createScaledBitmap(currentSFrame, FRAME_W * SCALE_FACTOR, FRAME_H* SCALE_FACTOR, true);
							if(Logger)Log.e("RN","Animation Frame: "+currentFrame);
							publishProgress(currentSFrame);
							if(stopAnimator){
								break;
							}
							currframe=currentFrame;
							if (++currentFrame >= NB_FRAMES) {
								if(loopAnimator){
									i=0;
									j=0;
									currentFrame=0;
								}else break;
							}
							try {
								Thread.sleep(duration);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					return null;
			}

			protected void onProgressUpdate(Bitmap... SFrame) {
				if(Logger)Log.e("RN","AnimationWorking");
				if(ghandlerImage!=null){
					ghandlerImage.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
					ghandlerImage.setImage(SFrame[0]);
				}else {
					handlerImage.setImageBitmap(SFrame[0]);
				}
				Drawable drawable = new BitmapDrawable(SFrame[0]);
				onAnimationUpdate(drawable, currframe);
			}

			@Override
			protected void onPreExecute() {
				if(Logger)Log.e("RN","AnimationStart");
				AnimatorWorking=true;
				onAnimationStart();
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if(Logger)Log.e("RN","AnimationDone");
				AnimatorWorking=false;
				onAnimationFinish();
				super.onPostExecute(aVoid);
			}
		}


	}
	public class iFramesAnimatorManager extends iWorld{
		private HashMap<Integer,iFramesAnimator> animations= new HashMap<>();
		public void addAnimator(iFramesAnimator animator,int id){
			animations.put(id,animator);
		}
		public void clear(){animations.clear();}
		public void removeAnimator(int id){if(animations.get(id)!=null)animations.remove(id);}
		public void startAnimator(int id){Log.e("QAZ","ANIMATION ID: "+id+"");if(animations.get(id)!=null)animations.get(id).startAnimation();}
		public void stopAnimator(int id){if(animations.get(id)!=null)animations.get(id).stopAnimation();}
		public void updateAnimator(int id,iFramesAnimator animator) {
			animations.put(id,animator);}
		public boolean isRunning(int id){if(animations.get(id)!=null)return animations.get(id).isRunning();else return false;}

	}

}
