package com.anningtex.roomsql;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anningtex.roomsql.database.Student;

import java.util.List;

/**
 * @author Administrator
 */
public class StudentAdapter extends BaseAdapter {
    private List<Student> data;
    private LayoutInflater layoutInflater;

    public StudentAdapter(Context context, List<Student> data) {
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
    public Student getItem(int position) {
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
            viewHolder.tvAge = convertView.findViewById(R.id.tvAge);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvId.setText(String.valueOf(data.get(position).id));
        viewHolder.tvName.setText(data.get(position).name);
        viewHolder.tvAge.setText(data.get(position).age);
        return convertView;
    }

    class ViewHolder {
        TextView tvId, tvName, tvAge;
    }
}
