package com.example.niranjan.smartnotes;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class LoginFragment extends Fragment {
EditText login_password;
EditText login_mailId;
Button button;
TextView tv;
Fragment fragment;
    private ProgressDialog pDialog;
    View rootview;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_login, container, false);
        login_mailId=(EditText)rootview.findViewById(R.id.input_login_email);
        login_password=(EditText)rootview.findViewById(R.id.input_login_password);
        button=(Button)rootview.findViewById(R.id.btn_login);
        tv=(TextView)rootview.findViewById(R.id.link_signup);
        
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment=new RegisterFragment();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.frame,fragment);
                ft.commit();
            }
        });
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = login_mailId.getText().toString();
            String password = login_password.getText().toString();
            Toast.makeText(getContext(),email+"//"+password,Toast.LENGTH_LONG).show();
        }
    });

        return rootview;
    }
    public boolean login()
    {


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

            JSONObject object=new JSONObject();
            try{
                object.put("email",login_mailId);
                object.put("password",login_password);


            }catch(JSONException e){
                Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_LONG).show();

            }

        String jsonStr=object.toString();
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
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
