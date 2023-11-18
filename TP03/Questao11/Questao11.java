import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Resultado {
    public Lista jogadoresAtualizados;
    public Jogador jogadoresRemovidos;

    public Resultado() {
        this.jogadoresAtualizados = new Lista();
        this.jogadoresRemovidos = new Jogador();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Jogadores Atualizados:\n");
        sb.append(jogadoresAtualizados.toString()); // Supondo que a classe Lista também tenha um método toString().
        sb.append("\nJogadores Removidos:\n");
        sb.append(jogadoresRemovidos.toString());

        return sb.toString();
    }

}

class Celula {
    public Celula prox;
    public Celula ant;
    public Jogador jogador;

    public Celula() {
        this.jogador = null;
        this.prox = null;
        this.ant = null;
    }

    public Celula(Jogador jogador) {
        this.jogador = jogador;
        this.prox = null;
        this.ant = null;
    }
}

class Lista extends Jogador { // Lista para o tipo Jogador
    // Atributos
    private Celula primeiro;
    private Celula ultimo;
    private int contador;

    // Metodos

    public Lista() {
        this.primeiro = new Celula();
        ultimo = primeiro;
        primeiro.ant = null;
    }

    public int size() {
        return this.contador;
    }

    public void inserirFim(Jogador jogador) {
        Celula temp = new Celula(jogador);
        temp.ant = ultimo;
        ultimo.prox = temp;
        temp.prox = null;
        ultimo = temp;
        this.contador++;
    }

    public Jogador get(int pos) throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Lista vazia");
        } else if (pos >= size() || pos < 0) {
            throw new Exception("POSICAO INVALIDA");
        } else {
            Celula temp = primeiro.prox;
            for (int i = 0; i < pos; i++, temp = temp.prox)
                ;
            return temp.jogador;
        }
    }

    public void swap(Celula c1, Celula c2) {
        Jogador temp = c1.jogador;
        c1.jogador = c2.jogador;
        c2.jogador = temp;
    }

    public static int compare(Jogador j1, Jogador j2) {
        if(j1.getEstadoNascimento().compareTo(j2.getEstadoNascimento()) == 0) {
            return j1.getNome().compareTo(j2.getNome()); 
        }
        else {
            return j1.getEstadoNascimento().compareTo(j2.getEstadoNascimento());
        }
    }

    public void quicksort(int esq, int dir) throws Exception {
        int i = esq, j = dir, c1 = 0, c2 = this.size() - 1, c3 = esq;
        Celula ci, cj, cp;
        int pivoP = (esq + dir) / 2;
        // Caminha com a celula ci
        for (ci = this.primeiro.prox; c1 < esq; c1++, ci = ci.prox)
            ;
        // Caminha com a celula cj
        for (cj = this.ultimo; c2 > dir; c2--, cj = cj.ant)
            ;
        // Caminha com o piv
        for (cp = ci; c3 < pivoP; c3++, cp = cp.prox)
            ;
        Jogador pivo = cp.jogador;

        while (i <= j) {
            while (compare(this.get(i), pivo) < 0) {
                i++;
                ci = ci.prox;
            }
            while (compare(this.get(j), pivo) > 0) {
                j--;
                cj = cj.ant;
            }
            // System.out.println("PRE TROCA");
            // System.out.print("lista[i] = ");
            // imprimir(this.get(i));
            // System.out.print("lista[j] = ");
            // imprimir(this.get(j));

            if (i <= j) {

                swap(ci, cj);

                // System.out.println("POS TROCA");
                // System.out.print("lista[i] = ");
                // imprimir(this.get(i));
                // System.out.print("lista[j] = ");
                // imprimir(this.get(j));
                i++;
                ci = ci.prox;

                j--;
                cj = cj.ant;
            }
        }
        if (esq < j)
            quicksort(esq, j);
        if (i < dir)
            quicksort(i, dir);
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
        List<String> colunas = new ArrayList<>();
        Matcher matcher = Pattern.compile("(\"[^\"]*\"|[^,]*),?").matcher(str);

        while (matcher.find()) {
            String coluna = matcher.group(1);
            if (coluna != null && !coluna.isEmpty()) {
                // Remove as aspas se estiver entre aspas
                coluna = coluna.startsWith("\"") && coluna.endsWith("\"") ? coluna.substring(1, coluna.length() - 1)
                        : coluna;
            } else {
                coluna = "nao informado";
            }
            colunas.add(coluna);
        }

        // Garante que temos exatamente 8 colunas
        while (colunas.size() < 8) {
            colunas.add("nao informado");
        }

        return colunas.toArray(new String[0]);
    }

    /*
     * Método que itera pelo arquivo players.csv, criando um vetor com todos os
     * objetos requisitado
     * 
     * @param array list de inteiros contendo os ids requisitados, String nome do
     * arquivo,
     * 
     * @return: array list de jogadores contendo os jogadores referentes aos Ids
     * requisitados
     */

    public static Lista criarVetor(String nomeArquivo) throws Exception {

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

        Lista Jogadores = new Lista();

        String[] colunas = new String[8];

        // Loop que armazena todos os jogadores em uma Lista

        for (int i = 1; i < linhas.length; i++) {
            // System.out.println(linhas[i]);

            colunas = tratamentoString(linhas[i]);

            int id = Integer.parseInt(colunas[0]);
            if (id == 223) {
                Jogadores.inserirFim(null);
                continue; // Ignora a entrada se o id for 223
            }
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

            Jogadores.inserirFim(objeto);
            // System.out.println("JOGADOR SENDO PRINTADO: " + objeto.id);
            // imprimir(Jogadores.get(i - 1));
        }

        return Jogadores;

    }
    /*
     * Método que filtra os jogadores por ID, retornando uma Lista de jogadores
     * corretos
     */

    public static Lista filtraId(ArrayList<Integer> ids, Lista Jogadores) throws Exception {
        Lista Jogadores_corretos = new Lista();

        Jogador[] array = new Jogador[4000];

        for (int i = 0; i < Jogadores.size(); i++) {
            array[i] = Jogadores.get(i);
        }

        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            for (int j = 0; j < Jogadores.size(); j++) {
                if (array[j] != null) {
                    Jogador jogador = array[j];
                    if (jogador.id == id) {
                        Jogadores_corretos.inserirFim(jogador);
                        // imprimir(jogador);
                    }
                }
            }
        }
        return Jogadores_corretos;
    }

    /* Método void que imprime um Jogador usado apenas para fins de Debug */

    public static void imprimir(Jogador objeto) {
        if (objeto != null) {
            System.out.println("[" + objeto.id + " ## " + objeto.nome + " ## " + objeto.altura + " ## " + objeto.peso
                    + " ## " + objeto.anoNascimento
                    + " ## " + objeto.universidade + " ## " + objeto.cidadeNascimento + " ## " + objeto.estadoNascimento
                    + "]");
        } else {
            System.out.println("NULL PEBA");
        }
    }

    public static void imprimir_verde(Lista jogadores) throws Exception {
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);
            System.out.println("[" + i + "] ## " + jogador.nome + " ## " + jogador.altura + " ## " + jogador.peso
                    + " ## " + jogador.anoNascimento
                    + " ## " + jogador.universidade + " ## " + jogador.cidadeNascimento + " ## "
                    + jogador.estadoNascimento + " ##");

        }
    }

}

public class Questao11 extends Jogador {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        String numero = sc.nextLine();

        ArrayList<Integer> ids = new ArrayList<>();

        Lista todos_Jogadores = new Lista();

        Lista Jogadores = new Lista();

        // Bloco que recebe as entradas dos ids a serem adicionados no vetor

        while (!numero.equals("FIM")) {
            int inteiro = Integer.parseInt(numero);
            ids.add(inteiro);
            numero = sc.nextLine();
        }

        int contador1 = 0;

        todos_Jogadores = criarVetor("/tmp/players.csv");

        for (int i = 0; i < todos_Jogadores.size(); i++) {
            // imprimir(todos_Jogadores.get(i));
        }

        Jogadores = filtraId(ids, todos_Jogadores);

        for (int i = 0; i < Jogadores.size(); i++) {
            // System.out.println(i);
            //imprimir(Jogadores.get(i));
        }

        Jogadores.quicksort(0, Jogadores.size() - 1);

        for (int i = 0; i < Jogadores.size(); i++) {
             imprimir(Jogadores.get(i));
        }

    }

    /*
     * int[] contador_mov = new int[1];
     * 
     * int[] contador_comp = new int[1];
     * 
     * long inicio = System.nanoTime();
     * 
     * countSort(Jogadores, contador_mov, contador_comp);
     * 
     * long fim = System.nanoTime();
     * 
     * long duracao = fim - inicio;
     * 
     * double duracaoEmMilissegundos = (double) duracao / 1_000_000;
     * 
     * // Imprimindo os Jogadores Ordenados
     * 
     * for (int i = 0; i < Jogadores.size(); i++) {
     * imprimir(Jogadores.get(i));
     * }
     * 
     * // Bloco para printar a matricula no arquivo
     * 
     * String nomeArquivo = "725052_countingsort.txt";
     * 
     * FileWriter fileWriter = new FileWriter(nomeArquivo);
     * 
     * PrintWriter printWriter = new PrintWriter(fileWriter);
     * 
     * printWriter.print("725052\t");
     * 
     * printWriter.print(contador_comp[0] + "\t");
     * 
     * printWriter.print(contador_mov[0] + "\t");
     * 
     * printWriter.print(duracaoEmMilissegundos);
     * 
     * printWriter.close();
     */

}
