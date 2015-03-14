package it.cosenonjaviste.testableandroidapps.v7;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import it.cosenonjaviste.testableandroidapps.ApplicationComponent;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.PostAdapter;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;


public class PostListActivity extends ActionBarActivity {

    public static final String MODEL = "model";

    @InjectView(R.id.list) ListView listView;

    @InjectView(R.id.progress_layout) View progressLayout;

    @InjectView(R.id.error_layout) View errorLayout;

    private PostAdapter adapter;

    @Inject PostListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetainedFragment<PostListComponent> retainedFragment = RetainedFragment.getOrCreate(this, "retained");
        PostListComponent component = retainedFragment.get();

        if (component == null) {
            ApplicationComponent appComponent = ((CnjApplication) getApplicationContext()).getComponent();
            component = Dagger_PostListComponent.builder().applicationComponent(appComponent).build();
            component.inject(this);
            retainedFragment.init(component, c -> presenter.destroy());
        } else {
            component.inject(this);
        }

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        adapter = new PostAdapter(getLayoutInflater());
        listView.setAdapter(adapter);

        PostListModel model;
        if (savedInstanceState != null) {
            model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
        } else {
            model = new PostListModel();
        }
        presenter.setModel(model);
    }

    @OnItemClick(R.id.list) void onItemClick(int position) {
        presenter.onItemClick(position);
    }

    public void startShareActivity(ShareModel model) {
        ShareActivity.createAndStart(this, model);
    }

    @OnClick(R.id.reload_button) void reloadData() {
        presenter.reloadData();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.resume(this);
    }

    public void updateView(PostListModel model, boolean runntingIsTask) {
        if (model.getErrorText() != null) {
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else if (runntingIsTask) {
            progressLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (model.getItems() != null) {
            adapter.setItems(model.getItems());
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        presenter.pause();
    }
}
