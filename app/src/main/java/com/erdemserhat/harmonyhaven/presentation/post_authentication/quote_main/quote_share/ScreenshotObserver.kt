package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore

class ScreenshotObserver(
    private val onScreenshotDetected: () -> Unit,
    handler: Handler
) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        onScreenshotDetected()
    }
}

/**
 *       DisposableEffect(Unit) {
 *                     // Handler ve Observer tanımlanıyor
 *                     val handler = Handler(context.mainLooper)
 *
 *                     // Observer için bir tetikleme durumu
 *                     val screenshotObserver = ScreenshotObserver(
 *                         onScreenshotDetected = {
 *                             coroutineScope.launch {
 *                                 // SS olayı algılandığında sadece bir kez tetiklenir
 *                                 val bundle = Bundle()
 *                                 bundle.putParcelable(
 *                                     "params",
 *                                     QuoteShareScreenParams(
 *                                         quoteUrl = quote.imageUrl,
 *                                         quote = quote.quote,
 *                                         author = quote.writer
 *                                     )
 *                                 )
 *
 *                                 viewmodel.updateSelectedQuote(
 *                                     QuoteForOrderModel(
 *                                         id = quote.id,
 *                                         quote = quote.quote,
 *                                         writer = quote.writer,
 *                                         imageUrl = quote.imageUrl,
 *                                         currentPage = currentScreen
 *                                     )
 *                                 )
 *                                 // Navigasyonu gerçekleştirme
 *                                 navController?.navigate(
 *                                     route = Screen.QuoteShareScreen.route,
 *                                     args = bundle
 *                                 )
 *                             }
 *                         },
 *                         handler = handler
 *                     )
 *
 *                     // Observer kayıt ediliyor
 *                     context.contentResolver.registerContentObserver(
 *                         MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
 *                         true,
 *                         screenshotObserver
 *                     )
 *
 *                     onDispose {
 *                         // Observer kaldırılıyor
 *                         context.contentResolver.unregisterContentObserver(screenshotObserver)
 *                     }
 *                 }
 *
 */
