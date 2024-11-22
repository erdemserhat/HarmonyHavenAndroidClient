package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share

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
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.FullScreenImage
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


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
        file // Dosyanın yolunu döndür
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

        // Instagram'a URI izni ver
        context.grantUriPermission(
            "com.instagram.android",
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
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
        val installedPackages = context.packageManager.getInstalledPackages(0)
        installedPackages.forEach { packageInfo ->
            Log.d("InstalledPackage", packageInfo.packageName)
        }


        // Paket mevcut mu kontrol et
        packageManager.getPackageInfo(whatsappPackage, 0)

        // Paylaşım için intent oluştur
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            `package` = whatsappPackage
        }

        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        // Eğer WhatsApp yüklü değilse, kullanıcıya bildirim göster
        Toast.makeText(context, "WhatsApp is not installed on this device.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        // Diğer hatalar için bir hata mesajı göster
        Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

fun shareToInstagram(context: Context, file: File) {
    try {
        // FileProvider ile URI oluştur
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

        // Instagram paylaşım intenti oluştur
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg" // Statik MIME tipi
            putExtra(Intent.EXTRA_STREAM, uri)
            `package` = "com.instagram.android" // Sadece Instagram için intent
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // URI izni
        }

        // Intent başlat
        context.startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        // Instagram yüklü değilse kullanıcıya bilgi ver
        Toast.makeText(context, "Instagram is not installed on this device.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        // Diğer hatalar için kullanıcıya bilgi ver
        Toast.makeText(context, "Failed to share on Instagram: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}










