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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Connexion extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mPassword = (EditText) findViewById(R.id.passwordField);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Fonction qui connecte un utilisateur et ouvre la nouvelle activité account
     *@param : view fait référence dans notre cas au bouton connexion
     */
    public void connexionUser(View v) {
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
        // petite pop up le temps de la connexion de l'utilisateur
        final ProgressDialog progressDialog = ProgressDialog.show(Connexion.this, "Patientez...", "Authentification...", true);
        // on se connecte dans firebase avec les identifiants en paramètres
        (mAuth.signInWithEmailAndPassword(mEmailField.getText().toString(), mPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        // Si c'est réussi, on l'indique avec un petit message, on lance l'activité account et on envoye l'email
                        if (task.isSuccessful()) {
                            Toast.makeText(Connexion.this, "Connexion réussi !", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Connexion.this, Account.class);
                            i.putExtra("Email", mAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        }
                        // Sinon on indique que non avec un petit message et on indique l'erreur dans la console
                        else {
                            Log.e("Erreur.", task.getException().toString());
                            Toast.makeText(Connexion.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}
