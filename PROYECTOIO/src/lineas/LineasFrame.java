/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineas;

import guiTransporte.Imagen;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Rafael
 */
public class LineasFrame extends JFrame{
    
    private JTextField vll;
    private JTextField vser;
    private JTextField nser;
    private JTextField desv;
    private JLabel uno;
    private JLabel dos;
    private JLabel tres;
    private JLabel cuatro;
    private JButton ok;
    private JPanel pnl;
    private JPanel pnlb;
    private Double lamba, mu, k1,s1,desv1;
    private JTextArea info;
    private Imagen btn;
    
    public LineasFrame(){
        super("Lineas de Espera");
        super.setSize(355,450);
        //super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        
        vll= new JTextField(3);
        vll.setText("0");
        vser= new JTextField(3);
        vser.setText("0");
        nser= new JTextField(3);
        nser.setText("0");
        desv= new JTextField(3);
        desv.setText("0");
        
        info = new JTextArea();
        
        uno = new JLabel("Velocidad llegadas (cliente/tiempo)");
        dos = new JLabel("Velocidad servidor (cliente/tiempo)");
        tres = new JLabel("Número de servidores                        ");
        cuatro = new JLabel("Desviación estándar                           ");
        
        pnl = new JPanel();
        pnlb = new JPanel();
        
        ok = new JButton("Resolver");
        btn = new Imagen("/img/equal.png");
        
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String a = vll.getText();
                String b = vser.getText();
                String c = nser.getText();
                String d = desv.getText();
                
                lamba = Double.parseDouble(a);
                mu = Double.parseDouble(b);
                s1 = Double.parseDouble(c);
                desv1 = Double.parseDouble(d);
                
                if(lamba >0 && mu >0 && s1 == 1  && desv1==0){
                    System.out.println("MM1");
                    Double ls = lamba/(mu-lamba);
                    Double ws = 1/(mu-lamba);
                    Double lq = (lamba*lamba)/(mu*(mu-lamba));
                    Double wq = lamba/(mu*(mu-lamba));
                    Double p = lamba/mu;
                    Double po = 1-p;
                    System.out.println("LS ="+ls+"WS="+ws+"LQ="+lq+"WQ="+wq+"P="+p+"PO="+po);
                    info.setText("SISTEMA M/M/1 \nLS="+ls+"\nWS="+ws+"\nLQ="+lq+"\nWQ="+wq+"\nP="+p*100+"%\nPO="+po*100+"%");
                }else if(lamba >0 && mu >0 && s1>1 && desv1==0){
                    System.out.println("MMS");

                    Double suma=0.0;
                    for (int i = 0; i <= (s1-1); i++) {
                        suma = suma + ((Math.pow((lamba/mu), i))/factorial(i));
                     }
                    Double po = 1/(suma+((Math.pow((lamba/mu),s1)/factorial(s1))*(1/(1-(lamba/(s1*mu))))));
                    Double ls = (mu*lamba*(Math.pow((lamba/mu), s1))*po)/((s1-1)*(Math.pow(((s1*mu)-lamba), 2)))+lamba/mu;
                    Double ws = ls/lamba;
                    Double lq = po*((Math.pow((lamba/mu), s1+1))/((factorial(s1-1))*(Math.pow((s1-(lamba/mu)), 2))));
                    Double wq = lq/lamba;
                    Double pw = (Math.pow((lamba/mu), s1)/factorial(s1))*((s1*mu)/(s1*mu-lamba))*po;
                    info.setText("SISTEMA M/M/S \nLS="+ls+"\nWS="+ws+"\nLQ="+lq+"\nWQ="+wq+"\nPO="+po*100+"%"+"\n\nSISTEMA M/M/K agregando \nPW="+pw);

                }else if(lamba>0 && mu>0 && s1==1 && desv1>0){
                     System.out.println("MG1");
                     Double p = lamba/mu;
                     Double lq = (Math.pow(lamba,2)*Math.pow(desv1, 2)+Math.pow(p,2))/(2*(1-p));
                     Double ls = lq+p;
                     Double wq = lq/lamba;
                     Double ws = wq+ (1/mu);
                     Double po = 1-p;
                     Double pw =p;

                    Double ls2 = lamba/(mu-lamba);
                    Double ws2 = 1/(mu-lamba);
                    Double lq2 = (lamba*lamba)/(mu*(mu-lamba));
                    Double wq2 = lamba/(mu*(mu-lamba));
                    Double p2 = lamba/mu;
                    Double po2 = 1-p2;
                    System.out.println("LS ="+ls2+"WS="+ws2+"LQ="+lq2+"WQ="+wq2+"P="+p2+"PO="+po2);
                    info.setText("SISTEMA M/G/1 \nP="+p+"\nLS="+ls+"\nWS="+ws+"\nLQ="+lq+"\nWQ="+wq+"\nPO="+po*100+"%"+"\nPW="+pw+"\nSISTEMA M/M/1 omitiendo la desviación estándar \nLS="+ls2+"\nWS="+ws2+"\nLQ="+lq2+"\nWQ="+wq2+"\nP="+p2*100+"%\nPO="+po2*100+"%");

                }else if  (lamba>0 && mu>0 && s1>1 && desv1>0){

                    Double suma=0.0;
                    for (int i = 0; i <= (s1-1); i++) {
                        suma = suma + ((Math.pow((lamba/mu), i))/factorial(i));
                     }
                    Double po = 1/(suma+((Math.pow((lamba/mu),s1)/factorial(s1))*(1/(1-(lamba/(s1*mu))))));
                    Double ls = (mu*lamba*(Math.pow((lamba/mu), s1))*po)/((s1-1)*(Math.pow(((s1*mu)-lamba), 2)))+lamba/mu;
                    Double ws = ls/lamba;
                    Double lq = po*((Math.pow((lamba/mu), s1+1))/((factorial(s1-1))*(Math.pow((s1-(lamba/mu)), 2))));
                    Double wq = lq/lamba;
                    Double pw = (Math.pow((lamba/mu), s1)/factorial(s1))*((s1*mu)/(s1*mu-lamba))*po;
                    info.setText("SISTEMA M/M/S omitiendo la desviación estándar \nLS="+ls+"\nWS="+ws+"\nLQ="+lq+"\nWQ="+wq+"\nPO="+po*100+"%"+"\n\nSISTEMA M/M/K agregando \nPW="+pw);

                }else if(s1<=0){
                    JOptionPane.showMessageDialog(null, "Debe de haber servidores", "Error", WIDTH);
                }else if (lamba<=0 || mu<=0){
                    JOptionPane.showMessageDialog(null, "Debe de haber tiempos de llegada", "Error", WIDTH);
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de haber servidores", "Error", WIDTH);
                }
            }
        });
        
        pnl.add(uno);
        pnl.add(vll);
        pnl.add(dos);
        pnl.add(vser);
        pnl.add(tres);
        pnl.add(nser);
        pnl.add(cuatro);
        pnl.add(desv);
        pnl.add(info);
        pnlb.add(btn);
        
        super.add(pnl, BorderLayout.CENTER);
        super.add(pnlb, BorderLayout.SOUTH);
        
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        //super.setResizable(false);
    }
    
    public int factorial (double numero) {
        if (numero==0){
            return 1;
        }else{
            return (int) (numero * factorial(numero-1));
        }
    }
}
