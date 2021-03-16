package com.example.inscriptionetudiants;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class inscriptionFormActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerProvince;
    private Spinner spinnerVille;
    private Spinner spinnerProgramme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_form);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_bar);



        TextView textActionBar = (TextView) findViewById(R.id.actionBar_text);
        textActionBar.setText("Inscription au programme");

        spinnerProvince = (Spinner) findViewById(R.id.spnProvince);
        spinnerVille = (Spinner) findViewById(R.id.spnVille);
        spinnerProgramme = (Spinner) findViewById(R.id.spnProgramme);

        ArrayAdapter<CharSequence> adapterProvince = ArrayAdapter.createFromResource(this, R.array.provinces, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterProgramme = ArrayAdapter.createFromResource(this, R.array.programmes, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerProvince.setAdapter(adapterProvince);
        spinnerProgramme.setAdapter(adapterProgramme);

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedProvince = parent.getItemAtPosition(position).toString();
                switch (selectedProvince) {
                    case "Québec":
                        spinnerVille.setAdapter(new ArrayAdapter<String>(inscriptionFormActivity.this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.villes_Quebec)));
                        break;
                    case "Ontario":
                        spinnerVille.setAdapter(new ArrayAdapter<String>(inscriptionFormActivity.this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.villes_Ontario)));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });

        Button inscription = (Button) findViewById(R.id.buttonCompleterInscription);
        Button clear = (Button) findViewById(R.id.buttonClear);
        Button cancel = (Button) findViewById(R.id.buttonCancel);

        inscription.setOnClickListener(this);
        clear.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCompleterInscription:
                Intent intentInformations = new Intent(inscriptionFormActivity.this, progressBarActivity.class);

                EditText nom = (EditText) findViewById(R.id.txtNom);
                EditText prenom = (EditText) findViewById(R.id.txtPrenom);
                EditText noCivique = (EditText) findViewById(R.id.txtNoCivique);
                EditText rue = (EditText) findViewById(R.id.txtRue);
                EditText telephone = (EditText) findViewById(R.id.txtTelephone);
                String selectedProvince = spinnerProvince.getSelectedItem().toString();
                String selectedVille = spinnerVille.getSelectedItem().toString();
                String selectedProgramme = spinnerProgramme.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                bundle.putString("prenom", prenom.getText().toString());
                bundle.putString("nom", nom.getText().toString());
                bundle.putString("noCivique", noCivique.getText().toString());
                bundle.putString("rue", rue.getText().toString());
                bundle.putString("telephone", telephone.getText().toString());
                bundle.putString("province", selectedProvince);
                bundle.putString("ville", selectedVille);
                bundle.putString("programme", selectedProgramme);
                intentInformations.putExtra("infos", bundle);

                startActivity(intentInformations);
                break;
            case R.id.buttonClear:
                clearForm((ViewGroup) findViewById(R.id.layoutForm));
                break;
            case R.id.buttonCancel:
                Intent intentCancel = new Intent(inscriptionFormActivity.this, MainActivity.class);
                startActivity(intentCancel);
                break;
        }
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }
}