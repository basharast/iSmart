package bashar.astifan.ismart.smart.adapter;

import java.io.File;

import bashar.astifan.ismart.smart.adapter.pinned.iBaseAdapter;
import bashar.astifan.ismart.smart.objects.ilistview.iHorizontalListView;
import bashar.astifan.ismart.smart.mobi.iGMS;
import bashar.astifan.ismart.smart.mobi.iJSON;
import bashar.astifan.ismart.Util.iMath;
import bashar.astifan.ismart.transformation.CircleTransform;
import bashar.astifan.ismart.transformation.RoundedTransformation;
import bashar.astifan.ismart.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.CheckBox;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
@SuppressLint("NewApi")
public class iListAdapter {
	
	iSmartAdapter.iLayout[] adapterLayouts={};
	public boolean isLog = iSmartAdapter.isLog;
	public Bitmap Offline_Image = null;
	Context ctx;
	iGMS gms;
	iHorizontalListView hlst = null;
	ListView lst = null;
	int mCurrentX = -1, mCurrentY = -1;
	private String jsonData;
	private String jsonmtag;
	public int ProgressBarID = 0;
	// ---------------
	private String[] TreeMap;
	private int animation = 0;
	private int animTime = 500;
	private Animation anim = null;
	private iSmartAdapter.iListHeader SubHeader=null;
	EfficientAdapterAdvanced adap;


	public EfficientAdapterAdvanced creatAdvancedListAdapter(Context context,
			OnClickListener OnclickEvent) {
		adap = new EfficientAdapterAdvanced(context, OnclickEvent);
		return adap;
	}

	public void setTreeMap(String[] TreeMap) {
		this.TreeMap = TreeMap;
	}

	public void setAnimation(int animationXML, int Time) {
		animation = animationXML;
		animTime = Time;
		this.anim = null;
		
	}
	public void setAnimation(Animation anim) {
		animation = 0;
		this.anim = anim;
	}
	public void setJSONData(String jsondata,String mtag){
		jsonData=jsondata;
		jsonmtag=mtag;
	}

	private class EfficientAdapterAdvanced extends iBaseAdapter implements
			Filterable {
		//final private int VIEW_TYPES = adapterLayouts.length;
		private LayoutInflater mInflater;
		private Context context;
		OnClickListener ItemClick;

		public EfficientAdapterAdvanced(Context context,
				OnClickListener OnclickEvent) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
			ctx = context;
			ItemClick = OnclickEvent;
			if (isLog)Log.d("Values State", "Values Setup Done ");
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return prepearView(context,mInflater, position, convertView, parent,ItemClick);
		}

		
		public Filter getFilter() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isSectionHeader(int position) {
			// TODO Auto-generated method stub
			return adapterLayouts[iSmartAdapter.getLayoutIndex(position, TreeMap)].isHeader();
		}



		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

				return TreeMap.length;

		}


		@Override
		public int getSectionPosition(int position) {
			// TODO Auto-generated method stub
			int li=iSmartAdapter.getLayoutIndex(position, TreeMap);
			for(int i=li;i>0;i--){
				if(adapterLayouts[i].isHeader()){
					return i;
				}
			}
			return -1;
		}

		@Override
		public iSmartAdapter.iListHeader getSubHeader() {
			// TODO Auto-generated method stub
			return SubHeader;
		}



	}
	private View prepearView(Context context,LayoutInflater mInflater, int position, View convertView,ViewGroup parent,OnClickListener ItemClick){
		if (lst != null) {
			if (mCurrentX == -1 & mCurrentY == -1 & lst != null) {
				lst.setOnScrollListener(new OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						// TODO Auto-generated method stub
						mCurrentX = view.getScrollX();
						mCurrentY = view.getScrollY();
					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						// TODO Auto-generated method stub

					}
				});
			}
		}
		if (hlst != null) {
			if (mCurrentX == -1 & mCurrentY == -1 & lst != null) {
				hlst.setOnScrollListener(new iHorizontalListView.OnScrollListener() {

					@Override
					public void onScrollStateChanged(
							iHorizontalListView view, int scrollState) {
						// TODO Auto-generated method stub
						mCurrentX = view.getScrollX();
						if (isLog)Log.e("", "dd" + mCurrentX);
						mCurrentY = view.getScrollY();
					}

					@Override
					public void onScroll(iHorizontalListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						// TODO Auto-generated method stub

					}

				});
			}
		}
		int LayoutIndex = 0;
		int real_index = position;
		if (TreeMap != null) {

				if (isLog)Log.d("", "Get Real Index");
				real_index = Integer.parseInt(TreeMap[position].substring(0, TreeMap[position].lastIndexOf(":")));
				LayoutIndex=iSmartAdapter.getLayoutIndex(position, TreeMap);
				if (isLog)Log.d("", "Real Index: "+real_index);

			if (isLog)Log.v(" Setup Layout", "" + real_index);
			try {
				convertView = mInflater.inflate(adapterLayouts[LayoutIndex].getLayout(),null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < adapterLayouts[LayoutIndex].getObjects().length; i++) {
				if (isLog)Log.d("", "Get View");
				View view=(View)convertView.findViewById(adapterLayouts[LayoutIndex].getObjects()[i].getObjectId());
				if (isLog)Log.d("", "Assign Values To View");
				setupView(view, LayoutIndex, i, real_index);
			}

			if (ItemClick != null)
				convertView.setOnClickListener(ItemClick);
			// convertView.setTag(holder);

		}

			if (!(animation == 0)) {
				Animation fadin = AnimationUtils.loadAnimation(context,
						animation);
				fadin.setDuration(animTime);
				convertView.setAnimation(fadin);
			} else if (anim != null) {
				convertView.setAnimation(anim);
			}
			return convertView;
	}
	private void setupView(View view, int LayoutIndex, int i,
			final int position) {
		
		int OID=adapterLayouts[LayoutIndex].getObjects()[i].getObjectId();
		final iSmartAdapter.iProperties pr=adapterLayouts[LayoutIndex].getObjects()[i].getProperties();
		final String JSONTAG=adapterLayouts[LayoutIndex].getObjects()[i].getJSONTAG();
		if(JSONTAG!=null){
			String value=new iJSON().get_json_object_data(jsonData,jsonmtag).get(JSONTAG).get(position);
			if (view instanceof TextView) ((TextView) view).setText(value);
			if (view instanceof EditText) ((EditText) view).setText(value);
			if (view instanceof ImageView) Picasso.with(ctx).load(value).into(((ImageView)view));
		}else{
		//Remove All Views
		if(pr.getremoveAllViews()){
			if(view instanceof RelativeLayout){
			((RelativeLayout)view).removeAllViews();
			}
		}
		//Set Adapter
		if(pr.getAdapter()!=null){
			if(view instanceof ListView)((ListView)view).setAdapter(pr.getAdapter()[position]);
			if(view instanceof iHorizontalListView)((iHorizontalListView)view).setAdapter(pr.getAdapter()[position]);
		}
		//Set Animations
		if(pr.getAnimations()!=null){
			view.setAnimation(pr.getAnimations());
		}
		//Set Background
		if(pr.getBackgroundResource()!=null){
			view.setBackground(((Activity)ctx).getResources().getDrawable(pr.getBackgroundResource()[position]));
		}
		//Set Bitmap Source
		if(pr.getBitmapSource()!=null){
			if(view instanceof ImageView)((ImageView)view).setImageBitmap(pr.getBitmapSource()[position]);
		}
		//Set Checked
		if(pr.getChecked()!=null){
			if(view instanceof CheckBox)((CheckBox)view).setChecked(pr.getChecked()[position]);
		}

		//Set dispatchTouchEvent
		if(pr.getdispatchTouchEvent()!=null){
			view.dispatchTouchEvent(pr.getdispatchTouchEvent());
		}
		//Set Enable
		if(pr.getEnable()!=null){
			view.setEnabled(pr.getEnable()[position]);
		}
		//Set Filters
		if(pr.getFillters()!=null){
			if(view instanceof EditText)((EditText)view).setFilters(pr.getFillters()[position]);
		}
		//Set Hints
		if(pr.getHint()!=null){
			if(view instanceof EditText)((EditText)view).setHint(pr.getHint()[position]);
			if(view instanceof TextView)((TextView)view).setHint(pr.getHint()[position]);
		}
		//Set Input Type
		if(pr.getInputType()!=null){
			if(view instanceof EditText)
				{
				((EditText)view).setInputType(pr.getInputType()[position]);
				if(pr.getInputType()[position]==InputType.TYPE_TEXT_VARIATION_PASSWORD)((EditText)view).setTransformationMethod(new PasswordTransformationMethod());
				}
		}
		//Set Mask
		if(pr.getMask()!=null){
			
		}
		// Set OnChangeClick
		if(pr.getOnCheckedChange()!=null){
			if(view instanceof CheckBox)((CheckBox)view).setOnCheckedChangeListener(pr.getOnCheckedChange());
		}
		//Set OnClick
		if(pr.getOnClick()!=null){
			view.setOnClickListener(pr.getOnClick());
		}
		//Set OnLongClick
		if(pr.getOnLongClick()!=null){
			view.setOnLongClickListener(pr.getOnLongClick());
		}
		//Set OnTouchListener
		if(pr.getOnTouchListener()!=null){
			view.setOnTouchListener(pr.getOnTouchListener());
		}
		//Set Progress Value
		if(pr.getProgress()!=null){
			if(view instanceof ProgressBar){
			((ProgressBar)view).setProgress(pr.getProgress()[position]);
			}
		}
		
		//Set Resource Animation
		if(pr.getResAnimations()!=0){
			Animation anim = AnimationUtils.loadAnimation(ctx,pr.getResAnimations());
			anim.setDuration(animTime);
			view.setAnimation(anim);
		}
		//Set Resource Source
		if(pr.getResIdSource()!=null){
			if(view instanceof ImageView){

				Picasso.with(ctx).load(pr.getResIdSource()[position]).into(((ImageView) view));
				((ImageView)view).setImageResource(pr.getResIdSource()[position]);

			}
			
		}
		//Set Selected
		if(pr.getSelected()!=null){
			view.setSelected(pr.getSelected()[position]);
		}
		
		//Set Text
		if(pr.getText()!=null){
				if (view instanceof TextView) ((TextView) view).setText(pr.getText()[position]);
				if (view instanceof EditText) ((EditText) view).setText(pr.getText()[position]);
		}
		//Set TextChangedListener
		if(pr.getTextChangedListener()!=null){
			if(view instanceof EditText)((EditText)view).addTextChangedListener(pr.getTextChangedListener());
		}
	
		if(pr.getValuesHandler()!=null){
			((EditText)view).addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					pr.getValuesHandler()[position]=s.toString();
				}
			});
		}
		//Set Type Face
		if(pr.getTypeface()!=null){
			String assets=pr.getTypeface()[position>=pr.getTypeface().length?0:position];
			Typeface type = Typeface.createFromAsset(ctx.getAssets(),assets);
			if(view instanceof EditText)((EditText)view).setTypeface(type);
		}
		//Set URLs Source
		if(pr.getURLsSource()!=null){
			String u = pr.getURLsSource()[position];
			if(view instanceof ImageView){
			if (pr.getMask()!=null) {
			
				if(pr.getMask().isCircle()){
					Picasso.with(ctx).load(u).transform(new CircleTransform()).into(((ImageView)view));
				}else if(pr.getMask().getMargin()>0) {
					Picasso.with(ctx).load(u).transform(new RoundedTransformation(pr.getMask().getMargin(),0)).into(((ImageView)view));
				}
					else{
				Picasso.with(ctx).load(u).transform(new RoundedTransformation(pr.getMask().getMask(), pr.getMask().getMargin())).into(((ImageView)view));
				}
			}else{
				if(u.contains("sdcard")){
					Picasso.with(ctx).load(new File(u)).into(((ImageView)view));
				}else{
					Picasso.with(ctx).load(u).into(((ImageView)view));
				}
			}
			}
			if(view instanceof WebView)((WebView)view).loadUrl(u);
		}
		//Set New Views
		if(pr.getViews()!=null){
			if(view instanceof RelativeLayout)((RelativeLayout)view).addView(pr.getViews()[position]);
		}
		//Set Visible
		if(pr.getVisible()!=null){
			view.setVisibility(pr.getVisible()[position]);
		}
		//Set RequestFocus
		if(pr.getRequestFocus()!=null){
			if(pr.getRequestFocus()[position]){
				view.requestFocus();
				InputMethodManager imm = (InputMethodManager) ((Activity)ctx).getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
			}else{
				view.clearFocus();
			}
		}
		//Set iRating 
		if(pr.getRating()!=null){
			float rating=Float.valueOf(pr.getRating().getRatingValues()[position]);
			if(view instanceof RatingBar){
				((RatingBar)view).setRating(rating);
			}else {
				int star_full=pr.getRating().getRatingStars()[2];
				int star_half=pr.getRating().getRatingStars()[1];
				int star_empty=pr.getRating().getRatingStars()[0];
				int final_result=star_empty;
				if (view.getId() == pr.getRating().getRatingIDS()[0]) {
					final_result = rating >= 1 ? star_full : rating >= 0.2 ? star_half : star_empty;
				} else if (view.getId() == pr.getRating().getRatingIDS()[1]) {
					final_result = rating >= 2 ? star_full : rating >= 1.2 ? star_half : star_empty;
				} else if (view.getId() == pr.getRating().getRatingIDS()[2]) {
					final_result = rating >= 3 ? star_full : rating >= 2.2 ? star_half : star_empty;
				} else if (view.getId() == pr.getRating().getRatingIDS()[3]) {
					final_result = rating >= 4 ? star_full : rating >= 3.2 ? star_half : star_empty;
				} else if (view.getId() == pr.getRating().getRatingIDS()[4]) {
					final_result = rating >= 5 ? star_full : rating >= 4.2 ? star_half : star_empty;
				}
				Picasso.with(ctx).load(final_result).into(((ImageView) view));
			}
		}
		//Set Images Pager
		if(pr.getPagerAdapter()!=null){
			if(view instanceof ViewPager){
				((ViewPager)view).setAdapter(pr.getPagerAdapter()[position]);
			}
		}
			//Set iMap
			if(OID==iSmartAdapter.iSmartObjects.iMAP){
				try {
					view = new iMap(ctx, LayoutIndex, i,position,pr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		

	}


	void update() {
		/** Restore: **/
		if (mCurrentX == -1 & mCurrentY == -1 & lst != null) {
			adap.notifyDataSetInvalidated();
			if (lst != null)
				lst.scrollTo(mCurrentX, mCurrentY);
			if (hlst != null)
				hlst.scrollTo(mCurrentX, mCurrentY);
		} else {
			adap.notifyDataSetChanged();
		}
	}


	public void setSubHeader(iSmartAdapter.iListHeader subHeader) {
		SubHeader = subHeader;
	}

	@SuppressLint("NewApi")
	public class iMap extends FrameLayout {

		public int myGeneratedFrameLayoutId;
		iSmartAdapter.iProperties pr;
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@SuppressLint({ "NewApi", "InflateParams" })
		public iMap(Context context, int LayoutIndex, int i, int position,iSmartAdapter.iProperties properties) {
			super(context);
			gms = new iGMS(context);
			pr=properties;
			myGeneratedFrameLayoutId = iMath.getRandom(10101225, 25486);

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			FrameLayout view = (FrameLayout) inflater.inflate(
					R.layout.act_map_layer, null);
			FrameLayout frame = new FrameLayout(context);
			frame.setId(12);

			int height = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 200, ((Activity)context).getResources()
							.getDisplayMetrics());
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, height);
			frame.setLayoutParams(layoutParams);

			view.addView(frame);

			GoogleMapOptions options = new GoogleMapOptions();
			options.liteMode(true);
	        options.zoomGesturesEnabled(false);
	        options.scrollGesturesEnabled(false);
	        options.compassEnabled(false);
			MapFragment mapFrag = MapFragment.newInstance(options);
			mapFrag.getMapAsync(new MyMapCallback(LayoutIndex, i, position));

			android.app.FragmentManager fm = ((Activity) context)
					.getFragmentManager();
			fm.beginTransaction().add(frame.getId(), mapFrag).commit();

			addView(view);
		}

		public class MyMapCallback implements OnMapReadyCallback {

			int LayoutIndex, i, position;

			MyMapCallback(int iLayoutIndex, int ii, int iposition) {
				LayoutIndex = iLayoutIndex;
				i = ii;
				position = iposition;
			}

			@Override
			public void onMapReady(GoogleMap googleMap) {
				googleMap.getUiSettings().setMapToolbarEnabled(true);
						if (pr.getdrawRouteMap()!=null) {
							// zoomIn
							gms.drawGoogleRoute(googleMap, pr.getdrawRouteMap()[position].getRouteData(),
									pr.getdrawRouteMap()[position].getRouteColor());
						} 
						if (pr.getMarkerMap()!=null) {
							// addMarker
							gms.addMarker(googleMap, pr.getMarkerMap()[position].getLocation(),
									pr.getMarkerMap()[position]
											.getTitle(), pr.getMarkerMap()[position]
											.getDetails(), pr.getMarkerMap()[position]
											.getIcon());
						} 
						if (pr.getzoomInMap()!=null) {
							// darwRoute
							gms.mapZoom(googleMap,
									pr.getzoomInMap()[position]
											.getLocation(), pr.getzoomInMap()[position]
											.getZoomFrom(), pr.getzoomInMap()[position]
											.getZoomTo());

						}
					
			}
		}
	}
}
