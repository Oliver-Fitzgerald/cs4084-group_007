package com.college.cs4048_group_007;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;
import com.college.cs4048_group_007.data.TransactionRepository;
import com.college.cs4048_group_007.data.SaleItemRepository;
import com.college.cs4048_group_007.data.PoiRepository;

import java.util.ArrayList;
import java.util.List;


public class ShopActivity extends AppCompatActivity {
    String item1Name = "CotonCandy";
    String item2Name = "Default Ring";
    String item3Name = "Ticket";

    float item1Price = 30;
    float item2Price = 40;

    float item3Price = 5;
    TransactionRepository Transactiondb;
    SaleItemRepository itemsDb;
    boolean ride = true;
    PoiRepository poiDb;
    boolean ordered = false;
    ArrayList<String> orderedItems = new ArrayList<String>();
    int orderNumber;

    //int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shops);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Transactiondb = new TransactionRepository(getApplication());
        itemsDb = new SaleItemRepository(getApplication());
        this.poiDb = new PoiRepository(getApplication());

        rideOrShop();
        setNames();
        back();



        Button item1 = findViewById(R.id.butItem1);
        Button item2 = findViewById(R.id.butitem2);
        Button item3 = findViewById(R.id.butItem3);

        item1.setOnClickListener( v -> {
            order(1);
        });
        item2.setOnClickListener( v -> {
            order(2);
        });
        item3.setOnClickListener( v -> {
            order(3);
        });

        Button order = findViewById(R.id.order);
        TextView orderID = findViewById(R.id.transId);

        order.setOnClickListener(v ->{
            //orderCompleted();
            if(!ordered){
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);
                item3.setVisibility(View.GONE);
                order.setText("Cancel");
                orderID.setText("Order Number ; " + orderNumber);
                this.ordered = true;
            }else{
                item1.setVisibility(View.VISIBLE);
                if(!ride) {
                    item2.setVisibility(View.VISIBLE);
                    item3.setVisibility(View.VISIBLE);
                }
                order.setText("Order");
                orderID.setText("");
                this.ordered = false;
            }
        });
    }
    void rideOrShop(){
        /*LiveData<Poi> poiLive= poiDb.getPoiById(id);
        Poi poi = poiLive.getValue();
        if(!poi.type.equals("shop")){
            this.ride = true;
        }else{
            this.ride= true;
        }*/


    }
    ArrayList<Integer> getProductIds(int id){

        LiveData<Poi> poi= poiDb.getPoiById(id);
        List<Poi> poisList = (List<Poi>) poi.getValue();
        LiveData<List<SaleItem>> saleItemsLiveData= itemsDb.getAllSaleItem();
        List<SaleItem> saleItemsList = saleItemsLiveData.getValue();
        ArrayList<Integer> productIds = new ArrayList<Integer>();

        for(int i = 0; i < saleItemsList.size(); i++){
            int idSaleItem = saleItemsList.get(i).poiId;
            if(idSaleItem == id){
                productIds.add(idSaleItem);
            }
        }
        return productIds;
    }
    void getNames(ArrayList<Integer> productIds){
        ArrayList<String> names = new ArrayList<String>();

        for(int i = 0; i < productIds.size();i++){
            LiveData<SaleItem> saleItemsLiveData= itemsDb.getSaleItemById(productIds.get(i));
            SaleItem saleItem = saleItemsLiveData.getValue();
            assert saleItem != null;
            names.add(saleItem.name);
        }
        if(ride){
            this.item1Name = "Ticket";

        }else{
            this.item1Name = names.get(0);
            this.item2Name = names.get(1);
            this.item3Name = names.get(2);
        }
    }

    void getPrices(ArrayList<Integer> productIds){
        ArrayList<Float> prices = new ArrayList<Float>();

        for(int i = 0; i < productIds.size();i++){
            LiveData<SaleItem> saleItemsLiveData= itemsDb.getSaleItemById(productIds.get(i));
            SaleItem saleItem = saleItemsLiveData.getValue();
            assert saleItem != null;
            prices.add((saleItem.price));
        }
        if(ride){
            this.item1Price = prices.get(0);

        }else{
            this.item1Price = prices.get(0);
            this.item2Price = prices.get(1);
            this.item3Price = prices.get(2);
        }
    }
    void back(){
        Button back = findViewById(R.id.back);
        back.setOnClickListener( v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
    @SuppressLint("SetTextI18n")
    void setNames(){

        TextView item1 = findViewById(R.id.item1);
        TextView item2 = findViewById(R.id.item2);
        TextView item3 = findViewById(R.id.item3);
        Button item2Bt = findViewById(R.id.butitem2);
        Button item3Bt = findViewById(R.id.butItem3);

        if(!ride) {
            item1.setText(this.item1Name + " €"+ item1Price);
            item2.setText(this.item2Name + " €"+ item2Price);
            item3.setText(this.item3Name + " €"+ item3Price);
            item2.setVisibility(View.VISIBLE);
            item3.setVisibility(View.VISIBLE);
        }
        else{
            item1.setText(this.item1Name + item1Price);
            item2.setText("");
            item3.setText("");
            item2Bt.setVisibility(View.GONE);
            item3Bt.setVisibility(View.GONE);
        }
    }

    void order(int item){
        Toast.makeText(this, "Added to Order", Toast.LENGTH_LONG).show();

        if(item == 1){
            Transaction itemOrder = new Transaction();
            Transactiondb.insertTransaction(itemOrder);
            addToOrder(this.item1Name, this.item1Price);
        }else if(item == 2){
            Transaction itemOrder = new Transaction();
            Transactiondb.insertTransaction(itemOrder);
            addToOrder(this.item2Name, this.item2Price);
        }else{
            Transaction itemOrder = new Transaction();
            Transactiondb.insertTransaction(itemOrder);
            addToOrder(this.item3Name, this.item3Price);
        }
    }

    void addToOrder(String name, float price){

    }
    void orderCompleted(){
        try {
            wait(1000);
            Toast.makeText(this, "Order Completed", Toast.LENGTH_LONG).show();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}