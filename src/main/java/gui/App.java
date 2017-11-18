package gui;

import model.Watershed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class App extends JFrame{

    private SchemaPanel schema;
    private WaterContainerPanel city, dam, farm1, farm2;
    private JButton launchSim;

    private static final int width = 600, height = 600;
    private static final int widthButton = 100, heightButton = 30;

    public App() {
        init();
    }

    private void init() {

        //Main Frame
        setTitle("Watershed");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        //Schema Panel
        schema = new SchemaPanel();
        schema.setBackground(Color.WHITE);
        schema.setLocation(0, 50);
        schema.setLayout(null);

        //Water Container Panels

        city = new WaterContainerPanel();
        city.setBackground(Color.GRAY);
        city.setLocation(0, 0);
        city.setLayout(null);

        dam = new WaterContainerPanel();
        dam.setBackground(Color.CYAN);
        dam.setLocation(150, 0);
        dam.setLayout(null);

        farm1 = new WaterContainerPanel();
        farm1.setBackground(Color.ORANGE);
        farm1.setLocation(300, 0);
        farm1.setLayout(null);

        farm2 = new WaterContainerPanel();
        farm2.setBackground(Color.RED);
        farm2.setLocation(450, 0);
        farm2.setLayout(null);

        JPanel waterContainers = new JPanel();
        waterContainers.setLayout(null);
        waterContainers.setSize(600, 200);
        waterContainers.setLocation(0, 350);
        waterContainers.add(city);
        waterContainers.add(dam);
        waterContainers.add(farm1);
        waterContainers.add(farm2);


        //Launch Button
        launchSim = new JButton("Launch");

        launchSim.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Watershed watershed = new Watershed();

            }

        });

        launchSim.setSize(widthButton, heightButton);
        launchSim.setLocation((width/2)-(widthButton/2), 560);
        launchSim.setLayout(null);


        //Wrap elements in main JFrame
        add(schema);
        add(waterContainers);
        add(launchSim);

        setVisible(true);

    }

    public static void main(String[] args) {
        App a = new App();
    }


    //Nested classes
    private class SchemaPanel extends JPanel {

        private static final int width = 600, height = 300;

        public SchemaPanel() {
            setSize(width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(20));

            //main river
            g2.draw(new Line2D.Float(width/10, height/2, width-(width/10), height/2));

            //city river
            g2.draw(new Line2D.Float(width/4, (height/2)-5, width/3, height/5));

            //down river
            g2.draw(new Line2D.Float(width/2, (height/2)+5, (width/2)-(width/6), height-(height/5)));

            //farm1 river
            g2.draw(new Line2D.Float((width/2)-(width/8), height-(height/4), (5*width/9), height-(height/4)));

            //farm2 river
            g2.draw(new Line2D.Float(width-(width/3), (height/2)-5, width-(width/4), height/5));

            g.setColor(Color.GRAY);
            g.drawOval((width/3)-10, (height/5)-10, 20, 20);

            g.setColor(Color.CYAN);
            g.drawOval((width/3)-10, (height/2)-10, 20, 20);

            g.setColor(Color.ORANGE);
            g.drawOval((5*width/9)-10, height-(height/4)-10, 20, 20);

            g.setColor(Color.RED);
            g.drawOval(width-(width/4)-10, height/5-10, 20, 20);

        }

    }

    private class WaterContainerPanel extends JPanel {

        private static final int width = 150, height = 200;

        public WaterContainerPanel() {
            setSize(width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }

    }

}
