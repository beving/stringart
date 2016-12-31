package com.marksoft.stringart;

/**
 * Copied from http://stackoverflow.com/questions/16396462/android-textview-settext-in-html-fromhtml-to-display-image-and-text
 */
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.*;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class HelpMenu extends Activity implements ImageGetter {

    TextView message;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //TODO private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);  //TODO this was guess

        Log.e("HelpMenu", "Made it... . . . . . . .");
        String code = "<p><b>First, </b><br/>" +
                "Please press the <img src ='addbutton.png'> button beside the to insert a new event.</p>" +
                "<p><b>Second,</b><br/>" +
                "Please insert the details of the event.</p>" +
                "<p>The icon of the is show the level of the event.<br/>" +
                "eg: <img src = 'tu1.png' > is easier to do.</p></td>";


//        TextView message = new TextView(this.getApplicationContext());
//
//        //message = (TextView) findViewById(R.id.message);
//
//        Spanned spanned = Html.fromHtml(code, this, null);
//        message.setText(spanned);
//        message.setTextSize(16);
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public Drawable getDrawable(String arg0) {
        // TODO Auto-generated method stub
        int id = 0;

        if (arg0.equals("addbutton.png")) {
            id = R.drawable.ic_action_undo;
        }

        if (arg0.equals("tu1.png")) {
            id = R.drawable.ic_clear_all;
        }
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);//TODO deprectated.
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight()); //TODO code checker said this may do a null ptr

        return d;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HelpMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.marksoft.stringart/http/host/path")
        );
        //TODO AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HelpMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.marksoft.stringart/http/host/path")
        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
    }
}