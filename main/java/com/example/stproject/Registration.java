package com.example.stproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5;
    Button register;
    RadioGroup r1;
    RadioButton category;
    int request_code = 1;
    ArrayList<String> result = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_1);
        t1 = findViewById(R.id.name);
        t2 = findViewById(R.id.last);
        t3 = findViewById(R.id.user);
        t4 = findViewById(R.id.pass);
        t5 = findViewById(R.id.contact);
        register = findViewById(R.id.register);
        r1 = findViewById(R.id.group);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t1.getText().toString().isEmpty() || t2.getText().toString().isEmpty() || t3.getText().toString().isEmpty() || t4.getText().toString().isEmpty() || t5.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter all details.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(t5.getText().toString().length()>10 || t5.getText().toString().length()<10)
                {
                   t5.setError("Phone no. must be of 10 digits.");
                   return;
                }
                if(t4.getText().toString().length()<6)
                {
                    t4.setError("Password must have more than 6 characters.");
                    return;
                }
                result.add(t1.getText().toString());
                result.add(t2.getText().toString());
                result.add(t3.getText().toString());
                result.add(t4.getText().toString());
                result.add(t5.getText().toString());
                category = findViewById(r1.getCheckedRadioButtonId());
                result.add(category.getText().toString());
                if(category.getText().equals("Customer"))
                {
                    startActivityForResult(new Intent("com.example.stproject.Customer"), request_code);
                }
                if(category.getText().equals("Professional"))
                {
                    startActivityForResult(new Intent("com.example.stproject.Professional"), request_code);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        StringBuffer d = new StringBuffer(data.getData().toString());
        d.deleteCharAt(0);d.deleteCharAt(d.length()-1);
        String[] d1 = d.toString().split(", ");
        for(String e : d1)
            result.add(e);
        if(requestCode == request_code && resultCode == RESULT_OK)
        {
            data.setData(Uri.parse(result.toString()));
            setResult(RESULT_OK, data);
        }
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mainoption, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.exit)
        {
            finish();
            Intent newi = new Intent(Registration.this, MainActivity.class);
            newi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newi);
        }
        return true;
    }
}
