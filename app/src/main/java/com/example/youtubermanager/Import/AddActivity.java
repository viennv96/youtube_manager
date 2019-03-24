package com.example.youtubermanager.Import;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubermanager.R;
import com.example.youtubermanager.User;
import com.example.youtubermanager.channel.MainActivity;
import com.example.youtubermanager.entity.YoutubeChannel;
import com.example.youtubermanager.sqlitedb.DBAccess;
import com.example.youtubermanager.youtubeparser.ChannelParser;

import java.util.concurrent.ExecutionException;

public class AddActivity extends AppCompatActivity {

    private TextView txtInput;
    private Button btnImport;
    private ProgressBar progressBar;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtInput = findViewById(R.id.input_id);
        btnImport = findViewById(R.id.btnImport);
        user = (User)getIntent().getSerializableExtra("user");
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importChannel();
            }
        });
    }

    private void importChannel() {
        //progressBar.setVisibility(View.VISIBLE);
        if (txtInput.getText().toString().trim().equals("")){
            progressBar.setVisibility(View.GONE);
            return;
        }
        btnImport.setEnabled(false);
        String items[] = txtInput.getText().toString().split("\n");
        for(String item:items){
            if(item.trim().equals("")){
                continue;
            }
            ChannelParser parser = new ChannelParser();
            String url = parser.generateRequest(item.trim());
            if (url.equals("")){
                btnImport.setEnabled(true);
                continue;
            }
            try {
                parser.execute(url).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            parser.onFinish(new ChannelParser.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(YoutubeChannel channel) {
                    if(channel.getLive().equals(getApplicationContext().getString(R.string.channel_live))){
                        DBAccess db = DBAccess.getInstance(getApplicationContext());
                        db.addNewChannel(channel);
                    }else{
                        btnImport.setEnabled(true);
                    }
                }
                @Override
                public void onError() {
                    btnImport.setEnabled(true);
                    Toast.makeText(AddActivity.this, "Error while loading data. Please retry", Toast.LENGTH_SHORT).show();
                }
            });
        }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user",user);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

    }
}


