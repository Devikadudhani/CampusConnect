package com.projectt.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.InputStream

class ComplaintActivity : AppCompatActivity() {

    private lateinit var imagePreview: ImageView
    private val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint)

        val buttonAttach = findViewById<ImageButton>(R.id.buttonAttachPhoto)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        imagePreview = findViewById(R.id.imagePreview)

        val checkboxLibrary = findViewById<CheckBox>(R.id.checkboxLibrary)
        val checkboxElectricity = findViewById<CheckBox>(R.id.checkboxElectricity)
        val checkboxCleanliness = findViewById<CheckBox>(R.id.checkboxCleanliness)
        val checkboxOthers = findViewById<CheckBox>(R.id.checkboxOthers)

        buttonAttach.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        buttonSubmit.setOnClickListener {
            val description = editTextDescription.text.toString().trim()

            if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Collect selected categories
            val selectedCategories = mutableListOf<String>()
            if (checkboxLibrary.isChecked) selectedCategories.add("Library")
            if (checkboxElectricity.isChecked) selectedCategories.add("Electricity")
            if (checkboxCleanliness.isChecked) selectedCategories.add("Cleanliness")
            if (checkboxOthers.isChecked) selectedCategories.add("Others")

            // Simulate submission
            Toast.makeText(this, "Complaint submitted!", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.nav_add_complaint // highlight current

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_suggestion -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_add_complaint -> {
                    // Already on this screen
                    true
                }
                R.id.nav_track_complaint -> {
                    Toast.makeText(this, "Track Complaint screen coming soon!", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_polls -> {
                    startActivity(Intent(this, SurveyActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageUri?.let {
                val inputStream: InputStream? = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imagePreview.setImageBitmap(bitmap)
                imagePreview.visibility = ImageView.VISIBLE
            }
        }
    }
}
