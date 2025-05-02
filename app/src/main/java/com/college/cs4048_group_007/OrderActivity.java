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
    List<Transaction> items = new ArrayList<>();

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
        Transactiondb = new TransactionRepository(getApplication());
        back();
        Log.i("test", "Order opens fine");
        getProductIds();
    }
    void back(){
        Button back = findViewById(R.id.back);
        back.setOnClickListener( v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }

    ArrayList<Integer> getProductIds(){

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
        Log.i("test", "Before Trans");
        LiveData<List<Transaction>> transactionItemLiveData= Transactiondb.getAllRide();
        Transactiondb.getAllRide().observe(this, transactions -> {
            Log.i("test", "Live Trans");
            if (transactions != null && !transactions.isEmpty()) {
                for (Transaction transaction : transactions) {
                    int productId = transaction.productId;

                    // Now query itemsDb to get SaleItem using productId
                    itemsDb.getSaleItemById(productId).observe(this, saleItem -> {
                        if (saleItem != null) {
                            int poiId = saleItem.poiId; // or saleItem.poiId if public
                            Log.i("Test", "Found PoiId: " + poiId + " for ProductId: " + productId);

                            // If you want to go further and get the POI itself:
                            poiDb.getPoiById(poiId).observe(this, poi -> {
                                if (poi != null) {
                                    Log.i("Test", "Poi name: " + poi.name); // example
                                    createButtons(saleItem.name, poi.name);
                                }
                            });
                        }
                    });
                }


            }else{
                Log.i("Test", "doesnt get an item");
            }
        });


        return productIds;
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