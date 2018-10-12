package com.example.niranjan.smartnotes;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class Fetch_notes_Fragment extends Fragment {

Spinner spinner_departmet,spinner_semester,spinner_year,spinner_subject;
String departmentList[]=new String[]{"Civil","ECE","EEE","ME","CS","ECE"};
String[] semesterList=new String[]{"1","2","3","4","5","6","7","8"};
Button button;
View rootview;
    private ProgressDialog pDialog;
    String[] subjectList;
    String[] yearList;
    //ArrayList<String> yearListFromServer=new ArrayList<String>();
    ArrayList<String> subjectListFromServer;

String department,semester,subject,year;
    public Fetch_notes_Fragment() {
        // Required empty public constructor
    }


    public static Fetch_notes_Fragment newInstance(String param1, String param2) {
        Fetch_notes_Fragment fragment = new Fetch_notes_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_fetch_notes_, container, false);
       spinner_departmet=(Spinner)rootview.findViewById(R.id.spinner_department);
       spinner_semester=(Spinner)rootview.findViewById(R.id.spinner_semester);
       spinner_year=(Spinner)rootview.findViewById(R.id.spinner_year);
       spinner_subject=(Spinner)rootview.findViewById(R.id.spinner_subject);
        subjectListFromServer=new ArrayList<String>();
        button=(Button)rootview.findViewById(R.id.btn_search_notes);


        ArrayAdapter<String> depAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, departmentList);
      
       spinner_departmet.setAdapter(depAdapter);

        spinner_departmet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                department = parent.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        ArrayAdapter<String> semAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,semesterList);

        spinner_semester.setAdapter(semAdapter);

        spinner_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                semester = parent.getItemAtPosition(position).toString();
                setSpinner_year();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("DEPT",department);
                bundle.putString("SEM",semester);
                bundle.putString("YEAR",year);
                bundle.putString("SUB",subject);
                Intent intent=new Intent(getActivity(),NotesList.class);
                intent.putExtras(bundle);
                startActivity(intent);




            }
        });

        return rootview;

    }

public void setSpinner_year(){
         yearList=new String[]{"2016","2017","2018"};
     //   new GetYear().execute("url");


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_spinner_dropdown_item,yearList);

        spinner_year.setAdapter(yearAdapter);

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            year = parent.getItemAtPosition(position).toString();
            setSpinner_subject();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    });

}


public void setSpinner_subject(){
    // subjectList=new String[]{"data","mobile","bigdata","machine"};
    String d="http://engam.16mb.com/mobileGetSubList.php";
    //String url2=String.format(ChangeURL.URL+"/mobile?type=subList&sem=%1$s&dept=%2$s&year=%3$s",semester,department,year);
//String url=ChangeURL.URL+"/mobile?type=subList&sem="+semester+"&dept="+department+"&year="+year;
    RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
    StringRequest stringRequest=new StringRequest(Request.Method.GET, d, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try{


                // Getting JSON Array node
                JSONArray Details = new JSONArray(response);
                // looping through All Contacts
                for (int i = 0; i < Details.length(); i++) {
                    JSONObject c = Details.getJSONObject(i);

                    String itemSubject = c.getString("subject");
                    if(itemSubject!=null) {
                        subjectListFromServer.clear();
                        subjectListFromServer.add(itemSubject);
                    }


                    else{
                    subjectListFromServer.clear();
                    subjectListFromServer.add("Empty");
                    }

                }

                spinner_subject.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subjectListFromServer));
            }catch (JSONException e){e.printStackTrace();}
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    });
    int socketTimeout = 30000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    stringRequest.setRetryPolicy(policy);
    requestQueue.add(stringRequest);


    spinner_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            subject = parent.getItemAtPosition(position).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
}

}
