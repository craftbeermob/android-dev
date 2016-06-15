package com.example.craftbeermob.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Interfaces.IObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by ret on 6/3/16.
 */


public class BaseQuery<T> implements TableOperationCallback {

    Context context;
    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;


    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Object> mTable;


    //params:context, and the class type we are going to query for
    public BaseQuery(Context con, Class classType) {
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
                    Log.d("Ex_addItem", e.getMessage());
                }
                return null;
            }
        };

        runAsyncTask(task);
    }


    public void getWhere(final IList activity, final String field, final String fieldEquals) {
        AsyncTask<Void, Void, ArrayList<Object>> task = new AsyncTask<Void, Void, ArrayList<Object>>() {
            @Override
            protected ArrayList<Object> doInBackground(Void... params) {
                final ArrayList<Object> results;
                try {
                    results = mTable.where().field(field).eq(fieldEquals).execute().get();

                } catch (Exception exception) {
                    Log.d("getting  all objects", "Error");
                    return null;
                }
                return results;
            }

            @Override
            protected void onPostExecute(ArrayList<Object> objectList) {
                super.onPostExecute(objectList);
                if (objectList.size() > 0 && objectList != null) {
                    activity.setList(objectList);
                }

            }
        };
        runAsyncTaskAllObjects(task);


    }

    public void getAll(final IList activity) {

        AsyncTask<Void, Void, ArrayList<Object>> task = new AsyncTask<Void, Void, ArrayList<Object>>() {
            @Override
            protected ArrayList<Object> doInBackground(Void... params) {
                final ArrayList<Object> results;
                try {
                    results = mTable.execute().get();


                } catch (Exception exception) {
                    Log.d("getting  all objects", "Error");
                    return null;
                }
                return results;
            }

            @Override
            protected void onPostExecute(ArrayList<Object> objectList) {
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
    private AsyncTask<Void, Void, ArrayList<Object>> runAsyncTaskAllObjects(AsyncTask<Void, Void, ArrayList<Object>> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    @Override
    public void onCompleted(Object entity, Exception exception, ServiceFilterResponse response) {
        Log.d("test", "test");
    }
}
