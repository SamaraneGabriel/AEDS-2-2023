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

    /*
     * Procedimento de ordenação parcial utilizando como base o algoritmo de seleção
     * 
     * @params: Lista de jogadores a serem ordenados, int k delemitador de
     * parcialidade
     */

    public static void selecao_parcial(Lista jogadores, int k) throws Exception
    {   
        for(int i = 0; i < k; i++)
        {
            int menor = i;
            for(int j = (i + 1); j < jogadores.size(); j++)
            {
                try {
                    if(jogadores.get(menor).nome.compareTo(jogadores.get(j).nome) > 0 )
                    {
                        menor = j;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            jogadores.swap(menor,i);    
        }
    }


}

public class Questao15 extends Jogador {

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

        selecao_parcial(Jogadores, 10);

        for(int i = 0; i < 10; i++)
        {
            imprimir(Jogadores.get(i));
        }

        sc.close();

    }

}
