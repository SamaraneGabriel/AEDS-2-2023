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
		if(str[i] != str[j]){
			resp = false;
			i = strlen(str);
		}
	}
	return resp;
}



// metodo principal

int main(){
		
	char* str = (char*) malloc(100*sizeof(char));
	
	while(strcmp(str,"FIM") != 0){
		fgets(str,100,stdin);
		size_t len = strlen(str);
		if (len > 0 && str[len-1] == '\n') {
		    str[len-1] = '\0';
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
	return 0;
}
