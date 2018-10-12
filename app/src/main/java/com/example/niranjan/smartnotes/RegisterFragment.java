package com.example.niranjan.smartnotes;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterFragment extends Fragment {
    EditText reg_password;
    EditText reg_mailId;
    Button button;
    TextView tv;
EditText reg_name;
    private ProgressDialog pDialog;
    Fragment fragment;
    View rootView;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();

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
        rootView=inflater.inflate(R.layout.fragment_register, container, false);
        reg_mailId=(EditText)rootView.findViewById(R.id.input_reg_email);
        reg_password=(EditText)rootView.findViewById(R.id.input_reg_password);
        reg_name=(EditText)rootView.findViewById(R.id.input_name);
        button=(Button)rootView.findViewById(R.id.btn_reg);
        tv=(TextView)rootView.findViewById(R.id.link_login);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment=new LoginFragment();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.frame,fragment);
                ft.commit();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_mailId.getText().toString();
                String password = reg_password.getText().toString();
                String name=reg_name.getText().toString();

            }
        });


        return rootView;

    }
    public boolean login()
    {


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        JSONObject object=new JSONObject();
        try{
            object.put("email",reg_mailId);
            object.put("password",reg_password);
            object.put("password",reg_name);



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
