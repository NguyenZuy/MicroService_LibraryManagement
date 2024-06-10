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
import com.example.project.entities.OrderSlip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderSlipAdapter extends ArrayAdapter<OrderSlip> {
    private Context context;
    private List<OrderSlip> orderSlipList;

    public OrderSlipAdapter(@NonNull Context context, @NonNull List<OrderSlip> objects) {
        super(context, 0, objects);
        this.context = context;
        this.orderSlipList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_slip, parent, false);
        }

        OrderSlip orderSlip = orderSlipList.get(position);

        String orderDateStr = orderSlip.getOrderDate();

        // Định dạng ban đầu của chuỗi ngày (giả sử là dd/MM/yyyy)
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Định dạng mong muốn
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String formattedDate = "";
        try {
            // Chuyển chuỗi ngày thành đối tượng Date
            Date date = originalFormat.parse(orderDateStr);

            // Chuyển đối tượng Date thành chuỗi với định dạng mong muốn
            formattedDate = targetFormat.format(date);

            // In ra chuỗi ngày đã được format
            System.out.println("Formatted Date: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView tvOrderDate = convertView.findViewById(R.id.tvOrderDate);
        TextView tvIdSupplier = convertView.findViewById(R.id.tvIdSupplier);

        tvOrderDate.setText(formattedDate);
        tvIdSupplier.setText(orderSlip.getSupplierName());

        return convertView;
    }
}
