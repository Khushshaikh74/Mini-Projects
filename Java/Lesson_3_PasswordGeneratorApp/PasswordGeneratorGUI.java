package Lesson_3_PasswordGeneratorApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordGeneratorGUI extends JFrame {

    private PasswordGenerator passwordGenerator;

    public PasswordGeneratorGUI(){
        super("Password Generator");
        setSize(540, 570);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        passwordGenerator = new PasswordGenerator();

        addGUIComponent();
    }

    private void addGUIComponent() {

        //Title
        JLabel titleLabel = new JLabel("Password Generator");
        titleLabel.setBounds(0, 10, 540, 39);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        //PasswordOutput
        JTextField passwordOutput = new JTextField();
        passwordOutput.setEditable(false);
        passwordOutput.setFont(new Font("Dialog", Font.BOLD, 32));

        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25, 97, 479, 70);
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(passwordOutputPane);

        //password Length Label
        JLabel passwordLengthLabel = new JLabel("Password Length: ");
        passwordLengthLabel.setBounds(25, 215, 272, 39);
        passwordLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        add(passwordLengthLabel);

        //password Length Input
        JTextField passwordLengthInput = new JTextField();
        passwordLengthInput.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthInput.setBorder(BorderFactory.createLineBorder(Color.black));
        passwordLengthInput.setBounds(310, 215, 192, 39);
        add(passwordLengthInput);

        //Toggle buttons
        //1. Uppercase
        JToggleButton uppercaseBtn = new JToggleButton("Uppercase");
        uppercaseBtn.setBounds(25,302, 225, 56);
        uppercaseBtn.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(uppercaseBtn);

        //2. Lowercase
        JToggleButton lowercaseBtn = new JToggleButton("Lowercase");
        lowercaseBtn.setBounds(282, 302, 225, 56);
        lowercaseBtn.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(lowercaseBtn);

        //3. Numbers
        JToggleButton numberBtn = new JToggleButton("Numbers");
        numberBtn.setBounds(25,373, 225, 56);
        numberBtn.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(numberBtn);

        //4. Symbols
        JToggleButton symbolBtn = new JToggleButton("Symbols");
        symbolBtn.setBounds(282,373, 225, 56);
        symbolBtn.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(symbolBtn);

        //Generate Button
        JButton generateBtn = new JButton("Generate");
        generateBtn.setFont(new Font("Dialog", Font.PLAIN, 32));
        generateBtn.setBounds(155, 470, 222, 41);
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordLengthInput.getText().length() <= 0) return;
                boolean anyToggleSelected = uppercaseBtn.isSelected() ||
                                            lowercaseBtn.isSelected() ||
                                            numberBtn.isSelected() ||
                                            symbolBtn.isSelected();

                int passwordLength = Integer.parseInt(passwordLengthInput.getText());
                if(anyToggleSelected){
                    String result = passwordGenerator.generatePassword(passwordLength, uppercaseBtn.isSelected(), lowercaseBtn.isSelected(), numberBtn.isSelected(), symbolBtn.isSelected());
                    passwordOutput.setText(result);
                    passwordLengthInput.setText("");
                }
            }
        });
        add(generateBtn);
    }
}
