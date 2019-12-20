package org.erpya.app.businesspartner.remotesetup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.erpya.app.businesspartner.R;

public class BusinessPartnerLocation extends AppCompatActivity {

    private android.support.design.widget.FloatingActionButton FloatingActionButton;


    private Button mBoton;
    private Context mCont = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_partner_location);

        mBoton = (Button) findViewById(R.id.button);

        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCont, SpinerActiviity.class);
                startActivity(intent);
            }
        });
    }

    public void Address(View v) {
        EditText Address = (EditText) findViewById(R.id.Address);
        Log.d("Address", Address.getText().toString());
    }
    public void Address1(View v) {
        EditText Address1 = (EditText) findViewById(R.id.Address1);
        Log.d("Address1", Address1.getText().toString());
    }
    public void Address2(View v) {
        EditText Address2 = (EditText) findViewById(R.id.Address2);
        Log.d("Addres2", Address2.getText().toString());
    }
    public void Address3(View v) {
        EditText Address3 = (EditText) findViewById(R.id.Address3);
        Log.d("Address3", Address3.getText().toString());
    }
    public void Name(View v) {
        EditText Name = (EditText) findViewById(R.id.Name);
        Log.d("Name", Name.getText().toString());
    }


   }
