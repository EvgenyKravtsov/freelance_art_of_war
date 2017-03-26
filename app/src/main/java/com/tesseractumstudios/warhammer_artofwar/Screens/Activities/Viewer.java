package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import art.of.war.tesseractumstudios.R;
public class Viewer extends ActionBarActivity {
    private RelativeLayout  root;
    private WebView         webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        WebSettings webSettings;
        String      path        = getIntent().getExtras().getString("path");

        findViews();
        root.setBackgroundColor(Color.WHITE);

        webSettings = webView.getSettings();

        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webSettings.setTextZoom(getResources().getInteger(R.integer.html_text_zoom));
        webView.loadUrl("file:///" + path);
    }

    private void findViews() {
        webView = (WebView) findViewById(R.id.content);
        root = (RelativeLayout) findViewById(R.id.root);
    }
}
