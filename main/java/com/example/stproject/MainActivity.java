package com.example.stproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView register;
    ProgressBar progressBar;
    Button login;
    EditText cate, user, pass;
    FirebaseAuth mauth;
    DatabaseReference databasePro, databaseCus;
    int request_code = 1;
    String name, username, password, cont, category, address, services, des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.here);
        cate = findViewById(R.id.cate);
        progressBar = findViewById(R.id.progress);
        mauth = FirebaseAuth.getInstance();
        databasePro = FirebaseDatabase.getInstance().getReference("Professinal");
        databaseCus = FirebaseDatabase.getInstance().getReference("Customer");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent("com.example.stproject.Registration"), request_code);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category == null || category.isEmpty())
                {
                    cate.setError("Please select the category.");
                    return;
                }
                userLogin();
            }
        });
        cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), cate);
                popup.getMenuInflater().inflate(R.menu.category, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        cate.setText(menuItem.getTitle());
                        category = cate.getText().toString();
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        StringBuffer temp;
        String[] temp1;
        if(requestCode == request_code)
            if(resultCode == RESULT_OK)
            {
                temp = new StringBuffer(data.getData().toString());
                temp.deleteCharAt(0);temp.deleteCharAt(temp.length()-1);
                temp1 = temp.toString().split(", ");
//                Toast.makeText(this,data.getData().toString(),Toast.LENGTH_SHORT).show();
                name = temp1[0] +" "+temp1[1];
                username = temp1[2];
                password = temp1[3];
                cont = temp1[4];
                category = temp1[5];
                address = temp1[6]+", " + temp1[7];
                if(category.equals("Professional"))
                {
                    des = temp1[8];
                    services = temp1[9];
                }
                mauth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            mauth.signInWithEmailAndPassword(username, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                            {
                                                String id = mauth.getCurrentUser().getUid();
                                                if(category.equals("Professional"))
                                                {
                                                    ProInfo info = new ProInfo(address, cont, username, name, services, des);
                                                    databasePro.child(id).setValue(info);
                                                }
                                                else
                                                {
                                                    ProInfo info = new ProInfo(address, cont, username, name);
                                                    databaseCus.child(id).setValue(info);
                                                }
                                            }
                                        }
                                    });
                            mauth.signOut();
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
            }
    }
    private void userLogin()
    {
        progressBar.setVisibility(View.VISIBLE);
        mauth.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            if(category.equals("Customer")) {
                                finish();
                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                String uname = user.getText().toString();
                                intent.putExtra("uid",uname);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Professional don't need to login.",Toast.LENGTH_SHORT).show();
                                mauth.signOut();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
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
            finish();
        return true;
    }
}
