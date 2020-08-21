package com.example.stproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Customer extends AppCompatActivity {

    EditText e1, e2;
    Button save;
    CheckBox c1;
    ArrayList<String> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        e1 = findViewById(R.id.address1);
        e2 = findViewById(R.id.address2);
        save = findViewById(R.id.save);
        c1 = findViewById(R.id.accept);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty())
                {
                    e1.setError("Address required");
                    e2.setError("City required");
                    return;
                }
                if(!c1.isChecked())
                {
                    Toast.makeText(getApplicationContext(),"Accept the conditions.",Toast.LENGTH_SHORT).show();
                    return;
                }
                result.add(e1.getText().toString());
                result.add(e2.getText().toString());
                Intent data = new Intent();
                data.setData(Uri.parse(result.toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
