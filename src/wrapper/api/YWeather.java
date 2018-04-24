package wrapper.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import wrapper.*;

public class YWeather extends Wrapper{

	public YWeather(Input input, ListApi la) throws IOException {
		super(input, la);
	}
	
	public void fillWrapper(Input input, ListApi la) throws IOException{
		
		try{
		String cityName = input.getLocalisation();
		
		JSONArray forecast = getForcast(cityName);

		int nbrJours = input.getNumJours();

		float[] the_temp = new float[nbrJours];
		int[] humidity = new int[nbrJours];
		float[] wind_speed = new float[nbrJours];

		getInfoFromJSON(the_temp, humidity, wind_speed, forecast, input);
				
		// fill the actual attributs
		this.setThe_temp(the_temp);
		this.setHumidity(humidity);
		this.setWind_speed(wind_speed);
		}catch(IOException e){
			System.out.println("Erreur: Problème de connexion à \"https://query.yahooapis.com\"");
			la.setDispYW(0); // like this the result of this API will not be shown
		}
		
	}
	
	public void getInfoFromJSON(float[] the_temp, int[] humidity, float[] wind_speed, JSONArray forecast, Input input) throws IOException {
		int nbrJours = input.getNumJours();
		String cityName = input.getLocalisation();
		
		for (int i = 0; i < nbrJours; i++)
		{
			the_temp[i] = ((forecast.getJSONObject(i).getFloat("low")-(float)32)/(float)1.8 + (forecast.getJSONObject(i).getFloat("high")-(float)32)/(float)1.8)/2; // by default it's Celcius
			if (input.getM() == 1){

				if (input.getUniteTemp().equals("F")){ // if Fahrenheit 
					the_temp[i] = (forecast.getJSONObject(i).getInt("low") + forecast.getJSONObject(i).getInt("high"))/2; 
				}
			
			}

			humidity[i] = -1; // we don't have the sufficient info to fill them so we give some impossible values(-1)
			wind_speed[i] = -1;

		}
		
		// humidity and wind_speed for the first day
		JSONObject atmosphere = getCh("atmosphere", cityName);
		JSONObject wind = getCh("wind", cityName);
		
		humidity[0] = atmosphere.getInt("humidity"); 
		wind_speed[0] = wind.getFloat("speed");
		
	}
	
	public JSONArray getForcast(String cityName) throws IOException{
		
		String urlLink = "https://query.yahooapis.com/v1/public/yql?q=select%20item%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%22"+cityName+"%22)&format=json";
		
		String s = getStringByUrl(urlLink);

		JSONObject jObj = (JSONObject) new JSONTokener(s).nextValue();

		JSONArray forecast = jObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");
		
		return forecast;
	}
	
	public JSONObject getCh(String ch,String cityName) throws IOException{
		
		String urlLink = "https://query.yahooapis.com/v1/public/yql?q=select%20"+ch+"%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%22"+cityName+"%22)&format=json";
		
		String s = getStringByUrl(urlLink);

		JSONObject jObj = (JSONObject) new JSONTokener(s).nextValue();

		JSONObject ret = jObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject(ch);
		
		return ret;
	}
	
}
