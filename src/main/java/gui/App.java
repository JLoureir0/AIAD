package gui;

import model.Watershed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class App extends JFrame{

    private static String title = "Watershed";

    private static final int width = 600, height = 600;

    private JPanel optionsPanel;
    private SchemaPanel schema;
    private JPanel statsPanel;
    private JButton launchSim;

    public App() {
        init();
    }

    private void initOptionsPanel() {

        optionsPanel = new JPanel();
        optionsPanel.setSize(600, 50);
        optionsPanel.setLocation(0, 0);

        Container opts = new Container();
        opts.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel scenariosLabel = new JLabel("Scenarios");
        JSlider scenarios = new JSlider(1, 3, 1);
        scenarios.setMajorTickSpacing(1);
        scenarios.setPaintTicks(true);
        scenarios.setPaintLabels(true);

        JLabel nEpisodesLabel = new JLabel("Episodes");
        JTextField nEpisodes = new JTextField("10", 3);

        JLabel learningRateLabel = new JLabel("Learning R");
        JTextField learningRate = new JTextField("10", 3);

        JLabel discountFactorLabel = new JLabel("Discount F");
        JTextField discountFactor = new JTextField("10", 3);

        opts.add(scenariosLabel);
        opts.add(scenarios);

        opts.add(nEpisodesLabel);
        opts.add(nEpisodes);

        opts.add(learningRateLabel);
        opts.add(learningRate);

        opts.add(discountFactorLabel);
        opts.add(discountFactor);

        optionsPanel.add(opts);


        //Launch Button
        launchSim = new JButton("Launch");

        launchSim.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Watershed watershed = new Watershed(scenarios.getValue()-1,
                        Integer.parseInt(nEpisodes.getText()),
                        Integer.parseInt(learningRate.getText()),
                        Integer.parseInt(discountFactor.getText()));

            }

        });

        launchSim.setSize(100, 30);
        launchSim.setLocation((width/2)-(100/2), 560);
        launchSim.setLayout(null);

        //Add elements to main JFrame
        add(optionsPanel);
        add(launchSim);

        return;

    }

    private void initStatsPanel() {

        //Water Container Panels
        WaterContainerPanel city = new WaterContainerPanel();
        city.setBackground(Color.GRAY);

        WaterContainerPanel dam = new WaterContainerPanel();
        dam.setBackground(Color.CYAN);

        WaterContainerPanel farm1 = new WaterContainerPanel();
        farm1.setBackground(Color.ORANGE);

        WaterContainerPanel farm2 = new WaterContainerPanel();
        farm2.setBackground(Color.RED);

        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 5));
        statsPanel.setSize(600, 200);
        statsPanel.setLocation(0, 350);

        statsPanel.add(city);
        statsPanel.add(dam);
        statsPanel.add(farm1);
        statsPanel.add(farm2);


        //Add elements to main JFrame
        add(statsPanel);

        return;

    }

    private void init() {

        //Main Frame
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        initOptionsPanel();

        //Schema Panel
        schema = new SchemaPanel();
        schema.setBackground(new Color(0, 130, 0));
        schema.setLocation(0, 50);
        schema.setLayout(null);

        add(schema);

        initStatsPanel();

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
