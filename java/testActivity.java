package com.example.mypackage.carpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class testActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("drivers");

        //firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }

    public void logout(View view){
        firebaseAuth.signOut();
        finish();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void delete(View view){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(null);
        user.delete();
        Toast.makeText(this, "Successful profile deletion", Toast.LENGTH_LONG).show();
        logout(view);
    }

}
