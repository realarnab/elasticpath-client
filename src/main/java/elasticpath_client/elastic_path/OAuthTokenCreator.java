package elasticpath_client.elastic_path;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OAuthTokenCreator {

	private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");

    private static final String CLIENT_ID = "pFjeLgv6FYWJqHTpphbdhH9mLUQt0RJekrTyAEE20X";
    private static final String CLIENT_SECRET = "eakbhJndHpcaoIkY1oppU7VQe4B6XidkcuUdNLPFpL";

    private final OkHttpClient client;

    public OAuthTokenCreator() {
        this.client = new OkHttpClient();
    }

    public void getAccessToken() throws IOException {
        String url = API_BASE_URL + "/oauth/access_token";

        String bodyContent = "client_id=" + CLIENT_ID +
                             "&client_secret=" + CLIENT_SECRET +
                             "&grant_type=client_credentials";

        RequestBody body = RequestBody.create(bodyContent, FORM);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Token retrieved successfully!");
                System.out.println(response.body().string());
            } else {
                System.err.println("❌ Failed to retrieve token. Status code: " + response.code());
                System.err.println(response.body().string());
            }
        }
    }

    public static void main(String[] args) {
        OAuthTokenCreator tokenCreator = new OAuthTokenCreator();

        try {
            tokenCreator.getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
