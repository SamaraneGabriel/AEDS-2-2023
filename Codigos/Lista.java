class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class Lista {
    private Celula primeiro;
    private Celula ultimo;
    private int tamanho;

    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
        tamanho = 0;
    }

    public boolean listaVazia() {
        return primeiro == ultimo;
    }

    public void inserirFinal(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
        tamanho++;
    }

    public void inserirInicio(int x) {
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tamanho++;
    }

    public void inserir(int x, int posicao) {
        if (posicao < 0 || posicao > tamanho) {
            throw new IllegalArgumentException("Posição inválida");
        } else if (posicao == tamanho) {
            inserirFinal(x);
        } else if (posicao == 0) {
            inserirInicio(x);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < posicao - 1; j++) {
                i = i.prox;
            }
            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
            tamanho++;
        }
    }

    public int removerInicio() {
        if (listaVazia()) {
            throw new IllegalStateException("Lista vazia");
        }
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        if (primeiro.prox == null) {
            ultimo = primeiro;
        }
        tamanho--;
        return tmp.elemento;
    }

    public int removerFinal() {
        if (listaVazia()) {
            throw new IllegalStateException("Lista vazia");
        }
        Celula i = primeiro;
        for (; i.prox != ultimo; i = i.prox);
        int elemento = ultimo.elemento;
        ultimo = i;
        ultimo.prox = null;
        tamanho--;
        return elemento;
    }

    public int remover(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IllegalArgumentException("Posição inválida");
        } else if (posicao == 0) {
            return removerInicio();
        } else if (posicao == tamanho - 1) {
            return removerFinal();
        } else {
            Celula i = primeiro;
            for (int j = 0; j < posicao - 1; j++) {
                i = i.prox;
            }
            Celula tmp = i.prox;
            i.prox = tmp.prox;
            tamanho--;
            return tmp.elemento;
        }
    }

    public void imprimirLista() {
        if (listaVazia()) {
            System.out.println("LISTA VAZIA");
        } else {
            Celula i = primeiro.prox;
            while (i != null) {
                System.out.print("[" + i.elemento + "] ");
                i = i.prox;
            }
            System.out.println();
        }
    }

    public void selectionSort() {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            Celula min = i;
            for (Celula j = i.prox; j != null; j = j.prox) {
                if (j.elemento < min.elemento) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = i.elemento;
                i.elemento = min.elemento;
                min.elemento = temp;
            }
        }
    }

    // As outras funções (inversão, quickSort, etc.) devem ser implementadas de maneira similar.
}

class Codigo {
    public static void main(String[] args) {
        Lista lista = new Lista();
        lista.inserirFinal(3);
        lista.inserirFinal(1);
        lista.inserirFinal(4);
        lista.inserirFinal(1);
        lista.inserirFinal(5);

        System.out.println("Lista antes da ordenação:");
        lista.imprimirLista();

        lista.selectionSort();

        System.out.println("Lista após a ordenação:");
        lista.imprimirLista();

        System.out.println("Elemento removido do início: " + lista.removerInicio());
        System.out.println("Elemento removido do final: " + lista.removerFinal());
        System.out.println("Elemento removido da posição 1: " + lista.remover(1));

        System.out.println("Lista após remoções:");
        lista.imprimirLista();
    }
}
