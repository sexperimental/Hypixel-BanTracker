import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Main {

    public static String PUNISHMENT_API = "https://api.plancke.io/hypixel/v1/punishmentStats";
    public static String Prefix = "[Logger]";

    public static int LatestWTotal = 0;
    public static int LatestSTotal = 0;
    public static boolean isFirstRun = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        System.out.println(Prefix + " mc.hypixel.net ban tracker");

        while (true) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(PUNISHMENT_API)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());

            int WTotal = jsonObject.getJSONObject("record").getInt("watchdog_total");
            int STotal = jsonObject.getJSONObject("record").getInt("staff_total");

            if (!isFirstRun) {
                if (WTotal > LatestWTotal) {
                    System.out.println(Prefix + " Banned by watchdog +" + (WTotal - LatestWTotal));
                }

                if (STotal > LatestSTotal) {
                    System.out.println(Prefix + " Banned by staff +" + (STotal - LatestSTotal));
                }
            }

            LatestWTotal = WTotal;
            LatestSTotal = STotal;
            isFirstRun = false;

            Thread.sleep(1000);

        }
    }
}
