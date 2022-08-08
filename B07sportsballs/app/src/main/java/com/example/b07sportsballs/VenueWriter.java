package com.example.b07sportsballs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VenueWriter extends AppCompatActivity
{

    public VenueWriter() {}


    //Writes a venue into the database
    public void write(DatabaseReference ref, Venue venue)
    {
        String venue_name = venue.getName();
        ref.child("Root").child("Venues").child(venue_name).child("Events").setValue("");
        ref.child("Root").child("Admin").child("VenuesCreated").child(venue_name);
        Venue.allVenues.add(venue_name);

    }
}





