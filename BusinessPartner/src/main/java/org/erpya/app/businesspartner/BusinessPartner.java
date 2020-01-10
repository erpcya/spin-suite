package org.erpya.app.businesspartner;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusinessPartner extends AppCompatActivity {

    private Button mBoton;
    private Context mCont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_partner);

        mBoton = (Button) findViewById(R.id.id_boton);

        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCont, BusinessPartneLocation.class);
                startActivity(intent);
            }
        });
    }



    public void Code(View v){
        EditText Code = (EditText) findViewById(R.id.Code);
        Log.d("Code", Code.getText().toString());
    }


        public void Name (View v){
            EditText Name = (EditText) findViewById(R.id.Name);
            Log.d("Name", Name.getText().toString());
        }

        public void Name2 (View v){
            EditText Name2 = (EditText) findViewById(R.id.Name2);
            Log.d("Name2", Name2.getText().toString());
        }
        public void GroupBusinessPartner (View v){
            EditText GroupBusinessPartner = (EditText) findViewById(R.id.C_BP_Group_ID);
            Log.d("C_BP_Group_ID", GroupBusinessPartner.getText().toString());
        }
    }

