import java.util.ArrayList;


/**
 * Clase que recibe los datos pre procesados de entrada y 
 * ejecuta el algoritmo principal: Problema de la mochila 
 * con objetos fraccionables
 * 
 * @author Isabel Manzaneque, imanzaneq3@alumno.uned.es
 */
public class Greedy{
    
    private int numeroObjetos;
    private int capacidad;
    private ArrayList<Integer> pesos;   
    private ArrayList<Integer> valores;

    public Greedy(int numeroObjetos,int capacidad, ArrayList<Integer> pesos, ArrayList<Integer> valores){
		this.numeroObjetos = numeroObjetos;
        this.capacidad = capacidad;
        this.pesos = pesos;
        this.valores = valores;
	}       
    
    
    /**
     * Funcion principal de quickSort
     */
    public void quickSort(int low, int high){
    	
        if (low < high) {             	
        	int pi = partition(low, high);
            quickSort(low, pi - 1);            
            quickSort(pi + 1, high);
        }
    }
    
    
    /**
     * Pivota colocando los elementos menores que el pivote 
     * a su izquierda y los mayores a su derecha
     */
    public int partition(int low, int high){
  
        float pivote = (float)valores.get(high)/(float)pesos.get(high);
        int i = (low - 1);
          
        for (int j = low; j <= high - 1; j++) {  
            // Si el elemento actual es mayor que el pivote, 
        	// incrementa el indice del elemento menor
            if ((float)valores.get(j)/(float)pesos.get(j) > pivote){
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }
    
    
    /**
     * Función auxiliar para intercambiar dos elementos de dos vectores 
     */
    public void swap(int i, int j){
    	
        int tempV = valores.get(i);
        int tempP = pesos.get(i);

        valores.set(i,valores.get(j));
        pesos.set(i,pesos.get(j));
        valores.set(j, tempV);
        pesos.set(j, tempP);       	
    }
            
        
    /**
     * Ejecuta el algoritmo voraz Mochila Fraccionable sobre los datos de entrada. Si
     * trazaOn es true, muestra la traza del algoritmo
     */
    public String mochilaFraccionable(boolean trazaOn){  

        ArrayList<Double> fracciones = new ArrayList<>();
        ArrayList<Double> ViPi = new ArrayList<>();
        String resultado = "";
        double beneficio = 0;
        int peso = 0;        
        int iteracion = 0;        
        
 
        String traza = "\n----------------- PARAMETROS -----------------\n\n" +
                       "Capacidad de la mochila: " + capacidad + "\nNumero de elementos: " + numeroObjetos; 
        
        for (int i = 0; i < numeroObjetos; i++) {
            ViPi.add((double)valores.get(i)/(double)pesos.get(i));
        }
        
        traza += "\nAntes de ordenar:    p = "+ pesos + ", v = " + valores + ", Vi/Pi = " + ViPi;
        
        // Ordenar objetos en orden no creciente de Vi/Pi
        quickSort(0, numeroObjetos-1);

        for (int i = 0; i < numeroObjetos; i++) {
            ViPi.set(i,(double)valores.get(i)/(double)pesos.get(i));
        }
        
        traza += "\nDespues de ordenar:  p = "+ pesos + ", v = " + valores + ", Vi/Pi = " + ViPi +       
                 "\n\n----------------- ITERACIONES -----------------\n\n";   

        for(int i = 0; i < numeroObjetos; i++){
        	fracciones.add(0.0);
        }
        
        // Mientras haya espacio en la mochila y queden objetos por meter         
        while(peso < capacidad && iteracion < numeroObjetos){
        	
            if(peso + pesos.get(iteracion) <= capacidad){
            	// El peso del objeto no se supera la capacidad, 
            	// se guarda entero y aporta su beneficio entero
            	fracciones.set(iteracion,1.0);
                peso += pesos.get(iteracion);
                beneficio += valores.get(iteracion);
                resultado += pesos.get(iteracion) + " " + fracciones.get(iteracion) + " " + valores.get(iteracion) + "\n";
            }else{
            	// El peso supera la capacidad, se guarda la fraccion
            	// que quepa y aporta esa fraccion del beneficio 
            	fracciones.set(iteracion, ((double)(capacidad-peso)/(double)pesos.get(iteracion)));
                peso = capacidad;
                beneficio += valores.get(iteracion)*fracciones.get(iteracion);
                resultado += pesos.get(iteracion) + " " + fracciones.get(iteracion) + " " + valores.get(iteracion)*fracciones.get(iteracion) + "\n";
            }
            
            traza += "Iteracion numero " + (+1) +" - Objeto seleccionado:\n" +
                     "   - Objeto " + (iteracion+1) + ", Fraccion: " + fracciones.get(iteracion) + 
            		 ", Peso aportado: " + (pesos.get(iteracion)*fracciones.get(iteracion)) + 
            		 ", Valor aportado: " + (valores.get(iteracion)*fracciones.get(iteracion)) + "\n";
            
            iteracion++;
        }
        
        traza += "\n------------------ RESULTADO ------------------\n\n" +
                  "Beneficio total en mochila: " + beneficio + "\nFraccion de cada objeto: " + fracciones + "\n";     
        
        // Si la traza esta activa, la muestra. 
        // En otro caso solo muestra el resultado
        if(trazaOn){return traza;}
        return resultado + beneficio;        
    }
    
}
