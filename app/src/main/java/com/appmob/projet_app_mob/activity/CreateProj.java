package com.appmob.projet_app_mob.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmob.projet_app_mob.ClasseBdd.Projet;
import com.appmob.projet_app_mob.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class CreateProj extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private EditText mNomP;
    private EditText mNomE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createproj);

        mNomP = (EditText) findViewById(R.id.etNomProjet);
        mNomE = (EditText) findViewById(R.id.etNomEtapes);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void writeEtape(View v) {
        if (TextUtils.isEmpty(mNomP.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Entrez un nom pour votre Projet. Celui-ci doit être différent de vos projets existants.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mNomE.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Entrez au moins une étape pour votre projet.", Toast.LENGTH_SHORT).show();
            return;
        }
        Projet p = new Projet();
        p.nommerEtapes(mNomE.getText().toString());
        Map<String, Object> postValues = p.toMap();
        mDatabase.child("users").child(userId).child("projets").child(mNomP.getText().toString()).setValue(p.toMap());
        Toast.makeText(CreateProj.this, "Projet crée !", Toast.LENGTH_LONG).show();
        mNomP.setText("");
        mNomE.setText("");
    }

}
