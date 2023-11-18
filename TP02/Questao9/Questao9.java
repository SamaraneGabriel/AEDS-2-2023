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

    public static void imprimir(Jogador objeto) {
        System.out.println("[" + objeto.id + " ## " + objeto.nome + " ## " + objeto.altura + " ## " + objeto.peso
                + " ## " + objeto.anoNascimento
                + " ## " + objeto.universidade + " ## " + objeto.cidadeNascimento + " ## " + objeto.estadoNascimento
                + "]");
    }

    public static boolean deveTrocar(Jogador j1, Jogador j2, int[] mov, int[] comp) throws Exception {
        comp[0]++;
        if (j1.altura > j2.altura) {
            return true;
        }
        comp[0]++; 
        if (j1.altura == j2.altura && j1.nome.compareTo(j2.nome) > 0) {
            return true;
        }
        return false;
    }

    public static void heapify(Lista lista, int n, int i, int[] mov, int[] comp) throws Exception {
        int maior = i;
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        if (esquerda < n && deveTrocar(lista.get(esquerda), lista.get(maior), mov, comp)) {
            maior = esquerda;
        }

        if (direita < n && deveTrocar(lista.get((direita)), lista.get(maior), mov, comp)) {
            maior = direita;
        }

        if (maior != i) {
            lista.swap(i, maior);
            mov[0] += 3;
            heapify(lista, n, maior, mov, comp);
        }
    }

    public static void heapSort(Lista lista, int[] mov, int[] comp) throws Exception {
        int n = lista.size();

        // Construir o heap (reorganizar o array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i, mov, comp);
        }

        // Um por um extrair elemento do heap
        for (int i = n - 1; i > 0; i--) {
            // Mova a raiz atual para o final
            lista.swap(0, i);

            // Chama heapify para a raiz reduzida
            heapify(lista, i, 0, mov, comp);

        }

    }
}

public class Questao9 extends Jogador {

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

        heapSort(Jogadores, contador_mov, contador_comp);

        long fim = System.nanoTime();

        long duracao = fim - inicio;

        double duracaoEmMilissegundos = (double) duracao / 1_000_000;

        // Imprimindo os Jogadores Ordenados

        for (int i = 0; i < Jogadores.size(); i++) {
            imprimir(Jogadores.get(i));
        }

        // Bloco para printar a matricula no arquivo

        String nomeArquivo = "725052_heapsort.txt";

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
