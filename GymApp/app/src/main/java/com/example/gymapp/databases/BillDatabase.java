package com.example.gymapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDatabase {
    private DatabaseHelper dbHelper;

    public BillDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addBill(Bill bill) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.BILL_COLUMN_USER_ID, bill.getUserId());
        values.put(DatabaseHelper.BILL_COLUMN_CART_ID, bill.getCartId());
        values.put(DatabaseHelper.BILL_COLUMN_TOTAL_AMOUNT, bill.getTotalAmount());
        values.put(DatabaseHelper.BILL_COLUMN_DATE, bill.getBillDate());

        long billId = db.insert(DatabaseHelper.BILL_TABLE_NAME, null, values);
        db.close();
        return billId;
    }


    public List<Bill> getAllBills(int userId) {
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.BILL_TABLE_NAME + " WHERE " + DatabaseHelper.BILL_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});

        String[] columns = {
                DatabaseHelper.BILL_COLUMN_ID,
                DatabaseHelper.BILL_COLUMN_USER_ID,
                DatabaseHelper.BILL_COLUMN_CART_ID,
                DatabaseHelper.BILL_COLUMN_TOTAL_AMOUNT,
                DatabaseHelper.BILL_COLUMN_DATE
        };

        if (cursor.moveToFirst()) {
            do {
                int billId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.BILL_COLUMN_ID));
                int cartId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.BILL_COLUMN_CART_ID));
                double totalAmount = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.BILL_COLUMN_TOTAL_AMOUNT));
                String billDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BILL_COLUMN_DATE));

                Bill bill = new Bill(billId, userId, cartId, totalAmount, billDate);
                bills.add(bill);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bills;
    }

}
