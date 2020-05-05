package com.example.testproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.testproject.R;
import com.example.testproject.model.Item;
import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList data;

    public ItemAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView name, description, age;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder ;
        Item currentItem = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.name =  convertView.findViewById(R.id.name);
            holder.description = convertView.findViewById(R.id.description);
            holder.age = convertView.findViewById(R.id.age);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        assert currentItem != null;
        holder.name.setText(currentItem.getName());
        holder.description.setText(currentItem.getDescription());
        holder.age.setText(String.valueOf(currentItem.getAge()));
        return convertView;
    }

}
