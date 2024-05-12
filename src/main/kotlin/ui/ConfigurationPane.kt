package io.zkz.fantasyfootball.ui

import io.zkz.fantasyfootball.printing.LabelData
import io.zkz.fantasyfootball.printing.LabelSize
import io.zkz.fantasyfootball.printing.LabelType
import io.zkz.fantasyfootball.printing.PrintingService
import io.zkz.fantasyfootball.sleeper.SleeperService
import java.awt.Color
import java.awt.FlowLayout
import javax.swing.*

object ConfigurationPane : JPanel(FlowLayout(FlowLayout.LEFT)) {
    private lateinit var draftIdField: JTextField
    private lateinit var confirmationLabel: JLabel

    private lateinit var labelSizeDropdown: JComboBox<LabelSize>
    private lateinit var printerDropdown: JComboBox<String>
    private lateinit var testPrintButton: JButton

    init {
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)

            addGroup("Sleeper") {
                // Draft ID
                addLabel("Draft ID")
                draftIdField = addTextField(columns = 30)

                // Verify Button
                addVerticalSpace()
                addButton("Verify Connection") {
                    verifySleeperConnection()
                }

                // Confirmation Label
                addVerticalSpace()
                confirmationLabel = addLabel("Connection not yet verified.")
            }

            addGroup("Printing") {
                addCheckbox("Printing enabled", PrintingService.enabled) {
                    PrintingService.enabled = it
                    togglePrinterFormFields()
                }

                // Label Size
                addVerticalSpace()
                addLabel("Label Size")
                labelSizeDropdown = addDropdown(
                    LabelSize.entries,
                    PrintingService.labelSize,
                    labels = LabelSize.entries.map { it.label },
                ) {
                    PrintingService.labelSize = it
                }

                // Printer
                addVerticalSpace()
                addLabel("Printer")
                printerDropdown = addDropdown(PrintingService.printerOptions, PrintingService.printerName) {
                    PrintingService.printerName = it
                }

                // Test Print Button
                addVerticalSpace()
                testPrintButton = addButton("Test Print") {
                    testPrint()
                }
            }
        })

        togglePrinterFormFields()
    }

    private fun verifySleeperConnection() {
        confirmationLabel.foreground = null
        confirmationLabel.text = "Testing connection..."

        try {
            SleeperService.setDraftId(draftIdField.text)
        } catch (error: Exception) {
            System.err.println(error)

            confirmationLabel.foreground = Color.red
            confirmationLabel.text = "Connection failed!"

            return
        }

        confirmationLabel.foreground = Color.green
        confirmationLabel.text = "Connection verified!"

        CurrentDraftPane.rebuild()
    }

    private fun togglePrinterFormFields() {
        labelSizeDropdown.isEnabled = PrintingService.enabled
        printerDropdown.isEnabled = PrintingService.enabled
        testPrintButton.isEnabled = PrintingService.enabled
    }

    private fun testPrint() {
        PrintingService.print(
            LabelData(
                "Test Print", null, null, LabelType.ALL_BLACK
            )
        )
    }
}