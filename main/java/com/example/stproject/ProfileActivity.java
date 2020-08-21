package com.example.stproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    EditText e1;
    Button show;
    String profession;
    Intent id;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        e1 = findViewById(R.id.servicewant);
        show = findViewById(R.id.showpro);
        id = getIntent();
        uid = id.getStringExtra("uid");
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), e1);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.isEnabled())
                        {
                            e1.setText(item.getTitle());
                        }
                        profession = e1.getText().toString();
                        return true;
                    }
                });
                popup.show();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ShowResult.class);
                i.putExtra("service",profession);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.signout)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            Toast.makeText(ProfileActivity.this,"Signout Successfully",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
