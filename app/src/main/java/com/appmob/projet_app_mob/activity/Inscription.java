package com.appmob.projet_app_mob.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appmob.projet_app_mob.R;
import com.appmob.projet_app_mob.ClasseBdd.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Inscription extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mPassword = (EditText) findViewById(R.id.passwordField);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Fonction qui créer un utilisateur et ouvre l'activité connexion
     *@param : view fait référence dans notre cas au bouton valider
     */
    public void creerUtilisateur(View v) {
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Entrez une adresse email !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Entrez un mot de passe !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Le mot de passe doit contenir 6 caractères.", Toast.LENGTH_SHORT).show();
            return;
        }
        // petite pop up le temps de la création de l'utilisateur
        final ProgressDialog progressDialog = ProgressDialog.show(Inscription.this, "Patientez...", "Authentification...", true);
        // on crée un utilisateur dans firebase, avec les identifiants en paramètres
        (mAuth.createUserWithEmailAndPassword(mEmailField.getText().toString(), mPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        // Si c'est réussi, on l'indique avec un petit message et on lance l'activité connexion
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            Toast.makeText(Inscription.this, "Utilisateur enregistré avec succès !", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Inscription.this, Connexion.class);
                            startActivity(i);
                        }
                        // Sinon on indique que non avec un petit message et on indique l'erreur dans la console
                        else
                        {
                            Log.e("Erreur", task.getException().toString());
                            Toast.makeText(Inscription.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }
}
