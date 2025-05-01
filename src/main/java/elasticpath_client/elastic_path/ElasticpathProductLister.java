package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ElasticpathProductLister {

    public static final String API_BASE_URL = "https://useast.api.elasticpath.com";

    private final OkHttpClient client = new OkHttpClient();

    public void listProducts(String accessToken) throws IOException {
        String url = API_BASE_URL + "/pcm/products";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Product List:");
                System.out.println(responseBody);
            } else {
                System.err.println("Failed to fetch products, Status code: " + response.code());
                if (response.body() != null) {
                    System.err.println(response.body().string());
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Bearer Token: ");
        String token = scanner.nextLine();
        scanner.close();

        ElasticpathProductLister lister = new ElasticpathProductLister();
        try {
            lister.listProducts(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
