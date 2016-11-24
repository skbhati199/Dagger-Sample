package com.test.xyz.daggersample.view.fragment.repolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.xyz.daggersample.R;
import com.test.xyz.daggersample.di.DaggerApplication;
import com.test.xyz.daggersample.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample.service.api.model.Repo;
import com.test.xyz.daggersample.view.activity.repodetails.RepoDetailsActivity;
import com.test.xyz.daggersample.view.fragment.base.BaseFragment;
import com.test.xyz.daggersample.view.util.CommonConstants;
import com.test.xyz.daggersample.view.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepoListFragment extends BaseFragment implements RepoListView {
    private final static String TAG = RepoListFragment.class.getName();

    @Inject
    RepoListPresenter presenter;

    @InjectView(R.id.repoList)
    ListView repoListView;

    @InjectView(R.id.noAvlRepos)
    TextView noAvlRepos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repolist, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    protected void initializeFragment(@Nullable Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new RepoListFragmentModule(this))
                .inject(this);

        presenter.requestRepoList(CommonConstants.DEFAULT_USER_NAME);
    }

    @Override
    public void showRepoList(final List<Repo> values) {
        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                displayResults(values);
            }
        });
    }

    @Override
    public void showError(final String errorMessage) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showToastMessage(RepoListFragment.this.getActivity(), errorMessage);
                displayResults(new ArrayList<Repo>(){});
            }
        });
    }

    private void displayResults(List<Repo> repos) {
        final List<String> values = getRepoNameList(repos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RepoListFragment.this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        repoListView.setAdapter(adapter);
        repoListView.setEmptyView(noAvlRepos);

        repoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition = position;
                Intent intent = new Intent(RepoListFragment.this.getActivity(), RepoDetailsActivity.class);

                intent.putExtra(CommonConstants.REPO_DESC, values.get(itemPosition));

                startActivity(intent);
            }

        });
    }

    private List<String> getRepoNameList(List<Repo> repos) {
        final List<String> values = new ArrayList<>();

        for (int i = 0; i < repos.size(); ++i) {
            values.add(repos.get(i).name);
        }

        return values;
    }
}
