package elasticpath_client.elastic_path;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ElasticpathProductLister {

	public static final String API_BASE_URL = "https://useast.api.elasticpath.com";
	public static final String ACCESS_TOKEN = "9d2a64fea96b32a34c1c0c10c58ce88862e1e50d";
	
	private final OkHttpClient client = new OkHttpClient();
	
	public void listProducts() throws IOException{
		String url = API_BASE_URL +"/pcm/products";
		
		Request request = new Request.Builder()
				.url(url)
				.addHeader("Authorization", "Bearer " +ACCESS_TOKEN)
				.get()
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				System.out.println("Product Lists: ");
				System.out.println(responseBody);
			} else {
				System.err.println("Failed to fetch products, Status code: "+response.code());
				System.err.println(response.body().string());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String [] args) {
		ElasticpathProductLister lister = new ElasticpathProductLister();
		try {
			lister.listProducts();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
