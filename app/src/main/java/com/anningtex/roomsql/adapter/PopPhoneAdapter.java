package com.anningtex.roomsql.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anningtex.roomsql.R;
import com.anningtex.roomsql.entriy.PhoneBean;

import java.util.List;

/**
 * @author : Song
 */
public class PopPhoneAdapter extends BaseAdapter {
    private List<PhoneBean> list;
    private Context context;

    public PopPhoneAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<PhoneBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<PhoneBean> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_phone_text, null);
            viewHolder.tvPop = view.findViewById(R.id.phone_number_text);
            viewHolder.tvPop.setTextColor(context.getResources().getColor(R.color.white));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvPop.setText(list.get(position).getName());
        return view;
    }

    class ViewHolder {
        TextView tvPop;
    }
}
