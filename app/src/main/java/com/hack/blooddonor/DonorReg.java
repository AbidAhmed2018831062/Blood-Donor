package com.hack.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class DonorReg extends AppCompatActivity {
    Spinner blood;
    String bl[],di[],div[];
    EditText phone;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_reg);
        blood=(Spinner)findViewById(R.id.blood);
        bl=getResources().getStringArray(R.array.BloodTypes);
        ArrayAdapter<String> ar=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,bl);
        blood.setAdapter(ar);
        phone=(EditText)findViewById(R.id.phone);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.getText().toString().length() == 11) {
                    SessionManager sh2 = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);

                    SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
                    HashMap<String, String> hm = sh.returnData();
                    String email = hm.get(SessionManager.EMAIL);
                    String token = hm.get(SessionManager.TOKEN);
                    String name = hm.get(SessionManager.FULLNAME);
                    String url = hm.get(SessionManager.URL);
                    String phone1 = hm.get(SessionManager.PHONE);
                    String pass = hm.get(SessionManager.PASS);
                    String dis = hm.get(SessionManager.DISTRICT);
                    String div = hm.get(SessionManager.DIVISION);
                    String email1 = "";
                    sh2.loginSession(name,email,phone1,pass,url,"Yes",token,div,dis);
                    for (int i = 0; i < email.length(); i++) {
                        if (email.charAt(i) == '@')
                            break;
                        email1 += email.charAt(i);
                    }
                    Random rn=new Random();
                    long pk=rn.nextInt(10000000);

                    Donor d = new Donor(dis, div, phone.getText().toString(),
                            blood.getSelectedItem().toString(),email,url,name,token);
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Donor").setValue(d);
                    FirebaseDatabase.getInstance().getReference("Donor").child(div).
                            child(pk+"").setValue(email1);
                    FirebaseDatabase.getInstance().getReference("Donor").child(dis).
                            child(pk+"").setValue(email1);
                    FirebaseDatabase.getInstance().getReference("Donor").child(blood.getSelectedItem().toString()).
                            child(pk+"").setValue(email1);
                    FirebaseDatabase.getInstance().getReference("Donors").child(email1).setValue(d);
                    FirebaseDatabase.getInstance().getReference("Donor").child(blood.getSelectedItem().toString()).child(email).setValue(d);
                    Toast.makeText(getApplicationContext(),"You have successfully registered as a blood donor.",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),PostsandWatch.class));
                    finish();
                }
                else
                {
                    phone.setError("Enter a legal number!!");
                    phone.requestFocus();
                }
            }
        });

    }
}