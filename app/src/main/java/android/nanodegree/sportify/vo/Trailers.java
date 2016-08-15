package android.nanodegree.sportify.vo;

import android.graphics.Bitmap;

/**
 * Created by ravisha on 12/8/15.
 */
public class Trailers {
    String title;
    Bitmap image;

    public Trailers(Bitmap image,String title){
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
