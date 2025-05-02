package com.college.cs4048_group_007;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.college.cs4048_group_007.data.AppDatabase;
import com.college.cs4048_group_007.data.SaleItemRepository;
import com.college.cs4048_group_007.entities.SaleItem;

public class AdminPanel extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_sale_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_add_sale_item_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button returnHome = findViewById(R.id.return_home);
        returnHome.setOnClickListener(v -> {
            finish();
        });

        TextView errorMessage = findViewById(R.id.add_sale_item_error_message);
        EditText itemName = findViewById(R.id.add_item_name);
        EditText itemDescription = findViewById(R.id.add_item_description);
        EditText itemPrice = findViewById(R.id.add_item_price);
        EditText timer = findViewById(R.id.add_item_time);

        Button submit = findViewById(R.id.submit_sale_item);
        submit.setOnClickListener(v -> {
            errorMessage.setText("");

            //Get Raw Input for add sale item
            String name = itemName.getText().toString();
            String description = itemDescription.getText().toString();
            String priceString = itemPrice.getText().toString();
            String timeString = timer.getText().toString();

            try {
                int price = Integer.parseInt(priceString);
                if (timeString.equals(""))
                    timeString = "0";

                if (name.equals("") || description.equals("") || priceString.equals(""))
                    errorMessage.setText("Mandatory fields left empty");

                else {
                    try {
                        long time = Long.parseLong(timeString);

                        //Insert Sale Item
                        SaleItemRepository saleItemRepository = new SaleItemRepository(getApplication());

                        Log.i("Add SaleItem","Name: " + name);
                        Log.i("Add SaleItem","Description: " + description);
                        Log.i("Add SaleItem","Price: " + price);
                        Log.i("Add SaleItem","Time: " + time);
                        SaleItem saleItem = new SaleItem(Integer.parseInt(getIntent().getStringExtra("poiId")),price,name,description,time);
                        saleItemRepository.insertSaleItem(saleItem);
                        errorMessage.setTextColor(Color.GREEN);
                        errorMessage.setText("Sale Item Added");

                    } catch (NumberFormatException exception) {
                        errorMessage.setText("Time input is invalid");
                    }
                }
            } catch (NumberFormatException exception) {
                errorMessage.setText("Price must be an integer");
            }

            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(intent);
        });
    }
}
