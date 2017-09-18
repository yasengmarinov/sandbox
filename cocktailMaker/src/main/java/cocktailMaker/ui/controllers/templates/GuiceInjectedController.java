package cocktailMaker.ui.controllers.templates;

import cocktailMaker.guice.annotations.ServerProperties;
import cocktailMaker.server.PageNavigator;
import com.google.inject.Inject;

import java.util.Properties;

public class GuiceInjectedController {

    @Inject @ServerProperties protected Properties serverProperties;
    @Inject protected PageNavigator pageNavigator;

    public GuiceInjectedController() {
    }

    @Inject
    public GuiceInjectedController(@ServerProperties Properties properties, PageNavigator pageNavigator) {
        this.serverProperties = properties;
        this.pageNavigator = pageNavigator;
    }
}
