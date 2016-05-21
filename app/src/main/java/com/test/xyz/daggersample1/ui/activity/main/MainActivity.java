package com.test.xyz.daggersample1.ui.activity.main;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.ui.activity.base.BaseActivity;
import com.test.xyz.daggersample1.ui.activity.repolist.RepoListActivity;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenter;
import com.test.xyz.daggersample1.ui.util.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {

    @Inject
    MainPresenter presenter;

    @InjectView(R.id.userNameText)
    EditText userNameText;

    @InjectView(R.id.cityText)
    EditText cityText;

    @InjectView(R.id.btnShowInfo)
    Button showInfoButton;

    @InjectView(R.id.btnShowRepoList)
    Button showRepoButton;

    @InjectView(R.id.resultView)
    TextView resultView;

    @InjectView(R.id.progress)
    ProgressBar progressBar;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        DaggerApplication.get(this)
                .getAppComponent()
                .plus(new MainActivityModule(this))
                .inject(this);

        showInfoButton.setOnClickListener(this);
        showRepoButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnShowInfo) {
            presenter.requestInformation();
        } else if (v.getId() == R.id.btnShowRepoList) {
            Intent intent = new Intent(this, RepoListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public String getUserNameText() {
        return userNameText.getText().toString();
    }

    @Override
    public String getCityText() {
        return cityText.getText().toString();
    }

    @Override
    public void showUserNameError(final int messageId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userNameText.setError(getString(messageId));
            }
        });
    }

    @Override
    public void showCityNameError(final int messageId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cityText.setError(getString(messageId));
            }
        });

    }

    @Override
    public void showBusyIndicator() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideBusyIndicator() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showResult(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultView.setText(result);
            }
        });
    }

    @Override
    public void showError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showAlert(MainActivity.this, error);
            }
        });
    }
}