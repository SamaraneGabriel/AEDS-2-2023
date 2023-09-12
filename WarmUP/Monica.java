class Monica{


	public static void main(String[] args){

		int monica = MyIO.readInt();
		int a = MyIO.readInt();
		int b = MyIO.readInt();
		int c = 0;

		if(40 <= monica && monica <= 110 && (a != b) && (1<= a && a < monica) && (1<=b && b<= monica)){
			c = monica - (a+b);
			if(c >= a && c >= b){
				MyIO.print(c);
			}
			else if(a >= b && a >= c){
				MyIO.print(a);
			}
			else if(b >= c && b >= a){
				MyIO.print(b);
			}
		}
	}
	
}
