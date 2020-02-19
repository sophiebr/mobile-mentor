package edu.rosehulman.brusniss.mobilementor.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import edu.rosehulman.brusniss.mobilementor.BitmapUtils
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class ProfileFragment : Fragment() {

    private val userRef = FirebaseFirestore.getInstance().document(User.firebasePath)
    private var currentPhotoPath = ""
    private val storageRef = FirebaseStorage.getInstance().reference.child(Constants.IMAGES_PATH)
    private lateinit var profileView: View

    init {
        userRef.addSnapshotListener() { snapshot: DocumentSnapshot?, exception: FirebaseFirestoreException? ->
            val user = ProfileModel.fromSnapshot(snapshot!!)
            profileView.profile_name_text.text = user.name
            profileView.profile_level.text = when (user.permissionLevel) {
                PermissionLevel.ADMIN -> getString(R.string.admin)
                PermissionLevel.MENTOR -> getString(R.string.mentor)
                PermissionLevel.PROFESSOR -> getString(R.string.prof)
                else -> getString(R.string.reg)
            }
            if (user.pictureUrl.isNotBlank()) {
                Picasso.get().load(user.pictureUrl).resize(1000, 1000).centerInside().into(profileView.default_profile_image)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileView = inflater.inflate(R.layout.fragment_profile, container, false)
        Log.d(Constants.TAG, userRef.path)
        profileView.change_image_button.setOnClickListener {
            showPictureDialog()
        }
        profileView.profile_edit_name.setOnClickListener {
            showChangeNameDialog()
        }
        profileView.profile_name_text.setOnClickListener {
            showChangeNameDialog()
        }
        return profileView
    }

    private fun showChangeNameDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showPictureDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(getString(R.string.photo_source))
        builder.setMessage(getString(R.string.photo_source_msg))
        builder.setPositiveButton(getString(R.string.take_pic)) { _, _ ->
            launchCameraIntent()
        }

        builder.setNegativeButton(getString(R.string.choose_pic)) { _, _ ->
            launchChooseIntent()
        }
        builder.create().show()
    }

    // Everything camera- and storage-related is from
    // https://developer.android.com/training/camera/photobasics
    private fun launchCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    // authority declared in manifest
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "edu.rosehulman.brusniss.mobilementor",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, Constants.RC_TAKE_PICTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun launchChooseIntent() {
        // https://developer.android.com/guide/topics/providers/document-provider
        val choosePictureIntent = Intent(
            Intent.ACTION_OPEN_DOCUMENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        choosePictureIntent.addCategory(Intent.CATEGORY_OPENABLE)
        choosePictureIntent.type = "image/*"
        if (choosePictureIntent.resolveActivity(context!!.packageManager) != null) {
            startActivityForResult(choosePictureIntent, Constants.RC_CHOOSE_PICTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.RC_TAKE_PICTURE -> {
                    sendCameraPhotoToAdapter()
                }
                Constants.RC_CHOOSE_PICTURE -> {
                    sendGalleryPhotoToAdapter(data)
                }
            }
        }
    }

    private fun sendCameraPhotoToAdapter() {
        addPhotoToGallery()
        Log.d(Constants.TAG, "Sending to adapter this photo: $currentPhotoPath")
        //adapter.add(currentPhotoPath)
        //val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
        //storageAdd(currentPhotoPath, bitmap)
        ImageRescaleTask(currentPhotoPath).execute()
    }

    private fun sendGalleryPhotoToAdapter(data: Intent?) {
        if (data != null && data.data != null) {
            val location = data.data!!.toString()
            //adapter.add(location)
            //val bitmap = BitmapFactory.decodeFile(location)
            //storageAdd(location, bitmap)
            ImageRescaleTask(location).execute()
        }
    }

    // Works Not working on phone
    private fun addPhotoToGallery() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            activity!!.sendBroadcast(mediaScanIntent)
        }
    }

    private fun storageAdd(localPath: String, bitmap: Bitmap?) {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val id = Math.abs(Random.nextLong()).toString()
        var uploadTask = storageRef.child(id).putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d(Constants.TAG, "Image upload failed: $localPath")
        }
        uploadTask.addOnSuccessListener {
            Log.d(Constants.TAG, "Image upload succeded: $localPath")
        }
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.child(id).downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                // Delete the old image before uploading a new one
                userRef.get().addOnSuccessListener {
                    val url = it.get("pictureUrl") as String
                    if (url.isNotBlank()) {
                        FirebaseStorage.getInstance().getReferenceFromUrl(url).delete()
                    }
                }
                userRef.update("pictureUrl", downloadUri.toString())
                Log.d(Constants.TAG, "Image download succeeded: $downloadUri")
            } else {
                Log.d(Constants.TAG, "Image download failed")
            }
        }
    }

    // Could save a smaller version to Storage to save time on the network.
    // But if too small, recognition accuracy can suffer.
    inner class ImageRescaleTask(val localPath: String) : AsyncTask<Void, Void, Bitmap>() {
        override fun doInBackground(vararg p0: Void?): Bitmap? {
            // Reduces length and width by a factor (currently 2).
            val ratio = 2
            return BitmapUtils.rotateAndScaleByRatio(context!!, localPath, ratio)
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            // TODO: Write and call a new storageAdd() method with the path and bitmap
            // that uses Firebase storageRef.
            // https://firebase.google.com/docs/storage/android/upload-files
            storageAdd(localPath, bitmap)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(this.context!!)
    }
}