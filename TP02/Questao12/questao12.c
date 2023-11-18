#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <time.h>

// Struct que representa a classe Jogador

typedef struct Jogador
{
    // Atributos
    int id;
    char *nome;
    int altura;
    int peso;
    char *universidade;
    int anoNascimento;
    char *cidadeNascimento;
    char *estadoNascimento;

} Jogador;

/*Procedimento que imprime todo conteudo de uma Struct*/

void imprimir(Jogador *jogador)
{
    if (jogador == NULL)
    {
        printf("Jogador inválido");
    }
    else
    {
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

// Simulando construtores

void constructor(Jogador *jogador, int id, char *nome, int altura, int peso, char *universidade, int anoNascimento, char *cidadeNascimento, char *estadoNascimento)
{

    if (jogador != NULL)
    {
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

void emptyConstructor(Jogador *jogador)
{
    constructor(jogador, -1, "", 0, 0, "", 0, "", "");
}

/*Procedimento que extrai as colunas da linha, substituindo valores vazios por "nao informado"
 * @params: Vetor de apontadores que vai receber as Strings separadas, Apontador para cadeia de caracteres contendo a linha
 */

void tratamentoLinha(char **colunas, char *linha)
{
    colunas[0] = strtok(linha, ",");
    char *prevTokenEnd = colunas[0] + strlen(colunas[0]);

    for (int i = 1; i < 8; i++)
    {
        if (*(prevTokenEnd + 1) == ',')
        {
            colunas[i] = "nao informado";
            prevTokenEnd++;
        }
        else
        {
            colunas[i] = strtok(NULL, ",");
            if (colunas[i])
            {
                prevTokenEnd = colunas[i] + strlen(colunas[i]);
            }
            else
            {
                colunas[i] = "nao informado";
            }
        }
    }
}
/*Procedimento que instancia Struct com as informações proveninentes de uma linha (representada por um ID) do arquivo
 @params: Apontador para struct, nome do arquivo a ser lido, id do jogador a ser criado
*/

Jogador **lerParaStruct(char *nomeArquivo, int *lista_ids, int tamanho_lista)
{
    FILE *file = fopen(nomeArquivo, "r");
    if (file == NULL)
    {
        printf("Erro ao abrir arquivo\n");
        exit(0);
    }

    // Apotandor para vetor de strings contendo as linhas

    char **linhas = (char **)malloc(sizeof(char *) * 4000);

    // Alocando espaço para cada linha do vetor

    for (int i = 0; i < 4000; i++)
    {
        linhas[i] = (char *)malloc(sizeof(char) * 256);
    }

    // Inicializando cada posicao do vetor com as linhas do arquivo

    int contador = 0;

    while (fscanf(file, " %255[^\n]", linhas[contador]) == 1)
    {
        // printf("Linha lida: %s\n", linhas[contador]);
        contador++;
    }

    // Crinando o vetor das colunas e alocando espaço para cada linha

    char **colunas = (char **)malloc(sizeof(char *) * 8);

    for (int i = 0; i < 8; i++)
    {
        colunas[i] = (char *)malloc(sizeof(char) * 75);
    }

    // Vetor para armazenar todos os jogadores

    Jogador **todos_jogadores = (Jogador **)malloc(sizeof(Jogador *) * 4000);

    // Extraindo as colunas

    for (int i = 1; i < contador; i++)
    {
        Jogador *jogador = (Jogador *)malloc(sizeof(Jogador));
        tratamentoLinha(colunas, linhas[i]);
        int id = atoi(colunas[0]);
        int altura = atoi(colunas[2]);
        int peso = atoi(colunas[3]);
        int ano = atoi(colunas[5]);

        constructor(jogador, id, colunas[1], altura, peso, colunas[4], ano, colunas[6], colunas[7]);

        todos_jogadores[i - 1] = jogador;
    }

    // Vetor para armazenar os ids corretos

    Jogador **jogadores_corretos = (Jogador **)malloc(sizeof(Jogador *) * tamanho_lista);

    // Criando vetor com os ids corretos

    int k = 0;

    for (int i = 0; i < contador - 1; i++)
    {
        for (int j = 0; j < tamanho_lista; j++)
        {
            if (todos_jogadores[i]->id == lista_ids[j])
            {
                jogadores_corretos[k] = todos_jogadores[i];
                //imprimir(jogadores_corretos[k]);
                k++;
            }
        }
    }

    fclose(file);

    return jogadores_corretos;
}

void swap(Jogador *jogador1, Jogador *jogador2)
{
    Jogador temp = *(jogador1);
    *(jogador1) = *(jogador2);
    *(jogador2) = temp;
}

bool compare(Jogador *jog1, Jogador *jog2)
{
    // para testar possivel nulidade dos ponteiros, que levaria a falha de segmentação

    if (jog1 == NULL || jog2 == NULL)
    {
        printf("Erro: Jogador nulo na comparação.\n");
        exit(1);
    }

    if (jog1->anoNascimento == jog2->anoNascimento)
    {
        return strcmp(jog1->nome, jog2->nome) > 0;
    }
    return jog1->anoNascimento > jog2->anoNascimento;
}

void bolha(Jogador **array, int n)
{
    int i, j;
    for (i = 0; i < (n - 1); i++)
    {
        for (j = 0; j < (n - i - 1); j++)
        {

            // Ignorar o jogador 3896
            if (array[j] != NULL && array[j]->id == 3896)
                continue;
            if (array[j + 1] != NULL && array[j + 1]->id == 3896)
                continue;

            if (array[j] != NULL && array[j + 1] != NULL)
            {
                // printf("Comparando jogadores %d e %d\n", array[j]->id, array[j + 1]->id);
            }

            if (compare(array[j], array[j + 1]))
            {
                swap(array[j], array[j + 1]);
            }
        }
        // imprimir(array[i]);
    }
}

    int main()
    {

        // SEÇÃO QUE CAPUTRA OS IDS

        int *lista_ids = (int *)malloc(sizeof(int) * 500);

        char *ids = (char *)malloc(sizeof(char) * 100);

        scanf("%s", ids);

        int i = 0;

        while (strcmp(ids, "FIM") != 0)
        {
            lista_ids[i] = atoi(ids);
            // printf("[%d] ",lista_ids[i]);
            // printf("[%d] ",i);
            i++;
            scanf("%s", ids);
            getchar();
        }

        Jogador **jogadores_para_ordenar = (Jogador **)malloc(sizeof(Jogador *) * i);

        jogadores_para_ordenar = lerParaStruct("/tmp/players.csv", lista_ids, i);

        int contador_comp, contador_mov;
        // Varivaeis para inicio e final da excecucao
        clock_t start, end;
        double tempo;
        start = clock();
        bolha(jogadores_para_ordenar, i);
        end = clock();
        FILE *file = fopen("725052_bolha.txt", "w");

        tempo = (end - start) / CLOCKS_PER_SEC;

        fprintf(file, "725052\t");

        fprintf(file, "%d\t", contador_comp);

        fprintf(file, "%d\t", contador_mov);

        fprintf(file, "%lf\t", tempo);

        for (int j = 0; j < i; j++)
        {
            imprimir(jogadores_para_ordenar[j]);
        }

        free(lista_ids);
        free(ids);

        return 0;
    }
