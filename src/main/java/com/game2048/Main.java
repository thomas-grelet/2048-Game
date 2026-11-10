package com.game2048;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.game2048.controller.GameController;
import com.game2048.view.*;

public class Main {
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int choice = 0;
        boolean condition = true;
        do {
            System.out.println("\nX======================X");
            System.out.println("| Choose your View :   |");
            System.out.println("|  1. HeadlessView     |");
            System.out.println("|  2. SwingNoAnimView  |");
            System.out.println("|  3. SwingAnimView    |");
            System.out.println("|  4. Exit             |");
            System.out.println("X======================X\n");

            System.out.print("-> Your choice : ");
            choice = keyboard.nextInt();

            if ((choice > 0) && (choice < 5)) {
                condition = false;
            }
        } while (condition);

        switch (choice) {
            case 1:
                GameController gameHV = new GameController(new HeadlessView(), 4);
                gameHV.startGame();
                break;

            case 2:
                SwingUtilities.invokeLater(() -> {
                    SwingNoAnimView window = new SwingNoAnimView();
                    GameController gameSNAV = new GameController(window, 4);

                    new Thread(() -> gameSNAV.startGame()).start();
                });
                break;

            case 3:
                System.out.println("Not implemented yet.\n");
                break;

            case 4:
                System.out.println("See you next time!\n");
                break;

            default:
                break;
        }  
        keyboard.close();      
    }

}