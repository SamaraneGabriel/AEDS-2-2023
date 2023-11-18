
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

class Lista { // Lista para o tipo Jogador
    // Atributos
    private Jogador[] array;
    private int primeiro, ultimo;
    private int TAMANHO_MAX;
    private int quantidade;

    // Metodos

    public Lista() {
        this(4000);
    }

    public Lista(int tamanho) {
        TAMANHO_MAX = tamanho;
        array = new Jogador[tamanho + 1];
        primeiro = 0;
        ultimo = 1;
        quantidade = 0;
    }

    public void add(Jogador x) throws Exception { // adiciona final
        if (ultimo == TAMANHO_MAX + 1) {
            throw new Exception("Fila cheia");
        }
        array[ultimo] = x;
        ultimo++;
        quantidade++;
    }

    public Jogador remover() throws Exception {
        if (primeiro + 1 == ultimo) { // remove inicio
            throw new Exception("Fila vazia!");
        }
        Jogador elemento = array[primeiro + 1];
        primeiro++;
        quantidade--;
        return elemento;
    }

    public Jogador get(int index) throws Exception {
        if (index < 0 || index >= TAMANHO_MAX + 1) { // Checa posicao pedida
            throw new Exception("Posicao Inválida");
        }
        return array[index + 1];
    }

    public void set(int index, Jogador jogador) throws Exception {
        if (index < 0 || index >= TAMANHO_MAX + 1) { // Checa posicao pedida
            throw new Exception("Posicao Inválida");
        }
        array[index + 1] = jogador;
    }

    public int size() {
        return quantidade;
    }

    public void swap(int pos1, int pos2) throws Exception {
        pos1++;
        pos2++;
        Jogador temp = this.array[pos2];
        this.array[pos2] = this.array[pos1];
        this.array[pos1] = temp;
    }

}

class Jogador {

    // Atributos
    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    // Construtores

    public Jogador() {

        this.id = 0;
        this.nome = null;
        this.altura = 0;
        this.peso = 0;
        this.universidade = null;
        this.anoNascimento = 0;
        this.cidadeNascimento = null;
        this.estadoNascimento = null;
    }

    public Jogador(int id, String nome, int altura, int peso, String universidade, int anoNascimento,
            String cidadeNascimento, String estadoNascimento) {

        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.universidade = universidade;
        this.anoNascimento = anoNascimento;
        this.cidadeNascimento = cidadeNascimento;
        this.estadoNascimento = estadoNascimento;
    }

    // Métodos

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAltura() {
        return this.altura;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public String getUniversidade() {
        return this.universidade;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoNascimento() {
        return this.anoNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public String getCidadeNascimento() {
        return this.cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public String getEstadoNascimento() {
        return this.estadoNascimento;
    }

    protected Jogador clone() throws CloneNotSupportedException {
        Jogador novo = new Jogador();
        novo.id = this.id;
        novo.nome = this.nome;
        novo.altura = this.altura;
        novo.peso = this.peso;
        novo.universidade = this.universidade;
        novo.anoNascimento = this.anoNascimento;
        novo.cidadeNascimento = this.cidadeNascimento;
        novo.estadoNascimento = this.estadoNascimento;
        return novo;
    }

    /*
     * Método que extrai as colunas da linha, substituindo valores vazios por
     * "nao informado"
     * 
     * @params: String linha
     * 
     * @return: Array de Strings colunas
     */

    public static String[] tratamentoString(String str) {

        String[] colunas = new String[8];

        // Dividir a string usando a vírgula como separador
        String[] partes = str.split(",", -1);

        // Adicionar cada parte ao array, verificando se está vazia
        int index = 0;
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                colunas[index] = parte;
            } else {
                colunas[index] = "nao informado";
            }
            index++;
        }

        return colunas;
    }

    /*
     * Método que itera pelo arquivo players.csv, criando um vetor com todos os
     * objetos requisitado
     * 
     * @params: array list de inteiros contendo os ids requisitados, String nome do
     * arquivo,
     * 
     * @return: array list de jogadores contendo os jogadores referentes aos Ids
     * requisitados
     */

    public static Lista criarVetor(String nomeArquivo, int[] ids, int ids_tamanho) throws Exception {

        // Le todo o arquivo e concatena todo o conteúdo em uma String

        FileReader file = new FileReader(nomeArquivo);
        BufferedReader buffer = new BufferedReader(file);
        String json = "";
        String line = buffer.readLine();
        while (line != null) {
            json = json + line + "\n";
            line = buffer.readLine();
        }

        buffer.close();
        file.close();

        // Converte o conteúdo do arquivo para um array de linhas
        String[] linhas = json.split("\n");

        Lista Jogadores = new Lista(4000);

        Lista Jogadores_corretos = new Lista(ids_tamanho);

        String[] colunas = new String[8];

        // Loop que armazena todos os jogadores em uma Lista

        for (int i = 1; i < linhas.length; i++) {
            colunas = tratamentoString(linhas[i]);

            // Instanciando o objeto
            Jogador objeto = new Jogador(
                    Integer.parseInt(colunas[0]),
                    colunas[1],
                    Integer.parseInt(colunas[2]),
                    Integer.parseInt(colunas[3]),
                    colunas[4],
                    Integer.parseInt(colunas[5]),
                    colunas[6],
                    colunas[7]);

            Jogadores.add(objeto);
        }

        // Loop que seleciona somente os objetos com ids requisitados e os armazena em
        // outra Lista de Jogadores

        for (int i = 0; i < ids_tamanho; i++) {
            int id = ids[i];
            for (int j = 0; j < Jogadores.size(); j++) {
                Jogador jogador = Jogadores.get(j);
                if (jogador.id == id) {
                    Jogadores_corretos.add(jogador);
                    // imprimir(Jogadores.get(j));
                }
            }
        }

        return Jogadores_corretos;

    }

    /* Método void que imprime um Jogador usado apenas para fins de Debug */

    public static void imprimir(Jogador objeto) throws Exception {
        System.out.println("[" + objeto.id + " ## " + objeto.nome + " ## " + objeto.altura + " ## " + objeto.peso
                + " ## " + objeto.anoNascimento
                + " ## " + objeto.universidade + " ## " + objeto.cidadeNascimento + " ## " + objeto.estadoNascimento
                + "]");
    }

    public static void sort(Lista lista, int[] mov, int[] cont) {
        try {
            mergesort(lista, 0, lista.size() - 1, mov, cont);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Algoritmo de ordenacao Mergesort.
     * 
     * @param int esq inicio do array a ser ordenado
     * @param int dir fim do array a ser ordenado
     */
    public static void mergesort(Lista lista, int esq, int dir, int[] mov, int[] cont) throws Exception {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(lista, esq, meio, mov, cont);
            mergesort(lista, meio + 1, dir, mov, cont);
            intercalar(lista, esq, meio, dir, mov, cont);
        }
    }

    /**
     * Algoritmo que intercala os elementos entre as posicoes esq e dir
     * 
     * @param int esq inicio do array a ser ordenado
     * @param int meio posicao do meio do array a ser ordenado
     * @param int dir fim do array a ser ordenado
     */
    public static void intercalar(Lista lista, int esq, int meio, int dir, int[] mov, int[] cont) throws Exception {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        Jogador[] a1 = new Jogador[n1];
        Jogador[] a2 = new Jogador[n2];

        for (int i = 0; i < n1; i++) {
            a1[i] = lista.get(esq + i).clone();
        }

        for (int j = 0; j < n2; j++) {
            a2[j] = lista.get(meio + 1 + j).clone();
        }

        int i = 0, j = 0;
        for (int k = esq; k <= dir; k++) {
            if (i < n1 && (j >= n2 || compararJogadores(a1[i], a2[j], mov, cont) <= 0)) {
                lista.set(k, a1[i]);
                mov[0]++;
                i++;
            } else {
                lista.set(k, a2[j]);
                mov[0]++;
                j++;
            }
        }
    }

    public static int compararJogadores(Jogador j1, Jogador j2, int[] mov, int[] cont) {
        int cmp = j1.getUniversidade().compareTo(j2.getUniversidade());
        cont[0]++;
        if (cmp == 0) {
            cmp = j1.getNome().compareTo(j2.getNome());
            cont[0]++;
        }
        return cmp;
    }

}

public class Questao13 extends Jogador {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        String n = sc.nextLine();

        int[] ids = new int[500];

        int contador = 0;

        Lista Jogadores = new Lista();

        // Bloco que recebe as entradas dos ids a serem adicionados no vetor

        while (!n.equals("FIM")) {
            ids[contador] = (Integer.parseInt(n));
            contador++;
            n = sc.nextLine();
        }

        Jogadores = criarVetor("/tmp/players.csv", ids, contador);

        int[] contador_mov = new int[1];

        int[] contador_comp = new int[1];

        long inicio = System.nanoTime();

        sort(Jogadores, contador_mov, contador_comp);

        long fim = System.nanoTime();

        long duracao = fim - inicio;

        double duracaoEmMilissegundos = (double) duracao / 1_000_000;

        // Imprimindo os Jogadores Ordenados

        for (int i = 0; i < Jogadores.size(); i++) {
            imprimir(Jogadores.get(i));
        }

        // Bloco para printar a matricula no arquivo

        String nomeArquivo = "725052_mergesort.txt";

        FileWriter fileWriter = new FileWriter(nomeArquivo);

        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.print("725052\t");

        printWriter.print(contador_comp[0] + "\t");

        printWriter.print(contador_mov[0] + "\t");

        printWriter.print(duracaoEmMilissegundos);

        printWriter.close();

        sc.close();

    }

}
