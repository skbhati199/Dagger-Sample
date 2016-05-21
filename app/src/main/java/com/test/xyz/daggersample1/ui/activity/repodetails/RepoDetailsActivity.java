package com.test.xyz.daggersample1.ui.activity.repodetails;

import android.util.Log;
import android.widget.TextView;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.ui.activity.base.BaseActivity;
import com.test.xyz.daggersample1.ui.presenter.details.RepoDetailsPresenter;
import com.test.xyz.daggersample1.ui.util.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepoDetailsActivity extends BaseActivity implements RepoDetailsView {
    static String TAG = RepoDetailsActivity.class.getName();

    @Inject
    RepoDetailsPresenter presenter;

    //@Inject
    //RepoListService repoListService;

    @InjectView(R.id.repoDetails)
    TextView repoDetails;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_repodetails);

        ButterKnife.inject(this);

        Log.i(TAG, "Getting custom component inside RepoDetailsActivity");

        DaggerApplication.get(this).getAppComponent()
                                   .plus(new RepoDetailsActivityModule(this))
                                   .inject(this);


        presenter.requestRepoDetails("hazems", "test");
    }

    @Override
    public void showRepoDetails(String data) {
        repoDetails.setText(data);
    }

    @Override
    public void showError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showAlert(RepoDetailsActivity.this, errorMessage);
            }
        });
    }
}
