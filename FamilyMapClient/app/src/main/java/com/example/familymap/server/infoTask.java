package com.example.familymap.server;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.familymap.MainActivity;
import com.example.familymap.model.Event;
import com.example.familymap.model.Person;
import com.example.familymap.model.localStorage;


public class infoTask extends AsyncTask<String, Integer, Person> {
    private Context DataContext;

    public infoTask(Context dataContext) {
        this.DataContext = dataContext;
    }

    @Override
    protected Person doInBackground(String... strings) {
        proxy prox = new proxy(strings[0], strings[1]);
        try {
            Person[] p = prox.person(strings[2]);
            Event[] e = prox.event(strings[2]);

            localStorage ld = localStorage.getInstance();
            ld.setP(p);
            ld.setE(e);

            Person user = null;
            for(Person person:p){
                if(person.getPersonID().equals(strings[3])){
                    user = person;
                }
            }

            return user;

        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Person person) {
        super.onPostExecute(person);

        if(person != null){

            String name = (person.getFirstName() + " " + person.getLastName());
            Toast.makeText(DataContext, name, Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(DataContext,"Wrong Credentials.", Toast.LENGTH_SHORT).show();

        }
    }
}
