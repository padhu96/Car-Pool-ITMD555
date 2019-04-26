package com.example.mypackage.carpool;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RiderActivity extends AppCompatActivity {
    ListView listViewDrivers;
    List<ProfileInformation> driversList;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        databaseReference = FirebaseDatabase.getInstance().getReference("drivers");

        listViewDrivers = findViewById(R.id.listViewDrivers);
        driversList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        if(isServicesOk())
            callMap();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                driversList.clear();
                for( DataSnapshot driverSnapshot : dataSnapshot.getChildren()){
                    ProfileInformation driver = driverSnapshot.getValue(ProfileInformation.class);
                    driversList.add(driver);
                }
                DriverList adapter = new DriverList(RiderActivity.this, driversList);
                listViewDrivers.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void callMap(){
        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RiderActivity.this,MapActivity.class);
                startActivity(i);
            }
        });
    }

    //Checking google services
    public boolean isServicesOk(){
        Log.d("Yes","Checking services");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(RiderActivity.this);
        if(available == ConnectionResult.SUCCESS){
            Log.d("Yes","Connection and Services exist");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d("Yes","Works but has errors");
            int n1=0;
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(RiderActivity.this, available,n1);
            dialog.show();
        }
        else{
            Toast.makeText(RiderActivity.this, "NO", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    public void logout(View view){
        firebaseAuth.signOut();
        Toast.makeText(this, "Log out successful", Toast.LENGTH_LONG).show();
        finish();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}
