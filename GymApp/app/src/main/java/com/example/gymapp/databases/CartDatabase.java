package com.example.gymapp.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartDatabase {
    private DatabaseHelper dbHelper;

    public CartDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addProductToCart(CartProduct product, int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CART_COLUMN_ID, product.getId());
        values.put(DatabaseHelper.CART_COLUMN_NAME, product.getName());
        values.put(DatabaseHelper.CART_COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.CART_COLUMN_QUANTITY, product.getQuantity());
        values.put("user_id", userId);  // Thêm userId

        long result = db.insert(DatabaseHelper.CART_TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public List<CartProduct> getAllCartItems(int userId) {
        List<CartProduct> cartProducts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DatabaseHelper.CART_COLUMN_ID,
                DatabaseHelper.CART_COLUMN_NAME,
                DatabaseHelper.CART_COLUMN_PRICE,
                DatabaseHelper.CART_COLUMN_QUANTITY
        };

        String selection = "user_id = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query(DatabaseHelper.CART_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_QUANTITY));

                CartProduct product = new CartProduct(id, name, price, quantity);
                cartProducts.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return cartProducts;
    }

    public void removeFromCart(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DatabaseHelper.CART_COLUMN_USER_ID + " = ?";
        String[] whereArgs = { String.valueOf(userId) };

        // Đảm bảo whereArgs chỉ chứa tham số cần thiết
        db.delete(DatabaseHelper.CART_TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public void removeProductFromCart(int productId, int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DatabaseHelper.CART_COLUMN_ID + " = ? AND user_id = ?";
        String[] whereArgs = { String.valueOf(productId), String.valueOf(userId) };

        db.delete(DatabaseHelper.CART_TABLE_NAME, whereClause, whereArgs);
        db.close();
    }
}
