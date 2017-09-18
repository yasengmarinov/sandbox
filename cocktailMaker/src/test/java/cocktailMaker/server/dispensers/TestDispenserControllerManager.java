package cocktailMaker.server.dispensers;

import cocktailMaker.server.Utils;
import cocktailMaker.server.dispensers.interfaces.DispenserController;
import junit.framework.TestCase;

import java.util.Properties;

public class TestDispenserControllerManager extends TestCase {

    protected Properties properties;

    public void setUp() {
        properties = Utils.loadPropertiesFile("config/server_config.properties");
//        FakeDispenserControllerManager.getInstance().init(properties);
    }
    public void testInit() {
//        DispenserController controller = FakeDispenserControllerManager.getDispenserController(1);
//        controller.run();
//        controller.stop();
    }

}
