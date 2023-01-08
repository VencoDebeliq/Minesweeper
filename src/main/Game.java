/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Ventsislav Peychev
 */
public class Game extends javax.swing.JFrame {
    /**
     * Creates new form Game
     */
    public Game() {
        initComponents();
        setLocationRelativeTo(null);
        init_arr();
    }
    
    private void init_arr()
    {
        try
        {
            int n = Integer.parseInt(JOptionPane.showInputDialog(this, "Hello", "Input grid size"));
            pnlarr = new javax.swing.JPanel[n][n];
        }
        catch (java.lang.NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please input a number", "Incorrect input", JOptionPane.ERROR_MESSAGE);
            init_arr();
        }
        int n = pnlarr.length;
        Random rand = new Random();
        int frameH = getHeight();
        int frameW = getWidth();
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                pnlarr[i][j] = new javax.swing.JPanel();
                jPanel1.add(pnlarr[i][j]);
                pnlarr[i][j].setBounds(j * frameW / n, i * frameH / n, frameW / n, frameH / n);
                //pnlarr[i][j].setBackground(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                pnlarr[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                final int kpi = i, kpj = j;
                pnlarr[i][j].addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent me)
                    {
                        pnlarr[kpi][kpj].setBorder(BorderFactory.createLoweredBevelBorder());
                    }
                });
                
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 949, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel[][] pnlarr;
}
