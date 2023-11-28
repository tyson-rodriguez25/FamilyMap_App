package com.example.familymap.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familymap.R;
import com.example.familymap.model.Event;
import com.example.familymap.model.Person;
import com.example.familymap.model.localStorage;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import org.w3c.dom.Text;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;


public class MapFragment extends Fragment {
    private GoogleMap map;
    private TextView eventDetails;
    private TextView personName;
    private MapView mapView;
    private ImageView genderIcon;
    private LinearLayout eventBar;
    private localStorage ls = localStorage.getInstance(); // Rename variable name
    private String eventId;


    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        eventDetails = (TextView) view.findViewById(R.id.locationText);
        personName = (TextView) view.findViewById(R.id.nameText);
        genderIcon = (ImageView) view.findViewById(R.id.genderIcon);
        eventBar = (LinearLayout) view.findViewById(R.id.eventBar);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                initMap();
            }
        });

        return view;

    }

    // really need to override all lifecycle methods
    // to make the MapView work correctly
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.clear();
                initMap();
            }
        });

    }


    void addMarker(Event e) {
        if (e.getEventType().equals("birth")) {
            LatLng latLng = new LatLng(e.getLatitude(), e.getLongitude());
            MarkerOptions options =
                    new MarkerOptions().position(latLng).title("").icon(defaultMarker(HUE_BLUE));
            Marker marker = map.addMarker(options);
            marker.setTag(e);
        }
        else if (e.getEventType().equals("marriage")) {
            LatLng latLng = new LatLng(e.getLatitude(), e.getLongitude());
            MarkerOptions options =
                    new MarkerOptions().position(latLng).title("").icon(defaultMarker(HUE_RED));
            Marker marker = map.addMarker(options);
            marker.setTag(e);

        }
        else if (e.getEventType().equals("death")) {
            LatLng latLng = new LatLng(e.getLatitude(), e.getLongitude());
            MarkerOptions options =
                    new MarkerOptions().position(latLng).title("").icon(defaultMarker(HUE_YELLOW));
            Marker marker = map.addMarker(options);
            marker.setTag(e);
        }
        else {
            LatLng latLng = new LatLng(e.getLatitude(), e.getLongitude());
            MarkerOptions options =
                    new MarkerOptions().position(latLng).title("").icon(defaultMarker(HUE_CYAN));
            Marker marker = map.addMarker(options);
            marker.setTag(e);
        }
    }

    void addMarkers() {
        Event[] eventList = ls.getE();
        for (Event evnt : eventList) {
            Person person = localStorage.getInstance().getPersonById(evnt.getPersonID());
            if (localStorage.getInstance().isMaleFilter()) {
                if (person.getGender().equals("m")) {
                    addMarker(evnt);
                }
            }
            if (localStorage.getInstance().isFemaleFilter()) {
                if (person.getGender().equals("f")) {
                    addMarker(evnt);
                }
            }
        }
    }

    void setMarkerListener() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final Event markedEvent = (Event)marker.getTag();
                eventActivityMap(markedEvent.getEventID());
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (eventId == null) {
            inflater.inflate(R.menu.main_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showToast("Search");
//                startActivity(SearchActivity.class);
                return true;    // return true when handled successfully
            case R.id.settings:
                showToast("Settings");
                startActivity(SettingsActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    void startActivity(Class aClass) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
    }



    void initMap() {

//        centerMap();
//        zoomMap(10);
//        setMapType();
//        setClickListener();
//        zoomMap(2);
        addMarkers();
//        setBounds();
        setMarkerListener();
//        drawLines();
        eventActivityMap(eventId);

    }
    void centerMap() {
        LatLng byu = new LatLng(40.2518, -111.6493);
        CameraUpdate update = CameraUpdateFactory.newLatLng(byu);
        map.moveCamera(update);
        map.addMarker(new MarkerOptions().position(byu));
    }

    public MapFragment() {
        // Required empty public constructor
    }

    public void eventActivityMap (String eventId) {
        if (eventId == null) {
            return;
        }
        final Event eventBlob = localStorage.getInstance().getEventById(eventId);
        final LatLng event = new LatLng(localStorage.getInstance().getEventById(eventId).getLatitude(), localStorage.getInstance().getEventById(eventId).getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLng(event);
        map.moveCamera(update);
        Person person = localStorage.getInstance().getPersonById(eventBlob.getPersonID());
        if (person.getGender().equals("m")) {
            Drawable mapIcon =  new IconDrawable(getContext(), FontAwesomeIcons.fa_male).
                    colorRes(R.color.colorPrimaryDark).sizeDp(40);

            genderIcon.setImageDrawable(mapIcon);

        }

        else {
            Drawable mapIcon =  new IconDrawable(getContext(), FontAwesomeIcons.fa_female).
                    colorRes(R.color.colorPrimaryDark).sizeDp(40);
            genderIcon.setImageDrawable(mapIcon);
        };
        personName.setText(person.getFirstName() + " " + person.getLastName());
        eventDetails.setText(eventBlob.getEventType().toUpperCase() + ": " + eventBlob.getCity() + ", " + eventBlob.getCountry() + " (" + eventBlob.getYear() + ")");
        eventBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.putExtra(PersonActivity.personKey, eventBlob.getPersonID());
                startActivity(intent);
            }
        });


    }

    public String setEventId(String eventId) {
        this.eventId = eventId;
        return eventId;
    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
