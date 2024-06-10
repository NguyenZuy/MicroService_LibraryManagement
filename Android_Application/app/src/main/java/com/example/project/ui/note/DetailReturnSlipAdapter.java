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
import com.example.project.entities.DetailReturnSlip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailReturnSlipAdapter extends ArrayAdapter<DetailReturnSlip> {
    private Context context;
    private List<DetailReturnSlip> detailReturnSlipList;

    public DetailReturnSlipAdapter(Context context, List<DetailReturnSlip> detailReturnSlipList) {
        super(context, 0, detailReturnSlipList);
        this.context = context;
        this.detailReturnSlipList = detailReturnSlipList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_return_slip, parent, false);
        }

        DetailReturnSlip detailReturnSlip = detailReturnSlipList.get(position);

        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvBorrowDate = convertView.findViewById(R.id.tvBorrowDate);
        TextView tvReturnDate = convertView.findViewById(R.id.tvReturnDate);

        tvBookName.setText(detailReturnSlip.getBookName());
        tvQuantity.setText(String.valueOf(detailReturnSlip.getQuantity()));
        // Giả sử detailReturnSlip.getBorrowDate() và detailReturnSlip.getReturnDate() trả về chuỗi ngày tháng với định dạng ban đầu
        String borrowDateStr = detailReturnSlip.getBorrowDate();
        String returnDateStr = detailReturnSlip.getReturnDate();

// Định dạng ban đầu của chuỗi ngày tháng (giả sử là dd/MM/yyyy)
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

// Định dạng mong muốn (yyyy-MM-dd)
        SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Chuyển đổi chuỗi ngày tháng từ định dạng ban đầu thành đối tượng Date
            Date borrowDate = originalFormat.parse(borrowDateStr);
            Date returnDate = originalFormat.parse(returnDateStr);

            // Định dạng lại đối tượng Date thành chuỗi với định dạng mong muốn
            String formattedBorrowDateStr = desiredFormat.format(borrowDate);
            String formattedReturnDateStr = desiredFormat.format(returnDate);

            // Đặt chuỗi định dạng này vào TextView
            tvBorrowDate.setText(formattedBorrowDateStr);
            tvReturnDate.setText(formattedReturnDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có
        }


        return convertView;
    }
}

