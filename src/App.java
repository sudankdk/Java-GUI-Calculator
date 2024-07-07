import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class App {
    private JFrame frame;
    private JTextField tfOutput;
    private JButton btnAdd, btnSub, btnMultiply, btnDivide, btnEquals, btnSquare, btnClear, btnBackspace, btnHistory;
    private JButton[] numberButtons;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private ArrayList<String> history = new ArrayList<>();

    public App() {
        frame = new JFrame("Calculator");
        frame.setSize(350, 550);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(32, 32, 32));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(32, 32, 32));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        tfOutput = new JTextField("0", 20);
        tfOutput.setEditable(false);
        tfOutput.setFont(new Font("Arial", Font.PLAIN, 36));
        tfOutput.setHorizontalAlignment(JTextField.RIGHT);
        tfOutput.setBackground(new Color(32, 32, 32));
        tfOutput.setForeground(Color.WHITE);
        tfOutput.setBorder(new EmptyBorder(20, 10, 20, 10));
        mainPanel.add(tfOutput);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(32, 32, 32));

        String[] buttonLabels = {
            "C", "\u232B", "x²", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", "H"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            customizeButton(button);
            buttonPanel.add(button);

            switch (label) {
                case "C": btnClear = button; button.addActionListener(e -> clear()); break;
                case "\u232B": btnBackspace = button; button.addActionListener(e -> backspace()); break;
                case "x²": btnSquare = button; button.addActionListener(e -> square()); break;
                case "÷": btnDivide = button; button.addActionListener(e -> operatorClicked('/')); break;
                case "×": btnMultiply = button; button.addActionListener(e -> operatorClicked('*')); break;
                case "-": btnSub = button; button.addActionListener(e -> operatorClicked('-')); break;
                case "+": btnAdd = button; button.addActionListener(e -> operatorClicked('+')); break;
                case "=": btnEquals = button; button.addActionListener(e -> calculate()); break;
                case "H": btnHistory = button; button.addActionListener(e -> showHistory()); break;
                default:
                    if (Character.isDigit(label.charAt(0))) {
                        int num = Integer.parseInt(label);
                        button.addActionListener(e -> numberClicked(num));
                    } else if (label.equals(".")) {
                        button.addActionListener(e -> addDecimalPoint());
                    }
                    break;
            }
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(70, 70));

        if (button.getText().matches("[0-9]|\\.")) {
            button.setBackground(new Color(60, 60, 60));
            button.setForeground(Color.WHITE);
        } else if (button.getText().equals("=")) {
            button.setBackground(new Color(255, 149, 0));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(212, 212, 210));
            button.setForeground(Color.BLACK);
        }
    }

    private void numberClicked(int num) {
        
        if (tfOutput.getText().equals("0")) {
            tfOutput.setText(String.valueOf(num));
        } else {
            tfOutput.setText(Integer.toString(num));
        }
    }

    private void operatorClicked(char op) {
        num1 = Double.parseDouble(tfOutput.getText());
        operator = op;
        tfOutput.setText("0");
    }

    private void calculate() {
        num2 = Double.parseDouble(tfOutput.getText());
        String expression = num1 + " " + operator + " " + num2 + " = ";
        switch (operator) {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    tfOutput.setText("Error");
                    return;
                }
                break;
        }
        expression += result;
        history.add(expression);
        tfOutput.setText(String.valueOf(result));
        
    }

    private void square() {
        double num = Double.parseDouble(tfOutput.getText());
        double squared = num * num;
        history.add(num + " ^ 2 = " + squared);
        tfOutput.setText(String.valueOf(squared));
    }

    private void clear() {
        tfOutput.setText("0");
        num1 = num2 = result = 0;
        operator = '\0';
        history.clear();
    }

    private void backspace() {
        String currentText = tfOutput.getText();
        if (currentText.length() > 0) {
            tfOutput.setText(currentText.substring(0, currentText.length() - 1));
        }
        if (tfOutput.getText().isEmpty()) {
            tfOutput.setText("0");
        }
    }

    private void addDecimalPoint() {
        if (!tfOutput.getText().contains(".")) {
            tfOutput.setText(tfOutput.getText() + ".");
        }
    }

    private void showHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No history available.", "History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder historyText = new StringBuilder();
            for (String entry : history) {
                historyText.append(entry).append("\n");
            }
            JOptionPane.showMessageDialog(frame, historyText.toString(), "History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
