package edu.auburn.perform;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.InputMismatchException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity implements MsgKey{

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
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        BitmapFactory.Options o=new BitmapFactory.Options();
        o.inSampleSize = 4;
        o.inDither=false;                     //Disable Dithering mode
        Bitmap originMap = BitmapFactory.decodeResource(res, R.drawable.tpmodel, o);

        Bitmap newMap = originMap.createScaledBitmap(originMap, width-200, height-100, true);

        canvas = new Canvas(bitmap);

        // Draw the background
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        // Draw the model
        canvas.drawBitmap(newMap, 100, 50, null);

        // Draw the trace

        Path path_right = new Path();
        Path path_compared = new Path();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        path_right.moveTo(130, 130);
        //path_right.cubicTo(30, 120, 40, 600, 580, 160);
        path_right.quadTo(30, 700, 580 ,160);
        canvas.drawPath(path_right, paint);

        paint.setColor(Color.GREEN);
        path_compared.moveTo(160,160);
        path_compared.quadTo(0, 800, 580 ,160);
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

        TPView = (ImageView) findViewById(R.id.TPModel);
        res = this.getResources();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        width=TPView.getWidth();
        height=TPView.getHeight();

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
}
