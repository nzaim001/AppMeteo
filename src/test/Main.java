package test;

import java.io.*;

import wrapper.*;

public class Main {

	public static void main(String[] args) throws IOException{
		Input input = new Input();
		input.fillInput(args);

		ListApi la = new ListApi(input);

		la.description(la, input);
	
		
	}

}