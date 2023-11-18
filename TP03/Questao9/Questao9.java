import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Celula {
    public Celula prox;
    public Celula ant;
    public Celula sup;
    public Celula inf;
    public int elemento;

    public Celula() {
        this.elemento = 0;
        this.prox = null;
        this.ant = null;
        this.sup = null;
        this.inf = null;

    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
        this.ant = null;
        this.sup = null;
        this.inf = null;
    }
}

class Matriz {
    // Atributos
    private Celula inicio;
    private int linhas, colunas;
    // Metodos

    public Matriz(int lin, int col) {
        this.linhas = lin;
        this.colunas = col;
        this.inicio = new Celula(0);
        // Criando a primeira linha
        Celula aux = inicio;
        for (int i = 0; i < col - 1; i++) {
            aux.prox = new Celula(0);
            aux.prox.ant = aux;
            aux = aux.prox;
        }
        // Criando as outras linhas
        aux = inicio;
        for (int i = 1; i < lin; i++) {
            aux.inf = new Celula(0);
            aux.inf.sup = aux;
            aux = aux.inf;
            Celula aux1 = aux;
            for (int j = 1; j < col; j++) {
                aux1.prox = new Celula(0);
                aux1.prox.ant = aux1;
                aux1 = aux1.prox;
                aux1.sup = aux1.ant.sup.prox;
                aux1.sup.inf = aux1;
            }
        }
    }

    public Matriz() {
        this.colunas = 0;
        this.linhas = 0;
        this.inicio = null;
    }

    public Celula getCelula(int lin, int col) {
        Celula ci = this.inicio;
        for(int i = 0; i < lin; i++, ci = ci.inf);
        Celula cj = ci;
        for(int j = 0; j < col; j++, cj = cj.prox);
        return cj;
    }

    public void setElemento(int lin, int col, int elemento) {
        Celula ci = this.inicio;
        for(int i = 0; i < lin; i++, ci = ci.inf);
        Celula cj = ci;
        for(int j = 0; j < col; j++, cj = cj.prox);
        cj.elemento = elemento;
    }

    public void imprimirMatriz() {
        for (Celula i = inicio; i != null; i = i.inf) {
            Celula j;
            for (j = i; j != null; j = j.prox) {
                System.out.print(j.elemento + " ");
            }
            System.out.println();
        }
    }

    public void inserirLinha(int linha, int[] array) {
        Celula aux = inicio;
        // loop que chega até a linha de inserçao
        for (int i = 0; i < linha; i++, aux = aux.inf)
            ;
        for (int i = 0; aux != null; i++) {
            aux.elemento = array[i];
            aux = aux.prox;
        }
    }

    public int[] diagonalPrincipal() {
        int i = 0, k = 0;
        int[] resp = new int[this.linhas];
        for (Celula ci = inicio; ci != null; ci = ci.inf, i++) {
            Celula cj = ci;
            for (int j = 0; cj != null; cj = cj.prox, j++) {
                if (i == j) {
                    resp[k] = cj.elemento;
                    k++;
                }
            }
        }
        return resp;
    }

    public int[] diagonalSecundaria() {
        int i = 0, k = 0;
        int[] resp = new int[this.linhas];
        for (Celula ci = inicio; ci != null; ci = ci.inf, i++) {
            Celula cj = ci;
            for (int j = 0; cj != null; cj = cj.prox, j++) {
                if (i + j == this.linhas - 1) {
                    resp[k] = cj.elemento;
                    k++;
                }
            }
        }
        return resp;
    }

    public static Matriz somaMatriz(Matriz m1, Matriz m2) {
        Matriz resp = new Matriz(m1.linhas, m1.colunas);
        for (Celula ci3 = resp.inicio, ci2 = m2.inicio,
                ci1 = m1.inicio; ci1 != null; ci1 = ci1.inf, ci2 = ci2.inf, ci3 = ci3.inf) {
            for (Celula cj3 = ci3, cj2 = ci2, cj1 = ci1; cj1 != null; cj1 = cj1.prox, cj2 = cj2.prox, cj3 = cj3.prox) {
                cj3.elemento = (cj1.elemento + cj2.elemento);
            }
        }
        return resp;
    }
    
    public static Matriz multiplicaMatriz(Matriz m1, Matriz m2) {
        Matriz m3 = new Matriz(m2.linhas, m1.colunas);
        for(int i = 0; i < m1.colunas; i++) {
            for(int j = 0; j < m2.linhas; j++) {
                int soma  = 0;
                for(int k = 0; k < m1.linhas; k++) {
                    soma += m1.getCelula(i, k).elemento * m2.getCelula(k, j).elemento;
                }
                m3.setElemento(i, j, soma);
            }
        }
        return m3;
    }

}

public class Questao9 extends Matriz {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        int numeroTestes = sc.nextInt();

        for (int i = 0; i < numeroTestes; i++) {
            int linhas1 = sc.nextInt();
            int colunas1 = sc.nextInt();

            Matriz matriz1 = new Matriz(linhas1, colunas1);

            int[][] elementos1 = new int[linhas1][colunas1];

            // Leia cada linha de entrada e armazene na matriz
            for (int p = 0; p < linhas1; p++) {
                for (int j = 0; j < colunas1; j++) {
                    if (sc.hasNextInt()) {
                        elementos1[p][j] = sc.nextInt();
                        // System.out.println(elementos[p][j]);
                    }
                }
            }
            for (int cont = 0; cont < linhas1; cont++) {
                matriz1.inserirLinha(cont, elementos1[cont]);
            }

            int linhas2 = sc.nextInt();
            int colunas2 = sc.nextInt();

            Matriz matriz2 = new Matriz(linhas2, colunas2);

            int[][] elementos2 = new int[linhas2][colunas2];

            // Leia cada linha de entrada e armazene na matriz
            for (int p = 0; p < linhas2; p++) {
                for (int j = 0; j < colunas2; j++) {
                    if (sc.hasNextInt()) {
                        elementos2[p][j] = sc.nextInt();
                        // System.out.println(elementos[p][j]);
                    }
                }
            }
            for (int cont = 0; cont < linhas2; cont++) {
                matriz2.inserirLinha(cont, elementos2[cont]);
            }

            Matriz soma = somaMatriz(matriz1, matriz2);

            int[] diagonal = matriz1.diagonalPrincipal();

            int[] secundaria = matriz1.diagonalSecundaria();

            for(int cont = 0; cont < diagonal.length; cont++) {
                System.out.print(diagonal[cont] + " ");
            }
            System.out.println();
            for(int cont = 0; cont < diagonal.length; cont++) {
                System.out.print(secundaria[cont] + " ");
            }
            System.out.println();
            Matriz multiplicao = multiplicaMatriz(matriz1, matriz2);
            soma.imprimirMatriz();
            multiplicao.imprimirMatriz();
            // matriz2.imprimirMatriz();

        }

    }

}
