package com.test.xyz.daggersample.view.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.xyz.daggersample.di.DaggerApplication;
import com.test.xyz.daggersample.view.fragment.base.BaseFragment;
import com.test.xyz.daggersample.presenter.main.MainPresenter;
import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample.view.util.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends BaseFragment implements MainView, View.OnClickListener {
    private static String TAG = MainFragment.class.getName();

    @Inject
    MainPresenter presenter;

    @InjectView(R.id.userNameText)
    EditText userNameText;

    @InjectView(R.id.cityText)
    EditText cityText;

    @InjectView(R.id.btnShowInfo)
    Button showInfoButton;

    @InjectView(R.id.resultView)
    TextView resultView;

    @InjectView(R.id.progress)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    protected void initializeFragment(Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new MainFragmentModule(this))
                .inject(this);

        showInfoButton.setOnClickListener(this);
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
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "MainFragment is destroyed ...");
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
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userNameText.setError(getString(messageId));
            }
        });
    }

    @Override
    public void showCityNameError(final int messageId) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cityText.setError(getString(messageId));
            }
        });

    }

    @Override
    public void showBusyIndicator() {

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideBusyIndicator() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showResult(final String result) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultView.setText(result);
            }
        });
    }

    @Override
    public void showError(final String error) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showDefaultAlert(MainFragment.this.getActivity(), error);
            }
        });
    }
}