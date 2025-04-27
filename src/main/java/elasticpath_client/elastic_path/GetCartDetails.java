package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetCartDetails {

	private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
    private final OkHttpClient client;

    public GetCartDetails() {
        this.client = new OkHttpClient();
    }

    public void getCartDetails(String cartId, String bearerToken) throws IOException {
        String url = API_BASE_URL + "/v2/carts/" + cartId;

        Request request = new Request.Builder()
                .url(url)
                .get()  // Corrected: No body
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + bearerToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Cart details fetched successfully!");
                System.out.println(response.body().string());
            } else {
                System.err.println("❌ Failed to fetch cart details. Status code: " + response.code());
                System.err.println(response.body().string());
            }
        }
    }

    public static void main(String[] args) {
        GetCartDetails cartFetcher = new GetCartDetails();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Cart ID: ");
        String cartId = scanner.nextLine();

        System.out.print("Enter Bearer Token: ");
        String bearerToken = scanner.nextLine();

        try {
            cartFetcher.getCartDetails(cartId, bearerToken);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
