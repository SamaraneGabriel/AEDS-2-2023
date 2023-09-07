	import java.io.RandomAccessFile;
	import java.io.FileNotFoundException;
	import java.io.IOException;

	class Arquivo{

			//Espaço dedicado para métodos usados indiretamente para a resolução do exercício
			
	/*==================================================================================================================================*/
	
		/*Procedimento que altera o tamanho do arquivo para 0, efetivamente limpando ele de qualquer conteúdo;
		 *@param: String nome do arquivo
		 */

		public static void clearFile(String fileName) {
		    try {
		        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		        file.setLength(0);
		        file.close();
		    } catch (FileNotFoundException e) {
		        MyIO.println("Arquivo não encontrado: " + fileName);
		    } catch (IOException e) {
		        MyIO.print("Erro ao limpar o arquivo: " + e.getMessage());
		    }
		}
		

	/*==================================================================================================================================*/


				//Espaço dedicado para métodos requisitados diretamente pelo exercício

			/*Procedimento que imprime em arquivo texto um double 
			 *@param: double numero a ser impresso, String nome do arquivo
			 */
			

		    public static void printDoubleToFile(double num, String fileName) {
		        try {
	    	        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
	    	        file.seek(file.length());
	        	    file.writeDouble(num); 
	           	    file.close();
	       		 } catch (FileNotFoundException e) {
	           		 	MyIO.println("Arquivo não encontrado:  " + fileName);
	        		}
	        		catch (IOException e){
	        			MyIO.print("erro: "+ e.getMessage());
	        		}
	    	}


			/*Procedimento que imprime na tela o conteúdo de um arquivo contendo doubles
			 *@param: String nome do arquivo, int quantidade de doubles
			 */	
	    	
	    	public static void printDoubleFromFile(String fileName, int size){
				double real = 0;
	    		try{
	    			RandomAccessFile file = new RandomAccessFile(fileName, "r");
	    			for(int i = 0; i < size; i++){
	    				file.seek(size*8 - (i+1)*8);
	    				real = file.readDouble();
	    				if(real - (int) real == 0){     		//Parte adicionada apenas para igualar a saida esperada pelo Verde
	    					MyIO.print((int)real + "\n");	
	    				}
	    				else{
	    					MyIO.print(real + "\n");
	    				}
	    			}
	    			file.close();	
	    			}
	    			catch (FileNotFoundException e) {
	    			         MyIO.println("Arquivo não encontrado:  " + fileName);
	    			     }
	    			 catch (IOException e){
	    			       	 MyIO.print("erro: "+ e.getMessage());
	    			       	 e.printStackTrace();
	    			     }
	    		}
	    		



			//Método principal
			public static void main(String[] args){

				clearFile("ArquivoTP.txt");

				int n = MyIO.readInt();

				for(int i = 0; i < n; i++){
					double real = MyIO.readDouble();
					printDoubleToFile(real,"ArquivoTP.txt");
				}

				printDoubleFromFile("ArquivoTP.txt",n);
			
			}

		
	}
