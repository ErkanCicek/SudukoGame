package frontend.GUI;

import backend.pointsystem.pointSystem;
import backend.soundFx.sfxclass;
import backend.sudukoBoardLogic.sudukoBoardGenerator;
import backend.sudukoBoardLogic.sudukoSolver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class gameframe extends JFrame {
    int screenwidth = 850;
    int screenheight = 800;
    int gamescreen_width = 600;
    int controlscreen_width = screenwidth - gamescreen_width - 10;

    JFrame gameframe;
    JPanel menu;
    JPanel gamePanel;
    JPanel controlPanel;
    JPanel inputPanel;
    JPanel afterGamePanel;
    JPanel afterGamePanelWin;
    JLabel livesLabel;
    JLabel scoreLabel;
    JLabel scoreLabelCount;
    JLabel finalScoreLabel;
    JProgressBar healthbar;
    final Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);

    String value = null;
    int lives = 3;
    int score = 45;
    int tempScore = 0;
    boolean isGameRunning = true;
    int didPlayerWinVariable = 0;

    JToggleButton [] buttons = new JToggleButton[10];
    JTextField [][] textFields = new JTextField[9][9];
    boolean[][]wasEmptyBeforeArray = new boolean[9][9];

    pointSystem pointSystem = new pointSystem(score, isGameRunning);
    Thread thread = new Thread(pointSystem);

    public gameframe(){
        afterGamePanel();
        afterGamePanelWin();
        menuPanel();
        controlPanel();
        inputPanel();
        gamePanel();
        runGame();
        gameFrame();
    }
    public gameframe(int x){
        if(x == 1){
            afterGamePanel();
            afterGamePanelWin();
            controlPanel();
            controlPanel.setVisible(true);
            inputPanel();
            inputPanel.setVisible(true);
            gamePanel();
            gamePanel.setVisible(true);
            runGame();
            gameFrameRerun();
        }
    } /* this is a overloaded constructor that is called when the user wants to play again
                                    , and therefore this method does not have the menu panel in it */


    private void runGame() {
        Arrays.stream(wasEmptyBeforeArray).forEach(booleans -> Arrays.fill(booleans, true));
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
        defaultCursorMode();
        disableKeyInput();
    } //Adding all functionalities and methods / styling

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

        gameframe.add(menu);
        gameframe.setVisible(true);
    }
    private void gameFrameRerun(){
        gameframe = new JFrame("Suduko Game");
        gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameframe.setSize(screenwidth, screenheight);
        gameframe.setLocationRelativeTo(null);
        gameframe.setLayout(new BorderLayout());
        gameframe.setResizable(false);


        gameframe.add(controlPanel, BorderLayout.EAST);
        gameframe.add(inputPanel, BorderLayout.SOUTH);
        gameframe.add(gamePanel, BorderLayout.WEST);

        gameframe.setVisible(true);
    } //does not contain the menu panel

    private void gamePanel() {
        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(gamescreen_width, screenheight));
        gamePanel.setBackground(new Color(206, 171, 147));
        gamePanel.setLayout(new GridLayout(9,9,2,2));
        gamePanel.setVisible(false);
    }
    private void inputPanel() {
        inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(gamescreen_width, screenheight-650));
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(173,139,115));
        inputPanel.setVisible(false);
    }
    private void controlPanel() {
        Font font = new Font("sanserif", Font.BOLD, 27);
        controlPanel = new JPanel();
        livesLabel = new JLabel();
        livesLabel.setText("<HTML><u>HEALTH</u></HTML>");
        livesLabel.setFont(font);
        livesLabel.setForeground(Color.white);
        livesLabel.setBounds(55,20,110,50);

        healthbar = new JProgressBar(SwingConstants.HORIZONTAL);
        healthbar.setBounds(30,100,170,10);
        healthbar.setMaximum(3);
        healthbar.setValue(3);
        healthbar.setBackground(Color.red);
        healthbar.setForeground(Color.green);

        scoreLabel = new JLabel();
        scoreLabel.setText("<HTML><u>SCORE</u></HTML>");
        scoreLabel.setFont(font);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBounds(55, 200, 170, 50);

        scoreLabelCount = new JLabel("0");
        font = new Font("sansserif", Font.BOLD, 50);
        scoreLabelCount.setFont(font);
        scoreLabelCount.setForeground(Color.white);
        scoreLabelCount.setBounds(90,200, 200,200);

        controlPanel.setPreferredSize(new Dimension(controlscreen_width, screenheight));
        controlPanel.setBackground(new Color(173, 139, 115));
        controlPanel.setLayout(null);

        controlPanel.add(livesLabel);
        controlPanel.add(healthbar);
        controlPanel.add(scoreLabel);
        controlPanel.add(scoreLabelCount);

        controlPanel.setVisible(false);
    }
    private void afterGamePanel() {
            Font font = new Font("sanserif", Font.BOLD, 50);
            JButton retry = new JButton("New Game");
            afterGamePanel = new JPanel();
            JButton quit = new JButton("QUIT");
            quit.addActionListener(e -> System.exit(0));
            quit.setForeground(Color.black);
            quit.setFont(font);
            quit.setBounds(80,480,200,100);

            retry.addActionListener(e -> {
                new gameframe(1);
                gameframe.dispose();
                gamePanel.setVisible(true);
                controlPanel.setVisible(true);
                inputPanel.setVisible(true);
            });
            retry.setForeground(Color.black);
            retry.setFont(font);
            retry.setBounds(470,480,300,100);
            afterGamePanel.setPreferredSize(new Dimension(screenwidth,screenheight));
            afterGamePanel.setLayout(null);
            afterGamePanel.setBackground(Color.black);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("src/frontend/pictures/death.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert image != null;
            JLabel imagelabel = new JLabel(new ImageIcon(image));
            imagelabel.setBounds(150,-50,500,500);
            afterGamePanel.add(imagelabel);
            afterGamePanel.add(quit);
            afterGamePanel.add(retry);
            afterGamePanel.setVisible(false);
    }
    private void afterGamePanelWin() {
        Font font = new Font("sanserif", Font.BOLD, 50);
        JButton retry = new JButton("New Game");
        afterGamePanelWin = new JPanel();
        JButton quit = new JButton("QUIT");
        quit.addActionListener(e -> System.exit(0));
        quit.setForeground(Color.black);
        quit.setFont(font);
        quit.setBounds(80, 480, 200, 100);

        retry.addActionListener(e -> {
            new gameframe(1);
            gameframe.dispose();
            gamePanel.setVisible(true);
            controlPanel.setVisible(true);
            inputPanel.setVisible(true);
        });
        retry.setForeground(Color.black);
        retry.setFont(font);
        retry.setBounds(470, 480, 300, 100);
        finalScoreLabel = new JLabel();
        finalScoreLabel.setForeground(Color.white);
        finalScoreLabel.setBackground(Color.white);
        finalScoreLabel.setBounds(300, 250, 400, 200);
        finalScoreLabel.setText("");
        finalScoreLabel.setFont(font);
        afterGamePanelWin.setPreferredSize(new Dimension(screenwidth, screenheight));
        afterGamePanelWin.setLayout(null);
        afterGamePanelWin.setBackground(Color.black);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/frontend/pictures/win.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert image != null;
        JLabel imagelabel = new JLabel(new ImageIcon(image));
        imagelabel.setBounds(150, -50, 500, 500);
        afterGamePanelWin.add(imagelabel);
        afterGamePanelWin.add(finalScoreLabel);
        afterGamePanelWin.add(quit);
        afterGamePanelWin.add(retry);
        afterGamePanelWin.setVisible(false);
    }
    private void menuPanel(){
        Font font = new Font("sanserif", Font.BOLD, 50);
        JLabel[]labels = new JLabel[3];
        menu = new JPanel();
        JButton quit = new JButton("QUIT");
        JButton start = new JButton("START");

        quit.setForeground(Color.black);
        quit.setBounds(80,480,200,100);
        quit.setFont(font);
        quit.addActionListener(e -> System.exit(0));

        start.setForeground(Color.black);
        start.setBounds(470,480,250,100);
        start.setFont(font);
        start.addActionListener(e -> {
            menu.setVisible(false);
            gamePanel.setVisible(true);
            controlPanel.setVisible(true);
            inputPanel.setVisible(true);
        });


        menu.setPreferredSize(new Dimension(screenwidth, screenheight));
        menu.setBackground(Color.black);
        menu.setLayout(null);

        BufferedImage[]images = new BufferedImage[3];
        try{
            assert false;
            images[0] = ImageIO.read(new File("src/frontend/pictures/name1.png"));
            images[1] = ImageIO.read(new File("src/frontend/pictures/name2.png"));
            images[2] = ImageIO.read(new File("src/frontend/pictures/name3.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        for (int i = 0; i < images.length; i++){
            assert images[i] != null;
            labels[i] = new JLabel(new ImageIcon(images[i]));
        }
        labels[0].setBounds(-80,50,1000,200);
        labels[1].setBounds(355,160,100,200);
        labels[2].setBounds(-85, 240, 1000, 200);
        menu.add(labels[0]);
        menu.add(labels[1]);
        menu.add(labels[2]);
        menu.add(start);
        menu.add(quit);
        menu.setVisible(true);
    }


    //AddMethods
    public void addClues(){
        sudukoBoardGenerator.getGenerateBoard(sudukoBoardGenerator.tempBoard);
        int test = sudukoBoardGenerator.tempBoard[0][0];
        sudukoBoardGenerator.tempBoard[0][0] = 0;
        sudukoSolver.solver(sudukoBoardGenerator.tempBoard, sudukoSolver.counter1);
        sudukoBoardGenerator.createSuduko(sudukoBoardGenerator.mainBoard);
        sudukoBoardGenerator.tempBoard[0][0] = test;
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
    public void addButton() {
        Font font = new Font("sanserif", Font.BOLD, 20);
        int x = 20;
        int y = 50;
        for (int i = 1; i < buttons.length; i++){
            JToggleButton temp = new JToggleButton("" + i);
            buttons[i] = temp;
            buttons[i].setBounds(x, y, 50,50);
            buttons[i].setBackground(new Color(255, 251, 233));
            buttons[i].setForeground(new Color(86,45,15));
            buttons[i].setFont(font);
            buttons[i].setFocusPainted(false);
            x = x + 70;
            inputPanel.add(buttons[i]);
        }
        JToggleButton erase = new JToggleButton("erase");
        erase.setBounds(650, 37, 150,80);
        buttons[0] = erase;
        buttons[0].setBackground(new Color(255, 251, 233));
        buttons[0].setForeground(new Color(86,45,15));
        buttons[0].setFont(font);
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
                        sfxclass damageTakenSound = new sfxclass("src/backend/soundFx/damage.wav");
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
                                        damageTakenSound.playSound();
                                        healthbar.setValue(lives);
                                    }else{
                                        if (wasEmptyBeforeArray[finalI][finalJ]){
                                            sudukoBoardGenerator.mainBoard[finalI][finalJ] = Integer.parseInt(temp.getText());
                                            wasEmptyBeforeArray[finalI][finalJ] = false;
                                            score = pointSystem.score;
                                            tempScore = tempScore + score;
                                            scoreLabelCount.setText(Integer.toString(tempScore));
                                            thread.interrupt();
                                            score = 50;
                                            pointSystem = new pointSystem(score, isGameRunning);
                                            thread = new Thread(pointSystem);
                                            thread.start();
                                            didPlayerWin();
                                            if (didPlayerWinVariable == 1){
                                                sfxclass win = new sfxclass("src/backend/soundFx/win.wav");
                                                win.playSound();
                                                gamePanel.setVisible(false);
                                                controlPanel.setVisible(false);
                                                inputPanel.setVisible(false);
                                                gameframe.add(afterGamePanelWin);
                                                finalScoreLabel.setText(tempScore + " POINTS");
                                                afterGamePanelWin.setVisible(true);
                                            }
                                        }else{
                                            System.out.println("you have already entered here");
                                        }
                                    }
                                }catch (NumberFormatException ignored){

                                }
                                if (didPlayerLose(lives)){
                                    gamePanel.setVisible(false);
                                    controlPanel.setVisible(false);
                                    inputPanel.setVisible(false);
                                    gameframe.add(afterGamePanel);
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
        sfxclass deathSound = new sfxclass("src/backend/soundFx/death.wav");
        if (lives == 0){
            deathSound.playSound();
        }
        return lives == 0;
    }
    private void didPlayerWin(){
        int count = 0;
        for (int i = 0; i < textFields.length; i++){
            for (int j = 0; j < textFields.length; j++){
                String realValue = Integer.toString(sudukoBoardGenerator.tempBoard[i][j]);
                if (textFields[i][j].getText().equals("") && !(sudukoBoardGenerator.mainBoard[i][j] == Integer.parseInt(realValue))){
                    didPlayerWinVariable = 0;
                }else{
                    count++;
                }

                if (count == 81){
                    didPlayerWinVariable = 1;
                }
            }
        }
    }
    //styling
    public void boxStyle1(int row, int col){
        Font font = new Font("sanserif", Font.BOLD, 50);
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
                    temp.setFont(font);
                }else{
                    temp.setBackground(new Color(227, 202, 165));
                    temp.setFont(new Font("sanserif", Font.BOLD, 27));
                }
                temp.setCaretColor(new Color(227, 202, 165));
                temp.setForeground(new Color(86,45,15));
                temp.setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }
    public void boxStyle2(int row, int col){
        Font font = new Font("sanserif", Font.BOLD, 50);
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
                    temp.setFont(font);
                }else {
                    temp.setBackground(new Color(255, 251, 233));
                    temp.setFont(new Font("sanserif", Font.BOLD, 27));
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
    public void defaultCursorMode(){
        for (JTextField[] textField : textFields) {
            for (int j = 0; j < textFields.length; j++) {
                textField[j].setCursor(cursor);
            }
        }
    }
}