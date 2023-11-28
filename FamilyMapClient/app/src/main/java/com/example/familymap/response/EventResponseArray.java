package com.example.familymap.response;

import java.util.Arrays;

import com.example.familymap.model.Event;

/** gson model class for responses to /event */
public class EventResponseArray extends InheritResponse {
    /** array of events owned by user */
    private Event[] data;

    public EventResponseArray(Event[] data) {
        setEvents(data);
    }

    public Event[] getEvents() {
        return data;
    }

    public void setEvents(Event[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventResponseArray [data=" + Arrays.toString(data) + "]";
    }
    
}