package com.example.familymap.ui.main;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymap.R;
import com.example.familymap.model.Event;
import com.example.familymap.model.Person;
import com.example.familymap.model.localStorage;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    List<Event> listOfEvents;
    List<Person> listOfPeople;
    Activity activity;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 List<Event> listOfEvents, List<Person> listOfPeople, Activity activity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listOfEvents = listOfEvents;
        this.listOfPeople = listOfPeople;
        this.activity = activity;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        if (groupPosition == 0) {
            return this.listOfEvents.get(childPosititon);
        }
        else {
            return this.listOfPeople.get(childPosititon);
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (groupPosition == 0) {
            final Event eventText = (Event) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.event_items, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.eventDetails);

            TextView nameOfPerson = (TextView) convertView
                    .findViewById(R.id.nameOfPerson);
            ImageView mapMarker = (ImageView) convertView
                    .findViewById(R.id.mapMarkerIcon);
            Person person = localStorage.getInstance().getPersonById(eventText.getPersonID());

            Drawable mapIcon =  new IconDrawable(activity, FontAwesomeIcons.fa_map_marker).
                    colorRes(R.color.colorPrimaryDark).sizeDp(40);
            txtListChild.setText(eventText.getEventType().toUpperCase() + ": " + eventText.getCity() +  ", " + eventText.getCountry() +  " (" + eventText.getYear() + ")");
            nameOfPerson.setText(person.getFirstName()+ " " + person.getLastName());
            mapMarker.setImageDrawable(mapIcon);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, EventActivity.class);
                    intent.putExtra(EventActivity.eventKey, eventText.getEventID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });


            return convertView;
        }
        else {
            final Person personText = (Person) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.event_items, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.eventDetails);

            TextView genderOfPerson = (TextView) convertView
                    .findViewById(R.id.nameOfPerson);
            ImageView mapMarker = (ImageView) convertView
                    .findViewById(R.id.mapMarkerIcon);
            final Person person = localStorage.getInstance().getPersonById(personText.getPersonID());

            if (person.getGender().equals("m")) {
                Drawable mapIcon =  new IconDrawable(activity, FontAwesomeIcons.fa_male).
                        colorRes(R.color.colorPrimaryDark).sizeDp(40);

                txtListChild.setText(person.getFirstName()+ " " + person.getLastName());
                genderOfPerson.setText("Male");
                mapMarker.setImageDrawable(mapIcon);

            }

            else {
                Drawable mapIcon =  new IconDrawable(activity, FontAwesomeIcons.fa_female).
                        colorRes(R.color.colorPrimaryDark).sizeDp(40);
                txtListChild.setText(person.getFirstName()+ " " + person.getLastName());
                genderOfPerson.setText("Female");
                mapMarker.setImageDrawable(mapIcon);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, PersonActivity.class);
                    intent.putExtra(PersonActivity.personKey,person.getPersonID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });


            return convertView;

        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return listOfEvents.size();
        }
        else {
            return listOfPeople.size();
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.event_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.eventListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
