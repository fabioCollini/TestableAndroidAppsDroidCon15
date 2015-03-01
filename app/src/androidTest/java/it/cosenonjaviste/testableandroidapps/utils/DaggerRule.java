package it.cosenonjaviste.testableandroidapps.utils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.functions.Action1;

import static android.support.test.espresso.Espresso.registerIdlingResources;

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

                final EspressoExecutor espressoExecutor = EspressoExecutor.newCachedThreadPool();

                if (afterInjectAction != null) {
                    afterInjectAction.call(component);
                }

                registerIdlingResources(espressoExecutor);

//                SchedulerManager.setIo(Schedulers.from(espressoExecutor));

                base.evaluate();
            }
        };
    }
}
