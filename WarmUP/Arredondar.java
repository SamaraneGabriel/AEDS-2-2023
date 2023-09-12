class Arredondar{
	public static void main(String[] args){

	
		String num = "";

		while(!num.equals("EOF")){

		 num = MyIO.readLine();

		if(!num.equals("EOF")){
		
		String cutoff = MyIO.readLine();

		int i = 0;

		int parteInt = 0, resp = 0;

		double parteFrac = 0.0, numero = 0.0, corte = 0.0;

		for(int p = 2; p < cutoff.length(); p++){
			corte += (cutoff.charAt(p) - '0')*(Math.pow(10, 2 - (p + 1))); 
		}


		while(i < num.length() && num.charAt(i) != '.'){
			i++;
		}
		if(i < num.length()){
				for(int j = 0; j < i; j++){
					parteInt += (num.charAt(j) - '0')*(Math.pow(10,i-j-1)); 
				}
				for(int k = i+1; k < num.length(); k++){
					parteFrac += (num.charAt(k) - '0')*(Math.pow(10,i-k)); 
				}
				 

			if(parteFrac > corte){
				resp = parteInt + 1;
			}
			else{
				resp = parteInt;
			}

		}

		else{
			for(int j = 0; j < i; j++){
				numero += (num.charAt(j) - '0')*(Math.pow(10,i-j-1)); 
			}
		}

		MyIO.print(resp + "\n");
		}
	}
} 
}
