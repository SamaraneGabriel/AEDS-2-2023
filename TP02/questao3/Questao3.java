package questao3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

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

    //Construtores

    public Jogador(){
 
    }

    public Jogador(int id, String nome, int altura, int peso, String universidade, int anoNascimento, String cidadeNascimento, String estadoNascimento){
        
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

    /*Método que extrai as colunas da linha, substituindo valores vazios por "nao informado"
     * @params: String linha
     * @return: Array de Strings colunas
     */


     public static List<String> tratamentoString(String str) {

        List<String> colunas = new ArrayList<>();
        
        // Dividir a string usando a vírgula como separador
        String[] partes = str.split(",",-1);
        
        // Adicionar cada parte à lista, verificando se está vazia
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                colunas.add(parte);
            } else {
                colunas.add("nao informado");
            }
        }
        
        return colunas;
    }


    /* Método que itera pelo arquivo players.csv, criando um vetor com todos os objetos requisitado
     * @params: array list de inteiros contendo os ids requisitados, String nome do arquivo, 
     * @return: array list de jogadores contendo os jogadores referentes aos Ids requisitados
     */


	public static List<Jogador> criarVetor(String nomeArquivo, List<Integer> ids) throws Exception {
		
        //Le todo o arquivo e concatena todo o conteúdo em uma String

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

        List<Jogador> Jogadores = new ArrayList<>();

        List<Jogador> Jogadores_corretos = new ArrayList<>();

        List<String> colunas = new ArrayList<>();

        //Loop que armazena todos os jogadores em uma Lista

        for(int i = 1; i < linhas.length; i++){
            colunas = tratamentoString(linhas[i]);
            
            //Instanciando o objeto

            Jogador objeto = new Jogador(
            Integer.parseInt(colunas.get(0)),
            colunas.get(1),
            Integer.parseInt(colunas.get(2)),
            Integer.parseInt(colunas.get(3)),
            colunas.get(4),
            Integer.parseInt(colunas.get(5)),
            colunas.get(6),
            colunas.get(7));

            Jogadores.add(objeto);            
        }

        //Loop que seleciona somente os objetos com ids requisitados e os armazena em outra Lista de Jogadores 

        for(int id: ids){
            for(Jogador jogador: Jogadores){
                if(jogador.id == id){
                    Jogadores_corretos.add(jogador);
                }
            }
        }
        
        return Jogadores_corretos;

	}

    public static boolean pesquisaSequencialNome(List<Jogador> Jogadores, String nome, int contador[]){
        boolean flag = false;
        for(Jogador jogador: Jogadores){
            contador[0]++;
            if(jogador.nome.equals(nome)){
                flag = true;
            }
        }
        return flag;
    }

    /*Método void que imprime um Jogador usado apenas para fins de Debug*/
    
    public static void imprimir(Jogador objeto){
        System.out.println("[" + objeto.id + " ## " + objeto.nome + " ## " + objeto.altura + " ## " + objeto.peso + " ## " + objeto.anoNascimento 
            + " ## " + objeto.universidade + " ## " + objeto.cidadeNascimento + " ## " + objeto.estadoNascimento + "]");
    }

}





public class Questao3 extends Jogador{
    
    public static void main(String[] args) throws Exception{

        Scanner sc = new Scanner(System.in);

        String n = sc.nextLine();

        List<Integer> ids = new ArrayList<>();

        List<Jogador> Jogadores = new ArrayList<>();
        
        //Bloco que recebe as entradas dos ids a serem adicionados no vetor

        while(!n.equals("FIM")){
            ids.add(Integer.parseInt(n));
            n = sc.nextLine();
        }

        Jogadores = criarVetor("/tmp/players.csv",ids);

        String nome = sc.nextLine();

        //Bloco para printar a matricula no arquivo
       
        String nomeArquivo = "725052_sequencial.txt";

        FileWriter fileWriter = new FileWriter(nomeArquivo);    

        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.print("725052\t");

        //Bloco que recebe as entradas dos nomes a serem procurados

        double inicio = 0, fim = 0;

        int[] contador = new int[1];
        
        inicio = System.nanoTime();
        while(!nome.equals("FIM")){
            if(pesquisaSequencialNome(Jogadores, nome, contador)){
                System.out.println("SIM");
            }
            else{
                System.out.println("NAO");
            }
            nome = sc.nextLine();
        }
        fim = System.nanoTime();
        
        //Calculo e impressão no arquivo do tempo de execução em ms

        double duracao = (fim-inicio)/1000000;
        printWriter.print(duracao+"\t");

        //Print numero de comparacoes

        printWriter.print(contador[0]);
        

        
        printWriter.close();
        sc.close();

    }


}
