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
import java.awt.Font.SANS_SERIF
import java.awt.event.*
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
    val MAP_WIDTH = 6
    val MAP_HEIGHT = 5

    // app data
    var playerX = 2
    var playerY = 4

    var treasureX = 0
    var treasureY = 0

    init {
        placeTreasure()
    }

    fun placeTreasure() {

    }

    fun moveNorth() {
        playerY--
        if (playerY < 0) playerY = 0
    }
    fun moveSouth() {
        playerY++
        if (playerY > MAP_HEIGHT) playerY = MAP_HEIGHT
    }
    fun moveEast() {
        playerX++
        if (playerX > MAP_WIDTH) playerX = MAP_WIDTH
    }
    fun moveWest() {
        playerX--
        if (playerX < 0) playerX = 0
    }

//    fun playerFoundTreasure(): Boolean {
//
//    }
}

class Locations(val name: String, val treasure: Boolean) {

}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener, KeyListener {

    // Fields to hold the UI elements
    private lateinit var clicksLabel: JLabel
    private lateinit var clickButton: JButton
    //Locations
    private lateinit var currentLocation: JPanel
    private lateinit var northLocation: JButton
    private lateinit var eastLocation: JButton
    private lateinit var westLocation: JButton
    private lateinit var southLocation: JButton
    private lateinit var dialogField: JTextArea
    private lateinit var backGround: JPanel
    private lateinit var xPosition: JPanel
    private lateinit var yPosition: JPanel



    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI\
        placeTreasure()

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        //updateView()                    // Initialise the UI
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
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

        clicksLabel = JLabel("CLICK INFO HERE")
        clicksLabel.horizontalAlignment = SwingConstants.CENTER
        clicksLabel.bounds = Rectangle(50, 50, 500, 100)
        clicksLabel.font = baseFont
        //add(clicksLabel)

        clickButton = JButton("Click Me!")
        clickButton.bounds = Rectangle(50,200,500,100)
        clickButton.font = baseFont
        clickButton.addActionListener(this)     // Handle any clicks
        //add(clickButton)

        //Locations
        currentLocation = JPanel()
        currentLocation.bounds = Rectangle(289, 355, 50, 50)
        currentLocation.background = Color.GRAY
        add(currentLocation)

        northLocation = JButton()
        northLocation.bounds = Rectangle(289, 287, 50, 50)
        northLocation.addActionListener(this)
        northLocation.background = Color.GRAY
        //northLocation.icon
        add(northLocation)

        eastLocation = JButton()
        eastLocation.bounds = Rectangle(357, 355, 50, 50)
        eastLocation.addActionListener(this)
        eastLocation.background = Color.GRAY
        //eastLocation.icon
        add(eastLocation)

        westLocation = JButton()
        westLocation.bounds = Rectangle(221, 355, 50, 50)
        westLocation.addActionListener(this)
        westLocation.background = Color.GRAY
        //westLocation.icon
        add(westLocation)

        southLocation = JButton()
        southLocation.bounds = Rectangle(289, 423, 50, 50)
        southLocation.addActionListener(this)
        southLocation.background = Color.GRAY
        //southLocation.icon
        add(southLocation)

        dialogField = JTextArea()
        dialogField.font = Font(SANS_SERIF, Font.PLAIN, 14)
        dialogField.bounds = Rectangle(647, 76, 250, 336)
        dialogField.background = Color.GRAY
        add(dialogField)

        xPosition = JPanel()
        xPosition.background = Color.RED
        xPosition.bounds = Rectangle(0, 76, 10, 405)
        xPosition.isVisible = false
        add(xPosition)

        yPosition = JPanel()
        yPosition.background = Color.RED
        yPosition.bounds = Rectangle(146, 0, 336, 10)
        yPosition.isVisible = false
        add(yPosition)

        backGround = JPanel()
        backGround.background = Color(0,105, 148)
        //backGround.icon
        backGround.bounds = Rectangle(app.MARGIN/2, app.MARGIN/2, app.MAP_WIDTH * app.GRID_SIZE, app.MAP_HEIGHT * app.GRID_SIZE)
        add(backGround)


    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
//    fun updateView() {
//
//        if (currentLocation.bounds.y == 355) {
//            southLocation.isVisible = false
//        }
//        else southLocation.isVisible = true
//
//        northLocation.isVisible = (currentLocation.bounds.y != 83)
//
//        if (currentLocation.bounds.x == 493) eastLocation.isVisible = false
//        else eastLocation.isVisible = true
//
//        if (currentLocation.bounds.x == 153) westLocation.isVisible = false
//        else westLocation.isVisible = true
//
//        //Showing Treasure Positions
//
//
//    }

    fun updateView() {
        val yCord = app.playerY * app.GRID_SIZE + app.MARGIN
        val xCord = app.playerX * app.GRID_SIZE + app.MARGIN
        val size = 50

        currentLocation.bounds = Rectangle(xCord, yCord, size, size)
        westLocation.bounds = Rectangle(xCord - app.GRID_SIZE, yCord, size, size)
        eastLocation.bounds = Rectangle(xCord + app.GRID_SIZE, yCord, size, size)
        northLocation.bounds = Rectangle(xCord, yCord - app.GRID_SIZE, size, size)
        southLocation.bounds = Rectangle(xCord, yCord + app.GRID_SIZE, size, size)

        northLocation.isVisible = (app.playerY > 0)
        southLocation.isVisible = (app.playerY < app.MAP_HEIGHT - 1)
        eastLocation.isVisible = (app.playerX < app.MAP_WIDTH - 1)
        westLocation.isVisible = (app.playerX > 0)

    }
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
    fun placeTreasure(){
        val treasureX = listOf(153, 221, 298, 357, 425, 493)
        val treasureY = listOf(83, 151, 219, 287, 355)


        xPosition.bounds = Rectangle(treasureX.random(), 78, 10, 336)
        yPosition.bounds = Rectangle(146,treasureY.random(), 405, 10)

        println("Treasure X: ${xPosition.bounds.x}")
        println("Treasure Y: ${yPosition.bounds.y}")

        xPosition.isVisible = true
        yPosition.isVisible = true
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

    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_UP    -> moveNorth()
            KeyEvent.VK_DOWN  -> moveSouth()
            KeyEvent.VK_LEFT   -> moveEast()
            KeyEvent.VK_RIGHT  -> moveWest()
        }
        updateView()

    }

    override fun keyReleased(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

}

