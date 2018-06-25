package controladorTransporte;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import guiTransporte.TransporteFrame;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Rafael
 */
public class Transporte {
    
    double []reqs;
    double []stock;

    public double[] getRequired() {
        return reqs;
    }

    public double[] getStock() {
        return stock;
    }

    public double[][] getCost() {
        return cost;
    }
    double [][]cost;
    LinkedList<Variable> factible = new LinkedList<Variable>();

    public LinkedList<Variable> getFeasible() {
        return factible;
    }
    
    int stockSize;    

    public int getStockSize() {
        return stockSize;
    }

    public int getRequiredSize() {
        return requiredSize;
    }
    int requiredSize;    
    
    public Transporte(int stockSize, int requiredSize ){
        this.stockSize = stockSize;
        this.requiredSize = requiredSize;
        
        stock = new double[stockSize];
        reqs = new double[requiredSize];
        cost = new double[stockSize][requiredSize];
        
        for(int i=0; i < (requiredSize + stockSize -1); i++)
            factible.add(new Variable());

    }
    
    public void setStock(double value, int index){
        stock[index] = value;
    }
    
    public void setRequired(double value, int index){
        reqs[index] = value;        
    }
    
    
    public void setCost(double value, int stock, int required){
        cost[stock][required] = value;
    }
    
    /**
     * inicializa la lista de soluciones factibles utilizando la esquina noroeste
      * @return tiempo transcurrido
     */
    
    public double noroeste() {
        long start = System.nanoTime();
        
        double min;        
        int k = 0; //contador de soluciones
        
        //isSet es responsable de anotar las celdas que se han asignado
        boolean [][]isSet = new boolean[stockSize][requiredSize];        
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                    isSet[i][j] = false;
        
        //el bucle for es responsable de iterar en la forma 'noroeste'
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                if(!isSet[i][j]){
                    
                    //asignando acciones de la manera adecuada
                    min = Math.min(reqs[j], stock[i]);
                    
                    factible.get(k).setRequired(j);
                    factible.get(k).setStock(i);
                    factible.get(k).setValue(min);
                    k++;

                    reqs[j] -= min;
                    stock[i] -= min;
                    
                    //asigancion de los valores correctos
                    if(stock[i] == 0)
                        for(int l = 0; l < requiredSize; l++)
                            isSet[i][l] = true;                    
                    else
                        for(int l = 0; l < stockSize; l++)
                            isSet[l][j] = true;
                }
        return (System.nanoTime() - start) * 1.0e-9;
    }
    
    /**
    * inicializa la lista de soluciones factibles utilizando la regla de menor costo
      *
      * difiere de la regla de esquina noroeste por el orden de células candidatas
      * que está determinado por el costo correspondiente
      *
      * @return double: tiempo transcurrido
     */
    
    public double menorCosto() {
        long start = System.nanoTime();

        double min;        
        int k = 0; //contador de soluciones
        
        //isSet es responsable de anotar las celdas que se han asignado
        boolean [][]isSet = new boolean[stockSize][requiredSize];        
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                    isSet[i][j] = false;
        
        int i = 0, j = 0;
        Variable minCost = new Variable();
        
        //este ciclo busca las celdas con menor costo
        while(k < (stockSize + requiredSize - 1)){
            
            minCost.setValue(Double.MAX_VALUE);            
            //obtencion de la celda de menor costo        
            for (int m = 0;  m < stockSize; m++)
                for (int n = 0; n < requiredSize; n++)
                    if(!isSet[m][n])
                        if(cost[m][n] < minCost.getValue()){
                            minCost.setStock(m);
                            minCost.setRequired(n);
                            minCost.setValue(cost[m][n]);
                        }            
            
            i = minCost.getStock();
            j = minCost.getRequired();            
            
            //asigancion de los valores correctos
            min = Math.min(reqs[j], stock[i]);

            factible.get(k).setRequired(j);
            factible.get(k).setStock(i);
            factible.get(k).setValue(min);
            k++;

            reqs[j] -= min;
            stock[i] -= min;
            
            //asignando valores nulos en la fila / columna eliminada
            if(stock[i] == 0)
                for(int l = 0; l < requiredSize; l++)
                    isSet[i][l] = true;                    
            else
                for(int l = 0; l < stockSize; l++)
                    isSet[l][j] = true;

        }
        
        return (System.nanoTime() - start) * 1.0e-9;    
            
    }
    
    public double getSolution(){
        
        double result = 0;
        for(Variable x: factible){
            result += x.getValue() * cost[x.getStock()][x.getRequired()];
        }
        
        return result;
    
    }
}

