/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorTransporte;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Formatter;

/**
 *
 * @author Rafael
 */
public class Variable {  
        
    private int stock;
    private int requer;
    private double value;
    
    public Variable(){
        this.stock = 0;
        this.requer = 0;
    }
    
    public Variable(int stock, int required){
        this.stock = stock;
        this.requer = required;
    }
    
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRequired() {
        return requer;
    }

    public void setRequired(int required) {
        this.requer = required;
    }

    public double getValue() {
        return value;
    }
   
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        Formatter f = new Formatter();
        f.format("x[%d,%d]=%f", this.stock+1, this.requer+1, this.value);
        return f.toString();
    }
}
