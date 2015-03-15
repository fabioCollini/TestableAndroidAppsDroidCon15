package it.cosenonjaviste.testableandroidapps.v10;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.ApplicationComponent;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.PostAdapter;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.mvplib.BaseActivity;
import it.cosenonjaviste.testableandroidapps.mvplib.Presenter;


public class PostListActivity extends BaseActivity<PostListModel, PostListActivity> {

    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PostAdapter(getLayoutInflater());
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);
    }

    @Override protected Presenter<PostListModel, PostListActivity> createPresenter() {
        ApplicationComponent component = ((CnjApplication) getApplicationContext()).getComponent();
        return component.getPostListPresenterV10();
    }

    @Override protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void updateItems(List<Post> items) {
        adapter.setItems(items);
    }
}
