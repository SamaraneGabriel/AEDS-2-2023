class Palindromo{
	
	public static boolean isPalindromo(String str){
		boolean resp = true;
		for(int i = 0, j = str.length()-1; i < str.length()/2; i++,j--)
		{
			if(str.charAt(i) != str.charAt(j)){
				resp = false;
				i = str.length();
			}
						
		}

		return resp;
	}

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
