package io.zkz.fantasyfootball.printing

import io.zkz.fantasyfootball.IService
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.awt.print.Printable
import java.awt.print.PrinterJob
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.standard.MediaPrintableArea
import javax.print.attribute.standard.PrinterResolution

object PrintingService : IService {
    lateinit var printerName: String
    lateinit var labelSize: LabelSize
    var printerDpi = 203
    var rotateLabels = true
    var enabled = false

    val printerOptions = PrinterJob.lookupPrintServices().map { it.name }

    override fun initialize() {
        printerName = printerOptions.first()
        labelSize = LabelSize.FOUR_BY_ONE
    }

    fun print(labelData: LabelData) {
        if (!enabled) {
            println("Tried to print but printing is disabled")
            return
        }

        val service = PrinterJob.lookupPrintServices().find { it.name == printerName }

        val job = PrinterJob.getPrinterJob()
        job.printService = service

        val attributes = HashPrintRequestAttributeSet()
        attributes.add(PrinterResolution(printerDpi, printerDpi, PrinterResolution.DPI))
        attributes.add(
            MediaPrintableArea(
                0f,
                0f,
                labelSize.width * 25.4f,
                labelSize.height * 25.4f,
                MediaPrintableArea.MM
            )
        )

        val pageFormat = job.defaultPage()
        val paper = pageFormat.paper
        paper.setSize(labelSize.width * 72.0, labelSize.height * 72.0)
        paper.setImageableArea(0.0, 0.0, labelSize.width * 72.0, labelSize.height * 72.0)
        pageFormat.paper = paper

        job.setPrintable({ graphics, pf, page ->
            // Ensure we only have one page
            if (page > 0) {
                return@setPrintable Printable.NO_SUCH_PAGE
            }

            // Align the image properly
            val g = graphics as Graphics2D
            g.translate(pf.imageableX, pf.imageableY)
            if (rotateLabels) {
                g.rotate(Math.PI, pf.imageableWidth / 2, pf.imageableHeight / 2)
            }

            // Draw the label
            drawBackground(g, labelData.type)
            drawForeground(g, labelData)

            return@setPrintable Printable.PAGE_EXISTS
        }, pageFormat)

        job.print(attributes)
    }

    private fun drawBackground(g: Graphics2D, type: LabelType) {
        g.color = Color.black
        g.background = Color.black
        g.fill(Rectangle2D.Float(0f, 0f, labelSize.width * 72, labelSize.height * 72))

        val borderWidth = 8f
        g.color = Color.white
        g.background = Color.white
        g.fill(
            Rectangle2D.Float(
                borderWidth,
                borderWidth,
                labelSize.width * 72 - borderWidth * 2,
                labelSize.height * 72 - borderWidth * 2
            )
        )
    }

    private fun drawForeground(g: Graphics2D, data: LabelData) {
        g.color = Color.black
        g.font = Font("Arial", Font.BOLD, 36)
        g.drawString(data.title.uppercase(), 16, 54)
    }
}
