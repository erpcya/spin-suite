package org.erpya.app.arduino.remotesetup;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class BusinessPartnerLocation extends AppCompatActivity {

    private FloatingActionButton FloatingActionButton;
    private Context mCont = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_partner_location);
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

   // FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.id_boton);

     //   floatingActionButton.setOnClickListener(new View.OnClickListener()

    {

       //   @Override
     //   public void onClick(View view){
           // Intent intent = new Intent(mCont, BusinessPartnerAddressWizard.class);
          //  startActivity(intent);

        }
    }

