package com.example.familymap.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.familymap.R;
import com.example.familymap.model.Event;
import com.example.familymap.model.Person;
import com.example.familymap.model.localStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonActivity extends AppCompatActivity {

    public static final String personKey = "com.example.familymap.ui.main.personKey";
    private String personId;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView genderNameText;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<Event> listOfEvents;
    List<Person> listOfPeople;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        setTitle("Family Map: Person Details");

        personId = getIntent().getStringExtra(personKey);

        firstNameText = findViewById(R.id.personFirstNameTextView);
        lastNameText = findViewById(R.id.personLastNameTextView);
        genderNameText = findViewById(R.id.personGenderTextView);

        firstNameText.setText(localStorage.getInstance().getPersonById(personId).getFirstName());
        lastNameText.setText(localStorage.getInstance().getPersonById(personId).getLastName());
        genderNameText.setText(localStorage.getInstance().getPersonById(personId).getGender());

        if(localStorage.getInstance().getPersonById(personId).getGender().equals("m")) {
            genderNameText.setText("Male");
        }
        else {
            genderNameText.setText("Female");
        }

        expListView = (ExpandableListView) findViewById(R.id.eventsList);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this,listDataHeader, listOfEvents, listOfPeople, this);
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<Event>>();

        // Adding child data
        listDataHeader.add("Life Events");
        listDataHeader.add("Family");

        // Adding child data
        listOfEvents = localStorage.getInstance().getEventByPerson(personId);


        listOfPeople = localStorage.getInstance().getFamilyMembers(personId);
      //  listOfPeople.add(localStorage.getInstance().getPersonById(personId).getMotherID());


      //  listDataChild.put(listDataHeader.get(0), lifeEvents); // Header, Child data
       // listDataChild.put(listDataHeader.get(1), familyMembers);
    }




}
