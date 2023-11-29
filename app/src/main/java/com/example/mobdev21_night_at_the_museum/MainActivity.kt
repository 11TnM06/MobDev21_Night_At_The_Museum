package com.example.mobdev21_night_at_the_museum

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val storage = FirebaseStorage.getInstance()
        val modelRef = storage.reference.child("table.glb")

        val arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        findViewById<Button>(R.id.downloadBtn).setOnClickListener {
            try {
                val file = File.createTempFile("table", "glb")
                val task = modelRef.getFile(file)
                task.addOnSuccessListener { buildModel(file) }
                task.addOnFailureListener { e: Exception -> Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_SHORT).show() }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_SHORT).show()
            }
        }

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.renderable = renderable
            arFragment.arSceneView.scene.addChild(anchorNode)
        }
    }

    private var renderable: ModelRenderable? = null

    private fun buildModel(file: File) {
        val renderableSource = RenderableSource
            .builder()
            .setSource(this, Uri.parse(file.path), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable
            .builder()
            .setSource(this, renderableSource)
            .setRegistryId(file.path)
            .build()
            .thenAccept { modelRenderable: ModelRenderable? ->
                Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show()
                renderable = modelRenderable
            }
    }
}
