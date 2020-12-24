package com.MainView.Personal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/2/17.
 * GridView 的adapter
 */
public class GvJJAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<GridListItem> graidListItems=null;
    private GridListCallBack gridListCallBack=null;

    public GvJJAdapter(Context mContext,ArrayList<GridListItem>  graidListItems,GridListCallBack gridListCallBack) {
        this.mContext = mContext;
        this.graidListItems=graidListItems;
        this.gridListCallBack=gridListCallBack;
    }

    @Override
    public int getCount() {
        if (graidListItems==null) return  0;
        return this.graidListItems.size();
    }

    @Override
    public Object getItem(int position) {
        if (graidListItems==null || graidListItems.size()< position ) return  null;
        return graidListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (graidListItems==null || graidListItems.size()< position ) return  0L;
        return graidListItems.get(position).getItemId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (graidListItems==null || graidListItems.size()< position ) return  null;
        ViewHOlder viewHOlder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_learnword_gridview_item, parent, false);
            viewHOlder = new ViewHOlder();
            viewHOlder.text = (TextView) convertView.findViewById(R.id.id_tv_ks);
            viewHOlder.image = (ImageView) convertView.findViewById(R.id.id_iv_bg);
            convertView.setTag(viewHOlder);

        } else {
            viewHOlder= (ViewHOlder) convertView.getTag();
        }
        viewHOlder.text.setText(graidListItems.get(position).getText());

        Drawable selectedDrawable = mContext.getDrawable(graidListItems.get(position).getSelectedImageId());
        Drawable normalDrawable = mContext.getDrawable(graidListItems.get(position).getNormalImageId());
        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed}, selectedDrawable);
        listDrawable.addState(new int[]{}, normalDrawable);
        viewHOlder.image.setBackground(listDrawable);
        // gridView 的点击事件
        viewHOlder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridListCallBack.callbackFunction(graidListItems.get(position).getCallbackId());
            }
        });
        return convertView;
    }

    public  class  ViewHOlder{
        TextView text;
        ImageView image;
    }
}