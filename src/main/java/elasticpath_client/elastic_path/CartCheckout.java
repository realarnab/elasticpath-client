package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CartCheckout {

	    private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
	    private final OkHttpClient client;

	    public CartCheckout() {
	        this.client = new OkHttpClient();
	    }

	    public void checkoutCart(String cartId, String bearerToken, String customerId) throws IOException {
	        String url = API_BASE_URL + "/v2/carts/" + cartId + "/checkout";

	        String requestBodyJson = "{\n" +
	                "  \"data\": {\n" +
	                "    \"customer\": {\n" +
	                "      \"id\": \"" + customerId + "\"\n" +
	                "    },\n" +
	                "    \"billing_address\": {\n" +
	                "      \"first_name\": \"Ron\",\n" +
	                "      \"last_name\": \"Swanson\",\n" +
	                "      \"company_name\": \"Ron Swanson Enterprises\",\n" +
	                "      \"line_1\": \"1 Sunny Street\",\n" +
	                "      \"line_2\": \"\",\n" +
	                "      \"city\": \"Sunny Town\",\n" +
	                "      \"county\": \"Orange\",\n" +
	                "      \"region\": \"CA\",\n" +
	                "      \"postcode\": \"92802\",\n" +
	                "      \"country\": \"US\"\n" +
	                "    },\n" +
	                "    \"shipping_address\": {\n" +
	                "      \"first_name\": \"Ron\",\n" +
	                "      \"last_name\": \"Swanson\",\n" +
	                "      \"company_name\": \"Ron Swanson Enterprises\",\n" +
	                "      \"line_1\": \"1 Sunny Street\",\n" +
	                "      \"line_2\": \"\",\n" +
	                "      \"city\": \"Sunny Town\",\n" +
	                "      \"county\": \"Orange\",\n" +
	                "      \"region\": \"CA\",\n" +
	                "      \"postcode\": \"92802\",\n" +
	                "      \"country\": \"US\"\n" +
	                "    }\n" +
	                "  }\n" +
	                "}";

	        RequestBody body = RequestBody.create(requestBodyJson, MediaType.parse("application/json"));

	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .addHeader("accept", "application/json")
	                .addHeader("content-type", "application/json")
	                .addHeader("Authorization", "Bearer " + bearerToken)
	                .build();

	        try (Response response = client.newCall(request).execute()) {
	            if (response.isSuccessful()) {
	                System.out.println("Checkout successful!");
	                System.out.println(response.body().string());
	            } else {
	                System.err.println("Checkout failed. Status code: " + response.code());
	                System.err.println(response.body().string());
	            }
	        }
	    }

	    public static void main(String[] args) {
	        CartCheckout checkout = new CartCheckout();
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter Cart ID: ");
	        String cartId = scanner.nextLine();

	        System.out.print("Enter Access/Bearer Token: ");
	        String bearerToken = scanner.nextLine();

	        System.out.print("Enter Customer ID: ");
	        String customerId = scanner.nextLine();

	        try {
	            checkout.checkoutCart(cartId, bearerToken, customerId);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            scanner.close();
	        }
	    }
	}


