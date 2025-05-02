package com.college.cs4048_group_007;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.data.AppDatabase;
import com.college.cs4048_group_007.data.PoiRepository;
import com.college.cs4048_group_007.data.SaleItemRepository;
import com.college.cs4048_group_007.data.TransactionRepository;
import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class OrderActivity extends AppCompatActivity {
    TransactionRepository Transactiondb;
    SaleItemRepository itemsDb = new SaleItemRepository(getApplication());
    PoiRepository poiDb = new PoiRepository(getApplication());
    List<Integer> items = new ArrayList<Integer>();
    LiveData<List<SaleItem>> allItems = itemsDb.getAllSaleItem();
    ArrayList<SaleItem> transactionItems = new ArrayList<SaleItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.order_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            AppDatabase.insertTestData(db);
        });
        allItems.observe(this, saleItems -> {
            if (saleItems != null) {
                transactionItems.clear();
                transactionItems.addAll(saleItems);
                Log.i("Test", "Loaded " + transactionItems.size() + " sale items");

                getProductIds(transactionItems);
            }
        });
        Transactiondb = new TransactionRepository(getApplication());
        back();
        Log.i("test", "Order opens fine");

    }
    void back(){
        Button back = findViewById(R.id.back);
        back.setOnClickListener( v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }

    ArrayList<Integer> getProductIds(ArrayList<SaleItem> items){

        /*LiveData<Poi> poi= poiDb.getPoiById(id);
        String test = poi.toString();
        if(!poi.isInitialized()){
            Log.i("Test", test);
        }
        ArrayList<Integer> productIds = new ArrayList<Integer>();
        List<Poi> poisList = (List<Poi>) poi.getValue();
        String test2 = String.valueOf(poisList.size());
        Log.i("Test", test2);*/
        ArrayList<Integer> productIds = new ArrayList<Integer>();
        LiveData<List<Transaction>> transactionItemLiveData= Transactiondb.getAllRide();
        Transactiondb.getAllRide().observe(this, transactions -> {
            Log.i("Test","Size :" + String.valueOf(transactions.size()));


            if (transactions != null && !transactions.isEmpty()) {
                for (int i = 0; i<transactions.size(); i++) {
                    Log.i("Test","I :" + String.valueOf(i));
                    Transaction transaction = transactions.get(i);
                    Log.i("Test","transaction :" + String.valueOf(transaction.productId + 1));
                    int productId = transaction.productId;
                    for(SaleItem item : items){
                        if(item.productId == productId){
                            if(item.timer == 0) {
                                createButtons(item.name, "Order Number " + String.valueOf(transaction.transactionId) + " Status :" + transaction.status);
                            }else{
                                createFoodOrder(item.name, "Order Number " + String.valueOf(transaction.transactionId), item.timer);
                            }
                        }
                    }
                }
            }else{
                Log.i("Test", "doesnt get an item");
            }
        });
        /*Transactiondb.getAllRide().observe(this, transactions -> {
            Log.i("test", "Live Trans");

            if (transactions != null && !transactions.isEmpty()) {
                for (Transaction transaction : transactions) {
                    int productId = transaction.productId;

                    itemsDb.getSaleItemById(productId).observe(this, saleItem -> {
                        if (saleItem != null) {
                            int poiId = saleItem.poiId;

                            poiDb.getPoiById(poiId).observe(this, poi -> {
                                if (poi != null) {
                                    Log.i("Test", "Poi name: " + poi.name + " for product " + saleItem.name);
                                    createButtons(saleItem.name, poi.name);
                                } else {
                                    Log.w("Test", "POI not found for poiId: " + poiId);
                                }
                            });
                        } else {
                            Log.w("Test", "SaleItem not found for productId: " + productId);
                        }
                    });
                }
            } else {
                Log.i("Test", "No transactions found");
            }
        });*/


        return productIds;
    }
    void createFoodOrder(String name, String id, long timer){
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            runOnUiThread(() -> {

                Log.i("test", "Breaks at the button");
                LinearLayout layout = findViewById(R.id.linearLayoutContainer);
                TextView textView = new TextView(this);
                int textViewId = View.generateViewId();
                textView.setId(textViewId);
                String nameDesc = "Order : " + name + " From ";
                textView.setText(nameDesc);
                layout.addView(textView);

                Button button = new Button(this);
                button.setText("Progress on "+ name);
                button.setOnClickListener( v -> {
                    Intent intent = new Intent(this, FoodwaitingActivity.class);
                    intent.putExtra("poi_id", timer);
                    intent.putExtra("poi_id", name);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
                layout.addView(button);
                    /*Button button = new Button(this);
                    int thisId = View.generateViewId();
                    buttonIds.add(thisId);
                    button.setText("Add to Order");

                    // Set layout params
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.verticalBias = 0.305f*productId;
                    params.horizontalBias = 0.671f;

                    button.setLayoutParams(params);

                    button.setOnClickListener(v -> {
                        addToOrder(name, productId, thisId);
                        Toast.makeText(this, name+"added to Order", Toast.LENGTH_SHORT).show();
                    });

                    ConstraintLayout layout = findViewById(R.id.main);
                    layout.addView(button);
                    buttonIds.add(button.getId());

                    TextView textView = new TextView(this);
                    int textViewId = View.generateViewId();
                    textView.setId(textViewId);
                    textView.setText(name);

                    ConstraintLayout.LayoutParams textParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    textParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    textParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;


                    textParams.endToStart = buttonIds.get(buttonIds.size() - 1);
                    textParams.verticalBias = 0.305f*productId;
                    textParams.setMarginEnd(16);

                    textView.setLayoutParams(textParams);
                    layout.addView(textView);*/
            });
        }).start();
    }
    void createButtons(String name, String place) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            runOnUiThread(() -> {

                Log.i("test", "Breaks at the button");
                LinearLayout layout = findViewById(R.id.linearLayoutContainer);
                TextView textView = new TextView(this);
                int textViewId = View.generateViewId();
                textView.setId(textViewId);
                String nameDesc = "Order : " + name + " From " + place;
                textView.setText(nameDesc);
                layout.addView(textView);


                    /*Button button = new Button(this);
                    int thisId = View.generateViewId();
                    buttonIds.add(thisId);
                    button.setText("Add to Order");

                    // Set layout params
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    params.verticalBias = 0.305f*productId;
                    params.horizontalBias = 0.671f;

                    button.setLayoutParams(params);

                    button.setOnClickListener(v -> {
                        addToOrder(name, productId, thisId);
                        Toast.makeText(this, name+"added to Order", Toast.LENGTH_SHORT).show();
                    });

                    ConstraintLayout layout = findViewById(R.id.main);
                    layout.addView(button);
                    buttonIds.add(button.getId());

                    TextView textView = new TextView(this);
                    int textViewId = View.generateViewId();
                    textView.setId(textViewId);
                    textView.setText(name);

                    ConstraintLayout.LayoutParams textParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    textParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    textParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;


                    textParams.endToStart = buttonIds.get(buttonIds.size() - 1);
                    textParams.verticalBias = 0.305f*productId;
                    textParams.setMarginEnd(16);

                    textView.setLayoutParams(textParams);
                    layout.addView(textView);*/
            });
        }).start();
    }
}