package it.cosenonjaviste.testableandroidapps.v10;

import it.cosenonjaviste.testableandroidapps.ApplicationComponent;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.mvplib.BaseActivity;
import it.cosenonjaviste.testableandroidapps.mvplib.Presenter;


public class ShareActivity extends BaseActivity<ShareModel, ShareActivity> {

    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    protected Presenter<ShareModel, ShareActivity> createPresenter() {
        ApplicationComponent component = ((CnjApplication) getApplicationContext()).getComponent();
        return component.getSharePresenterV10();
    }
}
