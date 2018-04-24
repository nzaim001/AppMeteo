package wrapper;

import wrapper.InputInvalide;


public class Input {

	private int j;
	private int h;
	private int w;
	private int m;
	private int o;
	private int a;

	private String localisation;
	private String fileName;
	private int numJours;
	private String UniteTemp;

	public Input(){
		
		this.j = 0;
		this.h = 0;
		this.w = 0;
		this.m = 0;
		this.o = 0;
		this.a = 0;

		this.localisation = "";
		this.fileName = "";
		this.numJours = 1; // le nombre par default des jours est 1
		this.UniteTemp = "C"; // l'unite de temperature par default est le Celcus "C"

	}

	public void fillInput(String[] arguments) throws InputInvalide{
		this.localisation = arguments[0]; 

		int n = arguments.length;
		int i = 1;

		while(i<n){

			switch (arguments[i])

			{

			case "-j":

				this.setJ(1);
				
				if(Integer.parseInt(arguments[i+1]) > 5) // if the request exceeds 5 days we inform the user that we can't handle this.
					throw new InputInvalide(Integer.parseInt(arguments[i+1]));
					
				this.setNumJours(Integer.parseInt(arguments[i+1]));

				i += 2;

				break;

			case "-m":

				this.setM(1);
				this.setUniteTemp(arguments[i+1]);

				i += 2;

				break;

			case "-o":

				this.setO(1);
				this.setFileName(arguments[i+1]);

				i += 2;

				break;  
				
			case "-a":

				this.setA(1);
				this.setFileName(arguments[i+1]);

				i += 2;

				break;  	

			case "-h":

				this.setH(1);
				
				i += 1;

				break;  	
				
			case "-w":

				this.setW(1);
				
				i += 1;

				break;  	

			default:
				
				throw new InputInvalide("Votre syntaxe d'arguments ( précisement \"" + arguments[i] + "\") n'est pas valide veuillez réesayer avec le bon synthaxe.");
				
			}

		}

	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getO() {
		return o;
	}

	public void setO(int o) {
		this.o = o;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getNumJours() {
		return numJours;
	}

	public void setNumJours(int numJours) {
		this.numJours = numJours;
	}

	public String getUniteTemp() {
		return UniteTemp;
	}

	public void setUniteTemp(String uniteTemp) {
		UniteTemp = uniteTemp;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}



}
