package igrad;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import igrad.commons.core.Config;
import igrad.commons.core.LogsCenter;
import igrad.commons.core.Version;
import igrad.commons.exceptions.DataConversionException;
import igrad.commons.util.ConfigUtil;
import igrad.commons.util.StringUtil;
import igrad.logic.Logic;
import igrad.logic.LogicManager;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.UserPrefs;
import igrad.model.util.SampleDataUtil;
import igrad.storage.CourseBookStorage;
import igrad.storage.JsonCourseBookStorage;
import igrad.storage.JsonUserPrefsStorage;
import igrad.storage.Storage;
import igrad.storage.StorageManager;
import igrad.storage.UserPrefsStorage;
import igrad.ui.Ui;
import igrad.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing iGrad ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        ReadOnlyUserPrefs userPrefs = initPrefs(userPrefsStorage);
        CourseBookStorage courseBookStorage = new JsonCourseBookStorage(userPrefs.getCourseBookFilePath());
        storage = new StorageManager(courseBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, model);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s course book and {@code userPrefs}. <br>
     * The data from the sample course book will be used instead if {@code storage}'s course book is not found,
     * or an empty course book will be used instead if errors occur when reading {@code storage}'s course book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyCourseBook> courseBookOptional;
        Optional<ReadOnlyUserPrefs> userPrefsOptional;
        ReadOnlyCourseBook initialData;
        try {
            courseBookOptional = storage.readCourseBook();

            if (courseBookOptional.isEmpty()) {
                logger.info("CourseBook Data file not found. Will be starting with an empty CourseBook");
            }

            initialData = courseBookOptional.orElseGet(SampleDataUtil::getSampleCourseBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty CourseBook");
            initialData = new CourseBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty CourseBook");
            initialData = new CourseBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected ReadOnlyUserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> userPrefsOptional = storage.readUserPrefs();

            if (!userPrefsOptional.isPresent()) {
                logger.info("UserPrefs Data file not found. Will be starting with a sample UserPrefs");
            }

            initializedPrefs = userPrefsOptional.orElseGet(SampleDataUtil::getSampleUserPrefs);
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty UserPrefs");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }


    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting iGrad " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping iGrad ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
