public class KnightBoard{
  public static void main(String[]args){
    KnightBoard k = new KnightBoard(5, 5);
    //System.out.println(k.whereToGoToString());
    //k.addKnight(0, 1, 7);
    //k.adjustOptimizedMoves(0, 1);
    //System.out.println(k.optimizedMovesToStringg());
    //System.out.println(k.solveHelper(1, 0, 0));
    //k.solve();
    //System.out.println(k);
    //System.out.println(k.countSolutionsHelper(1, 0, 4));
    //System.out.println(k.countSolutions());
    //System.out.println(k.movesToString());
    //System.out.println(k.whereToGoToString());
    //k.removeKnight(2, 2);
    //System.out.println(k.whereToGoToString());
    //System.out.println(k.isASquare(4, 10));
    runTest(7);
  }

  private int[][] board;
  private int[][] moves;
  private int[][] whereToGo;
  private int[][] optimizedMoves;


  public KnightBoard(int startingRows, int startingCols){
    if (startingCols <= 0 || startingRows <= 0) throw new IllegalArgumentException("rows and columns must be greater than 0");
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
      optimizedMoves[idx][2] = 0;
    }
  }

  private boolean isASquare(int r, int c){
    return r >= 0 && c >= 0 && r < board.length && c < board[0].length;
  }

  public boolean throwException(){
    for (int idx = 0; idx < board.length; idx ++){
      for (int idx2 = 0; idx2 < board[0].length; idx2 ++){
        if (board[idx][idx2] != 0) return true;
      }
    }
    return false;
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

  private void adjustWhereToGo(int r, int c, int increment){
    for (int idx = 0; idx < moves.length; idx ++){
      if (isASquare(r + moves[idx][0], c + moves[idx][1])){
        whereToGo[r + moves[idx][0]][c + moves[idx][1]] += increment;}
    }
  }

  private void adjustOptimizedMoves(int r, int c){
    for (int idx = 0; idx < optimizedMoves.length; idx ++){
      if (isASquare(r + optimizedMoves[idx][0], c + optimizedMoves[idx][1])){
        optimizedMoves[idx][2] = whereToGo[r + optimizedMoves[idx][0]][c + optimizedMoves[idx][1]];
      }
      else optimizedMoves[idx][2] = -1;
    }
    sortOptimizedMoves();
  }

  private void sortOptimizedMoves(){
    for (int idx = 0; idx < optimizedMoves.length; idx ++){
      int index = idx;
      for (int idx2 = idx; idx2 < optimizedMoves.length; idx2 ++){
        if (optimizedMoves[idx2][2] < optimizedMoves[index][2]) index = idx2;
      }
      int[] change = optimizedMoves[index];
      optimizedMoves[index] = optimizedMoves[idx];
      optimizedMoves[idx] = change;
    }
  }

public boolean solve(int r, int c){
    if (throwException()) throw new IllegalStateException("board must be empty to call this method.");
    if (!isASquare(r, c)) throw new IllegalArgumentException("row and column must both be on the board.");
    return solveHelper(1, r, c);
  }

  private boolean solveHelper(int num, int r, int c){
    if (num == board.length * board[0].length + 1) return true;
    if (addKnight(r, c, num)){
      adjustOptimizedMoves(r, c);
      for (int idx = 0; idx < optimizedMoves.length; idx ++){
        if (optimizedMoves[idx][2] > -1){
          if (solveHelper(num + 1, r + optimizedMoves[idx][0], c + optimizedMoves[idx][1])) return true;
        }
      }
      removeKnight(r, c);
    }
    return false;
  }

  public int countSolutions(int r, int c){
    if (throwException()) throw new IllegalStateException("board must be empty to call this method.");
    if (!isASquare(r, c)) throw new IllegalArgumentException("row and column must both be on the board.");
    return countSolutionsHelper(1, r, c);
  }

  public int countSolutionsHelper(int num, int r, int c){
    if (num == board.length * board[0].length + 1) return 1;
    int total = 0;
    if (addKnight(r, c, num)){
      if (num == board.length * board[0].length) total ++;
      else{
        for (int idx = 0; idx < moves.length; idx ++){
          total += countSolutionsHelper(num + 1, r + moves[idx][0], c + moves[idx][1]);
        }
      }
      removeKnight(r, c);
    }
    return total;
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

  public String optimizedMovesToStringg(){
    String output = "[[";
    for (int idx = 0; idx < optimizedMoves.length; idx ++){
      for (int idx2 = 0; idx2 < optimizedMoves[0].length; idx2 ++){
        output += optimizedMoves[idx][idx2] + ", ";
      }
      output = output.substring(0, output.length() - 2) + "], [";
    }
    return output.substring(0, output.length() - 3) + "]";
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

  public static void runTest(int i){

  KnightBoard b;
  int[]m =   {4,5,5,5,5};
  int[]n =   {4,5,4,5,5};
  int[]startx = {0,0,0,1,2};
  int[]starty = {0,0,0,1,2};
  int[]answers = {0,304,32,56,64};
  if(i >= 0 ){
    try{
      int correct = answers[i];
      b = new KnightBoard(m[i%m.length],n[i%m.length]);

      int ans  = b.countSolutions(startx[i],starty[i]);

      if(correct==ans){
        System.out.println("PASS board size: "+m[i%m.length]+"x"+n[i%m.length]+" "+ans);
      }else{
        System.out.println("FAIL board size: "+m[i%m.length]+"x"+n[i%m.length]+" "+ans+" vs "+correct);
      }
    }catch(Exception e){
      System.out.println("FAIL Exception case: "+i);

    }
  }
}



}
