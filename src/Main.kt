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
    // Constants defining any key values
    val MAX_CLICKS = 10

    // Data fields
    var clicks = 0

    // Application logic functions
    fun updateClickCount() {
        clicks++
        if (clicks > MAX_CLICKS) clicks = MAX_CLICKS
    }
}

class Locations(val name: String, val directions: List<String>, val treasure: Int) {

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



    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

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
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)
        var yCord: Int = 0

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
        currentLocation.bounds = Rectangle(289, 287, 50, 50)
        currentLocation.background = Color.GRAY
        add(currentLocation)

        northLocation = JButton()
        northLocation.bounds = Rectangle(289, 219, 50, 50)
        northLocation.addActionListener(this)
        northLocation.background = Color.GRAY
        add(northLocation)

        eastLocation = JButton()
        eastLocation.bounds = Rectangle(357, 287, 50, 50)
        eastLocation.addActionListener(this)
        eastLocation.background = Color.GRAY
        add(eastLocation)

        westLocation = JButton()
        westLocation.bounds = Rectangle(221, 287, 50, 50)
        westLocation.addActionListener(this)
        westLocation.background = Color.GRAY
        add(westLocation)

        southLocation = JButton()
        southLocation.bounds = Rectangle(289, 355, 50, 50)
        southLocation.addActionListener(this)
        southLocation.background = Color.GRAY
        add(southLocation)


    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        if (currentLocation.bounds.y == 287) southLocation.isVisible = false
        else southLocation.isVisible = true

        if (currentLocation.bounds.y == 83) northLocation.isVisible = false
        else northLocation.isVisible = true

        if (currentLocation.bounds.x == 425) eastLocation.isVisible = false
        else eastLocation.isVisible = true

        if (currentLocation.bounds.x == 153) westLocation.isVisible = false
        else westLocation.isVisible = true

    }
    fun moveNorth(){
        var yCord = currentLocation.bounds.y
        var xCord = currentLocation.bounds.x

        yCord -= 68
        currentLocation.bounds = Rectangle(xCord, yCord, 50, 50)
        westLocation.bounds = Rectangle(westLocation.bounds.x, yCord, 50, 50)
        eastLocation.bounds = Rectangle(eastLocation.bounds.x, yCord, 50, 50)
        northLocation.bounds = Rectangle(northLocation.bounds.x, northLocation.bounds.y - 68, 50, 50)
        southLocation.bounds = Rectangle(southLocation.bounds.x, southLocation.bounds.y - 68, 50, 50)

    }
    fun moveSouth(){
        var yCord = currentLocation.bounds.y
        var xCord = currentLocation.bounds.x

        yCord += 68
        currentLocation.bounds = Rectangle(xCord, yCord, 50, 50)
        westLocation.bounds = Rectangle(westLocation.bounds.x, yCord, 50, 50)
        eastLocation.bounds = Rectangle(eastLocation.bounds.x, yCord, 50, 50)
        northLocation.bounds = Rectangle(northLocation.bounds.x, northLocation.bounds.y + 68, 50, 50)
        southLocation.bounds = Rectangle(southLocation.bounds.x, southLocation.bounds.y + 68, 50, 50)
    }
    fun moveEast(){
        var yCord = currentLocation.bounds.y
        var xCord = currentLocation.bounds.x

        xCord += 68
        currentLocation.bounds = Rectangle(xCord, yCord, 50, 50)
        westLocation.bounds = Rectangle(westLocation.bounds.x + 68, yCord, 50, 50)
        eastLocation.bounds = Rectangle(eastLocation.bounds.x + 68, yCord, 50, 50)
        northLocation.bounds = Rectangle(northLocation.bounds.x + 68, northLocation.bounds.y, 50, 50)
        southLocation.bounds = Rectangle(southLocation.bounds.x + 68, southLocation.bounds.y, 50, 50)
    }
    fun moveWest(){
        var yCord = currentLocation.bounds.y
        var xCord = currentLocation.bounds.x

        xCord -= 68
        currentLocation.bounds = Rectangle(xCord, yCord, 50, 50)
        westLocation.bounds = Rectangle(westLocation.bounds.x - 68, yCord, 50, 50)
        eastLocation.bounds = Rectangle(eastLocation.bounds.x - 68, yCord, 50, 50)
        northLocation.bounds = Rectangle(northLocation.bounds.x - 68, northLocation.bounds.y, 50, 50)
        southLocation.bounds = Rectangle(southLocation.bounds.x - 68, southLocation.bounds.y, 50, 50)
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
        when (e?.keyCode) {
            KeyEvent.VK_UP    -> moveNorth()
            KeyEvent.VK_DOWN  -> moveSouth()
            KeyEvent.VK_LEFT   -> moveEast()
            KeyEvent.VK_RIGHT  -> moveWest()
        }
        updateView()

    }

    override fun keyPressed(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

    override fun keyReleased(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

}

