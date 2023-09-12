class Vetor{
	public static void main(String[] args){

		int n = MyIO.readInt();

		int[] vetor = new int[n];

		if((1 <= n && n <= 100000)){

			for(int i = 0; i < n; i++){
				vetor[i] = MyIO.readInt();
			}

			for(int i = 0; i < n; i++){					
				if((i % 2 != 0)&&(vetor[i] >= 0)){
					if(vetor[i] != vetor[i-1]){
						MyIO.print(vetor[i-1]);
					}
				}
			}
		}
	}
	
}
