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
public class MatrizPanel extends JPanel {

    private GridLayout layoutMatriz;
    private JFormattedTextField[][] txtCeldas;
    private int size;
    private int s;

    public void setS(int s) {
        this.s = s;
    }

    public MatrizPanel(int size, int s) {
        super();
        this.size = size;
        this.s = s;
        this.layoutMatriz = new GridLayout(this.size, this.s);
        this.setLayout(layoutMatriz);
        this.txtCeldas = new JFormattedTextField[size][s];
        NumberFormat formato = NumberFormat.getInstance();
        formato.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(formato);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(Integer.MIN_VALUE);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < s; j++) {
                this.txtCeldas[i][j] = new JFormattedTextField(formatter);
                this.txtCeldas[i][j].setColumns(4);
                super.add(txtCeldas[i][j]);
            }
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[][] getValores() {
        int [][] resultado = new int[size] [s];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < s; j++) {
                resultado[i][j] = Integer.valueOf(txtCeldas[i][j].getText());
            }
        }
        return resultado;
    }
}

