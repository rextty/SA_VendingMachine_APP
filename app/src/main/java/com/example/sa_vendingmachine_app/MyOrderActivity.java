package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.Entity.Product;
import com.example.sa_vendingmachine_app.Service.PreOrderService;
import com.example.sa_vendingmachine_app.Service.ProductService;
import com.example.sa_vendingmachine_app.databinding.ActivityMyOrderBinding;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {

    private LinearLayout scrollViewLinearLayout;

    private PreOrderService preOrderService = new PreOrderService();

    private ProductService productService = new ProductService();

    private ActivityMyOrderBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityMyOrderBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        scrollViewLinearLayout = UI.scrollViewLinearLayout;

        initMyOrder();
    }

    private void initMyOrder() {
        ArrayList<PreOrder> preOrders = preOrderService.getAllPreOrder();

        for (PreOrder preOrder : preOrders) {
            LinearLayout linearLayout = new LinearLayout(this);
            ImageView imageView = new ImageView(this);
            LinearLayout verticalLinearLayout = new LinearLayout(this);
            TextView totalPriceTextView = new TextView(this);

            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayoutParams.gravity = Gravity.CENTER;
            linearLayoutParams.setMargins(0, 5, 0, 0);
            linearLayout.setPadding(0, 0, 0, 20);
            linearLayout.setLayoutParams(linearLayoutParams);

            LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(
                    130,
                    130
            );
            imageViewLayoutParams.setMargins(0, 0, 10, 0);
            imageView.setLayoutParams(imageViewLayoutParams);

            LinearLayout.LayoutParams verticalLinearLayoutParams = new LinearLayout.LayoutParams(
                    470,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            verticalLinearLayoutParams.setMargins(0, 0, 50, 0);
            verticalLinearLayout.setGravity(Gravity.CENTER);
            verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
            verticalLinearLayout.setLayoutParams(verticalLinearLayoutParams);


            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    110,
                    120
            );
            buttonLayoutParams.setMargins(0, 0, 10, 0);

            LinearLayout.LayoutParams quantityLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            quantityLayoutParams.setMargins(0, 0, 10, 0);
            totalPriceTextView.setLayoutParams(quantityLayoutParams);

            imageView.setImageResource(R.drawable.line_icon);

            totalPriceTextView.setText("Total Price: " + preOrder.getTotalPrice());

            linearLayout.addView(imageView);
            linearLayout.addView(verticalLinearLayout);
            linearLayout.addView(totalPriceTextView);

            scrollViewLinearLayout.addView(linearLayout);
        }
    }

    private void cancelOrder() {

    }

    private void queryOrderInfo() {

    }
}