package minmax;

import com.sun.security.jgss.GSSUtil;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.LinkedList;
import java.util.List;

public class XOGame {

    private char[][] grid;
    private int width;
    private int height;
    private int numOfPiecesToWin;
    private int fills;

    public XOGame(int width, int height, int numOfPiecesToWin) {
        fills = 0;
        this.width = width;
        this.height = height;
        this.numOfPiecesToWin = numOfPiecesToWin;
        grid = new char[height][width];
        for (char[] grid1 : grid) {
            for (int j = 0; j < grid1.length; j++) {
                grid1[j] = ' ';
            }
        }
    }

    public XOGame(XOGame board) {
        grid = new char[board.height][board.width];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(board.grid[i], 0, this.grid[i], 0, board.width);
        }
        this.fills = board.fills;
        this.height = board.height;
        this.width = board.width;
        this.numOfPiecesToWin = board.numOfPiecesToWin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //TODO:  YOUR CODE HERE *******************************************
    public List<XOGame> allNextMoves(char nextPlayer) {
        List<XOGame> nextBoards = new LinkedList<>();
        for(int i=0;i<this.height ;i++){
            for(int j=0;j<this.width;j++){
                if(this.grid[i][j]==' ') {
                    XOGame board = new XOGame(this);
                    board.play(nextPlayer,i,j);
                    nextBoards.add(board);
                }
            }
        }
        // ======== complete THE CODE HERE ===============//
        //..
        //..
        return nextBoards;
    }

    public boolean play(char player, int row, int col) {
        if (grid[row][col] != ' ') {
            return false;
        }
        if ((row >= 0 && row < height) && (col >= 0 && col < width)) {
            grid[row][col] = player;
            fills++;
            return true;
        }
        return false;
    }

    /**
     * how good is the board for this player?
     *
     * @param player
     * @return
     */
    public int evaluate(char player) {
        //TODO:  YOUR CODE HERE *******************************************
        if(this.isWithdraw()) return 0;
       else if (this.isWin(player)) return Integer.MAX_VALUE;
        else {return Integer.MIN_VALUE; }
    }

    /**
     * checks if the game is withdraw
     *
     * @return
     */
    public boolean isWithdraw() {
        return (fills == width * height);
    }

    /**
     * checks if player is a winner (searching all the board)
     *
     * @param player
     * @return true if win
     */
    public boolean isWin(char player) {
        if (isWinInColumns(player) || isWinInRows(player) || isWinInDiagonals(player)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the game is win or withdraw for any player
     *
     * @return
     */
    public boolean isFinished() {
        return (isWin('x') || isWin('o') || isWithdraw());
    }

    private boolean isWinInColumns(char player) {
        for (int col = 0; col < width; col++) {
            int count = 0;
            for (int row = 0; row < height; row++) {
                if (grid[row][col] == player) {
                    count++;
                } else {
                    count = 0;
                }

                if (count == numOfPiecesToWin) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isWinInRows(char player) {
        for (int row = 0; row < height; row++) {
            int count = 0;
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == player) {
                    count++;
                } else {
                    count = 0;
                }

                if (count == numOfPiecesToWin) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isWinInDiagonals(char player) {
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (grid[row][col] == player) {
                    if (checkDiagonalDown(row, col, player) || checkDiagonalUp(row, col, player)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkDiagonalDown(int row, int col, char player) {
        int count = 1;
        int r = row + 1;
        int c = col + 1;

        while (r < height && c < width && grid[r][c] == player) {
            count++;
            r++;
            c++;

            if (count == numOfPiecesToWin) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonalUp(int row, int col, char player) {
        int count = 1;
        int r = row - 1;
        int c = col + 1;

        while (r >= 0 && c < width && grid[r][c] == player) {
            count++;
            r--;
            c++;

            if (count == numOfPiecesToWin) {
                return true;
            }
        }

        return false;
    }

    public char otherPlayer(char player) {
        if (player == 'x') {
            return 'o';
        }
        return 'x';
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            sb.append(" | ");
            for (int j = 0; j < width; j++) {
                sb.append(grid[i][j]);
                sb.append(" | ");
            }
            sb.append(" [").append(i + 1).append("]");
            sb.append('\n');
        }
        sb.append(" ");
        for (int i = 1; i < width; i++) {
            sb.append("-------");
        }
        sb.append("\n");
        sb.append(" ");
        for (int i = 1; i <= width; i++) {
            sb.append(" [").append(i).append("]");
        }

        return sb.toString();
    }
    public  XOGame getFirstAvailableMove(char nextPlayer) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == ' ') {
                    XOGame nextBoard = new XOGame(this);
                    if (nextBoard.play(nextPlayer, i, j)) {
                       return nextBoard;
                    }
                }
            }
        }
        return new XOGame(this);
    }
    public static void main(String[] args) {
        XOGame board = new XOGame(3, 3, 3);

        board.play('x', 2, 2);
        board.play('o', 1, 0);
        board.play('x', 1, 1);
        board.play('o', 2, 0);
        board.play('x', 0, 0);
        System.out.println("board:");
        System.out.println(board);
        System.out.println("****************");
        System.out.println("is win for x? " + board.isWin('x'));
        System.out.println("****************");

        List<XOGame> next = board.allNextMoves('o');
        int i = 1;
        for (XOGame b : next) {
            System.out.println(": (" + i + ")");
            System.out.println(b);
            i++;
        }
        System.out.println("______________________________");
//        System.out.println( board.evaluate('o') );

    }


}
