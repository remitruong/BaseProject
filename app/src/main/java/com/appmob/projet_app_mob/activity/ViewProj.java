package com.appmob.projet_app_mob.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appmob.projet_app_mob.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProj extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mChild;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ListView mList;
    private ArrayList<String> aList = new ArrayList<>();
    private String cle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproj);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, aList);

        mList = (ListView) findViewById(R.id.listView);
        mList.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChild = mDatabase.child("users").child(userId).child("projets");
        mChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.i("okokok", ds.getValue().toString());
                        if(ds.getKey().contains("username") || (ds.getKey().contains("email"))){
                            Log.i("okokok", "blablbalblablabbablab");
                        }
                        else {
                            aList.add(ds.getKey().toString() + " :\n " + ds.getValue().toString());
                            adapter.notifyDataSetChanged();
                        }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
