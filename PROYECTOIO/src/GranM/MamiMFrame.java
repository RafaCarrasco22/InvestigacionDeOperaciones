/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GranM;

import Simplex.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author House
 */
public class MamiMFrame extends JFrame{
    //GuiVars
    private JButton btnAceptar;
    private JButton btnRegresar;
    private JTable JTablaUno;
    private JScrollPane jScrollPane1;
    private JTextField txtVariables;
    private JTextField txtRestricciones;
    private JLabel lblVariables;
    private JLabel lblRestricciones;
    private JComboBox tipoProblema;
    private JPanel pnlNorth;
    private JLabel lblTipoProblema;
    private JTable jTablaSolucion;
    private JScrollPane jScrollPane2;
    //Resolución variables
    double Matriz[][] = null;
    int restricciones = 0, variables = 0, iter = 0, iteracion = 0;
    Object array[] = null;
    Object EtiquetaX[] = null, EtiquetaY[] = null;
    static DecimalFormat df = new DecimalFormat("#.##");
    private JComboBox comboBox;
    private String menorIgualQue = "<=";
    private String mayorIgualQue = ">=";
    private String igualQue = "=";
    private boolean opc;
    
    
    public MamiMFrame(){
        super("Gran M");
        super.setSize(new Dimension(700, 600));
        super.setLayout(new BorderLayout());
        //super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        //Panel North para txt y lbls
        pnlNorth = new JPanel(new FlowLayout());
        pnlNorth.setBackground(Color.WHITE);
        //pnlNorth.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        //Iniciamos los txt y lbls
        lblRestricciones = new JLabel("Restricciones "); 
        lblVariables = new JLabel("Variables: ");
        lblTipoProblema = new JLabel("Tipo de problema: ");
        txtRestricciones = new JTextField(3);
        txtVariables = new JTextField(3);
        //Combo Box para elegir tipo de problema
        tipoProblema = new javax.swing.JComboBox();
        //tipoProblema.setBackground(new java.awt.Color(255, 51, 51));
        tipoProblema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Max", "Min" }));
        
        //Botones aceptar y resolver
        btnAceptar = new JButton("Ok");
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 try {
            //txtAreaEcuaciones.setText(""); //No lo ocupo
            //resultados.setText("");         //No lo ocupo
            
            restricciones = Integer.parseInt(txtRestricciones.getText()); //Si se ocupa
            variables = Integer.parseInt(txtVariables.getText());        //Yes
            //jTablaSolucion.setModel(new DefaultTableModel());           //No
            //btnSolucion.setEnabled(true);                               //No
            //btnPasoSolucion.setEnabled(false);                          //No

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setRowCount(restricciones + 1);
            modelo.setColumnCount(variables + restricciones + 3);
            array = new Object[variables + restricciones + 2];
            Object[] array2 = new Object[variables + restricciones + 3];
            array[0] = "";
            array2[0] = "";
            EtiquetaX = new Object[variables + restricciones];
            for (int i = 1; i < array.length - 1; i++) {
                if (i < variables + 1) {
                    array[i] = "X" + i;
                    array2[i] = "X" + i;
                    EtiquetaX[i - 1] = "X" + i;
                } else {
                    array[i] = "S" + (i - variables);
                    array2[i] = "S" + (i - variables);
                    EtiquetaX[i - 1] = "S" + (i - variables);
                }
            }
            //----------------------
            array[array.length - 1] = "VB";
            array2[array2.length - 2] = "Símbolo";
            array2[array2.length - 1] = "VB";
            modelo.setColumnIdentifiers(array2);
            //---------------------
            EtiquetaY = new Object[restricciones + 1];
            for (int i = 0; i < restricciones; i++) {
                modelo.setValueAt("R" + (i + 1), i, 0);
                modelo.setValueAt(menorIgualQue, i, modelo.getColumnCount() - 2);
                EtiquetaY[i] = "S" + (i + 1);
            }
            modelo.setValueAt("Z", restricciones, 0);
            EtiquetaY[restricciones] = "Z";

            JTablaUno.setModel(modelo);
            comboBox = new JComboBox();
            comboBox.addItem(mayorIgualQue);
            comboBox.addItem(menorIgualQue);
            comboBox.addItem(igualQue);
            TableColumn signo = JTablaUno.getColumnModel().getColumn(JTablaUno.getColumnCount() - 2);
            signo.setCellEditor(new DefaultCellEditor(comboBox));

            for (int i = 0; i < restricciones; i++) {
                JTablaUno.removeColumn(JTablaUno.getColumnModel().getColumn(variables + 1));
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MamiMFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
            }
        });
        btnRegresar = new JButton("Solve");
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
            //lbl_Iteraccion.setText("");
            opc = tipoProblema.getSelectedIndex() == 0;

            for (int i = 0; i < JTablaUno.getModel().getRowCount(); i++) {

                for (int j = 0; j < restricciones; j++) {
                    JTablaUno.getModel().setValueAt("0", i, variables + 1 + j);
                }

                if (i != JTablaUno.getModel().getRowCount() - 1) {//sino es z
                    Object o = JTablaUno.getModel().getValueAt(i, JTablaUno.getModel().getColumnCount() - 2);
                    if (o.equals(menorIgualQue) || o.equals(mayorIgualQue)) {
                        JTablaUno.getModel().setValueAt("1", i, variables + 1 + i);
                    } else {
                        JTablaUno.getModel().setValueAt("0", i, variables + 1 + i);
                    }
                }
            }

            JTablaUno.getModel().setValueAt("0", JTablaUno.getModel().getRowCount() - 1, JTablaUno.getModel().getColumnCount() - 1);

            //mostrarEcuaciones();

            DefaultTableModel modeloSolucion = new DefaultTableModel();
            Matriz = new double[restricciones + 1][restricciones + variables + 1];
            for (int i = 0; i < (restricciones + 1); i++) {
                boolean sw = ((i == restricciones) && opc)
                        || JTablaUno.getModel().getValueAt(i, JTablaUno.getModel().getColumnCount() - 2) != null
                        && (JTablaUno.getModel().getValueAt(i, JTablaUno.getModel().getColumnCount() - 2).equals(mayorIgualQue));

                for (int j = 0; j < (restricciones + variables + 1); j++) {
                    double d;
                    if (j == (restricciones + variables)) {//para saltarse el signo
                        d = convert(JTablaUno.getModel().getValueAt(i, j + 2).toString());
                        Matriz[i][j] = sw ? d * -1 : d;
                    } else {
                        d = convert(JTablaUno.getModel().getValueAt(i, j + 1).toString());
                        Matriz[i][j] = sw ? d * -1 : d;
                    }

                }
            }

            while (ComprobarResultado() != true) {
                imprimirMatriz();
                EtiquetaY[FilaPivote()] = EtiquetaX[ColumnaPivote()];
                NuevaTabla(FilaPivote(), ColumnaPivote());
                modeloSolucion.setColumnCount(restricciones + variables + 2);
                modeloSolucion.setRowCount(restricciones + 1);
                //--------------------------
                modeloSolucion.setColumnIdentifiers(array);
                //---------------------------
                for (int i = 0; i < (restricciones + 1); i++) {
                    modeloSolucion.setValueAt(EtiquetaY[i], i, 0);
                    for (int j = 0; j < (restricciones + variables + 1); j++) {
                        modeloSolucion.setValueAt(Matriz[i][j], i, j + 1);
                    }
                }
                
                jTablaSolucion.setModel(modeloSolucion);
                
                
                MamiMFrame.this.repaint();
                iteracion++;
            }
            imprimirMatriz();
            //btnSolucion.setEnabled(false);
            //btnPasoSolucion.setEnabled(true);
            //mostrarResultados();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MamiMFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
            }
        });
        //Agregamos todo esto a Panel North
        pnlNorth.add(lblVariables);
        pnlNorth.add(txtVariables);
        pnlNorth.add(lblRestricciones);
        pnlNorth.add(txtRestricciones);
        pnlNorth.add(btnAceptar);
        pnlNorth.add(lblTipoProblema);
        pnlNorth.add(tipoProblema);
        pnlNorth.add(btnRegresar);
        
        //JTABLEs
        JTablaUno = new JTable();
        //JTablaUno.setBackground(new java.awt.Color(255, 222, 173));
        //JTablaUno.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 0, 153)));
        /*JTablaUno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "                X1", "                X2", "                X3", "                X4"
            }
        ));*/
        //JTablaUno.setGridColor(new java.awt.Color(0, 0, 204));
        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(JTablaUno);
        btnAceptar = new JButton();
        jTablaSolucion = new JTable();
        jScrollPane2 = new JScrollPane();
        jScrollPane2.setViewportView(jTablaSolucion);
        
        MamiMFrame.this.add(jScrollPane2, BorderLayout.SOUTH);
        super.add(pnlNorth, BorderLayout.NORTH);
        super.add(jScrollPane1, BorderLayout.CENTER);
        super.setVisible(true);
    }
    
    
    //MÉTODOS SIMPLEX
     
    
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
