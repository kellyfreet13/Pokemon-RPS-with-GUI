import java.net.*;
import java.io.*;

/**
 * The server class extends thread and communicates with the
 *    client class via a BufferedReader as well as a PrintStream.
 *    The server houses the computer that makes predictions based
 *    on the user's input.
 */
public class Server extends Thread {

    /**
     * ServerSocket created as the server host
     */
    private ServerSocket server;

    /**
     * Socket to connect with Client
     */
    private Socket sock;

    /**
     * BufferedReader to read input from the client
     */
    private BufferedReader read;

    /**
     * PrintStream to send data to the client
     */
    private PrintStream write;

    /**
     * Computer that will do the predictions
     */
    private Computer comp;

    /**
     * Constructs a server object that communicates with the Client
     *    via sending predictions and storing patterns.
     *    Instantiates all the data members
     */
    public Server() {
        try {
            server = new ServerSocket(49111);
            System.out.println("Waiting... in the server");
            sock = server.accept();
            read = new BufferedReader(new InputStreamReader(
                    sock.getInputStream()));
            write = new PrintStream(sock.getOutputStream());
            System.out.println("Server Connected");
            comp = processFile();
            if (comp == null) {
                comp = new Computer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Begins to communicate with the client. Initially, the computers guesses
     *    are random, but then it starts storing patterns and making predictions
     *    based on probability.
     */
    public void run() {
        String pattern = "";
        String user_guess;
        String s_user_guess = "";
        try {
            for (int i = 0; i < 4; i++) {     //random guesses
                System.out.println("About to read the line");
                user_guess = read.readLine();//read pattern
                System.out.println("After read the line");

                s_user_guess = getStringRep(Integer.parseInt(user_guess));
                pattern += s_user_guess;
                write.println(comp.makeRandomPrediction());//write comp guess
                System.out.println("S (read)- User guess: " + pattern);
                System.out.println("S (write)- Comp guess: random guess");

                write.flush();
            }
            comp.storePattern(pattern);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                user_guess = read.readLine();
                if(Integer.parseInt(user_guess) == 4){ saveComputer(); }
                s_user_guess = getStringRep(Integer.parseInt(user_guess));
                int comp_guess = comp.makePrediction(pattern);
                write.println(comp_guess);
                pattern = addChar(pattern, s_user_guess);//update pattern
                comp.storePattern(pattern);

                write.flush();
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Saves the computer object to a .dat file when the program is terminated
     */
    public void saveComputer(){
        File f = new File("Computer.dat");
        try {
            System.out.println("Saving file");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(comp);
            out.close();
        } catch (IOException e) {
            System.out.println("Error processing file");
        }
        System.exit(0);
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
    public String getStringRep(int FWG) {
        String guess = "";
        switch (FWG) {
            case 1:
                guess = "F";
                break;
            case 2:
                guess = "W";
                break;
            case 3:
                guess = "G";
                break;
        }
        return guess;
    }

    /**
     * Appends param old to the pattern, and deletes the first character in the string
     * @param old String pattern to be updated
     * @param newString Single character string to be appended to old
     * @return updated string with aforementioned changes
     */
    public String addChar(String old, String newString) {
        return old.substring(1, old.length()) + newString;
    }

    /**
     * Creates a server object and calls start (overridden from Thread class)
     * @param args standard for main
     */
    public static void main(String[] args) {
        Server chat = new Server();
        chat.start();
    }

    /**
     * If a computer.dat file exists, it will be read in and previous patterns
     * stored in the computer.dat file will be used and used from that point on
     * @return
     */
    public Computer processFile() {
        File f = new File("computer.dat");
        Computer comp;
        if (f.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
                comp = (Computer) in.readObject();
                in.close();
            } catch (IOException e) {
                System.out.println("Error processing file");
            } catch (ClassNotFoundException e) {
                System.out.println("Could not find class");
            }
        }
        return null;
    }
}


