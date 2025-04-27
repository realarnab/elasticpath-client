package elasticpath_client.elastic_path;

import java.io.IOException;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomerCreator {

	    private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
	    private static final MediaType JSON = MediaType.parse("application/json");
	    private final OkHttpClient client;
	    private final String accessToken;

	    public CustomerCreator(String accessToken) {
	        this.client = new OkHttpClient();
	        this.accessToken = accessToken;
	    }

	    public void createCustomer(String name, String email, String externalRef, String password) throws IOException {
	        String url = API_BASE_URL + "/v2/customers";

	        String customerJson = buildCustomerJson(name, email, externalRef, password);

	        RequestBody body = RequestBody.create(customerJson, JSON);

	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .addHeader("Authorization", "Bearer " + accessToken)
	                .addHeader("Content-Type", "application/json")
	                .build();

	        try (Response response = client.newCall(request).execute()) {
	            if (response.isSuccessful()) {
	                System.out.println("✅ Customer created successfully!");
	                System.out.println(response.body().string());
	            } else {
	                System.err.println("❌ Failed to create customer. Status code: " + response.code());
	                System.err.println(response.body().string());
	            }
	        }
	    }

	    private String buildCustomerJson(String name, String email, String externalRef, String password) {
	        return "{\n" +
	                "  \"data\": {\n" +
	                "    \"type\": \"customer\",\n" +
	                "    \"name\": \"" + name + "\",\n" +
	                "    \"email\": \"" + email + "\",\n" +
	                "    \"external_ref\": \"" + externalRef + "\",\n" +
	                "    \"password\": \"" + password + "\"\n" +
	                "  }\n" +
	                "}";
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter Access Token: ");
	        String accessToken = scanner.nextLine();

	        CustomerCreator creator = new CustomerCreator(accessToken);

	        System.out.print("Enter Customer Name: ");
	        String name = scanner.nextLine();

	        System.out.print("Enter Customer Email: ");
	        String email = scanner.nextLine();

	        System.out.print("Enter External Reference (external_ref): ");
	        String externalRef = scanner.nextLine();

	        System.out.print("Enter Password: ");
	        String password = scanner.nextLine();

	        try {
	            creator.createCustomer(name, email, externalRef, password);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            scanner.close();
	        }
	    }
	}


