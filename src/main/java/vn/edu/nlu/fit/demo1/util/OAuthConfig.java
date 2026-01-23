package vn.edu.nlu.fit.demo1.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OAuthConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = OAuthConfig.class.getClassLoader()
                .getResourceAsStream("oauth.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find oauth.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading OAuth configuration", e);
        }
    }
    //gg
    public static String getGoogleClientId() {
        return properties.getProperty("google.client.id");
    }

    public static String getGoogleClientSecret() {
        return properties.getProperty("google.client.secret");
    }

    public static String getGoogleRedirectUri() {
        return properties.getProperty("google.redirect.uri");
    }

    // fb
    public static String getFacebookAppId() {
        return properties.getProperty("facebook.app.id");
    }

    public static String getFacebookAppSecret() {
        return properties.getProperty("facebook.app.secret");
    }

    public static String getFacebookRedirectUri() {
        return properties.getProperty("facebook.redirect.uri");
    }
}