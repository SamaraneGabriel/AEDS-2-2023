#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//funcao que retorna T||F para se a string é um palindromo
bool isPalindromo(char* str){
	bool resp = true;
	for(int i = 0, j = strlen(str)-1; i < strlen(str)/2; i++, j--){
	        printf("%c,%c",str[i],str[j]);
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
		printf("%s",str);
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
