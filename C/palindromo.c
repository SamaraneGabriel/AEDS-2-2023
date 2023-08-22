#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
/*@Author: Gabriel Samarane Ribeiro
 *AEDS II
 */ 



/*Função para checar se um array de chars representando uma String é palindromo
 * @Param: Apontador para cadeia de caraters char* str 
 * @Retorno: Se for palindromo, true, else, false;*/


bool isPalindromo(char* str){
	bool resp = true;
	for(int i = 0, j = strlen(str)-1; i < strlen(str)/2; i++, j--){
		printf("%c, %c",str[i],str[j]);
		if(str[i] != str[j]){
			resp = false;
			i = strlen(str);
		}
	}
	return resp;
}



// metodo principal
//
int main(){
		
	char* str = (char*) malloc(100*sizeof(char));

	char* ptr = NULL;
	
	while(strcmp(str,"FIM") != 0){
		fgets(str,sizeof(str),stdin);
	
		ptr = strchr(str,'\n');
		if(ptr != NULL){
			*ptr = '\0';
		}

		if(strcmp(str,"FIM") != 0){
			if(isPalindromo(str)){
				printf("SIM\n");
			}
			else{
				printf("NAO\n");
			}
		}
	}
	free(str);
	return 0;
}
