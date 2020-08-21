package com.example.stproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class Professional extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    Button save;
    CheckBox c1;
    ArrayList<String> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        e1 = findViewById(R.id.address1);
        e2 = findViewById(R.id.address2);
        e3 = findViewById(R.id.descript);
        e4 = findViewById(R.id.service);
        save = findViewById(R.id.save);
        c1 = findViewById(R.id.accept);
        e4.setShowSoftInputOnFocus(false);
//        arrow = findViewById(R.id.arrow);
        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), e4);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.isEnabled())
                        {
                            e4.setText(item.getTitle());
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() || e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty())
                {
                    e1.setError("Address required");
                    e2.setError("City required");
                    e3.setError("Description required");
                    e4.setError("Profession required");
                    return;
                }
                if(!c1.isChecked())
                {
                    Toast.makeText(getApplicationContext(),"Accept the conditions.",Toast.LENGTH_SHORT).show();
                    return;
                }
                result.add(e1.getText().toString());
                result.add(e2.getText().toString());
                result.add(e3.getText().toString());
                result.add(e4.getText().toString());
                Intent data = new Intent();
                data.setData(Uri.parse(result.toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
