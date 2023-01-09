/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
        init_size();
        init_arr();
        smile_panel_init();
    }
    
    private void init_arr()
    {
        int n = pnlarr.length;
        
        Random rand = new Random();
        
        int panelW = jPanel3.getWidth();
        int panelH = jPanel3.getHeight();
        
        /* image declaration start */
        ImageIcon bombImg = new ImageIcon("bomb.png"); // load the image to a imageIcon
        Image image = bombImg.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        bombImg = new ImageIcon(newimg);  // transform it back
        
        ImageIcon flagImg = new ImageIcon("flag.png"); // load the image to a imageIcon
        image = flagImg.getImage(); // transform it 
        newimg = image.getScaledInstance(20, 20,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        flagImg = new ImageIcon(newimg);  // transform it back
        /* image declaration end */
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                pnlarr[i][j] = new MyPanel();
                jPanel3.add(pnlarr[i][j]);
                pnlarr[i][j].setBounds((panelW - n * 25) / 2 + j * 25, (panelH - n * 25) / 2 + i * 25, 25, 25);
                pnlarr[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                javax.swing.JLabel field = new javax.swing.JLabel();
                pnlarr[i][j].add(field);
                field.setVisible(false);
                final int kpi = i, kpj = j;
                final ImageIcon finalFlag = flagImg;
                pnlarr[i][j].addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent me)
                    {
                        if (me.getButton() == me.BUTTON1)
                        {
                            if (pnlarr[kpi][kpj].isVisited()) return;
                            if (check_if_won()) JOptionPane.showMessageDialog(null, "Congrats, you won!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                            if (!pnlarr[kpi][kpj].is_bomb())
                                panelClickedAtActionPerformed(kpi, kpj);
                            else
                                bombClickedActionPerformed(kpi, kpj);
                        }
                        else if (me.getButton() == me.BUTTON3)
                        {
                            if (pnlarr[kpi][kpj].isVisited()) return;
                            if (pnlarr[kpi][kpj].isFlagged())
                            {
                                ((javax.swing.JLabel)(pnlarr[kpi][kpj].getComponent(0))).setIcon(null);
                                pnlarr[kpi][kpj].getComponent(0).setVisible(false);
                                ((javax.swing.JLabel)(pnlarr[kpi][kpj].getComponent(0))).setText(pnlarr[kpi][kpj].getNum() + "");
                                pnlarr[kpi][kpj].setFlagged(false);
                                return;
                            }
                            pnlarr[kpi][kpj].getComponent(0).setVisible(true);
                            ((javax.swing.JLabel)(pnlarr[kpi][kpj].getComponent(0))).setIcon(finalFlag);
                            ((javax.swing.JLabel)(pnlarr[kpi][kpj].getComponent(0))).setText("");
                            pnlarr[kpi][kpj].setFlagged(true);
                        }
                        else if (me.getButton() == me.BUTTON2)
                        {
                            System.out.println("in");
                        }
                    }
                });
            }
        }
        
        int maxMines;
        if (n < 10) maxMines = 10;
        else if (n < 24) maxMines = 40;
        else maxMines = 99;
        for (int i = 0; i < maxMines; ++i)
        {
            int a, b;
            do
            {
                a = rand.nextInt(n);
                b = rand.nextInt(n);
            }
            while (pnlarr[a][b].is_bomb());
            pnlarr[a][b].set_bomb(true);
            ((javax.swing.JLabel)pnlarr[a][b].getComponent(0)).setIcon(bombImg);
        } // setting up bomb fields
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                int num = 0;
                if (!pnlarr[i][j].is_bomb())
                {
                    if (j < n - 1 && pnlarr[i][j + 1].is_bomb()) num++;
                    if (i < n - 1 && pnlarr[i + 1][j].is_bomb()) num++;
                    if (j < n - 1 && i < n - 1 && pnlarr[i + 1][j + 1].is_bomb()) num++;
                    if (j > 0 && pnlarr[i][j - 1].is_bomb()) num++;
                    if (i > 0 && pnlarr[i - 1][j].is_bomb()) num++;
                    if (j > 0 && i > 0 && pnlarr[i - 1][j - 1].is_bomb()) num++;
                    if (j > 0 && i < n - 1 && pnlarr[i + 1][j - 1].is_bomb()) num++;
                    if (i > 0 && j < n - 1 && pnlarr[i - 1][j + 1].is_bomb()) num++;
                    javax.swing.JLabel field = ((javax.swing.JLabel)pnlarr[i][j].getComponent(0));
                    if (num != 0) field.setText(num + "");
                    pnlarr[i][j].setNum(num);
                }
            }
        } // setting up field numbers
    }
    
    private void init_size()
    {
        try
        {
            int n = Integer.parseInt(JOptionPane.showInputDialog(this, "Hello", "Input grid size"));
            pnlarr = new MyPanel[n][n];
        }
        catch (java.lang.NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please input a number", "Incorrect input", JOptionPane.ERROR_MESSAGE);
            init_size();
        }
    }
    
    private void smile_panel_init()
    {
        pnlSmile = new javax.swing.JPanel();
        jPanel2.add(pnlSmile);
        pnlSmile.setBounds((jPanel2.getWidth() - jPanel2.getHeight()) / 2, 5, jPanel2.getHeight() - 5, jPanel2.getHeight() - 10);
        pnlSmile.setBorder(BorderFactory.createRaisedBevelBorder());
        
        ImageIcon smileImg = new ImageIcon("smiley_face.png"); // load the image to a imageIcon
        Image image = smileImg.getImage(); // transform it 
        Image newimg = image.getScaledInstance(50, 48,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        smileImg = new ImageIcon(newimg);  // transform it back
        
        ImageIcon unsmileImg = new ImageIcon("unsmiley_face.png"); // load the image to a imageIcon
        image = unsmileImg.getImage(); // transform it 
        newimg = image.getScaledInstance(50, 48,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        unsmileImg = new ImageIcon(newimg);  // transform it back
        
        javax.swing.JLabel lbl = new javax.swing.JLabel();
        pnlSmile.add(lbl);
        lbl.setIcon(smileImg);
        lbl.setVisible(true);
        final ImageIcon smile = smileImg;
        final ImageIcon unsmile = unsmileImg;
        pnlSmile.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me1)
            {
                pnlSmile.setBorder(BorderFactory.createLoweredBevelBorder());
                ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(unsmile);
                init_arr();
            }
            public void mouseReleased(MouseEvent me2)
            {
                pnlSmile.setBorder(BorderFactory.createRaisedBevelBorder());
                ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(smile);
            }
        });
        
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 953, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 547, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelClickedAtActionPerformed(int i, int j)
    {
        int n = pnlarr.length;
        if (i >= n || j >= n || j < 0 || i < 0) return;
        if (pnlarr[i][j].isVisited()) return;
        if (pnlarr[i][j].isFlagged()) return;
        pnlarr[i][j].setBorder(BorderFactory.createLoweredBevelBorder());
        pnlarr[i][j].getComponent(0).setVisible(true);
        pnlarr[i][j].setVisited(true);
        if (check_if_won()) JOptionPane.showMessageDialog(null, "Congrats, you won!", "Game Over", JOptionPane.PLAIN_MESSAGE);
        if (pnlarr[i][j].getNum() != 0 || pnlarr[i][j].is_bomb())
            return;
        panelClickedAtActionPerformed(i + 1, j);
        panelClickedAtActionPerformed(i, j + 1);
        panelClickedAtActionPerformed(i - 1, j);
        panelClickedAtActionPerformed(i, j - 1);
        panelClickedAtActionPerformed(i - 1, j - 1);
        panelClickedAtActionPerformed(i + 1, j + 1);
        panelClickedAtActionPerformed(i + 1, j - 1);
        panelClickedAtActionPerformed(i - 1, j + 1);
    }
    
    private void bombClickedActionPerformed(int i, int j)
    {
        if (pnlarr[i][j].isFlagged()) return;
        pnlarr[i][j].setBackground(Color.red);
        int n = pnlarr.length;
        for (i = 0; i < n; ++i)
        {
            for (j = 0; j < n; ++j)
            {
                pnlarr[i][j].setVisited(true);
                if (!pnlarr[i][j].is_bomb()) continue;
                pnlarr[i][j].getComponent(0).setVisible(true);
                pnlarr[i][j].setBorder(BorderFactory.createLoweredBevelBorder());
            }
        }
        ImageIcon unsmileImg = new ImageIcon("unsmiley_face.png"); // load the image to a imageIcon
        Image image = unsmileImg.getImage(); // transform it 
        Image newimg = image.getScaledInstance(50, 48,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        unsmileImg = new ImageIcon(newimg);  // transform it back
        ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(unsmileImg);
    }
    
    private boolean check_if_won()
    {
        int n = pnlarr.length;
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                if (!pnlarr[i][j].isVisited() && !pnlarr[i][j].is_bomb()) return false;
            }
        }
        
        ImageIcon flagImg = new ImageIcon("flag.png"); // load the image to a imageIcon
        Image image = flagImg.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20,
                java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        flagImg = new ImageIcon(newimg);  // transform it back
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                if (pnlarr[i][j].is_bomb() && !pnlarr[i][j].isFlagged())
                {
                    pnlarr[i][j].getComponent(0).setVisible(true);
                    ((javax.swing.JLabel)(pnlarr[i][j].getComponent(0))).setIcon(flagImg);
                    ((javax.swing.JLabel)(pnlarr[i][j].getComponent(0))).setText("");
                    pnlarr[i][j].setFlagged(true);
                }
            }
        }
        return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
    private MyPanel[][] pnlarr;
    private javax.swing.JPanel pnlSmile;
}
