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

public class BusinessPartner extends AppCompatActivity {

    private Button mBoton;
    private Context mCont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_partner_activity);


        mBoton = (Button) findViewById(R.id.id_boton);

        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCont, BusinessPartnerLocation.class);
                startActivity(intent);
            }
        });
    }

    public void Value(View v) {
        EditText Code = (EditText) findViewById(R.id.Code);
        Log.d("code", Code.getText().toString());

    }


    public void Name(View v) {
        EditText Name = (EditText) findViewById(R.id.Name);
        Log.d("Name", Name.getText().toString());
    }

    public void Name2(View v) {
        EditText Name2 = (EditText) findViewById(R.id.Name2);
        Log.d("Name2", Name2.getText().toString());
    }

    public void BusinessPartnerGroup(View view) {
        EditText BusinessPartnerGroup = (EditText) findViewById(R.id.C_BP_Group_ID);
        Log.d("BusinessPartnerGroup", BusinessPartnerGroup.getText().toString());
    }
}
