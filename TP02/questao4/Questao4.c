#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <time.h>

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


/*Procedimento que imprime todo conteudo de uma Struct*/

void imprimir(Jogador* jogador){
    if(jogador == NULL){
        printf("Jogador inválido");
    }
    else{
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
}

//Simulando construtores

void constructor(Jogador* jogador, int id, char* nome, int altura, int peso, char* universidade, int anoNascimento, char* cidadeNascimento, char* estadoNascimento){
    
    if(jogador != NULL){
        jogador->id = id;
        jogador->nome = nome;
        jogador->altura = altura;
        jogador->peso = peso;
        jogador->universidade = universidade;
        jogador->anoNascimento = anoNascimento;
        jogador->cidadeNascimento = cidadeNascimento;
        jogador->estadoNascimento = estadoNascimento;
    }
}

void emptyConstructor(Jogador* jogador){
    constructor(jogador,-1,"",0,0,"",0,"","");
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

void insertionSort(Jogador** jogadores, int n) {
    int j;
    Jogador* key;

    for (int i = 1; i < n; i++) {
        key = jogadores[i];
        j = i - 1;

        while (j >= 0 && strcmp(jogadores[j]->nome, key->nome) > 0) {
            jogadores[j + 1] = jogadores[j];
            j = j - 1;
        }

        jogadores[j + 1] = key;
    }
}



/*Procedimento que instancia Struct com as informações proveninentes de uma linha (representada por um ID) do arquivo
 @params: Apontador para struct, nome do arquivo a ser lido, id do jogador a ser criado
*/

Jogador** lerParaStruct(char* nomeArquivo, int* lista_ids, int tamanho_lista){
    FILE* file = fopen(nomeArquivo,"r");
    if(file == NULL){
        printf("Erro ao abrir arquivo\n");
        exit(0);
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
    
    //Vetor para armazenar todos os jogadores

    Jogador** todos_jogadores = (Jogador**)malloc(sizeof(Jogador*) * contador);

    //Extraindo as colunas 
    
    for(int i = 1; i < contador; i++){
        Jogador* jogador = (Jogador*)malloc(sizeof(Jogador));
        tratamentoLinha(colunas,linhas[i]);
        int id = atoi(colunas[0]);
        int altura = atoi(colunas[2]);
        int peso = atoi(colunas[3]);
        int ano = atoi(colunas[5]);

        constructor(jogador, id, colunas[1], altura, peso, colunas[4], ano, colunas[6], colunas[7]);
    
        todos_jogadores[i-1] = jogador;
    }

    //Vetor para armazenar os ids corretos

    Jogador** jogadores_corretos = (Jogador**)malloc(sizeof(Jogador*) * tamanho_lista);

    //Criando vetor com os ids corretos

    int k = 0;

    for(int i = 0; i < contador-1; i++){
        for(int j = 0; j < tamanho_lista; j++){
            if(todos_jogadores[i]->id == lista_ids[j]){
                jogadores_corretos[k] = todos_jogadores[i];
                k++;
            }
        }
    }

    fclose(file);

    //Ordena os jogadores em ordem alfabetica
    
    insertionSort(jogadores_corretos,k);
    
    return jogadores_corretos;
}


bool pesquisaBinariaNome(char* nome, Jogador** jogadores_corretos, size_t n, int* contador){
    bool resposta = false;
    int dir = n-1, esq = 0;
    while(esq <= dir){
        int meio = (esq + dir)/2;
        int cmp = strcmp(nome,jogadores_corretos[meio]->nome);
        (*contador)++;
        
        if(cmp == 0){
            resposta = true;
            esq = n;
        }
        else if(cmp < 0){ 
            dir = meio - 1;
        }
        else{
            esq = meio + 1;
        }
    }

    return resposta;
}





int main(){


    //SEÇÃO QUE CAPUTRA OS IDS

    Jogador* jogador;

    jogador = (Jogador*)malloc(sizeof(Jogador));

    int* lista_ids = (int*)malloc(sizeof(int) * 100);

    char* ids = (char*)malloc(sizeof(char) * 10); 

    scanf("%s",ids);

    int i = 0;

    while(strcmp(ids,"FIM") != 0){
        lista_ids[i] = atoi(ids);
        i++;
        scanf("%s",ids);
        getchar();
    }


    Jogador** jogadores_para_pesquisar = lerParaStruct("/tmp/players.csv",lista_ids,i);

    //Arquivo para reter resultados de tempo e numero de comp.
    FILE* file = fopen("725052_binaria.txt","w");

    //Contador para o numero de comparacoes
    int contador = 0;
    //Varivaeis para inicio e final da excecucao
    clock_t start, end;
    double tempo;

    //SEÇÃO QUE CAPTURA OS NOMES
    char* nome = (char*)malloc(sizeof(char) * 100);
    fgets(nome, 100, stdin);
    nome[strcspn(nome, "\n")] = '\0';  // Remove o '\n' do final da string

    start = clock();
    while(strcmp("FIM",nome) != 0){
        if(pesquisaBinariaNome(nome,jogadores_para_pesquisar,i,&contador)){
            printf("SIM\n");
        }
        else{
            printf("NAO\n");
        }
        fgets(nome, 100, stdin);
        nome[strcspn(nome, "\n")] = '\0';  // Remove o '\n' do final da string
    }
    end = clock();

    tempo = ((double) (end - start))/CLOCKS_PER_SEC;

    fprintf(file,"725052\t");
    fprintf(file,"%lf\t",tempo);
    fprintf(file,"%d\t",contador);

    fclose(file);
    return 0;
}
