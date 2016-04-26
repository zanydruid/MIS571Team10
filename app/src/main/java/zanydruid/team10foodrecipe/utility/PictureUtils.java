package zanydruid.team10foodrecipe.utility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by yizhu on 3/16/16.
 */
public class PictureUtils {
    /**
     * Resize a original picture with input dimension
     * This method will support the getScaleBitMap(String,Activity)
     *
     * @param path
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap getScaleBitmap(String path, int destWidth, int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if(srcHeight> destHeight || srcWidth>destWidth){
            if(srcWidth>srcHeight){
                inSampleSize = Math.round(srcHeight/destHeight);
            } else {
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = 7;
        return BitmapFactory.decodeFile(path,options);
    }

    /**
     * This method is eventually used for get bitmap image with consideration of screen sizes
     *
     * @param path
     * @param activity
     * @return
     */
    public static Bitmap getScaleBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaleBitmap(path,size.x,size.y);
    }
}
