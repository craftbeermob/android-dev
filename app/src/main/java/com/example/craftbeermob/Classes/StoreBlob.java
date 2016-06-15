package com.example.craftbeermob.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.craftbeermob.Models.RecentActivity;
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
public class StoreBlob extends AsyncTask<String, Void, Void> {
    RecentActivity mRecentActivity;
    CloudBlobContainer mCloudBlobContainer;
    Context context;

    Bitmap mUserPhoto;

    public StoreBlob(Context context, RecentActivity recentActivity, Bitmap userPhoto) {
        mRecentActivity = recentActivity;
        this.context = context;
        this.mUserPhoto = userPhoto;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            //connect
            CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(Constants.connectionString);
            CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();

            //get reference to a container (which is just a place for each user to upload photos
            mCloudBlobContainer = blobClient.getContainerReference(mRecentActivity.getUsername());
            mCloudBlobContainer.createIfNotExists();
            //set permission to off so data cannot be accessed from outside
            BlobContainerPermissions blobContainerPermissions = new BlobContainerPermissions();
            blobContainerPermissions.setPublicAccess(BlobContainerPublicAccessType.OFF);
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
                for (int i = 0; i < fullBlobArray.length; i++) {
                    if (i / 1024 == 0) {

                        byte[] splitBlobSize = Arrays.copyOfRange(fullBlobArray, 0, i + 1);
                        InputStream splitInputStream = new ByteArrayInputStream(splitBlobSize);
                        UploadImage(splitInputStream, mRecentActivity.getPhotoId(), splitBlobSize.length);
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
        //upload data to the container
        try {
            cloudBlockBlob = mCloudBlobContainer.getBlockBlobReference(mRecentActivity.getPhotoId());
            cloudBlockBlob.upload(inputStream, size);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void UploadImage(InputStream inputStream, String blockId, long size) {
        CloudBlockBlob cloudBlockBlob = null;
        //upload data to the container
        try {
            cloudBlockBlob = mCloudBlobContainer.getBlockBlobReference(blockId);
            cloudBlockBlob.uploadBlock(mRecentActivity.getPhotoId(), inputStream, size);


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
