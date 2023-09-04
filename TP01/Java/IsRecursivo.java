/*		
@author: Gabriel Samarane Ribeiro
@Atividade do TP1 para AEDS2 2023/2
*/

class IsRecursivo{


			//Espaço dedicado para métodos usados indiretamente para a resolução do exercício
		
/*==================================================================================================================================*/

	//Apenas chamada para passar o contador para o metodo recursivo

	public static boolean isAlpha(String str){
		return isAlpha(str, 0);	
	}

	/*Método rercursivo que percorre uma String e verifica se ela é composta apenas por caracteres ALFABETICOS
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isAlpha(String str, int i){
		if(i < str.length()){
			char c = str.charAt(i);
			if(('a' <= c && c <= 'z')||('A' <= c && c <= 'Z')){
				return isAlpha(str, i+1);
			}
			else{
				return false;
			}
		}
		else{
			return true;
		}
	}

	/*Método que verifica se um caracter é maiusculo, caso seja, o transforma em minusculo
	 *@param: char c
	 *@return: char cLower
	 */

	public static char toLower(char c){
			if('A' <= c && c <= 'Z'){
				c =  (char)(c + (32));
			}
		return c;
	}
	
	/*Método que transforma todos os caracteres maisculos de uma String em minusculos
	 *@param: String str
	 *@return: String str
	 */
	 
	public static String toLowerString(String str){
	  String aux = "";
	  for(int i = 0; i < str.length(); i++){
	      aux += toLower(str.charAt(i));
	   }
	   return aux;
	}

  /*Método que verifica se um caracter é numérico
	 *@param: char c
	 *@return: boolean value
	 */
  public static boolean isNum(char c){
   return ('0' <= c && c <= '9') ? true:false;
  }  
  
 /* Método que verifica a identicidade de duas Strings
  *@param: String str1, String str2
  *@return: boolean value
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
 /* Método que checa se um caracter é vogal
  *@param: char c
  *@return: boolean value
  */

	public static boolean isVogalChar(char c){
			  switch(toLower(c)) {
      		  	case 'a':
              case 'e':
         			case 'i':
              case 'o':
        	 	 	case 'u':
    					return true;
     			 	default:
     			 	return false;
    	}
  }


/*==================================================================================================================================*/

			//Espaço dedicado para métodos requisitados diretamente pelo exercício

	//Método auxliar para passar o contador 	

	public static boolean isVogal(String str){
		return isAlpha(str) ? isVogal(str,0):false;
	}

	/*Método recursivo que percorre uma String ALFA e retorna se ela é composta apenas por VOGAIS
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isVogal(String str, int i){
		if(i < str.length()){
			char c = str.charAt(i);
			if(isVogalChar(c)){
				return isVogal(str,i+1);	
			}
			else{
				return false;
			}
		}
		else{
			return true;
		}
	}

	//Método auxliar para passar o contador 	

	public static boolean isConsoante(String str){
		return isConsoante(str) ? isVogal(str,0):false;
	}

	/*Método recursivo que percorre uma String ALFA e retorna se ela é composta apenas por VOGAIS
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isConsoante(String str, int i){
		if(i < str.length()){
			char c = str.charAt(i);
			if(!isVogalChar(c)){
				return isConsoante(str,i+1);	
			}
			else{
			return false;
			}
		}
		else{
			return true;
		}
	}

	





//   Método main
  public static void main(String[] args) {
		
		while(true){
			String str = MyIO.readLine();
			if(isVogal(toLowerString(str))){
					MyIO.print("SUCESSO");
			}
		}

	}	
}
