package com.test.xyz.daggersample1.ui.fragment.repolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivity;
import com.test.xyz.daggersample1.ui.fragment.base.BaseFragment;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample1.ui.util.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepoListFragment extends BaseFragment implements RepoListView {
    static String TAG = RepoListFragment.class.getName();

    @Inject
    RepoListPresenter presenter;

    @InjectView(R.id.repoList)
    ListView repoListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repolist, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    protected void initailizeFragment(@Nullable Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new RepoListFragmentModule(this))
                .inject(this);

        presenter.requestRepoList("hazems");
    }

    @Override
    public void showRepoList(final String[] values) {
        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RepoListFragment.this.getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);

                repoListView.setAdapter(adapter);

                repoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {

                                Intent intent = new Intent(RepoListFragment.this.getActivity(), RepoDetailsActivity.class);

                                startActivity(intent);
                            }

                });
            }


        });
    }

    @Override
    public void showError(final String errorMessage) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showAlert(RepoListFragment.this.getActivity(), errorMessage);
            }
        });
    }
}
