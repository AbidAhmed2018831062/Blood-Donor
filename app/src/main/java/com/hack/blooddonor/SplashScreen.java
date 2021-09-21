package com.hack.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {
String phone2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                New();
            }
        });
        t.start();


    }
    private void doWork() {
        for(int i=10;i<=60;i=i+20){
            try{
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void New() {
        SessionManager sh=new SessionManager(getApplicationContext(),SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        phone2=hm.get(SessionManager.EMAIL);
        if (phone2 == null || phone2.isEmpty()) {
         //     Toast.makeText(getApplicationContext(),phone2+"Abid",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
//            Toast.makeText(getApplicationContext(),phone2+"Abid",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), PostsandWatch.class));
            finish();
        }
    }

}