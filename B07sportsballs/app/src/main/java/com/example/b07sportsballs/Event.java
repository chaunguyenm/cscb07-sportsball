package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Event {

    //Is the DatabaseReference neccessary?

    private EventWriter writer;
    private EventReader reader;
    private String name, host, location;
    private Date startTime, endTime;
    private int currPlayers, maxPlayers;
    private DatabaseReference ref;

    //Note - reader, writer, and ref have no getters to avoid being written

    //For persistent listener
    public Event()
    {

    }

    public Event(EventWriter writer, EventReader reader, String name, String host, String location, Date startTime, Date endTime, int currPlayers, int maxPlayers, DatabaseReference ref) {
        this.writer = writer;
        this.reader = reader;
        this.name = name;
        this.host = host;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currPlayers = currPlayers;
        this.maxPlayers = maxPlayers;
        this.ref = ref;
    }

    public void readFromDatabase(DatabaseReference ref, EventReader.Updater updater)
    {
        reader.read(ref, new EventReader.Loader() {
            @Override
            public void DataisLoaded(Event event) {

            }

        }, updater);
    }

    /**
     * Writes Event to database under /Venues/[specificvenue]/Event with a key of Name
     * @param ref the root node of the database in question
     */
    public void writeToDatabase(DatabaseReference ref)
    {
        writer.write(ref, this);
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getLocation() {
        return location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getCurrPlayers() {
        return currPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setCurrPlayers(int currPlayers) {
        this.currPlayers = currPlayers;
    }

    public void setData(Event event)
    {
        this.name = event.getName();
        this.host = event.getHost();
        this.location = event.getLocation();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.currPlayers = event.getCurrPlayers();
        this.maxPlayers = event.getMaxPlayers();
    }
}