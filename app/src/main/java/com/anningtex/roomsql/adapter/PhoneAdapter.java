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
 * @author Administrator
 */
public class PhoneAdapter extends BaseAdapter {
    private List<PhoneBean> data;
    private LayoutInflater layoutInflater;

    public PhoneAdapter(Context context, List<PhoneBean> data) {
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public PhoneBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_student, null);
            viewHolder = new ViewHolder();
            viewHolder.tvId = convertView.findViewById(R.id.tvId);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvNumber = convertView.findViewById(R.id.tvAge);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvId.setText(String.valueOf(data.get(position).longId));
        viewHolder.tvName.setText(data.get(position).name);
        viewHolder.tvNumber.setText(data.get(position).number);
        return convertView;
    }

    class ViewHolder {
        TextView tvId, tvName, tvNumber;
    }
}
