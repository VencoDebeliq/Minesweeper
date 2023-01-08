/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;

/**
 *
 * @author Ventsislav Peychev
 */
public class MyPanel extends javax.swing.JPanel {
    private boolean bomb;
    private int num;
    private boolean visited;
    private boolean flagged;

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setNum(int num) {
        if (num > 0 && num < 9)
        {
            this.num = num;
            setForeground(num);
        }
    }

    public int getNum() {
        return num;
    }

    public boolean is_bomb() {
        return bomb;
    }

    public void set_bomb(boolean bomb) {
        this.bomb = bomb;
    }
    
    public MyPanel()
    {
        super();
        set_bomb(false);
        setVisited(false);
        setNum(0);
        setFlagged(false);
    }
    
    public void setForeground(int n)
    {
        try
        {
            switch (n)
            {
                case 1:
                    getComponent(0).setForeground(Color.blue);
                    break;
                case 2:
                    getComponent(0).setForeground(new Color(0, 128, 0)); // green
                    break;
                case 3:
                    getComponent(0).setForeground(Color.red);
                    break;
                case 4:
                    getComponent(0).setForeground(new Color(128, 0, 128)); // purple
                    break;
                case 5:
                    getComponent(0).setForeground(new Color(128, 0, 0)); // maroon (red)
                    break;
                case 6:
                    getComponent(0).setForeground(new Color(64, 224, 208)); // Turquoise (blue)
                    break;
                case 7:
                    getComponent(0).setForeground(Color.black);
                    break;
                case 8:
                    getComponent(0).setForeground(Color.gray);
                    break;
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.err.println(e);
        }
    }
    
}
