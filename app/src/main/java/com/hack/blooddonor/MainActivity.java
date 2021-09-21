package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.mail.Message;


import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
EditText email,password,cpass;
Button submit;
String sEmail,sPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        cpass=(EditText)findViewById(R.id.Cpassword);
        submit=(Button)findViewById(R.id.submit);

        FirebaseDatabase.getInstance().getReference("sending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot s) {
              sEmail=s.child("email").getValue().toString();
              sPass=s.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(validateEmail()&&validatePassword())
              {
                  Random ra=new Random();
                  long p=100000+ra.nextInt(899999);
                  String subject= "OTP for Blood Donor App";
                  final String Email=sEmail;
                  final String pass=sPass;
                  Properties prop = new Properties();
                  prop.put("mail.smtp.host", "smtp.gmail.com");
                  prop.put("mail.smtp.port", "465");
                  prop.put("mail.smtp.auth", "true");
                  prop.put("mail.smtp.socketFactory.port", "465");
                  prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                  Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                      protected PasswordAuthentication getPasswordAuthentication() {
                          return new PasswordAuthentication(Email, pass);
                      }
                  });
                  try {

                      Message message = new MimeMessage(session);
                      message.setFrom(new InternetAddress(Email));
                      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getText().toString()));
                      message.setSubject("Sending Otp");
                      message.setText("Your OTP to change email address is "+ p);

                      new SendEmail().execute(message);

                  } catch (MessagingException e) {
                      e.printStackTrace();
                            /*StrictMode.ThreadPolicy po=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(po);*/
                  }
                  startActivity(new Intent(getApplicationContext(),OTPEmail.class).putExtra("OTP",p+"").putExtra("Email",email.getText().toString()
                  ).putExtra("Password",password.getText().toString()));
              }
            }
        });


    }
    private class SendEmail extends AsyncTask<Message, String, String> {

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }


        }
    }
    public boolean validateEmail()
    {
       String email1=email.getText().toString().trim();
        if(email1.isEmpty()){
            email.setError("This field needs to be filled");
            email.requestFocus();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Email is not valid");
            email.requestFocus();
            return false;

        }
        else
            return true;
    }
    public boolean validatePassword()
    {
       String pass1=password.getText().toString().trim();
        String cpass1=cpass.getText().toString().trim();
        if(pass1.isEmpty()){
            password.setError("This field needs to be filled");
            password.requestFocus();
            return false;
        }
        else if(pass1.length()<6)
        {
            password.setError("Password has to be at least of length 6");
            password.requestFocus();
            return false;
        }
        else if(!pass1.equals(cpass1)){
            password.setError("Password and Confirm Password have to be same");
            password.requestFocus();
            return false;
        }
        else
            return true;
    }
}