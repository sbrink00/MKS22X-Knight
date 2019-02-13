public class KnightBoard{
  public static void main(String[]args){
    KnightBoard k = new KnightBoard(5, 5);
    System.out.println(k);
  }

  private int[][] board;

  public KnightBoard(int startingRows, int startingCols){
    board = new int[startingRows][startingCols];
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
