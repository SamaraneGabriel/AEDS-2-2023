#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

//Struct que representa a classe Jogador

typedef struct Jogador
{
    // Atributos
     int id;
     char* nome;
     int altura;
     int peso;
     char* universidade;
     int anoNascimento;
     char* cidadeNascimento;
     char* estadoNascimento;

}Jogador;

//Simulando construtores

void constructor(Jogador* jogador, int id, char* nome, int altura, int peso, char* universidade, int anoNascimento, char* cidadeNascimento, char* estadoNascimento){
    jogador->id = id;
    jogador->nome = nome;
    jogador->altura = altura;
    jogador->peso = peso;
    jogador->universidade = universidade;
    jogador->anoNascimento = anoNascimento;
    jogador->cidadeNascimento = cidadeNascimento;
    jogador->estadoNascimento = estadoNascimento;
}

void emptyConstructor(Jogador* jogador){
    constructor(jogador,-1,"",0,0,"",0,"","");
}

/*Procedimento que imprime todo conteudo de uma Struct*/

void imprimir(Jogador* jogador){
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
           jogador->id,
           jogador->nome,
           jogador->altura,
           jogador->peso,
           jogador->anoNascimento,
           jogador->universidade,
           jogador->cidadeNascimento,   
           jogador->estadoNascimento);
}

/*Procedimento que extrai as colunas da linha, substituindo valores vazios por "nao informado"
 * @params: Vetor de apontadores que vai receber as Strings separadas, Apontador para cadeia de caracteres contendo a linha
 */

void tratamentoLinha(char** colunas, char* linha){

    colunas[0] = strtok(linha,",");

    for(int i = 1; i < 8; i++){
        char* token = strtok(NULL,",");
        if(token == NULL){
            colunas[i] = "nao informado";
        }
        else{
            colunas[i] = token;
        }
    }
}


/*Procedimento que instancia Struct com as informações prooveninentes de uma linha (representada por um ID) do arquivo
 @params: Apontador para struct, nome do arquivo a ser lido, id do jogador a ser criado
*/

void lerParaStruct(Jogador* jogador, char* nomeArquivo, int id_procurado){
    FILE* file = fopen(nomeArquivo,"r");
    if(file == NULL){
        printf("Erro ao abrir arquivo\n");
        return;
    }
    
    //Apotandor para vetor de strings contendo as linhas
    
    char** linhas = (char**)malloc(sizeof(char*) * 4000);

    //Alocando espaço para cada linha do vetor

    for(int i = 0; i < 4000; i++){
        linhas[i] = (char*)malloc(sizeof(char) * 256);
    }

    //Inicializando cada posicao do vetor com as linhas do arquivo

    int contador = 0;

    while(fscanf(file, " %255[^\n]", linhas[contador]) == 1){
        //printf("Linha lida: %s\n", linhas[contador]);
        contador++;
    }


    //Crinando o vetor das colunas e alocando espaço para cada linha

    char** colunas = (char**)malloc(sizeof(char*) * 8);

    for(int i = 0; i < 8; i++){
        colunas[i] = (char*)malloc(sizeof(char) * 75);
    }
    
    //Extraindo as colunas da linha referente ao id desejado
    tratamentoLinha(colunas,linhas[id_procurado + 1]);

    //Efetivamente associando os resultados a Struct Jogador

    

    int id = atoi(colunas[0]);
    int altura = atoi(colunas[2]);
    int peso = atoi(colunas[3]);
    int ano = atoi(colunas[5]);


    constructor(jogador, id, colunas[1], altura, peso, colunas[4], ano, colunas[6], colunas[7]);


    fclose(file);

}




int main(){

    Jogador* jogador;

    jogador = (Jogador*)malloc(sizeof(Jogador));

    char* id = (char*)malloc(sizeof(char)*10);

    scanf("%s",id);

    char* nomeArquivo = "/tmp/players.csv"; 

    while(strcmp(id,"FIM")){
        lerParaStruct(jogador,nomeArquivo,atoi(id));
        imprimir(jogador);
        scanf("%s",id);
    }

    return 0;
}
