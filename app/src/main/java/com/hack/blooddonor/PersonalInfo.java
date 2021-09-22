package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class PersonalInfo extends AppCompatActivity {
String email,pass,phone,name,url;
EditText na,pho;
ImageView profile;
Button cimg,submit;
    Uri imgUri;
    StorageReference st;
    int count=1;
    String di[],div[];
    Spinner division;
    AutoCompleteTextView district;
    ArrayAdapter<String> adapter;
    int d=0;
    StorageTask uploadtask;
    private static final int IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personal_info);
        district=(AutoCompleteTextView) findViewById(R.id.district);
        di=getResources().getStringArray(R.array.districts);
        district.setThreshold(1);
        adapter = new ArrayAdapter<String>(PersonalInfo.this,
                R.layout.education, di);
        district.setAdapter(adapter);
        division=(Spinner)findViewById(R.id.division);

        div=getResources().getStringArray(R.array.divisions);
        ArrayAdapter<String> a1r=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,di);
        district.setAdapter(a1r);
        ArrayAdapter<String> ar2=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,div);
        division.setAdapter(ar2);
        na=(EditText)findViewById(R.id.name);
        pho=(EditText)findViewById(R.id.phone);
        st= FirebaseStorage.getInstance().getReference("Upload");
        profile=(ImageView)findViewById(R.id.profile);
        cimg=(Button) findViewById(R.id.cimg);
        submit=(Button) findViewById(R.id.submit);
        email=getIntent().getStringExtra("Email");
        pass=getIntent().getStringExtra("Password");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadtask!=null&&uploadtask.isInProgress())
                {
                    Toast.makeText(getApplicationContext(),"Uploading In Progress",Toast.LENGTH_LONG).show();
                }else
                    uploadImage();


            }
        });
        cimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data.getData() != null)){
            imgUri=data.getData();
            profile.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            Picasso.with(this).load(imgUri).into(profile);

        }
    }

    private void openImageChooser()
    {


        Intent in=new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, IMAGE_REQUEST);

    }
    private void uploadImage()
    {
        if(imgUri==null)
        {
            Toast.makeText(getApplicationContext(), "It is demo image. You can not upload this. Select from yur own gallery", Toast.LENGTH_LONG).show();
        }
        else {
            StorageReference ref = st.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));

            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> tu = taskSnapshot.getStorage().getDownloadUrl();
                            while (!tu.isSuccessful()) ;
                            Uri dow = tu.getResult();
                           url=dow.toString();
                            if(na.getText().toString().length()>0&&pho.getText().toString().length()==11)
                            {

                                HashMap mp=new HashMap();
                                mp.put("Name",na.getText().toString());
                                mp.put("Phone",pho.getText().toString());
                                mp.put("Url",url);
                                mp.put("District",district.getText().toString());
                                mp.put("Division",division.getSelectedItem().toString());
                                String email1="";
                                for(int i=0;i<email.length();i++)
                                {
                                    if(email.charAt(i)=='@')
                                        break;
                                    email1+=email.charAt(i);
                                }
                                String finalEmail = email1;
                                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).updateChildren(mp);
                                FirebaseInstanceId.getInstance().getInstanceId()
                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (task.isSuccessful()) {
                                                    String token = Objects.requireNonNull(task.getResult()).getToken();
                                                    HashMap t=new HashMap();
                                                    t.put("token",token);
                                                    HashMap iu=new HashMap();
                                                    iu.put("token",token);
                                                    Random rn=new Random();
                                                    long yh=rn.nextInt(10000000);
                                                    iu.put("name",na.getText().toString());
                                                    FirebaseDatabase.getInstance().getReference("Names").child(yh+"").updateChildren(iu);
                                                    SessionManager sh=new SessionManager(PersonalInfo.this,SessionManager.USERSESSION);
                                                    sh.loginSession(na.getText().toString(),email,pho.getText().toString(),pass,url,"No",token,division.getSelectedItem().toString(),district.getText().toString());
                                                    FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Tokens").updateChildren(t);
                                                    startActivity(new Intent(getApplicationContext(),PostsandWatch.class));
                                                    finish();



                                                }

                                            }
                                        });

                            }
                            else
                            {
                                if(na.getText().toString().equals(""))
                                {
                                    na.setError("This filed needs to be filled");
                                    na.requestFocus();
                                }
                                else
                                {
                                    pho.setError("Enter A Legal Number");
                                    pho.requestFocus();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
    }
    public String getFileExtension(Uri imgURI)
    {
        ContentResolver con= getContentResolver();
        MimeTypeMap mim=MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(con.getType(imgURI));
    }
}