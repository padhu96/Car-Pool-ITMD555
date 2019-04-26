package com.example.mypackage.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernm);
        password = findViewById(R.id.pswd);
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("Position","Inside onCreate LoginActivity");

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            checkWhatUser();
        }
    }

    public void signIn(View view) {
        Log.d("Position","Inside signIn()");

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(TextUtils.isEmpty(user)){
            Toast.makeText(this, "Enter an username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Enter a password",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //add progress bar functionality
                            Log.d("Success","SignIn successful");
                            checkWhatUser();
                        }
                        else{
                            Log.d("Fail","SignIn unsuccessful");
                            Toast.makeText(LoginActivity.this, "Wrong credentials",Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }

    public void checkWhatUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        myRef.child("riders").child(user.getUid()).child("Role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    if(dataSnapshot.getValue()!=null){
                        try{
                            if("Rider".equals(dataSnapshot.getValue().toString())){
                                finish();
                                Intent i = new Intent(LoginActivity.this, RiderActivity.class);
                                startActivity(i);
                            }
                            else{
                                Log.d("Rider Not Found","Value with rider user-id not found");
                            }
                        }catch(Exception e){
                            Log.d("Rider Error in Log-In identity Line 1",""+e);
                        }
                    }
                    else{
                        Log.d("Rider dataSnapshot.getValue()","is null");
                    }
                }catch(Exception e){
                    Log.d("Rider Error in Log-In identity Line 2",""+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Rider onCancelled","Cancelled");
            }
        });

        myRef.child("drivers").child(user.getUid()).child("Role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    if(dataSnapshot.getValue()!=null){
                        try{
                            if("Driver".equals(dataSnapshot.getValue().toString())){
                                finish();
                                Intent i = new Intent(LoginActivity.this, testActivity.class);
                                startActivity(i);
                            }
                            else{
                                Log.d("Driver Not Found","Value with user-id not found");
                            }
                        }catch(Exception e){
                            Log.d("Driver Error in Log-In identity Line 1",""+e);
                        }
                    }
                    else{
                        Log.d("Driver dataSnapshot.getValue()","is null");
                    }
                }catch(Exception e){
                    Log.d("Driver Error in Log-In identity Line 2",""+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Driver onCancelled","Cancelled");
            }
        });
    }

    public void driverSignUp(View view){
        Log.d("Position","Inside driver signUp()");
        finish();
        Intent i = new Intent(LoginActivity.this, DriverSignUpActivity.class);
        startActivity(i);

    }

    public void riderSignUp(View view){
        Log.d("Position","Inside rider signUp()");
        finish();
        Intent i = new Intent(LoginActivity.this, RiderSignUpActivity.class);
        startActivity(i);

    }

}
