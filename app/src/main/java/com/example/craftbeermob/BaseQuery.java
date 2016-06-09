package com.example.craftbeermob;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.io.Console;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by ret on 6/3/16.
 */


public class BaseQuery<T> {

    Context context;
    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;


    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Object> mTable;


    Class type;

    BaseQuery(Context con, Class classType) {
        // Mobile Service URL and key
        try {


            mClient = new MobileServiceClient("https://craftbeermob.azurewebsites.net", con);
            // Get the Mobile Service Table instance to use

            mTable = mClient.getTable(classType);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Add a new  mission item
     */

    public void addItem(final IObject item) {
        if (mClient == null) {
            return;
        }


        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    mTable.insert(item.returnObj()).get();


                } catch (final Exception e) {
                    Log.d("drawerlayout", e.getMessage());
                }
                return null;
            }
        };

        runAsyncTask(task);
    }

    public void getAll(final IList activity) {

        AsyncTask<Void, Void, List<Object>> task = new AsyncTask<Void, Void, List<Object>>() {
            @Override
            protected List<Object> doInBackground(Void... params) {
                final List<Object> results;
                try {
                    results = mTable.execute().get();


                } catch (Exception exception) {
                    Log.d("getting  all objects", "Error");
                    return null;
                }
                return results;
            }

            @Override
            protected void onPostExecute(List<Object> objectList) {
                super.onPostExecute(objectList);
                if (objectList.size() > 0 && objectList != null) {
                    activity.setList(objectList);
                }

            }
        };
        runAsyncTaskAllObjects(task);


    }


    /**
     * Run an ASync task on the corresponding executor
     *
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    /**
     * Run an ASync task on the corresponding executor
     *
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, List<Object>> runAsyncTaskAllObjects(AsyncTask<Void, Void, List<Object>> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

}
