package com.example.project.ui.bookManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.entities.Category;
import com.example.project.entities.Publisher;

import java.util.List;

public class PublisherAdapter extends ArrayAdapter<Publisher> {

    private Context context;
    private List<Publisher> publishers;
    public PublisherAdapter(Context context, List<Publisher> publishers) {
        super(context, 0, publishers);
        this.context = context;
        this.publishers = publishers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.publisher_item, parent, false);
        }
        Publisher publisher = publishers.get(position);

        TextView tvId = convertView.findViewById(R.id.tvPublisherId);
        TextView tvName = convertView.findViewById(R.id.tvPublisherName);

        tvId.setText(String.valueOf(publisher.getId()));
        tvName.setText(publisher.getPublisherName());

        return convertView;
    }
}