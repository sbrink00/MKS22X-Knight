public class KnightBoard{
  public static void main(String[]args){
    KnightBoard k = new KnightBoard(5, 5);
    System.out.println(k.whereToGoToString());
    k.addKnight(2, 2, 5);
    //System.out.println(k);
    //System.out.println(k.solveHelper(1, 0, 0));
    //k.solve();
    //System.out.println(k.countSolutionsHelper(1, 0, 4));
    //System.out.println(k.countSolutions());
    //System.out.println(k.movesToString());
    System.out.println(k.whereToGoToString());
    k.removeKnight(2, 2);
    System.out.println(k.whereToGoToString());
    //System.out.println(k.isASquare(4, 10));
  }

  private int[][] board;
  private int[][] moves;
  private int[][] whereToGo;
  private int[][] optimizedMoves;


  public KnightBoard(int startingRows, int startingCols){
    board = new int[startingRows][startingCols];
    initializeMoves();
    initializeWhereToGo();
    initializeOptimizedMoves();
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

  private void initializeWhereToGo(){
    whereToGo = new int[board.length][board[0].length];
    for (int idx = 0; idx < whereToGo.length; idx ++){
      for (int idx2 = 0; idx2 < whereToGo[0].length; idx2 ++){
        for (int idx3 = 0; idx3 < moves.length; idx3 ++){
          if (isASquare(idx + moves[idx3][0], idx2 + moves[idx3][1])) whereToGo[idx][idx2] ++;
        }
      }
    }
  }

  private void initializeOptimizedMoves(){
    optimizedMoves = new int[8][3];
    for (int idx = 0; idx < moves.length; idx ++){
      optimizedMoves[idx][0] = moves[idx][0];
      optimizedMoves[idx][1] = moves[idx][1];
      optimizedMoves[idx][2] = -1;
    }
  }

  public int countSolutions(){
    int total = 0;
    for (int idx = 0; idx < board.length; idx ++){
      for (int idx2 = 0; idx2 < board[0].length; idx2 ++){
        total += countSolutionsHelper(1, idx, idx2);
      }
    }
    return total;
  }

  public boolean solve(){
    for (int idx = 0; idx < board.length; idx ++){
      for (int idx2 = 0; idx2 < board[0].length; idx ++){
        if (solveHelper(1, idx, idx2)) return true;
      }
    }
    return false;
  }

  /*private boolean solveHelper(int num, int r, int c){
    if (num == board.length * board[0].length + 1) return true;
    addKnight(r, c, num);
    for (int idx = 0; idx < moves.length; idx ++){
      if (addKnight(r + moves[idx][0], c + moves[idx][1], num)){
        if (solveHelper(num + 1, r + moves[idx][0], c + moves[idx][1])) return true;
        removeKnight(r + moves[idx][0], c + moves[idx][1]);
      }
    }
    return false;
  }*/

  public int countSolutionsHelper(int num, int r, int c){
    if (num == board.length * board[0].length + 1) return 1;
    int total = 0;
    if (addKnight(r, c, num)){
      if (num == board.length * board[0].length) total ++;
      else{
        for (int idx = 0; idx < moves.length; idx ++){
          total += countSolutionsHelper(num + 1, r + moves[idx][0], c + moves[idx][1]);}
      }
      removeKnight(r, c);
    }
    return total;
    }

  private boolean solveHelper(int num, int r, int c){
    if (num == board.length * board[0].length + 1) return true;
    if (addKnight(r, c, num)){
      for (int idx = 0; idx < moves.length; idx ++){
        if (solveHelper(num + 1, r + moves[idx][0], c + moves[idx][1])) return true;}
      removeKnight(r, c);
    }
    return false;
  }

  public void adjustWhereToGo(int r, int c, int increment){
    for (int idx = 0; idx < moves.length; idx ++){
      if (isASquare(r + moves[idx][0], c + moves[idx][1])){
        whereToGo[r + moves[idx][0]][c + moves[idx][1]] += increment;}
    }
  }

  private boolean addKnight(int r, int c, int num){
    if (!isASquare(r, c)) return false;
    if (board[r][c] != 0) return false;
    board[r][c] = num;
    adjustWhereToGo(r, c, -1);
    return true;
  }

  private boolean removeKnight(int r, int c){
    if (!isASquare(r, c)) return false;
    if (board[r][c] == 0) return false;
    board[r][c] = 0;
    adjustWhereToGo(r, c, 1);
    return true;
  }

  private boolean isASquare(int r, int c){
    return r >= 0 && c >= 0 && r < board.length && c < board[0].length;
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

  public String whereToGoToString(){
    String output = "";
    for (int idx = 0; idx < whereToGo.length; idx ++){
      for (int idx2 = 0; idx2 < whereToGo[0].length; idx2 ++){
        output += whereToGo[idx][idx2] + " ";
      }
      output += "\n";
    }
    return output;
  }

}
