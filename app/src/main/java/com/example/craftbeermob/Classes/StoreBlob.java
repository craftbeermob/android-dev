package com.example.craftbeermob.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.craftbeermob.Activities.SummaryActivity;
import com.example.craftbeermob.Interfaces.HasPhoto;
import com.example.craftbeermob.Models.Badges;
import com.example.craftbeermob.Models.RecentActivity;
import com.example.craftbeermob.Models.Users;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ret70 on 6/14/2016.
 */
public class StoreBlob extends AsyncTask<Void, Void, Void> {
    HasPhoto mModel;
    CloudBlobContainer mCloudBlobContainer;
    Context context;
    Bitmap mUserPhoto;

    public StoreBlob(Context context, HasPhoto model, Bitmap userPhoto) {
        mModel = model;
        this.context = context;
        this.mUserPhoto = userPhoto;

    }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            //connect
            CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(Constants.connectionString);
            CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();

            //get reference to a container (which is just a place for each user to upload photos
            mCloudBlobContainer = blobClient.getContainerReference(mModel.getUserID());
            mCloudBlobContainer.createIfNotExists();
            //set permission to off so data cannot be accessed from outside
            BlobContainerPermissions blobContainerPermissions = new BlobContainerPermissions();
            blobContainerPermissions.setPublicAccess(BlobContainerPublicAccessType.BLOB);
            mCloudBlobContainer.uploadPermissions(blobContainerPermissions);

            //
            breakDownImageAndUpload();

            //test purposes-list blobs
//            for (ListBlobItem listBlobItem : mCloudBlobContainer.listBlobs()) {
//                if (listBlobItem instanceof CloudBlockBlob) {
//                   listBlobItem.download
//                }
//            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

        return null;
    }


    private Boolean checkIfOver4Mb() {
        boolean isOver4Mb = false;
        Double sizeinMb = Double.valueOf(((mUserPhoto.getByteCount() / 1024) / 1014));
        if (sizeinMb.intValue() > 4) {
            return isOver4Mb = true;
        }

        return isOver4Mb;
    }

    private void breakDownImageAndUpload() {
        ArrayList<CloudBlockBlob> blobsList = null;
        //get the reference of the created container
        try {

            ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
            mUserPhoto.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            byte[] fullBlobArray = bufferedOutputStream.toByteArray();
            InputStream fullInputStream = new ByteArrayInputStream(fullBlobArray);
            if (checkIfOver4Mb()) {
                int prevIndex = 0;
                for (int i = 0; i < fullBlobArray.length; i++) {
                    if ((i != 0) && (2048 / i == 0)) {

                        byte[] splitBlobSize = Arrays.copyOfRange(fullBlobArray, prevIndex, i);
                        prevIndex = i;
                        InputStream splitInputStream = new ByteArrayInputStream(splitBlobSize);
                        UploadImage(splitInputStream, mModel.getPhotoID(), splitBlobSize.length);

                    } else if (i == fullBlobArray.length) {
                        byte[] splitBlobSize = Arrays.copyOfRange(fullBlobArray, prevIndex, i);
                        prevIndex = i;
                        InputStream splitInputStream = new ByteArrayInputStream(splitBlobSize);
                        UploadImage(splitInputStream, mModel.getPhotoID(), splitBlobSize.length);
                    }

                }

            } else {
                UploadImage(fullInputStream, fullBlobArray.length);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Uploading Image", "Error");
        }
    }

    private void UploadImage(InputStream inputStream, long size) {
        CloudBlockBlob cloudBlockBlob = null;
        String blob_reference;
        //upload data to the container
        try {
            cloudBlockBlob = mCloudBlobContainer.getBlockBlobReference(mModel.getPhotoID());
            cloudBlockBlob.upload(inputStream, size);
            if (mModel instanceof RecentActivity) {
                ((RecentActivity) mModel).setPhotoURI(cloudBlockBlob.getUri().toString());
                ((RecentActivity) mModel).setBrewery_Info("Test Brewery!");
                ((RecentActivity) mModel).setPhotoId(((RecentActivity) mModel).getPhotoId());
                ((RecentActivity) mModel).setTitle("Super Brewery-Ale,Summer");
                ((RecentActivity) mModel).setUsername(((RecentActivity) mModel).getUsername());
                new BaseQuery<>(context, RecentActivity.class).addItem(((RecentActivity) mModel));

            } else if (mModel instanceof Users) {
                //TODO:Possibly delete older profile pics??

                ((Users) mModel).setProfilePictureUri(cloudBlockBlob.getUri().toString());

                ((SummaryActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        ((SummaryActivity) context).setProfilePic(((Users) mModel).getProfilePictureUri());
                    }
                });
                new BaseQuery<>(context, Users.class).updateItem(((Users) mModel));
            } else if (mModel instanceof Users) {
                //TODO:Possibly delete older profile pics??

                ((Users) mModel).setProfilePictureUri(cloudBlockBlob.getUri().toString());

                ((SummaryActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        ((SummaryActivity) context).setProfilePic(((Users) mModel).getProfilePictureUri());
                    }
                });
                new BaseQuery<>(context, Users.class).updateItem(((Users) mModel));
            }
            //for testing of uploading badges
            else if (mModel instanceof Badges) {

                ((Badges) mModel).setBadgeUri(cloudBlockBlob.getUri().toString());

                new BaseQuery<>(context, Badges.class).addItem(((Badges) mModel));
            }


        } catch (URISyntaxException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (StorageException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }


    }

    private void UploadImage(InputStream inputStream, String blockId, long size) {
        CloudBlockBlob cloudBlockBlob = null;
        //upload data to the container
        try {
            cloudBlockBlob = mCloudBlobContainer.getBlockBlobReference(blockId);
            cloudBlockBlob.uploadBlock(mModel.getPhotoID(), inputStream, size);


        } catch (URISyntaxException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (StorageException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }


    }
}
