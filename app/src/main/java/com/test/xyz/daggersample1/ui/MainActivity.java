package com.test.xyz.daggersample1.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.xyz.daggersample1.R;

import com.test.xyz.daggersample1.service.api.HelloService;
import com.test.xyz.daggersample1.service.di.DaggerApplication;
import com.test.xyz.daggersample1.service.impl.HelloServiceManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    HelloService helloService;

    @InjectView(R.id.userNameText)
    EditText userNameText;

    @InjectView(R.id.greet)
    Button greetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        DaggerApplication.component().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        greetButton.setOnClickListener(this);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.greet) {
            String greetMessage = helloService.greet(userNameText.getText().toString());

            Snackbar mySnackbar = Snackbar.make( this.findViewById(R.id.activity_main_container),
                    greetMessage,
                    Snackbar.LENGTH_INDEFINITE);

            mySnackbar.show();
        }
    }
}
