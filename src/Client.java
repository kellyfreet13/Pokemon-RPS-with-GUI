import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.*;
import java.io.*;

/**
 * The client class is a class that extends thread, to be run concurrently
 * with the Server class. The client receives user input and sends it to
 * the server where it is stored. The client also received the Server's
 * output, and displays information accordingly
 */
public class Client extends Thread {

    /**
     * Client side socket to connect to server
     */
    private Socket sock;

    /**
     * Client side BufferedReader to read from server
     */
    private BufferedReader read;

    /**
     * Client side PrintStream to output to server
     */
    private PrintStream write;

    /**
     * Constructs the client object, and initializes the data
     * members. Connects the socket to the server
     */
    public Client() {
        try {
            System.out.println("Requesting Connection...");
            sock = new Socket("localhost", 49111);
            read = new BufferedReader(new InputStreamReader(
                    sock.getInputStream()));
            write = new PrintStream(sock.getOutputStream());
            System.out.println("Client Connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Frame object from which a Panel object is created.
     * Creates a while(true) loop in which data is continuously sent
     *    and read from the Server.
     * Sends user guess to Server
     * Reads in computer guess from Client
     * Displays winners accordingly and updates
     *    panel with user and computer guess
     */
    public void run() {
        Frame f = new Frame(); //create frame
        f.setTitle("Pokemon Project 6"); //set title
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setVisible(true);

        Panel panel = f.getPanel();
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
                f.dispose();
            }
        });

        int user_guess;
        int comp_guess;

        int count = 0;
        while (panel.getUser_int_guess() == 0) {
            //do nothing, don't send the output stream
            //before the user guesses (initialized to 0)
            //For whatever reason, if I don't have this it does not work
            //DO NOT GET RID OF THIS
            //the program will crash without this, as demonstrated
            if(count % 10000 == 0){
                System.out.println(" ");
            }
        }
        panel.cpu_guess.setOpaque(true);
        while (true) { //do panel updates here
            try {
                if (panel.getIsClicked()) { //only read in user guess if a button was clicked
                    user_guess = panel.getUser_int_guess();

                    write.println(user_guess);
                    comp_guess = Integer.parseInt(read.readLine());
                    panel.cpu_guess.setBackground(getColor(comp_guess));
                    panel.cpu_guess.setText("Comp guess: " + getStringRep(comp_guess));

                    write.flush();
                    panel.displayWinner(user_guess, comp_guess);
                    panel.falseClicked();
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fire water and grass are 1-3, so a 4 signifies
     *    that the program should be terminated
     */
    public void exitProcedure(){
        write.println(4);
    }

    /**
     * Converts an integer representation of fire
     *    water, and grass to a String representation:
     *    1 = "F"
     *    2 = "W"
     *    3 = "G"
     *
     * @param FWG integer representation of either fire, water, or grass
     * @return String representation: "F", "W", or "G"
     */
    public String getStringRep(int FWG){
        String guess = "";
        switch (FWG){
            case 1:
                guess = "Fire";
                break;
            case 2:
                guess = "Water";
                break;
            case 3:
                guess = "Grass";
                break;
        }
        return guess;
    }

    /**
     * Converts an integer representation of fire
     *    water, and grass to a Color
     *
     * @param FWG integer representation of either fire, water, or grass
     * @return Color in respect to FWG
     */
    public Color getColor(int FWG){
        Color guess = null;
        switch (FWG){
            case 1:
                guess = new Color(237, 120, 30);
                break;
            case 2:
                guess = new Color(113, 191, 177);
                break;
            case 3:
                guess = new Color(104, 174, 40);
                break;
        }
        return guess;
    }

    /**
     * Creates a new client, and starts the thread (calls
     * overridden start method)
     * @param args Standard for main method
     */
    public static void main(String[] args) {
        Client chat = new Client();
        chat.start();

    }
}
