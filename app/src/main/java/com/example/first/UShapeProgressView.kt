package com.example.first

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator // Make sure this is imported at the top



class UShapeProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.LTGRAY
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    private val solvedPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.parseColor("#7E57C2") // purple
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    private val pendingPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.parseColor("#F44336") // red
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }
    private var currentAnimatedSolved = 0

    fun animateProgress() {
        val animator = ValueAnimator.ofInt(0, solved)
        animator.duration = 1000 // duration in milliseconds

        animator.addUpdateListener { valueAnimator ->
            currentAnimatedSolved = valueAnimator.animatedValue as Int
            invalidate() // Redraw view with updated value
        }

        animator.start()
    }


    var total = 4
    var solved = 3
    var pending = 1

    override fun onDraw(canvas: Canvas) {


        super.onDraw(canvas)
        val sweepAngle = 180f * (currentAnimatedSolved.toFloat() / total)


        val padding = 80f
        val rect = RectF(padding, padding, width - padding, height * 2f)

        // Draw background arc
        canvas.drawArc(rect, 135f, 270f, false, backgroundPaint)

        val totalSweep = 270f
        val solvedSweep = (currentAnimatedSolved.toFloat() / total) * totalSweep
        val pendingSweep = (pending.toFloat() / total) * totalSweep

        // Draw solved arc
        canvas.drawArc(rect, 135f, solvedSweep, false, solvedPaint)

        // Draw pending arc after solved arc
        canvas.drawArc(rect, 135f + solvedSweep, pendingSweep, false, pendingPaint)
    }
}
