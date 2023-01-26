package juglm.jfx.overview;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javafx.application.Application;

/**
 * documentation
 */
public class Main {

    private static final XLogger LOG = XLoggerFactory.getXLogger(Main.class);
    
    /**
     * documentation
     */
    public static void main(String[] args) {

        if (LOG.isTraceEnabled()) { LOG.entry(args); }

        //
        
        Application.launch(OverviewApp.class, args);
    }
    
}

