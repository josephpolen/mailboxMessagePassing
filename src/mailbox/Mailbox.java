/*
*                           Mailbox Class   
*                             Joe Polen
*
*      This class creates the mailbox object that messages are sent to before 
*      being forwarded to the consumer class.
*
*/
package mailbox;

import java.util.ArrayList;


public class Mailbox {
    
    //ArrayList buffer to  hold integers
    ArrayList<Integer> buffer;
    //Buffer size
    final int SIZE = 10;

    //Mailbox constructor that creates buffer inside
    public Mailbox(){
   
        buffer = new ArrayList<>();
    }

/*send method that is synchronized and causes the process to wait
* if the buffer is full
* @param number: number being sent
* @param who: process that sent the number
*/
synchronized void send(int number, String who) throws InterruptedException{
    String proc = who;
    while(buffer.size() > SIZE-3){
        System.out.println(proc + " waiting");
        wait();
        
    }
    
    //notify waiting process that buffer is no longer full
    notify();
    int num = number;
    buffer.add(number);
    
}

/*receive method that is sychronized and causes the process to wait
* if the buffer is emtpy
* @param number: number being sent
* @param who: process that sent the number
*/
synchronized int receive(int number, String who) throws InterruptedException{
    String proc = who;
    while(buffer.size() < 1){
        
        System.out.println(proc + " waiting");
        wait();
    }
    
    //nofity waiting process that buffer is no longer empty
    notify();
    int num = number;
    //remove number from buffer and return number to process
    number = buffer.remove(buffer.size() - 1);
    return number;
}
    
}
