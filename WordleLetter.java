public class WordleLetter {
	private char letter;
	private String color;

	//Constructors
	public WordleLetter(char letterIn){
		this.letter = letterIn;
	}

	//Setters
	public void setColor(String colorIn){
		this.color = colorIn;
	}
	
	//getters
	public char getLetter(){
		return this.letter;
	}

	public String getColor(){
		return this.color;
	}

	//Methods
	public boolean isColorSet(){
		return this.color != null;
	}

	public boolean isGreen(){
		return "green".equals(this.color);
	}

	public boolean isYellow(){
		return "yellow".equals(this.color);
	}

	public String toString() {
		/*	Determine the special characters to add at the beginning of the String
			to change the text color to the right color. */
		String colorCode;
		if(color.equals("green")) {
			colorCode = "\u001B[32m";
		} else if(color.equals("yellow")) {
			colorCode = "\u001B[33m";
		} else {
			colorCode = "\u001B[31m";
		}
	
		/*	These are the special character to add 
			to the end of the String 
			to signify the end of the color change. */
		String resetCode = "\u001B[0m";

		/*  Surround the letter with space characters and with 
			the above color changing special characters. */ 
		return String.format(
			"%s %s %s",
			colorCode, letter, resetCode);
	}
}
