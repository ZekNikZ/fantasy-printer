package io.zkz.fantasyfootball.ui

import io.zkz.fantasyfootball.shutdown
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
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

        addWindowListener(object: WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                shutdown()
            }
        })
    }

    fun open() {
        isVisible = true
    }
}