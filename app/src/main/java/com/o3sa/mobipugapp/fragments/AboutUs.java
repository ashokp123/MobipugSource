package com.o3sa.mobipugapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.sidemenu.Sidemenu;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;

/**
 * Created by Kiran on 24-10-2018.
 */

public class AboutUs extends Fragment {


    BasicComponents components;
    ProgressBar progress_bar;
    WebView webview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aboutuswebview, container, false);

        initialization(v);
        return v;
    }

    public void initialization(View v) {
        components = new BasicComponents(getActivity());
        StoredObjects.pagetype="aboutus";
        Sidemenu.updatemenu(StoredObjects.pagetype);
        progress_bar = (ProgressBar)v.findViewById(R.id.progress_bar);
        webview = (WebView)v.findViewById(R.id.webview);
        progress_bar.setVisibility(View.GONE);

        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.loadUrl("https://www.google.com");


    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }
    }

}


