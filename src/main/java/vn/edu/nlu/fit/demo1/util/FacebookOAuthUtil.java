package vn.edu.nlu.fit.demo1.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FacebookOAuthUtil {

    private static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/v18.0/dialog/oauth";
    private static final String FACEBOOK_TOKEN_URL = "https://graph.facebook.com/v18.0/oauth/access_token";
    private static final String FACEBOOK_USERINFO_URL = "https://graph.facebook.com/me";

    public static String getAuthorizationUrl() {
        try {
            String appId = OAuthConfig.getFacebookAppId();
            String redirectUri = OAuthConfig.getFacebookRedirectUri();
            String scope = "email,public_profile";

            return FACEBOOK_AUTH_URL +
                    "?client_id=" + URLEncoder.encode(appId, "UTF-8") +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                    "&scope=" + URLEncoder.encode(scope, "UTF-8") +
                    "&response_type=code";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error creating authorization URL", e);
        }
    }

    public static String getAccessToken(String code) throws IOException {
        String url = FACEBOOK_TOKEN_URL +
                "?client_id=" + OAuthConfig.getFacebookAppId() +
                "&client_secret=" + OAuthConfig.getFacebookAppSecret() +
                "&redirect_uri=" + OAuthConfig.getFacebookRedirectUri() +
                "&code=" + code;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        try (CloseableHttpResponse response = client.execute(get)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            JsonObject json = new Gson().fromJson(responseBody, JsonObject.class);
            return json.get("access_token").getAsString();
        } finally {
            client.close();
        }
    }

    public static JsonObject getUserInfo(String accessToken) throws IOException {
        String url = FACEBOOK_USERINFO_URL +
                "?fields=id,name,email,picture" +
                "&access_token=" + accessToken;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        try (CloseableHttpResponse response = client.execute(get)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            return new Gson().fromJson(responseBody, JsonObject.class);
        } finally {
            client.close();
        }
    }
}