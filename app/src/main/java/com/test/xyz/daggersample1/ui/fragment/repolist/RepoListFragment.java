package com.test.xyz.daggersample1.ui.fragment.repolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivity;
import com.test.xyz.daggersample1.ui.fragment.base.BaseFragment;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample1.ui.util.CommonConstants;
import com.test.xyz.daggersample1.ui.util.CommonUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepoListFragment extends BaseFragment implements RepoListView {
    static String TAG = RepoListFragment.class.getName();

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
    public void showRepoList(final List<String> values) {
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
                displayResults(new ArrayList<String>(){});
            }
        });
    }

    private void displayResults(final List<String> values) {
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
}
