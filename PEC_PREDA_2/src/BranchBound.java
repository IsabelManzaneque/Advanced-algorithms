import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class BranchBound {
	
		
	private int n; // numero de pasteleros
	private int m; // tipos de pasteles
	private int pasteleros[]; // [0,0,0,0,0]  almacena en la posic i el pastelero asignado al pedido i.
	private int pedidos[];    // [1,1,3,2,1]  n pedidos, tantos pedidos como pasteleros
	private int costes[][];   //  el valor cij corresponde al coste de que el pastelero i realice el pastel j
	private double coste;       //  tiene tantas filas como pasteleros (n rows) y tantas columnas como pasteles (m cols)
	

    public BranchBound(int n, int m, int pedidos[], int costes[][]){
    	
    	this.n = n;
    	this.m = m;
    	this.pasteleros = new int[n]; 
    	this.pedidos = pedidos;
    	this.costes = costes;	
    	this.coste = 0.0;
	}       
    
    /* Clase privada que implementa la estructura de nodos*/     // Tiene acceso a los atributos de la clase padre, pero al reves no
    private class Nodo implements Comparable<Nodo>{
    	
    	private int pasteleros[];    // almacena en la posicion i el pastelero asignado al pedido i
    	private boolean asignados[]; // indica si el pastelero de la posicion i ha sido ya asignado
    	private int k;               // ultimo pedido asignado
    	private double coste;        // total de las asignaciones ya hechas
    	private double estOpt;       // estimacion optimista
    	
    	
		public Nodo() {
			
			this.pasteleros = new int[n];
			this.asignados = new boolean[n];
			this.k = -1;  // al cambiar la k a -1 el coste sale bien, al cambiarla a 0 el vector sale bien
			this.coste = 0.0;
			this.estOpt = 0.0;
		}
		public Nodo(Nodo n) {
			
			this.pasteleros = n.pasteleros;
			this.asignados = n.asignados;
			this.k = n.k;
			this.coste = n.coste;
			this.estOpt = n.estOpt;
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

		@Override
		public int compareTo(BranchBound.Nodo o) {		
			return Double.compare(this.estOpt, o.estOpt);
		}  	
		
		@Override
		public String toString() {		
			return "pasteleros: " + Arrays.toString(pasteleros) + " asignados: " + Arrays.toString(asignados) + " estOpt: " + estOpt + " k: " + k + " coste: " + coste;
		}  
    	
    }
    

    
    public int[][] getCostesTabla(int c[][], int p[]) {
    	
    	int[][] costesTabla = new int[n][n];
    	
    	for(int i = 0; i < n; i++) {
    		p[i] = p[i]-1;
    	}    	
    	
    	for(int i = 0; i < n; i++) {    		
    		for(int j = 0; j < n; j++) { 
    			costesTabla[i][j] = c[i][p[j]];
    	
    		}    		
    	}
        	
    	return costesTabla;
    }
    
	
    
    /* Estimacion optimista minima. Se calcula sumando el coste de las tareas ya asignadas 
     * con el coste minimo que tienen las tareas pendientes de aignar*/    
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
    
    
    /* Define una cota pesimista de una solucion parcial sumando el coste de los pasteles ya
     * asignados con el coste maximo que pueden tener los pendientes de asignar. Se actualiza
     * cuando se alzancen soluciones y cuando se produzcan nuevos nodos*/
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

    	PriorityQueue<Nodo> monticulo = new PriorityQueue<>();  //monticulo de minimos
        String resultado ="";
        Double cota = 0.0; // cota es el valor de la mejor soluci�n alcanzada hasta el momento
        Double estPes = 0.0;
        Nodo nodo = new Nodo();
        Nodo hijo = new Nodo(); 
        int it = 0;
        
        String traza = "----------------- PARAMETROS -----------------\n\n";

        traza += this;
        int c[][] = getCostesTabla(costes, pedidos);
        
        traza += "costes de cada pedido por pastelero:\n";
        for(int[] arr : c) {
        	traza += Arrays.toString(arr) + "\n";
        }
        
        
        nodo.setEstOpt(estimacionOptimista(c, pedidos, nodo.getK(), nodo.getCoste()));
        monticulo.add(nodo);
        cota = estimacionPesimista(c, pedidos, nodo.getK(), nodo.getCoste());
        
        
        while(!monticulo.isEmpty() && monticulo.peek().getEstOpt() <= cota) {
        	it++;
        	traza += "\n--------------------- Iteracion " + it + " ---------------------\n";
        	traza += "\nCota (estimacion pesimista): " + cota;
        	traza += "\nNodo en la cima del monticulo:\n" + monticulo.peek() + "\n";
        	traza += "\nHijos generados:\n";
        	
        	
        	hijo.setK(monticulo.peek().getK()+1);
        	hijo.setPasteleros(monticulo.peek().getPasteleros());
        	hijo.setAsignados(monticulo.peek().getAsignados());
        	double costeCima = monticulo.peek().getCoste();
        			
        	monticulo.poll();
        	
        	for(int i = 0; i < n; i++) {        		
        		if(!hijo.getAsignados()[i]) {
        			
        			hijo.getPasteleros()[hijo.getK()] = i+1;
        			hijo.getAsignados()[i] = true;
        			hijo.setCoste(costeCima + c[i][hijo.getK()]);
        			traza += hijo + "\n";
        			
        			if(hijo.getK() == n-1) { // la solucion esta completa
        				
        				if(cota >= hijo.getCoste()) {        					
        					
        					pasteleros = hijo.getPasteleros();  
        					coste = hijo.getCoste();
        					cota = coste;        					
        		
        				}
        			}else { // la solucion NO esta completa
        				
        				Nodo nodeArray[] = new Nodo[1];
        				
        				hijo.setEstOpt(estimacionOptimista(c, pedidos, hijo.getK(), hijo.getCoste()));        			
        				nodeArray[0] = new Nodo(hijo);
        				nodeArray[0].setPasteleros(Arrays.copyOf(hijo.getPasteleros(), hijo.getPasteleros().length)); 
        				nodeArray[0].setAsignados(Arrays.copyOf(hijo.getAsignados(), hijo.getAsignados().length));
        				monticulo.add(nodeArray[0]);
        				estPes = estimacionPesimista(c, pedidos, hijo.getK(), hijo.getCoste());
        				
        				
        				if(cota > estPes) { cota = estPes; }

        			}
        			hijo.getAsignados()[i] = false;        			
        		}
        	}
        }
     
        
        resultado += Arrays.toString(pasteleros) + "\n" + coste;
        traza += "\nTermina el algoritmo";
        traza += "\n\n----------------- RESULTADO -----------------\n\n";       
        traza += "Pasteleros asignados: " + Arrays.toString(pasteleros) + "\nCoste: " + coste;
        // Si la traza esta activa, la muestra. En otro caso solo muestra el resultado
        if(trazaOn){return traza;}
        return resultado;        
    }
    
    
    @Override
    public String toString() {
    	String c = "";
    	for(int[] arr : costes) {
        	c += Arrays.toString(arr) + "\n";
        }    	
        return "Numero pasteleros: " + this.n + "\nTipos de pasteles: " + this.m +  "\nPedidos: " + Arrays.toString(pedidos) + "\ncostes asociados: \n"+c;
    }

}

//0-2-1-3-4


//1-3-2-4-5
//20
