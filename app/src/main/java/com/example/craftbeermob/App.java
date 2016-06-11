package com.example.craftbeermob;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by ret70 on 6/9/2016.
 */
public class App extends Application {
    //global class which holds objects through the lifecycle of the application
    private Bitmap beerPhoto;





    public Bitmap getBeerPhoto() {
        return beerPhoto;
    }



    public void setBeerPhoto(Bitmap beerPhoto) {
        if(beerPhoto!=null)
        {
            //release bitmap resources
            beerPhoto.recycle();
            this.beerPhoto = beerPhoto;
        }

    }


}
