package com.test.xyz.daggersample1.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.test.xyz.daggersample1.R;

import butterknife.InjectView;

public abstract class BaseActivity extends AppCompatActivity {
    String TAG = BaseActivity.class.getName();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateActivity();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    abstract protected void onCreateActivity();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "Base:onOptionsItemSelected() is called ...");

        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
