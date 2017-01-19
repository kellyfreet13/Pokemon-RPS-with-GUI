import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The panel class displays a gui in which three buttons are displayed,
 *    fire, water and grass button, along with three labels, a score counter,
 *    the user's choice, and the computer's prediction
 */
public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, ActionListener{

    /**
     * Fire option button
     */
    public JButton fire;

    /**
     * Water option button
     */
    public JButton water;

    /**
     * Grass option button
     */
    public JButton grass;

    /**
     * Displays the score, user vs. computer
     */
    public JLabel score;

    /**
     * Displays the users guess
     */
    public JLabel user_guess;

    /**
     * Displays the computers prediction
     */
    public JLabel cpu_guess;

    /**
     * integer to keep track of user wins
     */
    private int user_score;

    /**
     * integer to keep track of computer wins
     */
    private int cpu_score;

    /**
     * Integer representation of the user's guess,
     * either 1, 2, or 3 (F, W, and G respectively)
     */
    private int user_int_guess;

    /**
     * Boolean for whether or not the panel's buttons has been clicked
     */
    private boolean isClicked;

    /**
     * Constructs a panel object. Creates a layout to which the data members,
     *    buttons and labels, are added. Also loads images to the button to
     *    make it clear which button represents which option (F, W, G).
     *
     *    isClicked is initially set to false
     */
    public Panel(){
        GridLayout gridLayout = new GridLayout(2,3);
        setLayout(gridLayout);

        user_score = 0;
        cpu_score = 0;
        user_int_guess = 0;

        score = new JLabel("Score (user-comp): " + user_score + " - " + cpu_score);
        user_guess = new JLabel("User guess: ");
        cpu_guess = new JLabel("Computer guess:");

        add(score);

        add(user_guess);
        add(cpu_guess);

        ImageIcon charmander0 = new ImageIcon("charmander0.png");
        ImageIcon squirtle = new ImageIcon("squirtle0.png");
        ImageIcon bulbasaur = new ImageIcon("bulbasaur0.png");

        fire = new JButton(charmander0);
        water = new JButton(squirtle);
        grass = new JButton(bulbasaur);
        fire.addMouseListener(this);
        water.addMouseListener(this);
        grass.addMouseListener(this);
        fire.addActionListener(this);
        water.addActionListener(this);
        grass.addActionListener(this);

        addKeyListener(this);
        fire.addKeyListener(this);
        water.addKeyListener(this);
        grass.addKeyListener(this);

        add(fire);
        add(water);
        add(grass);

        isClicked = false;
    }

    /**
     * Updates the JLabel of the user's guess based on the source
     *    of ActionEvent e. Depending on the source, the data member
     *    user_int_guess is updated accordingly.
     *
     *    isClicked is set to true
     * @param e event performed on a member of the panel
     */
    public void actionPerformed(ActionEvent e){
        user_guess.setOpaque(true);
        if(e.getSource() == fire){
            user_guess.setText("User guess: Fire");
            user_guess.setBackground(new Color(237, 120, 30));
            user_int_guess = 1;
        } else if (e.getSource() == water){
            user_guess.setText("User guess: Water");
            user_guess.setBackground(new Color(113, 191, 177));
            user_int_guess = 2;
        } else if (e.getSource() == grass){
            user_guess.setText("User guess: Grass");
            user_guess.setBackground(new Color(104, 174, 40));
            user_int_guess = 3;
        }
        isClicked = true;
    }

    /**
     * Getter for data member isClicked
     * @return boolean isClicked
     */
    public boolean getIsClicked(){
        return isClicked;
    }

    /**
     * Sets isClicked to false
     */
    public void falseClicked() {
        isClicked = false;
    }

    /**
     * Getter for data member user_int_guess
     * @return int user_int_guess
     */
    public int getUser_int_guess(){
        return user_int_guess;
    }

    /**
     * Displays the winner based on the table of winning pairings
     * @param a First input to be compared, usually user's
     * @param b Second input to be compared, usually the computer's
     */
    public void displayWinner(int a, int b){
        int winner;
        // F = 1, W = 2, G = 3
        if( (a == 1 && b == 3) || (a == 3 && b == 2) || (a == 2 && b == 1)){
            winner = 1;
        } else if( a == b){
            winner = 0;
        }  else {
            winner = -1;
        }

        if(winner == 1){
            user_score++;
        } else if (winner == 0){
            //tied
        } else if (winner == -1){
            cpu_score++;
        }
        score.setText("Score (user-comp): " + user_score + " - " + cpu_score
                + "\n " + getWinner(winner));
    }

    /**
     * Getter method for the winner of the round.
     *
     * 1 represents user win
     * 0 represents tie
     * -1 represents computer win
     * @param a winner to be determined
     * @return string congratulating winner or tie
     */
    public String getWinner(int a){
        if(a == 1){
            return "User wins!";
        } else if(a == 0){
            return "Tie!";
        } else if(a == -1){
            return "Computer wins!";
        }
        return null;
    }

    /**
     * Sets user_int_guess to 4, which the server interprets
     * as a signal to terminate the program and save the computer to a file
     * @param e Key event, a key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key pressed");
        user_int_guess = 4;
    }

    /**
     * Sets user_int_guess to 4, which the server interprets
     * as a signal to terminate the program and save the computer to a file
     * @param e Key event, a key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        user_int_guess = 4;

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Not used
     * @param e Key event e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
