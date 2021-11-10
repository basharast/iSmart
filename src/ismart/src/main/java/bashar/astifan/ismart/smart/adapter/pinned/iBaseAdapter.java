package bashar.astifan.ismart.smart.adapter.pinned;

import bashar.astifan.ismart.smart.objects.ilistview.iListView.PinnedSectionedHeaderAdapter;
import bashar.astifan.ismart.smart.adapter.iSmartAdapter.iListHeader;
import android.widget.BaseAdapter;

public abstract class iBaseAdapter extends BaseAdapter implements PinnedSectionedHeaderAdapter {


	public iBaseAdapter() {
        super();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    public abstract  boolean isSectionHeader(int position);
    public abstract  iListHeader getSubHeader();
    public abstract int getSectionPosition(int position);


}
