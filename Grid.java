public class Grid{
  private boolean isFull;
  private int filledSquares;
  //private int[][] timesFilled = new int[10][10];
  //Have threads send 2^n to Grid class, where n is the id number
  
  Grid(){
		isFull = false;
		filledSquares = 0;
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				timesFilled[i][j] = 0;
			}
		}
	}
	
	public int getFilledSquares(){
		return filledSquares;
	}
  }

