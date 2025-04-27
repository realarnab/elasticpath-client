package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomerTokenCreator {

	private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
    private static final MediaType JSON = MediaType.parse("application/json");
    private final OkHttpClient client;
    private final String accessToken;

    public CustomerTokenCreator(String accessToken) {
        this.client = new OkHttpClient();
        this.accessToken = accessToken;
    }

    public void generateToken(String email, String password) throws IOException {
        String url = API_BASE_URL + "/v2/customers/tokens";

        String tokenJson = buildTokenJson(email, password);

        RequestBody body = RequestBody.create(tokenJson, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Token generated successfully!");
                System.out.println(response.body().string());
            } else {
                System.err.println("❌ Failed to generate token. Status code: " + response.code());
                System.err.println(response.body().string());
            }
        }
    }

    private String buildTokenJson(String email, String password) {
        return "{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"token\",\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"password\": \"" + password + "\",\n" +
                "    \"authentication_mechanism\": \"password\"\n" +
                "  }\n" +
                "}";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Access Token: ");
        String accessToken = scanner.nextLine();

        CustomerTokenCreator creator = new CustomerTokenCreator(accessToken);

        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            creator.generateToken(email, password);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
