package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sa_vendingmachine_app.Model.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.SQLExecuteTypeEnum;
import com.example.sa_vendingmachine_app.databinding.ActivityVendingMachineBinding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine extends AppCompatActivity {

    private static final String TAG = VendingMachine.class.getSimpleName();
    private ActivityVendingMachineBinding UI;

    private ArrayList<Object[]> productList = new ArrayList<>();

    private LinearLayout scrollViewLinearLayout;

    private String vendingMachineSerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityVendingMachineBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        Intent intent = this.getIntent();
        vendingMachineSerialNumber =  intent.getStringExtra("data");

        scrollViewLinearLayout = UI.scrollViewLinearLayout;

        String sql = "SELECT * FROM vending_machine.product;";

        ExecuteSQL executeSQL = new ExecuteSQL();
        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();
        ResultSet rs = executeSQL.getResultSet();
        try {
            while (rs.next()) {
                LinearLayout linearLayout = new LinearLayout(this);
                ImageView imageView = new ImageView(this);
                LinearLayout verticalLinearLayout = new LinearLayout(this);
                TextView productNameTextView = new TextView(this);
                TextView productPriceTextView = new TextView(this);
                Button minusButton = new Button(this);
                TextView quantityTextView = new TextView(this);
                Button plusButton = new Button(this);

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

                LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                productNameTextView.setLayoutParams(textViewLayoutParams);
                productPriceTextView.setLayoutParams(textViewLayoutParams);

                productNameTextView.setTextSize(18);
                productPriceTextView.setTextSize(18);

                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                        110,
                        120
                );
                buttonLayoutParams.setMargins(0, 0, 10, 0);
                minusButton.setLayoutParams(buttonLayoutParams);
                plusButton.setLayoutParams(buttonLayoutParams);

                minusButton.setText("-");
                plusButton.setText("+");

                LinearLayout.LayoutParams quantityLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                quantityLayoutParams.setMargins(0, 0, 10, 0);
                quantityTextView.setLayoutParams(quantityLayoutParams);

                Object data[] = {rs.getString("productId"), quantityTextView};
                productList.add(data);

                imageView.setImageResource(R.drawable.line_icon);
                productNameTextView.setText(rs.getString("name"));
                productPriceTextView.setText(String.format("$%s", rs.getString("price")));
                quantityTextView.setText("0");

                minusButton.setOnClickListener(v -> {
                    int quantity = Integer.parseInt(quantityTextView.getText().toString());
                    if (quantity < 1)
                        return;
                    quantity--;
                    quantityTextView.setText(Integer.toString(quantity));
                });

                plusButton.setOnClickListener(v -> {
                    int quantity = Integer.parseInt(quantityTextView.getText().toString());
                    quantity++;
                    quantityTextView.setText(Integer.toString(quantity));
                });

                verticalLinearLayout.addView(productNameTextView);
                verticalLinearLayout.addView(productPriceTextView);

                linearLayout.addView(imageView);
                linearLayout.addView(verticalLinearLayout);
                linearLayout.addView(minusButton);
                linearLayout.addView(quantityTextView);
                linearLayout.addView(plusButton);

                scrollViewLinearLayout.addView(linearLayout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}