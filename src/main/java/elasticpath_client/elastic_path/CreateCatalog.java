package elasticpath_client.elastic_path;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateCatalog {
	

	    private static final String API_BASE_URL = "https://useast.api.elasticpath.com";
	    private static final MediaType JSON = MediaType.parse("application/json");
	    private final OkHttpClient client;
	    private final String accessToken;

	    public CreateCatalog(String accessToken) {
	        this.client = new OkHttpClient();
	        this.accessToken = accessToken;
	    }

	    public void createACatalog(String name, String description, String hierarchyId, String pricebookId) throws IOException {
	        String url = API_BASE_URL + "/pcm/catalogs";

	        String catalogJson = buildCatalogJson(name, description, hierarchyId, pricebookId);

	        RequestBody body = RequestBody.create(catalogJson, JSON);

	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .addHeader("Authorization", "Bearer " + accessToken)
	                .addHeader("Content-Type", "application/json")
	                .build();

	        try (Response response = client.newCall(request).execute()) {
	            if (response.isSuccessful()) {
	                System.out.println("✅ Catalog created successfully!");
	                System.out.println(response.body().string());
	            } else {
	                System.err.println("❌ Failed to create catalog. Status code: " + response.code());
	                System.err.println(response.body().string());
	            }
	        }
	    }

	    private String buildCatalogJson(String name, String description, String hierarchyId, String pricebookId) {
	        return "{\n" +
	                "  \"data\": {\n" +
	                "    \"type\": \"catalog\",\n" +
	                "    \"attributes\": {\n" +
	                "      \"name\": \"" + name + "\",\n" +
	                "      \"description\": \"" + description + "\",\n" +
	                "      \"hierarchy_ids\": [\n" +
	                "        \"" + hierarchyId + "\"\n" +
	                "      ],\n" +
	                "      \"pricebook_id\": \"" + pricebookId + "\"\n" +
	                "    }\n" +
	                "  }\n" +
	                "}";
	    }

	    public static void main(String[] args) {
	        String token = "abd6c5bf0777ec045eb0e4dae03f97f90bffff8f";
	        CreateCatalog creator = new CreateCatalog(token);

	        try {
	            creator.createACatalog(
	                    "Europiann Customer catalog",
	                    "This is a catalog for Europian customer",
	                    "f18cccc5-24e9-4ee9-978b-0ef96726882c",
	                    "6e59fa7f-606f-41c8-b974-3e7c34bdf4f0"
	            );
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
