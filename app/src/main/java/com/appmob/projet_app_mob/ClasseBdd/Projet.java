package com.appmob.projet_app_mob.ClasseBdd;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by felii on 04/03/2018.
 */

public class Projet {
    public String nom;
    public String tabEtapes[] = null;

    public Projet() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public void nommerEtapes(String nomDesEtapes){
        tabEtapes = nomDesEtapes.split(" ");
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        for(int i=0;i<tabEtapes.length;i++){
            result.put(Integer.toString(i),tabEtapes[i]);
        }
        return result;
    }

    @Override
    public String toString() {
        String res = null;
        for(int i=0;i<tabEtapes.length;i++){
            res+=tabEtapes[i];
            res+= " ";
        }
        return res;
    }
}
