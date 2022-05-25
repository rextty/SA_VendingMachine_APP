package com.example.sa_vendingmachine_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.sa_vendingmachine_app.Model.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.SQLExecuteTypeEnum;
import com.example.sa_vendingmachine_app.databinding.ActivityVendingMachineBinding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VendingMachine extends AppCompatActivity {

    private ActivityVendingMachineBinding UI;

    private RecyclerView recyclerView;

    private productAdapter productAdapter;

    private ArrayList<Product> products = new ArrayList<>();;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = ActivityVendingMachineBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        getProductFromDB();

        recyclerView = UI.recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        productAdapter = new productAdapter(products);
        recyclerView.setAdapter(productAdapter);

        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorBlue));
        swipeRefreshLayout.setOnRefreshListener(()->{
            products.clear();
            getProductFromDB();
            productAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });
    }

    private void getProductFromDB() {
        String sql = "SELECT * FROM vending_machine.product;";

        ExecuteSQL executeSQL = new ExecuteSQL();
        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        ResultSet rs = executeSQL.getResultSet();

        try {
            while (rs.next()) {
                Product product = new Product();
                product.setImage(rs.getString("image"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    private ArrayList<Product> products;

    public productAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.format("$ %d NTD", product.getPrice()));

        Button minusButton = holder.minusButton;
        TextView quantityTextView = holder.quantityTextView;
        Button plusButton = holder.plusButton;

        minusButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityTextView.getText().toString());
            if (quantity < 1)
                return;
            quantity--;
            quantityTextView.setText(quantity+"");
        });

        plusButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityTextView.getText().toString());
            quantity++;
            quantityTextView.setText(quantity+"");
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView productNameTextView;
        private final TextView productPriceTextView;
        private final TextView quantityTextView;

        private final Button minusButton;
        private final Button plusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            minusButton = itemView.findViewById(R.id.minusButton);
            plusButton = itemView.findViewById(R.id.plusButton);
        }
    }
}