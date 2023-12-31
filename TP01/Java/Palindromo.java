/*
@author: Gabriel Samarane Ribeiro
@Atividade do TP1 para AEDS2 2023/2
*/

class Palindromo{
	
	/*Método para checar se uma String é palindromo
	  @param: String str
	  @return: Booleano T||F
	*/
	public static boolean isPalindromo(String str){
		boolean resp = true;
		for(int i = 0, j = str.length()-1; i < str.length()/2; i++,j--)		//percorre a String buscando uma diferença, caso ache, quebra logicamente o loop e retorna falso
		{
			if(str.charAt(i) != str.charAt(j)){
				resp = false;
				i = str.length();
			}
						
		}

		return resp;
	}
  /*Método para checar se duas Strings são idênticas
    @param: String s1, String s2
    @return: Booleano T||F
  */
	public static boolean strCmpr(String str1, String str2){
		boolean resp = true;
		if(str1.length() != str2.length()){
			resp = false;		//condição para evitar erro de indexação devido a tamanhos diferentes de String
		}
		else{																	//Compara caracter por caracter até encontrar um diferente, de tal forma retorna falso se achado
			for(int i = 0; i < str1.length(); i++){					
				if(str1.charAt(i) != str2.charAt(i)){
					resp = false;
				}
			}
		}
		return resp;
	}

  //Método principal

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
