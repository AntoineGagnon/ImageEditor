package projet_techno_l3.imageeditor.ImageModifications;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Used to edit the brightness of an image using the Value in HSV colors
 */
public class BrightnessEditor extends AbstractImageModificationAsyncTask {

    private float value;

    /**
     * @param src   Source image to be modified
     * @param value Brightness adjusment value, between -100 and 100
     */
    public BrightnessEditor(Bitmap src, float value, Activity activity) {
        super(activity);
        this.src = src;
        this.value = value;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        publishProgress("Editing..."); // Calls onProgressUpdate()
        if (value == 0) {
            return src;
        }
        Bitmap result = src.copy(Bitmap.Config.ARGB_8888, true);

        int imgHeight = result.getHeight();
        int imgWidth = result.getWidth();

        // Getting each pixel in the Bitmap
        int[] pixels = new int[imgWidth * imgHeight];
        result.getPixels(pixels, 0, imgWidth, 0, 0, imgWidth, imgHeight);


        for (int i = 0; i < pixels.length; i++) {
            float[] hsv = new float[3];
            Color.colorToHSV(pixels[i], hsv);
            float addedValue = hsv[2] + value / 100;
            if (addedValue > 1)
                addedValue = 1;
            if (addedValue < -1)
                addedValue = -1;

            hsv[2] = addedValue;
            pixels[i] = Color.HSVToColor(hsv);
        }

        result.setPixels(pixels, 0, imgWidth, 0, 0, imgWidth, imgHeight);
        return result;
    }

}
