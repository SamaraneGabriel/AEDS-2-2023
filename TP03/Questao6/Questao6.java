import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Resultado {
    public Pilha jogadoresAtualizados;
    public Jogador jogadoresRemovidos;

    public Resultado() {
        this.jogadoresAtualizados = new Pilha();
        this.jogadoresRemovidos = new Jogador();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Jogadores Atualizados:\n");
        sb.append(jogadoresAtualizados.toString()); // Supondo que a classe ArrayList<Jogador> também tenha um método toString().
        sb.append("\nJogadores Removidos:\n");
        sb.append(jogadoresRemovidos.toString());

        return sb.toString();
    }

}

class Celula {
    public Celula prox;
    public Jogador jogador;

    public Celula() {
        this.jogador = null;
        this.prox = null;
    }

    public Celula(Jogador jogador) {
        this.jogador = jogador;
        this.prox = null;
    }
}

class Pilha { // ArrayList<Jogador> para o tipo Jogador
    // Atributos
    private Celula topo;
    private int contador;

    // Metodos

    public  Pilha() {
        this.topo = null;
    }

    public int size() {
        return this.contador;
    }

    public void push(Jogador jogador) {
        Celula temp = new Celula(jogador);
        temp.prox = topo;
        topo = temp;
        contador++;
    }


    public Jogador pop() {
        if(topo == null) {
            throw new Error("LISTA VAZIA");
        }
        else {
            Jogador removido = topo.jogador;
            topo = topo.prox;
            contador--;
            return removido;
        }
    }

    public Jogador get(int i) {
        Celula j = topo;
        for(int k = 0; k < i; k++, j = j.prox);
        Jogador jogador = j.jogador;
        return jogador;
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
    }Jogador jogador;

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

    public static ArrayList<Jogador> criarVetor(String nomeArquivo) throws Exception {

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

        ArrayList<Jogador> Jogadores = new ArrayList<>();

        String[] colunas = new String[8];

        // Loop que armazena todos os jogadores em uma ArrayList<Jogador>

        for (int i = 1; i < linhas.length; i++) {
            // System.out.println(linhas[i]);

            colunas = tratamentoString(linhas[i]);

            int id = Integer.parseInt(colunas[0]);
            if (id == 223) {
                Jogadores.add(null);
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

            Jogadores.add(objeto);
            // System.out.println("JOGADOR SENDO PRINTADO: " + objeto.id);
            // imprimir(Jogadores.get(i - 1));
        }

        return Jogadores;

    }
    /*
     * Método que filtra os jogadores por ID, retornando uma ArrayList<Jogador> de jogadores
     * corretos
     */

    public static Pilha filtraId(ArrayList<Integer> ids, ArrayList<Jogador> Jogadores) throws Exception {
        Pilha Jogadores_corretos = new Pilha();

        Jogador[] array = new Jogador[4000]; 

        for(int i = 0; i < Jogadores.size(); i++) {
            array[i] = Jogadores.get(i);
        }


        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            for (int j = 0; j < Jogadores.size(); j++) {
                if (array[j] != null) {
                    Jogador jogador = Jogadores.get(j);
                    if (jogador.id == id) {
                        Jogadores_corretos.push(jogador);
                        //imprimir(jogador);
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
        String[] comando_tratado = new String[2]; // Aumentar para 3, para acomodar possível posição em comandos I*

        if (comando.startsWith("I")) {
            comando_tratado[0] = comando.substring(0, 1);
            comando_tratado[1] = comando.substring(2);
        }
        else {
            comando_tratado[0] = comando.substring(0, 1);
            comando_tratado[1] = "";
        } 

        for (int i = 0; i < 2; i++) {
            //System.out.println(comando_tratado[i]);
        }

        return comando_tratado;
    }

    /*
     * Metodo que efetivamente realiza as chamads das funcoes na Lista de jogadores
     * com base na String tratada
     */

    public static Resultado callCommand(String[] comando_numero, Pilha jogadores, ArrayList<Jogador> todos_jogadores) throws Exception {
        String comando = comando_numero[0];
        Resultado res = new Resultado();

        switch (comando) {
            case "I":
                int id = Integer.parseInt(comando_numero[1]);
                Jogador jogadorInicio = todos_jogadores.get(id);
                jogadores.push(jogadorInicio);
                //imprimir(p.get(p.size() - 1));
                res.jogadoresRemovidos = null;
                break;

            case "R":
                Jogador removido = jogadores.pop();
                //imprimir(removido);
                res.jogadoresRemovidos = removido;
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

    public static void imprimir_verde(Pilha jogadores) throws Exception {
        for (int i = jogadores.size() - 1; i >= 0; i--) {
            Jogador jogador = jogadores.get(i);
            System.out.println("[" + ((jogadores.size() - 1) - i) + "] ## " + jogador.nome + " ## " + jogador.altura + " ## " + jogador.peso
                    + " ## " + jogador.anoNascimento
                    + " ## " + jogador.universidade + " ## " + jogador.cidadeNascimento + " ## "
                    + jogador.estadoNascimento + " ##");

        }
    }

}



public class Questao6 extends Jogador {

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws Exception {

        String numero = sc.nextLine();

        ArrayList<Integer> ids = new ArrayList<>();

        ArrayList<Jogador> todos_Jogadores = new ArrayList<>();

        Pilha Jogadores = new Pilha();

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
            //imprimir(todos_Jogadores.get(i));
        }

        Jogadores = filtraId(ids, todos_Jogadores);

        for (int i = 0; i < Jogadores.size(); i++) {
            //imprimir(Jogadores.get(i));
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
            System.out.println("(R) " + jogadoresRemovidos.get(i).getNome());
        }

        imprimir_verde(Jogadores);
    }

}
