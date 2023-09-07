/*
@author: Gabriel Samarane Ribeiro
@Atividade do TP1 para AEDS2 2023/2
*/
import java.util.Random;
class Aleatorio{

	/*Método para alterar toda ocorrencia de um certo caracter da String com parametros aleatorios
	 *@param: String s para ser alterada
	 *@return: String s com as modificações
	 */

	public static String randomCharOnStr(String str){
		char[] array = str.toCharArray();
		Random gerador = new Random();
		gerador.setSeed(4);
		char a, b;
		a = (char)('a' + Math.abs(gerador.nextInt()) % 26);
		b = (char)('a' + Math.abs(gerador.nextInt()) % 26);
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == a){
			array[i] = b;
			}
		}
		return new String(array);
	
	}

	
  /*Método para checar se duas Strings são idênticas
    @param: String s1, String s2
    @return: Booleano T||F
  */
	public static boolean strCmpr(String str1, String str2){
		boolean resp = true;
		if(str1.length() != str2.length()){
			resp = false;	//condição para evitar erro de indexação devido a tamanhos diferentes de String
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
				MyIO.print(randomCharOnStr(str));
				MyIO.print("\n");	
			
			}	
		
		}

	}
}
