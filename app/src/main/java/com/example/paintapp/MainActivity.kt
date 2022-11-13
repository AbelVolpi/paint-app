package com.example.paintapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class MainActivity : AppCompatActivity() {

    private lateinit var simplePaint: SimplePaint
    private lateinit var imageViewColorPicker: ImageView
    private lateinit var textViewCleanDraw: TextView
    private lateinit var textViewChangeForm: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simplePaint = findViewById(R.id.simplePaint)
        textViewChangeForm = findViewById(R.id.textViewChangeForm)
        textViewChangeForm.setOnClickListener {
            showFormDialog()
        }
        textViewCleanDraw = findViewById(R.id.textViewCleanDraw)
        textViewCleanDraw.setOnClickListener {
            simplePaint.cleanDraw()
        }
        imageViewColorPicker = findViewById(R.id.imageViewColorPicker)
        imageViewColorPicker.setOnClickListener {
            showColorPickerDialog()
        }
    }

    private fun showFormDialog() {
        AlertDialog.Builder(this)
            .setTitle("Pick what draw")
            .setItems(R.array.forms) { _, which ->
                when (which) {
                    0 -> {
                        simplePaint.changeDrawForm("SQUARE")
                    }
                    1 -> {
                        simplePaint.changeDrawForm("CIRCLE")
                    }
                    2 -> {
                        simplePaint.changeDrawForm("LINE")
                    }
                }
            }
            .show()
    }

    private fun showColorPickerDialog() {
        ColorPickerDialog.Builder(this)
            .setTitle("ColorPickerDialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton(getString(android.R.string.ok), object : ColorEnvelopeListener {
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    setColor(envelope)
                }
            })
            .setNegativeButton(getString(android.R.string.cancel)) { dialogInterface, p1 -> dialogInterface?.dismiss() }
            .attachAlphaSlideBar(true)
            .attachBrightnessSlideBar(true)
            .setBottomSpace(12)
            .show()
    }

    private fun setColor(envelope: ColorEnvelope?) {
        if (envelope != null) {
            simplePaint.setCurrentPaintColor(Color.valueOf(envelope.color))
            imageViewColorPicker.setColorFilter((Color.valueOf(envelope.color)).toArgb())
        }
    }
}
