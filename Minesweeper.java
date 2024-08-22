import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.util.Random;

// represents a mine sweeper game
class Minesweeper extends World implements IConstants {
  int row;
  int col;
  int numOfMines;
  Random rand;
  ArrayList<ArrayList<Cell>> grid;
  ArrayList<Cell> mines;
  WorldScene startingScene;
  String gameState;
  
  Minesweeper() {
    this.row = 0;
    this.col = 0;
    this.numOfMines = 0;
    this.rand = new Random();
    this.grid = new ArrayList<ArrayList<Cell>>();
    this.mines = new ArrayList<Cell>();
    this.gameState = "Welcome";
    this.startingScene = new WorldScene(500, 300);
    buildWelcomeScene();
  }
  
  Minesweeper(int row, int col, int numOfMines) {
    if (row > 1 && col > 1 && numOfMines < ((row * col) * 3) / 5) {
      this.row = row;
      this.col = col;
      this.numOfMines = numOfMines;
      this.rand = new Random();
      this.mines = new ArrayList<Cell>();
      this.grid = new ArrayList<ArrayList<Cell>>();
      this.gameState = "Game";
      this.startingScene = new WorldScene(col * CELL_WIDTH,
          row * CELL_HEIGHT);
      makeGrid();
      linkNeighbors();
      generateMines();
    } else {
      throw new IllegalArgumentException("Invalid row, col, or number of mines");
    }
  }
  
  // constructor used for testing seeded methods
  Minesweeper(int row, int col, int numOfMines, Random rand) {
    if (row > 1 && col > 1 && numOfMines < ((row * col) * 3) / 5) {
      this.row = row;
      this.col = col;
      this.numOfMines = numOfMines;
      this.rand = rand;
      this.mines = new ArrayList<Cell>();
      this.grid = new ArrayList<ArrayList<Cell>>();
      this.startingScene = new WorldScene(col * CELL_WIDTH,
          row * CELL_HEIGHT);
      this.gameState = "Game";
      makeGrid();
      linkNeighbors();
      generateMinesSeeded();
    } else {
      throw new IllegalArgumentException("Invalid row, col, or number of mines");
    }
  }
  
  // EFFECT: creates the welcome screen when the game is played
  void buildWelcomeScene() {
    WorldImage welcomeMsg = new TextImage("Welcome to Minesweeper!", 20, FontStyle.BOLD, 
        Color.BLACK);
    WorldImage selectMsg = new TextImage("Please select one of the levels below:", 20, 
        FontStyle.BOLD, Color.BLACK);
    WorldImage easyLevel = new TextImage("Easy", 20, FontStyle.BOLD, Color.BLACK);
    WorldImage mediumLevel = new TextImage("Medium", 20, FontStyle.BOLD, Color.BLACK);
    WorldImage hardLevel = new TextImage("Hard", 20, FontStyle.BOLD, Color.BLACK);
    
    WorldImage easyBox = new OverlayImage(easyLevel, new RectangleImage(100, 50, 
        OutlineMode.SOLID, Color.RED));
    
    WorldImage mediumBox = new OverlayImage(mediumLevel, new RectangleImage(100, 50, 
        OutlineMode.SOLID, Color.RED));
    WorldImage hardBox = new OverlayImage(hardLevel, new RectangleImage(100, 50, 
        OutlineMode.SOLID, Color.RED));
    
    WorldImage backGround = new OverlayOffsetImage(new OverlayOffsetImage(welcomeMsg, 
        0, 30, selectMsg), 0, 50, 
        new RectangleImage(500, 300, OutlineMode.SOLID, Color.LIGHT_GRAY));
    WorldImage boxes = new OverlayOffsetImage(new OverlayOffsetImage(easyBox, 120, 
        0, mediumBox), 180, 0, hardBox);
    WorldImage welcomeScreen = new OverlayOffsetImage(boxes, 0, -30, backGround);
    
    this.startingScene.placeImageXY(welcomeScreen, 250, 150);
  }
  
  // EFFECT: makes a grid of row * col with all empty cells
  void makeGrid() {
    for (int y = 0; y < this.row; y++) {
      ArrayList<Cell> rowOfCells = new ArrayList<Cell>();
      for (int x = 0; x < this.col; x++) {
        rowOfCells.add(new Cell());
      }
      this.grid.add(rowOfCells);
    }
  }
  
  // EFFECT: goes through the created grid and links every cell with 8 neighbors
  // whether they are null or an actual cell
  void linkNeighbors() {
    for (int y = 0; y < this.row; y++) {
      for (int x = 0; x < this.col; x++) {
        ArrayList<Cell> neighbors;
        if (x == 0 && y == 0) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x + 1), 
              this.grid.get(y + 1).get(x + 1), this.grid.get(y + 1).get(x)));
          
        } else if (x == this.col - 1 && y == 0) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x - 1),
              this.grid.get(y + 1).get(x), this.grid.get(y + 1).get(x - 1)));
          
        } else if (x == 0 && y == this.row - 1) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y - 1).get(x), 
              this.grid.get(y - 1).get(x + 1), this.grid.get(y).get(x + 1)));
          
        } else if (x == this.col - 1 && y == this.row - 1) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x - 1), 
              this.grid.get(y - 1).get(x - 1),
              this.grid.get(y - 1).get(x)));
          
        } else if (x == 0) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y - 1).get(x), 
              this.grid.get(y - 1).get(x + 1), this.grid.get(y).get(x + 1), 
              this.grid.get(y + 1).get(x + 1), 
              this.grid.get(y + 1).get(x)));
          
        } else if (x == this.col - 1) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x - 1), 
              this.grid.get(y - 1).get(x - 1), 
              this.grid.get(y - 1).get(x), this.grid.get(y + 1).get(x), 
              this.grid.get(y + 1).get(x - 1)));
        } else if (y == 0) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x - 1), 
              this.grid.get(y).get(x + 1), this.grid.get(y + 1).get(x + 1), 
              this.grid.get(y + 1).get(x), 
              this.grid.get(y + 1).get(x - 1)));
          
        } else if (y == this.row - 1) {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x - 1), 
              this.grid.get(y - 1).get(x - 1), 
              this.grid.get(y - 1).get(x), this.grid.get(y - 1).get(x + 1), 
              this.grid.get(y).get(x + 1)));
        } else {
          neighbors = new ArrayList<Cell>(Arrays.asList(this.grid.get(y).get(x - 1),
              this.grid.get(y - 1).get(x - 1), 
              this.grid.get(y - 1).get(x), this.grid.get(y - 1).get(x + 1), 
              this.grid.get(y).get(x + 1), 
              this.grid.get(y + 1).get(x + 1), this.grid.get(y + 1).get(x), 
              this.grid.get(y + 1).get(x - 1)));
        }
        
        this.grid.get(y).get(x).setNeighbors(neighbors);
      }
    }
  }
  
  // EFFECT: generates numOfMines mines at random places on the grid, makes the cell
  // at the random generated coordinate a mine through a helper, and adds the cell
  // to the list of mines field of the class 
  void generateMines() {
    ArrayList<Integer> oneDCoordinates = new ArrayList<Integer>();
    for (int i = 0; i < this.col * this.row; i++) {
      oneDCoordinates.add(i);
    }
    
    for (int i = 0; i <= this.numOfMines; i++) {
      int coordinate = oneDCoordinates.remove(this.rand.nextInt(oneDCoordinates.size()));
      int x = coordinate % this.col;
      int y = coordinate / this.col;
      this.grid.get(y).get(x).makeMine();
      this.mines.add(this.grid.get(y).get(x));
    }
  }
  
  // EFFECT: does the same as generateMines except the random object is seeded
  // for testing purposes
  void generateMinesSeeded() {
    ArrayList<Integer> oneDCoordinates = new ArrayList<Integer>();
    for (int i = 0; i < this.col * this.row; i++) {
      oneDCoordinates.add(i);
    }
    
    for (int j = 0; j < this.numOfMines; j++) {
      int coordinate = oneDCoordinates.remove(this.rand.nextInt(oneDCoordinates.size()));
      int x = coordinate % this.col;
      int y = coordinate / this.col;
      this.grid.get(y).get(x).makeMine();
      this.mines.add(this.grid.get(y).get(x));
    }
  }
  
  // returns a WorldScene of the grid
  public WorldScene makeScene() {
    if (this.gameState.equals("Welcome")) {
      return this.startingScene;
    } else {
      int x = STARTING_X;
      int y = STARTING_Y;
      
      for (int i = 0; i < this.row; i++) {
        for (int j = 0; j < this.col; j++) {
          this.startingScene.placeImageXY(this.grid.get(i).get(j).drawCell(), x, y);
          if (j == this.col - 1) {
            x = STARTING_X;
          } else {
            x = x + CELL_WIDTH;
          }
        }
        y = y + CELL_HEIGHT;
      }
      return this.startingScene; 
    }
  }
  
  // reveals all of the mines in the grid
  void revealAllMines() {
    for (int i = 0; i < this.mines.size(); i++) {
      this.mines.get(i).revealCell();
    }
  }
  
  // changes the cells of the grid depending on the button the user clicks
  // on the mouse pad
  public void onMouseClicked(Posn mousePos, String buttonClicked) {
    if ( this.gameState.equals("Welcome")) {
      if (mousePos.y > 150 && mousePos.y < 200 && buttonClicked.equals("LeftButton")) {
        if (mousePos.x > 80 && mousePos.x < 180) {
          this.row = 10;
          this.col = 10;
          this.numOfMines = 20;
          this.rand = new Random();
          this.mines = new ArrayList<Cell>();
          this.grid = new ArrayList<ArrayList<Cell>>();
          this.gameState = "Game";
          this.startingScene = new WorldScene(col * CELL_WIDTH,
              row * CELL_HEIGHT);
          makeGrid();
          linkNeighbors();
          generateMines();
        } else if (mousePos.x > 200 && mousePos.x < 300) {
          this.row = 20;
          this.col = 20;
          this.numOfMines = 50;
          this.rand = new Random();
          this.mines = new ArrayList<Cell>();
          this.grid = new ArrayList<ArrayList<Cell>>();
          this.gameState = "Game";
          this.startingScene = new WorldScene(col * CELL_WIDTH,
              row * CELL_HEIGHT);
          makeGrid();
          linkNeighbors();
          generateMines();
        } else if (mousePos.x > 320 && mousePos.x < 420) {
          this.row = 30;
          this.col = 30;
          this.numOfMines = 100;
          this.rand = new Random();
          this.mines = new ArrayList<Cell>();
          this.grid = new ArrayList<ArrayList<Cell>>();
          this.gameState = "Game";
          this.startingScene = new WorldScene(col * CELL_WIDTH,
              row * CELL_HEIGHT);
          makeGrid();
          linkNeighbors();
          generateMines();
        }
      }
    } else {
      int col = mousePos.x / CELL_WIDTH;
      int row = mousePos.y / CELL_HEIGHT;
        
      if (buttonClicked.equals("LeftButton")) {
        if (this.grid.get(row).get(col).leftClickHelp()) {
          this.revealAllMines();
          this.makeScene();
          this.endOfWorld("GameOver");
        }
      } else if (buttonClicked.equals("RightButton")) {
        this.grid.get(row).get(col).toggleFlag();
        this.numOfMines = this.numOfMines + this.grid.get(row).get(col).toggleFlagInt();
        if (this.numOfMines == 0) {
          this.endOfWorld("Winner!");
        }
      }
    }
  }
  
  // produces the last scene stopping the world
  public WorldScene lastScene(String msg) {
    int width = WORLD_SCENE_WIDTH;
    int height = WORLD_SCENE_HEIGHT;
    WorldScene endScene = new WorldScene(width, height);
    if (this.numOfMines == 0) {
      endScene.placeImageXY(new OverlayImage(new TextImage("YOU WON! ALL MINES WERE FOUND!", 
          20, FontStyle.BOLD, Color.BLACK), new RectangleImage(width, height, 
              OutlineMode.SOLID, Color.LIGHT_GRAY)), 
          width / 2, height / 2);
      return endScene;
    } else {
      endScene.placeImageXY(new OverlayImage(new TextImage("GAMEOVER! YOU CLICKED ON A MINE!", 
          20, FontStyle.BOLD, Color.BLACK), new RectangleImage(width, height, 
              OutlineMode.SOLID, Color.LIGHT_GRAY)), 
          width / 2, height / 2);
      return endScene;
    }
  }
}
