package com.example.paintapp

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.paintapp.MainActivity.Companion.CIRCLE
import com.example.paintapp.MainActivity.Companion.LINE
import com.example.paintapp.MainActivity.Companion.SQUARE

class SimplePaint(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var cleanDraw = false
    private var drawForm = LINE
    private var pathList = mutableListOf<Path>()
    private var paintList = mutableListOf<Paint>()

    private var currentColor: ColorDrawable = ColorDrawable().apply {
        color = Color.BLACK
    }

    private var currentPath = Path()
    private var currentPaint = Paint().apply {
        strokeWidth = 20F
        style = Paint.Style.STROKE
    }

    init {
        initLayerDraw()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paintList.forEachIndexed { index, _ ->
            canvas?.drawPath(pathList[index], paintList[index])
        }
        canvas?.drawPath(currentPath, currentPaint)

        if (cleanDraw) {
            canvas?.drawColor(Color.WHITE)
            cleanDraw = false
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val localX = event?.x
        val localY = event?.y

        if (localX != null && localY != null)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    when (drawForm) {
                        CIRCLE -> {
                            currentPath.addCircle(localX, localY, 100F, Path.Direction.CW)
                        }
                        SQUARE -> {
                            currentPath.addRect(RectF(localX - 70, localY - 70, localX + 70, localY + 70), Path.Direction.CW)
                        }
                        LINE -> {
                            currentPath.moveTo(localX, localY)
                            currentPath.lineTo(localX, localY)
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (drawForm == LINE)
                        currentPath.lineTo(localX, localY)
                }
                MotionEvent.ACTION_UP -> {
                    if (drawForm == LINE)
                        currentPath.lineTo(localX, localY)

                    paintList.add(currentPaint)
                    pathList.add(currentPath)
                    initLayerDraw()
                }
            }
        invalidate()
        return true
    }

    private fun initLayerDraw() {
        currentPath = Path()
        currentPaint = Paint().apply {
            strokeWidth = 20F
            style = Paint.Style.STROKE
            color = currentColor.color
        }
    }

    fun setCurrentPaintColor(color: Color) {
        currentColor.color = color.toArgb()
        currentPaint.color = color.toArgb()
    }

    fun cleanDraw() {
        cleanDraw = true
        paintList.clear()
        pathList.clear()
        invalidate()
    }

    fun changeDrawForm(form: String) {
        drawForm = form
    }
}
