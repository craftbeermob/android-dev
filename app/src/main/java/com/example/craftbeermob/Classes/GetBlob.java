package com.example.craftbeermob.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.craftbeermob.Models.RecentActivity;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

/**
 * Created by ret70 on 6/14/2016.
 */
public class GetBlob extends AsyncTask<ArrayList<Object>, Void, ArrayList<GetBlob.RecentImageAndPhoto>> {
    ImageView mImageView;
    CloudBlobContainer mCloudBlobContainer;
    ArrayList<RecentImageAndPhoto> mRecentImageAndPhoto;

    Bitmap mBitmap;
    RecentActivityRecyclerAdapter mRecentActivityRecyclerAdapter;


    public GetBlob(RecentActivityRecyclerAdapter recentActivityRecyclerAdapter) {
        mRecentActivityRecyclerAdapter = recentActivityRecyclerAdapter;

    }

    @Override
    protected ArrayList<GetBlob.RecentImageAndPhoto> doInBackground(ArrayList<Object>... recent) {
        mRecentImageAndPhoto = new ArrayList<>();
        try {
            CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(Constants.connectionString);
            CloudBlobClient cloudBlobClient = cloudStorageAccount.createCloudBlobClient();

            for (Object recentActivity : recent[0]) {
                //get reference to a container (which is just a place for each user to upload photos
                mCloudBlobContainer = cloudBlobClient.getContainerReference(((RecentActivity) recentActivity).getUsername());
                mCloudBlobContainer.createIfNotExists();

                //set permission to off so data cannot be accessed from outside
                BlobContainerPermissions blobContainerPermissions = new BlobContainerPermissions();
                blobContainerPermissions.setPublicAccess(BlobContainerPublicAccessType.BLOB);
                mCloudBlobContainer.uploadPermissions(blobContainerPermissions);

                URI uri = URI.create(((RecentActivity) recentActivity).getPhotoURI());
                CloudBlockBlob cloudBlockBlob = new CloudBlockBlob(uri);
                //convert byte array to bitmap
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                cloudBlockBlob.download(byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mRecentImageAndPhoto.add(new RecentImageAndPhoto(mBitmap, ((RecentActivity) recentActivity)));

            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return mRecentImageAndPhoto;
    }


    @Override
    protected void onPostExecute(ArrayList<GetBlob.RecentImageAndPhoto> recentAndImage) {
        super.onPostExecute(recentAndImage);
        //update UI
        mRecentActivityRecyclerAdapter.addItems(recentAndImage);
    }

    public class RecentImageAndPhoto {

        private Bitmap bitmap;
        private RecentActivity recentActivity;

        public RecentImageAndPhoto(Bitmap userphoto, RecentActivity recentActivity) {
            setBitmap(userphoto);
            setRecentActivity(recentActivity);
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public RecentActivity getRecentActivity() {
            return recentActivity;
        }

        public void setRecentActivity(RecentActivity recentActivity) {
            this.recentActivity = recentActivity;
        }
    }
}
