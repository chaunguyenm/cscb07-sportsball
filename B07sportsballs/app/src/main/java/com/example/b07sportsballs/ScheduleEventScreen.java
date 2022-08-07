package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleEventScreen extends AppCompatActivity {

    Spinner venueSpinner;
    List<String> venueName = new ArrayList<>();

    EditText nameText;
    EditText maxText;

    Button startButton;
    Button endButton;

    int startHour, startMinute, endHour, endMinute; //set to negative 1

    Date Start, End;
    Boolean startTime, startDate, endTime, endDate; //Have they picked the start time and date yet?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);

        /*
        Access list of venues from customer
         */

        venueName.add("TPASC");
        venueName.add("UOFT");

        venueSpinner = (Spinner) findViewById(R.id.ScheduleEventScreen_Spinner_Venue);
        ArrayAdapter<String> adapter = new ArrayAdapter(ScheduleEventScreen.this, android.R.layout.simple_spinner_item, venueName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        venueSpinner.setAdapter(adapter);

        nameText = (EditText) findViewById(R.id.ScheduleEventScreen_EditText_Name);
        startButton = (Button) findViewById(R.id.ScheduleEventScreen_EditText_StartTime);
        endButton = (Button) findViewById(R.id.ScheduleEventScreen_EditText_EndTime);
        maxText = (EditText) findViewById(R.id.ScheduleEventScreen_EditText_Max);

        Start = Calendar.getInstance().getTime(); //or get current date
        End = Calendar.getInstance().getTime();
        startTime = false;
        startDate = false;
        endTime = false;
        endDate = false;

    }


    /*
    Called when the submit button is pressed. Used to show Event
     */
    public void onSubmit(View view)
    {

        String name = nameText.getText().toString();

        if(name.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "Event Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(name.contains("/"))
        {
            Toast.makeText(ScheduleEventScreen.this, "Event Name cannot contain slashes", Toast.LENGTH_LONG).show();
            return;
        }

        String venue = venueSpinner.getSelectedItem().toString();

        if(venue.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "A venue must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(!startTime)
        {
            Toast.makeText(ScheduleEventScreen.this, "A start time must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(!endTime)
        {
            Toast.makeText(ScheduleEventScreen.this, "An end time must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        String max = maxText.getText().toString();
        if(max.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "A maximum occupancy must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        //Input field only accepts numbers so no try-catch statement neccessary here
        if(Integer.parseInt(max) <= 0)
        {
            Toast.makeText(ScheduleEventScreen.this, "A maximum occupancy of at least one is required", Toast.LENGTH_LONG).show();
            return;
        }

        int maxPlayer = Integer.parseInt(max);

        //Get Customer
        String host = Customer.username;

        Event newEvent = new Event(null, null, name, host, venue, Start, End, 0, maxPlayer, null);

        //Some sanitation before it gets to customer method
        //Name checking, date checking, and maximum occupancy checking are handled by customer

        Log.i("Event Created", newEvent.getName() + " at " + newEvent.getLocation() + " from " + newEvent.getStartTime() + " to " + newEvent.getEndTime() + " with " + newEvent.getCurrPlayers() + "/" + newEvent.getMaxPlayers());


        //Customer.scheduleEvent();
    }

    public void pickTime(View view, Date date)
    {
        TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                date.setHours(hour);
                date.setMinutes(min);
                startButton.setText(new SimpleDateFormat("HH:mm").format(date));
            }

        };

        TimePickerDialog startDialog = new TimePickerDialog(ScheduleEventScreen.this, startListener, date.getHours(), date.getMinutes(), true);
        startDialog.setTitle("Select Time");
        startDialog.show();
    }


    public void pickStartTime(View view) {

        pickTime(view, Start);
        startTime = true;

    }

    public void pickEndTime(View view) {

        pickTime(view, End);
        endTime = true;

    }
}