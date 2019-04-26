package com.example.mypackage.carpool;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiderSignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText name, phone_no;
    private EditText newUsrName, newPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("riders");

        name = findViewById(R.id.name);
        phone_no = findViewById(R.id.phone_no);
        newUsrName = findViewById(R.id.createUsernm);
        newPswd = findViewById(R.id.createPswd);

    }


    public void cancelBtn(View view){
        Log.d("Postion","Inside driver create profile. Cancel button clicked");
        Intent i = new Intent(RiderSignUpActivity.this,LoginActivity.class);
        startActivity(i);
    }

    public void createBtn(View view){
        Log.d("Postion","Inside driver create profile");

        //Getting values
        String n = name.getText().toString().trim();
        String pno = phone_no.getText().toString().trim();
        String r = "Rider";
        String user = newUsrName.getText().toString().trim();
        String pass = newPswd.getText().toString().trim();


        if(TextUtils.isEmpty(n)){
            Toast.makeText(this, "Enter a name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pno)){
            Toast.makeText(this, "Enter a phone.no",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pno.length()!=10){
            Toast.makeText(this, "Please enter 10 digits for phone number please",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(user)){
            Toast.makeText(this, "Enter a username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Enter a password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<6){
            Toast.makeText(this, "Password must be atleast 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        final ProfileInformation profileInformation = new ProfileInformation(n,pno,r);
        firebaseAuth.createUserWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(profileInformation);
                            Log.d("Success","Rider Creation successful");
                            Toast.makeText(getApplicationContext(),"Successful Rider Registration.Please login to continue",Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(RiderSignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        else{
                            Log.d("Failure","Rider Registration successful");
                            Toast.makeText(RiderSignUpActivity.this,"Error : Email format is wrong or it already exists.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
