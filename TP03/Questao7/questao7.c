#include <stdio.h>
#include <string.h>
#include <stdbool.h>
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

/*========================================*/

typedef struct Celula
{
    Jogador jogador;
    struct Celula *prox;

} Celula;

typedef struct FilaCircular
{
    Celula *primeiro, *ultimo;
    int contador;

} FilaCircular;

Celula *newCelula()
{
    Celula *resp = (Celula *)malloc(sizeof(Celula));
    resp->prox = NULL;
    return resp;
}

FilaCircular *newFila()
{
    FilaCircular *fila = (FilaCircular *)malloc(sizeof(FilaCircular));
    fila->primeiro = newCelula();
    fila->ultimo = fila->primeiro;
    fila->contador = 0;
    return fila;
}

bool isFull(FilaCircular *f)
{
    return f->contador == 5;
}
void inserir(Jogador jogador, FilaCircular *f)
{
    if (f->contador == 5)
    {
        printf("FILA CHEIA!!!");
    }
    else
    {
        Celula *temp = newCelula();
        temp->jogador = jogador;
        f->ultimo->prox = temp;
        f->ultimo = temp;

        f->ultimo->prox = f->primeiro;
    }
    f->contador++;
}

Jogador remover(FilaCircular *f)
{
    if (f->contador == 0)
    {
        printf("LISTA VAZIA!!!");
    }
    else
    {
        Celula *removida = f->primeiro->prox;
        f->primeiro->prox = removida->prox;

        if(removida == f->ultimo) {
            f->ultimo = f->primeiro;
        }

        Jogador removido = removida->jogador;
        free(removida);

        f->contador--;

        return removido;
    }
}

int calculaMedia(FilaCircular* fila) {
    double media = 0;
    int cont = 0;
    for(Celula* i = fila->primeiro->prox; i != fila->primeiro; i = i->prox, cont++) {
        media += (i->jogador.altura); 
    }
    media = media/cont;

    if(media - (int)media >= 0.5) {
        media++;
    }

    return (int)media;

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

void filtraId(FilaCircular *fila, int *ids, int tamanho_ids, Lista *todos_jogadores, int *medias)
{
    for (int i = 0; i < tamanho_ids; i++)
    {
        for (int j = 0; j < todos_jogadores->n; j++)
        {
            if (todos_jogadores->jogadores[j].id == ids[i])
            {
                int k = 0;
                Jogador jogador = todos_jogadores->jogadores[j];
                if (isFull(fila))
                {
                    remover(fila);
                    inserir(jogador, fila);
                }
                else
                {
                    inserir(jogador, fila);
                }
                medias[k] = calculaMedia(fila);
                printf("%d\n", medias[k]);
                k++;
                break;
            }
        }
    }
}

char **trataString(char *comando)
{
    char **comando_tratado = (char **)malloc(2 * sizeof(char *));
    for (int i = 0; i < 2; i++)
    {
        comando_tratado[i] = NULL;
    }

    // Tratando comandos do tipo "I <número>"
    if (strncmp(comando, "I ", 2) == 0)
    {
        comando_tratado[0] = (char *)malloc(2);
        strcpy(comando_tratado[0], "I");

        // Separando o número do comando
        comando_tratado[1] = strdup(comando + 2);
    }
    // Tratando comandos do tipo "R"
    else if (strcmp(comando, "R") == 0)
    {
        comando_tratado[0] = strdup("R");
    }

    for (int i = 0; i < 2; i++)
    {
        // printf("%s ",comando_tratado[i]);
    }

    return comando_tratado;
}

int callCommand(char **comando_numero, FilaCircular *jogadores, Lista *todos_jogadores, Lista *jogadores_removidos)
{
    int media = 0;
    char *comando = comando_numero[0];
    Jogador jogador;
    // printf("\ncomando: %s    valor: %s\n", comando, comando_numero[1]);

    if (strcmp(comando, "I") == 0)
    {
        int idInicio = atoi(comando_numero[1]);
        jogador = todos_jogadores->jogadores[idInicio];
        if (isFull(jogadores))
        {
            remover(jogadores);
            inserir(jogador, jogadores);
        }
        else
        {
            inserir(jogador, jogadores);
        }
        media = calculaMedia(jogadores);
        printf("%d\n", media);
    }
    else if (strcmp(comando, "R") == 0)
    {
        Jogador removido = remover(jogadores);
        printf("(R) %s\n", removido.nome);
        inserirFim(jogadores_removidos, &removido);
    }

    return media;
    // imprimir(jogador);
    // printf("Comando: %s\n", comando);
}

void imprimir_verde(FilaCircular *jogadores)
{
    int cont = 0;
    for (Celula *i = jogadores->primeiro->prox; i != jogadores->primeiro; i = i->prox, cont++)
    {
        Jogador *jogador = &(i->jogador);
        removeNewlines(jogador->nome);
        removeNewlines(jogador->universidade);
        removeNewlines(jogador->cidadeNascimento);
        removeNewlines(jogador->estadoNascimento);
        if (jogador != NULL)
        {
            printf("[%d] ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n",
                   cont, jogador->nome, jogador->altura, jogador->peso,
                   jogador->anoNascimento, jogador->universidade,
                   jogador->cidadeNascimento, jogador->estadoNascimento);
        }
    }
}

/*ESTE PROGRAMA UTILIZA DE UMA STRUCT "LISTA" APENAS POR QUESTÕES DE FACILIDADE E DESEMPENHO DE CODIGO, A ESTRUTURA EM FOCO É A
REQUISITADA PELA RESPECTIVA QUESTÃO NO VERDE*/

int main()
{

    int *lista_ids = (int *)malloc(sizeof(int) * 150);

    int *medias = (int *)malloc(sizeof(int) * 400);

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

    FilaCircular *jogadores = newFila();

    filtraId(jogadores, lista_ids, i, todos_jogadores, medias);
    // mostrar(jogadores);

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

    Lista *jogadores_removidos = (Lista *)malloc(sizeof(Lista));

    // // Loop que chama os comandos
    for (int j = 1; j <= n; j++)
    {
        char **comandos_aux = trataString(comandos[j]);
        int media = callCommand(comandos_aux, jogadores, todos_jogadores, jogadores_removidos);
        // printf("LOOP NA ITERAÇÃO: %d\n",j);
        // mostrar(jogadores);
    }

    for (int j = 0; j < jogadores_removidos->n; j++)
    {
        // printf("(R) %s\n", jogadores_removidos->jogadores[j].nome);
    }

    imprimir_verde(jogadores);
    return 0;
}
