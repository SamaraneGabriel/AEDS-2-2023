class CesarRecursivo{
	
	/*Metodo Recursivo para criptografar String segundo cifra de César com chave 3
	 * @param: String s
	 * @return: String aux criptografada
	 */
	
	public static String cifraCesar(String str, int i){

		if(i == str.length()){
			return ""; 
		}
		char c = (char) (str.charAt(i) + 3);
		return c + cifraCesar(str,i+1);
	}
	public static String cifraCesar(String str)
	{
		return cifraCesar(str,0);
	}

	

	//Metodo ja previamente descrito em outros excercicios


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

	//Metodo principal

	public static void main(String args[]){
		

		String str = "";

		while(!strCmpr(str,"FIM")){	
			str = MyIO.readLine();
			if(!strCmpr(str,"FIM")){
				MyIO.print(cifraCesar(str));			
				MyIO.print("\n");	
			
			}	
		
		}

	}

}
