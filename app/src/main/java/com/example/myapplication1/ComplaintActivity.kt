package com.example.myapplication1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ComplaintActivity : AppCompatActivity() {

    private lateinit var imagePreview: ImageView
    private val PICK_IMAGE = 1
    private var imageUri: Uri? = null
    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

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

            val selectedCategories = mutableListOf<String>()
            if (checkboxLibrary.isChecked) selectedCategories.add("Library")
            if (checkboxElectricity.isChecked) selectedCategories.add("Electricity")
            if (checkboxCleanliness.isChecked) selectedCategories.add("Cleanliness")
            if (checkboxOthers.isChecked) selectedCategories.add("Others")

            if (imageUri != null) {
                val fileName = "complaint_images/${UUID.randomUUID()}.jpg"
                val imageRef = storage.reference.child(fileName)

                imageRef.putFile(imageUri!!)
                    .addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            saveComplaint(description, selectedCategories, imageUrl)
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Image upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.e("UploadError", "Image upload failed", e)
                    }
            } else {
                saveComplaint(description, selectedCategories, null)
            }
        }

        setupBottomNavigation()
    }

    private fun saveComplaint(description: String, categories: List<String>, imageUrl: String?) {
        val complaintData = hashMapOf(
            "description" to description,
            "categories" to categories,
            "imageUrl" to imageUrl,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("complaints")
            .add(complaintData)
            .addOnSuccessListener {
                Toast.makeText(this, "Complaint submitted!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to submit complaint: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.nav_add_complaint
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_suggestion -> {
                    startActivity(Intent(this, StudentHomeActivity::class.java))
                    true
                }
                R.id.nav_add_complaint -> true
                R.id.nav_track_complaint -> {
                    startActivity(Intent(this, TrackComplaintActivity::class.java))
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
            imageUri = data.data
            imagePreview.setImageURI(imageUri)
            imagePreview.visibility = ImageView.VISIBLE
        }
    }
}
