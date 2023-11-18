#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

// Struct que representa a classe Jogador

#define MAXTAM 4001

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

void constructor(Jogador *jogador, int id, char *nome, int altura, int peso, char *universidade, int anoNascimento, char *cidadeNascimento, char *estadoNascimento)
{
    jogador->id = id;
    jogador->nome = strdup(nome);
    jogador->altura = altura;
    jogador->peso = peso;
    jogador->universidade = strdup(universidade);
    jogador->anoNascimento = anoNascimento;
    jogador->cidadeNascimento = strdup(cidadeNascimento);
    jogador->estadoNascimento = strdup(estadoNascimento);
}

void destructor(Jogador *jogador)
{
    free(jogador->nome);
    free(jogador->universidade);
    free(jogador->cidadeNascimento);
    free(jogador->estadoNascimento);
}

Jogador copyJogador(const Jogador *original)
{
    Jogador copy;
    copy.id = original->id;
    copy.nome = strdup(original->nome);
    copy.altura = original->altura;
    copy.peso = original->peso;
    copy.universidade = strdup(original->universidade);
    copy.anoNascimento = original->anoNascimento;
    copy.cidadeNascimento = strdup(original->cidadeNascimento);
    copy.estadoNascimento = strdup(original->estadoNascimento);
    return copy;
}

void removeNewlines(char *str)
{
    while (*str)
    {
        if (*str == '\n' || *str == '\r')
        {
            *str = ' '; // Replace newline with space
        }
        str++;
    }
}

void imprimir(Jogador jogador)
{
    removeNewlines(jogador.nome);
    removeNewlines(jogador.universidade);
    removeNewlines(jogador.cidadeNascimento);
    removeNewlines(jogador.estadoNascimento);

    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n",
           jogador.id,
           jogador.nome,
           jogador.altura,
           jogador.peso,
           jogador.anoNascimento,
           jogador.universidade,
           jogador.cidadeNascimento,
           jogador.estadoNascimento);
}

typedef struct Lista
{
    Jogador jogadores[MAXTAM];
    int n;

} Lista;

void inserirFim(Lista *lista, Jogador *jogador)
{
    if (lista->n < MAXTAM)
    {
        lista->jogadores[lista->n] = copyJogador(jogador);
        lista->n++;
    }
    else
    {
        printf("Lista cheia!\n");
    }
}

Jogador getJogador(Lista *lista, int id)
{
    if (id < lista->n)
    {
        return lista->jogadores[id];
    }
}

typedef struct Celula
{
    struct Celula *ant, *prox;
    Jogador jogador;

} Celula;

typedef struct ListaDupla
{
    Celula *primeiro, *ultimo;
    int tamanho;

} ListaDupla;

Celula *newCelula()
{
    Celula *resp = (Celula *)malloc(sizeof(Celula));
    resp->ant = NULL;
    resp->prox = NULL;
    return resp;
}

ListaDupla *newLista()
{
    ListaDupla *resp = (ListaDupla *)malloc(sizeof(ListaDupla));
    resp->primeiro = newCelula();
    resp->ultimo = resp->primeiro;
    resp->tamanho = 0;
    return resp;
}

void inserirFimDupla(Jogador jogador, ListaDupla *l)
{
    Celula *temp = newCelula();
    temp->jogador = jogador;
    l->ultimo->prox = temp;
    temp->ant = l->ultimo;
    l->ultimo = temp;
    l->tamanho++;
}

/*Procedimento que extrai as colunas da linha, substituindo valores vazios por "nao informado"
 * @params: Vetor de apontadores que vai receber as Strings separadas, Apontador para cadeia de caracteres contendo a linha
 */

void tratamentoLinha(char **colunas, char *linha)
{
    colunas[0] = strtok(linha, ",");
    char *prevTokenEnd = colunas[0] + strlen(colunas[0]);

    int i;
    for (i = 1; i < 8; i++)
    {
        if (*(prevTokenEnd + 1) == ',' || *(prevTokenEnd + 1) == '\r')
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

    // Verifica se todas as colunas foram preenchidas
    while (i < 8)
    {
        colunas[i++] = "nao informado";
    }
}

/*Procedimento que instancia Struct com as informações proveninentes de uma linha (representada por um ID) do arquivo
 @params: Apontador para struct, nome do arquivo a ser lido, id do jogador a ser criado
*/

void lerParaStruct(char *nomeArquivo, Lista *lista)
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

    // Extraindo as colunas

    for (int i = 1; i < contador; i++)
    {
        Jogador jogador;
        tratamentoLinha(colunas, linhas[i]);
        int id = atoi(colunas[0]);
        int altura = atoi(colunas[2]);
        int peso = atoi(colunas[3]);
        int ano = atoi(colunas[5]);

        constructor(&jogador, id, colunas[1], altura, peso, colunas[4], ano, colunas[6], colunas[7]);

        inserirFim(lista, &jogador);

        destructor(&jogador);
    }

    fclose(file);
}

void filtraId(ListaDupla *l, int *ids, int tamanho_ids, Lista *todos_jogadores)
{
    for (int i = 0; i < tamanho_ids; i++)
    {
        for (int j = 0; j < todos_jogadores->n; j++)
        {
            if (todos_jogadores->jogadores[j].id == ids[i])
            {
                int k = 0;
                Jogador jogador = todos_jogadores->jogadores[j];
                inserirFimDupla(jogador, l);
                break;
            }
        }
    }
}

int compare(Jogador j1, Jogador j2, int *comp)
{
    (*comp)++;
    if (strcmp(j1.estadoNascimento, j2.estadoNascimento) == 0)
    {
        return strcmp(j1.nome, j2.nome);
    }
    else
    {
        (*comp)++;
        return strcmp(j1.estadoNascimento, j2.estadoNascimento);
    }
}

void swap(Celula *i, Celula *j)
{
    Jogador temp = i->jogador;
    i->jogador = j->jogador;
    j->jogador = temp;
}

void quickSort_f(int esq, int dir, ListaDupla *l, int *comp, int *mov)
{

    int i = esq;
    int j = dir;

    int pivo = (esq + dir) / 2;

    Celula *ci = l->primeiro->prox;
    Celula *cj = l->primeiro->prox;

    // Posiciona os ponteiros ci e cj nos indices esquerda e direita

    for (int cont = 0; cont < esq; cont++, ci = ci->prox)
        ;

    cj = ci;

    for (int cont = esq; cont < dir; cont++, cj = cj->prox)
        ;

    Celula *cp = ci;

    for (int cont = esq; cont < pivo; cont++, cp = cp->prox)
        ;

    Jogador valorPivo = cp->jogador;

    while (i <= j)
    {
        while (compare(ci->jogador, valorPivo, comp) < 0)
        {
            i++;
            ci = ci->prox;
        }

        while (compare(cj->jogador, valorPivo, comp) > 0)
        {
            j--;
            cj = cj->ant;
        }

        if (i <= j)
        {
            swap(ci, cj);
            (*mov) = (*mov) + 3;

            // Bloco para o indice i e ponteiro ci
            i++;
            ci = ci->prox;
            // Bloco para o indice j e o ponteiro cj
            j--;
            cj = cj->ant;
        }
    }

    if (esq < j)
    {
        quickSort_f(esq, j, l, comp, mov);
    }
    if (i < dir)
    {
        quickSort_f(i, dir, l, comp, mov);
    }
}

void quickSort(ListaDupla *l, int *comp, int *mov)
{
    if (l->primeiro == l->ultimo)
    {
        printf("LISTA VAZIA!");
    }
    else
    {
        quickSort_f(0, l->tamanho - 1, l, comp, mov);
    }
}

void printaArquivo(char *fileName, int *comp, int *mov, double tempo)
{
    FILE *file = fopen(fileName, "w");
    
    fprintf(file, "725052\t");

    fprintf(file, "%d\t", *comp);

    fprintf(file, "%d\t", *mov);

    fprintf(file, "%lf\t", tempo);
}

/*ESTE PROGRAMA UTILIZA DE UMA STRUCT "LISTA" APENAS POR QUESTÕES DE FACILIDADE E DESEMPENHO DE CODIGO, A ESTRUTURA EM FOCO É A
REQUISITADA PELA RESPECTIVA QUESTÃO NO VERDE*/

int main()
{

    int *lista_ids = (int *)malloc(sizeof(int) * 500);

    char *ids = (char *)malloc(sizeof(char) * 10);

    scanf("%s", ids);

    int i = 0;

    while (strcmp(ids, "FIM") != 0)
    {
        lista_ids[i] = atoi(ids);
        // printf("%d   ", lista_ids[i]);
        i++;
        scanf("%s", ids);
        getchar();
    }

    int n = 0;
    scanf("%d", &n);
    Lista *todos_jogadores = (Lista *)malloc(sizeof(Lista));

    lerParaStruct("/tmp/players.csv", todos_jogadores);

    ListaDupla *jogadores = newLista();

    filtraId(jogadores, lista_ids, i, todos_jogadores);

    clock_t start, end;
    double tempo;
    int *comp = (int*)malloc(sizeof(int));
    int *mov = (int*)malloc(sizeof(int));

    *comp = 0;
    *mov = 0;

    start = clock();
    quickSort(jogadores, comp, mov);
    end = clock();

    tempo = (end - start) / CLOCKS_PER_SEC;

    printaArquivo("725052_quicksort2.txt", comp, mov, tempo);

    for (Celula *i = jogadores->primeiro->prox; i != NULL; i = i->prox)
    {
        imprimir(i->jogador);
    }

    return 0;
}
