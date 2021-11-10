package bashar.astifan.ismart.smart.adapter.pinned;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bashar.astifan.ismart.R;


public class iSliderAdapter extends FragmentPagerAdapter {

	  protected Context mContext;
	  
	    public iSliderAdapter(FragmentManager fm, Context context) {
	        super(fm);
	        mContext = context;
	    }
	 
	    @Override
	    // This method returns the fragment associated with
	    // the specified position.
	    //
	    // It is called when the Adapter needs a fragment
	    // and it does not exists.
	    public Fragment getItem(int position) {
	 
	        // Create fragment object
	        Fragment fragment = new DemoFragment();
	 
	        // Attach some data to it that we'll
	        // use to populate our fragment layouts
	        Bundle args = new Bundle();
	        args.putInt("page_position", position + 1);
	 
	        // Set the arguments on the fragment
	        // that will be fetched in DemoFragment@onCreateView
	        fragment.setArguments(args);
	 
	        return fragment;
	    }
	 
	    @Override
	    public int getCount() {
	        return 3;
	    }
	    @SuppressLint("ValidFragment")
		public class DemoFragment extends Fragment {
	    	 
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	            // Inflate the layout resource that'll be returned
	            View rootView = inflater.inflate(R.layout.lst_contact_info_left, container, false);
	     
	            // Get the arguments that was supplied when
	            // the fragment was instantiated in the
	            // CustomPagerAdapter
	            Bundle args = getArguments();
	            ((TextView) rootView.findViewById(R.id.txt_info)).setText("Page " + args.getInt("page_position"));
	     
	            return rootView;
	        }
	    }
}
