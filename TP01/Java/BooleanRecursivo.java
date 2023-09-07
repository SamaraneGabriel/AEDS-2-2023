class BooleanRecursivo{

 		//Metodos indiretamente necessarios para realizar o exercicio
		
//====================================================================================

	/*Método que reparte uma String dentro de um dado intervalo [inicio,fim-1]
	 *@param: String str, int inicio, int fim
	 *@return: String aux
	 */

	public static String substring(String str, int inicio, int fim){
		String aux = "";
		for(int i = inicio ; i < fim; i++){
			aux += str.charAt(i);
		}
		return aux;	
	}

	/*Método para transsformar String em array de caracteres
	 *@param: String str
	 *@return: char[] aux 
	 */	

	public static char[] toCharArray(String str){
		char[] aux = new char[str.length()];
		for(int i = 0; i < str.length(); i++){
			aux[i] = str.charAt(i);
		}
		return aux;
	}
	
	/*Método para transfomar array de caracteres em String
	 *@param: char[] array
	 *@return: String aux
	 */

	public static String toString(char[] array){
		String aux = "";
		for(int i = 0; i < array.length; i++){
			aux += array[i];
		}
		return aux;
	}

	/*Método para remover espaços vázios de uma String
	 *@param: String str
	 *@return: String aux
	 */

	public static String removeBlank(String str){
		String aux = "";
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) != ' '){
				aux += str.charAt(i);
			}
		}
		return aux;
	}	


//=================================================================================

	/*Método para tratar as entradas da questão de modo a facilitar a manipulação
	 *@param: String str
	 *@return: String tratada
	 */	


	
	public static String tratamento(String str){

		String aux = removeBlank(str);				//Removendo os espaços em branco da String
		
		int n = str.charAt(0) - '0';				 //Capturando quantos bits serão usados
		char[] array = new char[n];
	
		for(int i = 1; i <= n; i++){
			array[i-1] = aux.charAt(i);			//Armazena os bits em uma rray 
		}

		char[] sub = toCharArray(substring(aux,n+1,aux.length())); 		//Adquirindo apenas a expressão lógica e convertendo para char[];
		
		for(int i = 0; i < n; i++){
			char c = (char) ('A' + i);
			for(int j = n+1; j < sub.length; j++){				//Trocando as letras A,B ou C por seus respectivos bits
				if(sub[j] == c){
					sub[j] = array[i];
				}
			}
		}
	
		String tratada = toString(sub);

	        tratada  = tratada.replace("and", "a")				//Finalizando o tratamento simplificando os termos and, or e not
       	                     .replace("or", "o")
                             .replace("not", "n");	

		return tratada;
	 }


	/*Método para avaliar o resultado da expressão previamente simplificada
	 *@param: String expr (simplificada)
	 *@return: Caracter na forma String representando 0 ou 1
	 */
			
	public static String avaliar(String expr){
		char operacao = expr.charAt(0);
		switch(operacao){
			case 'a':														//caso AND, basta localizar um 0 que a resposta ja será 0
				for(int i = 2; i < expr.length(); i++){
					if(expr.charAt(i) == '0'){					
						return "0";
					}
				}
				return "1";												
		
			case 'o':														//caso OR, basta localizar um 1 que a reposta ja será 1
                for(int i = 2; i < expr.length(); i++){
                    if(expr.charAt(i) == '1'){
                         return "1";
                     }
                 }
				return "0";
		
			case 'n':														//caso NOT, inversão 0->1  1->0
				return expr.charAt(2) == '0' ? "1" : "0";
			default:								         			/*Caso default nunca deve acontecer, a menos que a expressão seja passada
												       	 				  na formatação incorreta*/									
				return "";
		}
	}
	
	public static boolean chamadaRecursiva(String expr){
		return resolverRecursivo(tratamento(expr));
	}

	public static boolean resolverRecursivo(String expr){
		if(expr.contains("(")){
			int indiceFechamento = expr.indexOf(')');
			int indiceAbertura = expr.lastIndexOf('(',indiceFechamento);	

			String subExpr = substring(expr,indiceAbertura - 1, indiceFechamento + 1);		//Captura somente o conteudo dentro de uma expressão simples, não composta por outrAS
					
			String resul = avaliar(subExpr);
			expr = substring(expr,0,indiceAbertura-1) + resul + substring(expr,indiceFechamento+1,expr.length());	//Pega o resultado da subexpressão e o devolve para a expressão inicial					

			return resolverRecursivo(expr);
		}
		else{
			return expr.equals("1");
		}
		
	}



			//Método principal

	public static void main(String args[]){

 		 String str = "";

		while (!str.equals("0")) {
    			str = MyIO.readLine();
    			if (!str.equals("0")) {
        			if (chamadaRecursiva(str)) {
            				MyIO.print(1 + "\n");
        				} 
				else{
            				MyIO.print(0 + "\n");
        				}
    				}
			}

	}



		
}
