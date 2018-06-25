/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simplex;

import java.text.DecimalFormat;
import javax.swing.JComboBox;

/**
 *
 * @author Rafael
 */

public class MetodoSimplex {
    double Matriz[][] = null;
    int restricciones = 0, variables = 0, iter = 0, iteracion = 0;
    Object array[] = null;
    Object EtiquetaX[] = null, EtiquetaY[] = null;
    static DecimalFormat df = new DecimalFormat("#.##");
    private JComboBox comboBox;
    private String menorIgualQue = "<=";
    private String mayorIgualQue = ">=";
    private String igualQue = "=";
    private boolean opc;//true maximizar
    
        //MÉTODOS
    private void imprimirMatriz() {
        System.out.println("\n\n\n");
        for (int i = 0; i < restricciones + 1; i++) {
            for (int j = 0; j < restricciones + variables + 1; j++) {
                System.out.print(Matriz[i][j] + "\t");
            }
            System.out.println("");
        }
    }
    public static double convert(String s) {
        return Double.parseDouble(s);
    }
        public int ColumnaPivote() {
        int pos = 0;
        double aux = Matriz[restricciones][0];
        for (int i = 1; i < restricciones + variables; i++) {
            if (aux > Matriz[restricciones][i]) {
                aux = Matriz[restricciones][i];
                pos = i;
            }
        }
        return pos;
    }
    public int FilaPivote() {
        int columna = ColumnaPivote();  //llamamos a la columna del pivot
        double temp = 0;
        Double razon = null;
        int pos = 0;
        for (int i = 0; i < restricciones; i++) {
            double columPiv = Matriz[i][columna];
            double columSoluc = Matriz[i][variables + restricciones];
            if ((columPiv > 0 && columSoluc > 0) || (columPiv < 0 && columSoluc < 0)) {
                temp = columSoluc / columPiv;
                if ((razon == null) || temp < razon) {
                    razon = temp;
                    pos = i;
                }
            }

        }
        return pos;
    }
    
    public void NuevaTabla(int Fila, int Columna) {

        double pivote = Matriz[Fila][Columna], temp = 0;//--
        System.out.println("pivote [" + Fila + "]" + "[" + Columna + "]=" + pivote);
        for (int i = 0; i < restricciones + variables + 1; i++) {
            Matriz[Fila][i] = Matriz[Fila][i] / pivote;
        }
        for (int i = 0; i < restricciones + 1; i++) {
            temp = Matriz[i][Columna];
            for (int j = 0; j < variables + restricciones + 1; j++) {
                if (i != Fila) {
                    Matriz[i][j] = Matriz[i][j] - temp * Matriz[Fila][j];
                } else {
                    break;
                }
            }
        }
    }
    public boolean ComprobarResultado() {
        boolean result = true;//+ variables
        for (int i = 0; i < restricciones; i++) {
            if (((Matriz[restricciones][i] < 0))) {// && opc || ((Matriz[restricciones][i] > 0) && !opc)
                //if (Matriz[i][restricciones + variables] < 0) {
                result = false;
                break;
            }
        }
        return result;
    }
    private int buscarVariable(String var) {
        int pos = -1;
        for (int i = 0; i < EtiquetaY.length; i++) {
            if (EtiquetaY[i].equals(var)) {
                return i;
            }
        }
        return pos;
    }


    

}
