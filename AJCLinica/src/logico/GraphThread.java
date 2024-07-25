package logico;

import Visual.Principal;

public class GraphThread extends Thread { 
    public GraphThread() {
        super("GraphThread");
    }

	@Override
	public void run() {
		try {
			boolean Terminado = true;
				while(Terminado){
					try {
						if(Principal.getFrames().length > 0) {
							Principal.UpdateGraphs();
						}
					} catch (Exception e) {
					}					
					Thread.sleep(2000);
				}
		} catch (Exception e) {
			System.out.println("Thread de graficos interrumpida");
		}
	}

}
