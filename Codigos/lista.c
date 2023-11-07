#include <stdio.h>
#include <stdlib.h>

#define newCelula() (Celula *)malloc(sizeof(Celula))

typedef struct Celula {
    int elemento;
    struct Celula *prox;
} Celula;

typedef struct Lista {
    Celula *primeiro;
    Celula *ultimo;
    int tamanho;
} Lista;

void construtor(Lista *lista) {
    lista->primeiro = newCelula();
    lista->primeiro->prox = NULL;
    lista->ultimo = lista->primeiro;
    lista->tamanho = 0;
}

void imprimir_lista(Lista *lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("LISTA VAZIA\n");
    } else {
        for (Celula *i = lista->primeiro->prox; i != NULL; i = i->prox) {
            printf("[%d] ", i->elemento);
        }
        printf("\n");
    }
}

void imprimir_lista_recursivo_f(Celula *i) {
    if (i != NULL) {
        printf("[%d] ", i->elemento);
        imprimir_lista_recursivo_f(i->prox);
    }
}

void imprimir_lista_recursivo(Lista *lista) {
    if (lista->ultimo == lista->primeiro) {
        printf("LISTA VAZIA");
    } else {
        imprimir_lista_recursivo_f(lista->primeiro->prox);
        printf("\n");
    }
}

void inserirFinal(Lista *lista, int x) {
    Celula *temp = newCelula();
    temp->elemento = x;
    temp->prox = NULL;
    lista->ultimo->prox = temp;
    lista->ultimo = temp;
    lista->tamanho++;
}

void inserirInicio(Lista *lista, int x) {
    Celula *temp = newCelula();
    temp->elemento = x;
    temp->prox = lista->primeiro->prox;
    lista->primeiro->prox = temp;
    if (lista->ultimo == lista->primeiro) {
        lista->ultimo = temp;
    }
    lista->tamanho++;
}

void inserir(Lista *lista, int x, int posicao) {
    if (posicao < 0 || posicao > lista->tamanho) {
        printf("ERRO POSICAO INVALIDA\n");
    } else if (posicao == lista->tamanho) {
        inserirFinal(lista, x);
    } else if (posicao == 0) {
        inserirInicio(lista, x);
    } else {
        Celula *temp = newCelula();
        temp->elemento = x;
        Celula *i = lista->primeiro;
        for (int j = 0; j < posicao - 1; j++, i = i->prox);
        temp->prox = i->prox;
        i->prox = temp;
        lista->tamanho++;
    }
}

int removerInicio(Lista *lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("LISTA VAZIA\n");
        return -1;
    } else {
        Celula *temp = lista->primeiro->prox;
        lista->primeiro->prox = temp->prox;
        int elemento = temp->elemento;
        free(temp);
        if (lista->primeiro->prox == NULL) {
            lista->ultimo = lista->primeiro;
        }
        lista->tamanho--;
        return elemento;
    }
}

int removerFinal(Lista *lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("LISTA VAZIA\n");
        return -1;
    } else {
        Celula *i = lista->primeiro;
        while (i->prox != lista->ultimo) {
            i = i->prox;
        }
        Celula *temp = lista->ultimo;
        lista->ultimo = i;
        lista->ultimo->prox = NULL;
        int elemento = temp->elemento;
        free(temp);
        lista->tamanho--;
        return elemento;
    }
}

int remover(Lista *lista, int posicao) {
    if (posicao < 0 || posicao >= lista->tamanho) {
        printf("ERRO POSICAO INVALIDA\n");
        return -1;
    } else if (posicao == 0) {
        return removerInicio(lista);
    } else if (posicao == lista->tamanho - 1) {
        return removerFinal(lista);
    } else {
        Celula *i = lista->primeiro;
        for (int j = 0; j < posicao - 1; j++, i = i->prox);
        Celula *temp = i->prox;
        i->prox = temp->prox;
        int elemento = temp->elemento;
        free(temp);
        lista->tamanho--;
        return elemento;
    }
}

void selectionSort(Lista *lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("LISTA VAZIA\n");
        return;
    }

    Celula *i, *j, *min;
    for (i = lista->primeiro->prox; i != lista->ultimo; i = i->prox) {
        min = i;
        for (j = i->prox; j != NULL; j = j->prox) {
            if (j->elemento < min->elemento) {
                min = j;
            }
        }
        if (i != min) {
            // Troca os elementos de 'i' e 'min'
            int temp = i->elemento;
            i->elemento = min->elemento;
            min->elemento = temp;
        }
    }
}


void inserirOrdenado(Lista* lista, int x) {
    if(lista->primeiro == lista->ultimo) {
        Celula* i = newCelula();
        i->elemento = x;
        i->prox = NULL;
        lista->ultimo->prox = i;
        i = NULL;
        lista->ultimo = lista->ultimo->prox;
    }
    else if(lista->primeiro->prox->elemento > x) {
        Celula* temp = newCelula();
        temp->elemento = x;
        temp->prox = lista->primeiro->prox;
        lista->primeiro->prox = temp;
        temp = NULL;
    }
    else {
        Celula* temp = newCelula();
        temp->elemento = x;
        Celula* i = lista->primeiro->prox;
        while(i->prox != NULL && (i->elemento < x && i->prox->elemento < x)) {
            i = i->prox;
        }
        temp->prox = i->prox;
        i->prox = temp;
        temp = NULL;
    }
}

int main()
{

    Lista* lista = (Lista*)malloc(sizeof(Lista));

    construtor(lista);

    inserirFinal(lista, 1);
    inserirFinal(lista, 17);
    inserirFinal(lista, 4);
    inserirFinal(lista, 9);

    imprimir_lista(lista);

    selectionSort(lista);

    imprimir_lista(lista);

    return 0;
}
