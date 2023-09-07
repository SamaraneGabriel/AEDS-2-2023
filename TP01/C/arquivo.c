#include <stdio.h>
#include <stdlib.h>
#include <string.h>


			//Espaço dedicado para métodos usados indiretamente para a resolução do exercício
			
	/*==================================================================================================================================*/
	
		/*Procedimento que altera o tamanho do arquivo para 0, efetivamente limpando ele de qualquer conteúdo;
		 *@param: Apontador para cadeia de caracteres -> nome do arquivo
		 */
		void clearFile(const char *fileName){
			FILE *file = fopen(fileName,"w");
			fclose(file);
		}

		/*Método que trata um numero double para remover todos os zero após o ultimo digito significativo ou após o ponto 
		 *@param: double num ->numero para ser tratado
		 */


		void printFormattedDouble(double num) {
		    char buffer[100]; 
		    sprintf(buffer, "%.10f", num);
		
		    int len = strlen(buffer); 
		    while(len > 0 && (buffer[len-1] == '0' || buffer[len-1] == '.')) { 
		        if (buffer[len-1] == '.') { 
		            buffer[len-1] = '\0';
		            break;
		        }
		        buffer[len-1] = '\0'; 
		        len--; 
		    }
		
		    printf("%s\n", buffer); 
		}
		
			

	/*==================================================================================================================================*/


				//Espaço dedicado para métodos requisitados diretamente pelo exercício

			/*Procedimento que imprime em arquivo texto um double 
			 *@param: Apontador para cadeia de caracteres fileName -> nome do arquivo a ser gravado, double num -> numero a ser gravado no arquivo
			 */

			void printDoubleToFile(const char* fileName, double num){
				FILE* file = fopen(fileName,"ab"); 			
				fwrite(&num, sizeof(double),1,file);
				fclose(file);
			} 

			/*Procedimento que imprime na tela o conteúdo de um arquivo contendo doubles
			 *@param: Apontador para cadeia de caracteres fileName -> nome do arquivo, int quantidade de doubles
			 */

			void printDoubleFromFile(const char* fileName, int quantidade){
				double real = 0.0;
				long posicaoFinal = 8*quantidade;
				FILE *file = fopen(fileName,"r");
				for(int i = 0; i < quantidade; i++){
					fseek(file,posicaoFinal - (i+1)*8,SEEK_SET);		//Desloca o ponteiro para o byte de inicio da sequencia de 8 que formam o double
					fread(&real,sizeof(double),1,file);
					printFormattedDouble(real);
				}
				fclose(file);
			}			 
			
			
			//Função principal

			 int main(){

				clearFile("ArquivoTP.txt");

				int n = 0;

				double num = 0.0;

				scanf("%d",&n);

				for(int i = 0; i < n; i++){
					scanf("%lf",&num);		
					printDoubleToFile("ArquivoTP.txt",num);
				}

				printDoubleFromFile("ArquivoTP.txt",n);

			 	return 0;
			 }
