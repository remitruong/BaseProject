package com.appmob.projet_app_mob.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.Animation;
import android.content.Intent;
import android.view.View;

import com.appmob.projet_app_mob.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tx = (TextView)findViewById(R.id.Welcome);
        Typeface police_custom = Typeface.createFromAsset(getAssets(),"fonts/PT_Sans-Web-Regular.ttf");
        tx.setTypeface(police_custom);
        Animation a =(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.introduction));
        tx.startAnimation(a);
        a.setAnimationListener(new Animation.AnimationListener() {
            Button btn1 = (Button)findViewById(R.id.InscBtn);
            Button btn2 = (Button)findViewById(R.id.loginBtn);
            @Override
            public void onAnimationStart(Animation animation) {
                btn1.setAlpha(0f);
                btn2.setAlpha(0f);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AlphaAnimation fadeReverse = new AlphaAnimation(0f,1f);

                fadeReverse.setDuration(500);
                btn1.setAlpha(1f);
                btn2.setAlpha(1f);
                btn1.startAnimation(fadeReverse);
                btn2.startAnimation(fadeReverse);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * Fonction qui ouvre la nouvelle activité connexion
     *@param : view fait référence dans notre cas au bouton connexion
     */
    public void connexion(View view){
        Intent coPage = new Intent(MainActivity.this, Connexion.class);
        startActivity(coPage);
    }

    /**
     * Fonction qui ouvre la nouvelle activité inscription
     *@param : view fait référence dans notre cas au bouton inscription
     */
    public void inscription(View view){
        Intent coPage = new Intent(MainActivity.this, Inscription.class);
        startActivity(coPage);
    }
}
