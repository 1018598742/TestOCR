package com.fta.testtesstwo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private int result = 109;
    private Intent intent = null;
    private int REQUEST_MEDIA_PROJECTION = 1;
    private MediaProjectionManager mMediaProjectionManager;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mMediaProjectionManager = (MediaProjectionManager) getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startIntent();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startIntent() {
        if (intent != null && result != 0) {
            ((ShotApplication) getApplication()).setResult(result);
            ((ShotApplication) getApplication()).setIntent(intent);
            Intent intent = new Intent(getApplicationContext(), ShotService.class);
            startService(intent);
        } else {
            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
            ((ShotApplication) getApplication()).setMediaProjectionManager(mMediaProjectionManager);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            } else if (data != null && resultCode != 0) {
                result = resultCode;
                intent = data;
                ((ShotApplication) getApplication()).setResult(resultCode);
                ((ShotApplication) getApplication()).setIntent(data);
                Intent intent = new Intent(getApplicationContext(), ShotService.class);
                startService(intent);

                finish();
            }
        }
    }
}

