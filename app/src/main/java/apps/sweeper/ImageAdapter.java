package apps.sweeper;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jordan on 2017-05-07.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int numButtons;

    // numButtons: the number of buttons to create for the gridview.
    // TODO dynamically fill the gridview with the correct number of buttons.
    public ImageAdapter(Context c, int n) {
        numButtons = n;
        mContext = c;
    }

    // seems like this is the method that determines how many items to put in the gridview.
    // may just get it to use Btn.boardSize().
    public int getCount() {
        return Btn.boardSize();
    }

    public Object getItem(int position) {
        return Btn.buttonNums.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create new Button and Btn for each position
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        Button b;
        Btn btn;
        if (Btn.buttonNums.containsKey(position)) {
            b = Btn.buttonNums.get(position);
            btn = Btn.minePlacements.get(position);
        }
        else {
            btn = new Btn (pos);
            b = new Button(mContext);
            b.setLayoutParams(new GridView.LayoutParams (80, 80));
            b.setPadding(1, 1, 1, 1);
        }

        Btn.buttonNums.put(position, b);
        Btn.minePlacements.put(position, btn);

        b.setOnClickListener (new View.OnClickListener() {
            public void onClick (View view) {
                Btn.minePlacements.get(pos).clicked();
            }
        });
        b.setOnLongClickListener (new View.OnLongClickListener() {
            public boolean onLongClick (View view) {
                Btn.minePlacements.get(pos).held();
                return true;
            }
        });
        Btn.btnReset (btn, b);

        return b;
    }

    // references to our images
    // TODO dynamically fill this array with resources when other board sizes are available.
    private Integer[] mThumbIds = {
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square, R.drawable.square,
            R.drawable.square, R.drawable.square, R.drawable.square
    };
}