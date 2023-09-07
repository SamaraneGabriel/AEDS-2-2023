/*
@author: Gabriel Samarane Ribeiro
@Atividade do TP1 para AEDS2 2023/2
*/
import java.io.*;
import java.net.*;



class Leitura{
	

			//Espaço dedicado para métodos usados indiretamente para a resolução do exercício
		
/*==================================================================================================================================*/

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

 	/*Método que reparte uma String dentro de um dado intervalo [inicio,fim-1]
	 *@param: String str, int inicio, int fim
	 *@return: String aux
	 */

	public static String substring(String str, int inicio, int fim){
		String aux = "";
		for(int i = inicio ; i < fim; i++){
			aux += str.charAt(i);
		}
		return aux;	
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

	/*Método que analisa uma String e um caracter e retorna o indice desse caracter na String caso ele exista
 	 *@param: String str, char procurado
 	 *@return: int contendo a posição do char na String, caso não exista, retorna -1;
 	 */

 	 public static int indexOf(char procurado,String str){
 	 	int indice = -1;
 	 	for(int i = 0; i < str.length(); i++){
 	 		char c = str.charAt(i);
 	 		if(c == procurado){
 	 			indice = i;
 	 			i = str.length();
 	 		}
 	 	}
 	 	return indice;
 	 }


/*==================================================================================================================================*/


			//Espaço dedicado para métodos requisitados diretamente pelo exercício

   /*Método que le toda uma página html e armazena o conteudo em uma String
	*@param: String endereco
 	*@return: String retorno
	*/
	
	   public static String getHtml(String endereco){
	      URL url;
	      InputStream is = null;
	      BufferedReader br;
	      String resp = "", line;
	
	      try {
	         url = new URL(endereco);
	         is = url.openStream();  
	         br = new BufferedReader(new InputStreamReader(is));			//Método disponibilizado pelos professores da disciplina
	
	         while ((line = br.readLine()) != null) {
	            resp += line + "\n";
	         }
	      }
	       catch (MalformedURLException mue) {
	         mue.printStackTrace();
	      }
	       catch (IOException ioe) {
	         ioe.printStackTrace();
	      } 
	
	      try {
	         is.close();
	      } catch (IOException ioe) {
	        
	
	      }
	      return resp;
	   }



	/*Método que itera por String e conta quantas vogais (acentuadas ou não) estão presentes nessa String
 	 *@param: String str
 	 *@return: Array indicando a quantidade de vezes que cada vogal aparece
 	 */ 	 
	public static int[] contarVogais(String str){
    	String vogaisStr = "aeiouáéíóúàèìòùãõâêîôû";	//Definindo todas as vogais
    	int[] vogais = new int[vogaisStr.length()];
		

    	for(int i = 0; i < str.length(); i++){
    		char c = str.charAt(i);
    		int index = indexOf(c,vogaisStr);			//Checando se o caracter pertence ao conjunto das vogais
    		if(index != -1){
    			vogais[index]++;						/*Caso sim, ou seja, caso ele seja encontrado em alguma posição do conjunto, é incrimentando em 1 o valor daquela posição 
    												 	no array de vogais*/
    		}
    	}
    	
		return vogais;
	}
	

		
  	/*Método que itera por String e conta quantas tags <br> estão presentes
	 *@param: String str
	 *@return: int resultado
	 */

	 public static int contarBr(String str){
	 	int resul = 0, i = 0;
	 	String aux = "";
		while(i <= str.length() - 4){					//itera por cada quarteto de chars buscando por <br>, quando acha, incrementa o contador
			aux = substring(str,i,i+4);			
			if(strCmpr(aux,"<br>")){
				resul++;
			}
			i++;
		}
	 	return resul;
	 }

	 /*Método que itera por String e conta quantas tags <table> estão presentes
	 *@param: String str
	 *@return: int resultado
	 */

	 public static int contarTable(String str){
	 	int resul = 0, i = 0;
	 	String aux = "";
		while(i <= str.length() - 7){				//itera por cada hepteto de chars buscando por <table>, quando acha, incrementa o contador
			aux = substring(str,i,i+7);
			if(strCmpr(aux,"<table>")){
				resul++;
			}
			i++;
		}
	 	return resul;
	 }

	/*Método que itera por String e conta quantas consoantes estão presentes
	 *@param: String str
	 *@return: int resultado
	 */

	public static int contarConsoantes(String str) {
	     int resul = 0;
	     String consoantes = "bcdfghjklmnpqrstvwxyz";  // Todas as consoantes em ordem alfabética.
	     
	     for (int i = 0; i < str.length(); i++) {
	         char c = str.charAt(i);
	         if (consoantes.indexOf(c) != -1) {
	             resul++;
	         }
	     }
	    return resul;
	 }
	 
		
				
 

		
	public static void main(String[] args){

	
        char A_ACENTO_AGUDO = (char) 225;
        char E_ACENTO_AGUDO = (char) 233;
        char I_ACENTO_AGUDO = (char) 237;
        char O_ACENTO_AGUDO = (char) 243;
        char U_ACENTO_AGUDO = (char) 250;
        char A_ACENTO_GRAVE = (char) 224;
        char E_ACENTO_GRAVE = (char) 232;
        char I_ACENTO_GRAVE = (char) 236;
        char O_ACENTO_GRAVE = (char) 242;
        char U_ACENTO_GRAVE = (char) 249;
        char A_TIL = (char) 227;
        char O_TIL = (char) 245;
        char A_CIRC = (char) 226;
        char E_CIRC = (char) 234;
        char I_CIRC = (char) 238;
        char O_CIRC = (char) 244;
        char U_CIRC = (char) 251;
					

		String vogaisStr = "aeiouáéíóúàèìòùãõâêîôû";
	
		String url  = "", nome = "";

		MyIO.setCharset("ISO-8859-1");
	
		while(!strCmpr(nome,"FIM")){
			nome = MyIO.readLine();
			if(!strCmpr(nome,"FIM")){

			    url = MyIO.readLine();

				String str = getHtml(url);
				
				int consoantes = contarConsoantes(str);

				int[] vogais = contarVogais(str);

				int br = contarBr(str);

				int table = contarTable(str);


				consoantes = consoantes - ((2*br) + 3*table);

				vogais[0] = vogais[0] - table;							//Elimina a contagem de consoantes e vogais dentro das tags br e table

				vogais[1] = vogais[1] - table;



	System.out.printf("a(%s) ", vogais[0]);
	System.out.printf("e(%s) ", vogais[1]);
	System.out.printf("i(%s) ", vogais[2]);
	System.out.printf("o(%s) ", vogais[3]);
	System.out.printf("u(%s) ", vogais[4]);
	System.out.printf("%s(%s) ", A_ACENTO_AGUDO, vogais[5]);
	System.out.printf("%s(%s) ", E_ACENTO_AGUDO, vogais[6]);
	System.out.printf("%s(%s) ", I_ACENTO_AGUDO, vogais[7]);
	System.out.printf("%s(%s) ", O_ACENTO_AGUDO, vogais[8]);
	System.out.printf("%s(%s) ", U_ACENTO_AGUDO, vogais[9]);
	System.out.printf("%s(%s) ", A_ACENTO_GRAVE, vogais[10]);
	System.out.printf("%s(%s) ", E_ACENTO_GRAVE, vogais[11]);
	System.out.printf("%s(%s) ", I_ACENTO_GRAVE, vogais[12]);
	System.out.printf("%s(%s) ", O_ACENTO_GRAVE, vogais[13]);
	System.out.printf("%s(%s) ", U_ACENTO_GRAVE, vogais[14]);
	System.out.printf("%s(%s) ", A_TIL, vogais[15]);
	System.out.printf("%s(%s) ", O_TIL, vogais[16]);
	System.out.printf("%s(%s) ", A_CIRC, vogais[17]);
	System.out.printf("%s(%s) ", E_CIRC, vogais[18]);
	System.out.printf("%s(%s) ", I_CIRC, vogais[19]);
	System.out.printf("%s(%s) ", O_CIRC, vogais[20]);
	System.out.printf("%s(%s) ", U_CIRC, vogais[21]);
	System.out.printf("consoantes(%s) ", consoantes);
	System.out.printf("br(%s) ", br);
	System.out.printf("table(%s) ", table);
	System.out.printf("%s\n", nome);
	
			}
		}
	}
}
