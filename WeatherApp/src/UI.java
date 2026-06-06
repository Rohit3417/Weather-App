package WeatherApp.src;

import javax.swing.*;
// import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {

    static public void BuildUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Weather App");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // By default centre
        frame.setLocationRelativeTo(null);

        // JLabel HeadingLabel = new JLabel("----WEATHER INFO----");

        JLabel headingLabel = new JLabel("-----Weather Info-----");

        headingLabel.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headingLabel.setBackground(Color.decode("#f4c064"));
        headingLabel.setForeground(Color.decode("#73664e"));
        frame.add(headingLabel, BorderLayout.NORTH);// NORTH assigns the label to top section
        JPanel Parent = new JPanel();
        Parent.setLayout(new BoxLayout(Parent, BoxLayout.Y_AXIS)); // this parent panel will contain input Panel and
                                                                   // output Panel

        JPanel InputPanel = new JPanel(new FlowLayout());// Flow layout keeps the components organized
        Parent.setBackground(Color.decode("#f4c064"));
        // Creating components of the panel
        JLabel text = new JLabel("Enter City name : ");
        JTextField CityName = new JTextField(20); // 20 columns wide
        JButton search = new JButton("Search 🔍");

        // Adding components to panel
        InputPanel.add(text);
        InputPanel.add(CityName);
        InputPanel.add(search);

        JTextArea output = new JTextArea(" ");
        output.setFont(new Font("Arial", Font.BOLD, 24));
        output.setEditable(false);
        output.setLineWrap(true);
        output.setBackground(Color.decode("#ffe7bf"));
        output.setForeground(Color.decode("#73664e"));

        Parent.add(InputPanel);
        Parent.add(output, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(output);
        Parent.add(scrollPane, BorderLayout.CENTER);
        Parent.add(InputPanel);
        Parent.add(Box.createVerticalStrut(15)); // Adds a 15-pixel gap
        Parent.add(scrollPane);
        Parent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Top,
        // Left, Bottom, Right padding
        frame.add(Parent);
        // // Making the button do something
        search.addActionListener(e -> {
            search.setEnabled(false);
            output.setText("Fetching...");

            // SwingWorker splits the work into two threads:
            new SwingWorker<Location, Void>() {
                @Override
                // Runs on BACKGROUND thread — do heavy work here
                protected Location doInBackground() {
                    Location loc = CityData.getLocation(CityName.getText());
                    return loc != null ? Weather.getWeather(loc) : null;
                }

                @Override

                protected void done() {
                    try {
                        Location result = get();
                        output.setText(result != null ? result.toString() : "City not found.");
                    } catch (Exception ex) {
                        output.setText("Error fetching weather.");
                    }
                    search.setEnabled(true);
                }
            }.execute();
        });

        CityName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search.doClick();
            }
        });

        // MOST IMPORTANT AS BY DEFAULT IT IS FALSE AND THUS NOT SHOWN
        frame.setVisible(true);

    }
}
