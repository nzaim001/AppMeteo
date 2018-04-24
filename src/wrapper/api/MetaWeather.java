package wrapper.api;

import java.io.*;


import org.json.*;

import wrapper.*;

public class MetaWeather extends Wrapper{

	public MetaWeather(Input input, ListApi la) throws IOException {
		super(input, la);
	}

	public void fillWrapper(Input input, ListApi la) throws IOException{

		try{
			int woeid = getWoeid(input.getLocalisation());

			String urlLink = "https://www.metaweather.com/api/location/"+woeid+"/";	

			String s = getStringByUrl(urlLink);

			JSONObject jObj = (JSONObject) new JSONTokener(s).nextValue();

			JSONArray consolidated_weather = jObj.getJSONArray("consolidated_weather");	

			int nbrJours = input.getNumJours();

			float[] the_temp = new float[nbrJours];
			int[] humidity = new int[nbrJours];
			float[] wind_speed = new float[nbrJours];

			getInfoFromJSON(the_temp, humidity, wind_speed, consolidated_weather, input);

			// the actual filling
			this.setThe_temp(the_temp);
			this.setHumidity(humidity);
			this.setWind_speed(wind_speed);

		}catch(IOException e){
			System.out.println("Erreur: Problème de connexion à \"www.metaweather.com\"");
			la.setDispMW(0); // like this the result of this API will not be shown
		}
	}

	public int getWoeid(String cityName) throws IOException{

		String urlLink = "https://www.metaweather.com/api/location/search/?query=" + cityName;	

		String s = getStringByUrl(urlLink);

		s = s.substring(1, s.length()-1);

		JSONObject jObj = new JSONObject(s);

		return jObj.getInt("woeid");
	}

	public void getInfoFromJSON(float[] the_temp, int[] humidity, float[] wind_speed, JSONArray consolidated_weather, Input input) throws IOException {
		int nbrJours = input.getNumJours();

		for (int i = 0; i < nbrJours; i++)
		{
			the_temp[i] = consolidated_weather.getJSONObject(i).getFloat("the_temp"); // by default it's Celcius
			if (input.getM() == 1){ // if the user want to control the temp unit

				if (input.getUniteTemp().equals("F")){ // if  Fahrenheit
					the_temp[i] = (float)32 + (float)1.8 * consolidated_weather.getJSONObject(i).getFloat("the_temp");
				}

			}

			humidity[i] = consolidated_weather.getJSONObject(i).getInt("humidity");
			wind_speed[i] = consolidated_weather.getJSONObject(i).getFloat("wind_speed");

		}

	}

	public void createFile(Input input) throws IOException {
		File file = new File(input.getFileName());

		// create the file if the user want to and do nothing in the other cases
		if (input.getO() != 0){
			try {
				file.createNewFile();
				PrintWriter writer = new PrintWriter("test.txt");

				writer.println("+-------------+-----+-----+-----+-----+");
				writer.println("                f     f                ");
				writer.println("+-------------+-----+-----+-----+-----+");
				writer.println("                                       ");
				writer.println("                                       ");
				writer.println("+-------------+-----+-----+-----+-----+");
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if(input.getA() != 0) {

		}
	}
}





