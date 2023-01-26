package juglm.jfx.overview;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * documentation
 */
public class WorldClockSceneController {

    private final static XLogger LOG = XLoggerFactory.getXLogger(WorldClockSceneController.class);

    //

    private DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.US);
    private DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.US);
    private DateTimeFormatter dayOfMonthFormatter = DateTimeFormatter.ofPattern("dd", Locale.US);
    private DateTimeFormatter monthOfYearFormatter = DateTimeFormatter.ofPattern("MMM", Locale.US);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);

    private ObservableList<ZoneId> zoneIds = FXCollections.observableArrayList();

    private final ReadOnlyObjectWrapper<ZoneId> zoneId = new ReadOnlyObjectWrapper<>();
    public final ReadOnlyObjectProperty<ZoneId> zoneIdProperty() { return zoneId.getReadOnlyProperty(); }
    public ZoneId getZoneId() { return zoneId.get();  }
    private void setZoneId(ZoneId zoneId) { this.zoneId.set(zoneId); }

    private final ReadOnlyObjectWrapper<ZonedDateTime> dateTime = new ReadOnlyObjectWrapper<>();
    public final ReadOnlyObjectProperty<ZonedDateTime> dateTimeProperty() { return dateTime.getReadOnlyProperty(); }
    public ZonedDateTime getDateTime() { return dateTime.get();  }
    private void setDateTime(ZonedDateTime dateTime) { this.dateTime.set(dateTime); }

    //

    private Scene scene;
    public Scene getScene() { return scene; }

    @FXML
    private Button addZoneButton;
    
    @FXML
    private TableView<ZoneId> zonesTableView;

    @FXML
    private TableColumn<ZoneId, String> zoneIdColumn;

    @FXML
    private TableColumn<ZoneId, String> zoneNameColumn;
    
    @FXML
    private Label yearLabel;

    @FXML
    private Label dayOfWeekLabel;

    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private Label monthOfYearLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label zoneCountLabel;
    
    //

    /**
     * documentation
     */
    public WorldClockSceneController(ZoneId zoneId) throws Exception {

        if (LOG.isTraceEnabled()) { LOG.entry(zoneId); }

        //

        zoneIds.add(zoneId);
        
        //

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/META-INF/fxml/WorldClockScene.fxml"));
        loader.setController(this);
        scene = loader.load();

        //

        AnimationTimer timer = new AnimationTimer() {
                /**
           ,* documentation
           ,*/
                @Override
                public void handle(long now) {
              setDateTime(ZonedDateTime.now(getZoneId()));
                }
            };
        timer.start();
        
        
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

        addZoneButton.setOnAction(e -> addZone());

        zoneIdColumn.setCellValueFactory(cdf -> { return new ReadOnlyStringWrapper(cdf.getValue().getId()); });

        zoneNameColumn.setCellValueFactory(cdf -> { return  new ReadOnlyStringWrapper(cdf.getValue().getDisplayName(TextStyle.FULL, Locale.FRANCE)); });
        
        zonesTableView.setItems(zoneIds);

        zonesTableView.getSelectionModel().selectFirst();
    
        zoneId.bind(zonesTableView.getSelectionModel().selectedItemProperty());
        
        yearLabel
            .textProperty()
            .bind(Bindings.createStringBinding(() -> (getDateTime() != null) ? getDateTime().format(yearFormatter) : "",
                                               dateTimeProperty()));

        dayOfWeekLabel
            .textProperty()
            .bind(Bindings.createStringBinding(() -> (getDateTime() != null) ? StringUtils.upperCase(getDateTime().format(dayOfWeekFormatter)) : "",
                                               dateTimeProperty()));

        dayOfMonthLabel
            .textProperty()
            .bind(Bindings.createStringBinding(() -> (getDateTime() != null) ? StringUtils.upperCase(getDateTime().format(dayOfMonthFormatter)) : "",
                                               dateTimeProperty()));

        monthOfYearLabel
            .textProperty()
            .bind(Bindings.createStringBinding(() -> (getDateTime() != null) ? StringUtils.upperCase(getDateTime().format(monthOfYearFormatter)) : "",
                                               dateTimeProperty()));

        timeLabel
            .textProperty()
            .bind(Bindings.createStringBinding(() -> (getDateTime() != null) ? getDateTime().format(timeFormatter) : "",
                                               dateTimeProperty()));

        zoneCountLabel.textProperty().bind(Bindings.size(zoneIds).asString().concat(" zone(s)"));
        
        //

        if (LOG.isTraceEnabled()) { LOG.exit(); }        
    }

    /**
     * documentation
     */
    public void addZone() {

        if (LOG.isTraceEnabled()) { LOG.entry(); }

        //

        ChoiceDialog<String> dialog = new ChoiceDialog<>(getZoneId().getId(), ZoneId.getAvailableZoneIds());
        dialog
            .showAndWait()
            .ifPresent(response -> {
                    ZoneId zoneId = ZoneId.of(response);
                    if (!zoneIds.contains(zoneId)) {
                        zoneIds.add(zoneId);
                    }
                });
    }

}

