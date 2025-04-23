package com.example.myapplication1
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.RoleSelectionActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val btnLetsGo = findViewById<Button>(R.id.btn_lets_go)

        // Set up the fade-out animation
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 1000 // duration of 1 second
        fadeOut.setFillAfter(true) // Keep the final state after animation ends

        // When the button is clicked
        btnLetsGo.setOnClickListener {
            // Apply the fade-out animation to the root layout
            val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)
            rootLayout.startAnimation(fadeOut)

            // After animation ends, navigate to StudentLoginActivity
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    startActivity(Intent(this@SplashActivity, RoleSelectionActivity::class.java))
                    finish() // Close the splash activity
                }

            })
        }
    }
}