/*
*                  Message Passing Indirect Mailbox Example
*                             Joe Polen
*
*        This program is an example of an indirect mailbox message passing
*        between two threads using process synchronization.           
 */
package mailbox;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class messagePassing {

    //Create mailbox object
    static Mailbox box = new Mailbox();

    public static void main(String[] args) {

        //create producer and consumer threads
        Thread producer = new Thread(new producer());
        Thread consumer = new Thread(new consumer());
        //start threads
        producer.start();
        consumer.start();
    }

    //producer class that runs in its own thread
    static class producer implements Runnable {

        //printer 
        PrintWriter prodPrinter;
        //String for telling who is waiting
        String prod = "Producer ";
        int number;

        public void run() {
            try {
                prodPrinter = new PrintWriter("producer.txt");
            } catch (FileNotFoundException ex) {
            }

            //run 100 times
            for (int i = 0; i < 100; i++) {
                //produce random number
                number = random();
                prodPrinter.println(number);
                System.out.println("Producer: " + number);
                
                //send number to the mailbox
                try {
                    box.send(number, prod);
                } catch (InterruptedException ex) {
                }

            }
            prodPrinter.close();
        }

    }

    //consumer class that runs in its own thread
    static class consumer implements Runnable {

        PrintWriter consPrinter;
        String cons = "Consumer ";
        int number;

        public void run() {
            try {
                consPrinter = new PrintWriter("consumer.txt");
            } catch (FileNotFoundException e) {
            }
            //run 100 times
            for (int i = 0; i < 100; i++) {
                
                //receive number from the mailbox
                try {
                    number = box.receive(number, cons);
                } catch (InterruptedException ex) {
                }
                
                consPrinter.println(number);
                System.out.println("Consumer: " + number);
            }
            consPrinter.close();
        }

    }
    
    //method for creating a random integer
    //from 0-999
    public static int random() {
        int max = 999;
        int min = 0;
        Random random = new Random();
        int randNum = random.nextInt((max - min) + 1) + min;
        return randNum;
    }
}
