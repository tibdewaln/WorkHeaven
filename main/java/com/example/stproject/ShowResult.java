package com.example.stproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.midi.MidiDeviceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowResult extends AppCompatActivity {
    ListView prodata;
    List<ProInfo> prodata1;
    DatabaseReference databasePro, data;
    String s, uid, details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        prodata = findViewById(R.id.prolist);
        prodata1 = new ArrayList<>();
        databasePro = FirebaseDatabase.getInstance().getReference("Professinal");
        data = FirebaseDatabase.getInstance().getReference();
        prodata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.sending, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("E-mail"))
                        {
                            ProInfo info = prodata1.get(i);
                            String recipient = info.getEmail();
                            String subject = "I want your service.";
                            String message = "My Details are:" + "\n";
                            Intent sintent = new Intent(Intent.ACTION_SEND);
                            sintent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                            sintent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            sintent.putExtra(Intent.EXTRA_TEXT, (message + details));
                            sintent.setType("message/rfc822");
                            startActivity(Intent.createChooser(sintent,"Choose an email Client"));
                        }
                        if(menuItem.getTitle().equals("Call"))
                        {
                            ProInfo info = prodata1.get(i);
                            String phone = info.getContact();
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                            startActivity(intent);
                        }
                        if(menuItem.getTitle().equals("Text"))
                        {
                            ProInfo info = prodata1.get(i);
                            String phone = info.getContact();
                            Uri uri = Uri.parse("sms:" + phone);
                            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                            intent.putExtra("sms_body", "I want your service.\nMy Details are : \n" + details);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent get = getIntent();
        s = get.getStringExtra("service");
        uid = get.getStringExtra("uid");
        databasePro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prodata1.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    ProInfo proInfo = data.getValue(ProInfo.class);
                    if(s.equals(proInfo.getService()))
                    {
                        prodata1.add(proInfo);
                    }
                }
                ProList adapter = new ProList(ShowResult.this, prodata1);
                prodata.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data1 : dataSnapshot.child("Customer").getChildren())
                {
                    ProInfo proInfo = data1.getValue(ProInfo.class);
                    if(uid.equals(proInfo.getEmail()))
                    {
                        details = "Name: "+ proInfo.getName()+"\n" +"Address: "+ proInfo.getAddress()+"\n" +"Phone: "+ proInfo.getContact()+"\n" +"EmailID: "+ proInfo.getEmail();
                        return;
                    }
                }
                for(DataSnapshot data1 : dataSnapshot.child("Professinal").getChildren())
                {
                    ProInfo proInfo = data1.getValue(ProInfo.class);
                    if(uid.equals(proInfo.getEmail()))
                    {
                        details = "Name: "+ proInfo.getName()+"\n" +"Address: "+ proInfo.getAddress()+"\n" +"Phone: "+ proInfo.getContact()+"\n" +"EmailID: "+ proInfo.getEmail();
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.goback)
            finish();
        return true;
    }
}