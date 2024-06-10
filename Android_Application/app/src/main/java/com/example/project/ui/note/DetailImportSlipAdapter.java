package com.example.project.ui.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.entities.DetailImportSlip;

import java.util.List;

public class DetailImportSlipAdapter extends ArrayAdapter<DetailImportSlip> {
    private Context context;
    private List<DetailImportSlip> detailImportSlips;

    public DetailImportSlipAdapter(@NonNull Context context, @NonNull List<DetailImportSlip> objects) {
        super(context, 0, objects);
        this.context = context;
        this.detailImportSlips = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_import_slip, parent, false);
        }

        DetailImportSlip detailImportSlip = detailImportSlips.get(position);

        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);

        tvBookName.setText(detailImportSlip.getBookName());
        tvQuantity.setText(String.valueOf(detailImportSlip.getQuantity()));

        return convertView;
    }
}

