package com.example.craftbeermob.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.craftbeermob.Classes.StoreBlob;
import com.example.craftbeermob.Fragments.SummaryFragment;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Interfaces.IListFragmentInteractionListener;
import com.example.craftbeermob.LocationClasses.GeoMain;
import com.example.craftbeermob.Models.Users;
import com.example.craftbeermob.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SummaryActivity extends BaseActivity implements IListFragmentInteractionListener, IList {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int CAPTURE_PROFILE_IMAGE = 101;
    private static final int CAPTURE_RECENT_ACTIVITY_PICTURE = 100;

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        Fragment summaryFragment = new SummaryFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, summaryFragment, null);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onListFragmentInteraction(Object item) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Uri fileURI;


        if (item.getItemId() == R.id.menu_uploadbeer) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // fileURI = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            //cameraIntent.putExtra("return-data", true);
            // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileURI);
            startActivityForResult(cameraIntent, CAPTURE_RECENT_ACTIVITY_PICTURE);
        }

        return super.onOptionsItemSelected(item);


    }

    private void makeSnack(String textToShow) {

        Snackbar.make(getWindow().getDecorView().getRootView(), textToShow, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo;
        if (requestCode == CAPTURE_RECENT_ACTIVITY_PICTURE) {
            if (resultCode == RESULT_OK) {
                if ((photo = (Bitmap) data.getExtras().get("data")) != null) {
                    // Image captured and saved to fileUri specified in the Intent
                    //TODO: after image is taken ask the user what type of beer it was and pass it to recentactivity
                    Intent intent = new Intent(this, GeoMain.class);
                    intent.putExtra("userphoto", photo);
                    intent.putExtra("requestcode", CAPTURE_RECENT_ACTIVITY_PICTURE);
                    startActivityForResult(intent, GeoMain.GeoMain_RequestCode);


                } else if (resultCode == RESULT_CANCELED) {
                    // Users cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
            }
        } else if (requestCode == GeoMain.GeoMain_RequestCode) {
            if (resultCode == RESULT_OK) {

            }
        } else if (requestCode == CAPTURE_PROFILE_IMAGE) {
            if (resultCode == RESULT_OK) {
                if ((photo = (Bitmap) data.getExtras().get("data")) != null) {
                    Users users = new Users();
                    users.setUserId("1234");
                    users.setUsername("testuser");
                    //TODO:Once we have authentication
                    users.setProfilePictureId(UUID.randomUUID().toString());
                    StoreBlob storeBlob = new StoreBlob(this, users, photo);
                    storeBlob.execute();

                }
            }
        }


    }


    public void setProfilePic(String uri) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_summary_userimage);
        imageView.setBackgroundResource(0);
        Glide.with(this).load(uri).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    @Override
    public void setList(ArrayList<Object> objects) {

    }
}
