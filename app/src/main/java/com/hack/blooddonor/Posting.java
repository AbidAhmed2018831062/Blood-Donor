package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class Posting extends AppCompatActivity {
String bl[],di[],div[];
ArrayAdapter<String> adapter;
Spinner blood,division;
    AutoCompleteTextView district;
EditText patient,phone,location,disease;
Button submit;
    int year,cmonth,day,day1,cmonth1,year1;
    Calendar en= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_posting);
        bl=getResources().getStringArray(R.array.BloodTypes);
        di=getResources().getStringArray(R.array.districts);
        blood=(Spinner)findViewById(R.id.blood);
        district=(AutoCompleteTextView) findViewById(R.id.district);
        district.setThreshold(1);
        adapter = new ArrayAdapter<String>(Posting.this,
                android.R.layout.simple_list_item_1, di);
        district.setAdapter(adapter);
        division=(Spinner)findViewById(R.id.division);
        div=getResources().getStringArray(R.array.divisions);
        ArrayAdapter<String> ar=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,bl);
        blood.setAdapter(ar);
        district.setAdapter(adapter);
        ArrayAdapter<String> ar2=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,div);
        division.setAdapter(ar2);
        blood=(Spinner)findViewById(R.id.blood);
        patient=(EditText)findViewById(R.id.patient);
        phone=(EditText)findViewById(R.id.phone);
        disease=(EditText)findViewById(R.id.disease);
        submit=(Button)findViewById(R.id.submit);
        location=(EditText)findViewById(R.id.location);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String blood_type=blood.getSelectedItem().toString();
                if(validatePhone()&&validateAddress()&&validateDisease()&&valodatePatient()) {
                    SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
                    HashMap<String, String> hm = sh.returnData();
                    String email = hm.get(SessionManager.EMAIL);
                    String name = hm.get(SessionManager.FULLNAME);
                    String url = hm.get(SessionManager.URL);
                    String email1 = "";
                    for (int i = 0; i < email.length(); i++) {
                        if (email.charAt(i) == '@')
                            break;
                        email1 += email.charAt(i);
                    }


                    Calendar cal = Calendar.getInstance();
                    int cday = cal.get(Calendar.DAY_OF_MONTH);
                    int cmonth = cal.get(Calendar.MONTH);
                    int cy = cal.get(Calendar.YEAR);
                    String month = "";
                    if (cmonth == 1 - 1)
                        month = "January";
                    else if (cmonth == 2 - 1)
                        month = "February";
                    else if (cmonth == 3 - 1)
                        month = "March";
                    else if (cmonth == 4 - 1)
                        month = "April";
                    else if (cmonth == 5 - 1)
                        month = "May";
                    else if (cmonth == 6 - 1)
                        month = "June";
                    else if (cmonth == 7 - 1)
                        month = "July";
                    else if (cmonth == 8 - 1)
                        month = "August";
                    else if (cmonth == 9 - 1)
                        month = "September";
                    else if (cmonth == 10 - 1)
                        month = "October";
                    else if (cmonth == 11 - 1)
                        month = "November";
                    else month = "December";
                    Random rn=new Random();
                    long gh=rn.nextInt(10000000);  SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
                    String time=sdf.format(cal.getTime());

                    PostingData pd = new PostingData(blood_type, patient.getText().toString(), disease.getText().toString(),
                            location.getText().toString(), phone.getText().toString(),cday + " " + month + " " + cy,url,name,email,district.getText().toString(),division.getSelectedItem().toString(),time,name,gh+"");


                    String finalMonth = month;
                    String finalMonth1 = month;
                    FirebaseDatabase.getInstance().getReference("Donor").child(division.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot d:snapshot.getChildren())
                            {
                                String em=d.getValue().toString();   String em1=em+"@gmail.com";
                                if(em1.equals(email))
                                    continue;

                                FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            Donor dn=snapshot.getValue(Donor.class);
                                           // Toast.makeText(getApplicationContext(),dn.getBlood()+" "+blood_type,Toast.LENGTH_LONG).show();
                                            if(dn.getBlood().equals(blood_type)){
                                                String ema="";
                                                for(int i=0;i<em.length();i++)
                                                {
                                                    if(em.charAt(i)=='@')
                                                        break;
                                                    ema+=em.charAt(i);
                                                }
                                                HashMap op=new HashMap();
                                                op.put("Clicked","No");
                                                NotiData nd = new NotiData(blood_type, patient.getText().toString(), disease.getText().toString(),
                                                        location.getText().toString(), phone.getText().toString(),cday + " " + finalMonth1 + " " + cy,url,name,email,district.getText().toString(),"Urgent "+blood_type+" blood is needed at "+division.getSelectedItem().toString(),time,name,gh+"");
                                                FirebaseDatabase.getInstance().getReference("Users").child(ema).child("Clicked").child(cday + " " + finalMonth + " " + cy+" "+time).setValue(op);
                                                FirebaseDatabase.getInstance().getReference("Users").child(ema).child("Notifications").
                                                        child(cday + " " + finalMonth + " " + cy+" "+time).setValue(nd);
                                              //  Toast.makeText(getApplicationContext(),dn.getToken(),Toast.LENGTH_LONG).show();
                                            FcmNotificationsSender fcm = new FcmNotificationsSender(dn.getToken(), "Blood Needed", blood_type+" blood is needed at "+
                                                    location.getText().toString()+", "+district.getText().toString(), getApplicationContext(), Posting.this);
                                           // Toast.makeText(getApplicationContext(), dn.getToken(), Toast.LENGTH_LONG).show();
                                            fcm.SendNotifications();
                                            }
                                        }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    FirebaseDatabase.getInstance().getReference("Posts").child(month).child(cday + " " + month + " " + cy+" "+gh).setValue(pd);
                    FirebaseDatabase.getInstance().getReference("PostsDi").child(district.getText().toString()).child(month).child(cday + " " + month + " " + cy+" "+gh).setValue(pd);
                    FirebaseDatabase.getInstance().getReference("PostsDi").child(division.getSelectedItem().toString()).child(month).child(cday + " " + month + " " + cy+" "+gh).setValue(pd);
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Posts").child(month).child(cday + " " + month + " " + cy+" "+gh).setValue(pd);
                   startActivity(new Intent(getApplicationContext(),PostsandWatch.class));
                   finish();
                }
            }
        });
    }
    public boolean validatePhone()
    {
        String pho=phone.getText().toString();
        if(pho.length()<11)
        {
            phone.setError("Insert a legal number!!");
            phone.requestFocus();
            return false;
        }
        return true;
    }
    public boolean validateAddress()
    {
        String address=location.getText().toString();
        if(address.equals("")||address.isEmpty())
        {
            location.setError("Address has to be entered!!");
            location.requestFocus();
            return false;
        }
        return true;
    }
    public boolean valodatePatient()
    {
        String pa=patient.getText().toString();
        if(pa.equals("")||pa.isEmpty())
        {
            patient.setError("Patient's name has to be added!!");
            patient.requestFocus();
            return false;
        }
        return true;
    }
    public boolean validateDisease()
    {
        String pa=disease.getText().toString();
        if(pa.equals("")||pa.isEmpty())
        {
            disease.setError("Patient's disease has to be mentioned!!");
            disease.requestFocus();
            return false;
        }
        return true;
    }
}