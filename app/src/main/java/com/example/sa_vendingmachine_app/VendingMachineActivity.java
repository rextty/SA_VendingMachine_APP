package com.example.sa_vendingmachine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa_vendingmachine_app.Model.Bank;
import com.example.sa_vendingmachine_app.Model.Entity.PreOrder;
import com.example.sa_vendingmachine_app.Model.Entity.Product;
import com.example.sa_vendingmachine_app.Model.Entity.ShopCart;
import com.example.sa_vendingmachine_app.Service.PreOrderService;
import com.example.sa_vendingmachine_app.Service.ProductService;
import com.example.sa_vendingmachine_app.databinding.ActivityVendingMachineBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class VendingMachineActivity extends AppCompatActivity {
    private static final String TAG = VendingMachineActivity.class.getSimpleName();

    private ActivityVendingMachineBinding UI;

    private Button nextButton;
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private RadioGroup paymentRadioGroup;
    private LinearLayout scrollViewLinearLayout;

    private String vendingMachineSerialNumber;
    private ArrayList<String> sqlList = new ArrayList<>();
    private ArrayList<Object[]> productList = new ArrayList<>();

    private Bank bank = new Bank();
    private PreOrder preOrder = new PreOrder();
    private ArrayList<ShopCart> shopCarts = new ArrayList<>();
    private ProductService productService = new ProductService();
    private PreOrderService preOrderService = new PreOrderService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityVendingMachineBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        Intent intent = this.getIntent();
        vendingMachineSerialNumber =  intent.getStringExtra("data");

        scrollViewLinearLayout = UI.scrollViewLinearLayout;
        titleTextView = UI.titleTextView;
        nextButton = UI.nextButton;

        initProductItem();
//        showPickTime();
//        showPayment();
    }

    private void showPickTime() {
        scrollViewLinearLayout.removeAllViews();

        titleTextView.setText("Pick Time");
        nextButton.setText("Next");

        dateTextView = new TextView(this);
        timeTextView = new TextView(this);

        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParams.weight = 1;

        dateTextView.setGravity(Gravity.CENTER);
        timeTextView.setGravity(Gravity.CENTER);

        dateTextView.setLayoutParams(textViewLayoutParams);
        timeTextView.setLayoutParams(textViewLayoutParams);

        dateTextView.setText("Click to pick date");
        timeTextView.setText("Click to pick Time");

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickDateDialog(dateTextView);
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickTimeDialog(timeTextView);
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;

        scrollViewLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        scrollViewLinearLayout.setLayoutParams(params);

        scrollViewLinearLayout.addView(dateTextView);
        scrollViewLinearLayout.addView(timeTextView);

        nextButton.setOnClickListener(v -> {
            saveTime();
        });
    }

    private void showPickDateDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year1, month1, day1) -> {
            @SuppressLint("DefaultLocale")
            String datetime = String.format(
                    "%d-%02d-%02d", year1, month1 + 1, day1
            );
            textView.setText(datetime);
        }, year, month, day);
        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.getDatePicker().setMaxDate(new Date().getTime() + (1000*60*60*24*3));
        dialog.show();
    }

    private void showPickTimeDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        new TimePickerDialog(this, (view, hourOfDay1, minute1) -> {
            @SuppressLint("DefaultLocale")
            String datetime = String.format(
                    "%02d:%02d:%d", hourOfDay1, minute1, second
            );
            textView.setText(datetime);
        }, hourOfDay, minute,true).show();
    }

    private void showPayment() {
        int amount = bank.getBalance();
        int price = preOrder.getTotalPrice();

        scrollViewLinearLayout.removeAllViews();

        titleTextView.setText("Select Payment");
        nextButton.setText("Next");

        paymentRadioGroup = new RadioGroup(this);

        RadioButton creditRadioButton = new RadioButton(this);
        creditRadioButton.setText("Credit Card");

        RadioButton lineRadioButton = new RadioButton(this);
        lineRadioButton.setText("line pay");

        paymentRadioGroup.addView(creditRadioButton);
        paymentRadioGroup.addView(lineRadioButton);

        paymentRadioGroup.setPadding(50, 0, 0, 0);

        scrollViewLinearLayout.addView(paymentRadioGroup);

        nextButton.setOnClickListener(v -> {
            if (findViewById(paymentRadioGroup.getCheckedRadioButtonId()) == null) {
                showMsg("Please select payment.");
            }else {
                checkoutShopCart(amount, price);
            }
        });
    }

    // TODO: Listener 不要用 new, 這樣就不會有一堆無用function

    private void checkoutShopCart(int amount, int price) {
        if (amount >= price) {
            bank.debit(price);
            showMsg("Debit Success");
//            preOrder.setQrcode();
            preOrderService.savePreOrder(preOrder);
        }else {
            showMsg("Insufficient balance");
        }
        finish();
    }

    private void saveToShopCart() {
        int totalPrice = 0;
        int totalQuantity = 0;
        for (Object[] obj : productList) {
            totalQuantity += Integer.parseInt(((TextView) obj[1]).getText().toString());
        }

        if (totalQuantity == 0) {
            showMsg("shopping list is empty");
            return;
        }

        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        for (Object[] obj : productList) {
            int quantity = Integer.parseInt(((TextView) obj[1]).getText().toString());

            if (quantity != 0) {
                for (int i = 0; i < quantity; i++) {
                    Product product = (Product) obj[0];

                    ShopCart shopCart = new ShopCart();
                    shopCart.setProductId(product.getProductId());
                    shopCart.setQuantity(quantity);

                    shopCarts.add(shopCart);
                    totalPrice += product.getPrice() * shopCart.getQuantity();

                    String sql = String.format(
                            "INSERT INTO vending_machine.sales_record (productId, machineSerialNumber, date) VALUES ('%s', '%s', '%s');",
                            obj[0],
                            vendingMachineSerialNumber,
                            simpleDateFormat.format(new java.util.Date())
                    );
                    sqlList.add(sql);
                }
                preOrder.setTotalPrice(totalPrice);
                preOrder.setMachineSerialNumber(vendingMachineSerialNumber);
                // TODO: set userId
            }
        }
        showPickTime();
    }

    // TODO: QRCOde First

    private void saveTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dtf.format(LocalDateTime.now());

        String date = dateTextView.getText().toString();
        String time = timeTextView.getText().toString();

        String dateTime = String.format("%s %s", date, time);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(currentTime);
            date2 = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        assert date2 != null;

        long difference = date2.getTime() - date1.getTime();

        if (difference <= 300000) {
            showMsg("Please pick later than 5 min.");
        }else if (!date.equals("Click to pick date") && !time.equals("Click to pick Time")) {
            preOrder.setExpireDate(dateTime);
            showShopCart();
//            showPayment();
        }else {
            showMsg("Please pick time.");
        }
    }

    @SuppressLint("SetTextI18n")
    private void showShopCart() {
        titleTextView.setText("Shop Cart");
        nextButton.setText("Next");

        scrollViewLinearLayout.removeAllViews();

        TextView totalPriceTextView = new TextView(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP;

        scrollViewLinearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollViewLinearLayout.setLayoutParams(params);

        for (ShopCart shopCart : shopCarts) {
            productService = new ProductService();

            Product product = productService.getProductByProductId(shopCart.getProductId());

            LinearLayout linearLayout = new LinearLayout(this);
            ImageView imageView = new ImageView(this);
            LinearLayout verticalLinearLayout = new LinearLayout(this);
            TextView productNameTextView = new TextView(this);
            TextView productPriceTextView = new TextView(this);
            TextView quantityTextView = new TextView(this);

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

            LinearLayout.LayoutParams quantityLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            quantityLayoutParams.setMargins(0, 0, 10, 0);
            quantityLayoutParams.gravity = Gravity.CENTER;
            quantityTextView.setLayoutParams(quantityLayoutParams);

            imageView.setImageResource(R.drawable.line_icon);
            productNameTextView.setText(product.getName());
            productPriceTextView.setText(String.valueOf(product.getPrice()));
            quantityTextView.setText(String.valueOf(shopCart.getQuantity()));

            verticalLinearLayout.addView(productNameTextView);
            verticalLinearLayout.addView(productPriceTextView);

            linearLayout.addView(imageView);
            linearLayout.addView(verticalLinearLayout);
            linearLayout.addView(quantityTextView);

            scrollViewLinearLayout.addView(linearLayout);
        }

        totalPriceTextView.setGravity(Gravity.CENTER);
        totalPriceTextView.setText("Total Price: " + preOrder.getTotalPrice());

        scrollViewLinearLayout.addView(totalPriceTextView);

        nextButton.setOnClickListener(v -> {
            showPayment();
        });
    }

    private void initProductItem() {
        ArrayList<Product> products = productService.getAllProduct();

        for (Product product : products) {
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

            productList.add(new Object[] {product, quantityTextView});

            imageView.setImageResource(R.drawable.line_icon);
            productNameTextView.setText(product.getName());
            productPriceTextView.setText(String.format("$%s", product.getPrice()));
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

        nextButton.setOnClickListener(v -> {
            saveToShopCart();
        });
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}