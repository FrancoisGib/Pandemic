package pandemic.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;

import javax.swing.*;

import pandemic.Map;
import pandemic.NoSuchTownException;
import pandemic.Town;
import pandemic.player.Player;
import pandemic.player.Role;

public class Main extends JFrame {
    public Main() {
        super("Test jFrame");
        Map map = new Map();
        try {
			map.setMapWithJSON("./json/villes48.json");
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
        catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
        ArrayList<Town> towns = map.getTowns();
        Player p = new Player("Player", Role.GLOBETROTTER);
        p.setTown(towns.get(0));
        JPanel pan = new JPanel();
        JPanel totalGui = new JPanel();
        pan.setSize(200, 720);
        pan.setLocation(0, 0);
        totalGui.setLayout(null);
        ArrayList<Town> movableTowns = new ArrayList<Town>();
        if (p.getRole() == Role.GLOBETROTTER) {
            movableTowns = map.getTowns();
        }
        else {
            movableTowns = p.getTown().getNeighbors();
        }
        for (Town t : movableTowns) {
            JButton btn = new JButton(t.getName());
            townButton instance = new townButton(this, t, p);
            btn.addActionListener(instance);
            pan.add(btn);
        }

        totalGui.add(pan);

        

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1280, 720);

        Graphs graph = new Graphs();
        graph.drawString(getGraphics(), "fhsdufdshnfdsfnsdfsd\nhdzsbdhsdshq\nhdbsqdhqsd");
        graph.setSize(WIDTH/2, HEIGHT);
        graph.setLocation(1200, 500);
        totalGui.add(graph);
        totalGui.setOpaque(true);
        this.add(totalGui);
        System.out.println(p.getTownName());
    }
    
    
    public static void main(String []args) {
        Main main = new Main();
    }
}


