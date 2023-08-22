class PalindromoRecursivo{

	/*Método para comparar a igualdade de duas Strings
	 * Param: String str1, String str2
	 * Retorno: True se STR1 = STR2, else False
       							*/

	public static boolean strCmpr(String str1, String str2){
		boolean resp = true;
		if(str1.length() != str2.length()){
			resp = false;
		}
		else{
			for(int i = 0; i < str1.length(); i++){
				if(str1.charAt(i) != str2.charAt(i)){
					resp = false;
				}
			}
		}
		return resp;
	}


	/*Método recursivo para checar se uma String é palindromo
	 * Param: String str;
	 * Retorno: True se for Palindromo, else False;
	 */

	public static boolean isPalindromo(String str){
		return isPalindromo(str, 0);
	}

	public static boolean isPalindromo(String str, int i){
		boolean resp = true;
		int j = str.length() - i - 1;
		if(i < str.length()/2){
	 		if(str.charAt(i) == str.charAt(j)){
				isPalindromo(str,i + 1);
			}
			else{
				resp = false;
				i = str.length()/2;
			}

		}
		return resp;
	}


	//Metodo principal

	public static void main(String args[]){
		

		String str = "";

		while(!strCmpr(str,"FIM")){	
			str = MyIO.readLine();
			if(!strCmpr(str,"FIM")){
				if(isPalindromo(str)){
					MyIO.print("SIM");
				}
				else{
					MyIO.print("NAO");
				}
				MyIO.print("\n");	
			
			}	
		
		}

	}
}













