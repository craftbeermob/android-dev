package com.example.craftbeermob.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.craftbeermob.Classes.BaseQuery;
import com.example.craftbeermob.Classes.Constants;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Models.Users;
import com.example.craftbeermob.R;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;

import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements IList {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mClient = App.getMobileClientSingleton(this);


        if (loadUserTokenCache(mClient)) {
            startActivity(new Intent(this, SummaryActivity.class));
        }


        ImageButton facebookLoginBtn = (ImageButton) findViewById(R.id.login_facebook_ib);
        facebookLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // attemptLogin();
                //TODO: Replace later with actual login details
                authenticate(MobileServiceAuthenticationProvider.Facebook);
            }
        });


        ImageButton googleLoginBtn = (ImageButton) findViewById(R.id.login_google_ib);
        googleLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // attemptLogin();
                //TODO: Replace later with actual login details
                authenticate(MobileServiceAuthenticationProvider.Google);
            }
        });

        ImageButton twitterLoginBtn = (ImageButton) findViewById(R.id.login_twitter_ib);
        twitterLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // attemptLogin();
                //TODO: Replace later with actual login details
                authenticate(MobileServiceAuthenticationProvider.Twitter);
            }
        });


    }

    private void authenticate(MobileServiceAuthenticationProvider provider) {
        // We first try to load a token cache if one exists.

        // If we failed to load a token cache, login and create a token cache

        // Login using the  provider.
        ListenableFuture<MobileServiceUser> mLogin = mClient.login(provider);
        Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
            @Override
            public void onFailure(Throwable exc) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG);
                exc.printStackTrace();
            }

            @Override
            public void onSuccess(MobileServiceUser user) {

                cacheUserToken(mClient.getCurrentUser(), mClient);
                new BaseQuery<>(LoginActivity.this, Users.class).getWhere(LoginActivity.this, "UserId", App.GetUserSingleton().getUserId());

            }
        });





    }


    private void deleteUserToken() {
        SharedPreferences preferences = getSharedPreferences(Constants.SHAREDPREFFILE, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    private void cacheUserToken(MobileServiceUser user, MobileServiceClient client) {
        SharedPreferences prefs = getSharedPreferences(Constants.SHAREDPREFFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.USERIDPREF, user.getUserId());
        editor.putString(Constants.TOKENPREF, user.getAuthenticationToken());
        editor.apply();
        SetUser(client, user.getUserId(), user.getAuthenticationToken());
    }

    private boolean loadUserTokenCache(MobileServiceClient client) {
        SharedPreferences prefs = getSharedPreferences(Constants.SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(Constants.USERIDPREF, null);
        if (userId == null)
            return false;
        String token = prefs.getString(Constants.TOKENPREF, null);
        if (token == null)
            return false;

        SetUser(client, userId, token);

        return true;
    }

    private void SetUser(MobileServiceClient client, String userId, String token) {
        MobileServiceUser user = new MobileServiceUser(userId);
        user.setAuthenticationToken(token);
        client.setCurrentUser(user);
        Users local_user = App.GetUserSingleton();
        local_user.setUserId(user.getUserId().substring(4));
        //TODO:Delete UserId field in db and just id manually.
    }

    //add user if he didnt exist
    @Override
    public void setList(ArrayList<Object> objects) {
        if (objects == null || objects.size() == 0) {
            new BaseQuery<>(this, Users.class).addItem(App.GetUserSingleton());
        }

        startActivity(new Intent(LoginActivity.this, SummaryActivity.class));
        finish();
    }
}

