package wrapper;

@SuppressWarnings("serial")
public class InputInvalide extends RuntimeException{
	
	public InputInvalide(String s){
		super(s);
	}
	
	public InputInvalide(int i){
		super("Le nombre de jours maximal est 5");
	}
}
