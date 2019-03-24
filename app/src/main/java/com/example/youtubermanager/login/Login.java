package com.example.youtubermanager.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youtubermanager.R;
import com.example.youtubermanager.channel.MainActivity;
import com.example.youtubermanager.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvName;
    private TextView tvEmail;
    private EditText userName;
    private EditText password;
    private static Login loginActivity;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       btnLogin = (Button)findViewById(R.id.btnLogin);
       userName = findViewById(R.id.editText);
       password = findViewById(R.id.editText2);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginActivity = this;
        initFaceBook();
        LoginManager.getInstance().registerCallback(callbackManager, loginResult);
        Button fbLogin = (Button) findViewById(R.id.login_button);
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFaceBook();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user info from database to check here
                String username = userName.getText().toString();
                String pass = "";
                pass = password.getText().toString();
                User user = new User("",username,pass,0,1,"");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void loginFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(loginActivity, Arrays.asList("public_profile", "user_friends","email"));
    }
    public boolean isLoggedInFaceBook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

    public void initFaceBook () {
        loginResult = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                 GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code
                                String  name = object.optString(getString(R.string.name));
                                String id = object.optString(getString(R.string.id));
                                String email = object.optString(getString(R.string.email));
                                String link = object.optString(getString(R.string.link));
                                URL imageURL = extractFacebookIcon(id);
                                Log.d("name: ",name);
                               Log.d("id: ",id);
                                Log.d("email: ",email);
                               Log.d("link: ",link);
                                Log.d("imageURL: ",imageURL.toString());
                                // if user chua dang ky thi insert to database, else get info


                                User user = new User(name,email,"",0,1,imageURL.toString());
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
    }


}
