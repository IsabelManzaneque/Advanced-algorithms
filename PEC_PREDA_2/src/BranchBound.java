import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class BranchBound {
	
		
	private int n; // numero de pasteleros
	private int m; // tipos de pasteles
	private int pasteleros[]; // [1,2,3,4,5]  de 1 a n pasteleros
	private int pedidos[];    // [1,1,3,2,1]  n pedidos, tantos pedidos como pasteleros
	private int costes[][];   //  el valor cij corresponde al coste de que el pastelero i realice el pastel j
	private double coste;                       //  tiene tantas filas como pasteleros (n rows) y tantas columnas como pasteles (m cols)
	

    public BranchBound(int n, int m, int pasteleros[], int pedidos[], int costes[][]){
    	
    	this.n = n;
    	this.m = m;
    	this.pasteleros = pasteleros; // igual no me hace falta y solo necesito el de la clase nodo
    	this.pedidos = pedidos;
    	this.costes = costes;	
    	this.coste = 0.0;
	}       
    
    /* Clase privada que implementa la estructura de nodos*/     // Tiene acceso a los atributos de la clase padre, pero al reves no
    private class Nodo{
    	
    	private int pasteleros[];    // almacena en la posicion i el pastelero asignado al pedido i
    	private boolean asignados[]; // indica si el pastelero de la posicion i ha sido ya asignado
    	private int k;               // ultimo pedido asignado
    	private double coste;        // total de las asignaciones ya hechas
    	private double estOpt;       // estimacion optimista
    	
    	
		public Nodo() {
			super();
			this.pasteleros = new int[n];
			this.asignados = new boolean[n];
			this.k = 0;
			this.coste = 0.0;
			this.estOpt = 0.0;
		}

		public int[] getPasteleros() {
			return pasteleros;
		}

		public boolean[] getAsignados() {
			return asignados;
		}

		public int getK() {
			return k;
		}

		public void setPasteleros(int[] pasteleros) {
			this.pasteleros = pasteleros;
		}

		public void setAsignados(boolean[] asignados) {
			this.asignados = asignados;
		}

		public void setK(int k) {
			this.k = k;
		}

		public void setCoste(double coste) {
			this.coste = coste;
		}

		public void setEstOpt(double estOpt) {
			this.estOpt = estOpt;
		}

		public double getCoste() {
			return coste;
		}

		public double getEstOpt() {
			return estOpt;
		}    	
    	
    }
    
    
    public double estimacionOptimista(int costes[][], int pedidos[], int k, double coste) {
    	
    	double estimacion = coste;
    	double menorC = 0.0;    	
    	
    	for(int i = k+1; i < n; i++) {       		
    		menorC = costes[1][pedidos[i]];
    		
    		for(int j = 2; j < n ; j++) {    			
    			if(menorC > costes[j][pedidos[i]]) {
    				menorC = costes[j][pedidos[i]];
    			}
    		}
    		estimacion += menorC;    		
    	}
    	return estimacion;
    }
    
    public double estimacionPesimista(int costes[][], int pedidos[], int k, double coste) {
    	
    	double estimacion = coste;
    	double mayorC = 0.0;
    	
    	for(int i = k+1; i < n; i++) {
    		mayorC = costes[1][pedidos[i]];
    		
    		for(int j = 2; j < n; j++) {
    			if(mayorC < costes[j][pedidos[i]]) {
    				mayorC = costes[j][pedidos[i]];
    			}
    		}
    		estimacion += mayorC;
    	}
    	return estimacion;
    }
  
    

    /* Ejecuta el algoritmo de Ramificacion y Poda "pasteleria" sobre los datos de entrada. Si
     * trazaOn es true, muestra la traza del algoritmo */
    public String pasteleria(boolean trazaOn){  

    	PriorityQueue<Nodo> monticulo = new PriorityQueue();  //monticulo de minimos
        String resultado ="";
        String traza = "";
        Double cota = 0.0;
        Double estPes = 0.0;
        Nodo nodo = new Nodo();
        Nodo hijo = new Nodo();        
        
        // Construimos el primer nodo:
        
        // pasteleros y asignados ya estan inicializados
        // coste y k ya son 0
       
        nodo.setEstOpt(estimacionOptimista(this.costes, this.pedidos, nodo.getK(), nodo.getCoste()));
        monticulo.add(nodo);
        cota = estimacionPesimista(this.costes, this.pedidos, nodo.getK(), nodo.getCoste());
        
        while(!monticulo.isEmpty() && monticulo.peek().getEstOpt() <= cota) {
        	
        	nodo = monticulo.poll();
        	
        	//se generan las extensiones validas del nodo
        	//para cada pastelero no asignado se crea un nodo
        	
        	hijo.setK(nodo.getK()+1);
        	hijo.setPasteleros(nodo.getPasteleros());
        	hijo.setAsignados(nodo.getAsignados());
        	
        	for(int i = 0; i < n; i++) {
        		
        		if(!hijo.getAsignados()[i]) {
        			
        			hijo.getPasteleros()[hijo.getK()] = i;
        			hijo.getAsignados()[i] = true;
        			hijo.setCoste(nodo.getCoste() + costes[i][pedidos[hijo.getK()]]);
        			
        			if(hijo.getK() == n) {
        				
        				if(cota >= hijo.getCoste()) {
        					
        					pasteleros = hijo.getPasteleros();  //aqui pasa algo con los vectores pasteleros
        					coste = hijo.getCoste();
        					cota = coste;
        				}
        			}else {
        				
        				hijo.setEstOpt(estimacionOptimista(this.costes, this.pedidos, hijo.getK(), hijo.getCoste()));
        				monticulo.add(hijo);
        				estPes = estimacionPesimista(this.costes, this.pedidos, hijo.getK(), hijo.getCoste());
        				
        				if(cota > estPes) {
        					cota = estPes;
        				}
        			}
        			hijo.getAsignados()[i] = false;        			
        		}
        	}
        }
        
        resultado += Arrays.toString(pasteleros) + "\n" + coste;
        

        
        // Si la traza esta activa, la muestra. En otro caso solo muestra el resultado
        if(trazaOn){return traza;}
        return resultado;        
    }
    
    
    
    
    @Override
    public String toString() {
    	String c = "";
    	for(int i = 0; i<this.costes.length; i++) {
    		for(int j = 0; j< this.costes[0].length; j++) {
    			c += this.costes[i][j];
    		}
    		c+="\n";
    	}
        return "n: " + this.n + ", m: " + this.m + "\npasteleros: " + Arrays.toString(pasteleros) + "\npedidos: " + Arrays.toString(pedidos) + "\ncostes: \n"+c;
    }

}
