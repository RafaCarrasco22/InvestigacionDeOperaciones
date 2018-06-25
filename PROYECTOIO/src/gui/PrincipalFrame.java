/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import GranM.MamiMFrame;
import Simplex.SimplexFrame;
import guiTransporte.TransporteFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import lineas.LineasFrame;

/**
 *
 * @author Rafael
 */
public class PrincipalFrame extends JFrame{
    private AboutDialog aboutDlg;
    private String[] busca = {"Lineas de Espera","Gran M","Simplex","Transporte"};
    private JComboBox ListaBuscar;
    private JButton btnFind;
    private JPanel pnlExtra;
    private JLabel txt;
    
    public PrincipalFrame(){
        super("Proyecto INVESTIGACIÓN DE OPERACIONES");
        super.setSize(430,150);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setJMenuBar(createMenu());
        
        aboutDlg = new AboutDialog(this);
        pnlExtra = new JPanel();
        
        btnFind = new JButton("GO!");
        ListaBuscar = new JComboBox(busca);
        txt = new JLabel("Método a ocupar: ");
        
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch(ListaBuscar.getSelectedIndex()){
                    case 0:
                        LineasFrame lf = new LineasFrame();
                        lf.setVisible(true);
                        break;
                    case 1:
                        //MamiMFrame gf = new MamiMFrame();
                        //gf.setVisible(true);
                        break;
                    case 2:
                        SimplexFrame sf = new SimplexFrame();
                        sf.setVisible(true);
                        break;
                    case 3:
                        TransporteFrame tf= new TransporteFrame();
                        tf.setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "ERROR");
                        break;
                    
                }
            }
        });
     
        pnlExtra.add(txt);
        pnlExtra.add(ListaBuscar);
        pnlExtra.add(btnFind);
        
        super.add(pnlExtra, BorderLayout.CENTER);
        super.setVisible(true);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
    }
    
    private JMenuBar createMenu(){
        JMenuBar menu = new JMenuBar();

        JMenu mmAiuda = new JMenu("Ayuda");
        
        JMenuItem nnAcerca = new JMenuItem("Acerca de...");
        nnAcerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        nnAcerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                aboutDlg.setVisible(true);
            }
        });
        mmAiuda.add(nnAcerca);
        menu.add(mmAiuda);
        
        return menu;
    }
}
