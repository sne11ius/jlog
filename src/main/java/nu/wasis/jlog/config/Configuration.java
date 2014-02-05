package nu.wasis.jlog.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import nu.wasis.jlog.exception.InvalidConfigurationException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Configuration {

    private static Configuration instance;

    private String googleApiClientId;
    private String googleApiClientSecret;
    private String applicationName;
    private String ownerGoogleUserId;
    private String baseTemplatePath;
    private String baseUrl;

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
