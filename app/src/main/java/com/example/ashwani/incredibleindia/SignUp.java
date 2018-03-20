package com.example.ashwani.incredibleindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import fr.ganfra.materialspinner.MaterialSpinner;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout usernameWrapper,passwordWrapper;
    private Button btnSignUp,btnGoogleSignUp,btnFacebookSignUp;
    private ProgressDialog progress;
    private MaterialSpinner countrySpinner,languageSpinner,genderSpinner;
    private ArrayAdapter<String> countryAdapter,languageAdapter,genderAdapter;
    final private String[] countryNames = {"Australia","China","France","Germany","United Kingdom"};
    final private String[] languages = {"English","Chinese","French","German"};
    final private String[] genders = {"Male","Female"};
    private String country,language,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();
    }

    private void initComponents(){
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Signing Up...");
        countrySpinner = (MaterialSpinner) findViewById(R.id.country);
        languageSpinner = (MaterialSpinner) findViewById(R.id.language);
        genderSpinner = (MaterialSpinner) findViewById(R.id.gender);
        countryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,countryNames);
        languageAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,languages);
        genderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,genders);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        languageSpinner.setAdapter(languageAdapter);
        genderSpinner.setAdapter(genderAdapter);
        Resources r =getResources();
        Drawable d = r.getDrawable(R.drawable.spin_back);
        countrySpinner.setBackground(d);
        languageSpinner.setBackground(d);
        usernameWrapper.setBackground(d);
        passwordWrapper.setBackground(d);
        genderSpinner.setBackground(d);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == -1){
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnFacebookSignUp = (Button) findViewById(R.id.btnFacebookSignUp);
        btnGoogleSignUp = (Button) findViewById(R.id.btnGoogleSignUp);
        btnFacebookSignUp.setOnClickListener(this);
        btnGoogleSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                String email = usernameWrapper.getEditText().getText().toString().trim();
                String password = passwordWrapper.getEditText().getText().toString().trim();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    progress.show();
                    signup(email, password);
                }
                else
                    Toast.makeText(getApplicationContext(),"Fields are empty!!!",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnFacebookSignUp:
                startActivity(new Intent(SignUp.this,MainActivity.class));
                finish();
                break;
            case R.id.btnGoogleSignUp:
                startActivity(new Intent(SignUp.this,MainActivity.class));
                finish();
        }
    }

    private void signup(String email, String password) {
        country = countrySpinner.getSelectedItem().toString().trim();
        language = languageSpinner.getSelectedItem().toString().trim();
        gender = genderSpinner.getSelectedItem().toString().trim();
        if(!TextUtils.isEmpty(country) && !TextUtils.isEmpty(language)) {
            startActivity(new Intent(SignUp.this,MainActivity.class));
            progress.dismiss();
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Fields are empty!!!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp.this,Login.class));
        finish();
    }
}