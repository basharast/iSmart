package bashar.astifan.ismart.smart.adapter.pinned;

import java.util.ArrayList;

import bashar.astifan.ismart.smart.mobi.iImageTools.iMask;
import bashar.astifan.ismart.transformation.CircleTransform;
import bashar.astifan.ismart.transformation.RoundedTransformation;
import bashar.astifan.ismart.viewpager.CirclePageIndicator;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class iSliderView extends PagerAdapter {
Context context;
private String URls[];
private int padding=0;
private iMask mask=null;
public iSliderView(Context context){
this.context=context;
}
public void setURLs(String[] URLs){
	this.URls=URLs;
}
public void setURLs(ArrayList<String> URLs){
	this.URls=URLs.toArray(new String[URLs.size()]);
}
@Override
public int getCount() {
return URls.length;
}
public void setPadding(int padding){
	this.padding=padding;
}
public void setPagerSettings(int indicerId,ViewPager viewPager,boolean setSnap){
	if(indicerId>0){ 
	  CirclePageIndicator  mIndicator = (CirclePageIndicator) ((Activity)context).findViewById(indicerId);
      mIndicator.setViewPager(viewPager);
     ((CirclePageIndicator) mIndicator).setSnap(setSnap);
	}
}
public void setMask(iMask mask){
	
}
@Override
public boolean isViewFromObject(View view, Object object) {
return view == ((ImageView) object);
}

@Override
public Object instantiateItem(ViewGroup container, int position) {
ImageView imageView = new ImageView(context);
imageView.setPadding(padding, padding, padding, padding);
imageView.setScaleType(ImageView.ScaleType.FIT_XY);
if(mask!=null){
	Picasso.with(context).load(URls[position]).transform(mask.isCircle()? new CircleTransform():new RoundedTransformation(mask.getMask(),mask.getMargin())).into(imageView);
}else Picasso.with(context).load(URls[position]).into(imageView);

((ViewPager) container).addView(imageView, 0);
return imageView;
}
 
@Override
public void destroyItem(ViewGroup container, int position, Object object) {
((ViewPager) container).removeView((ImageView) object);
}
}
