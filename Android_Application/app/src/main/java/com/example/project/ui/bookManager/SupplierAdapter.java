package com.example.project.ui.bookManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.entities.Supplier;

import java.util.List;

public class SupplierAdapter extends ArrayAdapter<Supplier> {
    private Context context;
    private List<Supplier> suppliers;

    public SupplierAdapter(@NonNull Context context, @NonNull List<Supplier> objects) {
        super(context, 0, objects);
        this.context = context;
        this.suppliers = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_supplier, parent, false);
        }

        Supplier supplier = suppliers.get(position);

        TextView tvName = convertView.findViewById(R.id.tvSupplierName);
        TextView tvEmail = convertView.findViewById(R.id.tvSupplierEmail);
        TextView tvPhoneNumber = convertView.findViewById(R.id.tvSupplierPhoneNumber);
        TextView tvAddress = convertView.findViewById(R.id.tvSupplierAddress);
        TextView tvStatus = convertView.findViewById(R.id.tvSupplierStatus);

        tvName.setText(supplier.getName());
        tvEmail.setText(supplier.getEmail());
        tvPhoneNumber.setText(supplier.getPhoneNumber());
        tvAddress.setText(supplier.getAddress());
        tvStatus.setText(supplier.getStatus());

        return convertView;
    }
}