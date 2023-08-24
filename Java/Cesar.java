/*
@author: Gabriel Samarane Ribeiro
@Atividade do TP1 para AEDS2 2023/2
*/

class Cesar{


	/*Metodo para criptografar String segundo cifra de César com chave 3
	 * @param: String s
	 * @return: String aux criptografada
	 */
	
	public static String cifraCesar(String str){
		char[] aux = str.toCharArray();
		String aux1;
		for(int i = 0; i < str.length(); i++){
			aux[i] = (char) (str.charAt(i) + 3);
		}
		aux1 = new String(aux);
		return aux1;
	}
	

	//metodo ja previamente descrito em outros excercicios


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

	//metodo principal

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
