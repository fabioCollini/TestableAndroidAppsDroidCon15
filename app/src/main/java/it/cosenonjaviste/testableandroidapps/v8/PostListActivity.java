package it.cosenonjaviste.testableandroidapps.v8;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.OnItemClick;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedObservableFragment;
import it.cosenonjaviste.testableandroidapps.lib.AndrularContext;


public class PostListActivity extends ActionBarActivity {

    public static final String MODEL = "model";

    @Inject PostListPresenter presenter;

    private AndrularContext andrularContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        RetainedObservableFragment.getOrCreate(this, "retained", f -> {
//            ApplicationComponent appComponent = ((CnjApplication) getApplicationContext()).getComponent();
//            PostListComponent component = Dagger_PostListComponent.builder().applicationComponent(appComponent).build();
//            component.inject(this);
//            f.init(component, c -> presenter.destroy());
//        });

        RetainedObservableFragment<PostListComponent> retained = RetainedObservableFragment.getOrCreate(this, "retained", () -> {
            ApplicationComponent appComponent = ((CnjApplication) getApplicationContext()).getComponent();
            return Dagger_PostListComponent.builder().applicationComponent(appComponent).build();
        });
        retained.get().inject(this);
        retained.setOnDestroy(c -> presenter.destroy());

//        RetainedObservableFragment<PostListComponent> retainedFragment = RetainedObservableFragment.getOrCreate(this, "retained");
//        PostListComponent component = retainedFragment.get();
//
//        if (component == null) {
//            ApplicationComponent appComponent = ((CnjApplication) getApplicationContext()).getComponent();
//            component = Dagger_PostListComponent.builder().applicationComponent(appComponent).build();
//            component.inject(this);
//            retainedFragment.init(component, c -> presenter.destroy());
//        } else {
//            component.inject(this);
//        }

        setContentView(R.layout.activity_main);

        PostListModel model;
        if (savedInstanceState != null) {
            model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
        } else {
            model = new PostListModel();
        }
        presenter.setModel(model);
        andrularContext = new AndrularContext(this, model, presenter);
    }

    @OnItemClick(R.id.list) void onItemClick(int position) {
        presenter.onItemClick(position);
    }

    public void startShareActivity(ShareModel model) {
        ShareActivity.createAndStart(this, model);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.resume(this, andrularContext);
    }

    @Override protected void onPause() {
        super.onPause();
        presenter.pause();
    }
}
