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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverSignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    private EditText name, phone_no, destination,time,address;
    private EditText newUsrName, newPswd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("drivers");

        name = findViewById(R.id.name);
        phone_no = findViewById(R.id.phone_no);
        destination = findViewById(R.id.destination);
        time = findViewById(R.id.time);
        address = findViewById(R.id.address);
        newUsrName = findViewById(R.id.createUsernm);
        newPswd = findViewById(R.id.createPswd);

    }

    public void cancelBtn(View view){
        Log.d("Postion","Inside driver create profile. Cancel button clicked");
        Intent i = new Intent(DriverSignUpActivity.this,LoginActivity.class);
        startActivity(i);
    }

    public void createBtn(View view){
        Log.d("Postion","Inside driver create profile");

        //Getting values
        String n = name.getText().toString().trim();
        String pno = phone_no.getText().toString().trim();
        String d = destination.getText().toString().trim();
        String t = time.getText().toString().trim();
        String r = "Driver";
        String a = address.getText().toString().trim();
        String user = newUsrName.getText().toString().trim();
        String pass = newPswd.getText().toString().trim();

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
        if(TextUtils.isEmpty(d)){
            Toast.makeText(this, "Enter destination",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(t)){
            Toast.makeText(this, "Enter the time",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(a)){
            Toast.makeText(this, "Enter an address ",Toast.LENGTH_SHORT).show();
            return;
        }


        final ProfileInformation profileInformation = new ProfileInformation(n,d,t,r,pno,a);
        firebaseAuth.createUserWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(profileInformation);
                            if(deleteExtra(user.getUid())){
                                Log.d("Success"," Driver Creation successful");
                                Toast.makeText(DriverSignUpActivity.this,"Successful Driver Registration.",Toast.LENGTH_LONG).show();
                                finish();
                                Intent i = new Intent(DriverSignUpActivity.this, testActivity.class);
                                startActivity(i);
                            }

                        }
                        else{
                            Log.d("Failure"," Driver Registration successful");
                            Toast.makeText(getApplicationContext(),"Error : Email format is wrong or it already exists.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public boolean deleteExtra(String id){
        databaseReference.child(id).child("theName").setValue(null);
        databaseReference.child(id).child("theAddress").setValue(null);
        databaseReference.child(id).child("theDestination").setValue(null);
        databaseReference.child(id).child("thePhone_no").setValue(null);
        databaseReference.child(id).child("theTime").setValue(null);
        return true;
    }

}
