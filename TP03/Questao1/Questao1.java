import java.io.BufferedReader;
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

class Lista { // Lista para o tipo Jogador
    // Atributos
    private Jogador[] array;
    private int n;

    // Metodos

    public Lista() {
        this(4000);
    }

    public Lista(int tamanho) {
        array = new Jogador[tamanho];
        n = 0;
    }

    /* Metodo para retornar o tamanho da fila */

    public int size() {
        if (n == 0) {
            System.out.println("LISTA VAZIA");
        }
        return n;

    }

    public Jogador get(int pos) {
        if (pos >= n) {
            return null;
        } else {
            return array[pos];
        }
    }

    /*
     * Metodo para inserir no inicio da lista
     * param jogador, jogador a ser inserido
     */

    public void inserirInicio(Jogador jogador) {
        if (n >= array.length) {
            System.out.println("LISTA CHEIA, ERRO!");
        }

        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = jogador;
        n++;
    }

    /**
     * Insere um elemento na ultima posicao da lista.
     * 
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    public void inserirFim(Jogador jogador) throws Exception {

        // validar insercao
        if (n >= array.length) {
            throw new Exception("Erro ao inserir!");
        }

        array[n] = jogador;
        n++;
    }

    /**
     * Insere um elemento em uma posicao especifica e move os demais
     * elementos para o fim da lista.
     * 
     * @param x   int elemento a ser inserido.
     * @param pos Posicao de insercao.
     * @throws Exception Se a lista estiver cheia ou a posicao invalida.
     */
    public void inserir(Jogador jogador, int pos) throws Exception {

        // validar insercao
        if (n >= array.length || pos < 0 || pos > n) {
            throw new Exception("Erro ao inserir!");
        }

        // levar elementos para o fim do array
        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }

        array[pos] = jogador;
        n++;
    }

    /**
     * Remove um elemento da primeira posicao da lista e movimenta
     * os demais elementos para o inicio da mesma.
     * 
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Jogador removerInicio() throws Exception {

        // validar remocao
        if (n == 0) {
            throw new Exception("Erro ao remover!");
        }

        Jogador resp = array[0];
        n--;

        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }

        return resp;
    }

    /**
     * Remove um elemento da ultima posicao da lista.
     * 
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Jogador removerFim() throws Exception {

        // validar remocao
        if (n == 0) {
            throw new Exception("Erro ao remover!");
        }

        return array[--n];
    }

    /**
     * Remove um elemento de uma posicao especifica da lista e
     * movimenta os demais elementos para o inicio da mesma.
     * 
     * @param pos Posicao de remocao.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
     */
    public Jogador remover(int pos) throws Exception {

        // validar remocao
        if (n == 0 || pos < 0 || pos >= n) {
            throw new Exception("Erro ao remover!");
        }

        Jogador resp = array[pos];
        n--;

        for (int i = pos; i < n; i++) {
            array[i] = array[i + 1];
        }

        return resp;
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

        Lista Jogadores = new Lista(4000);

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

    public static Lista filtraId(ArrayList<Integer> ids, Lista Jogadores) {
        Lista Jogadores_corretos = new Lista();
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            for (int j = 0; j < Jogadores.size(); j++) {
                if (Jogadores.get(j) != null) {
                    Jogador jogador = Jogadores.get(j);
                    if (jogador.id == id) {
                        try {
                            Jogadores_corretos.inserirFim(jogador);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return Jogadores_corretos;
    }

    /*
     * Metodo que recebe a entrada e divide ela em: comando E numero
     * 
     * @params String contendo o comando e o numero
     * 
     * @return Array de String contendo respectivamente o comando e o numero
     */

    public static String[] trataString(String comando) {
        // System.out.println(comando);
        String[] comando_tratado = new String[3]; // Aumentar para 3, para acomodar possível posição em comandos I*

        if (comando.startsWith("II") || comando.startsWith("IF") || comando.startsWith("RI")
                || comando.startsWith("RF")) {
            comando_tratado[0] = comando.substring(0, 2);
            comando_tratado[1] = comando.length() > 3 ? comando.substring(3) : "";
        } else if (comando.startsWith("I*") || comando.startsWith("R*")) {
            comando_tratado[0] = comando.substring(0, 2);
            String[] partes = comando.substring(3).split(" ");
            comando_tratado[1] = partes[0]; // posição
            if (partes.length > 1) {
                comando_tratado[2] = partes[1]; // ID, no caso do comando I*
            }
        }

        for (int i = 0; i < 3; i++) {
            // System.out.println(comando_tratado[i]);
        }

        return comando_tratado;
    }

    /*
     * Metodo que efetivamente realiza as chamads das funcoes na Lista de jogadores
     * com base na String tratada
     */

    public static Resultado callCommand(String[] comando_numero, Lista jogadores, Lista todos_jogadores)
            throws Exception {
        String comando = comando_numero[0];
        Resultado res = new Resultado();
        switch (comando) {
            case "II":
                int idInicio = Integer.parseInt(comando_numero[1]);
                Jogador jogadorInicio = todos_jogadores.get(idInicio);
                jogadores.inserirInicio(jogadorInicio);
                res.jogadoresRemovidos = null;
                break;

            case "IF":
                int idFim = Integer.parseInt(comando_numero[1]);
                Jogador jogadorFim = todos_jogadores.get(idFim);
                jogadores.inserirFim(jogadorFim);
                res.jogadoresRemovidos = null;
                break;

            case "I*":
                int posicao = Integer.parseInt(comando_numero[1]);
                int id = Integer.parseInt(comando_numero[2]);
                Jogador jogador = todos_jogadores.get(id);
                jogadores.inserir(jogador, posicao);
                res.jogadoresRemovidos = null;
                break;

            case "RI":
                Jogador jogadorRemovidoInicio = jogadores.removerInicio();
                res.jogadoresRemovidos = jogadorRemovidoInicio;
                break;

            case "RF":
                Jogador jogadorRemovidoFim = jogadores.removerFim();
                res.jogadoresRemovidos = jogadorRemovidoFim;
                break;

            case "R*":
                int posRemover = Integer.parseInt(comando_numero[1]);
                Jogador jogadorRemovido = jogadores.remover(posRemover);
                res.jogadoresRemovidos = jogadorRemovido;
                break;

            default:
                break;
        }

        res.jogadoresAtualizados = jogadores;

        return res;
    }

    /* Método void que imprime um Jogador usado apenas para fins de Debug */

    public static void imprimir(Jogador objeto) {
        if (objeto != null) {
            System.out.println("[" + objeto.id + " ## " + objeto.nome + " ## " + objeto.altura + " ## " + objeto.peso
                    + " ## " + objeto.anoNascimento
                    + " ## " + objeto.universidade + " ## " + objeto.cidadeNascimento + " ## " + objeto.estadoNascimento
                    + " ]");
        } else {
            System.out.println("NULL PEBA");
        }
    }

    public static void imprimir_verde(Lista jogadores) {
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);
            System.out.println("[" + i + "] ## " + jogador.nome + " ## " + jogador.altura + " ## " + jogador.peso
                    + " ## " + jogador.anoNascimento
                    + " ## " + jogador.universidade + " ## " + jogador.cidadeNascimento + " ## "
                    + jogador.estadoNascimento + " ##");

        }
    }

}   

public class Questao1 extends Jogador {

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

        int n = sc.nextInt();

        String[] comandos = new String[1000];

        int contador1 = 0;

        todos_Jogadores = criarVetor("/tmp/players.csv");

        for (int i = 0; i < todos_Jogadores.size(); i++) {
            // imprimir(todos_Jogadores.get(i));
        }

        Jogadores = filtraId(ids, todos_Jogadores);

        for(int i = 0; i < Jogadores.size(); i++) {
            imprimir(Jogadores.get(i));
        }
        while (sc.hasNextLine()) {
            String comando = sc.nextLine();
            comandos[contador1] = comando;
            // System.out.println(comandos[contador1]);
            contador1++;
        }

        for (int i = 0; i < Jogadores.size(); i++) {
            // imprimir(Jogadores.get(i));
        }
        // loop que chama os comandos

        ArrayList<Jogador> jogadoresRemovidos = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            // System.out.println(comandos[i]);
            String[] comandos_aux = trataString(comandos[i]);
            Resultado resultado = callCommand(comandos_aux, Jogadores, todos_Jogadores);
            Jogadores = resultado.jogadoresAtualizados;
            if (resultado.jogadoresRemovidos != null) {
                jogadoresRemovidos.add(resultado.jogadoresRemovidos);
            }
        }

        for (int i = 0; i < jogadoresRemovidos.size(); i++) {
            //System.out.println("(R) " + jogadoresRemovidos.get(i).getNome());
        }

        //imprimir_verde(Jogadores);
    }

}
    