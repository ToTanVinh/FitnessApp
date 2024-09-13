package com.example.gymapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gymapp.R;
import com.example.gymapp.model.CartProduct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartAdapter extends ArrayAdapter<CartProduct> {
    private Context context;
    private List<CartProduct> cartList;
    private Set<CartProduct> selectedProducts = new HashSet<>(); // Danh sách sản phẩm được chọn

    public CartAdapter(Context context, List<CartProduct> products){
        super(context, 0, products);
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_product_item_cart, parent, false);
        }

        CartProduct product = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.productImage);
        TextView nameTextView = convertView.findViewById(R.id.productName);
        TextView priceTextView = convertView.findViewById(R.id.productPrice);
        CheckBox checkBoxSelect = convertView.findViewById(R.id.checkBoxSelect);

        nameTextView.setText(product.getName());
        priceTextView.setText(String.format("$%.2f", product.getPrice()));

        // Xử lý sự kiện khi CheckBox được chọn hoặc bỏ chọn
        checkBoxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedProducts.add(product); // Thêm sản phẩm vào danh sách đã chọn
            } else {
                selectedProducts.remove(product); // Xóa sản phẩm khỏi danh sách đã chọn
            }
        });

        // Đảm bảo rằng trạng thái CheckBox được lưu trữ chính xác khi cuộn danh sách
        checkBoxSelect.setChecked(selectedProducts.contains(product));

        return convertView;
    }

    public Set<CartProduct> getSelectedProducts() {
        return selectedProducts;
    }
}
