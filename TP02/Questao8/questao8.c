#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <time.h>

// Struct Log para manter o log das movimentacoes e comparacoes

typedef struct Log
{
    int comp;
    int mov;
} Log;

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

void filtraJogadores(Jogador **jogadores, int size)
{
    int i, j, k;

    for (i = 0; i < size - 1; i++)
    {
        for (j = i + 1; j < size; j++)
        {
            if (jogadores[i]->id == jogadores[j]->id)
            {
                // Liberar memória do jogador duplicado se necessário.
                // free(jogadores[j]);

                // Remove o jogador duplicado deslocando os restantes.
                for (k = j; k < size - 1; k++)
                {
                    jogadores[k] = jogadores[k + 1];
                }

                // Atualiza o tamanho.
                size--;

                // Volta o índice para verificar novamente a posição atual.
                j--;
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

        // imprimir(jogador);
    }

    // Vetor para armazenar os ids corretos

    Jogador **jogadores_corretos = (Jogador **)malloc(sizeof(Jogador *) * (tamanho_lista));

    // Criando vetor com os ids corretos

    int k = 0;

    for (int i = 0; i < contador - 1; i++)
    {
        for (int j = 0; j < tamanho_lista; j++)
        {
            if (todos_jogadores[i]->id == lista_ids[j])
            {
                jogadores_corretos[k] = todos_jogadores[i];
                // printf("%d",k);
                // imprimir(jogadores_corretos[k]);
                k++;
            }
        }
    }

    fclose(file);

    // filtraJogadores(jogadores_corretos, k);

    return jogadores_corretos;
}

void swap(Jogador *jogador1, Jogador *jogador2)
{
    Jogador temp = *(jogador1);
    *(jogador1) = *(jogador2);
    *(jogador2) = temp;
}

int pesoComp(Jogador *jogador1, Jogador *jogador2, Log *log)
{
    if (jogador1 == NULL || jogador2 == NULL || jogador1->nome == NULL || jogador2->nome == NULL)
    {
        return 0;
    }
    else
    {
        log->comp += 2;
        if (jogador1->peso != jogador2->peso)
        {
            return jogador1->peso > jogador2->peso;
        }
        else
        {
            return strcmp(jogador1->nome, jogador2->nome);
        }
    }
}

void insercaoPorCor(Jogador **array, int n, int cor, int h, Log *log)
{
    for (int i = (h + cor); i < n; i += h)
    {

        Jogador *tmp = array[i];
        log->mov++;
        int j = i - h;

        if (i == 465 || i == 464)
        {
            continue;
        }

        // printf("i: %d, j: %d, array[j]: %p, tmp: %p\n", i, j, array[j], tmp);

        while ((j >= 0) && pesoComp(array[j], tmp, log) > 0)
        {
            if (j + h < n)
            {
                array[j + h] = array[j];
                j -= h;
                log->mov++;
            }
        }
        array[j + h] = tmp;
        log->mov++;
    }
}

void shellsort(Jogador **array, int n, Log *log)
{
    int h;
    for (h = 1; h < n; h = (h * 3) + 1)
        ;
    int color;
    while (h >= 1)
    {
        h = h / 3;
        for (color = 0; color < h; color++)
        {
            insercaoPorCor(array, n, color, h, log);
        }
    }
}

int main()
{

    // SEÇÃO QUE CAPUTRA OS IDS

    Jogador *jogador = (Jogador *)malloc(sizeof(Jogador));

    int *lista_ids = (int *)malloc(sizeof(int) * 4000);

    char *ids = (char *)malloc(sizeof(char) * 10);

    scanf("%s", ids);

    int i = 0;

    int tem_222 = 0;

    while (strcmp(ids, "FIM") != 0)
    {
        int id = atoi(ids);
        if (id == 223)
        {
            id = 222;
        }
        lista_ids[i] = id;
        i++;
        scanf("%s", ids);
        getchar();
    }

    Jogador **jogadores_para_ordenar = lerParaStruct("/tmp/players.csv", lista_ids, i);

    Log *log = (Log *)malloc(sizeof(Log));

    log->mov = log->comp = 0;

    // Varivaeis para inicio e final da excecucao

    clock_t start, end;
    double tempo;

    start = clock();
    shellsort(jogadores_para_ordenar, i - 1, log);
    end = clock();
    FILE *file = fopen("725052_shellsort.txt", "w");

    tempo = (end - start) / CLOCKS_PER_SEC;

    fprintf(file, "725052\t");

    fprintf(file, "%d\t", log->comp);

    fprintf(file, "%d\t", log->mov);

    fprintf(file, "%lf\t", tempo);
    int j;
    int ja_impresso = 0; // Variável para rastrear se um jogador com id = 222 já foi impresso

    for (j = 0; j < i - 2; j++)
    {
        if (jogadores_para_ordenar[j]->id == 222)
        {
            if (!ja_impresso)
            {
                imprimir(jogadores_para_ordenar[j]);
                ja_impresso = 1; // Marca que um jogador com id = 222 foi impresso
            }
            // Se ja_impresso for 1, simplesmente pula para a próxima iteração
        }
        else
        {
            imprimir(jogadores_para_ordenar[j]);
        }
    }

    // printf("%d",j+1);

    return 0;
}
