package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.erdemserhat.harmonyhaven.BuildConfig
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.FullScreenImage
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun ScreenShotQuote(quote: Quote) {

    Box {

        FullScreenImage(
            quote.imageUrl,
            modifier = Modifier
        )

        Box(
            modifier = Modifier
                .padding(15.dp)
                .wrapContentSize()
                .align(Alignment.Center)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Gray.copy(alpha = 0.5f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                .padding(0.dp) // İçerik için padding
        )
        {
            //  Image(painter = painterResource(id = R.drawable.a1), contentDescription =null,modifier = Modifier.align(
            //    Alignment.TopStart).padding(12.dp), colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.4f)))


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    color = Color.White.copy(0.9f),
                    text = quote.quote,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 30.sp // Satır yüksekliğini ayarlama
                )

                Text(
                    color = Color.White.copy(0.7f),
                    text = quote.writer,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = georgiaFont
                )

                Spacer(modifier = Modifier.size(15.dp))
            }
        }


    }


}

fun saveBitmapToFile(context: Context, bitmap: Bitmap) {
    // Save Bitmap to file
    val file = File(context.filesDir, "screenshot.png")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, out) // Compression with quality 10
    }
    Log.d("23esdsadsa", "Screenshot saved: ${file.absolutePath}")
}


fun saveBitmapToFile(context: Context, imageBitmap: ImageBitmap): File? {
    return try {
        val bitmap: Bitmap = imageBitmap.asAndroidBitmap()
        val fileName = "screenshot.jpeg"
        val file = File(context.filesDir, fileName) // Geçici dizine kaydet
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // %80 kalite
        }
        Log.d("23esdsadsa", "Screenshot saved: ${file.absolutePath}")
        file //
    } catch (e: Exception) {
        Log.e("23esdsadsa", "Error saving bitmap: ${e.message}")
        null
    }
}

fun shareImage(context: Context, file: File) {
    // File URI'sini FileProvider üzerinden oluştur
    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    // Paylaşım intent oluştur
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // URI izinleri
    }

    // Paylaşım ekranını başlat
    context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
}
fun shareToInstagramStory(context: Context, file: File) {

    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
    Log.d("FileProvider", "Generated URI: $uri")


    val storyIntent = Intent("com.instagram.share.ADD_TO_STORY").apply {
        type = "image/jpeg"
        putExtra("interactive_asset_uri", uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)

        // Instagram'a URI izni ver
        context.grantUriPermission(
            "com.instagram.android",
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION,
        )
    }

    // Instagram'ın yüklü olup olmadığını kontrol edin
    val packageManager = context.packageManager
    val isInstagramInstalled = try {
        packageManager.getPackageInfo("com.instagram.android", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    if (isInstagramInstalled) {
        context.startActivity(storyIntent)
    } else {
        Toast.makeText(context, "Instagram is not installed.", Toast.LENGTH_SHORT).show()
    }
}


fun shareToWhatsApp(context: Context, file: File) {
    val packageManager = context.packageManager
    val whatsappPackage = "com.whatsapp"

    try {
        // Check if WhatsApp is installed
        packageManager.getPackageInfo(whatsappPackage, 0)

        // Generate URI for the file
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        // Determine MIME type dynamically based on file extension
        val mimeType = when (file.extension.lowercase()) {
            "jpg", "jpeg", "png" -> "image/jpeg" // For images
            "mp4" -> "video/mp4" // For videos
            else -> throw IllegalArgumentException("Unsupported file type: ${file.extension}")
        }

        // Create the share intent
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType // Dynamically set MIME type
            putExtra(Intent.EXTRA_STREAM, uri) // Attach file URI
            `package` = whatsappPackage // Target WhatsApp
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant URI permission
        }

        // Start the share intent
        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        // If WhatsApp is not installed, notify the user
        Toast.makeText(context, "WhatsApp is not installed on this device.", Toast.LENGTH_SHORT).show()
    } catch (e: IllegalArgumentException) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        // Handle other exceptions
        Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}


fun shareToInstagram(context: Context, file: File) {
    try {

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)




        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, uri)
            `package` = "com.instagram.android"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }


        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        // Instagram yüklü değilse kullanıcıya bilgi ver
        Toast.makeText(context, "Instagram is not installed on this device.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        // Diğer hatalar için kullanıcıya bilgi ver
        Toast.makeText(context, "Failed to share on Instagram: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

fun shareToFacebook(context: Context, file: File) {
    try {
        // Create a URI using FileProvider
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        // Determine MIME type based on file extension
        val mimeType = when (file.extension.lowercase()) {
            "jpg", "jpeg", "png" -> "image/jpeg" // For images
            "mp4" -> "video/mp4" // For videos
            else -> throw IllegalArgumentException("Unsupported file type: ${file.extension}")
        }

        // Create the share intent
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType // Set the MIME type dynamically
            putExtra(Intent.EXTRA_STREAM, uri) // Attach the file URI
            `package` = "com.facebook.katana" // Target Facebook
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant URI permission
        }

        // Check if Facebook is installed
        context.packageManager.getPackageInfo("com.facebook.katana", 0)

        // Start the intent
        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(context, "Facebook is not installed.", Toast.LENGTH_SHORT).show()
    } catch (e: IllegalArgumentException) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("FacebookShare", "Error sharing to Facebook: ${e.message}")
        Toast.makeText(context, "Failed to share to Facebook.", Toast.LENGTH_SHORT).show()
    }
}


fun shareToFacebookStory(context: Context, file: File) {
    try {
        // FileProvider ile URI oluştur
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        Log.d("FacebookStory", "Generated URI: $uri")

        Log.d("FacebookStory", "Generated URI: $uri")
        Log.d("FacebookStory", "File Path: ${file.absolutePath}")
        Log.d("FacebookStory", "File Size: ${file.length()}")


        // Facebook hikayesine paylaşım intenti
        val intent = Intent("com.facebook.stories.ADD_TO_STORY").apply {
            type = "image/jpeg" // Görüntü MIME tipi
            putExtra("com.facebook.platform.extra.APPLICATION_ID", "1573810886567149") // Facebook App ID
            putExtra(Intent.EXTRA_STREAM, uri) // Resim URI
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // URI izni
        }

        // Facebook'un yüklü olup olmadığını kontrol et
        context.packageManager.getPackageInfo("com.facebook.katana", 0)

        context.grantUriPermission(
            "com.facebook.katana",
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        // Intent'i başlat
        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(context, "Facebook is not installed.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("FacebookStory", "Error sharing to Facebook story: ${e.message}")
        Toast.makeText(context, "Failed to share to Facebook story.", Toast.LENGTH_SHORT).show()
    }
}

fun resizeBitmap(original: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
    return Bitmap.createScaledBitmap(original, targetWidth, targetHeight, false)
}

fun shareToX(context: Context, file: File?, text: String = "") {
    try {
        if (file != null) {
            // Generate URI and determine MIME type
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val mimeType = when (file.extension.lowercase()) {
                "jpg", "jpeg", "png" -> "image/jpeg"
                "mp4" -> "video/mp4"
                else -> throw IllegalArgumentException("Unsupported file type: ${file.extension}")
            }

            // Create intent to share via X app
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, text)
                `package` = "com.twitter.android"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            // Check if X app is installed
            context.packageManager.getPackageInfo("com.twitter.android", 0)

            // Grant URI permission to X app
            context.grantUriPermission(
                "com.twitter.android",
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            // Start intent
            context.startActivity(intent)
        } else {
            // Fallback to web sharing if no file is provided
            shareToXWeb(context, text)
        }
    } catch (e: PackageManager.NameNotFoundException) {
        // Fallback to web sharing if X app is not installed
        shareToXWeb(context, text)
    } catch (e: IllegalArgumentException) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("XShare", "Error sharing to X: ${e.message}")
        Toast.makeText(context, "Failed to share to X.", Toast.LENGTH_SHORT).show()
    }
}

fun shareToXWeb(context: Context, text: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://twitter.com/intent/tweet?text=${Uri.encode(text)}")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("XShareWeb", "Error sharing to X web: ${e.message}")
        Toast.makeText(context, "Failed to share to X web.", Toast.LENGTH_SHORT).show()
    }
}




suspend fun downloadVideo(context: Context, videoUrl: String): File? {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val videoFile = File(context.filesDir, "shared_video.mp4")

        val request = Request.Builder()
            .url(videoUrl)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.let { responseBody ->
                    videoFile.outputStream().use { it.write(responseBody.bytes()) }
                    Log.d("VideoDownload", "Video saved to: ${videoFile.absolutePath}")
                    return@withContext videoFile
                }
            } else {
                Log.e("VideoDownload", "Server returned error: ${response.code}")
                return@withContext null
            }
        } catch (e: IOException) {
            Log.e("VideoDownload", "Failed to download video: ${e.message}")
            return@withContext null
        }
    }
}




fun shareVideo(context: Context, videoFile: File) {
    try {
        // Video dosyasının URI'sini FileProvider ile oluştur
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            videoFile
        )
        Log.d("VideoShare", "Generated URI: $uri")

        // Paylaşım intentini oluştur
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "video/mp4"
            putExtra(Intent.EXTRA_STREAM, uri) // Videoyu ekle
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // URI izni ver
        }

        // Kullanıcıya paylaşım seçeneklerini göster
        context.startActivity(Intent.createChooser(intent, "Share Video"))
    } catch (e: Exception) {
        Log.e("VideoShare", "Error sharing video: ${e.message}")
        Toast.makeText(context, "Failed to share video.", Toast.LENGTH_SHORT).show()
    }
}

fun shareToInstagram1(context: Context, file: File) {
    try {
        // Generate URI for the file
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

        // Determine the MIME type based on the file extension
        val mimeType = when (file.extension.lowercase()) {
            "jpg", "jpeg", "png" -> "image/jpeg" // For image files
            "mp4" -> "video/mp4" // For video files
            else -> throw IllegalArgumentException("Unsupported file type") // Unsupported type
        }

        // Create the intent for sharing
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType // Set the MIME type dynamically
            putExtra(Intent.EXTRA_STREAM, uri) // Attach the file URI
            `package` = "com.instagram.android" // Target Instagram
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant URI permissions
        }

        // Start the sharing intent
        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        // Instagram is not installed
        Toast.makeText(context, "Instagram is not installed on this device.", Toast.LENGTH_SHORT).show()
    } catch (e: IllegalArgumentException) {
        // Unsupported file type
        Toast.makeText(context, "Unsupported file type: ${file.extension}", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        // Handle other exceptions
        Toast.makeText(context, "Failed to share on Instagram: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

fun shareToInstagramStory1(context: Context, file: File) {
    try {
        // Generate URI for the file
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        Log.d("FileProvider", "Generated URI: $uri")
        Log.d("FileProvider", "File Path: ${file.absolutePath}")
        Log.d("FileProvider", "File Size: ${file.length()}")

        // Determine the MIME type based on the file extension
        val mimeType = when (file.extension.lowercase()) {
            "jpg", "jpeg", "png" -> "image/jpeg" // For image files
            "mp4" -> "video/mp4" // For video files
            else -> throw IllegalArgumentException("Unsupported file type") // Unsupported type
        }

        // Create Instagram Story Intent
        val storyIntent = Intent("com.instagram.share.ADD_TO_STORY").apply {
            type = mimeType // Set the MIME type dynamically
            putExtra("interactive_asset_uri", uri) // Attach the file URI
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant URI permission
        }

        // Grant URI permission to Instagram
        context.grantUriPermission(
            "com.instagram.android",
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        // Check if Instagram is installed
        val packageManager = context.packageManager
        val isInstagramInstalled = try {
            packageManager.getPackageInfo("com.instagram.android", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        if (isInstagramInstalled) {
            context.startActivity(storyIntent)
        } else {
            Toast.makeText(context, "Instagram is not installed.", Toast.LENGTH_SHORT).show()
        }
    } catch (e: IllegalArgumentException) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("InstagramStory", "Error sharing to Instagram story: ${e.message}")
        Toast.makeText(context, "Failed to share to Instagram story.", Toast.LENGTH_SHORT).show()
    }
}


fun shareToInstagramStoryx(context: Context, file: File) {
    try {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        // Verify file format
        val mimeType = "video/*"

        val storyIntent = Intent("com.instagram.share.ADD_TO_STORY").apply {
            type = mimeType
            putExtra("interactive_asset_uri", uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

            // Grant URI permission to Instagram
            context.grantUriPermission(
                "com.instagram.android",
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        // Check if Instagram is installed
        context.packageManager.getPackageInfo("com.instagram.android", 0)

        // Launch Instagram Stories
        context.startActivity(storyIntent)
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(context, "Instagram is not installed.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("InstagramStory", "Error sharing to Instagram story: ${e.message}")
        Toast.makeText(context, "Failed to share to Instagram story.", Toast.LENGTH_SHORT).show()
    }
}


fun shareVideo(activity: Context, sharedImgPath: String, pkgName: String) {
    val list = ArrayList<Uri>()
    list.add(
        FileProvider.getUriForFile(
            activity,
            BuildConfig.APPLICATION_ID + ".provider",
            File(sharedImgPath.replace("file://", "").trim { it <= ' ' })
        )
    )

    val intentImage = Intent(Intent.ACTION_SEND)
    intentImage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intentImage.putExtra(
        Intent.EXTRA_STREAM,
        FileProvider.getUriForFile(
            activity,
            BuildConfig.APPLICATION_ID + ".provider",
            File(sharedImgPath.replace("file://", "").trim { it <= ' ' })
        )
    )
    intentImage.setType("video/*")

    if (pkgName.length > 0) {
        intentImage.setPackage(pkgName)
    }
    activity.startActivity(intentImage)
}










