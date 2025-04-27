package elasticpath_client.elastic_path;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ElasticpathProductCreator {
	
	public static final String API_BASE_URL = "https://useast.api.elasticpath.com";
	public static final String ACCESS_TOKEN = "9d2a64fea96b32a34c1c0c10c58ce88862e1e50d";
	
	public static void main(String[] args) {
		try {
			createProduct();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void createProduct() throws IOException{
		OkHttpClient client = new OkHttpClient();
		
		String url = API_BASE_URL + "/pcm/products";
		
		String productJson = "{\n" +
				"    \"data\": {\n" +
				"        \"type\": \"product\",\n" +
				"        \"attributes\": {\n" +
				"            \"name\": \"Sony Gaming Headset\",\n" +
				"            \"sku\": \"PGH1234\",\n" +
				"            \"description\": \"Sony brand gaming headset\",\n" +
				"            \"commodity_type\": \"physical\",\n" +
				"            \"status\": \"live\",\n" +
				"            \"mpn\": \"1234-5678\",\n" +
				"            \"upc\": \"123456123\"\n" +
				"        }\n" +
				"    }\n" +
				"}";
		
		RequestBody body = RequestBody.create(
				productJson,
				MediaType.parse("application/json"));
		
		Request request = new Request.Builder()
							.url(url)
							.post(body)
							.addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
							.build();
		
		try (Response response = client.newCall(request).execute()){
			if (response.isSuccessful()) {
				System.out.println("Product created successfully!");
				System.out.println(response.body().string());
			} else {
				System.out.println("Failed to create the product!!, Status code: "+response.code());
				System.out.println(response.body().string());
			}
		}
	}
}
