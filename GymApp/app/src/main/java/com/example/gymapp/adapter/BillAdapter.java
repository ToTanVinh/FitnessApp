package com.example.gymapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gymapp.R;
import com.example.gymapp.model.Bill;

import java.util.List;

public class BillAdapter extends ArrayAdapter<Bill> {
    private Context context;
    private List<Bill> bills;

    public BillAdapter(Context context, List<Bill> bills) {
        super(context, 0, bills);
        this.context = context;
        this.bills = bills;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_client_bill, parent, false);
        }

        Bill bill = getItem(position);

        TextView billDateTextView = convertView.findViewById(R.id.billDate);
        TextView billTotalAmountTextView = convertView.findViewById(R.id.billTotalAmount);

        billDateTextView.setText(bill.getBillDate());
        billTotalAmountTextView.setText(String.format("$%.2f", bill.getTotalAmount()));

        return convertView;
    }
}