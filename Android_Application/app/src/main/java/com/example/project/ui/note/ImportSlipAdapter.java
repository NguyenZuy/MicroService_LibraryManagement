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
import com.example.project.entities.ImportSlip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImportSlipAdapter extends ArrayAdapter<ImportSlip> {
    private Context context;
    private List<ImportSlip> importSlips;

    public ImportSlipAdapter(@NonNull Context context, @NonNull List<ImportSlip> objects) {
        super(context, 0, objects);
        this.context = context;
        this.importSlips = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_import_slip, parent, false);
        }

        ImportSlip importSlip = importSlips.get(position);

        TextView tvId = convertView.findViewById(R.id.tvImportSlipId);
        TextView tvDate = convertView.findViewById(R.id.tvImportSlipDate);
        TextView tvStaff = convertView.findViewById(R.id.tvImportSlipStaff);
        TextView tvSupplier = convertView.findViewById(R.id.tvImportSlipSupplier);

        tvId.setText(String.valueOf(importSlip.getId()));

        String importDateStr = importSlip.getImportDate();

        // Define the date format that matches the input date string
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // e.g., "dd/MM/yyyy"
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the input date string to a Date object
            Date importDate = inputFormat.parse(importDateStr);

            // Format the Date object to the desired output format
            String formattedDate = outputFormat.format(importDate);

            // Set the formatted date to the TextView
            tvDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception, e.g., show an error message
        }
        tvSupplier.setText(importSlip.getSupplierName());

        return convertView;
    }
}
