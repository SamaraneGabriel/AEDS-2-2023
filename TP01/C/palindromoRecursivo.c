#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
/*@Author: Gabriel Samarane Ribeiro
 *AEDS II
 */ 

bool isPalindromo(char* str, int i){
	int j = strlen(str) - i - 1;
	if(i < strlen(str)/2){
		if(str[i] != str[j]){
			return false;
		}
		else{
			isPalindromo(str,i+1);
		}
	}
	else{
		return true;
	}
	
}


bool isPalindromoAux(char* str){
	return isPalindromo(str,0); 
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
			if(isPalindromoAux(str)){
				printf("SIM\n");
			}
			else{
				printf("NAO\n");
			}
		}
	}
	return 0;
}
