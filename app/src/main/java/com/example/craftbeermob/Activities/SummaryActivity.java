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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SummaryActivity extends BaseActivity implements IListFragmentInteractionListener, IList {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int CAPTURE_PROFILE_IMAGE = 101;
    private static final int CAPTURE_RECENT_ACTIVITY_PICTURE = 100;


    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(Bitmap photo) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name));
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(getResources().getString(R.string.app_name), "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        try {
            OutputStream outputStream = new FileOutputStream(mediaFile);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            MediaStore.Images.Media.insertImage(getContentResolver(), mediaFile.getAbsolutePath(), mediaFile.getName(), mediaFile.getName());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

        ImageView imageView = (ImageView) findViewById(R.id.iv_summary_userimage);

        if (App.GetUserSingleton().getProfilePictureUri() != null) {
            imageView.setBackgroundResource(0);
            Glide.with(this).load(
                    App.GetUserSingleton()
                            .getProfilePictureUri())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

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
            startActivityForResult(cameraIntent, CAPTURE_RECENT_ACTIVITY_PICTURE);
        } else if (item.getItemId() == R.id.menu_test) {
            startActivity(new Intent(this, PlacesActivity.class));
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

                    Intent intent = new Intent(this, GeoMain.class);
                    intent.putExtra("userphoto", photo);
                    startActivityForResult(intent, GeoMain.GeoMain_RequestCode);


                } else if (resultCode == RESULT_CANCELED) {
                    // Users cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
            }
        } else if (requestCode == GeoMain.GeoMain_RequestCode) {
            if (resultCode == RESULT_OK) {
                startActivity(getIntent());
            }
        } else if (requestCode == CAPTURE_PROFILE_IMAGE) {
            if (resultCode == RESULT_OK) {
                if ((photo = (Bitmap) data.getExtras().get("data")) != null) {
                    //TODO:Once we have authentication
                    Users users = App.GetUserSingleton();
                    users.setProfilePictureId(UUID.randomUUID().toString());
                    StoreBlob storeBlob = new StoreBlob(this, users, photo);
                    storeBlob.execute();

                }
            }
        }


    }


    public void setProfilePic(String uri) {
        SetProfilePicture(uri);
        App.setProfileUri(uri);
    }

    private void SetProfilePicture(String uri) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_summary_userimage);
        if (imageView != null) {
            imageView.setBackgroundResource(0);
            Glide.with(this).load(uri).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }

    @Override
    public void setList(ArrayList<Object> objects) {

    }
}
