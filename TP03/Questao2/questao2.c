#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

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

// Simulando construtores

void constructor(Jogador *jogador, int id, char *nome, int altura, int peso, char *universidade, int anoNascimento, char *cidadeNascimento, char *estadoNascimento)
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

void emptyConstructor(Jogador *jogador)
{
    constructor(jogador, -1, "", 0, 0, "", 0, "", "");
}

/*Procedimento que imprime todo conteudo de uma Struct*/

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

void imprimir(Jogador *jogador)
{
    removeNewlines(jogador->nome);
    removeNewlines(jogador->universidade);
    removeNewlines(jogador->cidadeNascimento);
    removeNewlines(jogador->estadoNascimento);

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

// LISTA DE JOGADORES

typedef struct Lista
{
    Jogador **jogadores;
    int n;

} Lista;

// FUNCOES INERTES AO TIPO LISTA

void newLista(int size, Lista **lista)
{
    if (size >= 0)
    {
        *lista = (Lista *)malloc(sizeof(Lista));
        (*lista)->jogadores = (Jogador **)malloc(sizeof(Jogador *) * size);
        (*lista)->n = 0;
    }
}

Jogador *getJogador(Lista *lista, int id)
{
    if (id <= lista->n)
    {
        return lista->jogadores[id];
    }
}

void inserirInicio(Jogador *elemento, Lista *lista)

{
    int i;

    // validar insercao
    if (lista->n >= MAXTAM)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    // levar elementos para o fim do array
    for (i = lista->n; i > 0; i--)
    {
        lista->jogadores[i] = lista->jogadores[i - 1];
    }

    lista->jogadores[0] = elemento;
    lista->n++;
}

void inserirFim(Jogador *elemento, Lista *lista)
{

    // validar insercao
    if (lista->n >= MAXTAM)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    lista->jogadores[lista->n] = elemento;
    lista->n++;
}

void inserir(Jogador *elemento, int pos, Lista *lista)
{
    int i;
    int n = lista->n;

    // validar insercao
    if (n >= MAXTAM || pos < 0 || pos > n)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    // levar elementos para o fim do array
    for (i = n; i > pos; i--)
    {
        lista->jogadores[i] = lista->jogadores[i - 1];
    }

    lista->jogadores[pos] = elemento;
    lista->n++;
}

Jogador *removerInicio(Lista *lista)
{
    int i;
    Jogador *resp;

    // validar remocao
    if (lista->n == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    resp = lista->jogadores[0];
    lista->n--;

    for (i = 0; i < lista->n; i++)
    {
        lista->jogadores[i] = lista->jogadores[i + 1];
    }

    return resp;
}

Jogador *removerFim(Lista *lista)
{

    // validar remocao
    if (lista->n == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    return lista->jogadores[--(lista->n)];
}

Jogador *remover(int pos, Lista *lista)
{
    int i;
    Jogador *resp;

    printf("%d", lista->n);

    // validar remocao
    if (lista->n == 0 || pos < 0 || pos >= lista->n)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    resp = lista->jogadores[pos];
    lista->n--;

    for (i = pos; i < lista->n; i++)
    {
        lista->jogadores[i] = lista->jogadores[i + 1];
    }

    return resp;
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
        Jogador *jogador = (Jogador *)malloc(sizeof(Jogador));
        tratamentoLinha(colunas, linhas[i]);
        int id = atoi(colunas[0]);
        int altura = atoi(colunas[2]);
        int peso = atoi(colunas[3]);
        int ano = atoi(colunas[5]);

        constructor(jogador, id, colunas[1], altura, peso, colunas[4], ano, colunas[6], colunas[7]);

        inserirFim(jogador, lista);
    }

    fclose(file);
}

void filtraId(Lista *lista, int *ids, int tamanho_ids, Lista *todos_jogadores)
{
    for (int i = 0; i < todos_jogadores->n - 1; i++)
    {
        for (int j = 0; j < tamanho_ids; j++)
        {
            if (todos_jogadores->jogadores[i]->id == ids[j])
            {
                inserirFim(todos_jogadores->jogadores[i], lista);
                //printf("Numero: %d", j);
                //imprimir(todos_jogadores->jogadores[i]);
            }
        }
    }
}

char **trataString(char *comando)
{
    char **comando_tratado = (char **)malloc(3 * sizeof(char *));
    for (int i = 0; i < 3; i++)
    {
        comando_tratado[i] = NULL;
    }

    if (strncmp(comando, "II", 2) == 0 || strncmp(comando, "IF", 2) == 0 ||
        strncmp(comando, "RI", 2) == 0 || strncmp(comando, "RF", 2) == 0)
    {

        comando_tratado[0] = (char *)malloc(3);
        strncpy(comando_tratado[0], comando, 2);
        comando_tratado[0][2] = '\0';

        if (strlen(comando) > 3)
        {
            comando_tratado[1] = strdup(comando + 3);
        }
        else
        {
            comando_tratado[1] = strdup("");
        }
    }
    else if (strncmp(comando, "I*", 2) == 0 || strncmp(comando, "R*", 2) == 0)
    {
        comando_tratado[0] = (char *)malloc(3);
        strncpy(comando_tratado[0], comando, 2);
        comando_tratado[0][2] = '\0';

        char *restante = comando + 3;
        char *espaco = strchr(restante, ' ');

        if (espaco != NULL)
        {
            *espaco = '\0';
            comando_tratado[1] = strdup(restante);
            comando_tratado[2] = strdup(espaco + 1);
        }
        else
        {
            comando_tratado[1] = strdup(restante);
        }
    }

    //  for (int i = 0; i < 3; i++)
    //  {
    //      printf("%s ", comando_tratado[i]);
    //  }
    //  printf("\n");
    return comando_tratado;
}

void callCommand(char **comando_numero, Lista *jogadores, Lista *todos_jogadores, Lista *jogadores_removidos)
{
    char *comando = comando_numero[0];
    Jogador *jogador;
    // printf("comando: %s    valor: %s",comando,comando_numero[1]);

    if (strcmp(comando, "II") == 0)
    {
        int idInicio = atoi(comando_numero[1]);
        jogador = getJogador(todos_jogadores, idInicio);
        inserirInicio(jogador, jogadores); 
        printf("n = %d", jogadores->n);
        imprimir(jogador);printf("\n");
        
    }

    else if (strcmp(comando, "IF") == 0)
    {
        int idFim = atoi(comando_numero[1]);
        jogador = getJogador(todos_jogadores, idFim);
        inserirFim(jogador, jogadores);
    }

    else if (strcmp(comando, "I*") == 0)
    {
        int posicao = atoi(comando_numero[1]);
        int id = atoi(comando_numero[2]);
        jogador = getJogador(todos_jogadores, id);
        inserir(jogador, posicao, jogadores);
    }

    else if (strcmp(comando, "RI") == 0)
    {
        jogador = removerInicio(jogadores);
        inserirFim(jogador, jogadores_removidos);
    }

    else if (strcmp(comando, "RF") == 0)
    {
        jogador = removerFim(jogadores);
        inserirFim(jogador, jogadores_removidos);
    }

    else if (strcmp(comando, "R*") == 0)
    {
        int posRemover = atoi(comando_numero[1]);
        jogador = remover(posRemover, jogadores);
        inserirFim(jogador, jogadores_removidos);
    }

    // imprimir(jogador);
    // printf("Comando: %s\n", comando);
}

int main()
{

    int *lista_ids = (int *)malloc(sizeof(int) * 100);

    char *ids = (char *)malloc(sizeof(char) * 10);

    scanf("%s", ids);

    int i = 0;

    while (strcmp(ids, "FIM") != 0)
    {
        lista_ids[i] = atoi(ids);
        i++;
        scanf("%s", ids);
        getchar();
    }

    int n = 0;
    scanf("%d", &n);
    Lista *todos_jogadores;
    newLista(4000, &todos_jogadores);

    lerParaStruct("/tmp/players.csv", todos_jogadores);

    for (int k = 0; k < i; k++)
    {
        // imprimir(todos_jogadores->jogadores[k]);
    }

    Lista *jogadores;
    newLista(i, &jogadores);


    filtraId(jogadores, lista_ids, i, todos_jogadores);

    for (int j = 0; j < jogadores->n; j++)
    {
        
        //imprimir(getJogador(jogadores, j));
    }
    //printf("==================================================");

    char **comandos = (char **)malloc(sizeof(char *) * n);

    for (int j = 0; j <= n; j++)
    {
        comandos[j] = (char *)malloc(sizeof(char) * 256);
        if (fgets(comandos[j], 256, stdin) != NULL)
        {
            // Removendo a quebra de linha, se houver
            size_t len = strlen(comandos[j]);
            if (len > 0 && comandos[j][len - 1] == '\n')
            {
                comandos[j][len - 1] = '\0';
            }
            // printf("%s\n", comandos[j]);
        }
    }
    Lista *jogadores_removidos;
    newLista(100, &jogadores_removidos);

    // Loop que chama os comandos

    for (int j = 1; j <= n; j++)
    {
        char **comandos_aux = trataString(comandos[j]);
        callCommand(comandos_aux, jogadores, todos_jogadores, jogadores_removidos);
        //imprimir(jogadores->jogadores[j]);
    }

    for (int j = 0; j < jogadores_removidos->n; j++)
    {
        // imprimir(jogadores_removidos->jogadores[j]);
    }
    return 0;
}