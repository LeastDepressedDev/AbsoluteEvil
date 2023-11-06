package me.qigan.abse.ant;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class InputField extends JPanel {
    
    public static class Form {
        public static final int WIDTH = 450;
    }

    public static Field0 uname = new Field0();
    public static Field1 token = new Field1();
    public static Field2 pid = new Field2();
    public static Login login1 = new Login();

    public static class Field0 extends JPanel {

        public Label label = new Label();
        public Comp comp = new Comp();

        public static class Label extends JLabel {
            public Label() {
                super("Uname: ");
                Dimension dimension = new Dimension();
                dimension.setSize(100, 40);
                setHorizontalAlignment(JLabel.LEFT);
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setPreferredSize(dimension);
            }
        }
        public static class Comp extends JTextField {
            public Comp() {
                super();
                Dimension dimension = new Dimension();
                dimension.setSize(Form.WIDTH, 40);
                setHorizontalAlignment(JLabel.LEFT);
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setPreferredSize(dimension);
            }
        }

        public Field0() {
            super();
            setBackground(Color.PINK);
            setBounds(0, 0, Form.WIDTH, 100);

            add(label);
            add(comp);
        }
    }

    public static class Field1 extends JPanel {

        public Label label = new Label();
        public Comp comp = new Comp();

        public static class Label extends JLabel {
            public Label() {
                super("Token: ");
                Dimension dimension = new Dimension();
                dimension.setSize(100, 40);
                setHorizontalAlignment(JLabel.LEFT);
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setPreferredSize(dimension);
            }
        }
        public static class Comp extends JTextField {
            public Comp() {
                super();
                Dimension dimension = new Dimension();
                dimension.setSize(Form.WIDTH, 40);
                setHorizontalAlignment(JLabel.LEFT);
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setPreferredSize(dimension);
            }
        }

        public Field1() {
            super();
            setBackground(Color.PINK);
            setBounds(0, 200, Form.WIDTH, 100);

            add(label);
            add(comp);
        }
    }

    public static class Field2 extends JPanel {

        public Label label = new Label();
        public Comp comp = new Comp();

        public static class Label extends JLabel {
            public Label() {
                super("Pid: ");
                Dimension dimension = new Dimension();
                dimension.setSize(100, 40);
                setHorizontalAlignment(JLabel.LEFT);
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setPreferredSize(dimension);
            }
        }
        public static class Comp extends JTextField {
            public Comp() {
                super();
                Dimension dimension = new Dimension();
                dimension.setSize(Form.WIDTH, 40);
                setHorizontalAlignment(JLabel.LEFT);
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setPreferredSize(dimension);
            }
        }

        public Field2() {
            super();
            setBackground(Color.PINK);
            setBounds(0, 400, Form.WIDTH, 100);

            add(label);
            add(comp);
        }
    }

    public static class Login extends JPanel {

        public JButton button = new Button();

        public static class Button extends JButton {
            public Button() {
                super();
                Font font = new Font("Sans", Font.PLAIN, 30);
                setFont(font);
                setText("Login");
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Session session = new Session(uname.comp.getText(), pid.comp.getText(), token.comp.getText(), "mojang");
                        Field sessionField = ReflectionHelper.findField(Minecraft.class, "session", "field_71449_j");
                        ReflectionHelper.setPrivateValue(Field.class, sessionField, sessionField.getModifiers() & ~Modifier.FINAL, "modifiers");
                        ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), session, "session", "field_71449_j");


                        JOptionPane.showMessageDialog(null, "Logged as " + uname.comp.getText(), "ABSE", JOptionPane.INFORMATION_MESSAGE);
                        uname.comp.setText("");
                        pid.comp.setText("");
                        token.comp.setText("");
                    }
                });
            }
        }

        public Login() {
            super();
            setBackground(Color.PINK);
            setBounds(0, 600, Form.WIDTH, 100);

            add(button);
        }
    }




    public InputField() {
        super();
        setBackground(Color.PINK);
        setBounds(0, 0, 700, 400);
        add(uname);
        add(token);
        add(pid);
        add(login1);
    }
}
