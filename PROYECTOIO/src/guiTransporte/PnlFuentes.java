/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTransporte;

import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Rafael
 */
public class PnlFuentes extends JPanel{
    
    private GridLayout layoutMatriz;
    private JFormattedTextField[] txtCeldas;
    private int size;
    
    public PnlFuentes(int size){
        super();
        this.size = size;
        this.layoutMatriz = new GridLayout(this.size, this.size);
        this.setLayout(layoutMatriz);
        this.txtCeldas = new JFormattedTextField[size];
        NumberFormat formato = NumberFormat.getInstance();
        formato.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(formato);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(Integer.MIN_VALUE);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        for (int i = 0; i < txtCeldas.length; i++) {
           
                this.txtCeldas[i] = new JFormattedTextField(formatter);
                this.txtCeldas[i].setColumns(4);
                super.add(txtCeldas[i]);
            
        }
    }
    
    public void setSize(int size) {
        this.size = size;
    }

    public int[] getValores() {
        int [] resultado = new int[txtCeldas.length];
        for (int i = 0; i < txtCeldas.length; i++) {
                resultado[i] = Integer.valueOf(txtCeldas[i].getText());
            
        }
        return resultado;
    }
    
}
