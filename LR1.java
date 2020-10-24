package edu;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/*
  Author: Christian Miller
  This is a shift-reduce parser that follows the grammar of the table given. Each case has its own method where it determines if it should be add,subtracted,multiplied... all the way
  until its no longer valid.

  Node: I had a lot of problems with my original program so this is a new one that isn't based on what I wrote in the paper. It follows the same logic, however I implemented the shiftDown
  inside the case instead of having its own method. This seemed to work more efficiently.
*/

public class Parser {



    static Stack <ParseToken> myStack = new Stack<ParseToken>();
    static Queue<String> input = new LinkedList<String>();


    public static void main(String args[]) {
        StringTokenizer st = new StringTokenizer(args[0], "+-*/()", true);
        boolean works = true;


        while(st.hasMoreTokens())
            input.add(st.nextToken());

        input.add("$");
        ParseToken pt = new ParseToken('-',0);
        myStack.push(pt);




        while(works) {
            switch(myStack.peek().state) {
            case 0:
                parseZero(works);
                break;
            case 1:
                parseOne(works);
                break;
            case 2:
                parseTwo(works);
                break;
            case 3:
                parseThree(works);
                break;
            case 4:
                parseFour(works);
                break;
            case 5:
                parseFive(works);
                break;
            case 6:
                parseSix(works);
                break;
            case 7:
                parseSeven(works);
                break;
            case 8:
                parseEight(works);
                break;
            case 9:
                parseNine(works);
                break;
            case 10:
                parseTen(works);
                break;
            case 11:
                parseEleven(works);
            break;


            }
        }


    }


    //prints expression
    public static void print() {
        String temp;

        for(int i =0; i < myStack.size(); i++) {
            temp = "[" + String.valueOf(myStack.get(i).symbol) + ":" + String.valueOf(myStack.get(i).state) + "] ";
            System.out.println(temp);
        }

        for(int i = 0; i <input.size(); i++) {
            String parse = input.poll();
            System.out.println(parse + " ");
            input.offer(parse);
        }

    }

    static ParseToken pt;

    //case 11
    public static void parseEleven(boolean works) {
        if(input.peek().equals("+") || input.peek().equals("-") || input.peek().equals("*") || input.peek().equals("/")|| input.peek().equals(")") || input.peek().equals("$")
                && myStack.peek().symbol == ')') {
            pt = myStack.pop();
            if(myStack.peek().symbol == 'E') {
                ParseToken pt2 = myStack.pop();
                myStack.pop();
                myStack.push(pt = new ParseToken('F',myStack.peek().state,pt2.token));
            }
            else {
                System.out.println("Not a valid expression");
                System.exit(0);
            }
        print();
        }
        else {
            System.out.println("Not a valid expression");
            System.exit(0);
        }
    }

    //case 10
     public static void parseTen(boolean works) {
         if(input.peek().equals("+") || input.peek().equals("-") || input.peek().equals("*") || input.peek().equals("/") || input.peek().equals("$") || input.peek().equals(")")
                    && myStack.peek().symbol == 'F') {
                pt = myStack.pop();
                if(myStack.peek().symbol == '*') {
                    myStack.pop();
                    ParseToken pt2 = myStack.pop();
                    int hold = pt2.token * pt2.token;
                    myStack.push(pt = new ParseToken('T', myStack.peek().state, hold));
                }
                else if(myStack.peek().symbol == '/') {
                    myStack.pop();
                    ParseToken pt2 = myStack.pop();
                    int hold = pt2.token/pt.token;
                    myStack.push(pt = new ParseToken('T', myStack.peek().state,hold));
                }
                else {
                    System.out.println("Not a valid expression");
                    System.exit(0);
                }
                print();
         }
         else {
             System.out.println("Not a valid expression");
                System.exit(0);
         }
     }

     //case 9
     public static void parseNine(boolean works) {
         if(input.peek().equals("+") || input.peek().equals("-") || input.peek().equals("$") || input.peek().equals(")")
                    && myStack.peek().symbol == 'T') {
                pt = myStack.pop();
                if(myStack.peek().symbol == '+') {
                    myStack.pop();
                    ParseToken pt2 = myStack.pop();
                    int hold = pt2.token + pt.token;
                    myStack.push(pt = new ParseToken('E', myStack.peek().state, hold));
                    print();
                }
                else if(myStack.peek().symbol == '-') {
                    myStack.pop();
                    ParseToken pt2 = myStack.pop();
                    int hold2 = pt2.token-pt.token;
                    myStack.push(pt = new ParseToken('E', myStack.peek().state,hold2));
                    print();
                }
                else {
                    System.out.println("Not a valid expression");
                    System.exit(0);
                }
                print();
         }

         else if( input.peek().equals("*") || input.peek().equals("/")) {
             myStack.push(pt = new ParseToken(input.peek().charAt(0), 7));
             input.poll();
             print();
         }

         else {
             System.out.println("Not a valid expression");
                System.exit(0);
         }
     }


     //case 0
    public static void parseZero(boolean works) {
        if(input.peek().equals("(")) {
            myStack.push(pt = new ParseToken('(',4));
            input.poll();
            print();
        }
        else if( myStack.peek().symbol == 'F') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('F',3,pt.token));
            print();
        }
        else if( myStack.peek().symbol == 'T') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('T',2,pt.token));
            print();
        }
        else if(myStack.peek().symbol == 'E') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('E',1, pt.token));
            print();
        }
        else {
            try {
                Integer.parseInt(input.peek());
            }
            catch(NumberFormatException e) {
                System.out.println("Not a valid expression");
                System.exit(0);
            }
            myStack.push(pt = new ParseToken('n',5, Integer.parseInt(input.peek())));
            input.poll();
            print();
        }
    }

    //case 4
    public static void parseFour(boolean works) {
        if(input.peek().equals("(")) {
            myStack.push(pt = new ParseToken('(',4));
            input.poll();
            print();
        }
        else if( myStack.peek().symbol == 'F') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('F',3,pt.token));
            print();
        }
        else if( myStack.peek().symbol == 'T') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('T',2,pt.token));
            print();
        }
        else if(myStack.peek().symbol == 'E') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('E',8, pt.token));
            print();
        }
        else {
            try {
                Integer.parseInt(input.peek());
            }
            catch(NumberFormatException e) {
                System.out.println("Not a valid expression");
                System.exit(0);
            }
            myStack.push(pt = new ParseToken('n',5, Integer.parseInt(input.peek())));
            input.poll();
            print();
        }
    }

    //case 5
    public static void parseFive(boolean works) {
        if(input.peek().equals("+") ||input.peek().equals("-") ||input.peek().equals("*") ||input.peek().equals("/") ||input.peek().equals("$") ||input.peek().equals(")") &&
        myStack.peek().symbol == 'n' ){
          pt = myStack.pop();
          int hold = myStack.peek().state;
          myStack.push(pt = new ParseToken('F', hold, pt.token));
          print();
        }
        else{
          System.out.println("Not a valid expression");
          System.exit(0);
        }
    }

    //case 6
    public static void parseSix(boolean works) {
        if(input.peek().equals("(")) {
            myStack.push(pt = new ParseToken('(',4));
            input.poll();
            print();
        }
        else if(myStack.peek().symbol == 'T') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('T',9,pt.token));
            print();
        }
        else if(myStack.peek().symbol == 'F') {
            pt = myStack.pop();
            myStack.push(pt = new ParseToken('F',3,pt.token));
            print();
        }
        else {
            try {
                Integer.parseInt(input.peek());
            }
            catch(NumberFormatException e) {
                System.out.println("Not a valid expression");
                System.exit(0);
            }
            myStack.push(pt = new ParseToken('n',5, Integer.parseInt(input.peek())));
            input.poll();
            print();
        }
    }

    //case 7
    public static void parseSeven(boolean works) {
        if(input.peek().equals("(")) {
            myStack.push(pt = new ParseToken('(',4));
            input.poll();
            print();
        }
        else if(myStack.peek().symbol == 'F'){
          pt = myStack.pop();
          myStack.push(pt = new ParseToken('F', 10, pt.token));
          print();
        }
        else{
          try {
              Integer.parseInt(input.peek());
          }
          catch(NumberFormatException e) {
              System.out.println("Not a valid expression");
              System.exit(0);
          }
          myStack.push(pt = new ParseToken('n',5, Integer.parseInt(input.peek())));
          input.poll();
          print();
      }

    }

    //case 3
     public static void parseThree(boolean works) {
         if(input.peek().equals("+") || input.peek().equals("-") || input.peek().equals("$") || input.peek().equals(")") ||input.peek().equals("*")
                 || input.peek().equals("/") && myStack.peek().symbol == 'F') {

            pt = myStack.pop();
            int hold = myStack.peek().state;
            myStack.push(pt = new ParseToken('T', hold, pt.token));
            print();
         }
         else {
             System.out.println("Not a valid expression");
                System.exit(0);
         }
     }

     //case 2
     public static void parseTwo(boolean works) {
         if(input.peek().equals("+") || input.peek().equals("-") || input.peek().equals("$") || input.peek().equals(")")
                 && myStack.peek().symbol == 'T') {

             pt = myStack.pop();
             int hold = myStack.peek().state;
             myStack.push(pt = new ParseToken('E', hold, pt.token));
             print();
         }
         else if(input.peek().equals("*") || input.peek().equals("/")) {
             myStack.push(pt = new ParseToken(input.peek().charAt(0),7));
             input.poll();
             print();
         }
         else {
             System.out.println("Not a valid expression");
                System.exit(0);
         }
     }

     //case 1
     public static void parseOne(boolean works) {
         if(input.peek().equals("-") || input.peek().equals("+")) {
             myStack.push(pt = new ParseToken(input.peek().charAt(0), 6));
             input.poll();
             print();
         }
         else if(input.peek().equals("$")) {
             System.out.println("Valid Expression, total = ");
             System.out.println(myStack.pop().token);
             works = false;

             System.exit(0);
         }
         else {
             System.out.println("Not a valid expression");
                System.exit(0);
         }
     }

     //case 8
     public static void parseEight(boolean works) {
         if(input.peek().equals("+") || input.peek().equals("-")) {
             myStack.push(pt = new ParseToken(input.peek().charAt(0), 6));
             input.poll();
             print();
         }
         else if(input.peek().equals(")")) {
             myStack.push(pt = new ParseToken(input.peek().charAt(0), 11));
             input.poll();
             print();
         }
         else {
             System.out.println("Not a valid expression");
                System.exit(0);
         }
     }


    static class ParseToken{
        int state;
        int token;
        char symbol;


        ParseToken(){}

        ParseToken(char symbol, int state){
            this.symbol = symbol;
            this.state = state;
        }

        ParseToken(char symbol, int state, int token){
            this.symbol = symbol;
            this.state = state;
            this.token = token;
        }
    }

}
