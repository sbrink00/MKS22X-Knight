public class KnightBoard{
  public static void main(String[]args){
    KnightBoard k = new KnightBoard(5, 5);
    System.out.println(k);
    k.addKnight(-1, -1, 24);
    k.addKnight(5, 5, 24);
    k.addKnight(-1, -1, 24);
    k.addKnight(5, 2, 24);
    k.addKnight(2, 5, 24);
    k.addKnight(4, 4, 24);
    System.out.println(k);
  }

  private int[][] board;

  public KnightBoard(int startingRows, int startingCols){
    board = new int[startingRows][startingCols];
  }

  private boolean solveHelper(int num){
    if (num == board.length * board[0].length) return true;
    else{
      return 
    }
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

}
