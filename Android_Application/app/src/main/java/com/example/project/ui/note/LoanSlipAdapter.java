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
import com.example.project.entities.LoanSlip;

import java.util.List;

public class LoanSlipAdapter extends ArrayAdapter<LoanSlip> {

    public LoanSlipAdapter(Context context, List<LoanSlip> loanSlips) {
        super(context, 0, loanSlips);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LoanSlip loanSlip = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.loan_slip_item, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.idTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);

        idTextView.setText(Integer.toString(loanSlip.getId()));
        emailTextView.setText(loanSlip.getEmail());

        return convertView;
    }
}

