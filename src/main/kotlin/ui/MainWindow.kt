package io.zkz.fantasyfootball.ui

import javax.swing.JFrame
import javax.swing.JTabbedPane

object MainWindow : JFrame() {
    init {
        title = "Sleeper Label Printer"
        setSize(800, 600)
        defaultCloseOperation = EXIT_ON_CLOSE

        add(JTabbedPane().apply {
            addTab("Configuration", ConfigurationPane)
            addTab("Current Draft", CurrentDraftPane)
        })
    }

    fun open() {
        isVisible = true
    }
}