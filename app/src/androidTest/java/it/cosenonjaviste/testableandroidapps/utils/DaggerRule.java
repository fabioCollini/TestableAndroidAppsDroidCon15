package it.cosenonjaviste.testableandroidapps.utils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.functions.Action1;

public class DaggerRule<C> implements TestRule {

    private C component;

    private Action1<C> afterInjectAction;

    public DaggerRule(C component, Action1<C> afterInjectAction) {
        this.component = component;
        this.afterInjectAction = afterInjectAction;
    }

    @Override public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {

                if (afterInjectAction != null) {
                    afterInjectAction.call(component);
                }

                base.evaluate();
            }
        };
    }
}
