package com.alex.util

import java.awt.Component
import java.awt.Container
import javax.swing.Spring
import javax.swing.SpringLayout

/*
* Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*   - Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*
*   - Redistributions in binary form must reproduce the above copyright
*     notice, this list of conditions and the following disclaimer in the
*     documentation and/or other materials provided with the distribution.
*
*   - Neither the name of Oracle or the names of its
*     contributors may be used to endorse or promote products derived
*     from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
* IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
* PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/**
 * A 1.4 file that provides utility methods for
 * creating form- or grid-style layouts with SpringLayout.
 * These utilities are used by several programs, such as
 * SpringBox and SpringCompactGrid.
 */
object SpringUtilities {
    /**
     * A debugging utility that prints to stdout the component's
     * minimum, preferred, and maximum sizes.
     *
     * @param c the c
     */
    @JvmStatic
    fun printSizes(c: Component) {
        println("minimumSize = " + c.minimumSize)
        println("preferredSize = " + c.preferredSize)
        println("maximumSize = " + c.maximumSize)
    }

    /**
     * Aligns the first `rows` * `cols`
     * components of `parent` in
     * a grid. Each component is as big as the maximum
     * preferred width and height of the components.
     * The parent is made just big enough to fit them all.
     *
     * @param parent   the parent
     * @param rows     number of rows
     * @param cols     number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad     x padding between cells
     * @param yPad     y padding between cells
     */
    @JvmStatic
    fun makeGrid(
        parent: Container,
        rows: Int,
        cols: Int,
        initialX: Int,
        initialY: Int,
        xPad: Int,
        yPad: Int,
    ) {
        val layout: SpringLayout
        try {
            layout = parent.layout as SpringLayout
        } catch (exc: ClassCastException) {
            System.err.println("The first argument to makeGrid must use SpringLayout.")
            return
        }

        val xPadSpring = Spring.constant(xPad)
        val yPadSpring = Spring.constant(yPad)
        val initialXSpring = Spring.constant(initialX)
        val initialYSpring = Spring.constant(initialY)
        val max = rows * cols

        // Calculate Springs that are the max of the width/height so that all
        // cells have the same size.
        var maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth()

        var maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getHeight()

        for (i in 1 until max) {
            val cons =
                layout.getConstraints(
                    parent.getComponent(i),
                )

            maxWidthSpring = Spring.max(maxWidthSpring, cons.width)
            maxHeightSpring = Spring.max(maxHeightSpring, cons.height)
        }

        // Apply the new width/height Spring. This forces all the
        // components to have the same size.
        for (i in 0 until max) {
            val cons =
                layout.getConstraints(
                    parent.getComponent(i),
                )

            cons.width = maxWidthSpring
            cons.height = maxHeightSpring
        }

        // Then adjust the x/y constraints of all the cells so that they
        // are aligned in a grid.
        var lastCons: SpringLayout.Constraints? = null
        var lastRowCons: SpringLayout.Constraints? = null
        for (i in 0 until max) {
            val cons =
                layout.getConstraints(
                    parent.getComponent(i),
                )
            if (i % cols == 0) { // start of new row
                lastRowCons = lastCons
                cons.x = initialXSpring
            } else { // x position depends on previous component
                cons.x =
                    Spring.sum(
                        lastCons!!.getConstraint(SpringLayout.EAST),
                        xPadSpring,
                    )
            }

            if (i / cols == 0) { // first row
                cons.y = initialYSpring
            } else { // y position depends on previous row
                cons.y =
                    Spring.sum(
                        lastRowCons!!.getConstraint(SpringLayout.SOUTH),
                        yPadSpring,
                    )
            }
            lastCons = cons
        }

        // Set the parent's size.
        val pCons = layout.getConstraints(parent)
        pCons.setConstraint(
            SpringLayout.SOUTH,
            Spring.sum(
                Spring.constant(yPad),
                lastCons!!.getConstraint(SpringLayout.SOUTH),
            ),
        )
        pCons.setConstraint(
            SpringLayout.EAST,
            Spring.sum(
                Spring.constant(xPad),
                lastCons.getConstraint(SpringLayout.EAST),
            ),
        )
    }

    // Used by makeCompactGrid.
    private fun getConstraintsForCell(
        row: Int,
        col: Int,
        parent: Container,
        cols: Int,
    ): SpringLayout.Constraints {
        val layout = parent.layout as SpringLayout
        val c = parent.getComponent(row * cols + col)
        return layout.getConstraints(c)
    }

    /**
     * Aligns the first `rows` * `cols`
     * components of `parent` in
     * a grid. Each component in a column is as wide as the maximum
     * preferred width of the components in that column;
     * height is similarly determined for each row.
     * The parent is made just big enough to fit them all.
     *
     * @param parent   the parent
     * @param rows     number of rows
     * @param cols     number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad     x padding between cells
     * @param yPad     y padding between cells
     */
    @JvmStatic
    fun makeCompactGrid(
        parent: Container,
        rows: Int,
        cols: Int,
        initialX: Int,
        initialY: Int,
        xPad: Int,
        yPad: Int,
    ) {
        val layout: SpringLayout
        try {
            layout = parent.layout as SpringLayout
        } catch (exc: ClassCastException) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.")
            return
        }

        // Align all cells in each column and make them the same width.
        var x = Spring.constant(initialX)
        for (c in 0 until cols) {
            var width = Spring.constant(0)
            for (r in 0 until rows) {
                width =
                    Spring.max(
                        width,
                        getConstraintsForCell(r, c, parent, cols).width,
                    )
            }
            for (r in 0 until rows) {
                val constraints = getConstraintsForCell(r, c, parent, cols)
                constraints.x = x
                constraints.width = width
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)))
        }

        // Align all cells in each row and make them the same height.
        var y = Spring.constant(initialY)
        for (r in 0 until rows) {
            var height = Spring.constant(0)
            for (c in 0 until cols) {
                height =
                    Spring.max(
                        height,
                        getConstraintsForCell(r, c, parent, cols).height,
                    )
            }
            for (c in 0 until cols) {
                val constraints = getConstraintsForCell(r, c, parent, cols)
                constraints.y = y
                constraints.height = height
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)))
        }

        // Set the parent's size.
        val pCons = layout.getConstraints(parent)
        pCons.setConstraint(SpringLayout.SOUTH, y)
        pCons.setConstraint(SpringLayout.EAST, x)
    }
}
