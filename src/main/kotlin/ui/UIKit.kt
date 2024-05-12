package io.zkz.fantasyfootball.ui

import java.awt.Component
import java.awt.Container
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun Container.addLabel(
    text: String,
    setup: (JLabel.() -> Unit)? = null,
    textAlign: Int = SwingConstants.LEFT,
    alignmentX: Float = Component.LEFT_ALIGNMENT,
): JLabel {
    return add(JLabel(text, textAlign).apply {
        this.alignmentX = alignmentX

        if (setup != null) {
            this.setup()
        }
    }) as JLabel
}

fun Container.addTextField(
    columns: Int? = null,
    alignmentX: Float = Component.LEFT_ALIGNMENT,
    initialText: String? = null,
    setup: (JTextField.() -> Unit)? = null,
    onChange: ((value: String) -> Unit)? = null,
): JTextField {
    return add(JTextField().apply {
        val textField = this
        this.alignmentX = alignmentX

        if (columns != null) {
            this.columns = columns
        }

        if (initialText != null) {
            this.text = initialText
        }

        if (onChange != null) {
            document.addDocumentListener(object : DocumentListener {
                override fun insertUpdate(e: DocumentEvent?) {
                    onChange(textField.text)
                }

                override fun removeUpdate(e: DocumentEvent?) {
                    onChange(textField.text)
                }

                override fun changedUpdate(e: DocumentEvent?) {
                    onChange(textField.text)
                }
            })
        }

        if (setup != null) {
            this.setup()
        }
    }) as JTextField
}

inline fun <reified E> Container.addDropdown(
    options: List<E>,
    selectedOption: E? = null,
    labels: List<String>? = null,
    alignmentX: Float = Component.LEFT_ALIGNMENT,
    noinline setup: (JComboBox<E>.() -> Unit)? = null,
    noinline onChange: ((value: E) -> Unit)? = null,
): JComboBox<E> {
    @Suppress("UNCHECKED_CAST")
    return add(JComboBox(options.toTypedArray()).apply {
        val dropdown = this
        this.alignmentX = alignmentX

        if (selectedOption != null) {
            this.selectedItem = selectedOption
        }

        if (onChange != null) {
            addItemListener {
                onChange(dropdown.selectedItem as E)
            }
        }

        setRenderer(object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(
                list: JList<*>?,
                value: Any?,
                index: Int,
                isSelected: Boolean,
                cellHasFocus: Boolean,
            ): Component {
                val label = if (labels != null) labels[options.indexOf(value)] else value

                return super.getListCellRendererComponent(list, label, index, isSelected, cellHasFocus)
            }
        })

        if (setup != null) {
            this.setup()
        }
    }) as JComboBox<E>
}

fun Container.addCheckbox(
    label: String,
    initialValue: Boolean = false,
    alignmentX: Float = Component.LEFT_ALIGNMENT,
    setup: (JCheckBox.() -> Unit)? = null,
    onChange: ((value: Boolean) -> Unit)? = null,
): JCheckBox {
    return add(JCheckBox(label, initialValue).apply {
        val checkbox = this
        this.alignmentX = alignmentX

        if (onChange != null) {
            addItemListener {
                onChange(checkbox.isSelected)
            }
        }

        if (setup != null) {
            this.setup()
        }
    }) as JCheckBox
}

fun Container.addButton(
    text: String,
    setup: (JButton.() -> Unit)? = null,
    onPress: (() -> Unit)? = null,
): JButton {
    return add(JButton(text).apply {
        if (onPress != null) {
            addActionListener { onPress() }
        }

        if (setup != null) {
            this.setup()
        }
    }) as JButton
}

fun Container.addVerticalSpace(
    height: Int = 10,
) {
    add(Box.createVerticalStrut(height))
}

fun Container.addHorizontalSpace(
    height: Int = 10,
) {
    add(Box.createVerticalStrut(height))
}

fun Container.addGroup(
    title: String,
    setup: (Container.() -> Unit),
): JPanel {
    return add(JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        border =
            BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title), EmptyBorder(5, 5, 5, 5))
        this.setup()
    }) as JPanel
}