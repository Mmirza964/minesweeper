/* USER GUIDE:
 * - when the game is run, a welcome message will be displayed and three red boxes
 * under it will display the difficulties that are offered. All a user has to do
 * is simply hover over the box and left-click and the difficulty will be chosen
 * and a game board will be displayed
 * 
 * DIFFICULTY OPTIONS:
 * - easy: 10 X 10 grid with 20 mines in it
 * - medium: 20 X 20 grid with 50 mines in it
 * - hard: 30 X 30 grid with 100 mines in it
 * 
 * - left-clicking will reveal a cell. If the cell is already revealed nothing will happen.
 * If the cell is revealed and displays a number, the number is the amount of mines
 * in it's neighbors. If the cell is a mine, then the game will stop and a gameover screen
 * will display
 * 
 * - right-clicking will flag a cell with a yellow triangle. A cell that is already flagged
 * cannot be revealed. Right-clicking on a cell that is already flagged will un-flag the cell
 * returning it back to a normal light gray cell
 * 
 * EXTRA CREDIT:
 * - the extra credit project we implemented is the ability to choose the difficulty documented
 * in more depth above. This functionality required more if statements in onMouseClick
 * to keep track of the x and y values of the mouse to make sure the correct box is clicked. As well
 * as changing all of values of the fields. We added a buildWelcomeScreen method that 
 * does exactly what it is named. It will build the screen that allows the user to select the level.
 * This is also done with the addition of another constructor that takes in no values because all 
 * of the fields are manually changed based on the user's selection.
 */
