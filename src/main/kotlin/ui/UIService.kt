package io.zkz.fantasyfootball.ui

import com.formdev.flatlaf.FlatDarkLaf
import io.zkz.fantasyfootball.IService

object UIService : IService {
    lateinit var thread: Thread
        private set

    override fun initialize() {
        FlatDarkLaf.setup()

        thread = Thread(::buildUI).apply(Thread::start)
    }

    private fun buildUI() {
        MainWindow.open()
    }

    fun rebuildDraftPane() {
        CurrentDraftPane.rebuild()
    }
}