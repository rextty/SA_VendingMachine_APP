package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.Entity.Product;
import com.example.sa_vendingmachine_app.Model.Entity.User;
import com.example.sa_vendingmachine_app.Service.PreOrderService;
import com.example.sa_vendingmachine_app.Service.ProductService;
import com.example.sa_vendingmachine_app.Service.UserService;
import com.example.sa_vendingmachine_app.databinding.ActivityMyOrderBinding;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private LinearLayout scrollViewLinearLayout;

    private PreOrderService preOrderService = new PreOrderService();

    private ProductService productService = new ProductService();

    private UserService userService = new UserService();

    private ActivityMyOrderBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityMyOrderBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        scrollViewLinearLayout = UI.scrollViewLinearLayout;

        initMyOrder();
    }

    @SuppressLint("SetTextI18n")
    private void initMyOrder() {
        String token = getIntent().getStringExtra("token");

        User user = userService.getUser(token);

        ArrayList<PreOrder> preOrders = preOrderService.getPreOrder(user.getId());

        Product product = productService.getProductByProductId(1);

        for (PreOrder preOrder : preOrders) {
            LinearLayout contentLayout = new LinearLayout(this);
            LinearLayout itemLayout = new LinearLayout(this);
            LinearLayout stateLayout = new LinearLayout(this);
            TextView totalPriceTextView = new TextView(this);
            TextView machineNumberTextView = new TextView(this);
            TextView expireDateTextView = new TextView(this);
            TextView stateTextView = new TextView(this);

            LinearLayout.LayoutParams contentLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
//            linearLayoutParams.gravity = Gravity.TOP;
            contentLayoutParams.setMargins(0, 5, 0, 0);
            contentLayout.setPadding(0, 0, 0, 20);
            contentLayout.setLayoutParams(contentLayoutParams);

            LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            itemLayoutParams.setMargins(0, 5, 0, 0);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setLayoutParams(itemLayoutParams);

            LinearLayout.LayoutParams stateLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            stateLayoutParams.gravity = Gravity.CENTER;
            stateLayoutParams.setMargins(100, 5, 0, 0);
            stateLayout.setLayoutParams(stateLayoutParams);
            stateLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textViewLayoutParams.setMargins(0, 0, 10, 0);
            totalPriceTextView.setLayoutParams(textViewLayoutParams);
            expireDateTextView.setLayoutParams(textViewLayoutParams);
            machineNumberTextView.setLayoutParams(textViewLayoutParams);
            stateTextView.setLayoutParams(textViewLayoutParams);

            expireDateTextView.setText("Expire Date: " + preOrder.getExpireDate());
            machineNumberTextView.setText("Machine Number: " + preOrder.getMachineSerialNumber());
            totalPriceTextView.setText("Total Price: " + preOrder.getTotalPrice());

            contentLayout.setOnClickListener(v -> {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setAdjustViewBounds(true);

                try {
                    imageView.setImageBitmap(BitmapFactory.decodeStream(preOrder.getQrcode().getBinaryStream()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                new AlertDialog.Builder(this).setView(imageView).show();
            });

            if (!preOrder.getTake()) {
                stateTextView.setTextColor(Color.rgb(255,0, 0));
                stateTextView.setText("Not yet picked up");
            } else {
                stateTextView.setTextColor(Color.rgb(0,255, 0));
                stateTextView.setText("Picked up");
            }

            contentLayout.addView(itemLayout);

            itemLayout.addView(expireDateTextView);
            itemLayout.addView(machineNumberTextView);
            itemLayout.addView(totalPriceTextView);

            contentLayout.addView(stateLayout);

            stateLayout.addView(stateTextView);

            scrollViewLinearLayout.addView(contentLayout);
        }
    }

    public void showImage(Bitmap bitmap) {
    }

    private void cancelOrder() {

    }

    private void queryOrderInfo() {

    }
}