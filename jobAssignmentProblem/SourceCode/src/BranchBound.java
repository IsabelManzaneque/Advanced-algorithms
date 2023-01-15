import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * Clase que recibe los datos pre procesados de entrada y 
 * ejecuta el algoritmo principal "Asignacion de tareas: 
 * Pasteleria"
 * 
 * @author Isabel Manzaneque, imanzaneq3@alumno.uned.es
 */
public class BranchBound {
	
		
	private int numeroPasteleros;           
	private int tiposPastel;           
	private int pasteleros[];
	private int pedidos[];   
	private int costes[][];  
	private double coste;       
	

    public BranchBound(int numeroPasteleros, int tiposPastel, int pedidos[], int costes[][]){
    	
    	this.numeroPasteleros = numeroPasteleros;
    	this.tiposPastel = tiposPastel;
    	this.pasteleros = new int[numeroPasteleros]; 
    	this.pedidos = pedidos;
    	this.costes = costes;	
    	this.coste = 0.0;
	}       
    
   
    /**
     * Clase privada que implementa la estructura de nodos
     */
    private class Nodo implements Comparable<Nodo>{
    	
    	private int pasteleros[];    
    	private boolean asignados[]; 
    	private int ultimoAsignado;               
    	private double coste;        
    	private double estOptimista;       
    	
    	/**
         * Constructor
         */
		public Nodo() {
			
			this.pasteleros = new int[numeroPasteleros];
			this.asignados = new boolean[numeroPasteleros];
			this.ultimoAsignado = -1;  
			this.coste = 0.0;
			this.estOptimista = 0.0;
		}
		
		/**
	     * Constructor por copia
	     */
		public Nodo(Nodo n) {
			
			this.pasteleros = n.pasteleros;
			this.asignados = n.asignados;
			this.ultimoAsignado = n.ultimoAsignado;
			this.coste = n.coste;
			this.estOptimista = n.estOptimista;
		}
		
		/**
	     * Getters y setters
	     */
		public int[] getPasteleros() {
			return pasteleros;
		}

		public boolean[] getAsignados() {
			return asignados;
		}

		public int getUltimoAsignado() {
			return ultimoAsignado;
		}

		public void setPasteleros(int[] pasteleros) {
			this.pasteleros = pasteleros;
		}

		public void setAsignados(boolean[] asignados) {
			this.asignados = asignados;
		}

		public void setUltimoAsignado(int ultimoAsignado) {
			this.ultimoAsignado = ultimoAsignado;
		}

		public void setCoste(double coste) {
			this.coste = coste;
		}

		public void setEstOpt(double estOptimista) {
			this.estOptimista = estOptimista;
		}

		public double getCoste() {
			return coste;
		}

		public double getEstOpt() {
			return estOptimista;
		}

		/**
	     * Override de la funcion compareTo para que compare
	     * dos nodos por sus estimaciones optimistas
	     */
		@Override
		public int compareTo(BranchBound.Nodo o) {		
			return Double.compare(this.estOptimista, o.estOptimista);
		}  	
		
		/**
	     * Override de la funcion toString del objeto nodo
	     */
		@Override
		public String toString() {		
			return "pasteleros: " + Arrays.toString(pasteleros) + " asignados: " + Arrays.toString(asignados) + 
				   " estimacion Optimista: " + estOptimista + " ultimo Asignado: " + ultimoAsignado + " coste: " + coste;
		}  
    	
    }   

        
    /**
     * Estimacion optimista minima. Se calcula sumando el coste de las tareas ya asignadas 
     * con el coste minimo que tienen las tareas pendientes de aignar
     */
    public double estimacionOptimista(int costes[][], int pedidos[], int k, double coste) {
    	
    	double estimacion = coste;
    	double menorC = 0.0;    	
    	
    	for(int i = k+1; i < numeroPasteleros; i++) {       		
    		menorC = costes[0][pedidos[i]-1];
    		
    		for(int j = 1; j < numeroPasteleros; j++) {    			
    			if(menorC > costes[j][pedidos[i]-1]) {
    				menorC = costes[j][pedidos[i]-1];
    			}
    		}
    		estimacion += menorC;    		
    	}
    	return estimacion;
    }
    
     
    /**
     * Define una cota pesimista de una solucion parcial sumando el coste de los pedidos ya
     * asignados con el coste maximo que pueden tener los pendientes de asignar. Se actualiza
     * cuando se alzancen soluciones y cuando se produzcan nuevos nodos
     */
    public double estimacionPesimista(int costes[][], int pedidos [], int k, double coste) {
    	
    	double estimacion = coste;
    	double mayorC = 0.0;
    	
    	for(int i = k+1; i < numeroPasteleros; i++) {
    		mayorC = costes[0][pedidos[i]-1];
    		
    		for(int j = 1; j < numeroPasteleros; j++) {
    			if(mayorC < costes[j][pedidos[i]-1]) {
    				mayorC = costes[j][pedidos[i]-1];
    			}
    		}
    		estimacion += mayorC;
    	}
    	return estimacion;
    } 
    

    /**
     * Ejecuta el algoritmo de Ramificacion y Poda sobre los datos de entrada. Si
     * trazaOn es true, muestra la traza del algoritmo
     */
    public String pasteleria(boolean trazaOn){  

    	PriorityQueue<Nodo> monticulo = new PriorityQueue<>();  
    	ArrayList<String> soluciones = new ArrayList<>();
    	Nodo nodo = new Nodo();
        Nodo hijo = new Nodo(); 
        String resultado ="";
        Double estPesimista = 0.0;
        Double cota = 0.0;       
        int it = 0;
        
        String traza = "----------------- PARAMETROS -----------------\n\n";

        traza += this;   
        
        nodo.setEstOpt(estimacionOptimista(costes, pedidos, nodo.getUltimoAsignado(), nodo.getCoste()));
        monticulo.add(nodo);
        cota = estimacionPesimista(costes, pedidos, nodo.getUltimoAsignado(), nodo.getCoste());
        
        // Mientras el monticulo no este vacio y la estimacion optimista 
        // del nodo cima sea mejor que la cota continua ejecutandose
        while(!monticulo.isEmpty() && monticulo.peek().getEstOpt() <= cota) {
        	it++;
        	traza += "\n\n--------------------- Iteracion " + it + " ---------------------\n" +
        	         "\nCota (estimacion pesimista): " + cota +
        	         "\nNodo en la cima del monticulo (Mejor solución hasta el momento):\n" + monticulo.peek() +
        	         "\n\nHijos generados:\n";
        	
        	
        	hijo.setUltimoAsignado(monticulo.peek().getUltimoAsignado()+1);
        	hijo.setPasteleros(monticulo.peek().getPasteleros());
        	hijo.setAsignados(monticulo.peek().getAsignados());
        	double costeCima = monticulo.peek().getCoste();
        			
        	monticulo.poll();
        	
        	for(int i = 0; i < numeroPasteleros; i++) {        		
        		if(!hijo.getAsignados()[i]) {        			
        			
        			// Construye nodos con las posibles extensiones, los
        			// pasteleros que se pueden asignar al siguiente pedido
        			hijo.getPasteleros()[hijo.getUltimoAsignado()] = i+1;
        			hijo.getAsignados()[i] = true;
        			hijo.setCoste(costeCima + costes[i][pedidos[hijo.getUltimoAsignado()]-1]);
        			traza += hijo + "\n";
        			
        			// Se han asignado todos los pasteleros
        			if(hijo.getUltimoAsignado() == numeroPasteleros-1) {         				
        				if(cota >= hijo.getCoste()) {        					
        					
        					// Ha encontrado una solucion, actualiza el resultado        					
        					pasteleros = hijo.getPasteleros();  
        					coste = hijo.getCoste();
        					cota = coste;           					
        					resultado = Arrays.toString(pasteleros) + "\n" + coste;
        					
        					// Inserta solucion en vector de soluciones encontradas
        					soluciones.add("\n * Pasteleros asignados: " + Arrays.toString(pasteleros) + "  Coste: " + coste);
        					traza += "\nSolucion " + soluciones.size() + " --> Pasteleros asignados: " + Arrays.toString(pasteleros) + "  Coste: " + coste;	
        				} 
        		    // Quedan pasteleros por asignar
        			}else { 
        				        				        				
        				hijo.setEstOpt(estimacionOptimista(costes, pedidos, hijo.getUltimoAsignado(), hijo.getCoste()));        			
        				
        				// Para evitar problemas de referencias, se realiza una copia del
        				// nodo hijo, se le asignan copias de los vectores asignados y 
        				// pasteleros. Es el nodo copia el que se guarda en el monticulo  
        				Nodo nodoCopia = new Nodo(hijo);
        				nodoCopia.setPasteleros(Arrays.copyOf(hijo.getPasteleros(), hijo.getPasteleros().length)); 
        				nodoCopia.setAsignados(Arrays.copyOf(hijo.getAsignados(), hijo.getAsignados().length));        				
        				monticulo.add(nodoCopia);
        				
        				estPesimista = estimacionPesimista(costes, pedidos, hijo.getUltimoAsignado(), hijo.getCoste());        				
        				
        				if(cota > estPesimista) { cota = estPesimista; }
        			}
        			
        			hijo.getAsignados()[i] = false;        			
        		}
        	}
        }         
        
	    traza += "\n\n\nTERMINA EL ALGORITMO\n"
	          + "\n\n----------------- RESULTADO -----------------\n\n"
	    	  + "Ultima solucion optima encontrada: \n\n"
		      + " * Pasteleros asignados: " + Arrays.toString(pasteleros) + "  Coste: " + coste 
		      + "\n\nSoluciones encontradas por el algoritmo: \n";
		
		for (String solucion : soluciones) {
			traza += solucion;
		}
        if(trazaOn){return traza;}
	    return resultado;  
    }
    
    /**
     * Override de la funcion toString del objeto BranchBound
     */
    @Override
    public String toString() {
    	String c = "";
    	for(int[] arr : costes) {
        	c += Arrays.toString(arr) + "\n";
        }    	
        return "Numero pasteleros: " + this.numeroPasteleros + "\nTipos de pasteles: " + this.tiposPastel +  
        	   "\nPedidos: " + Arrays.toString(pedidos) + "\ncostes asociados: \n"+c;
    }

}

