public class KnightBoard{
  public static void main(String[]args){
    KnightBoard k = new KnightBoard(5, 5);
    System.out.println(k);
    k.solveHelper(1, 2, 3);
    System.out.println(k);
    //System.out.println(k.movesToString());
  }

  private int[][] board;
  private int[][] moves;


  public KnightBoard(int startingRows, int startingCols){
    board = new int[startingRows][startingCols];
    initializeMoves();
  }

  private void initializeMoves(){
    moves = new  int[8][2];
    moves[0] = new int[]{2, 1};
    moves[1] = new int[]{2, -1};
    moves[2] = new int[]{-2, 1};
    moves[3] = new int[]{-2, -1};
    moves[4] = new int[]{1, -2};
    moves[5] = new int[]{1, 2};
    moves[6] = new int[]{-1, -2};
    moves[7] = new int[]{-1, 2};
  }

  private boolean solveHelper(int num, int r, int c){
    if (num == board.length * board[0].length) return true;
    else{
      for (int idx = 0; idx < moves.length; idx ++){
        if (addKnight(r + moves[idx][0], c + moves[idx][1], num)){
          if (solveHelper(num + 1, r + moves[idx][0], c + moves[idx][1])) return true;
          removeKnight(r + moves[idx][0], c + moves[idx][1]);
        }
        System.out.println(toString());
      }
    }
    return false;
  }

  private boolean addKnight(int r, int c, int num){
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
    if (board[r][c] != 0) return false;
    board[r][c] = num;
    return true;
  }

  private boolean removeKnight(int r, int c){
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
    if (board[r][c] == 0) return false;
    board[r][c] = 0;
    return true;
  }

  public String toString(){
    String output = "";
    for (int idx = 0; idx < board.length; idx ++){
      for (int idx2 = 0; idx2 < board[0].length; idx2 ++){
        int val = board[idx][idx2];
        if (board.length * board[0].length < 10) output += val + " ";
        else if (board[idx][idx2] < 10) output += "_" + val + " ";
        else output += val + " ";
      }
      output += "\n";
    }
    return output;
  }

  public String movesToString(){
    String output = "[";
    for (int idx = 0; idx < moves.length; idx ++){
      output += "[" + moves[idx][0] + ", " + moves[idx][1] + "], ";
    }
    return output.substring(0, output.length() - 2) + "]";
  }

}
