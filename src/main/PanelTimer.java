/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author Ventsislav Peychev
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelTimer extends javax.swing.JPanel {
    private Timer timer;
    private int seconds;
    
    public PanelTimer() {
        javax.swing.JLabel label = new javax.swing.JLabel();
        Font font = new Font("Times New Roman", Font.BOLD, 50);
        add(label);
        label.setText("0");
        label.setFont(font);
        label.setForeground(Color.red);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                label.setText(Integer.toString(seconds));
            }
        });
        timer.setInitialDelay(0);
    }
    
    public void stop()
    {
        timer.stop();
    }
    
    public void restart()
    {
        refresh();
        timer.restart();
    }
    
    public void refresh()
    {
        JLabel lbl = (JLabel)(getComponent(0));
        lbl.setText("0");
        seconds = 0;
        stop();
    }
    
    public void start()
    {
        timer.start();
    }
}
