package com.example.project.ui.note;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.entities.DetailLoanSlip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailLoanSlipAdapter extends ArrayAdapter<DetailLoanSlip> {
    private Context context;
    private List<DetailLoanSlip> detailLoanSlipList;
    private SparseBooleanArray selectedItems;

    public DetailLoanSlipAdapter(Context context, List<DetailLoanSlip> detailLoanSlipList) {
        super(context, 0, detailLoanSlipList);
        this.context = context;
        this.detailLoanSlipList = detailLoanSlipList;
        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_loan_slip, parent, false);
        }

        DetailLoanSlip detailLoanSlip = detailLoanSlipList.get(position);

        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvBorrowDate = convertView.findViewById(R.id.tvBorrowDate);
        TextView tvReturnDate = convertView.findViewById(R.id.tvReturnDate);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);

        tvBookName.setText(detailLoanSlip.getBookName());
        tvQuantity.setText(String.valueOf(detailLoanSlip.getQuantity()));
        String borrowDateStr = detailLoanSlip.getBorrowDate();
        String returnDateStr = detailLoanSlip.getReturnDate();

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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

        // Kiểm tra trạng thái và hiển thị hoặc ẩn CheckBox
        if ("returned".equalsIgnoreCase(detailLoanSlip.getStatus())) {
            checkBox.setVisibility(View.GONE);
        } else {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setTag(position);
            checkBox.setChecked(selectedItems.get(position, false));

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (int) buttonView.getTag();
                    if (isChecked) {
                        selectedItems.put(position, true);
                    } else {
                        selectedItems.delete(position);
                    }
                }
            });
        }

        return convertView;
    }

    public List<DetailLoanSlip> getSelectedDetailLoanSlips() {
        List<DetailLoanSlip> selectedList = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            int key = selectedItems.keyAt(i);
            if (selectedItems.get(key)) {
                selectedList.add(detailLoanSlipList.get(key));
            }
        }
        return selectedList;
    }
}



