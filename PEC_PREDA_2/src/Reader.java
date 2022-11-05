import java.util.*;
import java.io.*; 

// Cambiar para que en lugar de guardar los atributos, los parser devuelvan un objeto pasteleria?
public class Reader {
    
    /* Dado un archivo de entrada, extrae y devuelve la informacion */
    public BranchBound parseFile(String str) {   	
    	
    	File myFile = new File(str);  	
    	BranchBound bb = null;
        
        try{        	
            Scanner scanner = new Scanner(myFile);  
            int n = Integer.parseInt(scanner.nextLine());    
            int m = Integer.parseInt(scanner.nextLine()); 
            int costes[][] = new int[n][m];
            //int pasteleros[] = IntStream.range(0, n).toArray();
            int pedidos[] = Arrays.stream(scanner.nextLine().split("-")).mapToInt(Integer::parseInt).toArray();           
            
            int i = 0;
            while (scanner.hasNextLine()) {
            	            	
                String data[] = scanner.nextLine().split("\\s+");
                costes[i] = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
                i++;                        
            }
            scanner.close(); 
            
            // Si los datos son validos, se construye y devuelve un objeto BranchBound
            if(!inputValidator(n,m,pedidos,costes)) {return null;}
            bb = new BranchBound(n,m,pedidos,costes);            
           
        }catch (Exception e){
            return null;
        }            
        return bb;    
    }
    
    /* Dado una String de entrada, extrae y devuelve la informacion */
    public BranchBound parseStdin(String str) {
    	
    	BranchBound bb = null;
    	String[] splited = str.split("\\s+");   	
    	    	
    	try{
	    	int n = Integer.parseInt(splited[0]);
	    	int m = Integer.parseInt(splited[1]); 
	        int costes[][] = new int[n][m];
	    	//int pasteleros[] = IntStream.range(0, n).toArray();
	    	int pedidos[] = Arrays.stream(splited[2].split("-")).mapToInt(Integer::parseInt).toArray();  
	    		
		    int aux = 3;
		    for(int i = 0; i < n; i++) {
		    	for(int j = 0; j < m; j++) {
		    		costes[i][j] = Integer.parseInt(splited[aux]);
		    		aux ++;
		    	}
		    }
		    // Si los datos son validos, se construye y devuelve un objeto BranchBound
		    if(!inputValidator(n,m,pedidos,costes)) {return null;}
	    	bb = new BranchBound(n,m,pedidos,costes);   
	    	
		}catch (Exception e){
	        return null;
	    }     	  	
    	return bb;    
    }  
    
    /* Comprueba que los parametros de la entrada sean consistentes con el enunciado */
    public boolean inputValidator(int n, int m, int pe[], int c[][]) {
    	
    	if( n != pe.length || /*n != pa.length ||*/ n != c.length) {return false;}
        
    	for(int num : pe) 
    		if(num<1 || num>m) {return false;}   		
    	   	
    	for(int i = 0; i < c.length; i++) 
    		if(c[i].length != m) {return false;}    	
    	   	
    	return true;
    }
	
}
