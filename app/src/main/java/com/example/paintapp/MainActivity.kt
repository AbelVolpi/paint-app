package com.example.paintapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.paintapp.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            shapeGroupLayout.setOnClickListener {
                showFormDialog()
            }
            eraseGroupLayout.setOnClickListener {
                simplePaint.cleanDraw()
            }
            selectColorGroupLayout.setOnClickListener {
                showColorPickerDialog()
            }
        }
    }

    private fun showFormDialog() {
        AlertDialog.Builder(this)
            .setTitle("Pick what draw")
            .setItems(R.array.forms) { _, which ->
                with(binding) {
                    when (which) {
                        0 -> {
                            simplePaint.changeDrawForm(SQUARE)
                        }
                        1 -> {
                            simplePaint.changeDrawForm(CIRCLE)
                        }
                        2 -> {
                            simplePaint.changeDrawForm(LINE)
                        }
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
            .setNegativeButton(getString(android.R.string.cancel)) { dialogInterface, _ -> dialogInterface?.dismiss() }
            .attachAlphaSlideBar(true)
            .attachBrightnessSlideBar(true)
            .setBottomSpace(12)
            .show()
    }

    private fun setColor(envelope: ColorEnvelope?) {
        with(binding) {
            if (envelope != null) {
                simplePaint.setCurrentPaintColor(Color.valueOf(envelope.color))
                selectColorIcon.setColorFilter((Color.valueOf(envelope.color)).toArgb())
            }
        }
    }

    companion object {
        const val SQUARE = "SQUARE"
        const val CIRCLE = "CIRCLE"
        const val LINE = "LINE"
    }
}
