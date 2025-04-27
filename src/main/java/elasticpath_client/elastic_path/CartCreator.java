package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CartCreator {

	private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
    private final OkHttpClient client;

    public CartCreator() {
        this.client = new OkHttpClient();
    }

    public void createCart(String customerToken, String bearerToken) throws IOException {
        String url = API_BASE_URL + "/v2/carts";

        String cartJson = "{\n" +
                "  \"data\": {\n" +
                "    \"name\": \"my cart\",\n" +
                "    \"description\": \"my cart description\",\n" +
                "    \"discount_settings\": {\n" +
                "      \"custom_discounts_enabled\": false\n" +
                "    }\n" +
                "  }\n" +
                "}";

        RequestBody body = RequestBody.create(cartJson, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("x-moltin-customer-token", customerToken)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Cart created successfully!");
                System.out.println(response.body().string());
            } else {
                System.err.println("❌ Failed to create the cart. Status code: " + response.code());
                System.err.println(response.body().string());
            }
        }
    }

    public static void main(String[] args) {
        CartCreator cartCreator = new CartCreator();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer Token: ");
        String customerToken = scanner.nextLine();

        System.out.print("Enter Bearer Token: ");
        String bearerToken = scanner.nextLine();

        try {
            cartCreator.createCart(customerToken, bearerToken);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
