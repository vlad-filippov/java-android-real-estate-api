package com.example.houses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HouseDetailsActivity extends AppCompatActivity {
    Button back;
    TextView priceView, descriptionView, bedroomsView, bathroomsView, sizeView;
    ImageView imageView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        getData();
        setBackButton();
        loadMap();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData() {
        priceView = findViewById(R.id.textViewHomePrice);
        imageView = findViewById(R.id.imageView15);
        descriptionView = findViewById(R.id.textViewDescription);
        bedroomsView = findViewById(R.id.textViewBedRooms);
        bathroomsView = findViewById(R.id.textViewBathRooms);
        sizeView = findViewById(R.id.textViewRoomSize);

        String price = getIntent().getStringExtra("PRICE");
        String image = getIntent().getStringExtra("IMAGE");
        String description = getIntent().getStringExtra("DESCRIPTION");
        String bedrooms = getIntent().getStringExtra("BEDROOMS");
        String bathrooms = getIntent().getStringExtra("BATHROOMS");
        String size = getIntent().getStringExtra("SIZE");

        priceView.setText(price);
        imageView.setImageResource(Integer.parseInt(image));
        descriptionView.setText(description);
        bedroomsView.setText(bedrooms);
        bathroomsView.setText(bathrooms);
        sizeView.setText(size);
    }

    private void loadMap() {
        webView = (WebView) findViewById(R.id.webViewMap);

        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.requestFocus(View.FOCUS_DOWN);
        webView.loadUrl("file:///android_asset/map.html");
    }

    private void setBackButton() {
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
    }
}