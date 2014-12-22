package edu.auburn.perform;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements MsgKey {

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("MMM dd, yyyy hh:mm a", sysTime);
                    mTime.setText(sysTimeStr);
                    break;

                default:
                    break;
            }
        }
    };
    private TextView mTime;
    private RoundProgressBar RPBar;
    private ImageView TPView;
    private Canvas canvas;
    private Resources res;
    private int width, height;

    // Draw the Model and Path
    private void draw() {
        //Log.v("fang", "wdith = " + TPView.getMeasuredWidth());
        //Log.v("fang", "height = " + TPView.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvas = new Canvas(bitmap);

        // Draw the background
        Paint paint = new Paint();

        // Draw the model

        // Draw the trace

        Path path_right = new Path();
        Path path_compared = new Path();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(15);
        path_right.moveTo(150, 180);
        //path_right.cubicTo(30, 120, 40, 600, 580, 160);
        path_right.quadTo(200, 1000, 900, 160);
        canvas.drawPath(path_right, paint);

        paint.setColor(Color.GREEN);
        path_compared.moveTo(200, 190);
        path_compared.quadTo(80, 1000, 900, 160);
        canvas.drawPath(path_compared, paint);

        TPView.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTime = (TextView) findViewById(R.id.TimeLabel);
        new TimeThread(mHandler).start();
        RPBar = (RoundProgressBar) findViewById(R.id.RoundProgressBar);
        RPBar.setProgress(75);

        TPView = (ImageView) findViewById(R.id.ComparedLines);
        res = this.getResources();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        width = TPView.getWidth();
        height = TPView.getHeight();

        draw();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.SettingView) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
