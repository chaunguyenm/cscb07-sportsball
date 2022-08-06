package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.List;

public class CreateVenueScreen extends AppCompatActivity {


    public static Button addVenue;
    public static Button Quit;
    public static Button venueList;
    public static DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_venue_screen);
        ref = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference();
        Log.i("AdminLogin", "Startup");
        Log.i("CreateVenue", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");


        addVenue = (Button) findViewById(R.id.button6);

        Quit = (Button) findViewById(R.id.button5);

        venueList = (Button) findViewById(R.id.button7);

        venueList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listVenue();
            }
        });

        Quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quitcreate();
            }
        });

        addVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText VenueName = (EditText) findViewById(R.id.CreateVenueScreen_EditText_VenueName);
                String avenuename = VenueName.getText().toString();
                ref.child("Root").child("Venue").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean match = false;
                        for(DataSnapshot infoSnapshot : snapshot.getChildren()){
                            if(infoSnapshot!=null){
                                String vname = infoSnapshot.child("VenuesName").getValue().toString();
                                if(avenuename.isEmpty()){
                                    Toast.makeText(CreateVenueScreen.this, "Venue name cannot be empty", Toast.LENGTH_SHORT).show();
                                    match = true;
                                }
                                if(vname.equals(avenuename)){
                                    match = false;
                                    Toast.makeText(CreateVenueScreen.this, "This venue already exists, please try input other venues", Toast.LENGTH_SHORT).show();
                                }
                                if (!vname.equals(avenuename)){

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                Venue venue = new Venue(VenueName.getText().toString(), new HashSet<Event>());
//                DatabaseReference ref = FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
//                venue.writeToDataBase(ref);
//                venue.setName(VenueName.getText().toString());
//                venue.setEvents(new HashSet<Event>());
//                new AdminVenueWriter().addVenue(venue, new AdminVenueWriter.datastatus() {
//                    @Override
//                    public void isLoaded(List<Admin> admins, List<String> keys) {
//
//                    }
//
//                    @Override
//                    public void isInserted() {
//                        Toast.makeText(CreateVenueScreen.this, "This venue has been inserted successfully",
//                        Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void isDeleted() {
//
//                    }
//
//                    @Override
//                    public void isUpdated() {
//
//                    }
//                });


            }
        });

    }

    private void listVenue() {
        Intent intent = new Intent(this, VenueScreen.class);
        startActivity(intent);
    }

    private void Quitcreate() {
        this.finishAffinity();
    }


}