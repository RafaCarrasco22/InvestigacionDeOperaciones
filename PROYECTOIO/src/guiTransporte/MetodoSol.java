/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTransporte;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Rafael
 */
public class MetodoSol extends JDialog{
    
    private JLabel uno;
    private JLabel dos;
    private JLabel tres;
    private JPanel pnl2;
    private JPanel pnl3;
    private JPanel pnl4;
    private MatrizPanel mat;
    private PnlDestinos dest;
    private PnlFuentes fuen;
    
    public MetodoSol(){
        setTitle("Método Solución");
        uno = new JLabel("");
        dos = new JLabel("Capacidad Destinos");
        tres = new JLabel("Requerimientos");
        
        pnl2= new JPanel();
        pnl3= new JPanel();
        pnl4= new JPanel();
        
        fuen = new PnlFuentes(WIDTH);
        
       
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }
}
