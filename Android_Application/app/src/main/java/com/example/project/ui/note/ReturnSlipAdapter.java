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
import com.example.project.entities.ReturnSlip;

import java.util.List;

public class ReturnSlipAdapter extends ArrayAdapter<ReturnSlip> {
    private Context context;
    private List<ReturnSlip> returnSlipList;

    public ReturnSlipAdapter(Context context, List<ReturnSlip> returnSlipList) {
        super(context, 0, returnSlipList);
        this.context = context;
        this.returnSlipList = returnSlipList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_return_slip, parent, false);
        }

        ReturnSlip returnSlip = returnSlipList.get(position);

        TextView tvReturnSlipId = convertView.findViewById(R.id.tvReturnSlipId);
        TextView tvReturnSlipDate = convertView.findViewById(R.id.tvReturnSlipDate);

        tvReturnSlipId.setText(String.valueOf(returnSlip.getId()));
        tvReturnSlipDate.setText(returnSlip.getEmail());

        // Set other fields if needed

        return convertView;
    }
}

