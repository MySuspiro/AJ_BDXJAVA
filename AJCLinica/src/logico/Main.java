package logico;

public class Main {

	public static void main(String[] args) {
	    LoginThread t1 = new LoginThread();
	    GraphThread t2 = new GraphThread();
	    t1.start();
	    t2.start();
		
	}
	
}
