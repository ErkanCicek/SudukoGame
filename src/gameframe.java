import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class gameframe extends JFrame {
    int screenwidth = 800;
    int screenheight = 800;
    int gamescreen_width = 600;
    int controlscreen_width = screenwidth - gamescreen_width;

    JFrame gameframe;
    JPanel gamePanel;
    JPanel controlPanel;
    JPanel inputPanel;
    JPanel afterGamePanel;
    JLabel livesLabel;
    JLabel livesLabelCount;
    final Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);

    String value = null;
    int lives = 3;

    JToggleButton [] buttons = new JToggleButton[10];
    JTextField [][] textFields = new JTextField[9][9];

    gameframe(){
        afterGamePanel();
        controlPanel();
        inputPanel();
        gamePanel();
        runGame();
        gameFrame();
    }

    //Gamelogic
    private void runGame() {
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
        addMouseListener();
        cursorDef();
        disableKeyInput();
    }
    //panels
    private void gameFrame() {
        gameframe = new JFrame("Suduko Game");
        gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameframe.setSize(screenwidth, screenheight);
        gameframe.setLocationRelativeTo(null);
        gameframe.setLayout(new BorderLayout());
        gameframe.setResizable(false);


        gameframe.add(controlPanel, BorderLayout.EAST);
        gameframe.add(inputPanel, BorderLayout.SOUTH);
        gameframe.add(gamePanel, BorderLayout.WEST);
        gameframe.add(afterGamePanel);
        gameframe.setVisible(true);
    }
    private void gamePanel() {
        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(gamescreen_width, screenheight));
        gamePanel.setBackground(new Color(206, 171, 147));
        gamePanel.setLayout(new GridLayout(9,9,2,2));
        gamePanel.setVisible(true);
    }
    private void inputPanel() {
        inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(gamescreen_width, screenheight-650));
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(173,139,115));
        inputPanel.setVisible(true);
    }
    private void controlPanel() {
        controlPanel = new JPanel();
        livesLabel = new JLabel();
        livesLabel.setText("<HTML><u>LIVES</u></HTML>");
        livesLabel.setFont(new Font("Sansserif", Font.BOLD, 30));
        livesLabel.setForeground(Color.white);
        livesLabel.setBounds(55,20,100,50);

        livesLabelCount = new JLabel(Integer.toString(lives));
        livesLabelCount.setText(Integer.toString(lives));
        livesLabelCount.setFont(new Font("Sansserif", Font.BOLD, 45));
        livesLabelCount.setForeground(Color.white);
        livesLabelCount.setBounds((controlscreen_width/2)-20,60,100,50);

        controlPanel.setPreferredSize(new Dimension(controlscreen_width, screenheight));
        controlPanel.setBackground(new Color(173, 139, 115));
        controlPanel.setLayout(null);

        controlPanel.add(livesLabel);
        controlPanel.add(livesLabelCount);
        controlPanel.setVisible(true);
    }
    private void afterGamePanel() {
        afterGamePanel = new JPanel();
        afterGamePanel.setPreferredSize(new Dimension(screenwidth,screenheight));
        afterGamePanel.setLayout(null);
        afterGamePanel.setBackground(Color.black);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/death.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;
        JLabel imagedis = new JLabel(new ImageIcon(image));
        imagedis.setBounds(150,100,500,500);
        afterGamePanel.add(imagedis);
        afterGamePanel.setVisible(false);
    }


    //AddMethods
    public void addClues(){
        sudukoBoardGenerator.getGenerateBoard(sudukoBoardGenerator.tempBoard);
        int test = sudukoBoardGenerator.tempBoard[0][0];
        sudukoBoardGenerator.tempBoard[0][0] = 0;
        int counter = sudukoSolver.solver(sudukoBoardGenerator.tempBoard, sudukoSolver.counter1);
        System.out.println(counter);
        sudukoBoardGenerator.createSuduko(sudukoBoardGenerator.mainBoard);
        sudukoBoardGenerator.tempBoard[0][0] = test;
        sudukoSolver.printBoard(sudukoBoardGenerator.mainBoard);
    }
    public void addTextfields(){
        addClues();
        for (int i = 0; i < textFields.length; i++){
            for (int j = 0; j < textFields.length; j++){
                JTextField temp = new JTextField("" + sudukoBoardGenerator.mainBoard[i][j]);
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
                    showSelectedNums();
                    System.out.println(value);
                }else if(!temp.isSelected()){
                    unshowSelectedNums();
                    value = null;
                    enableInactiveBtn();
                }
            });
        }
    }
    public void addMouseListener(){
        for (int i = 0; i < textFields.length; i++) {
            for (int j = 0; j < textFields.length; j++) {
                JTextField temp;
                temp = textFields[i][j];
                int finalI = i;
                int finalJ = j;
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        if (value == null){
                            JOptionPane.showMessageDialog(null, "You need to choose a number below to input", "Choose a number!", JOptionPane.WARNING_MESSAGE);
                        }else{
                            if (!temp.isEditable()){
                                System.out.println("clue");
                            }else{
                                if (value.equals("erase")){
                                    value = "";
                                    temp.setText(value);
                                }
                                temp.setText(value);

                                try {
                                    if (Integer.parseInt(temp.getText()) != sudukoBoardGenerator.tempBoard[finalI][finalJ]){
                                        lives--;
                                        livesLabelCount.setText(Integer.toString(lives));
                                        damageTakenSound();
                                    }
                                }catch (NumberFormatException ignored){

                                }
                                if (didPlayerLose(lives)){
                                    gamePanel.setVisible(false);
                                    controlPanel.setVisible(false);
                                    inputPanel.setVisible(false);
                                    playerLoseSound();
                                    afterGamePanel.setVisible(true);

                                }
                                temp.setForeground(Color.RED);
                            }
                        }
                    }
                });
            }
        }
    }

    private void playerLoseSound() {
        File deathsound = new File("src/8d82b5_Left_4_Dead_Bill_Death_Sound_Effect.wav");
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(deathsound);
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            assert clip != null;
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
        clip.start();
    }

    private void damageTakenSound() {
        File deathsound = new File("src/8d82b5_SM64_Mario_Takes_Damage_Sound_Effect.wav");
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(deathsound);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            assert clip != null;
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        clip.start();
    }


    //Disable/Enable
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
    public void disableKeyInput(){
        for (JTextField[] textField : textFields) {
            for (int j = 0; j < textFields.length; j++) {
                textField[j].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        e.consume();
                    }
                });
            }
        }
    }

    //Boolean methods
    private void isZero(JTextField temp) {
        if (temp.getText().equals("0")){
            temp.setText("");
        }
    }
    private boolean notZero(JTextField temp){
        return !temp.getText().equals("");
    }
    private boolean didPlayerLose(int lives){
        return lives == 0;
    }

    //styling
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
    public void showSelectedNums(){
        for (JTextField[] textField : textFields) {
            for (int j = 0; j < textFields.length; j++) {
                if (textField[j].getText().equals(value)) {
                    textField[j].setForeground(Color.RED);
                }
            }
        }
    }
    public void unshowSelectedNums(){
        for (JTextField[] textField : textFields) {
            for (int j = 0; j < textFields.length; j++) {
                if (textField[j].getText().equals(value)) {
                    textField[j].setForeground(new Color(86, 45, 15));
                }
            }
        }
    }
    public void cursorDef(){
        for (JTextField[] textField : textFields) {
            for (int j = 0; j < textFields.length; j++) {
                textField[j].setCursor(cursor);
            }
        }
    }



    public static void main(String[] args) {
        new gameframe();
        //sudukoSolver.printBoard(sudukoBoardGenerator.unavoidableSetsArray);
    }
}