/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechecker;

import java.util.*;

import java.net.URL;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import chinesechecker.*;
//////////////////////////////////////////////////////////////////////////////
// ChineseCheckers class

public class ChineseCheckers extends JFrame {

    //////////////////////////////////////////////////////////////////////////
    // main
    public static void main(String[] args) {
        new ChineseCheckers();
    } // end of main

    //////////////////////////////////////////////////////////////////////////
    // ChineseCheckers constructor
    public ChineseCheckers() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Chinese Checkers");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                CCState state = new CCState();
                frame.add(new Board(state));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }
}
