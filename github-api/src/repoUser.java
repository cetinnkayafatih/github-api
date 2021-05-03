import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class repoUser {

	public static void main(String[] args) {
		
		String[] repos = new String[] { "echarts", "superset", "dubbo", "spark", "airflow" };
		String repo, username, company, location;
		int contributions;
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
			
			
			for (int i = 0; i <repos.length; i++) {

				String url = String.format("https://api.github.com/repos/apache/%s/contributors", repos[i]);
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				String result = "";
				result = response.toString();
				JSONArray jsonArray = new JSONArray(result); // Store the string in JSON array as objects 
				
				for (int j = 0; j < 10; j++) {
					System.out.println(i+1+"."+(j+1));
					JSONObject contributor= jsonArray.getJSONObject(j); // Get the object of each contributor
					repo = repos[i];
					username = contributor.getString("login");
					contributions = contributor.getInt("contributions");
					JSONObject user = getUserInfo(contributor.getString("login")); 	         // Get user object with username
					company = user.isNull("company") ? null : user.optString("company");    // Check for null values
					location = user.isNull("location") ? null : user.optString("location");
					writer.write("repo: " + repo + ", username: " + username + ", contributions: " + contributions
							+ ", company: " + company + ", location: " + location);
					writer.newLine();

				}
				
			}
			
			writer.close();
			System.out.println("Users written to users.txt file");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static JSONObject getUserInfo(String username) throws Exception {

		String url = String.format("https://api.github.com/users/%s", username);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String result = "";
		result = response.toString();

		JSONObject user = new JSONObject(result); // create JSONObject for user informations

		return user;

	}

}
