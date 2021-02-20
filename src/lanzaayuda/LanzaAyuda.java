/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanzaayuda;

import static com.sun.glass.ui.Cursor.setVisible;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author Miguel
 */
public class LanzaAyuda extends JFrame implements ActionListener, ItemListener{
    
    JTextArea output;
    JScrollPane scrollPane;
    ImageIcon bgimage = new ImageIcon("src/img/BG.png");
    
    public JMenuBar createMenuBar() {
        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        //Build second menu in the menu bar.
        JMenu menu = new JMenu("Ayuda");
        menu.setMnemonic(KeyEvent.VK_N);
        menuBar.add(menu);
       
        HelpSet hs = obtenFicAyuda();
        HelpBroker hb = hs.createHelpBroker();
        
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Contenido de Ayuda");
        menu.add(menuItem);
        
        JMenuItem menuItem2 = new JMenuItem();
        menuItem2.setText("About");
        menu.add(menuItem2);
        
        hb.enableHelpOnButton(menuItem,"Main",hs);
        
        menuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desarrollado por Miguel Á. Ligero Lozano");
            }
        });
        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        hb.enableHelpKey(menu, "top", hs);
        
        return menuBar;
    }
    
    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Action event detected."
                   + "\n"
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")";
        output.append(s + "\n");
        output.setCaretPosition(output.getDocument().getLength());
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
         JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                   + "\n"
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")"
                   + "\n"
                   + "    New state: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
        output.append(s + "\n");
        output.setCaretPosition(output.getDocument().getLength());
    }
    
    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    } 
    
    private static void createAndShowGUI () {
        
        //Create and set up the window.
        JFrame frame = new JFrame("Aplicación Swing con Ayuda | LanzaAyuda");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = (JPanel)frame.getContentPane();
        
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("src/img/Bg.png"));
        panel.add(label);
        	
        //Create and set up the content pane.
        LanzaAyuda ayuda = new LanzaAyuda();
        frame.setJMenuBar(ayuda.createMenuBar());
        
        //Display the window.
        frame.setSize(450, 260);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        
        
    }
    

    public static void main (String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    
    public HelpSet obtenFicAyuda(){
        
        try {
            File file = new File("src/help/helpset.hs");
            URL url = file.toURI().toURL();
            //Creamos el Objeto Helpset
            HelpSet hs = new HelpSet(null,url);
            return hs;
        } 
        catch (Exception ex){
            
            JOptionPane.showMessageDialog(null,"No se ha podido encontrar el HelpSet");
            return null;
        }
        
    }
}
