/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTransporte;

import controladorTransporte.Transporte;
import controladorTransporte.Variable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Rafael
 */
public class TransporteFrame extends JFrame{
    private JLabel datos;
    private JLabel x;
    private JLabel y;
    private JLabel uno;
    private JLabel dos;
    private JLabel tres;
    private JTextField of;
    private JTextField dem;
    private JButton btnOk;
    private JButton calc;
    private JPanel pnl;
    private JPanel pnl2;
    private JPanel pnl3;
    private JPanel pnl4;
    private JPanel pnlbtCa;
    private JPanel pnlF;
    private PnlFuentes fuentes;
    private PnlDestinos dest;
    private MatrizPanel mat;
    private String[] metodo = {"","Esquina Noreste","Salto de Piedra"};
    private JComboBox ListaMetd;
    private JLabel lblInst;
    
    
    public TransporteFrame(){
        super("Problema Transporte");
        super.setSize(650,250);
        //super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        
        datos = new JLabel("Ingresa los datos que se requieren: ");
        uno = new JLabel("Oferta: ");
        dos = new JLabel("Demanda: ");
        tres = new JLabel("Matriz de costos: ");
        x = new JLabel("Destinos: ");
        of = new JTextField(4);
        y = new JLabel("Fuentes: ");
        dem = new JTextField(4);
        btnOk = new JButton("Aceptar");
        calc = new JButton("Calcular");
        pnl = new JPanel();
        pnl2 = new JPanel();
        pnl3 = new JPanel();
        pnl4 = new JPanel();
        pnlbtCa = new JPanel();
        lblInst = new JLabel("Seleccione método: ");
        ListaMetd = new JComboBox(metodo);
        ListaMetd.setBackground(Color.PINK);
        Imagen ok = new Imagen("/img/ok.png");
        Imagen equal = new Imagen("/img/equal.png");
        //fuentes = new PnlFuentes(1);
        //fuentes.setVisible(false);
        //dest = new PnlDestinos(1);
       // dest.setVisible(false);
        
        
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                of.getText();
                if(isNumeric(of.getText()) && isNumeric(dem.getText())){
                    Integer a = Integer.parseInt(of.getText());
                    Integer b = Integer.parseInt(dem.getText());
                    double x;
                    
                    Transporte c = new Transporte(a, b);

                    /*System.out.println(c.getStockSize());
                    for (int i = 0; i < c.getStockSize(); i++){
                        x = scanner.nextDouble();
                        c.setStock(x, i);
                    }*/
 
                    //PrincipalFrame.super.remove(pnl2);
                    fuentes = null;
                    fuentes = new PnlFuentes(a);
                    pnl2.add(uno);
                    pnl2.add(fuentes);
                    pnl2.setVisible(true);
                    TransporteFrame.super.add(pnl2,BorderLayout.WEST);
                    
                    dest = null;
                    dest = new PnlDestinos(b);
                    pnl3.add(dos);
                    pnl3.add(dest);
                    TransporteFrame.super.add(pnl3, BorderLayout.CENTER);
                    
                    mat = null;
                    mat = new MatrizPanel(a,b);
                    pnl4.add(tres);
                    pnl4.add(mat);
                    TransporteFrame.super.add(pnl4, BorderLayout.EAST);
                    
                    equal.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            int[] dataF = fuentes.getValores();
                            int[] dataD = dest.getValores();
                            int[][] dataM = mat.getValores();
                            
                            for(int i =0;i<c.getStockSize();i++){
                                c.setStock(dataF[i], i);
                            }
                            for(int i=0; i<c.getRequiredSize();i++){
                                c.setRequired(dataD[i], i);
                            }
                            for(int i=0;i<c.getStockSize();i++){
                                for(int j=0;j<c.getRequiredSize();j++){
                                    c.setCost(dataM[i][j], i, j);
                                }
                            }
                            
                            switch(ListaMetd.getSelectedIndex()){
                                case 0:
                                    JOptionPane.showMessageDialog(null, "Seleccione un caso");
                                    break;
                                case 1:
                                    c.noroeste();
                                    /*for(Variable t: c.feasible){
                                        System.out.println(t);        
                                    }*/
                                    /*for(Variable t: c.getFeasible()){
                                        System.out.println(t);   
                                        JOptionPane.showMessageDialog(null,t);
                                    }*/
                                    JOptionPane.showMessageDialog(null,c.getFeasible());
                                    System.out.println(c.getSolution());
                                    JOptionPane.showMessageDialog(null,"El costo es: "+c.getSolution());
                                    break;
                                    
                                case 2:
                                    c.menorCosto();
                                    for(Variable t: c.getFeasible()){
                                        System.out.println(t);        
                                    }
                                    JOptionPane.showMessageDialog(null,c.getFeasible());
                                    System.out.println(c.getSolution());
                                    JOptionPane.showMessageDialog(null,"El costo es: "+c.getSolution());
                                    //c.getSolution();
                                    break;
                                default:
                            }
                            
                            /*for(int i=0;i<c.getStockSize();i++){
                                for(int j=0;j<c.getRequiredSize();j++){
                                    System.out.println(dataM[i][j]);
                                }
                            }*/
                            
                            
                            
                        }
                    });
                    pnlbtCa.add(lblInst);
                    pnlbtCa.add(ListaMetd);
                    pnlbtCa.add(equal,BorderLayout.SOUTH);
                    TransporteFrame.super.add(pnlbtCa,BorderLayout.SOUTH);
                    TransporteFrame.super.setSize(650, 300);
                    
                    
                    
                    /*dest.setVisible(true);
                    PrincipalFrame.super.remove(dest);
                    dest = null;
                    dest = new PnlDestinos(b);
                    pnl3.add(dest);
                    PrincipalFrame.super.add(pnl3,BorderLayout.CENTER);*/
                    
                    
                    
                    
                    //PrincipalFrame.super.repaint();
                    
                    /*System.out.println("Please enter the requirements:");
                    for (int i = 0; i < c.getRequiredSize(); i++){
                        x = scanner.nextDouble();
                        c.setRequired(x, i);
                    }

                    System.out.println("Please enter the transportation costs:");
                    for (int i = 0; i < c.getStockSize(); i++)
                        for (int j = 0; j < c.getRequiredSize(); j++) {
                            x = scanner.nextDouble();
                            c.setCost(x, i, j);
                        }
                    c.northWestCorner();
                    //test.leastCostRule();
                    for(Variable t: c.getFeasible()){
                        System.out.println(t);        
                    }
                    System.out.println("Target function: " + c.getSolution());
                    JOptionPane.showMessageDialog(null, "El costo es: "+c.getSolution());*/
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Verfique los datos ingresados");
                }
                ok.setEnabled(false);
            }
        });
        
       
        pnl.add(y);
        pnl.add(of);
        pnl.add(x);
        pnl.add(dem);
        pnl.add(ok);
        super.add(pnl,BorderLayout.NORTH);
        super.add(pnl2,BorderLayout.WEST);
        super.add(pnl3, BorderLayout.CENTER);
        super.add(pnl4, BorderLayout.EAST);
        super.add(pnlbtCa, BorderLayout.SOUTH);
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }
    
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}

    }
}
