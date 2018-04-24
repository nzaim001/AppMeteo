package wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.Date;


public abstract class Wrapper {
	private float[] the_temp;
	private int[] humidity;
	private float[] wind_speed;
	
	public Wrapper(Input input, ListApi la) throws IOException{
		int nbrJours = input.getNumJours();
		
		this.the_temp = new float[nbrJours];
		this.humidity = new int[nbrJours];
		this.wind_speed = new float[nbrJours];
		
		this.fillWrapper(input,la);
		
	}
	
	public abstract void fillWrapper(Input input, ListApi la) throws IOException; //this method fills our lists (the_temp, humidity, wind_speed) and determines the results we can show.
	
	public String getStringByUrl(String urlLink) throws IOException{
		
		// building the url
		URL url = new URL(urlLink);
		
		// connecting with GET method
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		
		// saving the request in "requetes.log"
		fillRequetes(urlLink, connection);
		
		// filling the StringBuffer
		BufferedReader in  = new BufferedReader( new InputStreamReader(connection.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while((line  = in.readLine()) != null){
			sb.append(line);			
		}			        		       
		in.close();

		String s = sb.toString();
		
		return s;
	}
	
	protected void fillRequetes(String urlLink, HttpURLConnection connection) throws IOException, FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("requetes.log", true)));			
		int codeR = connection.getResponseCode();
		Date date = new Date();	   
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
		String state;
		if(codeR == 200) state = "OK";
		else state = "ERROR";
		
		writer.println(sdf.format(date) + " - " + "[ "+codeR+" "+state+" ] ");
		writer.println(urlLink);
		writer.close();
	}
	
	//FIXME put getInfoFromJSON() as an abstract method (an idea could be up-casting JSONArray to JSONObject and use the same format
	
	public float[] getThe_temp() {
		return the_temp;
	}
	public void setThe_temp(float[] the_temp) {
		this.the_temp = the_temp;
	}
	public int[] getHumidity() {
		return humidity;
	}
	public void setHumidity(int[] humidity) {
		this.humidity = humidity;
	}
	public float[] getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(float[] wind_speed) {
		this.wind_speed = wind_speed;
	}
	
	
}
