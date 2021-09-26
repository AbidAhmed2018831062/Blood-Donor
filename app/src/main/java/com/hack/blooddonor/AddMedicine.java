package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AddMedicine extends AppCompatActivity {
    Spinner type;
    Spinner disease;
    EditText name, mname, price;
    LinearLayout a;
    String t[], d[],work;
    Button add, submit,finish,cancel,choose;
    String email,div,dis,url,phone,donor,token,pass,email1="",URL;
    CircleImageView ph;
    StorageTask uploadtask;
    private static final int IMAGE_REQUEST=1;
    Uri imgUri;
    StorageReference st;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        work= getIntent().getStringExtra("Work");
        progressDialog=new ProgressDialog(this);
            name.setVisibility(View.GONE);
        st= FirebaseStorage.getInstance().getReference("Upload");
        add = (Button) findViewById(R.id.add);
        ph = (CircleImageView) findViewById(R.id.ph);
        finish = (Button) findViewById(R.id.finish);
        choose = (Button) findViewById(R.id.choose);
        cancel = (Button) findViewById(R.id.cancel);
        t = getResources().getStringArray(R.array.TypeM);
        d = getResources().getStringArray(R.array.What);
        a = (LinearLayout) findViewById(R.id.a);
        type = (Spinner) findViewById(R.id.type);
        disease = (Spinner) findViewById(R.id.disease);
        name = (EditText) findViewById(R.id.name);
        mname = (EditText) findViewById(R.id.mname);
        price = (EditText) findViewById(R.id.price);
        submit = (Button) findViewById(R.id.submit);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        SessionManager sh2 = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        url = hm.get(SessionManager.URL);
        pass = hm.get(SessionManager.PASS);
        dis = hm.get(SessionManager.DISTRICT);
        div = hm.get(SessionManager.DIVISION);
        token = hm.get(SessionManager.TOKEN);
        donor = hm.get(SessionManager.DONOR);
        phone = hm.get(SessionManager.PHONE);

        for(int i=0;i<email.length();i++)
        {
            if(email.charAt(i)=='@')
                break;
            email1+=email.charAt(i);

        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicine();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(work.equals("Asking")) {
                    HashMap uy = new HashMap();
                    uy.put("Name", name.getText().toString());
                    FirebaseDatabase.getInstance().getReference("MedicineOwners").child("Owners").child(email1).setValue(uy);
                }
                startActivity(new Intent(getApplicationContext(),OwnerProfile.class));
                finish();
            }
        });
    }

    private void addMedicine() {
        add.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
        a.setVisibility(View.VISIBLE);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             openImageChooser();


            }
        });
        ArrayAdapter<String> ar = new ArrayAdapter<String>(this, R.layout.education, R.id.Edu, t);
        type.setAdapter(ar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMedicine.this,
                android.R.layout.simple_list_item_1, d);
        disease.setAdapter(adapter);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkDisease()&&checkPrice()&&checkMName())
                {
                    String p=price.getText().toString();
                    String me=mname.getText().toString();
                    String ty=type.getSelectedItem().toString();
                    String di=disease.getSelectedItem().toString();
                    a.setVisibility(View.GONE);
                    add.setVisibility(View.VISIBLE);
                    finish.setVisibility(View.VISIBLE);
                    Random rn=new Random();
                    long op=rn.nextInt(1000000);
                    Med med=new Med(me,p,ty,di,op+"",name.getText().toString(),dis+" "+div,URL);
                    FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(disease.getSelectedItem().toString()).child(op+"").setValue(med);
                    FirebaseDatabase.getInstance().getReference("Medicines").child(disease.getSelectedItem().toString()).child(op+"").setValue(med);
                    FirebaseDatabase.getInstance().getReference("AllMedicines").child(op+"").setValue(med);

                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data.getData() != null)){
            imgUri=data.getData();
            ph.setVisibility(View.VISIBLE);
           // submit.setVisibility(View.VISIBLE);
            Picasso.with(this).load(imgUri).into(ph);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            uploadImage();

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
                            URL = dow.toString();
                            if(URL!=null&&URL.length()>0) {
                                progressDialog.dismiss();
                                submit.setVisibility(View.VISIBLE);
                                cancel.setVisibility(View.GONE);
                               // choose.setVisibility(Vi);
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
    private boolean checkDisease() {

          return true;
    }
    private boolean checkPrice() {
        if(price.getText().toString().length()==0)
        {
            price.setError("Fill this filed");
            price.requestFocus();
            return false;
        }
        return true;
    }
    private boolean checkMName() {
        if(mname.getText().toString().length()==0)
        {
            mname.setError("Fill this filed");
            mname.requestFocus();
            return false;
        }
        return true;
    }
}