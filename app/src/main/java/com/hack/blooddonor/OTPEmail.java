package com.hack.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OTPEmail extends AppCompatActivity {
String OTP,email,password;
PinView pin;
String comp;
Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_o_t_p_email);
        OTP=getIntent().getStringExtra("OTP");
        email=getIntent().getStringExtra("Email");
        password=getIntent().getStringExtra("Password");
        pin=(PinView)findViewById(R.id.pin);
        verify=(Button)findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.getText().toString().equals(OTP)){
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                    HashMap mp=new HashMap();
                    String email1="";
                    for(int i=0;i<email.length();i++)
                    {
                        if(email.charAt(i)=='@')
                            break;
                        email1+=email.charAt(i);
                    }
                    mp.put("Email",email);
                    mp.put("Password",password);
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).updateChildren(mp);
                    startActivity(new Intent(getApplicationContext(),PersonalInfo.class).putExtra("Email",email).
                            putExtra("Password",password));
                }
            }
        });



    }
}