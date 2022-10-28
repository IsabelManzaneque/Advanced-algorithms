import java.util.*;
import java.util.stream.IntStream;
import java.io.*; 

// Cambiar para que en lugar de guardar los atributos, los parser devuelvan un objeto pasteleria?
public class Reader {
	
	private int n; 
	private int m; 
	private int pasteleros[]; 
	private int pedidos[];    
	private int costes[][];   
   

    public Reader(){	    	
    	n = 0;
    	m = 0;   	                      
    }
    
    /* Dado un archivo de entrada. comprueba si es valido y extrae la informacion */
    public void parseFile(String str) {   	
    	
    	File myFile = new File(str);  	    
        
        try{        	
            Scanner scanner = new Scanner(myFile);  
            this.n = Integer.parseInt(scanner.nextLine());    
            this.m = Integer.parseInt(scanner.nextLine()); 
            this.costes = new int[n][m];
            this.pasteleros = IntStream.range(0, n).toArray();
            this.pedidos = Arrays.stream(scanner.nextLine().split("-")).mapToInt(Integer::parseInt).toArray();           
            
            int i = 0;
            while (scanner.hasNextLine()) {
            	            	
                String data[] = scanner.nextLine().split("\\s+");
                costes[i] = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
                i++;                        
            }
            scanner.close();    
           
        }catch (Exception e){
            System.out.println("Error en el fichero de entrada");
        }    
    
    }
    
    /* Dado una String de entrada. comprueba si es valida y extrae la informacion */
    public boolean parseStdin(String str) {
    	
    	String[] splited = str.split("\\s+");   	
    	    	
    	try{
	    	this.n = Integer.parseInt(splited[0]);
	    	this.m = Integer.parseInt(splited[1]); 
	    	this.costes = new int[n][m];
	    	this.pasteleros = IntStream.range(0, n).toArray();
	    	this.pedidos = Arrays.stream(splited[2].split("-")).mapToInt(Integer::parseInt).toArray();  
		}catch (NumberFormatException ff){
	        return false;
	    } 
 
    	// si no hay al menos n, m, pedidos y n*m valores, o si el numero de 
    	// pasteleros no es igual al de pedidos, la entrada no es correcta 
    	if(splited.length != (3 + n*m) || n != pedidos.length) { return false;}
    	
    	int aux = 3;
    	for(int i = 0; i < n; i++) {
    		for(int j = 0; j < m; j++) {
    			costes[i][j] = Integer.parseInt(splited[aux]);
    			aux ++;
    		}
    	}    	    	
    	return true;    
    }
    
    /* Getters de los atributos de la clase */  
	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}

	public int[] getPasteleros() {
		return pasteleros;
	}

	public int[] getPedidos() {
		return pedidos;
	}

	public int[][] getCostes() {
		return costes;
	}


    
   
	
}
