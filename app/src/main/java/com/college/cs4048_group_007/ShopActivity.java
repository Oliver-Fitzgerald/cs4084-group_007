package com.college.cs4048_group_007;

import static com.college.cs4048_group_007.data.AppDatabase.insertTestData;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.college.cs4048_group_007.data.AppDatabase;
import com.college.cs4048_group_007.entities.Poi;
import com.college.cs4048_group_007.entities.SaleItem;
import com.college.cs4048_group_007.entities.Transaction;
import com.college.cs4048_group_007.data.TransactionRepository;
import com.college.cs4048_group_007.data.SaleItemRepository;
import com.college.cs4048_group_007.data.PoiRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;


public class ShopActivity extends AppCompatActivity {

    TransactionRepository Transactiondb;
    SaleItemRepository itemsDb;
    PoiRepository poiDb;

    int id;
    List<Integer> buttonIds = new ArrayList<>();
    List<Integer> itemIds = new ArrayList<>();
    List<SaleItem> items = new ArrayList<>();
    String name = "";
    Transaction transaction;


    @SuppressLint("SetTextI18n")
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


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        resetAct();
        //setupShopActivity();
    }

    protected void onResume(){

        super.onResume();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shops);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resetAct();
        setupShopActivity();
    }

    void setupShopActivity(){

        int poiId = getIntent().getIntExtra("poi_id", -1);
        Log.i("Test", "OPEN SHOP ACT");
        Log.i("Test", "POI ID 0: " + String.valueOf(poiId));
        if (poiId != -1) {
            this.id = poiId;
            Log.i("Test", "POI ID 1 : " + String.valueOf(id));
        } else {
            Log.e("ShopActivity","Error with Id");
        }
        Transactiondb = new TransactionRepository(getApplication());
        itemsDb = new SaleItemRepository(getApplication());
        this.poiDb = new PoiRepository(getApplication());
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            insertTestData(db);
        });

        Log.i("Test", String.valueOf(id));
        back();
        getProductIds(id);

        Button order = findViewById(R.id.order);
        order.setVisibility(View.GONE);
        order.setOnClickListener(v ->{

            orderCompleted();
        });

        Button cancel = findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        cancel.setOnClickListener(v ->{

            orderCancel();
        });

        Button orderButton = findViewById(R.id.btnOpenOrder);
        orderButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        });
    }
    void resetAct(){

        this.items.clear();
        Button order = findViewById(R.id.order);
        Button cancel = findViewById(R.id.cancel);

        order.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    // getProduct
    ArrayList<Integer> getProductIds(int id){

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

        LiveData<List<SaleItem>> saleItemsLiveData= itemsDb.getAllSaleItem();
        itemsDb.getAllSaleItem().observe(this, saleItemsList -> {

            if(saleItemsList != null && !saleItemsList.isEmpty()){
                this.items.clear();
                System.out.println(saleItemsList.toString());
                for(int i = 0; i < Objects.requireNonNull(saleItemsList).size(); i++){
                    this.items.add(saleItemsList.get(i));
                    // the sale item id
                    int idSaleItem = saleItemsList.get(i).productId;
                    int poiIdSaleItem = saleItemsList.get(i).poiId;
                    Log.i("Test", "POI " + String.valueOf(poiIdSaleItem));
                    Log.i("Test", "Sale Item " + String.valueOf(idSaleItem));
                    if(poiIdSaleItem == id){
                        Log.i("Test", "Sale Item actually added" + String.valueOf(idSaleItem));
                        // adds it of it has the same poi is the same
                        productIds.add(idSaleItem);
                        createButtons(items.get(i).name, items.get(i).description, items.get(i).productId, items.get(i).timer);
                    }
                }
                /*for (int i = 0; i < productIds.size(); i++) {
                    Log.i("Test", "ProductsSize " + String.valueOf(productIds.size()));
                    for(int j = 0; j < items.size(); j++){

                        Log.i("Test", "productIds" + String.valueOf(items.get(j).productId));
                        Log.i("Test", "other value" + String.valueOf(productIds.get(i)));
                        if(items.get(j).productId == productIds.get(i)){
                            Log.i("Test", "productIds actually checked" + String.valueOf(items.get(j).productId));
                            name = items.get(j).name;
                            createButtons(name,items.get(j).description, items.get(j).productId, items.get(j).timer);
                        }
                    }
                }*/
            }else{
                Log.i("Test", "doesnt get an item");
            }
        });



        this.itemIds = productIds;
        return productIds;
    }



    void back(){
        Button back = findViewById(R.id.back);
        back.setOnClickListener( v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

    }


    void createButtons(String name,String desc, int productId, long timer) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                runOnUiThread(() -> {
                    LinearLayout layout = findViewById(R.id.linearLayoutContainer);
                    TextView textView = new TextView(this);
                    int textViewId = View.generateViewId();
                    textView.setId(textViewId);
                    String nameDesc = "Name : " + name;
                    textView.setText(nameDesc);


                    TextView textView2 = new TextView(this);
                    int textViewId2 = View.generateViewId();
                    textView2.setId(textViewId2);
                    String thisDesc = "Description : " + desc;
                    textView2.setText(thisDesc);


                    Button button = new Button(this);
                    int thisId = View.generateViewId();
                    buttonIds.add(thisId);
                    button.setText("Add to Order");

                    button.setOnClickListener(v -> addToOrder(name, productId, thisId, timer));

                    layout.addView(textView);
                    layout.addView(textView2);
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


    void addToOrder(String name, int productId, int thisId, Long timer){



        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            runOnUiThread(() -> {

                for(int buttins : this.buttonIds){
                    Button myButton = findViewById(buttins);
                    if (myButton != null) myButton.setVisibility(View.GONE);

                }
                Button order = findViewById(R.id.order);
                order.setEnabled(true);
                order.setVisibility(View.VISIBLE);
                Toast.makeText(this, name+"added to Order", Toast.LENGTH_SHORT).show();
                if(timer == 0) {
                    this.transaction = new Transaction(productId, "Ready for Pickup");
                }else{
                    this.transaction = new Transaction(productId, "Preparing");
                }
                Button cancel = findViewById(R.id.cancel);
                cancel.setEnabled(true);
                cancel.setVisibility(View.VISIBLE);
                LinearLayout layout = findViewById(R.id.linearLayoutContainer);
                layout.setVisibility(View.GONE);

            });
        }).start();




    }

    void orderCompleted(){
        Log.i("test", String.valueOf(this.transaction.transactionId));
        Log.i("test", String.valueOf(this.transaction.productId));
        Transactiondb.insertTransaction(this.transaction);
        Button order = findViewById(R.id.order);
        order.setEnabled(false);
        order.setVisibility(View.GONE);
        Button cancel = findViewById(R.id.cancel);
        cancel.setEnabled(false);
        cancel.setVisibility(View.GONE);
        LinearLayout layout = findViewById(R.id.linearLayoutContainer);
        layout.setVisibility(View.VISIBLE);
    }
    void orderCancel(){
        Button order = findViewById(R.id.order);
        order.setEnabled(false);
        order.setVisibility(View.GONE);
        Button cancel = findViewById(R.id.cancel);
        cancel.setEnabled(false);
        cancel.setVisibility(View.GONE);
        LinearLayout layout = findViewById(R.id.linearLayoutContainer);
        layout.setVisibility(View.VISIBLE);

    }
}