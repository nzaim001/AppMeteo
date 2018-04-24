package wrapper;

import java.io.IOException;

import wrapper.api.*;

public class ListApi {
	private MetaWeather metaWeather;
	private PMeteo pMeteo;
	private YWeather yWeater;
	private int dispMW = 1; // a variable that takes 1 if the connexion went well for the API(so we can display it) and 0 if not. 
	                        // We assume that all APIs are working(=1) at the beginning but this will change if it's not the case.
	private int dispPM = 1;
	private int dispYW = 1;

	public ListApi(Input input) throws IOException {

		System.out.println("\nConnexion aux APIs en cours ...\n");

		this.metaWeather = new MetaWeather(input, this);
		this.pMeteo = new PMeteo(input, this);
		this.yWeater = new YWeather(input, this);

	}

	public void description(ListApi la, Input input){
		int nbJours = input.getNumJours();

		System.out.println("Le resultat est :");
		System.out.println("");

		drawLigne(nbJours);

		System.out.print("|   "+input.getLocalisation());
		System.out.print("   |  J+0");
		for (int i =1;i<nbJours;i++){
			System.out.print("  |  J+"+i);
		}
		System.out.print("  |\n");	

		drawLigne(nbJours);

		if(la.getDispMW() == 1)
			descriptionMetaWeather(la, input);
		if(la.getDispPM() == 1)
			descriptionPMeteo(la, input);
		if(la.getDispYW() == 1)
			descriptionYMeteo(la, input);
	}

	public void descriptionMetaWeather(ListApi la, Input input) {
		MetaWeather MW = la.getMetaWeather();
		int nbJours = input.getNumJours();

		System.out.print("|  MetaWeather");
		System.out.format("%2s%4d%s", "|", (int)MW.getThe_temp()[0],"°");
		for (int i =1;i<nbJours;i++){
			System.out.format("%3s%4d%s", "|", (int)MW.getThe_temp()[i],"°");

		}
		System.out.print("  |");
		
		
		if(input.getH() != 0){
			System.out.print("\n");
			
			System.out.print("|             ");
			System.out.format("%2s%4d%s", "|", MW.getHumidity()[0],"%");
			for (int i =1;i<nbJours;i++){
				System.out.format("%3s%4d%s", "|", MW.getHumidity()[i],"%");

			}
			System.out.print("  |");
		
		}

		if(input.getW() != 0){	
			System.out.print("\n");
			
			System.out.print("|             ");
			System.out.format("%2s%2d%s", "|", (int)MW.getWind_speed()[0],"Km/h");
			for (int i =1;i<nbJours;i++){
				System.out.format("%2s%2d%s", "|", (int)MW.getWind_speed()[i],"Km/h");

			}
			System.out.print(" |");
		
		}

		System.out.print("\n");

		drawLigne(nbJours);

	}

	public void descriptionPMeteo(ListApi la, Input input) {
		PMeteo PM = la.getpMeteo();
		int nbJours = input.getNumJours();
		
		System.out.print("|  P-Meteo    ");
		System.out.format("%2s%4d%s", "|", (int)PM.getThe_temp()[0],"°");
		for (int i =1;i<nbJours;i++){
			System.out.format("%3s%4d%s", "|", (int)PM.getThe_temp()[i],"°");

		}
		System.out.print("  |");

		if(input.getH() != 0){
			System.out.print("\n");
			
			System.out.print("|             ");
			System.out.format("%2s%4d%s", "|", PM.getHumidity()[0],"%");
			for (int i =1;i<nbJours;i++){
				System.out.format("%3s%4d%s", "|", PM.getHumidity()[i],"%");

			}
			System.out.print("  |");
			
		}

		if(input.getW() != 0){	
			System.out.print("\n");
			
			System.out.print("|             ");
			System.out.format("%2s%2d%s", "|", (int)PM.getWind_speed()[0],"Km/h");
			for (int i =1;i<nbJours;i++){
				System.out.format("%2s%2d%s", "|", (int)PM.getWind_speed()[i],"Km/h");

			}
			System.out.print(" |");
			
		}

		System.out.print("\n");

		drawLigne(nbJours);

	}

	public void descriptionYMeteo(ListApi la, Input input) {
		YWeather YM = la.getyWeater();
		int nbJours = input.getNumJours();

		System.out.print("| Y! Weather  ");
		System.out.format("%2s%4d%s", "|", (int)YM.getThe_temp()[0],"°");
		for (int i =1;i<nbJours;i++){
			System.out.format("%3s%4d%s", "|", (int)YM.getThe_temp()[i],"°");

		}
		System.out.print("  |");

		if(input.getH() != 0){
			System.out.print("\n");
			
			System.out.print("|             ");
			System.out.format("%2s%4d%s", "|", YM.getHumidity()[0],"°");
			for (int i =1;i<nbJours;i++){
				System.out.format("%3s%4s%s", "|", "-"," ");

			}
			System.out.print("  |");
		}

		if(input.getW() != 0){	
			System.out.print("\n");
			
			System.out.print("|             ");
			System.out.format("%2s%2d%s", "|", (int)YM.getWind_speed()[0],"Km/h");
			for (int i =1;i<nbJours;i++){
				System.out.format("%2s%4s%s", "|", "-","  ");

			}
			System.out.print(" |");
			
		}

		System.out.print("\n");

		drawLigne(nbJours);

	}

	public void drawLigne(int nbJours) {
		String ligneLongue = "+--------------+";
		String lignepetite = "-------+";

		System.out.print(ligneLongue);
		for (int i =0;i<nbJours;i++){
			System.out.print(lignepetite);
		}
		System.out.print("\n");

	}

	public MetaWeather getMetaWeather() {
		return metaWeather;
	}

	public void setMetaWeather(MetaWeather metaWeather) {
		this.metaWeather = metaWeather;
	}

	public PMeteo getpMeteo() {
		return pMeteo;
	}

	public void setpMeteo(PMeteo pMeteo) {
		this.pMeteo = pMeteo;
	}

	public YWeather getyWeater() {
		return yWeater;
	}

	public void setyWeater(YWeather yWheater) {
		this.yWeater = yWheater;
	}

	public int getDispMW() {
		return dispMW;
	}

	public void setDispMW(int dispMW) {
		this.dispMW = dispMW;
	}

	public int getDispPM() {
		return dispPM;
	}

	public void setDispPM(int dispPM) {
		this.dispPM = dispPM;
	}

	public int getDispYW() {
		return dispYW;
	}

	public void setDispYW(int dispYW) {
		this.dispYW = dispYW;
	}	



}
