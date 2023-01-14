/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Font;
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
        init_size();
        init_bomb_counter();
        init_arr();
        init_smile_panel();
        init_timer();
    }
    
    private void init_arr()
    {
        int n = pnlarr.length;
        int m = pnlarr[0].length;
        
        Random rand = new Random();
        
        int panelW = jPanel3.getWidth();
        int panelH = jPanel3.getHeight();
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                pnlarr[i][j] = new MyPanel();
                jPanel3.add(pnlarr[i][j]);
                pnlarr[i][j].setBounds((panelW - m * 25) / 2 + j * 25, (panelH - n * 25) / 2 + i * 25, 25, 25);
                pnlarr[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                javax.swing.JLabel field = new javax.swing.JLabel();
                pnlarr[i][j].add(field);
                field.setVisible(false);
                final int kpi = i, kpj = j;
                pnlarr[i][j].addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent me)
                    {
                        if (me.getButton() == me.BUTTON1)
                        {
                            if (pnlarr[kpi][kpj].isVisited()) 
                            {
                                clickSurrounding(kpi, kpj);
                                return;
                            }
                            pnlTimer.start();
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
                                int c = Integer.parseInt(((javax.swing.JLabel)(pnlBombCount.getComponent(0))).getText()) + 1;
                                ((javax.swing.JLabel)(pnlBombCount.getComponent(0))).setText(Integer.toString(c));
                                return;
                            }
                            pnlarr[kpi][kpj].getComponent(0).setVisible(true);
                            ((javax.swing.JLabel)(pnlarr[kpi][kpj].getComponent(0))).setIcon(Images.FLAG);
                            ((javax.swing.JLabel)(pnlarr[kpi][kpj].getComponent(0))).setText("");
                            pnlarr[kpi][kpj].setFlagged(true);
                            int c = Integer.parseInt(((javax.swing.JLabel)(pnlBombCount.getComponent(0))).getText()) - 1;
                            ((javax.swing.JLabel)(pnlBombCount.getComponent(0))).setText(Integer.toString(c));
                        }
                        else if (me.getButton() == me.BUTTON2)
                        {
                            System.out.println("in");
                        }
                    }
                    public void mouseReleased(MouseEvent re)
                    {
                        unpressUnvisited();
                    }
                });
            }
        }
        
        int maxMines = GameSettingsScreen.mines;
        
        for (int i = 0; i < maxMines; ++i)
        {
            int a, b;
            do
            {
                a = rand.nextInt(n);
                b = rand.nextInt(m);
            }
            while (pnlarr[a][b].is_bomb());
            pnlarr[a][b].set_bomb(true);
            ((javax.swing.JLabel)pnlarr[a][b].getComponent(0)).setIcon(Images.BOMB);
        } // setting up bomb fields
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                int num = 0;
                if (!pnlarr[i][j].is_bomb())
                {
                    if (j < m - 1 && pnlarr[i][j + 1].is_bomb()) num++;
                    if (i < n - 1 && pnlarr[i + 1][j].is_bomb()) num++;
                    if (j < m - 1 && i < n - 1 && pnlarr[i + 1][j + 1].is_bomb()) num++;
                    if (j > 0 && pnlarr[i][j - 1].is_bomb()) num++;
                    if (i > 0 && pnlarr[i - 1][j].is_bomb()) num++;
                    if (j > 0 && i > 0 && pnlarr[i - 1][j - 1].is_bomb()) num++;
                    if (j > 0 && i < n - 1 && pnlarr[i + 1][j - 1].is_bomb()) num++;
                    if (i > 0 && j < m - 1 && pnlarr[i - 1][j + 1].is_bomb()) num++;
                    javax.swing.JLabel field = ((javax.swing.JLabel)pnlarr[i][j].getComponent(0));
                    if (num != 0) field.setText(num + "");
                    pnlarr[i][j].setNum(num);
                }
            }
        } // setting up field numbers
        
        javax.swing.JLabel lbl = ((javax.swing.JLabel)pnlBombCount.getComponent(0));
        lbl.setText(Integer.toString(maxMines));
    }
    
    private void init_size()
    {
        pnlarr = new MyPanel[GameSettingsScreen.height][GameSettingsScreen.width];
    }
    
    private void init_smile_panel()
    {
        pnlSmile = new javax.swing.JPanel();
        jPanel2.add(pnlSmile);
        pnlSmile.setBounds((jPanel2.getWidth() - jPanel2.getHeight()) / 2, 5, jPanel2.getHeight() - 5, jPanel2.getHeight() - 10);
        pnlSmile.setBorder(BorderFactory.createRaisedBevelBorder());
        
        javax.swing.JLabel lbl = new javax.swing.JLabel();
        pnlSmile.add(lbl);
        lbl.setIcon(Images.SMILE);
        lbl.setVisible(true);
        pnlSmile.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me1)
            {
                pnlSmile.setBorder(BorderFactory.createLoweredBevelBorder());
                ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(Images.UNSMILE);
                init_arr();
                pnlTimer.refresh();
            }
            public void mouseReleased(MouseEvent me2)
            {
                pnlSmile.setBorder(BorderFactory.createRaisedBevelBorder());
                ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(Images.SMILE);
            }
        });
        
    }
    
    private void init_timer()
    {
        pnlTimer = new PanelTimer();
        jPanel2.add(pnlTimer);
        pnlTimer.setBounds(jPanel2.getWidth() - jPanel2.getHeight() - 20, 3, jPanel2.getHeight(), jPanel2.getHeight() - 5);
        pnlTimer.setBackground(Color.pink);
        pnlTimer.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlTimer.stop();
    }
    
    private void init_bomb_counter()
    {
        pnlBombCount = new javax.swing.JPanel();
        jPanel2.add(pnlBombCount);
        pnlBombCount.setBounds(10, 3, jPanel2.getHeight(), jPanel2.getHeight() - 5);
        pnlBombCount.setBackground(Color.pink);
        pnlBombCount.setBorder(BorderFactory.createLineBorder(Color.black));
        javax.swing.JLabel lbl = new javax.swing.JLabel();
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 50));
        lbl.setForeground(Color.red);
        pnlBombCount.add(lbl);
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
        btnGame = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
            .addGap(0, 1135, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );

        btnGame.setText("Game");
        btnGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGameActionPerformed(evt);
            }
        });

        btnHelp.setText("Help");
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHelp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit))
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGame)
                    .addComponent(btnHelp)
                    .addComponent(btnExit))
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGameActionPerformed
        new GameSettingsScreen(jPanel2, this).setVisible(true);
    }//GEN-LAST:event_btnGameActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void panelClickedAtActionPerformed(int i, int j)
    {
        int n = pnlarr.length;
        int m = pnlarr[0].length;
        if (i >= n || j >= m || j < 0 || i < 0) return;
        if (pnlarr[i][j].isVisited()) return;
        if (pnlarr[i][j].isFlagged()) return;
        pnlarr[i][j].setBorder(BorderFactory.createLoweredBevelBorder());
        pnlarr[i][j].getComponent(0).setVisible(true);
        pnlarr[i][j].setVisited(true);
        check_if_won();
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
        int m = pnlarr[0].length;
        for (i = 0; i < n; ++i)
        {
            for (j = 0; j < m; ++j)
            {
                pnlarr[i][j].setVisited(true);
                if (!pnlarr[i][j].is_bomb()) continue;
                javax.swing.JLabel lbl = (javax.swing.JLabel)(pnlarr[i][j].getComponent(0));
                lbl.setIcon(Images.BOMB);
                lbl.setText("");
                lbl.setVisible(true);
                pnlarr[i][j].setBorder(BorderFactory.createLoweredBevelBorder());
            }
        }
        ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(Images.UNSMILE);
        pnlTimer.stop();
    }
    
    private void clickSurrounding(int i, int j)
    {
        int n = pnlarr.length;
        int m = pnlarr[0].length;
        
        int howManyFlagged = 0;
        if (j < m - 1 && pnlarr[i][j + 1].isFlagged()) howManyFlagged++;
        if (i < n - 1 && pnlarr[i + 1][j].isFlagged()) howManyFlagged++;
        if (j < m - 1 && i < n - 1 && pnlarr[i + 1][j + 1].isFlagged()) howManyFlagged++;
        if (j > 0 && pnlarr[i][j - 1].isFlagged()) howManyFlagged++;
        if (i > 0 && pnlarr[i - 1][j].isFlagged()) howManyFlagged++;
        if (j > 0 && i > 0 && pnlarr[i - 1][j - 1].isFlagged()) howManyFlagged++;
        if (j > 0 && i < n - 1 && pnlarr[i + 1][j - 1].isFlagged()) howManyFlagged++;
        if (i > 0 && j < m - 1 && pnlarr[i - 1][j + 1].isFlagged()) howManyFlagged++;
        // counts how many flags surrond the panel
        
        if (howManyFlagged == pnlarr[i][j].getNum())
        {
            if (j < m - 1 && !pnlarr[i][j + 1].isVisited())
            {
                if (pnlarr[i][j + 1].is_bomb())
                    bombClickedActionPerformed(i, j + 1);
                else
                    panelClickedAtActionPerformed(i, j + 1);
            }
            if (i < n - 1 && !pnlarr[i + 1][j].isVisited())
            {
                if (pnlarr[i + 1][j].is_bomb())
                    bombClickedActionPerformed(i + 1, j);
                else
                    panelClickedAtActionPerformed(i + 1, j);
            }
            if (j < m - 1 && i < n - 1 && !pnlarr[i + 1][j + 1].isVisited())
            {
                if (pnlarr[i + 1][j + 1].is_bomb())
                    bombClickedActionPerformed(i + 1, j + 1);
                else
                    panelClickedAtActionPerformed(i + 1, j + 1);
            }
            if (j > 0 && !pnlarr[i][j - 1].isVisited()) 
            {
                if (pnlarr[i][j - 1].is_bomb())
                    bombClickedActionPerformed(i, j - 1);
                else
                    panelClickedAtActionPerformed(i, j - 1);
            }
            if (i > 0 && !pnlarr[i - 1][j].isVisited()) 
            {
                if (pnlarr[i - 1][j].is_bomb())
                    bombClickedActionPerformed(i - 1, j);
                else
                    panelClickedAtActionPerformed(i - 1, j);
            }
            if (j > 0 && i > 0 && !pnlarr[i - 1][j - 1].isVisited()) 
            {
                if (pnlarr[i - 1][j - 1].is_bomb())
                    bombClickedActionPerformed(i - 1, j - 1);
                else
                    panelClickedAtActionPerformed(i - 1, j - 1);
            }
            if (j > 0 && i < n - 1 && !pnlarr[i + 1][j - 1].isVisited()) 
            {
                if (pnlarr[i + 1][j - 1].is_bomb())
                    bombClickedActionPerformed(i + 1, j - 1);
                else
                    panelClickedAtActionPerformed(i + 1, j - 1);
            }
            if (i > 0 && j < m - 1 && !pnlarr[i - 1][j + 1].isVisited()) 
            {
                if (pnlarr[i - 1][j + 1].is_bomb())
                    bombClickedActionPerformed(i - 1, j + 1);
                else
                    panelClickedAtActionPerformed(i - 1, j + 1);
            }
        } // clicks every surrounding panel if the number of surrounding flags equals the panel number
        else
        {
            if (j < m - 1 && !pnlarr[i][j + 1].isFlagged()) 
                pnlarr[i][j + 1].setBorder(BorderFactory.createLoweredBevelBorder());
            if (i < n - 1 && !pnlarr[i + 1][j].isFlagged())
                pnlarr[i + 1][j].setBorder(BorderFactory.createLoweredBevelBorder());
            if (j < m - 1 && i < n - 1 && !pnlarr[i + 1][j + 1].isFlagged())
                pnlarr[i + 1][j + 1].setBorder(BorderFactory.createLoweredBevelBorder());
            if (j > 0 && !pnlarr[i][j - 1].isFlagged())
                pnlarr[i][j - 1].setBorder(BorderFactory.createLoweredBevelBorder());
            if (i > 0 && !pnlarr[i - 1][j].isFlagged())
                pnlarr[i - 1][j].setBorder(BorderFactory.createLoweredBevelBorder());
            if (j > 0 && i > 0 && !pnlarr[i - 1][j - 1].isFlagged())
                pnlarr[i - 1][j - 1].setBorder(BorderFactory.createLoweredBevelBorder());
            if (j > 0 && i < n - 1 && !pnlarr[i + 1][j - 1].isFlagged())
                pnlarr[i + 1][j - 1].setBorder(BorderFactory.createLoweredBevelBorder());
            if (i > 0 && j < m - 1 && !pnlarr[i - 1][j + 1].isFlagged())
                pnlarr[i - 1][j + 1].setBorder(BorderFactory.createLoweredBevelBorder());
        }
    }
    
    private void unpressUnvisited()
    {
        int n = pnlarr.length;
        int m = pnlarr[0].length;
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                if (!pnlarr[i][j].isVisited())
                    pnlarr[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }
    }
    
    private boolean check_if_won()
    {
        int n = pnlarr.length;
        int m = pnlarr[0].length;
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                if (!pnlarr[i][j].isVisited() && !pnlarr[i][j].is_bomb()) return false;
            }
        }
        
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                if (pnlarr[i][j].is_bomb() && !pnlarr[i][j].isFlagged())
                {
                    pnlarr[i][j].getComponent(0).setVisible(true);
                    ((javax.swing.JLabel)(pnlarr[i][j].getComponent(0))).setIcon(Images.FLAG);
                    ((javax.swing.JLabel)(pnlarr[i][j].getComponent(0))).setText("");
                    pnlarr[i][j].setFlagged(true);
                }
            }
        }
        ((javax.swing.JLabel)(pnlSmile.getComponent(0))).setIcon(Images.COOL_SMILE);
        ((javax.swing.JLabel)(pnlBombCount.getComponent(0))).setText("0");
        pnlTimer.stop();
        
        return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGame;
    private javax.swing.JButton btnHelp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
    private MyPanel[][] pnlarr;
    private javax.swing.JPanel pnlSmile;
    private PanelTimer pnlTimer;
    private javax.swing.JPanel pnlBombCount;
}
