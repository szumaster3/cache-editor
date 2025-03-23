package com.editor.cache.iface.sprites

import java.util.logging.ConsoleHandler
import java.util.logging.Logger

/**
 * Interface tool
 * paolo 04/08/2019
 * #Shnek6969
 */
object LogFactory {
    @JvmStatic
    fun createLogger(name: String?): Logger {
        val logger = Logger.getLogger(name)
        logger.useParentHandlers = false
        val handler = ConsoleHandler()
        logger.addHandler(handler)
        return logger
    }
}
