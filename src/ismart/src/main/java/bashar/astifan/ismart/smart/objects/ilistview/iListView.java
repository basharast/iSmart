package bashar.astifan.ismart.smart.objects.ilistview;

import bashar.astifan.ismart.smart.adapter.iSmartAdapter.iListHeader;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class iListView extends ListView implements OnScrollListener{


	public iListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		super.setOnScrollListener(this);
		
	}
	public iListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnScrollListener(this);
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		  mOnScrollListener = l;
	}
	
	//Pinned Area
	private OnScrollListener mOnScrollListener;

    public static interface PinnedSectionedHeaderAdapter {
        public boolean isSectionHeader(int position);
        public View getView(int position, View convertView, ViewGroup parent);
        public int getCount();
        public  iListHeader getSubHeader();
        public int getSectionPosition(int position);

    }

    private PinnedSectionedHeaderAdapter mAdapter;
    private View mCurrentHeader;
    private float mHeaderOffset;
    private int mCurrentSection = 0;
    private int mFirstSection = -1;
    private boolean mShouldPin = true;
    private int mWidthMode;
    private int mHeightMode;

    public iListView(Context context) {
        super(context);
        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mCurrentHeader = null;
        mAdapter = (PinnedSectionedHeaderAdapter) adapter;
        super.setAdapter(adapter);
    }
    
    public void setPinHeaders(boolean shouldPin) {
        mShouldPin = shouldPin;
    }
    
    int indexEscape=1;
    View iheader;
	int minHeader=0;
	
    View SubHeader=null;
	double SubHeaderVisible=0.25;
	double SubHeaderSlideLess=0.30;
	double SubHeaderAlphaIncrement=1;
	int iHeaderOffset=0;
    @SuppressLint("NewApi") @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
       
    	
		 if(mAdapter!=null&&mAdapter.getSubHeader()!=null){
		 if(iheader==null){
			 iheader = getChildAt(0);
			 SubHeader=mAdapter.getSubHeader().getSubHeader();
			 SubHeaderVisible=mAdapter.getSubHeader().getSubHeaderVisible();
			 SubHeaderSlideLess=mAdapter.getSubHeader().getSubHeaderSlideLess();
			 SubHeaderAlphaIncrement=mAdapter.getSubHeader().getSubHeaderAlphaIncrement();
			 
		 }
		 
		 if (iheader != null) {
		 Integer scrollY = getScrollY(view);
		 minHeader=(int) (iheader.getHeight()*SubHeaderVisible);
		 iheader.setVisibility(VISIBLE);
		 iHeaderOffset=Math.max(0, scrollY + minHeader);

		 if((iheader.getTop()*-1)<((iheader.getHeight()-minHeader)))
		 iheader.setTranslationY((float) (Math.max(0, iHeaderOffset-(iheader.getTop()*SubHeaderSlideLess))));
		  // float offset = 1 - Math.max((float) (-minHeader - scrollY) / -minHeader, 0f);
		 }
		 }
		 
	   
    	
		 int FVI=firstVisibleItem;
    	if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (mAdapter == null || mAdapter.getCount() == 0 || !mShouldPin ||(firstVisibleItem < getHeaderViewsCount())) {
            mCurrentHeader = null;
            mHeaderOffset = 0.0f;
            for (int i = FVI; i < firstVisibleItem + visibleItemCount; i++) {
                View header = getChildAt(i);
                if (header != null) {
                    header.setVisibility(VISIBLE);
                }
            }
            return;
        }

             FVI -= getHeaderViewsCount();
        	 boolean isHeader = mAdapter.isSectionHeader(FVI);
        	 if(isHeader){
             mCurrentHeader = getSectionHeaderView(FVI, mCurrentHeader);
             ensurePinnedHeaderLayout(mCurrentHeader);
             mHeaderOffset = 0.0f;
        	 }
             for (int i = FVI; i < firstVisibleItem + visibleItemCount; i++) {
            	 if(i>=mAdapter.getCount())break;
             if(mAdapter.isSectionHeader(i)){
             if(mCurrentHeader!=null){
             View header = getChildAt(i-FVI);
             if(header!=null){
             float headerTop = header.getTop();
             float pinnedHeaderHeight = mCurrentHeader.getMeasuredHeight();
             header.setVisibility(VISIBLE);
             if (pinnedHeaderHeight >= headerTop && headerTop > 0) {
                 mHeaderOffset = headerTop - header.getHeight();
             } else if (headerTop <= 0) {
                 header.setVisibility(INVISIBLE);
             }
             }
             }
             }
             }
             invalidate();
             
        
    }
   
	public int getScrollY(AbsListView view)
	{
	    View c = view.getChildAt(1);

	    if (c == null)
	        return 0;

	    int firstVisiblePosition = view.getFirstVisiblePosition();
	    int top = c.getTop();

	    int headerHeight = 0;
	    if (firstVisiblePosition >= 1)
	        headerHeight = c.getHeight();

	    return -top + firstVisiblePosition * c.getHeight() + headerHeight;
	}
	
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    private View getSectionHeaderView(int position, View oldView) {
        boolean shouldLayout = position != mCurrentSection || oldView == null;

        View view =  mAdapter.getView(position, oldView, this);
        if (shouldLayout) {
            // a new section, thus a new header. We should lay it out again
            ensurePinnedHeaderLayout(view);
            if(mFirstSection==-1)mFirstSection=position;
            mCurrentSection = position;
        }
        return view;
    }

    private void ensurePinnedHeaderLayout(View header) {
        if (header.isLayoutRequested()) {
            int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), mWidthMode);
            
            int heightSpec;
            ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
            if (layoutParams != null && layoutParams.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            } else {
                heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            header.measure(widthSpec, heightSpec);
            header.layout(0,0, header.getMeasuredWidth(), header.getMeasuredHeight());
        }
    }
    
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        
		 if(mAdapter != null&&SubHeader!=null&&iheader!=null){

			 int saveCount = canvas.save();
			 
			// SubHeader.setAlpha((float) ((iheader.getTop()*SubHeaderAlphaIncrement)*-1));
			 canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(),  Math.min((int) ((iheader.getTop()*SubHeaderAlphaIncrement)*-1), 255), Canvas.ALL_SAVE_FLAG);
			 if((iheader.getTop()*-1)<((iheader.getHeight()-minHeader)))
				 canvas.translate(0, iheader.getTop());
			 else  canvas.translate(0, -(iheader.getHeight()-minHeader));
			 
			 SubHeader.draw(canvas);
			 canvas.restoreToCount(saveCount);
		 }else{
			// Log.e("", "SUB HEADER NULL");
		 }
		 
        if (mAdapter == null || !mShouldPin || mCurrentHeader == null)
            return;
        
        int saveCount = canvas.save();
        canvas.translate(0, mHeaderOffset);
        canvas.clipRect(0, 0, getWidth(), mCurrentHeader.getMeasuredHeight()); // needed
        // for
        // <
        // HONEYCOMB
        mCurrentHeader.draw(canvas);
        canvas.restoreToCount(saveCount);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        setmHeightMode(MeasureSpec.getMode(heightMeasureSpec));
    }

    public void setOnItemClickListener(iListView.OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }
	public int getmHeightMode() {
		return mHeightMode;
	}
	public void setmHeightMode(int mHeightMode) {
		this.mHeightMode = mHeightMode;
	}

}
