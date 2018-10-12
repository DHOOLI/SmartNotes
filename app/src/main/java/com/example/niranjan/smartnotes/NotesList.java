package com.example.niranjan.smartnotes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

public class NotesList extends AppCompatActivity {
  public CustomAdapter adapter;

   public RecyclerView.LayoutManager layoutManager;
    public  RecyclerView recyclerView;
   public ArrayList<DataModel> data;
    private ProgressDialog pDialog;
    ImageView imageView;
    TextView textView;
    String department,semester,year,subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
       
        getSupportActionBar().setTitle("Availiable Notes");
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.notes_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Cart.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        textView=(TextView)findViewById(R.id.No_notes_text);
        imageView=(ImageView)findViewById(R.id.imageView_no_records);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
             department = bundle.getString("DEPT");
             semester = bundle.getString("SEM");
            year = bundle.getString("YEAR");
             subject = bundle.getString("SUB");

        }

        data= new ArrayList<DataModel>();
       String d="http://engam.16mb.com/mobileGetNotes.php";
      // String url2=String.format(ChangeURL.URL+"/mobile?type=notesList&subject=%1$s&sem=%2$s&dept=%3$s&year=%4$s",subject,semester,department,year);
//String url=ChangeURL.URL+"/mobile?type=notesList&subject="+subject+"&sem="+semester+"&dept="+department+"&year="+year;
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET,d
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{


                    // Getting JSON Array node
                    JSONArray Details = new JSONArray(response);
                    // looping through All Contacts
                    for (int i = 0; i < Details.length(); i++) {
                        JSONObject c = Details.getJSONObject(i);

                        String item_id = c.getString("_id");
                        String item_subject = c.getString("subject");
                        JSONArray array_notes=c.getJSONArray("notes");
                        JSONObject notesObject=array_notes.getJSONObject(0);
                        String item_title=notesObject.getString("title");
                        String item_link=notesObject.getString("link");
                        String item_price=String.valueOf(notesObject.getInt("price"));

                        data.add(new DataModel(
                                item_title,item_subject,item_id,item_price,item_link));

                    }

                    if(!data.isEmpty()) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        adapter = new CustomAdapter(getApplicationContext(), data);
                        recyclerView.setAdapter(adapter);
                    }
                    else if(data.isEmpty())
                    {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("No Records Availiable for this search..");



                    }



                }catch (JSONException e){
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("No Records Availiable for this search..");

                    e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("No Records Availiable for this search..");

                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }




}
