/*
@author: Gabriel Samarane Ribeiro
@Atividade do TP1 para AEDS2 2023/2
*/

class Is{


			//Espaço dedicado para métodos usados indiretamente para a resolução do exercício
		
/*==================================================================================================================================*/
	/*Método que itera por uma String e verifica se ela é composta apenas por caracteres ALFABETICOS
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isAlpha(String str){
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(!('a' <= c && c <= 'z')||('A' <= c && c <= 'Z')){
				return false;
			}
		}
		return true;
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


	/*Método que itera pro uma String ALFA e retorna se ela é composta apenas por VOGAIS
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isVogal(String str){
		int contador = 0;
		if(isAlpha(str)){
			for(int i = 0; i < str.length(); i++){
          if(isVogalChar(str.charAt(i))){
            contador++;
          }
			  }
		}
		 return contador == str.length() ? true:false;
	}

	/*Método que itera por String ALFA e retorna se ela é composta apenas por CONSOANTES
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isConsoante(String str){
		int contador = 0;
		if(isAlpha(str)){
			for(int i = 0; i < str.length(); i++){
          if(!isVogalChar(str.charAt(i))){
            contador++;
          }
			  }
		}
		 return contador == str.length() ? true:false;
	}

	/*Méotodo que itera por String NÚMERICA e retorna se ela é compõem um número inteiro
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isInteger(String str){
		boolean resp = true;
		for(int i = 0; i < str.length(); i++){
		  char c = str.charAt(i);
			if(!('0' <= c  && c <= '9')){
				resp = false;
				i = str.length();
			}
		}

		return resp;		
	}
	
	/*Méotodo que itera por String NÚMERICA e retorna se ela é compõem um número real
	 *@param: String str
	 *@return: boolean value
	 */

	public static boolean isReal(String str){
		int contador = 0, ponto = 1;
		for(int i = 0; i < str.length(); i++){
      char c = str.charAt(i);
      if(isNum(c)){
          contador++;
      }
      else if(c == '.' || c == ','){
          ponto++;
      }
    }
    return contador == str.length() && ponto == 1 ? true:false;
  }
  
//   Método main
  public static void main(String[] args) {
  
      String str = "";
    
      while ((!strCmpr(str, "FIM")) && (!strCmpr(str, "fim"))) {
          str = MyIO.readLine();
          str = toLowerString(str);
          if ((!strCmpr(str, "FIM")) && (!strCmpr(str, "fim")))  {
              if (isVogal(str)) {
                  MyIO.print("SIM ");
              } else {
                  MyIO.print("NAO ");
              }
              if (isConsoante(str)) {
                  MyIO.print("SIM ");
              } else {
                  MyIO.print("NAO ");
              }
              if (isInteger(str)) {
                  MyIO.print("SIM ");
              } else {
                  MyIO.print("NAO ");
              }
              if (isReal(str)) {
                  MyIO.print("SIM ");
              } else {
                  MyIO.print("NAO ");
              }
              MyIO.print("\n");
          }
      }
  }
}

