package apps.sweeper;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Jordan on 2017-05-07.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square
    };
}