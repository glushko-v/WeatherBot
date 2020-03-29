import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=57ec359ceba44fe9b1e5c53a7dd5cd23");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

//        result = result.replace("_", "\\_");


        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));
        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));


        }


        return "Город: " + model.getName() + "\n" +
                "Температура воздуха: " + model.getTemp() + "\u00B0" + "\n" +
                "Влажность: " + model.getHumidity() + "%" + "\n" +
                "Погода: " + model.getMain() + "\n" +
                "https://openweathermap.org/img/w/" + model.getIcon() + ".png";


    }
}
