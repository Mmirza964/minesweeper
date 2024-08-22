import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import javalib.worldimages.*;

// represents a cell
class Cell implements IConstants {
  boolean isRevealed;
  boolean isFlagged;
  boolean isMine;
  ArrayList<Cell> neighbors;
  
  Cell() {
    this.isRevealed = false;
    this.isFlagged = false;
    this.isMine = false;
    this.neighbors = new ArrayList<Cell>();
  }
  
  // EFFECT: sets the neighbors for the cell
  void setNeighbors(ArrayList<Cell> neighbors) {
    this.neighbors = neighbors;
  }
  
  // EFFECT: makes a cell a mine
  void makeMine() {
    this.isMine = true;
  }
  
  // EFFECT: toggles if the cell has a flag on it or not
  void toggleFlag() {
    this.isFlagged = !this.isFlagged;
  }
  
  // returns a number depending what toggleFalg will do, if the cell
  // is already a flag, -1 will be returned when toggling it off and 
  // the opposite for when a cell is not flagged
  int toggleFlagInt() {
    if (this.isFlagged && !this.isRevealed) {
      return -1;
    } else {
      return 1;
    }
  }
  
  // EFFECT: reveals the cell on the grid
  void revealCell() {
    this.isRevealed = true;
    if (this.isFlagged) {
      this.toggleFlag();
    }
  }
  
  // returns the number of mines neighboring the cell
  int numNeighboringMines() {
    int numMines = 0;
    for (int i = 0; i < this.neighbors.size(); i++) {
      if (this.neighbors.get(i).isMine) {
        numMines++;
      }
    }
    return numMines;
  }
  
  // returns a colored number depending on the number of mines around the cell
  WorldImage drawNumber() {
    int mineNum = this.numNeighboringMines();
    ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.BLUE, 
        Color.RED, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.CYAN, 
        Color.BLACK, Color.MAGENTA));
    if (mineNum == 0) {
      return new TextImage("", Color.WHITE);
    } else {
      return new TextImage(Integer.toString(mineNum), FONT_SIZE, 
          FontStyle.BOLD, colors.get(mineNum - 1));
    }
  }
  
  // draws the cell on the WorldScene depending on if the cell is revealed, flagged, is a mine
  // or none of them
  WorldImage drawCell() {
    WorldImage outline = new RectangleImage(CELL_WIDTH, CELL_HEIGHT, OutlineMode.OUTLINE, 
        Color.BLACK);
    WorldImage notRevealed = new OverlayImage(outline, new RectangleImage(
        CELL_WIDTH, CELL_HEIGHT, OutlineMode.SOLID, Color.LIGHT_GRAY));
    WorldImage revealed = new OverlayImage(this.drawNumber(), new OverlayImage(outline, 
        new RectangleImage(CELL_WIDTH, CELL_HEIGHT, OutlineMode.SOLID, Color.DARK_GRAY)));
    WorldImage mine = new OverlayImage(new StarImage(MINE_CIRCUM, OutlineMode.SOLID, Color.BLACK), 
        new OverlayImage(outline, new RectangleImage(CELL_WIDTH, CELL_HEIGHT, 
            OutlineMode.SOLID, Color.RED)));
    WorldImage flag = new OverlayImage(new EquilateralTriangleImage(
        FLAG_SIDE_LENGTH, OutlineMode.SOLID, Color.YELLOW), notRevealed);
    
    if (this.isRevealed) {
      if (this.isMine) {
        return mine;
      } else {
        return revealed;
      }
    } else if (this.isFlagged) {
      return flag;
    } else {
      return notRevealed;
    }
  }
  
  // reveals all of the cells that do not have any neighboring mines
  void floodFill() {
    this.revealCell();
    if (this.numNeighboringMines() == 0) {
      for (Cell neighbor: this.neighbors) {
        if (!neighbor.isRevealed) {
          neighbor.floodFill();
        }
      }
    }
  }
  
  // changes the cell based on what state the cell clicked is at
  // returns a boolean because of onMouseClicked for the left click
  // true means that the cell is a mine and all of the mines should be revealed
  boolean leftClickHelp() {
    if (this.isFlagged) {
      return false;
    } else if (this.isMine) {
      return true;
    } else if (this.numNeighboringMines() == 0) {
      this.floodFill();
      return false;
    } else {
      this.revealCell();
      return false;
    }
  }
}