import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import conf.AppConfig;
import conf.DataConfig;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) {
        super.onStart(app);
        ctx = new AnnotationConfigApplicationContext(AppConfig.class, DataConfig.class);
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
        return ctx.getBean(clazz);
    }
}
