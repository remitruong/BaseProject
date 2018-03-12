package com.appmob.projet_app_mob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appmob.projet_app_mob.R;

public class Account extends AppCompatActivity {
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        Bundle getText = getIntent().getExtras(); // récupère le mail entré par l'utililisateur lors de l'activité Connexion
        tvEmail.setText(getText.getString("Email"));
    }

    public void Creation(View v){
        Intent i = new Intent(Account.this, CreateProj.class);
        startActivity(i);
    }

    public void Visualisation(View v){
        Intent i = new Intent(Account.this, ViewProj.class);
        startActivity(i);
    }

    public void SetAlarm(View v){
        Intent i = new Intent(Account.this, AlarmActivity.class);
        startActivity(i);
    }

}
