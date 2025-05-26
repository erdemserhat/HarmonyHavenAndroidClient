package com.erdemserhat.harmonyhaven.presentation.post_authentication.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationBottomSection
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationRowElement
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.AccountInformationViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.NameUpdatePopup
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.account_information.PasswordUpdatePopup
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: AccountInformationViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEmailInfoDialog by remember { mutableStateOf(false) }


    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }
    
    // Name and Password Update States
    var shouldShowUpdateNamePopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowUpdatePasswordPopUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowSuccessAnimation by rememberSaveable { mutableStateOf(false) }
    
    val passwordChangeResponse by viewModel.passwordChangeResponseModel
    val nameChangeState by viewModel.nameChangeState
    val context = LocalContext.current
    
    // Function to get app version
    fun getAppVersion(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            "${packageInfo.versionName} (${packageInfo.longVersionCode})"
        } catch (e: PackageManager.NameNotFoundException) {
            "Bilinmiyor"
        }
    }
    
    // Function to send help and support email
    fun sendHelpSupportEmail(context: Context) {
        val appVersion = getAppVersion(context)
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("info@harmonyhavenapp.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Harmony Haven - Yardım ve Destek Talebi")
            putExtra(Intent.EXTRA_TEXT, """
                Merhaba Harmony Haven Ekibi,
                
                Uygulama ile ilgili yardıma ihtiyacım var.
                
                Sorunum/Talebim:
                [Lütfen buraya sorunuzu veya talebinizi detaylı bir şekilde yazın]
                
                Cihaz Bilgileri:
                - Uygulama Versiyonu: $appVersion
                - İşletim Sistemi: Android ${android.os.Build.VERSION.RELEASE}
                - Cihaz Modeli: ${android.os.Build.MODEL}
                - Kullanıcı E-posta: ${viewModel.userInfo.value.email}
                
                Teşekkürler,
                
            """.trimIndent())
        }
        
        try {
            context.startActivity(Intent.createChooser(emailIntent, "E-posta gönder"))
        } catch (e: Exception) {
            android.widget.Toast.makeText(
                context,
                "E-posta uygulaması bulunamadı. Lütfen info@harmonyhavenapp.com adresine manuel olarak e-posta gönderin.",
                android.widget.Toast.LENGTH_LONG
            ).show()
        }
    }
    
    // Function to send feedback email
    fun sendFeedbackEmail(context: Context) {
        val appVersion = getAppVersion(context)
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("info@harmonyhavenapp.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Harmony Haven - Geri Bildirim")
            putExtra(Intent.EXTRA_TEXT, """
                Merhaba Harmony Haven Ekibi,
                
                Uygulama hakkında geri bildirimde bulunmak istiyorum.
                
                Geri Bildirimim:
                [Lütfen buraya önerilerinizi, beğendiklerinizi veya iyileştirme fikirlerinizi yazın]
                
                Değerlendirme (1-5 yıldız): ⭐⭐⭐⭐⭐
                
                Cihaz Bilgileri:
                - Uygulama Versiyonu: $appVersion
                - İşletim Sistemi: Android ${android.os.Build.VERSION.RELEASE}
                - Cihaz Modeli: ${android.os.Build.MODEL}
                - Kullanıcı E-posta: ${viewModel.userInfo.value.email}
                
                Teşekkürler,
                
            """.trimIndent())
        }
        
        try {
            context.startActivity(Intent.createChooser(emailIntent, "E-posta gönder"))
        } catch (e: Exception) {
            android.widget.Toast.makeText(
                context,
                "E-posta uygulaması bulunamadı. Lütfen info@harmonyhavenapp.com adresine manuel olarak e-posta gönderin.",
                android.widget.Toast.LENGTH_LONG
            ).show()
        }
    }
    
    // Handle success states
    if (passwordChangeResponse.isSuccessfullyChangedPassword) {
        shouldShowUpdatePasswordPopUp = false
        LaunchedEffect(passwordChangeResponse.isSuccessfullyChangedPassword) {
            shouldShowSuccessAnimation = true
            delay(1000)
            shouldShowSuccessAnimation = false
            viewModel.resetPasswordResetStates()
        }
    }

    if (nameChangeState.result) {
        shouldShowUpdateNamePopUp = false
        LaunchedEffect(nameChangeState.result) {
            shouldShowSuccessAnimation = true
            delay(1000)
            shouldShowSuccessAnimation = false
            viewModel.resetNameState()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Hesap Detayları",
                        fontFamily = ptSansFont,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2E3C59)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color(0xFF2E3C59)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Personal Information Section
            SectionTitle("Kişisel Bilgiler")
            
            // Name
            SettingsItem(
                icon = Icons.Default.Person,
                title = "Ad: ${viewModel.userInfo.value.name}",
                onClick = { shouldShowUpdateNamePopUp = true }
            )
            
            // Email
            SettingsItem(
                icon = Icons.Default.Email,
                title = "E-Posta: ${viewModel.userInfo.value.email}",
                onClick = { showEmailInfoDialog = true }
            )
            
            // Password
            SettingsItem(
                icon = Icons.Default.Lock,
                title = "Şifre Değiştir",
                onClick = { shouldShowUpdatePasswordPopUp = true }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Notifications Section
            SectionTitle("Bildirimler")
            
            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Bildirim Ayarları",
                onClick = { 
                    navController.navigate(Screen.NotificationScheduler.route)
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Support Section
            SectionTitle("Destek")
            
            SettingsItem(
                icon = Icons.Default.Help,
                title = "Yardım ve Destek",
                onClick = { sendHelpSupportEmail(context) }
            )
            
            SettingsItem(
                icon = Icons.Default.Share,
                title = "Geri Bildirim Gönder",
                onClick = { sendFeedbackEmail(context) }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Logout Section
            SettingsItem(
                icon = Icons.Default.ExitToApp,
                title = "Çıkış Yap",
                titleColor = Color.Red,
                iconTint = Color.Red,
                onClick = { showLogoutDialog = true }
            )
            
            Spacer(modifier = Modifier.height(80.dp)) // Extra space for bottom nav
        }
    }
    
    // Email Info Dialog
    if (showEmailInfoDialog) {
        AlertDialog(
            onDismissRequest = { showEmailInfoDialog = false },
            title = { 
                Text(
                    text = "E-Posta Değişikliği",
                    fontFamily = ptSansFont,
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Text(
                    text = "Güvenlik nedenlerinden dolayı e-posta adresinizi doğrudan güncellemiyoruz. Talebinizi bize e-posta göndererek iletebilirsiniz.",
                    fontFamily = ptSansFont
                ) 
            },
            confirmButton = {
                Button(
                    onClick = { showEmailInfoDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    )
                ) {
                    Text(
                        text = "Anladım",
                        color = Color.White,
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
    
    // Logout Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { 
                Text(
                    text = "Çıkış Yap",
                    fontFamily = ptSansFont,
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Text(
                    text = "Çıkış yapmak istediğine emin misin?",
                    fontFamily = ptSansFont
                ) 
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) // Clear the back stack
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    )
                ) {
                    Text(
                        text = "Çıkış Yap",
                        color = Color.White,
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "Vazgeç",
                        color = Color.White,
                        fontFamily = ptSansFont
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
    
    // Name Update Popup
    if (shouldShowUpdateNamePopUp) {
        Box(
            modifier = Modifier
                .imePadding()
                .navigationBarsPadding()
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        shouldShowUpdateNamePopUp = false
                    })
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            NameUpdatePopup(
                isError = !nameChangeState.isNameAppropriate,
                onDismissRequest = {
                    shouldShowUpdateNamePopUp = false
                },
                onPositiveButtonClicked = { newName ->
                    viewModel.changeName(newName)
                },
                currentName = viewModel.userInfo.value.name,
                backgroundColor = Color.White,
                buttonColor = harmonyHavenGreen,
                buttonTextColor = Color.White
            )
        }
    }
    
    // Password Update Popup
    if (shouldShowUpdatePasswordPopUp) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        shouldShowUpdatePasswordPopUp = false
                        viewModel.resetPasswordResetStates()
                    })
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            PasswordUpdatePopup(
                onDismissRequest = {
                    shouldShowUpdatePasswordPopUp = false
                    viewModel.resetPasswordResetStates()
                },
                onSaveButtonClicked = { newPassword, confirmNewPassword, currentPassword ->
                    viewModel.changePassword(newPassword, currentPassword, confirmNewPassword)
                    GlobalScope.launch(Dispatchers.IO) {
                        delay(3000)
                        withContext(Dispatchers.Main) {
                            viewModel.resetPasswordResetStates()
                        }
                    }
                },
                isNewPasswordAppropriate = viewModel.passwordChangeResponseModel.value.isNewPasswordAppropriate,
                isCurrentPasswordCorrect = viewModel.passwordChangeResponseModel.value.isCurrentPasswordCorrect,
                isNewPasswordsMatch = viewModel.passwordChangeResponseModel.value.isNewPasswordsMatch,
                isLoading = passwordChangeResponse.isLoading,
                isCurrentPasswordShort = passwordChangeResponse.isCurrentPasswordShort,
                backgroundColor = Color.White,
                buttonColor = harmonyHavenGreen,
                buttonTextColor = Color.White
            )
        }
    }
    
    // Success Animation
    if (shouldShowSuccessAnimation) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LocalGifImage(resId = R.raw.successfullygif)
            LaunchedEffect(Unit) {
                delay(1000)
                shouldShowSuccessAnimation = false
            }
        }
    }
    
    // Loading Animation
    if (passwordChangeResponse.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LocalGifImage(resId = R.raw.loading)
        }
    }
}

@Composable
fun RowDividingLine(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .border(1.dp, Color(0xFFEDEDED))
            .height(1.dp)
    )
}

@Composable
fun LocalGifImage(resId: Int, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(resId)
            .decoderFactory(GifDecoder.Factory())
            .scale(Scale.FIT)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
        fontFamily = ptSansFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    titleColor: Color = Color.Black,
    iconTint: Color = Color.DarkGray,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular icon background
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = title,
            fontFamily = ptSansFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = titleColor,
            modifier = Modifier.weight(1f)
        )
        
        androidx.compose.material3.Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
    
    // Divider
    Divider(
        modifier = Modifier.padding(start = 72.dp, end = 16.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}

@Composable
private fun IconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()

    
}

@Composable
fun SettingsScreenContent(navController: NavController,modifier: Modifier) {

    var shouldShowLogoutAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

///////////////////////////////Exit Alert Dialog/////////////////////////////
        if (shouldShowLogoutAlertDialog) {
            AlertDialogBase(
                alertTitle = "Çıkış Yap",
                alertBody = "Çıkış yapmak istediğine emin misin?",
                positiveButtonText = "Çıkış Yap",
                negativeButtonText = "Vazgeç",
                onPositiveButtonClicked = {

                    navController.navigate(Screen.Login.route)
                    
                    navController.navigate("Login")


                    shouldShowLogoutAlertDialog = false

                },
                onNegativeButtonClicked = {
                    shouldShowLogoutAlertDialog = false
                }) {

            }
        }
///////////////////////////////Exit Alert Dialog/////////////////////////////



        // Border ile ayrılmış 3 adet buton
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            SettingsButton(
                icon = painterResource(id = R.drawable.account_icon),
                title = "Hesap Bilgileri",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            12.dp, 12.dp, 0.dp, 0.dp
                        )
                    ),
                onButtonClicked = {
                    navController.navigate(Screen.AccountInformationScreen.route)
                }
            )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

          //  SettingsButton(
            //    icon = painterResource(id = R.drawable.saved_articles_icon),
             //   title = "Saved articles",
             //   modifier = Modifier.clip(
              //      RoundedCornerShape(
               //         0.dp, 0.dp, 12.dp, 12.dp
                //    )
               // ),
               // onButtonClicked = {
                //    navController.navigate(Screen.SavedArticles.route)
               // }
          //  )
        }

        Spacer(modifier = Modifier.size(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            //SettingsButton(
              //  icon = painterResource(id = R.drawable.information_icon),
               // title = "Hakkımızda",
               // modifier = Modifier.clip(
                 //   RoundedCornerShape(
                   //     12.dp, 12.dp, 0.dp, 0.dp
                  //  )
              //  ),
              //  onButtonClicked = {
              //     navController.navigate(Screen.AboutUs.route)
              //  }
          //  )

           // Divider(
            //    color = Color(0xFFD9D9D9),
           //     thickness = 1.dp,
            //    modifier = Modifier.padding(horizontal = 16.dp)
         //   )

         //   SettingsButton(
              //  icon = painterResource(id = R.drawable.report_icon),
              //  title = "Report problem",
              //  modifier = Modifier
           // )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

          //  SettingsButton(
             //   icon = painterResource(id = R.drawable.terms_icon),
              //  title = "Terms and conditions",
               // modifier = Modifier.clip(
                //    RoundedCornerShape(
                  //      0.dp, 0.dp, 12.dp, 12.dp
                  //  )
              //  )
           // )
        }

        Spacer(modifier = Modifier.size(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            SettingsButton(
                icon = painterResource(id = R.drawable.logout_icon),
                iconColor = Color.Red,
                title = "Çıkış Yap",
                titleColor = Color.Red,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            12.dp, 12.dp, 12.dp, 12.dp
                        )
                    ),
                onButtonClicked = {shouldShowLogoutAlertDialog=true}

            )
        }
    }
}

@Composable
fun SettingsButton(
    icon: Painter,
    title: String,
    titleColor: Color = Color.Black,
    iconColor: Color = Color(0xFF222222),
    modifier: Modifier = Modifier,
    onButtonClicked:()->Unit ={}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = { onButtonClicked() })
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = titleColor,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFD9D9D9)
        )
    }
}


@Composable
fun AlertDialogBase(
    alertTitle: String,
    alertBody: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    onNegativeButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = alertTitle,
                fontFamily = DefaultAppFont,
                color = Color.Black

            )
        },
        text = {
            Text(
                text = alertBody,
                fontFamily = DefaultAppFont,
                color = Color.Black

            )
        },
        confirmButton = {
            TextButton(onClick = {
                onPositiveButtonClicked()
            }
            ) {
                Text(
                    text = positiveButtonText,
                    color = Color.Red,
                    fontFamily = DefaultAppFont

                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNegativeButtonClicked()
            }) {
                Text(
                    text = negativeButtonText,
                    fontFamily = DefaultAppFont

                )
            }
        }
    )
}


@Composable
fun AccountInformation(
    alertTitle: String,
    alertBody: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    onNegativeButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = alertTitle,
                fontFamily = DefaultAppFont

            )
        },
        text = {
            Text(
                text = alertBody,
                fontFamily = DefaultAppFont

            )
        },
        confirmButton = {
            TextButton(onClick = {
                onPositiveButtonClicked()
            }
            ) {
                Text(
                    text = positiveButtonText,
                    color = Color.Red,
                    fontFamily = DefaultAppFont

                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNegativeButtonClicked()
            }) {
                Text(
                    text = negativeButtonText,
                    fontFamily = DefaultAppFont

                )
            }
        }
    )
}


