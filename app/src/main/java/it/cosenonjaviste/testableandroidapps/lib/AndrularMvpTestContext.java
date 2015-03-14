package it.cosenonjaviste.testableandroidapps.lib;

import it.cosenonjaviste.testableandroidapps.RetainedFragment;

public class AndrularMvpTestContext<M, V> extends AndrularMvpContext<M, V> {
    public AndrularMvpTestContext(V view, Presenter<M, V> presenter) {
        super(view, null, RetainedFragment.create(presenter));
    }

    @Override protected BaseContext createAndrularContext(Object view, Object model, Presenter presenter) {
        return new AndrularTestContext(model, presenter);
    }

    public int getListSize(int list) {
        return getContext().getListSize(list);
    }

    private AndrularTestContext getContext() {
        return (AndrularTestContext) context;
    }

    public String getText(int viewId) {
        return getContext().getText(viewId);
    }

    public void clickOnItem(int listId, int position) {
        getContext().clickOnItem(listId, position);
    }
}
