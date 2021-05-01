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
		try {
			String file = "C:/Users/PARTNERA-Z14N/Desktop/file.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			String[] repos = new String[] { "echarts", "superset", "dubbo", "spark", "airflow" };
			for (int j = 0; j < repos.length; j++) {

				String url = String.format("https://api.github.com/repos/apache/%s/contributors", repos[j]);
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				// optional default is GET
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

				JSONArray itemArray = new JSONArray(result);
				for (int i = 0; i < 5; i++) {
					System.out.println(i + 1);
					JSONObject e = itemArray.getJSONObject(i);
					String repo = repos[j];
					String username = e.getString("login");
					int contributions = e.getInt("contributions");
					JSONObject user = getUserInfo(e.getString("login"));
					String company = user.isNull("company") ? null : user.optString("company");
					String location = user.isNull("company") ? null : user.optString("location");
					writer.write("repo: " + repo + ", username: " + username + ", contributions: " + contributions
							+ ", company: " + company + ", location: " + location);
					writer.newLine();

				}
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static JSONObject getUserInfo(String username) throws Exception {

		String test = String.format("https://api.github.com/users/%s", username);
		URL obj = new URL(test);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
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

		JSONObject user = new JSONObject(result);

		return user;

	}

	

}
