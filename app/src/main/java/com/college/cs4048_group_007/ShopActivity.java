package com.college.cs4048_group_007;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.college.cs4048_group_007.entities.Transaction;
import com.college.cs4048_group_007.data.TransactionRepository;
import com.college.cs4048_group_007.data.TransactionDao;
import com.college.cs4048_group_007.data.SaleItemRepository;
import com.college.cs4048_group_007.data.SaleItemDao;

import dalvik.system.ApplicationRuntime;


public class ShopActivity extends AppCompatActivity {
    String item1 = "CotonCandy";
    String item2 = "Default Ring";
    String item3 = "OMG ITS A THIRD ITEM";
    TransactionRepository Transactiondb;
    SaleItemRepository itemsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Transactiondb = new TransactionRepository(Application);
        itemsDb = new SaleItemRepository();
        Order();
        setNames();


    }
    void getNames(String id){

    }
    void setNames(){
        TextView item1 = findViewById(R.id.item1);
        TextView item2 = findViewById(R.id.item2);

        item1.setText((CharSequence) item1 + "22");
        item2.setText((CharSequence) item2 + "33");

    }

    void Order(){
        Button item1 = findViewById(R.id.butItem1);
        Button item2 = findViewById(R.id.butitem2);

        item1.setOnClickListener( v -> {
            Toast.makeText(this, "Order Added", Toast.LENGTH_LONG).show();
            Transaction item1Order = new Transaction();
            Transactiondb.insertTransaction(item1Order);
        });
        item2.setOnClickListener( v -> {
            Toast.makeText(this, "Order Added", Toast.LENGTH_LONG).show();
            Transaction item2Order = new Transaction();
            Transactiondb.insertTransaction(item2Order);
        });
    }
}