package bashar.astifan.ismart.smart.adapter;

import bashar.astifan.ismart.smart.objects.ilistview.iHorizontalListView;
import bashar.astifan.ismart.smart.mobi.iGMS.iMarker;
import bashar.astifan.ismart.smart.mobi.iGMS.iRoute;
import bashar.astifan.ismart.smart.mobi.iGMS.iZoom;
import bashar.astifan.ismart.smart.mobi.iImageTools.iMask;
import bashar.astifan.ismart.Util.iMath;

import java.util.Arrays;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;

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
public class iSmartAdapter {
	private static int lcounter = 0;
	private static int ocounter = 0;
	public static boolean isLog = false;
	private String json_data=null;
	private String json_mtag=null;

	public void setJSONData(String jsonData){
		json_data=jsonData;
	}
	public String getJSONData(){
		return json_data;
	}

	public void setJSONMTag(String jsonMTAG){
		json_mtag=jsonMTAG;
	}
	public String getJSONMTag(){
		return json_mtag;
	}
	public static class iLayout {
		private int lid;
		private iObject[] objs = {};
		private boolean isHeader=false;
		public iLayout(int resId) {
			lcounter += 1;
			lid = resId;
		}
		public iLayout(int resId,boolean isHeader) {
			lcounter += 1;
			lid = resId;
			this.isHeader=isHeader;
		}
		public boolean isHeader(){
			return isHeader;
		}
		public int getLayout(){
			return lid;
		}
		public iObject[] getObjects(){
			return objs;
		}

		public void addObject(iObject object) {
			objs = append(objs, object);
			if (isLog)
				Log.d("Add Object", " Object Added");
		}

		public void addObject(iObject[] object) {
			for (int i = 0; i < object.length; i++) {
				objs = append(objs, object[i]);
			}
			if (isLog)Log.d("Add Objects", "All Objects Added");
		}
	}
	
	public static class iObject {
		private int oid;
		private int key;
		private String jsonTag=null;
		private iProperties properties;
		public iObject(int resId) {
			ocounter += 1;
			key = ((ocounter * 3 + lcounter * 2) + iMath.getRandom(73, 7)) + 1;
			oid = resId;
		}
		public iObject(int resId,String jsonTAG) {
			ocounter += 1;
			key = ((ocounter * 3 + lcounter * 2) + iMath.getRandom(73, 7)) + 1;
			oid = resId;
			jsonTag=jsonTAG;
		}
		public String getJSONTAG(){return jsonTag;}
		public void addProperties(iProperties properties){
			this.properties=properties;
		}
		public iProperties getProperties(){
			return this.properties;
		}
		public int getObjectKey(){
			return key;
		}

		public int getObjectId(){
			return oid;
		}
	}
	public static class iProperties{
		//Set Sources
		private boolean[] setEnable=null,setChecked=null,setSelected=null,setRquestFocus=null;
		private boolean removeAllViews=false;
		private String[] setHints=null,setText=null,setURLs=null;
		private Bitmap[] setBitmaps = null; 
		private int[] setResources = null;
		private View[] addView=null;
		private int[] setVisibilty=null;
		private String[] setTypeFace=null;
		private int[] setBackgroundResourse=null;
		private int setAnimationsResourse=0;
		private Animation setAnimation=null;
		private int valuesCount=0;
		private int valuesDefault=1;

		
		public int getCount(){
			return valuesDefault=valuesCount;
		}
		public void setText(String[] text) {
			setText = text;
			valuesDefault=valuesCount=setText.length;

		}
		public void setText(String text) {
			setText = new String[]{text};
			valuesDefault=valuesCount=setText.length;

		}
		public String[] getText(){
			return setText;
		}
		public void setEnable(boolean[] state) {
			setEnable=state;
			valuesDefault=valuesCount=setEnable.length;
		}
		public void setEnable(boolean state) {
			
			setEnable=new boolean[]{state};
			valuesDefault=valuesCount=setEnable.length;
			
		}
		public boolean[] getEnable(){
			return setEnable;
		}
		public void setVisible(int[] state) {
			setVisibilty=state;
			valuesDefault=valuesCount=setVisibilty.length;
		}
		public void setVisible(int state) {

			setVisibilty=new int[]{state};
			valuesDefault=valuesCount=setVisibilty.length;
			
		}
		public int[] getVisible(){
			return setVisibilty;
		}
		public void setChecked(boolean[] state) {
			setChecked=state;
			valuesDefault=valuesCount=setChecked.length;
		}
		public void setChecked(boolean state) {
			setChecked=new boolean[]{state};
			valuesDefault=valuesCount=setChecked.length;
		}
		public boolean[] getChecked(){
			return setChecked;
		}
		public void setSelected(boolean[] state) {
			setSelected=state;
			valuesDefault=valuesCount=setSelected.length;
		}
		public void setSelected(boolean state) {
			setSelected=new boolean[]{state};
			valuesDefault=valuesCount=setSelected.length;
		}
		public boolean[] getSelected(){
			return setSelected;
		}
		public void setHint(String[] hints) {
			setHints = hints;
			valuesDefault=valuesCount=setHints.length;
		}
		public void setHint(String hint) {
			setHints = new String[]{hint};
			valuesDefault=valuesCount=setHints.length;
		}
		public String[] getHint(){
			return setHints;
		}
		public void setSource(int[] resIds) {
			setResources = resIds;
			valuesDefault=valuesCount=setResources.length;
		}
		public void setSource(int resId) {
			setResources = new int[]{resId};
		}
		public int[] getResIdSource(){
			return setResources;
		}
		public void setSource(String[] urls) {
			this.setURLs = urls;
			valuesDefault=valuesCount=setURLs.length;
		}
		public void setSource(String urls) {
			this.setURLs = new String[]{urls};
			valuesDefault=valuesCount=setURLs.length;
		}
		public String[] getURLsSource(){
			return this.setURLs;
		}
		public void setSource(Bitmap[] bitmap) {
			setBitmaps = bitmap;
			valuesDefault=valuesCount=setBitmaps.length;
		}
		public void setSource(Bitmap bitmap) {
			setBitmaps = new Bitmap[]{bitmap};
			valuesDefault=valuesCount=setBitmaps.length;
		}
		public Bitmap[] getBitmapSource(){
			return setBitmaps;
		}
		public void setTypeface(String[] AssetsURIs) {
			setTypeFace = AssetsURIs;
			valuesDefault=valuesCount=setTypeFace.length;
		}
		public void setTypeface(String AssetsURIs) {
			setTypeFace = new String[]{AssetsURIs};
			valuesDefault=valuesCount=setTypeFace.length;
		}
		public String[] getTypeface(){
			return setTypeFace;
		}
		public void addView(View[] views){
			addView=views;
			valuesDefault=valuesCount=addView.length;
		}
		public void addView(View views){
			addView=new View[]{views};
			valuesDefault=valuesCount=addView.length;
		}
		public View[] getViews(){
			return addView;
		}
		public void removeAllViews(){
			removeAllViews=true;
			valuesCount=valuesDefault;
		}
		public boolean getremoveAllViews(){
			return removeAllViews;
		}
		public void setRequestFocus(boolean[] state){
			setRquestFocus=state;
			valuesDefault=valuesCount=setRquestFocus.length;
	        }
	    public void setRequestFocus(boolean state){
	    	setRquestFocus=new boolean[]{state};
	    	valuesDefault=valuesCount=setRquestFocus.length;
	        }
		public boolean[] getRequestFocus(){
			return setRquestFocus;
		}
	    public void setBackgroundResource(int[] resIds){
	    	setBackgroundResourse=resIds;
	    	valuesDefault=valuesCount=setBackgroundResourse.length;
	        }
	    public void setBackgroundResource(int resId){
	    	setBackgroundResourse=new int[]{resId};
	    	valuesDefault=valuesCount=setBackgroundResourse.length;
	        }
		public int[] getBackgroundResource(){
			return setBackgroundResourse;
		}
	    public void setAnimation(int animations){
	    	setAnimationsResourse=animations;
	    	valuesCount=valuesDefault;
        }
	    public int getResAnimations(){
			return setAnimationsResourse;
		}
        public void setAnimation(Animation anim) {
        	setAnimation=anim;
        	valuesCount=valuesDefault;
		}
        public Animation getAnimations(){
			return setAnimation;
		}
		
		//Set Events
		private OnClickListener onClick = null;
		private OnLongClickListener onLongClick = null;
		private OnCheckedChangeListener onCheckedChange = null;
		private MotionEvent setDispatchTouchEvent = null;
		private OnTouchListener onTouch=null;
		
		public void setOnClick(OnClickListener onclick) {
			onClick = onclick;
			valuesCount=valuesDefault;
		}
		public OnClickListener getOnClick(){
			return onClick;
		}
		public void setOnLongClick(OnLongClickListener onlongclick) {
			onLongClick = onlongclick;
			valuesCount=valuesDefault;
		}
		public OnLongClickListener getOnLongClick(){
			return onLongClick;
		}
		public void setOnCheckedChange(OnCheckedChangeListener oncheckedchange) {
			onCheckedChange = oncheckedchange;
			valuesCount=valuesDefault;
		}
		public OnCheckedChangeListener getOnCheckedChange(){
			return onCheckedChange;
		}
		public void dispatchTouchEvent(MotionEvent dispatchevent) {
			setDispatchTouchEvent = dispatchevent;
			valuesCount=valuesDefault;
		}
		public MotionEvent getdispatchTouchEvent(){
			return setDispatchTouchEvent;
		}
		public void setOnTouchListener(OnTouchListener ontouch) {
			onTouch=ontouch;
			valuesCount=valuesDefault;
			}
		public OnTouchListener getOnTouchListener(){
			return onTouch;
		}
		
		
		//static class EditText{
			
			//Set EditView Properties
			private InputFilter[][] setInputFillter=null;
			private TextWatcher setTextWatcher=null;
			private int[] setInputType=null;
			private String[] valuesHandler=null;
			
	        public void addTextChangedListener(TextWatcher textwatcher){
	        	setTextWatcher=textwatcher;
	        	valuesCount=valuesDefault;
	        }
	        public TextWatcher getTextChangedListener(){
				return setTextWatcher;
			}
	        public void setFillters(InputFilter[] fillters){
	        	setInputFillter=new InputFilter[][]{fillters};
	        	valuesCount=valuesDefault;
	        }
	        public void setFillters(InputFilter[][] fillters){
	        	setInputFillter=fillters;
	        	valuesDefault=valuesCount=setInputFillter.length;
	        }
	        public InputFilter[][] getFillters(){
				return setInputFillter;
			}
	        public void setInputType(int[] inputtype){
	        	setInputType=inputtype;
	        	valuesDefault=valuesCount=setInputType.length;
	        }
	        public void setInputType(int inputtype){
	        	setInputType=new int[]{inputtype};
	        	valuesDefault=valuesCount=setInputType.length;
	        }
	        public int[] getInputType(){
				return setInputType;
			}
	        public void setValuesHandler(String handler){
	        	valuesHandler=new String[]{handler};
	        	valuesDefault=valuesCount=valuesHandler.length;
	        }
	        public void setValuesHandler(String[] handler){
	        	valuesHandler=handler;
	        	valuesDefault=valuesCount=valuesHandler.length;
	        }
	        public String[] getValuesHandler(){
	        	return valuesHandler;
	        }
		//}
		
		
		//static class ProgressBar{
			//Set ProgressBar Properties
			private int[] setProgress=null;
			public void setProgress(int[] progress) {
				setProgress = progress;
				valuesDefault=valuesCount=setProgress.length;
			}
			public void setProgress(int progress) {
				setProgress = new int[]{progress};
				valuesDefault=valuesCount=setProgress.length;
			}
			 public int[] getProgress(){
					return setProgress;
				}
		//}
		
	//	static class ListView{
			//Set ListView Properties
			private ListAdapter[] setAdapters=null;
			
			public void setAdapter(ListAdapter[] adapter){
				setAdapters=adapter;
				valuesDefault=valuesCount=setAdapters.length;
				}
			public void setAdapter(ListAdapter adapter){
				setAdapters=new ListAdapter[]{adapter};
				valuesDefault=valuesCount=setAdapters.length;
			    }   
			public ListAdapter[] getAdapter(){
				return setAdapters;
			}
	//	}

	//	static class iSmarProperites{
			//iSmart Properties
			private iZoom[] izoom=null;
			private iMarker[] imarker=null;
			private iRoute[] idraw=null;
			private iRating rating=null;
			private iMask mask=null;	
			private PagerAdapter[] pagerImages=null;
			
			public void zoomInMap(iZoom[] zoomDate){
				izoom=zoomDate;
				valuesDefault=valuesCount=izoom.length;
			}
	        public void zoomInMap(iZoom zoomDate){
	        	izoom=new iZoom[]{zoomDate};
	        	valuesDefault=valuesCount=izoom.length;
			}
	        public iZoom[] getzoomInMap(){
				return izoom;
			}
	        public void addMarkerMap(iMarker[] markerData){
	        	imarker=markerData;
	        	valuesDefault=valuesCount=imarker.length;
	        }
	        public void addMarkerMap(iMarker markerData){
	        	imarker=new iMarker[]{markerData};
	        	valuesDefault=valuesCount=imarker.length;
	        }
	        public iMarker[] getMarkerMap(){
				return imarker;
			}
	        public void drawRouteMap(iRoute[] routeData){
	        	idraw=routeData;
	        	valuesDefault=valuesCount=idraw.length;
	        }
	        public void drawRouteMap(iRoute routeData){
	        	idraw=new iRoute[]{routeData};
	        	valuesDefault=valuesCount=idraw.length;
	        }
	        public iRoute[] getdrawRouteMap(){
				return idraw;
			}
	        public void setRating(iRating irating) {
	        	rating=irating;
	        	valuesCount=valuesDefault;
			}
	        public iRating getRating(){
				return rating;
			}
	        public void setMask(iMask imask) {
				mask=imask;
				valuesCount=valuesDefault;
			}
	        public iMask getMask(){
				return mask;
			}
	        public void setPagerAdapter(PagerAdapter[] ipager){
	        	pagerImages=ipager;
	        	valuesDefault=valuesCount=pagerImages.length;
	        }
	        public void setPagerAdapter(PagerAdapter ipager){
	        	pagerImages=new PagerAdapter[]{ipager};
	        	valuesDefault=valuesCount=pagerImages.length;
	        }
	        public PagerAdapter[] getPagerAdapter(){
	        	return pagerImages;
	        }
	//	}
		
	}


	public static class iOptions {
		int prb = 0;
		Bitmap ofi = null;
		Animation anm = null;
		int anim = 0;
		int time = 0;

		public void setProgressBar(int resId) {
			prb = resId;
		}
		public void setOfflineImage(Bitmap image) {
			ofi = image;
		}
		public void setFaildImage(Bitmap image) {
			ofi = image;
		}
		public void setAnimation(Animation animation) {
			anm = animation;
		}
		public void setAnimation(int animation, int time) {
			anim = animation;
			this.time = time;
		}
	}

	public static class iRuler {
		String[] rules = {};
		int[] index = {};

		public void AddRule(String whenfind, int tolayout) {
			rules = append(rules, whenfind);
			index = append(index, tolayout);
		}
	}
   public static class iSmartObjects{
	   public static int iMAP=7101;
   }
	private iOptions ioptions=null;
	private iRuler iruler=null;
	private String[] irules=null;
	private ListView ilist=null;
	private iHorizontalListView ihlist=null;
	private iLayout[] ilayouts=null;
	public void addOption(iOptions options){
		ioptions=options;
	}
	public void addRules(iRuler ruler,String[] rules){
		iruler=ruler;
		irules=rules;
	}
	private iListHeader SubHeader=null;
	public void addSubHeader(iListHeader subHeader){
		SubHeader=subHeader;
	}
	public void addList(ListView list){
		ilist=list;
	}
	public void addList(iHorizontalListView list){
		ihlist=list;
	}
	public void addLayouts(iLayout[] layouts){
		ilayouts=layouts;
	}
	public void addLayouts(iLayout layout){
		ilayouts=new iLayout[]{layout};
	}
	
	public ListAdapter getSmartAdapter(Context ctx,OnClickListener itemClick){
		return CreateAdapter(ctx, ilayouts, ilist, iruler, irules, ioptions, itemClick);
	}
    private String[] TreeMap;
	iListAdapter lst = new iListAdapter();
	public ListAdapter CreateAdapter(Context ctx, iLayout[] layouts,
			ListView list, iRuler rules, String[] dataRules,
			iOptions options, OnClickListener itemClick) {

		int tree_size=0;
		for (int i = 0; i < layouts.length; i++) {
			tree_size += layouts[i].getObjects()[0].getProperties().getCount();
			if (isLog)Log.d("Check Objects Type", "Done, Count: "+tree_size);
		}

		// ------------------------
		
		
		
		lst.lst = list;
		lst.setJSONData(json_data,json_mtag);
		if(ihlist!=null)lst.hlst=ihlist;
		if (isLog)
			Log.d("Generate Tree Map", "Generate Tree Map");
		//Tree Map Area
				TreeMap = new String[tree_size];
				if(iruler!=null){
				HashMap<Integer, Integer> real_index=new HashMap<Integer, Integer>();
				
				for (int i = 0; i < tree_size; i++) {
					int key=1;
					int map=0;
					/* | this will be for -> R.layout.LT_1 <- | */
					for (int j = 0; j < rules.rules.length; j++) {
						if (dataRules[i].contains(rules.rules[j])) {
							key=rules.index[j];
							map=rules.index[j] - 1;
							break;
						} else {
							map=0;
						}	
					}
					if(real_index.get(key)!=null){
						if (isLog)Log.d("", "Found Key ["+key+"]");
						real_index.put(key, real_index.get(key)+1);
					}else{
						if (isLog)Log.d("", "New Key ["+key+"]");
						real_index.put(key, 0);
					}
					TreeMap[i] = real_index.get(key)+":"+map+"#";
					if (isLog)Log.d("Generate Tree Map", TreeMap[i]);
				}
				}else{
					int counter=0;
					for(int j=0;j<layouts.length;j++){
						int count = layouts[j].getObjects()[0].getProperties().getCount();
						if (isLog)Log.d("Generate Tree Map", "Objects Number:"+count);
					for (int i = 0; i < count; i++) {
						TreeMap[counter] = i+":"+j+"#";
						counter+=1;
						if (isLog)Log.d("Generate Tree Map", TreeMap[j]);
					}
					}
				}
		if (isLog)
			Log.d("Generate Tree Map", "Done");
		//Tree Map Area
		lst.setSubHeader(SubHeader);
		lst.adapterLayouts=layouts;
		lst.setTreeMap(TreeMap);

		if(ioptions!=null){
		if (options.anim != 0) {
			lst.setAnimation(options.anim, options.time);
		} else if (options.anm != null) {
			lst.setAnimation(options.anm);
		}
		if (options.prb != 0) {
			lst.ProgressBarID = options.prb;
		}
		if (options.ofi != null) {
			lst.Offline_Image = options.ofi;
		}
		}
		
		if (isLog)
			Log.d("Successful Create", "Successful Create No Errors");
		return lst.creatAdvancedListAdapter(ctx, itemClick);
	}

	public void update() {
		lst.update();
	}

	
	public int getItemIndex(ListView lst, View v) {
		/* | Item OnClick Method | */
		return lst.getPositionForView(v);
	}

	public String[] getTreeMap(){
		return TreeMap;
	}
	public static int getRealindex(int position,String[] TreeMap){
		return Integer.parseInt(TreeMap[position].substring(0, TreeMap[position].lastIndexOf(":")));
	}
	public static int getLayoutIndex(int position,String[] TreeMap){
		int Sindex = TreeMap[position].indexOf(":");
		int Eindex = TreeMap[position].indexOf("#");
		String Lindex = TreeMap[position].substring(Sindex + 1,Eindex);
		return Integer.parseInt(Lindex.toString());
	}
	@SuppressLint("NewApi")
	static <T> int[] append(int[] ids2, int id) {
		final int N = ids2.length;
		if(android.os.Build.VERSION.SDK_INT > 8){
		ids2 = Arrays.copyOf(ids2, N + 1);
		ids2[N] = id;
		}else{
			int[] newSplitValues = new int[N+1];
			System.arraycopy(ids2, 0, newSplitValues, 0, ids2.length);
			newSplitValues[N]=id;
			ids2=newSplitValues;
		}
		
		return ids2;
	}
	@SuppressLint("NewApi")
	static <T> String[] append(String[] ids2, String id) {
		final int N = ids2.length;
		if(android.os.Build.VERSION.SDK_INT > 8){
		ids2 = Arrays.copyOf(ids2, N + 1);
		ids2[N] = id;
		}else{
			String[] newSplitValues = new String[N+1];
			System.arraycopy(ids2, 0, newSplitValues, 0, ids2.length);
			newSplitValues[N]=id;
			ids2=newSplitValues;
		}
		return ids2;
	}
	@SuppressLint("NewApi")
	static <T> iObject[] append(iObject[] ids2, iObject id) {
		final int N = ids2.length;
		if(android.os.Build.VERSION.SDK_INT > 8){
		ids2 = Arrays.copyOf(ids2, N + 1);
		ids2[N] = id;
		}else{
			iObject[] newSplitValues = new iObject[N+1];
			System.arraycopy(ids2, 0, newSplitValues, 0, ids2.length);
			newSplitValues[N]=id;
			ids2=newSplitValues;
		}
		return ids2;
	}


	public static class iSliderList{
		public iSliderList(ListView list){
			iList=list;
		}
		public iSliderList(iHorizontalListView list){
			iHList=list;
		}
		int fixerv=0;
		public void setFixerValue(int fixer){
			fixerv=fixer;
		}
		ListView iList=null;
		iHorizontalListView iHList=null;
		public void setScrollByPostion(boolean state){
			scrollByPosition=state;
		}
		public void setScrollSpeed(int speed){
			ScrollSpeed=speed;
		}
		public void setScrollDelay(int delay){
			Scroll_Delay=delay;
		}
		int Scroll_Delay=5000;
		int ScrollSpeed=10;
		boolean scrollByPosition=true;
		public void startSlider(){
			
			slider_handler.postDelayed(slider_run, scrollByPosition?Scroll_Delay:ScrollSpeed);
		}
		Handler slider_handler=new Handler();
		Runnable slider_run=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sliderAnimator();
			}
		};
		boolean scroll_reverese=false;
		private void sliderAnimator(){


		if(!scrollByPosition){
		int scroll_incr=1;
		if(scroll_reverese&&iHList.getFirstVisiblePosition()<=0){
			scroll_reverese=false;
		}
		if(iHList.getFirstVisiblePosition()>=iHList.getCount()-(fixerv+1)&&!scroll_reverese){
			scroll_reverese=true;
		}
		iHList.scrollBy(scroll_reverese?scroll_incr:-scroll_incr);
		}else{
			int currentPosition=iHList.getFirstVisiblePosition()+1;
			
			if(currentPosition>=iHList.getCount()-(fixerv))currentPosition=fixerv;
			
			iHList.setSelection(currentPosition);
		}
		startSlider();
		}
		public void stopSlider(){
			slider_handler.removeCallbacks(slider_run);
		}
	}
	public static class ListViewUtil {
		public static void setListViewHeightBasedOnChildren(ListView listView) {
			ListAdapter mAdapter = listView.getAdapter();

		    int totalHeight = 0;

		    for (int i = 0; i < mAdapter.getCount(); i++) {
		        View mView = mAdapter.getView(i, null, listView);

		        mView.measure(
		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),

		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

		        totalHeight += mView.getMeasuredHeight();
		        Log.w("HEIGHT" + i, String.valueOf(totalHeight));

		    }

		    ViewGroup.LayoutParams params = listView.getLayoutParams();
		    params.height = totalHeight
		            + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		    listView.setLayoutParams(params);
		    listView.requestLayout();
	    }
		
		
		public static void enable_ScrollView_Control(ListView lst){
			lst.setOnTouchListener(new OnTouchListener() {
		        // Setting on Touch Listener for handling the touch inside ScrollView
				@SuppressLint("ClickableViewAccessibility")
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					v.getParent().requestDisallowInterceptTouchEvent(true);
					return false;
				}
		    });
		}
		@SuppressLint("ClickableViewAccessibility")
		public static void enable_ScrollView_Control(iHorizontalListView lst){
			lst.setOnTouchListener(new OnTouchListener() {
		        // Setting on Touch Listener for handling the touch inside ScrollView
				@SuppressLint("ClickableViewAccessibility")
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					v.getParent().requestDisallowInterceptTouchEvent(true);
					return false;
				}
		    });
			
		}
		
		public static void activate_fastMode(ListView listview){
			listview.setCacheColorHint(Color.TRANSPARENT);
			listview.setFastScrollEnabled(true);
			listview.setScrollingCacheEnabled(false);
		}

	}
	public static void Logger(String log){
		if(isLog)Log.i("iLogger","Message: "+ log);
	}
	public static class iRating{
		private String[] rating_values={};
		private int[] ratingStars={};
		private int[] ratingIDS={};
		public String[] getRatingValues() {
			return rating_values;
		}
		public void setRatingValues(String[] ratingValues) {
			this.rating_values = ratingValues;
		}
		public int[] getRatingStars() {
			return ratingStars;
		}
		public void setRatingStars(int[] ratingStars) {
			this.ratingStars = ratingStars;
		}
		public int[] getRatingIDS() {
			return ratingIDS;
		}
		public void setRatingIDS(int[] ratingIDS) {
			this.ratingIDS = ratingIDS;
		}
	}
	public static class iListHeader{
		private  View SubHeader=null;
		private	double SubHeaderVisible=0.25;
		private	double SubHeaderSlideLess=0.30;
		private	double SubHeaderAlphaIncrement=1;
		
		public iListHeader(View header){
			setSubHeader(header);
		}
		public iListHeader(View header,double visibleArea,double slideLess,double alphaIncrement){
			setSubHeader(header);
			setSubHeaderVisible(visibleArea);
			setSubHeaderSlideLess(slideLess);
			setSubHeaderAlphaIncrement(alphaIncrement);
		}
		public View getSubHeader() {
			return SubHeader;
		}
		public void setSubHeader(View subHeader) {
			SubHeader = subHeader;
		}
		public double getSubHeaderVisible() {
			return SubHeaderVisible;
		}
		public void setSubHeaderVisible(double subHeaderVisible) {
			SubHeaderVisible = subHeaderVisible;
		}
		public double getSubHeaderSlideLess() {
			return SubHeaderSlideLess;
		}
		public void setSubHeaderSlideLess(double subHeaderSlideLess) {
			SubHeaderSlideLess = subHeaderSlideLess;
		}
		public double getSubHeaderAlphaIncrement() {
			return SubHeaderAlphaIncrement;
		}
		public void setSubHeaderAlphaIncrement(double subHeaderAlphaIncrement) {
			SubHeaderAlphaIncrement = subHeaderAlphaIncrement;
		}
	}
}
