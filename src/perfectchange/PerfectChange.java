/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perfectchange;
import java.util.Scanner;
import java.text.MessageFormat;
/**
 * @author jacob hinkebein
 */
public class PerfectChange {
    static Scanner sc = new Scanner(System.in);
    
    static int[] onhand =  {0, 0, 0, 0}; //qoh, doh, noh, poh, allows for loop in perfectChange()
    public static void main(String[] args) {
        int cents;
        boolean usingset;
        System.out.println("Welcome to Perfect Change");
        
        usingset = getCoinSet(); //sets t/f on using coin set
        
        cents = getCents();
        while (cents != 0) {
            if (cents == 101) {
                usingset = getCoinSet();
            }else if (cents == 102) {
                showAll(usingset);
            } else {
                if (usingset) {
                    perfectChange(cents);
                } else {
                    makeChange(cents);
                }
                
            }       
            
            cents = getCents();
        }
        System.out.println("Thanks for using Perfect Change.");
    }
    public static int getCents() {
        int c = 0;
        do {
            try {
                System.out.print("What value would you like change for? (1-100, 101 = new coin set, 102 = All, 0 - quit): ");
                c = sc.nextInt();
                if (c < 0 || c > 102) {
                    System.out.println("Value out of bounds: 0-102 only");
                }
            } catch (Exception e) {
                System.out.println("Illegal value: not an integer.");
                sc.nextLine();
                c = -1;
            }
        } while (c < 0 || c > 102);
        return c;
    }
    public static boolean getCoinSet() {
        //ask if they have coinset: return true if yes (after filling on hand), else false, set on hand values to zero
        String choice;
        System.out.println("Do you have a set of coins for making change? (Y/N): ");
        choice = sc.next();
        if (choice.toUpperCase().startsWith("Y")) {
            onhand[0] = getCoinCount("quarters"); //qoh
            onhand[1] = getCoinCount("dimes"); //doh
            onhand[2] = getCoinCount("nickels"); //noh
            onhand[3] = getCoinCount("pennies"); //poh
            return true;
        } else {
            return false;
        }
    }
    public static int getCoinCount(String prompt) {
        int ccount;
        do {
            try {
                System.out.print("How many " + prompt + " do you have? ");
                ccount = sc.nextInt();
                if (ccount < 0) {
                    System.out.println("non-negative values only.");
                }
            } catch (Exception e) {
                System.out.println("Illegal entry: not an integer.");
                sc.nextLine();
                ccount = -1;
            } 
        } while (ccount < 0);
        return ccount;
    }
    public static void perfectChange(int cents){
         int q = 0, d = 0 , n = 0, p, r = cents;
        int[] coins = {q, d, n};
        int[] coinValues = {25, 10, 5}; 
        for (int i = 0; i < coins.length; i++) {
            coins[i] = r / coinValues[i]; //coins we would like to give
            if (coins[i] > onhand[i]) { //checks if onhand global array to see if we have enough
                coins[i] = onhand[i];
            }
            r -= (coins[i] * coinValues [i]); //set remainder 
        }
        p = r;
        if (p > onhand[3]) {
            p = onhand[3];
        }
        r -= p;
        
        if (r != 0) {
            System.out.println("Unable to make perfect change for " + cents + " cents. I am short: " + r + " cent(s).");
        } else { //made perfect change
            String out = MessageFormat.format("For {0} cents I give:\n"
                    + "\t {1} quarters, {2} dimes, {3} nickels, {4} pennies, leaving\n"
                    + "\t {5} quarters, {6} dimes, {7} nickels, {8} pennies", cents, coins[0], coins[1], coins[2], p, (onhand[0]-q), (onhand[1]-d),
            (onhand[2]-n), (onhand[3]-p));
            System.out.println(out);
        }
    }
    public static void makeChange (int cents){
        int p, r = cents;
        int[] coins = {0, 0, 0}; //q, d, n
        int[] coinValues = {25, 10, 5}; 
        String out = MessageFormat.format("For {0} cent(s), I give:\n", cents);
        
        for (int i = 0; i < coins.length; i++) {
            coins[i] = r / coinValues[i];
            r -= (coins[i] * coinValues [i]);
        }
        p = r;
        
        
        out = out.concat(MessageFormat.format("\t{0} quarters, {1} dimes, {2} nickels, {3} pennies", coins[0], coins[1], coins[2], p));
        System.out.println(out); 
    }
    public static void showAll(boolean usingSet) {
        if (usingSet) {
            for (int i = 1; i <= 100; i++) {
                perfectChange(i);
            }
        } else {
            for (int i = 0; i <= 100; i++) {
                makeChange(i);
            }
        }  
    }
}
