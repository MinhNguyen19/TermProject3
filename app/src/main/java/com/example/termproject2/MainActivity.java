package com.example.termproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText user, pass;
    Button signIn, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);
        register = findViewById(R.id.register);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int test = verifyData(user.getText().toString(), pass.getText().toString());
                Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(user.getText().toString(), pass.getText().toString());
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int verifyData(String username, String password) {
        DBHandler dbHandler = new DBHandler(this);
        int correct = dbHandler.verifyUser(username, password);
        return correct;
    }
    public boolean addUser(String username, String password) {
        DBHandler dbHandler = new DBHandler(this);
        User user = new User(0, username, password, 0);
        dbHandler.addUser(user);
        return true;

    }
}