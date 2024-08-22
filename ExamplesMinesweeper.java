import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;

// holds all of the examples and tests
class ExamplesMinesweeper implements IConstants {
  
  // examples of cells
  Cell c1;
  Cell c2;
  Cell c3;
  Cell c4;
  Cell c5;
  
  // examples of mine sweepers
  Minesweeper m1; // uses seeded constructor
  Minesweeper m2; // uses seeded constructor
  Minesweeper m3; // uses normal constructor
  Minesweeper m4;
  
  // sets the values of the fields
  void reset() {
    c1 = new Cell();
    c2 = new Cell();
    c3 = new Cell();
    c4 = new Cell();
    c5 = new Cell();
    
    m1 = new Minesweeper(3, 3, 3, new Random(1));
    m2 = new Minesweeper(20, 20, 50, new Random(1));
    m3 = new Minesweeper(19, 30, 21);
    m4 = new Minesweeper();
  }
  
  // tests constructor exception
  boolean testConstructor(Tester t) {
    // tests when row is invalid
    return t.checkConstructorException(new IllegalArgumentException(
            "Invalid row, col, or number of mines"), "Minesweeper", 0, 20, 10)
        // tests when col in invalid
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid row, col, or number of mines"), "Minesweeper", 10, -2, 10)
        // tests when row and col are both invalid
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid row, col, or number of mines"), "Minesweeper", 0, -5, 2)
        // tests when numOfMines is invalid
        && t.checkConstructorException(new IllegalArgumentException(
            "Invalid row, col, or number of mines"), "Minesweeper", 10, 10, 90);
  }
  
  // tests makeGrid method
  void testMakeGrid(Tester t) {
    // resets values, make grid is called in the constructor of m1
    reset();
    
    // tests every row's size to make sure the grid is constructed correctly
    t.checkExpect(m1.grid.get(0).size(), m1.col);
    t.checkExpect(m1.grid.get(1).size(), m1.col);
    t.checkExpect(m1.grid.get(2).size(), m1.col);
    
    // does the same for another mine sweeper game that is longer
    // checks the first row, last row, and a random middle row
    // to check if the grid was constructed correctly
    t.checkExpect(m2.grid.get(0).size(), m2.col);
    t.checkExpect(m2.grid.get(8).size(), m2.col);
    t.checkExpect(m2.grid.get(19).size(), m2.col);
  }
  
  // tests linkNeighbors method
  void testLinkNeighbors(Tester t) {
    // resets values
    reset();

    // each set of checkExpects tests all of the neighbors of the cell that are cells.
    // there is a set of test for every edge case in the grid
    
    // Top-left corner (0, 0)
    t.checkExpect(m1.grid.get(0).get(0).neighbors.contains(m1.grid.get(0).get(1)), true);
    t.checkExpect(m1.grid.get(0).get(0).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(0).get(0).neighbors.contains(m1.grid.get(1).get(0)), true);

    // Top-right corner (0, 2)
    t.checkExpect(m1.grid.get(0).get(2).neighbors.contains(m1.grid.get(0).get(1)), true);
    t.checkExpect(m1.grid.get(0).get(2).neighbors.contains(m1.grid.get(1).get(2)), true);
    t.checkExpect(m1.grid.get(0).get(2).neighbors.contains(m1.grid.get(1).get(1)), true);

    // Bottom-left corner (2, 0)
    t.checkExpect(m1.grid.get(2).get(0).neighbors.contains(m1.grid.get(1).get(0)), true);
    t.checkExpect(m1.grid.get(2).get(0).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(2).get(0).neighbors.contains(m1.grid.get(2).get(1)), true);

    // Bottom-right corner (2, 2)
    t.checkExpect(m1.grid.get(2).get(2).neighbors.contains(m1.grid.get(1).get(2)), true);
    t.checkExpect(m1.grid.get(2).get(2).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(2).get(2).neighbors.contains(m1.grid.get(2).get(1)), true);

    // Top row, second cell (0, 1)
    t.checkExpect(m1.grid.get(0).get(1).neighbors.contains(m1.grid.get(0).get(0)), true);
    t.checkExpect(m1.grid.get(0).get(1).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(0).get(1).neighbors.contains(m1.grid.get(1).get(0)), true);
    t.checkExpect(m1.grid.get(0).get(1).neighbors.contains(m1.grid.get(0).get(2)), true);
    t.checkExpect(m1.grid.get(0).get(1).neighbors.contains(m1.grid.get(1).get(2)), true);

    // Bottom row, second cell (2, 1)
    t.checkExpect(m1.grid.get(2).get(1).neighbors.contains(m1.grid.get(2).get(0)), true);
    t.checkExpect(m1.grid.get(2).get(1).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(2).get(1).neighbors.contains(m1.grid.get(1).get(0)), true);
    t.checkExpect(m1.grid.get(2).get(1).neighbors.contains(m1.grid.get(2).get(2)), true);
    t.checkExpect(m1.grid.get(2).get(1).neighbors.contains(m1.grid.get(1).get(2)), true);

    // Left column, second cell (1, 0)
    t.checkExpect(m1.grid.get(1).get(0).neighbors.contains(m1.grid.get(0).get(0)), true);
    t.checkExpect(m1.grid.get(1).get(0).neighbors.contains(m1.grid.get(0).get(1)), true);
    t.checkExpect(m1.grid.get(1).get(0).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(1).get(0).neighbors.contains(m1.grid.get(2).get(0)), true);
    t.checkExpect(m1.grid.get(1).get(0).neighbors.contains(m1.grid.get(2).get(1)), true);

    // Right column, second cell (1, 2)
    t.checkExpect(m1.grid.get(1).get(2).neighbors.contains(m1.grid.get(0).get(2)), true);
    t.checkExpect(m1.grid.get(1).get(2).neighbors.contains(m1.grid.get(0).get(1)), true);
    t.checkExpect(m1.grid.get(1).get(2).neighbors.contains(m1.grid.get(1).get(1)), true);
    t.checkExpect(m1.grid.get(1).get(2).neighbors.contains(m1.grid.get(2).get(2)), true);
    t.checkExpect(m1.grid.get(1).get(2).neighbors.contains(m1.grid.get(2).get(1)), true);

    // Internal cell (1, 1)
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(0).get(0)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(0).get(1)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(0).get(2)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(1).get(0)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(1).get(2)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(2).get(0)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(2).get(1)), true);
    t.checkExpect(m1.grid.get(1).get(1).neighbors.contains(m1.grid.get(2).get(2)), true);
  }
  
  // tests generateMinesSeeded method
  void testGenerateMinesSeeded(Tester t) {
    // resets values, generateMinesSeeded is called when
    // constructing the mine sweeper game
    reset();
    
    // adds the cells in the grid that are mines to minesUseLoop
    ArrayList<Cell> minesUseLoop = new ArrayList<Cell>();
    for (int y = 0; y < m1.row; y++) {
      for (int x = 0; x < m1.col; x++) {
        if (m1.grid.get(y).get(x).isMine) {
          minesUseLoop.add(m1.grid.get(y).get(x));
        }
      }
    }
    
    // checks if m1.mines has the mines in minesUseLoop
    // uses the loops to add the mines to a list and compares
    // to the list that is made in generateMinesSeeded
    t.checkExpect(m1.mines.contains(minesUseLoop.get(0)), true);
    t.checkExpect(m1.mines.contains(minesUseLoop.get(1)), true);
    t.checkExpect(m1.mines.contains(minesUseLoop.get(2)), true);
    
    // tests to make sure mines.size that is created in generateMinesSeeded
    // is the same number as the numOfMines inputed into the constructor
    t.checkExpect(m1.mines.size(), m1.numOfMines);
    // same test as above but with a different mine sweeper game
    t.checkExpect(m2.mines.size(), m2.numOfMines);
  }
  
  // tests revealAllMines method
  void testRevealAllMines(Tester t) {
    // resets the values
    reset();
    
    // makes sure none of the mines are revealed
    // before calling the method
    for (Cell mine : m1.mines) {
      t.checkExpect(mine.isRevealed, false); 
    }
    // calling the method
    m1.revealAllMines();
    // checks if all of the mines are revealed
    for (Cell mine : m1.mines) {
      t.checkExpect(mine.isRevealed, true);
    }
    
    // makes sure none of the mines are revealed
    // before calling the method
    for (Cell mine : m2.mines) {
      t.checkExpect(mine.isRevealed, false); 
    }
    // calling the method
    m2.revealAllMines();
    // checks if all of the mines are revealed
    for (Cell mine : m2.mines) {
      t.checkExpect(mine.isRevealed, true);
    }
  }
  
  // tests makeScene method
  boolean testMakeScene(Tester t) {
    // WorldImages are from drawCell method
    WorldImage outline = new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK);
    WorldImage notRevealed = new OverlayImage(outline, new RectangleImage(20, 20, 
        OutlineMode.SOLID, Color.LIGHT_GRAY));
    WorldImage mine = new OverlayImage(new StarImage(8, OutlineMode.SOLID, Color.BLACK), 
        new OverlayImage(outline, new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED)));
    WorldImage flag = new OverlayImage(new EquilateralTriangleImage(15, OutlineMode.SOLID,
        Color.YELLOW), notRevealed);
    
    // resets values
    reset();
    // creates the image 
    m1.grid.get(0).get(1).revealCell();
    m1.grid.get(2).get(2).revealCell();
    m1.grid.get(1).get(1).revealCell();
    m1.grid.get(1).get(0).toggleFlag();
    m1.grid.get(1).get(2).toggleFlag();
    m1.revealAllMines();
    
    // builds the WorldScene to test against makeScene
    WorldScene scene = new WorldScene((m1.col * 20), (m1.row * 20));
    scene.placeImageXY(mine, 10, 10);
    scene.placeImageXY(new OverlayImage(m1.grid.get(0).get(1).drawNumber(), 
        new OverlayImage(outline, new RectangleImage(20, 20, OutlineMode.SOLID, 
            Color.DARK_GRAY))), 30, 10);
    scene.placeImageXY(mine, 50, 10);
    scene.placeImageXY(flag, 10, 30);
    scene.placeImageXY(new OverlayImage(m1.grid.get(1).get(1).drawNumber(), 
        new OverlayImage(outline, 
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.DARK_GRAY))), 30, 30);
    scene.placeImageXY(flag, 50, 30);
    scene.placeImageXY(mine, 10, 50);
    scene.placeImageXY(notRevealed, 30, 50);
    scene.placeImageXY(new OverlayImage(outline, 
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.DARK_GRAY)), 50, 50);
    
    // tests if makeScene produces scene
    return t.checkExpect(m1.makeScene(), scene);
  }
  
  // runs the bigBang and shows the intermediate grid
  void testBigBang(Tester t) {
    // resets the values
    reset();
    // outputs the image
    m4.bigBang(WORLD_SCENE_WIDTH, WORLD_SCENE_HEIGHT);
  }
  
  // tests setNeighbors method
  void testsetNeighbors(Tester t) {
    // resets values
    reset();
    // makes an array list of neighbors
    ArrayList<Cell> n1 = new ArrayList<Cell>();
    // adding cells to array list
    n1.add(c2);
    // adds cell to array list
    n1.add(c3);
    // adds cell to array list
    n1.add(c4);
    // tests neighbors on a cell with no neighbors
    t.checkExpect(c1.neighbors, new ArrayList<Cell>());
    // adds neighbors to a cell
    this.c1.setNeighbors(n1);
    // tests to see if neighbors were added to cell
    t.checkExpect(c1.neighbors, n1);
  }

  // tests makeMine method
  void testmakeMine(Tester t) {
    //resets values
    reset();
    // tests to see if cell is a mine
    t.checkExpect(c1.isMine, false);
    // makes a cell a mine
    c1.makeMine();
    // tests if cell is a mile after makeMine
    t.checkExpect(c1.isMine, true);
    // calls make mine on a cell thats already a mine
    c1.makeMine();
    // tests to see if a mine is still a mine after calling makemine on it
    t.checkExpect(c1.isMine, true);
  }

  // tests toggleFlag method
  void testToggleFlag(Tester t) {
    // resets values
    reset();
    // tests to see if a cell is flagged
    t.checkExpect(c1.isFlagged, false);
    // toggles flag on a cell that isnt flagged
    c1.toggleFlag();
    // tests to see if cell is now flagged
    t.checkExpect(c1.isFlagged, true);
    // toggles flag on a cell that is flagged
    c1.toggleFlag();
    // tests to see if cell becomes unflagged
    t.checkExpect(c1.isFlagged, false);
  }

  // tests revealCell
  void testrevealCell(Tester t) {
    // resets values
    reset();
    // tests to see if a cell is revealed
    t.checkExpect(c1.isRevealed, false);
    // reveals a cell
    c1.revealCell();
    // tests to see if a cell is revealed after revealcell
    t.checkExpect(c1.isRevealed, true);
    // reveals an already revealed cell
    c1.revealCell();
    // tests to make sure cell is still revealed
    t.checkExpect(c1.isRevealed, true);
  }

  // tests numNeighboringMines method
  void testnumNeighboringMines(Tester t) {
    // resets values
    reset();
    // makes an array list of neighbors
    ArrayList<Cell> n1 = new ArrayList<Cell>();
    // makes cell a mine
    c2.makeMine();
    // makes cell a mine
    c3.makeMine();
    // adding cells to array list
    n1.add(c2);
    // adds cell to array list
    n1.add(c3);
    // adds cell to array list
    n1.add(c4);
    // tests neighbors of a cell with no neighbors
    t.checkExpect(c1.numNeighboringMines(), 0);
    // sets neighbors of a cell with no neighbors
    this.c1.setNeighbors(n1);
    // tests neighbors of a cell with neighbors
    t.checkExpect(c1.numNeighboringMines(), 2);
    // makes neighbor into mine
    c4.makeMine();
    // tests neighbors of a mine with all neighbors as mines
    t.checkExpect(c1.numNeighboringMines(), 3);
  }

  // tests drawCell method
  boolean testdrawCell(Tester t) {
    // resets values
    reset();
    
    // sets cells to different states to test
    c2.makeMine();
    c3.toggleFlag();
    c4.revealCell();
    c5.makeMine();
    c5.revealCell();
    
    // tests drawing an empty cell
    return t.checkExpect(c1.drawCell(), new OverlayImage(new RectangleImage(20,20,
        OutlineMode.OUTLINE,Color.BLACK),
        new RectangleImage(20,20,OutlineMode.SOLID,Color.LIGHT_GRAY)))
        && t.checkExpect(c2.drawCell(), new OverlayImage(new RectangleImage(20, 20, 
            OutlineMode.OUTLINE, Color.BLACK),
            new RectangleImage(20, 20, OutlineMode.SOLID, Color.LIGHT_GRAY)))
        && t.checkExpect(c3.drawCell(), new OverlayImage(
            new EquilateralTriangleImage(15, OutlineMode.SOLID, Color.YELLOW), new OverlayImage(
                new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK), 
                new RectangleImage(20, 20, OutlineMode.SOLID, Color.LIGHT_GRAY))))
        && t.checkExpect(c5.drawCell(), new OverlayImage(new StarImage(8, OutlineMode.SOLID, 
            Color.BLACK), 
            new OverlayImage(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK), 
                new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED))));
  }

  // tests drawNumber method
  boolean testdrawNumber(Tester t) {
    // resets values
    reset();
    // makes an array list of neighbors
    ArrayList<Cell> n1 = new ArrayList<Cell>();
    // adding cells to array list
    n1.add(c2);
    // adds cell to array list
    n1.add(c3);
    // adds cell to array list
    n1.add(c4);
    // adds neighbors to a cell
    this.c1.setNeighbors(n1);
    // makes neighbor into mine
    c2.makeMine();
    // makes neighbor into mine
    c3.makeMine();
    // makes neighbor into mine
    c4.makeMine();
    // tests a cell with 3 mines as neighbors
    return t.checkExpect(c1.drawNumber(), new TextImage("3", 15, FontStyle.BOLD, Color.GREEN) )
        && t.checkExpect(c5.drawNumber(), new TextImage("", Color.WHITE));
  }
  
  // tests lastScene method
  void testLastScene(Tester t) {
    reset();
    int width = WORLD_SCENE_WIDTH;
    int height = WORLD_SCENE_HEIGHT;
    // makes end scene
    WorldScene endScene = new WorldScene(width, height);
    WorldScene endScene2 = new WorldScene(width, height);
    endScene.placeImageXY(new OverlayImage(new TextImage("YOU WON! ALL MINES WERE FOUND!", 
        20, FontStyle.BOLD, Color.BLACK), new RectangleImage(width, height, 
            OutlineMode.SOLID, Color.LIGHT_GRAY)), 
        width / 2, height / 2);
    // sets num of mines to 0
    m2.numOfMines = 0;
    // makes sure scene is correct when there are no mines left
    t.checkExpect(m2.lastScene("Gameover"), endScene); 
    // 
    reset();
    // reveals all mines to test the gameover screen
    m2.revealAllMines();
    // checks game scene when mine is clicked
    endScene2.placeImageXY(new OverlayImage(new TextImage("GAMEOVER! YOU CLICKED ON A MINE!", 
        20, FontStyle.BOLD, Color.BLACK), new RectangleImage(width, height, 
            OutlineMode.SOLID, Color.LIGHT_GRAY)), 
        width / 2, height / 2);
    // makes sure scene is correct when mine is clicked
    t.checkExpect(m2.lastScene("Gameover"), endScene2);
    
  }
 
  // tests onMouseClick method
  void testonMouseClick(Tester t) {
    reset();
    // clicks on specific cell
    m2.onMouseClicked(new Posn(200,200), "LeftButton");
    int col = 200 / CELL_WIDTH;
    int row = 200 / CELL_HEIGHT;
    // makes sure the specific cell gets revealed
    t.checkExpect(m2.grid.get(row).get(col).isRevealed, true);
    // flags a cell
    m2.onMouseClicked(new Posn(180,180), "RightButton");
    int col1 = 180 / CELL_WIDTH;
    int row2 = 180 / CELL_HEIGHT;
    // makes sure cell becomes flagged
    t.checkExpect(m2.grid.get(row2).get(col1).isFlagged, true);
    // clicks on a mine
    m2.onMouseClicked(new Posn(260,10),"LeftButton");
    // reveals all mines
    m2.revealAllMines();
    boolean allMines = true;
    // for loop checks to make sure all mines in grid
    // are revealed, and makes sure number of mines is same
    // as the size of m2.mines
    for (int i = 0; i < m2.mines.size(); i++) {
      if (!m2.mines.get(i).isMine) {
        allMines = false;
      }
      // checks that x(num of mines in grid) is equal to m2.mines
      t.checkExpect(allMines, true);
    } 
  }
  
  // tests leftClickHelp method
  void testLeftClickHelp(Tester t) {
    reset();
    // checks a normal cell
    t.checkExpect(m2.grid.get(0).get(0).leftClickHelp(), false);
    // checks a mine
    t.checkExpect(m2.grid.get(7).get(11).leftClickHelp(), true);
    // toggles flag for a specific cell
    m2.grid.get(0).get(1).toggleFlag();
    // makes sure that specific cell has 0 neighbors
    t.checkExpect(m2.grid.get(0).get(1).numNeighboringMines(),0);
    // tests case of a cell that is flagged with no mines as neighbors
    t.checkExpect(m2.grid.get(0).get(1).leftClickHelp(), false);
    
  }
  
  // tests floodFill method
  void testFloodFill(Tester t) {
    reset();
    // calls floodfill on cell
    c1.floodFill();
    // makes sure floodfill reveals the cell
    t.checkExpect(c1.isRevealed, true);
    // calls floodfill on a cell next to a cell
    // which has mine as neighbors
    m2.grid.get(0).get(11).floodFill();
    // checks to see cell which floodfill is called on
    //is revealed
    t.checkExpect(m2.grid.get(0).get(11).isRevealed, true);
    // makes sure cell that has mine as neighbor is not revealed
    t.checkExpect(m2.grid.get(0).get(12).isRevealed, false);
  }
  
  // tests toggleFlagInt method
  void testToggleFlagInt(Tester t) {
    // checks that c1 is not flagged
    t.checkExpect(c1.isFlagged, false);
    // flags c1
    c1.toggleFlag();
    // checks correct int is returned for
    // a flagged and not revealed cell
    t.checkExpect(c1.toggleFlagInt(),-1);
    // checks to see if correct int is returned for
    // a cell which is not flagged but is revealed
    t.checkExpect(c2.toggleFlagInt(), 1);
  } 
  
  // tests buildWelcomeScene method
  boolean testBuildWelcomeScene(Tester t) {
    // all of the images were copied from the actual method and built just like the method would
    WorldImage welcomeMsg = new TextImage("Welcome to Minesweeper!", 20, 
        FontStyle.BOLD, Color.BLACK);
    WorldImage selectMsg = new TextImage("Please select one of the levels below:", 
        20, FontStyle.BOLD, Color.BLACK);
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
    WorldImage boxes = new OverlayOffsetImage(new OverlayOffsetImage(easyBox, 120, 0, 
        mediumBox), 180, 0, hardBox);
    WorldImage welcomeScreen = new OverlayOffsetImage(boxes, 0, -30, backGround);
    WorldScene scene = new WorldScene(500, 300);
    scene.placeImageXY(welcomeScreen, 250, 150);
    // the method does not need to be called because it is already called in the constructor
    // so when the game is made the startingScene is automatically the output of buildWelcomeScene
    return t.checkExpect(m4.startingScene, scene);
  }
}