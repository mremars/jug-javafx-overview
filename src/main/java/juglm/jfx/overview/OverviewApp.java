package juglm.jfx.overview;

import java.time.ZoneId;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * documentation
 */
public class OverviewApp extends Application {

    private static final XLogger LOG = XLoggerFactory.getXLogger(OverviewApp.class);

    //

    /**
     */
    public OverviewApp() {
        
        super();

        //

        if (LOG.isTraceEnabled()) { LOG.entry(); }

    }

    /**
     */
    @Override
    public void init() throws Exception {

        if (LOG.isTraceEnabled()) { LOG.entry(); }

        //
        
        if (LOG.isTraceEnabled()) { LOG.exit(); }
    }

    /**
     * documentation
     */
    @Override
    public void start(Stage stage) throws Exception {

        if (LOG.isTraceEnabled()) { LOG.entry(stage); }

        //

        // MainSceneController sceneController = new MainSceneController("Hello JUG");
        
        WorldClockSceneController sceneController = new WorldClockSceneController(ZoneId.systemDefault());
        
        stage.setScene(sceneController.getScene());
        
        stage.show();

        //

        if (LOG.isTraceEnabled()) { LOG.exit(); }
        
    }

    /**
     */
    @Override
    public void stop() throws Exception {

        if (LOG.isTraceEnabled()) { LOG.entry(); }
        
        //

        if (LOG.isTraceEnabled()) { LOG.exit(); }
    }
    
}

