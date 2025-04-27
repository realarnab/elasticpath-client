package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddProductToCartCreator {

	private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
    private final OkHttpClient client;

    public AddProductToCartCreator() {
        this.client = new OkHttpClient();
    }

    public void addProductToCart(String cartId, String productId, String bearerToken) throws IOException {
        String url = API_BASE_URL + "/v2/carts/" + cartId + "/items";

        String productJson = "{\n" +
                "  \"data\": {\n" +
                "    \"id\": \"" + productId + "\",\n" +
                "    \"type\": \"cart_item\",\n" +
                "    \"quantity\": 1\n" +
                "  }\n" +
                "}";

        RequestBody body = RequestBody.create(productJson, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + bearerToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Product added to cart successfully!");
                System.out.println(response.body().string());
            } else {
                System.err.println("❌ Failed to add product to cart. Status code: " + response.code());
                System.err.println(response.body().string());
            }
        }
    }

    public static void main(String[] args) {
        AddProductToCartCreator cartItemAdder = new AddProductToCartCreator();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Cart ID: ");
        String cartId = scanner.nextLine();

        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();

        System.out.print("Enter Bearer Token: ");
        String bearerToken = scanner.nextLine();

        try {
            cartItemAdder.addProductToCart(cartId, productId, bearerToken);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
