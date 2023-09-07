#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>


  /*Método para checar se duas Strings são idênticas
   *@param: String s1, String s2
   *@return: Booleano T||F
   */
	bool strCmpr(char* str1, char* str2){
		bool resp = true;
		if(strlen(str1) != strlen(str2)){
			resp = false;														//Condição para evitar erro de indexação devido a tamanhos diferentes de char*
		}
		else{																	//Compara caracter por caracter até encontrar um diferente, de tal forma retorna falso se achado
			for(int i = 0; i < strlen(str1); i++){			
				if(str1[i] != str2[i]){
					resp = false;
					i = strlen(str1);											//Saída lógica do loop
				}
			}
		}
		return resp;
	}


	/*Método para alterar toda ocorrencia de um certo caracter da cadeia de chars (char*) com parametros aleatorios
	 *@param: char* s para ser alterada
	 *@return: char* s com as modificações
	 */


	char* gerador (char* s){

		int size = strlen(s);
		
		char* auxiliar = s;

   	 	char a, b;
    	a = 'a' + abs(rand()) % 26;
    	b = 'a' + abs(rand()) % 26;

		for(int i = 0; i < size; i++) {
        	if(s[i] == a) {
            	auxiliar[i] = b;
        	}
   		}

   		return auxiliar;	
	}




int main(){
		
	char* str = (char*) malloc(100*sizeof(char));

	srand(4);
	
	while(strcmp(str,"FIM") != 0){
		fgets(str,100,stdin);
		size_t len = strlen(str);
		if (len > 0 && str[len-1] == '\n') {			//lógica para garantir que a String é terminada da forma correta para sofrer a manipulação 
		    str[len-1] = '\0';
		}	
		if(strcmp(str,"FIM") != 0){
			printf("%s\n",gerador(str));
		}
	}
	return 0;
}
