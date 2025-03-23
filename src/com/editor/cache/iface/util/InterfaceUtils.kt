package com.editor.cache.iface.util

import com.alex.defs.interfaces.ComponentDefinition

/**
 * Utility object providing helper functions for components and interfaces.
 * It includes methods for checking visibility of components, retrieving parent components,
 * and parsing script and configuration arrays.
 */
object InterfaceUtils {
    /**
     * Checks if the given component is hidden or if it is inside a hidden parent.
     *
     * It first checks if the component itself is hidden, and then recursively checks its parent components.
     * If any parent component is hidden, the function returns `true`.
     *
     * @param component The component to check.
     * @return `true` if the component or any of its parents are hidden, otherwise `false`.
     */
    @JvmStatic
    fun isHidden(component: ComponentDefinition): Boolean {
        if (component.hidden) return true
        var parent = getParent(component.parentId)
        var hidden = false
        while (parent != null) {
            if (parent.hidden) {
                hidden = true
                break
            }
            parent = getParent(parent.parentId)
        }
        return hidden
    }

    /**
     * Retrieves the parent component based on its hash.
     *
     * The hash is split into the interface ID and component ID. If the hash is `-1`, it returns `null`.
     *
     * @param parentHash The hash representing the parent component.
     * @return The parent `ComponentDefinition`, or `null` if the parent does not exist.
     */
    @JvmStatic
    fun getParent(parentHash: Int): ComponentDefinition? {
        if (parentHash == -1) return null
        val interfaceId = parentHash shr 16
        val baseHash = interfaceId shl 16
        val componentId = parentHash - baseHash
        return ComponentDefinition.getInterfaceComponent(interfaceId, componentId)
    }

    /**
     * Turns a string into an array of objects representing script inputs.
     *
     * The input string is expected to contain values separated by semicolons (`;`).
     * The function attempts to parse each value as an integer, and if it succeeds, stores it as an `Int`.
     * If parsing fails, it stores the value as a `String`.
     *
     * @param input The input string to parse.
     * @return An array of objects representing the parsed script inputs, or `null` if the input is invalid.
     */
    @JvmStatic
    fun getScriptArray(input: String?): Array<Any?>? {
        if (input == null || input === "" || input === " ") return null
        val values = input.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val objs = arrayOfNulls<Any>(values.size)
        try {
            values[0].toInt()
        } catch (exp: Exception) {
            // Return null if the first value isn't a number
            return null
        }
        for (i in values.indices) {
            try {
                val x = values[i].toInt()
                objs[i] = x
            } catch (ex: Exception) {
                objs[i] = values[i]
            }
        }
        return objs
    }

    /**
     * Turns a string into an array of integers representing configuration values.
     *
     * The input string is expected to contain values separated by semicolons (`;`).
     * Each value is parsed into an integer.
     *
     * @param input The input string to parse.
     * @return An array of integers representing the parsed configuration values, or `null` if the input is invalid.
     */
    @JvmStatic
    fun getConfigArray(input: String?): IntArray? {
        if (input == null || input.isEmpty() || input.equals(" ", ignoreCase = true)) return null
        val values = input.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val intArray = IntArray(values.size)
        for (i in values.indices) {
            intArray[i] = values[i].toInt()
        }
        return intArray
    }
}
