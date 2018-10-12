package com.example.niranjan.smartnotes;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Cart extends AppCompatActivity {

    private SQLiteOpenHelper sqliteOpenHelper;
android.support.v7.widget.Toolbar toolbar;
    public CustomAdapter2 adapter;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView recyclerView;
ImageView imageView;
ArrayList<DataModel> data;
    private ProgressDialog pDialog;
    TextView textView;
    CartHandler cartHandler;
    FloatingActionButton fab;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

     cartHandler = new CartHandler(this);
     imageView=(ImageView)findViewById(R.id.imageView_empty_cart);
        getSupportActionBar().setTitle("Place Your Order");
       fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (cartHandler.getProductsCount()>0) {
                   placeorder();
                    Snackbar.make(findViewById(R.id.forSnackBAr), "Your Order Is placed", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartHandler.deleteTable();
                            Intent intent = new Intent(getApplicationContext(), Cart.class);
                            startActivity(intent);
                        }
                    }).show();
                }
                else {
                    Snackbar.make(findViewById(R.id.forSnackBAr), "Cart is Empty", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Cart.class);
                            startActivity(intent);
                        }
                    }).show();

                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        textView=(TextView)findViewById(R.id.No_cart_text);
       data=cartHandler.getAllOrders();
       if(!data.isEmpty()) {
           fab.show();
           adapter = new CustomAdapter2(getApplicationContext(), data);
           recyclerView.setAdapter(adapter);
       }

        else {
            fab.hide();
           imageView.setVisibility(View.VISIBLE);
           textView.setVisibility(View.VISIBLE);
           textView.setText("Your Cart Is Empty");
       }

    }


    public boolean placeorder()
    {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        data=cartHandler.getAllOrders();
        Log.d("fine","fine");
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<data.size();i++)
        {
            String title=data.get(i).getNotes_title();
            String link=data.get(i).getLink();
            String price=data.get(i).getPrice();
            JSONObject  object=new JSONObject();
            try {
                object.put("title",title);
                object.put("link",link);
                object.put("price",price);

            }catch(JSONException e){
                Log.d("error","eer");
            }
            jsonArray.put(object);

        }
        String jsonStr=jsonArray.toString();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://engam.16mb.com/mobileGetSemList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (pDialog.isShowing())
                    pDialog.dismiss();


            }

            },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pDialog.isShowing())
                    pDialog.dismiss();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


        return true;
    }



}
