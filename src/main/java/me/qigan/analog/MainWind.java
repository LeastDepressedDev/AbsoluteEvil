package me.qigan.analog;

import javax.swing.*;
import java.awt.*;

public class MainWind extends JFrame {
    public MainWind() {
        super();
        setTitle("AbsoluteEvil manager(like truly evil lol)");
        setSize(700, 400);
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        JTextPane pane = new JTextPane();
        pane.setText("This thing is COMPLETELY unfinished so you can use it to make notes, lol");
        add(pane);
    }
}
