package com.test.xyz.daggersample1.ui.activity.repolist;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.ui.activity.base.BaseActivity;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivity;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample1.ui.util.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepoListActivity extends BaseActivity implements RepoListView {
    static String TAG = RepoListActivity.class.getName();

    @Inject
    RepoListPresenter presenter;

    @InjectView(R.id.repoList)
    ListView repoListView;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_repolist);

        ButterKnife.inject(this);

        DaggerApplication.get(this)
                         .getAppComponent()
                         .plus(new RepoListActivityModule(this))
                         .inject(this);

        presenter.requestRepoList("hazems");
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Destroying activity is called ...");
        super.onDestroy();
    }

    @Override
    public void showRepoList(String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        repoListView.setAdapter(adapter);

        repoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(RepoListActivity.this, RepoDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showAlert(RepoListActivity.this, errorMessage);
            }
        });
    }
}
