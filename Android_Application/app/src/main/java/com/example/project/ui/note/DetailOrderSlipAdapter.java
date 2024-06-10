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
import com.example.project.entities.DetailOrderSlip;

import java.util.ArrayList;
import java.util.List;

public class DetailOrderSlipAdapter extends ArrayAdapter<DetailOrderSlip> {
    private Context context;
    private List<DetailOrderSlip> detailOrderSlipList;
    private SparseBooleanArray selectedItems;

    public DetailOrderSlipAdapter(Context context, List<DetailOrderSlip> detailOrderSlipList) {
        super(context, 0, detailOrderSlipList);
        this.context = context;
        this.detailOrderSlipList = detailOrderSlipList;
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_order_slip, parent, false);
        }

        DetailOrderSlip detailOrderSlip = detailOrderSlipList.get(position);

        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        CheckBox cbSelect = convertView.findViewById(R.id.cbSelect);

        tvBookName.setText(detailOrderSlip.getBookName());
        tvQuantity.setText(String.valueOf(detailOrderSlip.getQuantity()));
        tvStatus.setText(detailOrderSlip.getStatus());

        if ("Imported".equals(detailOrderSlip.getStatus())) {
            cbSelect.setVisibility(View.GONE);
        } else {
            cbSelect.setVisibility(View.VISIBLE);
        }

        cbSelect.setOnCheckedChangeListener(null);
        cbSelect.setChecked(selectedItems.get(position, false));
        cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.put(position, true);
            } else {
                selectedItems.delete(position);
            }
        });

        return convertView;
    }

    public List<DetailOrderSlip> getSelectedItems() {
        List<DetailOrderSlip> selectedList = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            int key = selectedItems.keyAt(i);
            if (selectedItems.get(key)) {
                selectedList.add(detailOrderSlipList.get(key));
            }
        }
        return selectedList;
    }
}

