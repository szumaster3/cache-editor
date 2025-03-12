package console

import java.awt.EventQueue
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * The type Main.
 */
object Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
        Console.redirectSystemStreams()
        EventQueue.invokeLater {
            Console().isVisible = true
            ToolSelection().isVisible = true
        }
    }

    /**
     * Log.
     *
     * @param className the class name
     * @param message   the message
     */
    @JvmStatic
    fun log(className: String, message: String) {
        println("[$className]: $message")
        printDebug(className, message)
    }

    private fun printDebug(className: String, message: String) {
        val f = File("logs.txt")

        try {
            if (!f.exists()) {
                f.createNewFile()
            }

            val strFilePath = f.absolutePath

            FileOutputStream(strFilePath, true).use { var9 ->
                val strContent = Date().toString() + ": [" + className + "]: " + message
                val lineSep = System.getProperty("line.separator")
                var9.write(strContent.toByteArray())
                var9.write(lineSep.toByteArray())
            }
        } catch (var8: IOException) {
            log("Main", "IOException: " + var8.message)
        }
    }
}
