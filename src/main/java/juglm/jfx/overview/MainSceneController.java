package juglm.jfx.overview;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * documentation
 */
public class MainSceneController {

    private final static XLogger LOG = XLoggerFactory.getXLogger(MainSceneController.class);

    //

    private FXMLLoader loader;
    
    private Scene scene;

    @FXML
    private TextField textField;

    @FXML
    private Label label;

    @FXML
    private CheckBox checkBox;

    
    private final StringProperty text = new SimpleStringProperty();
    public final StringProperty textProperty() { return text; }
    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }

    
    //

    /**
     * documentation
     */
    public MainSceneController(String text) throws Exception {

        if (LOG.isTraceEnabled()) { LOG.entry(); }

        //

        loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/META-INF/fxml/MainScene.fxml"));
        loader.setController(this);
        scene = loader.load();
              
        //

        setText(text);

        textProperty().addListener((observable, oldValue, newValue) -> {
                if (LOG.isDebugEnabled()) { LOG.debug("oldValue={}, newValue={}", oldValue, newValue); }
            });
        
        //

        if (LOG.isTraceEnabled()) { LOG.exit(); }        
    }

    /**
     * documentation
     */
    @FXML
    private void initialize() {
        
        if (LOG.isTraceEnabled()) { LOG.entry(); }

        //

        textField.textProperty().bindBidirectional(textProperty());
        label.textProperty().bind(textProperty().length().asString());
        checkBox.selectedProperty().bind(textProperty().length().isEqualTo(0));
                
        //

        if (LOG.isTraceEnabled()) { LOG.exit(); }        
    }

    /**
     * documentation
     */
    public Scene getScene() { return scene; }

    
}
