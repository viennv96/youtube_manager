package com.example.youtubermanager.channel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubermanager.Import.AddActivity;
import com.example.youtubermanager.User;
import com.example.youtubermanager.channel.fragment.LiveFragment;
import com.example.youtubermanager.R;
import com.example.youtubermanager.channel.fragment.SuspendFragment;
import com.example.youtubermanager.channel.fragment.TotalFragment;
import com.example.youtubermanager.login.Login;
import com.example.youtubermanager.profile.Profile;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,NavigationView.OnNavigationItemSelectedListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView navUsername;
    private TextView navEmail;
    private ImageView navImage;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //view page
        viewPager = findViewById(R.id.viewPage_id);
        final ViewChannelAdapter adapter = new ViewChannelAdapter(this.getSupportFragmentManager());
        adapter.addFragments(new LiveFragment());
        adapter.addFragments(new SuspendFragment());
        adapter.addFragments(new TotalFragment());
        viewPager.setAdapter(adapter);

        //tab layout
        tabLayout = findViewById(R.id.tablayout_id);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = (User)getIntent().getSerializableExtra("user");
        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.tvUser);
        navEmail = headerView.findViewById(R.id.tvMail);
        navImage = headerView.findViewById(R.id.imgAvarta);
        if(user != null){
            navUsername.setText(user.getName());
            navEmail.setText(user.getEmail());
        }


        try{
            Picasso.get().load(user.getImg()).into(navImage);
        }catch (Exception e){
            Picasso.get().load("https://www.fancyhands.com/images/default-avatar-250x250.png").into(navImage);
        }
     }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onNavigationItemSelected(@Nullable MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_add) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        } else if (id == R.id.nav_remove) {
            if(user == null) {
                Toast.makeText(this, "Please login to use this feature", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_settings) {
            if(user == null) {
                Toast.makeText(this, "Please login to use this feature", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_rate) {
            Toast.makeText(this,"Please go to Google play to rate app. Thanks!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
                Toast.makeText(this, "Please login to use this feature", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_profile) {
            if(user == null) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
