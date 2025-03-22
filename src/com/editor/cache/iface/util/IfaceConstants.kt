package com.editor.cache.iface.util

import java.awt.Color

object IfaceConstants {
    const val LEFT_SCROLLPANE_WIDTH: Int = 229
    const val RIGHT_SCROLLPANE_WIDTH: Int = 300
    const val CONTENT_PADDING: Int = 10
    const val SEARCHBOX_WIDTH: Int = 68
    const val SEARCHBOX_HEIGHT: Int = 32
    const val BUTTON_WIDTH: Int = 115
    const val BUTTON_HEIGHT: Int = 32
    const val BUTTON_HEIGHT_SMALL: Int = 24
    const val BUTTON_WIDTH_SMALL: Int = 75

    const val DEFAULT_EDITOR_WIDTH: Int = 1366
    const val DEFAULT_EDITOR_HEIGHT: Int = 768
    const val TOPROW_HEIGHT: Int = CONTENT_PADDING + SEARCHBOX_HEIGHT
    const val PREMADE_COMP_WIDTH: Int = LEFT_SCROLLPANE_WIDTH
    const val PREMADE_COMP_HEIGHT: Int = 112
    const val INFO_FIELD_WIDTH: Int = 300
    const val INFO_FIELD_HEIGHT: Int = BUTTON_HEIGHT_SMALL
    @JvmField
	val BG_FILL_COLOR: Color = Color.lightGray
    const val VIEWPORT_WIDTH: Int = DEFAULT_EDITOR_WIDTH - LEFT_SCROLLPANE_WIDTH - RIGHT_SCROLLPANE_WIDTH
    const val VIEWPORT_HEIGHT: Int = 503
}