package com.example.n01204206.milestone;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TooManyListenersException;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private View view;
    DataStructure mData;


    private TextView moisture;
    private TextView temp;
    private TextView humidity;
    private TextView time;
    private EditText location;
    private Button getdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getDatabase();
        findAllViews();
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });


        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Initialization of Navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String stateSaved = savedInstanceState.getString("saved_state");
        String stateSaved2 = savedInstanceState.getString("saved_state2");
        String stateSaved3 = savedInstanceState.getString("saved_state3");
        String stateSaved4 = savedInstanceState.getString("saved_state4");
        String stateSaved5 = savedInstanceState.getString("saved_state5");
        if(stateSaved == null){
            Toast.makeText(Main2Activity.this, R.string.nosavedstate,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(Main2Activity.this, R.string.valuesstored,Toast.LENGTH_LONG).show();
            moisture.setText(stateSaved);
            temp.setText(stateSaved2);
            humidity.setText(stateSaved3);
            time.setText(stateSaved4);
            location.setText(stateSaved5);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String stateToSave = moisture.getText().toString();
        String stateToSave2 = temp.getText().toString();
        String stateToSave3 = humidity.getText().toString();
        String stateToSave4 = time.getText().toString();
        String stateToSave5 = location.getText().toString();
        outState.putString("saved_state",stateToSave);
        outState.putString("saved_state2",stateToSave2);
        outState.putString("saved_state3",stateToSave3);
        outState.putString("saved_state4",stateToSave4);
        outState.putString("saved_state5",stateToSave5);
        Toast.makeText(Main2Activity.this, R.string.values_saved,Toast.LENGTH_LONG).show();
    }

    private void findAllViews() {
        moisture = findViewById(R.id.readmoisture);
        temp = findViewById(R.id.readtemp);
        humidity = findViewById(R.id.readhumidity);
        time = findViewById(R.id.readtime);
        getdata = findViewById(R.id.getdata);
        location = findViewById(R.id.location);
    }
    private void getDatabase() {
        database = FirebaseDatabase.getInstance();


    }

    private void retrieveData() {

        String City = location.getText().toString();

       if(City == ""){
           Toast.makeText(this, R.string.entercity,Toast.LENGTH_LONG).show();
       }
       else{

        myRef = database.getReference(City);

                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        DataStructure ds = dataSnapshot.getValue(DataStructure.class);
                        moisture.setText(ds.getMoisture());
                        temp.setText(ds.getTemp());
                        humidity.setText(ds.getHumidity());
                        // time.setText(ds.getTime());
                        time.setText(convertTimestamp(ds.getTime()));

                    }


            //}



            private String convertTimestamp(String timestamp){

                long yourSeconds = Long.valueOf(timestamp);
                Date mDate = new Date(yourSeconds * 1000);
                DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
                return df.format(mDate);
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DataStructure ds = dataSnapshot.getValue(DataStructure.class);
                moisture.setText(ds.getMoisture());
                temp.setText(ds.getTemp());
                humidity.setText(ds.getHumidity());
                time.setText(convertTimestamp(ds.getTime()));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MapleLeaf", getString(R.string.dataloadingfailed), databaseError.toException());
            }


        });

        // TODO: Get the whole data array on a reference.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<DataStructure> arraylist = new ArrayList<DataStructure>();

                // TODO: Now data is reteieved, needs to process data.
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    // iterate all the items in the dataSnapshot
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        DataStructure dataStructure = new DataStructure();
                        dataStructure.setMoisture(a.getValue(DataStructure.class).getMoisture());
                        dataStructure.setTemp(a.getValue(DataStructure.class).getTemp());
                        dataStructure.setHumidity(a.getValue(DataStructure.class).getHumidity());
                       // dataStructure.setTime(a.getValue(DataStructure.class).getTime());
                        dataStructure.setTime(a.getValue(DataStructure.class).getTime());


                        arraylist.add(dataStructure);  // now all the data is in arraylist.
                        Log.d("MapleLeaf", "dataStructure " + dataStructure.getTime());
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting data failed, log a message
                Log.d("MapleLeaf", getString(R.string.dataloadingfailed), databaseError.toException());
            }
        });
    }}


        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        Fragment fragment = null;
        Bundle bundle = new Bundle();

        if (id == R.id.home) {
            fragment = new HomeFragment();

        } else if (id == R.id.settings) {
            //goes to setting page


                    Intent i = new Intent(Main2Activity.this,Settings.class);
                    startActivity(i);

        }
        else if (id == R.id.maps) {
            //goes to maps page
            Intent i = new Intent(Main2Activity.this,MapsActivity.class);
            startActivity(i);

        } else if (id == R.id.help) {
            //goes to help page
            intent = new Intent(getApplicationContext(),Help.class);

        } else if (id == R.id.logout) {
            //exit
/*
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
*/

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }

        if(intent != null) {
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


   public void onClick(View view) {
        Intent intent = new Intent(this, CameraOption.class);
        startActivity(intent);
    }
}

