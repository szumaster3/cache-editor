package com.editor.cache.iface.util

import com.alex.defs.interfaces.ComponentDefinition

/**
 * Helper object providing utility functions for handling components in the interface.
 *
 * Contains methods to check if a component has specific scripts associated with it, such as a scroll bar or button.
 *
 * @author: Paolo
 * @date: 04/08/2019
 */
object ContainerHelper {
    /**
     * Checks if the given component has a scroll bar script associated with it.
     *
     * @param component The component to check.
     * @return `true` if the component has a scroll bar script, `false` otherwise.
     */
    @JvmStatic
    fun isScrollBar(component: ComponentDefinition): Boolean {
        // Check if the component's onLoadScript matches the scroll bar script constant
        return component.onLoadScript != null && component.onLoadScript[0].toString()
            .toInt() == ComponentConstants.SCROLL_BAR_SCRIPT
    }

    /**
     * Checks if the given component is a button.
     *
     * @param component The component to check.
     * @return `true` if the component is a button, `false` otherwise.
     */
    @JvmStatic
    fun isButton(component: ComponentDefinition): Boolean {
        // Check if the component's onLoadScript matches the button script constant
        return component.onLoadScript != null && component.onLoadScript[0].toString()
            .toInt() == ComponentConstants.BUTTON_SCRIPT
    }
}
