import javax.swing.*;
import java.awt.*;

public class gameframe extends JFrame {
    int screenwidth = 800;
    int screenheight = 800;
    int gamescreen_width = 600;
    int controlscreen_width = screenwidth - gamescreen_width;

    JFrame gameframe;
    JPanel gamePanel;
    JPanel controlPanel;
    JPanel inputPanel;

    String value = null;

    JToggleButton [] buttons = new JToggleButton[10];
    JTextField [][] textFields = new JTextField[9][9];
    int [][] gameboardFinal = new int[9][9];

    gameframe(){
        gameframe = new JFrame("Suduko Game");
        gamePanel = new JPanel();
        inputPanel = new JPanel();
        controlPanel = new JPanel();

        controlPanel.setPreferredSize(new Dimension(controlscreen_width, screenheight));
        controlPanel.setBackground(new Color(173, 139, 115));
        controlPanel.setLayout(null);
        controlPanel.setVisible(true);

        inputPanel.setPreferredSize(new Dimension(gamescreen_width, screenheight-650));
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(173,139,115));
        inputPanel.setVisible(true);

        gamePanel.setPreferredSize(new Dimension(gamescreen_width, screenheight));
        gamePanel.setBackground(new Color(206, 171, 147));
        gamePanel.setLayout(new GridLayout(9,9,2,2));
        gamePanel.setVisible(true);

        gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameframe.setSize(screenwidth, screenheight);
        gameframe.setLocationRelativeTo(null);
        gameframe.setLayout(new BorderLayout());
        gameframe.setResizable(false);
        addTextfields();
        boxStyle1(0,0);
        boxStyle2(0,5);
        boxStyle1(0,8);

        boxStyle1(8,0);
        boxStyle2(8,5);
        boxStyle1(8,8);

        boxStyle2(5,0);
        boxStyle1(5,5);
        boxStyle2(5,8);
        addButton();
        addActionListenerBTN();

        gameframe.add(controlPanel, BorderLayout.EAST);
        gameframe.add(inputPanel, BorderLayout.SOUTH);
        gameframe.add(gamePanel, BorderLayout.WEST);
        gameframe.setVisible(true);
    }

    public void addClues(){
        sudukoBoardGenerator.getGenerateBoard(gameboardFinal);
        sudukoBoardGenerator.getReadyBoard(gameboardFinal);
    }
    public void addTextfields(){
        addClues();
        for (int i = 0; i < textFields.length; i++){
            for (int j = 0; j < textFields.length; j++){
                JTextField temp = new JTextField("" + gameboardFinal[i][j]);
                isZero(temp);
                textFields[i][j] = temp;
                gamePanel.add(textFields[i][j]);
            }
        }
    }
    public void addButton(){
        int x = 20;
        int y = 50;
        for (int i = 1; i < buttons.length; i++){
            JToggleButton temp = new JToggleButton("" + i);
            buttons[i] = temp;
            buttons[i].setBounds(x, y, 50,50);
            x = x + 70;
            inputPanel.add(buttons[i]);
        }
        JToggleButton erase = new JToggleButton("erase");
        erase.setBounds(650, 37, 80,80);
        buttons[0] = erase;
        inputPanel.add(erase);
    }

    public void addActionListenerBTN(){
        for (JToggleButton button : buttons) {
            JToggleButton temp;
            temp = button;
            temp.addActionListener(e -> {
                if (temp.isSelected()) {
                    value = temp.getText();
                    disableInactiveBtn();
                    System.out.println(value);
                }else if(!temp.isSelected()){
                    value = null;
                    enableInactiveBtn();
                }
            });
        }
    }

    public void disableInactiveBtn(){
        for (JToggleButton b : buttons){
            b.setEnabled(b.getText().equals(value));
        }
    }
    public void enableInactiveBtn(){
        for (JToggleButton b : buttons){
            b.setEnabled(true);
        }
    }

    private void isZero(JTextField temp) {
        if (temp.getText().equals("0")){
            temp.setText("");
        }
    }
    private boolean notZero(JTextField temp){
        return !temp.getText().equals("");
    }


    public void boxStyle1(int row, int col){
        int lRow = row - row % 3;
        int lCol = col - col % 3;

        for (int i = lRow; i < lRow + 3;i++){
            for (int j = lCol; j < lCol + 3; j++){
                JTextField temp;
                temp = textFields[i][j];
                if (notZero(temp)){
                    temp.setEditable(false);
                    temp.setFocusable(false);
                    temp.setBackground(new Color(227, 202, 165));
                    temp.setFont(new Font("SansSerif", Font.BOLD, 50));
                }else{
                    temp.setBackground(new Color(227, 202, 165));
                    temp.setFont(new Font("SansSerif", Font.BOLD, 30));
                }
                temp.setCaretColor(new Color(227, 202, 165));
                temp.setForeground(new Color(86,45,15));
                temp.setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }
    public void boxStyle2(int row, int col){
        int lRow = row - row % 3;
        int lCol = col - col % 3;
        for (int i = lRow; i < lRow+3; i++){
            for (int j = lCol; j < lCol+3; j++){
                JTextField temp;
                temp = textFields[i][j];
                if (notZero(temp)){
                    temp.setFocusable(false);
                    temp.setEditable(false);
                    temp.setBackground(new Color(255, 251, 233));
                    temp.setFont(new Font("SansSerif", Font.BOLD, 50));
                }else {
                    temp.setBackground(new Color(255, 251, 233));
                    temp.setFont(new Font("SansSerif", Font.BOLD, 30));
                }
                temp.setCaretColor(new Color(255, 251, 233));
                temp.setHorizontalAlignment(JTextField.CENTER);
                temp.setForeground(new Color(86, 45, 15));
            }
        }
    }



    public static void main(String[] args) {
        new gameframe();
    }
}