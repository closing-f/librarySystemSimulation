package window;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main 
{	
	public static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(1000);
	
	public static void main(String[] args) 
	{	
		cachedThreadPool.execute(() -> 
		{
        	new Land();            
        });
	}
}