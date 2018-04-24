package wrapper.api;

import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

import wrapper.*;

public class PMeteo extends Wrapper{

	public PMeteo(Input input, ListApi la) throws IOException {
		super(input, la);
	}

	public void fillWrapper(Input input, ListApi la) throws IOException{

		try{
			String urlLink = "https://www.prevision-meteo.ch/services/json/"+input.getLocalisation();

			String s = getStringByUrl(urlLink);

			JSONObject jObj = (JSONObject) new JSONTokener(s).nextValue();

			int nbrJours = input.getNumJours();

			float[] the_temp = new float[nbrJours];
			int[] humidity = new int[nbrJours];
			float[] wind_speed = new float[nbrJours];

			getInfoFromJSON(the_temp, humidity, wind_speed, jObj, input);

			this.setThe_temp(the_temp);
			this.setHumidity(humidity);
			this.setWind_speed(wind_speed);
		}catch(IOException e){
			System.out.println("Erreur: Problème de connexion à \"www.prevision-meteo.ch\"");
			la.setDispPM(0); // like this the result of this API will not be shown
		}
	}

	public void getInfoFromJSON(float[] the_temp, int[] humidity, float[] wind_speed, JSONObject jObj, Input input) throws IOException {
		int nbrJours = input.getNumJours();

		for (int i = 0; i < nbrJours; i++) {

			JSONObject hourly_data = jObj.getJSONObject("fcst_day_"+i).getJSONObject("hourly_data");

			the_temp[i] = 0;
			humidity[i]  = 0;
			wind_speed[i] = 0;

			for(int h= 0;h <24; h++){		
				the_temp[i] += hourly_data.getJSONObject(h+"H00").getInt("TMP2m");
				humidity[i] += hourly_data.getJSONObject(h+"H00").getInt("RH2m");
				wind_speed[i] += hourly_data.getJSONObject(h+"H00").getInt("WNDSPD10m"); 

			}
			the_temp[i] = the_temp[i]/24; // the average of temp for each day, by default it's in Celcius
			if (input.getM() == 1){ // if the user want to control the temp unit

				if (input.getUniteTemp().equals("F")){ // if  Fahrenheit
					the_temp[i] = (float)32 + (float)1.8 * the_temp[i];
				}

			}

			humidity[i] = humidity[i]/24; 
			wind_speed[i] = wind_speed[i]/24;

		}

	}	
}
