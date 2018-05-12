package life;

/**
 * Deze klasse representeert een generatie van cellen
 * op een veld. De afmeting van het veld is vastgelegd door
 * de constanten BREEDTE en HOOGTE.
 * Cellen kunnen groeien naar een volgende generatie
 * volgens de regels van Game of Life van John Conway:
 * At each step in time, the following transitions occur:
 * 1: Any live cell with fewer than two live neighbors dies, as if caused by under population
 * 2: Any live cell with two or three live neighbors lives on to the next generation
 * 3: Any live cell with more than three live neighbors dies, as if by overpopulation
 * 4: Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * @author Teun Blijlevens
 */
public class Life {
  // constants
  public static final int HOOGTE = 25;
  public static final int BREEDTE = 40;

  // attributes
  /**
   * A matrix filled with boolean values. <br>
   * The matrix' size is <code>HOOGTE</code> * <code>BREEDTE</code>. <br>
   * A cell can be populated (<code>true</code>) or unpopulated (<code>false</code>).
   */
  private boolean[][] lifeCell = new boolean[HOOGTE][BREEDTE];


  /**
   * Creates a new instance of the Game of Life.
   * All cells in the matrix are unpopulated, each cell is initialised
   * by the standard value <code>false</code> in the attribute lifeCell
   */
  public Life() {
  }

  /**
   * Geeft de status van de cel op rij i en kolom j.
   * <br>Voorwaarde: 0 <= i < HOOGTE en 0 <= j < BREEDTE
   * @param i coordinate of the cell on the y-axis. Index of the row (starting at 0).
   * @param j coordinate of the cell on the x-axis. Index of the column (starting at 0).
   * @return the value of the specified cell
   */
  public boolean isBevolkt(int i, int j) {
    return lifeCell[i][j];
  }

  /**
   * Verandert de status van een cel. Een bevolkte cel
   * wordt onbevolkt en andersom.
   * <br>Voorwaarde: 0 <= i < HOOGTE en 0 <= j < BREEDTE
   * @param i coordinate of the cell on the y-axis. Index of the row (starting at 0).
   * @param j coordinate of the cell on the x-axis. Index of the column (starting at 0).
   */
  public void wisselBevolking(int i, int j) {
    lifeCell[i][j] = !lifeCell[i][j];
  }

  /**
   * Bepaalt voor de cel op rij i en kolom j hoeveel buurcelllen bevolkt zijn.
   * @param i coordinate of the cell on the y-axis. Index of the row (starting at 0).
   * @param j coordinate of the cell on the x-axis. Index of the column (starting at 0).
   * @return the amount of neighbours of this cell that are populated
   */
  public int telBuren (int i, int j){

/* // Game of life with borders of the gamefield (comment out infinite world code below):
    int aantal = 0;

    // if cell is at the edge of the world it has less neighbours:
    int iMin; // = Math.max(i-1, 0);
    int iMax; // = Math.min(i+1, HOOGTE-1);
    int jMin; // = Math.max(j-1, 0);
    int jMax; // = Math.min(j+1, BREEDTE -1);

        for (int k = iMin ; k <= iMax ; k++){
          for (int l = jMin ; l <= jMax ; l++){
            if (!(k==i && l==j) && lifeCell[k][l]){
              aantal++;
            }
          }
        }
    return aantal;

*/ // end of bordered Game of Life

// Game of Life without borders of the gamefield (infinite world):
    int aantal = 0;
    int iMin = i-1;
    int iMax = i+1;
    int jMin = j-1;
    int jMax = j+1;

    // when a cell is on the edge of the world, count cells on the opposite edge
    // of the world as neighbours (set their coordinates in iMin, etc.):
    if (i == 0){iMin = HOOGTE-1;}
    if (i == HOOGTE-1){iMax = 0;}
    if (j == 0){jMin = BREEDTE-1;}
    if (j == BREEDTE-1){jMax = 0;}

    // initialise arrays with neighbour coordinates:
    int[] iArray = {iMin, i, iMax};
    int[] jArray = {jMin, j, jMax};

    // count neighbours:
    for (int a=0 ; a<3 ; a++){
      for (int b=0 ; b<3 ; b++){
        if (!(iArray[a]==i && jArray[b]==j) && lifeCell[iArray[a]][jArray[b]]){
          aantal++;
        }
      }
    }
    return aantal;
// end of borderless Game of Life
  } //endmethod telBuren

  /**
   * Bepaalt de bevolking van cel in rij i en kolom j in de volgende generatie.
   * @param i coordinate of the cell on the y-axis. Index of the row (starting at 0).
   * @param j coordinate of the cell on the x-axis. Index of the column (starting at 0).
   * @return the value that this cell is going to have in the next generation
   */
  public boolean volgendeCelWaarde(int i, int j){
    int aantalBuren = telBuren(i,j);
    boolean nuBevolkt = lifeCell[i][j];
    if ((nuBevolkt && (aantalBuren == 2 || aantalBuren == 3)) || (!nuBevolkt && aantalBuren == 3)){
      return true; // only stays true when populated and 2/3 neighbours OR unpopulated and 3 neighbours
    }
    else {
      return false; //all others die or stay unpopulated
    }
  } //endmethod volgendeCelWaarde

  /**
   * Kent aan lifeCell de volgende generatie cellen toe.
   * Copies the current lifeCell matrix. Adjusts all cells for the next generation.
   * Sets the lifeCell matrix to the new generation of cells
   */
  public void nieuweGeneratie() {
    boolean[][] volgendeGeneratie = new boolean[HOOGTE][BREEDTE];

    for (int i = 0 ; i < HOOGTE ; i++){
      for (int j = 0 ; j < BREEDTE ; j++){
        volgendeGeneratie[i][j] = volgendeCelWaarde(i,j);
      } //end for j
    } // end for i
    lifeCell = volgendeGeneratie;
  } // endmethod nieuweGeneratie

}
