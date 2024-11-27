package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun shareToInstagramStoryTRY(context: Context, file: File) {


    Log.d("followdsadsadsa","${context.packageName}")

    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    context.grantUriPermission(
        "com.instagram.android",
        uri,
        Intent.FLAG_GRANT_READ_URI_PERMISSION,

        )


    Log.d("followdsadsadsa", "Real Path: ${file.absolutePath}")
    Log.d("followdsadsadsa", "Generated URI: $uri")

    val storyIntent = Intent("com.instagram.share.ADD_TO_STORY").apply {
        type = "video/mp4"
        putExtra("interactive_asset_uri", uri)

        putExtra("interactive_asset_uri", uri) // Attach video URI
        putExtra("top_background_color", "#000000") // Optional background color
        putExtra("bottom_background_color", "#FFFFFF") // Optional background color
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)




        Log.d("followdsadsadsa", "Generated URI: $uri")
        Log.d("followdsadsadsa", "File Path: ${file.absolutePath}")



    }

    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    storyIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    storyIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    storyIntent.setFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    storyIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

    val uriFromIntent = storyIntent.getParcelableExtra<Uri>("interactive_asset_uri")
    Log.d("followdsadsadsa", uriFromIntent.toString())



    val inputStream = context.contentResolver.openInputStream(uri)
    if (inputStream != null) {
        Log.d("followdsadsadsa", "Successfully accessed the file via URI")
        inputStream.close()
    } else {
        Log.e("followdsadsadsa", "Failed to access the file via URI")
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



