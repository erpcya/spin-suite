package org.erpya.app.businesspartner.remotesetup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.erpya.app.businesspartner.R;
import org.erpya.app.businesspartner.initialsetup.BusinessPartnerWizard;

public class SpinerActiviity extends AppCompatActivity {

    private Button mBoton;
    private Context mCont = this;
    private String[] Proof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiner_activiity);

        mBoton = (Button) findViewById(R.id.button2);

        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCont, BusinessPartnerWizard.class);
                startActivity(intent);
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.Region);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Region, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//       spinner.setAdapter(adapter);

    }
}