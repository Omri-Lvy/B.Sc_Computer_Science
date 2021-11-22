public class Submarine {
    public static int[][] createBoard(int N) {
        int[][] board = new int[N][N];
        int targets = 0;
        while (targets < N) {
            int targetX = (int) (Math.random() * N);
            int targetY = (int) (Math.random() * N);
            if (board[targetX][targetY] != 1) {
                board[targetX][targetY] = 1;
                targets++;
            }
        }
        return board;
    }

    public static int play(int[][] board, int targets, int hits) {
        StdOut.println("Please Enter the location of targets: ");
        int targetX = StdIn.readInt();
        int targetY = StdIn.readInt();
        if (targetX >= targets || targetY >= targets) {
            StdOut.println("You are out of bounds try again");
        } else if (board[targetX][targetY] == 1) {
            board[targetX][targetY] = 'X';
            hits++;
            StdOut.println("Hit!");
        } else if (board[targetX][targetY] == 'X') {
            StdOut.println("You already Hit this location");
        } else {
            StdOut.println("You Missed, Try again");
        }
        return hits;
    }

    public static void main(String[] args) {
        StdOut.print("Please Enter the number of targets: ");
        int N = StdIn.readInt();
        int[][] board = createBoard(N);
        int hits = 0;
        while (hits < N) {
            hits = play(board, N, hits);
        }
        StdOut.println("You Won!");
    }
}