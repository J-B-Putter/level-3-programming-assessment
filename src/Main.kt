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
class MainWindow(val app: App) : JFrame(), ActionListener {

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
        eastLocation.bounds = Rectangle(221, 287, 50, 50)
        eastLocation.addActionListener(this)
        eastLocation.background = Color.GRAY
        add(eastLocation)

        westLocation = JButton()
        westLocation.bounds = Rectangle(357, 287, 50, 50)
        westLocation.addActionListener(this)
        westLocation.background = Color.GRAY
        add(westLocation)

        southLocation = JButton()
        southLocation.bounds = Rectangle(289, 355, 50, 50)
        southLocation.addActionListener(this)
        southLocation.background = Color.GRAY
        add(southLocation)

        if (currentLocation.bounds.y == 287) remove(southLocation)

    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        if (app.clicks == app.MAX_CLICKS) {
            clicksLabel.text = "Max clicks reached!"
            clickButton.isEnabled = false
        }
        else {
            clicksLabel.text = "You clicked ${app.clicks} times"
            clickButton.isEnabled = true
        }
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        var yCord = currentLocation.bounds.y
        var xCord = currentLocation.bounds.x
        when (e?.source) {
            northLocation -> {
                yCord -= 68
                currentLocation.bounds.y = yCord

            }
        }
    }

}

