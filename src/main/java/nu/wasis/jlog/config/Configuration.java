package nu.wasis.jlog.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Singleton;

import nu.wasis.jlog.exception.InvalidConfigurationException;

import org.apache.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Singleton
public class Configuration {

    private static final Logger LOG = Logger.getLogger(Configuration.class);

    @Resource(lookup = "nu/wasis/jlog/config")
    private String configuration;

    private static Configuration instance;

    private String googleApiClientId;
    private String googleApiClientSecret;
    private String applicationName;
    private String ownerGoogleUserId;
    private String baseTemplatePath;
    private String baseUrl;

    @PostConstruct
    public void init() {
        LOG.debug(configuration);
        try {
            final Configuration loadedConfig = new GsonBuilder().create().fromJson(new FileReader(configuration), Configuration.class);
            this.googleApiClientId = loadedConfig.googleApiClientId;
            this.googleApiClientSecret = loadedConfig.googleApiClientSecret;
            this.applicationName = loadedConfig.applicationName;
            this.ownerGoogleUserId = loadedConfig.ownerGoogleUserId;
            this.baseTemplatePath = loadedConfig.baseTemplatePath;
            this.baseUrl = loadedConfig.baseUrl;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        LOG.debug(this);
    }

    public static Configuration getInstance() {
        return instance;
    }

    public static void load(final String filename) {
        checkIsFile(filename);
        try {
            Configuration.instance = loadFile(filename);
        } catch (final JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    private static void checkIsFile(final String filename) {
        final File file = new File(filename);
        if (!file.exists() || !file.canRead()) {
            throw new InvalidConfigurationException("Cannot read from file `" + filename + "'.");
        }
    }

    private static Configuration loadFile(final String filename) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return new GsonBuilder().create().fromJson(new FileReader(filename), Configuration.class);
    }

    public String getGoogleApiClientId() {
        return googleApiClientId;
    }

    public String getGoogleApiClientSecret() {
        return googleApiClientSecret;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getOwnerGoogleUserId() {
        return ownerGoogleUserId;
    }

    public String getBaseTemplatePath() {
        return baseTemplatePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
