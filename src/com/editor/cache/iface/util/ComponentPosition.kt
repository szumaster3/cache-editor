package com.editor.cache.iface.util

import com.alex.defs.interfaces.ComponentDefinition

/**
 * This object handles the positioning and resizing of user interface components based on their aspect ratios
 * relative to their parent container. It adjusts the component's position and size based on various aspect
 * types for both width and height, ensuring that components stay within the bounds of their parent container.
 */
object ComponentPosition {
    /**
     * Adjusts the position of the component based on its aspectX and aspectY types relative to its parent
     * container's width and height.
     *
     * @param component The component to be positioned.
     * @param parentWidth The width of the parent container.
     * @param parentHeight The height of the parent container.
     */
    private fun realPosition(
        component: ComponentDefinition,
        parentWidth: Int,
        parentHeight: Int,
    ) {
        // Positioning the component based on aspectXType
        when (component.aspectXType.toInt()) {
            0 -> component.positionX = component.basePositionX
            1 -> component.positionX = (component.basePositionX + (parentWidth - component.width) / 2)
            2 -> component.positionX = (parentWidth - component.width - component.basePositionX)
            3 -> component.positionX = (parentWidth * (component.basePositionX) shr 14)
            4 -> component.positionX =
                ((parentWidth - component.width) / 2 + (parentWidth * (component.basePositionX) shr 14))

            else -> component.positionX =
                (parentWidth - component.width - (component.basePositionX * parentWidth shr 14))
        }

        // Positioning the component based on aspectYType
        when (component.aspectYType.toInt()) {
            0 -> component.positionY = component.basePositionY
            1 -> component.positionY = (component.basePositionY + (parentHeight - component.height) / 2)
            2 -> component.positionY = (parentHeight - component.height - component.basePositionY)
            3 -> component.positionY = (component.basePositionY * parentHeight shr 14)
            4 -> component.positionY =
                ((component.basePositionY * parentHeight shr 14) + (parentHeight - component.height) / 2)

            else -> component.positionY =
                (parentHeight - component.height - (component.basePositionY * parentHeight shr 14))
        }

        // Ensure component stays within the bounds of its parent container
        if (component.positionX < 0) component.positionX = 0
        if ((component.positionX + component.width) > parentWidth) component.positionX = (parentWidth - component.width)
        if (component.positionY < 0) component.positionY = 0
        if ((component.positionY + component.height) > parentHeight) component.positionY =
            (parentHeight - component.height)
    }

    /**
     * Adjusts the width and height of the component based on its aspectWidthType and aspectHeightType
     * relative to the provided width and height values.
     *
     * @param component The component to be resized.
     * @param width The width value to use for resizing.
     * @param height The height value to use for resizing.
     */
    private fun realAfmeting(
        component: ComponentDefinition,
        width: Int,
        height: Int,
    ) {
        // Adjusting the component width based on aspectWidthType
        when (component.aspectWidthType.toInt()) {
            0 -> component.width = component.baseWidth
            1 -> component.width = (width - component.baseWidth)
            2 -> component.width = (width * (component.baseWidth) shr 14)
        }

        // Adjusting the component height based on aspectHeightType
        when (component.aspectHeightType.toInt()) {
            0 -> component.height = component.baseHeight
            1 -> component.height = (height - component.baseHeight)
            2 -> component.height = (height * (component.baseHeight) shr 14)
        }

        // Ensuring minimum dimensions for the component
        if (component.type == 0) {
            if (component.height < 5 && component.width < 5) {
                component.height = 5
                component.width = 5
            } else {
                if (component.height <= 0) component.height = 5
                if (component.width <= 0) component.width = 5
            }
        }
    }

    /**
     * Recursively sets the values of a component (position and size) relative to its parent container's size.
     *
     * @param component The component whose values need to be set.
     */
    @JvmStatic
    fun setValues(component: ComponentDefinition?) {
        if (component == null) return

        // Get the parent component (if any)
        val parent = InterfaceUtils.getParent(component.parentId)

        // Determine the width and height based on the parent's dimensions
        val (width, height) =
            if (parent == null) {
                IfaceConstants.VIEWPORT_WIDTH to IfaceConstants.VIEWPORT_HEIGHT
            } else {
                setValues(parent)
                parent.width to parent.height
            }

        // Apply the calculated width and height to the component
        realAfmeting(component, width, height)
        realPosition(component, width, height)
    }
}
