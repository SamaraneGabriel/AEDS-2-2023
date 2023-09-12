
class Severino{


	public static void main (String[] args){

		String n = "";
		
		while(!n.equals("EOF")){
			n = MyIO.readLine();
			if(!n.equals("EOF")){
		
			int tamanho = Integer.parseInt(n);

			//int[] codigos = new int[tamanho];

			String[] livros = new String[tamanho];
	
				for(int i = 0; i < tamanho; i++){
					livros[i] = MyIO.readLine();
				}
					
				for(int i = 0; i < tamanho; i++){
					int menor = i;
					for(int j = i+1; j < tamanho; j++){
						if(Integer.parseInt(livros[j]) < Integer.parseInt(livros[menor])){
							menor = j;
						}
					}
					String temp = livros[i];
					livros[i] = livros[menor];
					livros[menor] = temp;

					MyIO.print(livros[i]+"\n");
				}
				

			}
		}
			
	}
		
}
