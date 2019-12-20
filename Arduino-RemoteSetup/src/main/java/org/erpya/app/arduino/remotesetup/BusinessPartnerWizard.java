package org.erpya.app.arduino.remotesetup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusinessPartnerWizard extends AppCompatActivity {

    private Button mBoton;
    private Context mCont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_partner_wizard);


        mBoton = (Button) findViewById(R.id.id_boton);

        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCont, BusinessPartnerLocation.class);
                startActivity(intent);
            }
        });
    }

 public void Value(View v){
    EditText Code = (EditText) findViewById(R.id.Value);
    Log.d("Value", Code.getText().toString());
    }


 public void Name (View v){
    EditText Name = (EditText) findViewById(R.id.Name);
    Log.d("Name", Name.getText().toString());
 }

 public void Name2 (View v){
    EditText Name2 = (EditText)findViewById(R.id.Name2);
    Log.d("Name2", Name2.getText().toString());
    }
 public void GroupBusinessPartner (View v){
    EditText GroupBusinessPartner = (EditText) findViewById(R.id.C_BP_Group_ID);
    Log.d("C_BP_Group_ID",GroupBusinessPartner.getText().toString());
    }
}
