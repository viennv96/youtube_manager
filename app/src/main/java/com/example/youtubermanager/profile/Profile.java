package com.example.youtubermanager.profile;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubermanager.R;
import com.example.youtubermanager.User;
import com.example.youtubermanager.channel.MainActivity;
import com.squareup.picasso.Picasso;


public class Profile extends AppCompatActivity {
    private User user;
    private TextView username;
    private TextView email;
    private TextView accountType;
    private ImageView avartar;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = findViewById(R.id.tvNameProfile);
        email = findViewById(R.id.tvEmailProfile);
        accountType = findViewById(R.id.tvTypeAccount);
        avartar = findViewById(R.id.avartar);
        button = findViewById(R.id.btnBack);
        user = (User)getIntent().getSerializableExtra("user");
        if(user != null){
            username.setText(user.getName());
            email.setText(user.getEmail());
            if (user.getTypeAccount() == 0) {
                accountType.setText("Normal");
            }
            else {
                accountType.setText("Premium");
            }
            try{
                Picasso.get().load(user.getImg()).into(avartar);
            }catch (Exception e){
                Picasso.get().load("https://www.fancyhands.com/images/default-avatar-250x250.png").into(avartar);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}
