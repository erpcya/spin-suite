package org.erpya.app.businesspartner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusinessPartneLocation extends AppCompatActivity {

    private Button mBoton;
    private Context mCont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_partne_location);

        mBoton = (Button) findViewById(R.id.boton);

        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCont, BusinessPartnerAddress.class);
                startActivity(intent);
            }
        });
    }

    public void Address1(View v) {
        EditText Address1 = (EditText) findViewById(R.id.Address1);
        Log.d("Address1", Address1.getText().toString());
    }

    public void Address2(View v) {
        EditText Address2 = (EditText) findViewById(R.id.Address2);
        Log.d("Address2", Address2.getText().toString());
    }

    public void Address3(View v) {
        EditText Address3 = (EditText) findViewById(R.id.Address3);
        Log.d("Address3", Address3.getText().toString());
    }

    public void Address4(View v) {
        EditText Address4 = (EditText) findViewById(R.id.Address4);
        Log.d("Address4", Address4.getText().toString());
    }

    public void Name(View v) {
        EditText Name = (EditText) findViewById(R.id.Name);
        Log.d("Name", Name.getText().toString());
    }
}

