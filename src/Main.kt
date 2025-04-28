/**
 * =====================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   SAILING ADVENTURE
 * Project Author: JANNO PUTTER
 * GitHub Repo:    https://github.com/J-B-Putter/level-3-programming-assessment
 * ---------------------------------------------------------------------
 * Notes:
 * Write your notes etc. in here.
 * =====================================================================
 */



import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import java.io.File
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // constants
    val GRID_SIZE = 68
    val MARGIN = 18
    val MAP_WIDTH = 8
    val MAP_HEIGHT = 5
    val SQUARE_SIZE = GRID_SIZE - MARGIN
    val TREASURE_CLUES_X = 3
    val TREASURE_CLUES_Y = 3

    // app data
    var playerX = 2
    var playerY = 4
    var playerCollectX = 0
    var playerCollectY = 0

    var treasureX = 0
    var treasureY = 0

    /**
     * Creating lists for different clues
     */
    //X Coordinate clues
    val XcluesX = mutableListOf<Int>()
    val XcluesY = mutableListOf<Int>()
    //Y Coordinate clues
    val YcluesY = mutableListOf<Int>()
    val YcluesX = mutableListOf<Int>()

    var cluesDiscovered = 0

    init {
        placeTreasure()
    }

    fun placeTreasure() {
        //Place treasure
        treasureX = (0..<MAP_WIDTH).random()
        treasureY = (0..<MAP_HEIGHT).random()

        //Place Clues
        XcluesX.clear()
        XcluesY.clear()
        YcluesX.clear()
        YcluesY.clear()
        repeat (TREASURE_CLUES_X) {
            XcluesX.add((0..<MAP_WIDTH).random())
            XcluesY.add((0..<MAP_HEIGHT).random())
        }
        repeat(TREASURE_CLUES_Y) {
            YcluesY.add((0..<MAP_HEIGHT).random())
            YcluesX.add((0..<MAP_WIDTH).random())
        }


    }

    /**
     * Handles movement input
     */
    fun moveNorth() {
        playerY--
        if (playerY < 0) playerY = 0
        checkForClue()
    }
    fun moveSouth() {
        playerY++
        if (playerY > MAP_HEIGHT) playerY = MAP_HEIGHT
        checkForClue()
    }
    fun moveEast() {
        playerX++
        if (playerX > MAP_WIDTH) playerX = MAP_WIDTH
        checkForClue()
    }
    fun moveWest() {
        playerX--
        if (playerX < 0) playerX = 0
        checkForClue()
    }

    fun checkForClue(): Int {
        if (playerX in XcluesX && playerY in XcluesY) {
            println("Found clue X")
            XcluesX.remove(playerX)
            XcluesY.remove(playerY)
            playerCollectX++
            return 1
        }
        if (playerY in YcluesY && playerX in YcluesX) {
            println("Found clue Y")
            YcluesX.remove(playerX)
            YcluesY.remove(playerY)
            playerCollectY++
            return 2
        }
        else return 0
    }
    fun playerFoundTreasure(): Boolean {
        if (playerX == treasureX && playerY == treasureY) {
            println("Payer on Treasure Square")
            return true
        }
        else return false
    }

    /**
     * Messages in the dialogField
     */
    val TUTORIAL_MESSAGE = "Welcome to Sailing Adventure!\n" +
            "\n" +
            "This is a game focused on exploration and collecting elements to achieve a goal. \n" +
            "You are the captain of a ship and you're looking for a sunken treasure. Clues are scattered around the map. You need to find them all to reveal the location of the treasure. \n" +
            "\n" +
            "How to Play:\n" +
            "- To move around, use the arrow keys or click on the arrow-buttons.\n" +
            "- Move around the map and search for clues. A message will pop up in the box if you are on the location of a clue (keep your eyes open for it!). Once you moved over the clue it automatically collects it for you. \n" +
            "- If you found all the clues for the X coordinate of the treasure, it will display as a line on the map. This is the same for the Y coordinate. \n" +
            "- When both X and Y coordinates are found, you can go to the location where they intersect to collect the treasure!"

    val FOUND_TREASURE_MESSAGE = "Congratulations! You found the treasure, the loot,\n the fortune of a thousand islands!"
    val FOUND_BOTH_POSITIONS_MESSAGE = "You found both coordinates! The treasure lies where the two lines intersect."
    val FOUND_CLUE_MESSAGE = "Well done! You found a clue. One step closer to finding the treasure..."
    val XPOSITION_VISIBLE_MESSAGE = "You just discovered the X-Coordinate of the treasure! This means that the treasure lies somewhere on that vertical line."
    val YPOSITION_VISIBLE_MESSAGE = "You just discovered the Y-Coordinate of the treasure! This means that the treasure lies somewhere on that horizontal line."
    val NOTHING_MESSAGE = "Open ocean..."
}

/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener, KeyListener {

    // Fields to hold the UI elements
    //Locations
    private lateinit var currentLocation: JLabel
    private lateinit var northLocation: JButton
    private lateinit var eastLocation: JButton
    private lateinit var westLocation: JButton
    private lateinit var southLocation: JButton
    private lateinit var xPosition: JPanel
    private lateinit var yPosition: JPanel
    //Visual Assistants
    private lateinit var dialogField: JTextArea
    private lateinit var backGround: JLabel



    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI\

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        showTutorial()                  //Showing tutorial
        updateView()                    // Initialise the UI
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(1000, 500)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }


    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 14)
        val startPositionX = app.playerX * app.GRID_SIZE + app.MARGIN
        val startPositionY = app.playerY * app.GRID_SIZE + app.MARGIN
        //Image icons
        var backGroundImage = ImageIcon("src/images/wavesBG.jpg").image
        val playerIconImage = ImageIcon("src/images/ship-icon-23.png").image
        var northArrowImage = ImageIcon("src/images/north-arrow-compass-5287 (2).png").image
        var eastArrowImage = ImageIcon("src/images/east-arrow-compass-5287 (2).png").image
        var westArrowImage = ImageIcon("src/images/west-arrow-compass-5287 (2).png").image
        var southArrowImage = ImageIcon("src/images/south-arrow-compass-5287 (2).png").image

        this.addKeyListener(this)

        //Locations
        currentLocation = JLabel()
        currentLocation.bounds = Rectangle(startPositionX, startPositionY, app.SQUARE_SIZE, app.SQUARE_SIZE)
        currentLocation.background = Color.GRAY
        currentLocation.icon = ImageIcon(playerIconImage)
        currentLocation.isFocusable = false
        add(currentLocation)

        northLocation = JButton(ImageIcon(northArrowImage))
        northLocation.bounds = Rectangle(startPositionX, startPositionY - app.GRID_SIZE, app.SQUARE_SIZE, app.SQUARE_SIZE)
        northLocation.addActionListener(this)
        northLocation.isFocusable = false
        northLocation.background = Color(0,0,0,0)
        northLocation.border = BorderFactory.createEmptyBorder()
        northArrowImage = northArrowImage.getScaledInstance(northLocation.width, northLocation.height, Image.SCALE_SMOOTH)
        northLocation.icon = ImageIcon(northArrowImage)
        add(northLocation)

        eastLocation = JButton()
        eastLocation.bounds = Rectangle(startPositionX + app.GRID_SIZE, startPositionY, app.SQUARE_SIZE, app.SQUARE_SIZE)
        eastLocation.addActionListener(this)
        eastLocation.isFocusable = false
        eastLocation.background = Color(0,0,0,0)
        eastLocation.border = BorderFactory.createEmptyBorder()
        eastArrowImage = eastArrowImage.getScaledInstance(eastLocation.width, eastLocation.height, Image.SCALE_SMOOTH)
        eastLocation.icon = ImageIcon(eastArrowImage)
        add(eastLocation)

        westLocation = JButton()
        westLocation.bounds = Rectangle(startPositionX - app.GRID_SIZE, startPositionY, app.SQUARE_SIZE, app.SQUARE_SIZE)
        westLocation.addActionListener(this)
        westLocation.isFocusable = false
        westLocation.background = Color(0,0,0,0)
        westLocation.border = BorderFactory.createEmptyBorder()
        westArrowImage = westArrowImage.getScaledInstance(westLocation.width, westLocation.height, Image.SCALE_SMOOTH)
        westLocation.icon = ImageIcon(westArrowImage)
        add(westLocation)

        southLocation = JButton()
        southLocation.bounds = Rectangle(startPositionX + app.GRID_SIZE, startPositionY, app.SQUARE_SIZE, app.SQUARE_SIZE)
        southLocation.addActionListener(this)
        southLocation.isFocusable = false
        southLocation.background = Color(0,0,0,0)
        southLocation.border = BorderFactory.createEmptyBorder()
        southArrowImage = southArrowImage.getScaledInstance(southLocation.width, southLocation.height, Image.SCALE_SMOOTH)
        southLocation.icon = ImageIcon(southArrowImage)
        add(southLocation)

        //Text field for messages and such
        dialogField = JTextArea()
        dialogField.font = baseFont
        dialogField.bounds = Rectangle(app.MAP_WIDTH * app.GRID_SIZE + app.MARGIN + 100, app.MARGIN/2, 250, 336)
        dialogField.background = Color.GRAY
        dialogField.foreground = Color.BLACK
        dialogField.lineWrap = true
//        dialogField.
        dialogField.isFocusable = false
        dialogField.isEditable = false
        add(dialogField)

        //Treasure Visual Coordinates
        xPosition = JPanel()
        xPosition.background = Color(170,0,0)
        xPosition.isVisible = false
        xPosition.isFocusable = false
        add(xPosition)

        yPosition = JPanel()
        yPosition.background = Color(170,0,0)
        yPosition.isVisible = false
        yPosition.isFocusable = false
        add(yPosition)

        //Visual Map
        backGround = JLabel()
        backGround.bounds = Rectangle(app.MARGIN/2, app.MARGIN/2, app.MAP_WIDTH * app.GRID_SIZE, app.MAP_HEIGHT * app.GRID_SIZE)
        backGroundImage = backGroundImage.getScaledInstance(app.MAP_WIDTH * app.GRID_SIZE, app.MAP_HEIGHT * app.GRID_SIZE, Image.SCALE_SMOOTH)
        backGround.icon = ImageIcon(backGroundImage)
        backGround.isFocusable = false
        add(backGround)

        updateView()
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        val yCord = app.playerY * app.GRID_SIZE + app.MARGIN
        val xCord = app.playerX * app.GRID_SIZE + app.MARGIN
        val size = app.SQUARE_SIZE

        currentLocation.bounds = Rectangle(xCord, yCord, size, size)
        westLocation.bounds = Rectangle(xCord - app.GRID_SIZE, yCord, size, size)
        eastLocation.bounds = Rectangle(xCord + app.GRID_SIZE, yCord, size, size)
        northLocation.bounds = Rectangle(xCord, yCord - app.GRID_SIZE, size, size)
        southLocation.bounds = Rectangle(xCord, yCord + app.GRID_SIZE, size, size)

        northLocation.isVisible = (app.playerY > 0)
        southLocation.isVisible = (app.playerY < app.MAP_HEIGHT - 1)
        eastLocation.isVisible = (app.playerX < app.MAP_WIDTH - 1)
        westLocation.isVisible = (app.playerX > 0)

        if (app.playerCollectX == app.TREASURE_CLUES_X) {
            showTreasure()
        }
        if (app.playerCollectY == app.TREASURE_CLUES_Y) {
            showTreasure()
        }
        findClues()
        this.requestFocus()

        checkTreasureFound()
    }
    fun showTutorial(){
        dialogField.text = app.TUTORIAL_MESSAGE
    }

    /**
     * Connecting the App class to the controls
     * and Updating the UI as changes are made
     */
    fun moveNorth() {
        app.moveNorth()
        updateView()
    }
    fun moveSouth(){
        app.moveSouth()
        updateView()
     }
    fun moveEast(){
        app.moveEast()
        updateView()
   }
    fun moveWest(){
        app.moveWest()
        updateView()
    }

    /**
     * Events based on Certain conditions
     * and State of UI elements
     */
    fun showTreasure(){
        val xPos = app.treasureX * app.GRID_SIZE + app.MARGIN + ((app.SQUARE_SIZE - 2)/2)
        val yPos = app.treasureY * app.GRID_SIZE + app.MARGIN + ((app.SQUARE_SIZE - 2)/2)
        xPosition.bounds = Rectangle(xPos, backGround.bounds.y, 4, app.MAP_HEIGHT * app.GRID_SIZE)
        yPosition.bounds = Rectangle(backGround.bounds.x, yPos, app.MAP_WIDTH * app.GRID_SIZE, 4)

        if (app.playerCollectX == app.TREASURE_CLUES_X) {
            xPosition.isVisible = true
            if (xPosition.isVisible && !yPosition.isVisible) dialogField.text = app.XPOSITION_VISIBLE_MESSAGE
            else dialogField.text = ""
        }
        if (app.playerCollectY == app.TREASURE_CLUES_Y) {
            yPosition.isVisible = true
            if (!xPosition.isVisible && yPosition.isVisible) dialogField.text = app.YPOSITION_VISIBLE_MESSAGE
            else dialogField.text = ""
        }
        if (xPosition.isVisible && yPosition.isVisible) dialogField.text = app.FOUND_BOTH_POSITIONS_MESSAGE
    }
    fun findClues(){
        if (app.checkForClue() == 1 || app.checkForClue() == 2) dialogField.text = app.FOUND_CLUE_MESSAGE
        if (dialogField.text == app.TUTORIAL_MESSAGE) dialogField.text = app.TUTORIAL_MESSAGE
        else dialogField.text = app.NOTHING_MESSAGE
    }
    fun checkTreasureFound() {
        if (app.playerFoundTreasure() == true && xPosition.isVisible && yPosition.isVisible) {
            dialogField.text = app.FOUND_TREASURE_MESSAGE
        }
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            northLocation -> {
                moveNorth()
                updateView()
            }
            southLocation -> {
                moveSouth()
                updateView()
            }
            eastLocation -> {
                moveEast()
                updateView()
            }
            westLocation -> {
                moveWest()
                updateView()
            }
        }

    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_UP    ->  if (northLocation.isVisible) app.moveNorth()
            KeyEvent.VK_DOWN  -> if(southLocation.isVisible) app.moveSouth()
            KeyEvent.VK_LEFT   -> if(westLocation.isVisible) app.moveWest()
            KeyEvent.VK_RIGHT  -> if(eastLocation.isVisible) app.moveEast()
        }
        updateView()

    }
    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyReleased(e: KeyEvent?) {
    }

}

