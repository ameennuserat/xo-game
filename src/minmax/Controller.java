package minmax;

import java.util.*;
//import javafx.util.Pair;

public class Controller {
   //List list = new ArrayList<>();
    char computer = 'o';
    char human = 'x';
    XOGame board = new XOGame(3, 3, 3);

    public void play() {
        System.out.println(board);
        while (true) {
            humanPlay();
            System.out.println(board);
            if (board.isWin(human)) {
                System.out.println("Human wins");
                break;
            }
            if (board.isWithdraw()) {
                System.out.println("Draw");
                break;
            }
            computerPlay();
            System.out.println("_____Computer Turn______");
            System.out.println(board);
            if (board.isWin(computer)) {
                System.out.println("Computer wins!");
                break;
            }
            if (board.isWithdraw()) {
                System.out.println("Draw");
                break;
            }


        }

    }

    //         ************** YOUR CODE HERE ************            \\
    private void computerPlay() {
        // this is first available move, you should change this code to run you own code
       // board = board.getFirstAvailableMove(computer);
        board = (XOGame) maxMove(board,Integer.MIN_VALUE,Integer.MAX_VALUE).get(1);

    }

    /**
     * Human plays
     *
     * @return the column the human played in
     */
    private void humanPlay() {
        Scanner s = new Scanner(System.in);
        int col, row;
        while (true) {
            System.out.print("Enter row: ");
            row = s.nextInt();
            System.out.print("Enter column: ");
            col = s.nextInt();
            System.out.println();
            boolean validRange = false;
            if ((col > 0) && (col - 1 < board.getWidth())) {
                if ((row > 0) && (row - 1 < board.getHeight())) {
                    validRange = true;
                    if (board.play(human, row - 1, col - 1)) {
                        return;
                    } else {
                        System.out.println("Invalid cell: (" + row + ", " + col + ") --> cell is reserved ..");
                    }
                }
            }

            if (!validRange) {
                System.out.println("Invalid cell: out of range Width= " + board.getWidth() + ", height: " + board.getHeight() + ", try agine");
            }
        }
    }

    private List<Object> maxMove(XOGame g,int alpha,int beta) {
        // the function returns Pair where the (key) represent the valuation (type Integer),
        //and the (value) represents the state with the max evaluation
//         ************** YOUR CODE HERE ************            \\
        List<Object> board = new LinkedList<>();
            if(g.isFinished()){
               board.add(g.evaluate(computer));
               board.add(g);
               return board;
            }


            else {
                int ev = Integer.MIN_VALUE;
                XOGame next =null ;
                for(XOGame xo: g.allNextMoves(computer)){
                    List<Object> l= minMove(xo,alpha, beta);
                    int max = (int) l.get(0);
//                    ev = Math.max(ev,max);
//                    next = xo;
                    if(ev <= max){
                        ev = max;
                        next =xo;
                    }
                    alpha = Math.max(ev, alpha);

                    if (beta <= alpha) break;

                }
                board.add(ev);
                board.add(next);
                return board;


            }



//        return null;


    }

    private  List<Object>  minMove(XOGame g,int alpha,int beta) {
        // the function returns Pair where the (key) represent the valuation (type Integer),
        //and the (value) represents the state with the min evaluation
//         ************** YOUR CODE HERE ************            \\
        List<Object> board = new LinkedList<>();
        if(g.isFinished()){
            board.add(g.evaluate(computer));
            board.add(g);
            return board;
        }

        else {

            int ev = Integer.MAX_VALUE;
            XOGame next =null ;
            for(XOGame xo: g.allNextMoves(human)){
                List<Object> l= maxMove(xo, alpha, beta);
                int min = (int) l.get(0);
                ev = Math.min(ev,min);
                next = xo;
                if(ev >= min){
                    ev = min;
                    next = xo;
                }
                beta = Math.min(ev,beta);

                if(beta <= alpha) break;

                
            }
            board.add(ev);
            board.add(next);
            return board;

        }


//        return null;


    }

    public static void main(String[] args) {
        Controller g = new Controller();
        g.play();

    }

}
