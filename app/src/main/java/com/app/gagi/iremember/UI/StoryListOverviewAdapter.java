package com.app.gagi.iremember.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gagi.iremember.Common.StoryListOverviewItem;
import com.app.gagi.iremember.Common.Utils;
import com.app.gagi.iremember.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igaglioti on 10/03/14.
 */
public class StoryListOverviewAdapter  extends BaseAdapter{
    List<StoryListOverviewItem> mStoryLisOverviewItems;
    List<StoryListOverviewItem> mOriginalStoryLisOverviewItems;

    public StoryListOverviewAdapter()
    {
        mStoryLisOverviewItems = new ArrayList<StoryListOverviewItem>();
        mOriginalStoryLisOverviewItems = new ArrayList<StoryListOverviewItem>();
    }

    public void addElement(StoryListOverviewItem item)
    {
        this.mOriginalStoryLisOverviewItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mOriginalStoryLisOverviewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mOriginalStoryLisOverviewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mOriginalStoryLisOverviewItems.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StoryListOverviewItem item = (StoryListOverviewItem)this.getItem(position);
        LayoutInflater inflater = (LayoutInflater)parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout itemLayout = (RelativeLayout)inflater.inflate(R.layout.story_list_fragment_item,null);

        ((TextView)itemLayout.findViewById(R.id.textView)).setText(item.getItemName());
        ((ImageView)itemLayout.findViewById(R.id.imageViewThumbail)).setImageBitmap(Utils.getInstance().
                getScaledImageBitmap(parent.getContext(),item.getPhotoPath(), Double.valueOf(parent.getResources().getString(R.string.scaleSizeThumbail))));
        ((ImageView)itemLayout.findViewById(R.id.imageViewThumbail)).setRotation(270);
        return itemLayout;
    }
}
