package com.example.myapplication1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BarChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var chartData: Map<String, List<Int>> = emptyMap()  // Ensure we are storing the data correctly

    // Correct the setChartData method to accept Map<String, List<Int>>
    fun setChartData(data: Map<String, List<Int>>) {
        chartData = data
        invalidate()  // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply { color = Color.BLUE }

        // Draw the bars based on the data
        var left = 0f
        val barWidth = 100f
        val spacing = 20f

        // Loop through the data to display each month's data as a bar
        for ((month, data) in chartData) {
            val barHeight = data.sum() * 50f  // Scale the data (sum of values in the list)

            val top = height - barHeight
            val right = left + barWidth
            val bottom = height.toFloat()

            // Draw the rectangle representing the bar
            canvas.drawRect(left, top, right, bottom, paint)

            // Move to the next bar
            left += barWidth + spacing
        }
    }
}