package edu.auburn.perform;

import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;

/**
 * Created by fzhou on 11/26/14.
 */


public class TimeThread extends Thread implements MsgKey{
    Handler handler;
    public TimeThread(Handler handler) {
        this.handler = handler;
    }
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
                Message msg = new Message();
                msg.what = msgKey1;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}