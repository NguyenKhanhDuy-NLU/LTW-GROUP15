package vn.edu.nlu.fit.demo1.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleOAuthUtil {

    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public static String getAuthorizationUrl() {
        try {
            String clientId = OAuthConfig.getGoogleClientId();
            String redirectUri = OAuthConfig.getGoogleRedirectUri();
            String scope = "email profile openid";

            return GOOGLE_AUTH_URL +
                    "?client_id=" + URLEncoder.encode(clientId, "UTF-8") +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                    "&response_type=code" +
                    "&scope=" + URLEncoder.encode(scope, "UTF-8") +
                    "&access_type=offline" +
                    "&prompt=consent";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error creating authorization URL", e);
        }
    }

    public static String getAccessToken(String code) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(GOOGLE_TOKEN_URL);

        String jsonBody = String.format(
                "{\"code\":\"%s\",\"client_id\":\"%s\",\"client_secret\":\"%s\",\"redirect_uri\":\"%s\",\"grant_type\":\"authorization_code\"}",
                code,
                OAuthConfig.getGoogleClientId(),
                OAuthConfig.getGoogleClientSecret(),
                OAuthConfig.getGoogleRedirectUri()
        );

        StringEntity entity = new StringEntity(jsonBody);
        entity.setContentType("application/json");
        post.setEntity(entity);

        try (CloseableHttpResponse response = client.execute(post)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            JsonObject json = new Gson().fromJson(responseBody, JsonObject.class);
            return json.get("access_token").getAsString();
        } finally {
            client.close();
        }
    }

    public static JsonObject getUserInfo(String accessToken) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(GOOGLE_USERINFO_URL);
        get.setHeader("Authorization", "Bearer " + accessToken);

        try (CloseableHttpResponse response = client.execute(get)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            return new Gson().fromJson(responseBody, JsonObject.class);
        } finally {
            client.close();
        }
    }
}