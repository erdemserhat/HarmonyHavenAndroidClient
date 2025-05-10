package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState

import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationDefinedType
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationType
import com.erdemserhat.harmonyhaven.data.api.notification.PredefinedMessageSubject
import com.erdemserhat.harmonyhaven.data.api.notification.PredefinedReminderSubject
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSchedulerScreen(
    navController: NavController,
    viewModel:NotificationSchedulerViewModel
) {
    val state by viewModel.state.collectAsState()
    val deletionStates by viewModel.deletionStates.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = true) {
        viewModel.getSchedulers()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = harmonyHavenGreen,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Bildirim Ekle"
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (state.isLoadingSchedulers) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = harmonyHavenGreen
                    )
                }
            } else if (state.notificationScheduler.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Henüz bildirim zamanlaması oluşturmadınız",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.notificationScheduler) { scheduler ->
                        // Get deletion state for this item
                        val deletionState = deletionStates[scheduler.id] ?: DeletionState()
                        
                        // Check if this item is being deleted
                        val isDeleting = deletionState.isDeleting
                        
                        // Check if deletion just completed
                        val deletionJustCompleted = deletionState.isDeleting == false && deletionState.isSuccess != null
                        
                        // Check if this is a new item (UUID length > 30)
                        val isPending = scheduler.id?.let { 
                            it.length > 30 // UUID length check for temporary items
                        } ?: false
                        
                        // Determines whether to show success/failure message for this item
                        val showDeletionResult = deletionState.isSuccess != null && !deletionState.isDeleting
                        
                        SchedulerItem(
                            scheduler = scheduler,
                            onDelete = { 
                                scheduler.id?.let { id ->
                                    viewModel.deleteScheduler(id)
                                }
                            },
                            isDeleting = isDeleting,
                            isPending = isPending,
                            deletionSuccess = deletionState.isSuccess,
                            errorMessage = deletionState.errorMessage,
                            showDeletionResult = showDeletionResult,
                            onDismissDeletionResult = {
                                scheduler.id?.let { id ->
                                    viewModel.clearDeletionState(id)
                                }
                            }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddSchedulerDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { schedulerDto ->
                    viewModel.scheduleNotification(schedulerDto)
                    showAddDialog = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchedulerItem(
    scheduler: NotificationSchedulerDto,
    onDelete: () -> Unit,
    isDeleting: Boolean = false,
    isPending: Boolean = false,
    deletionSuccess: Boolean? = null,
    errorMessage: String? = null,
    showDeletionResult: Boolean = false,
    onDismissDeletionResult: () -> Unit = {}
) {
    val alpha by animateFloatAsState(if (isDeleting || isPending) 0.6f else 1f)
    
    // Effect to auto-dismiss deletion result after 3 seconds
    LaunchedEffect(showDeletionResult) {
        if (showDeletionResult) {
            delay(3000)
            onDismissDeletionResult()
        }
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title and delete button row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top // Changed to Top alignment
            ) {
                // Title and content column - limit width to make room for delete button
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp) // Ensure there's space between text and delete button
                ) {
                    // Title
                    Text(
                        text = when {
                            scheduler.definedType == NotificationDefinedType.DEFAULT && scheduler.type == NotificationType.MESSAGE ->
                                "Mesaj: ${getMessageSubjectText(scheduler.predefinedMessageSubject)}"
                            scheduler.definedType == NotificationDefinedType.DEFAULT && scheduler.type == NotificationType.REMINDER ->
                                "Hatırlatıcı: ${getReminderSubjectText(scheduler.predefinedReminderSubject)}"
                            else -> "Özel: ${scheduler.customSubject ?: ""}"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = harmonyHavenDarkGreenColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Details section with icons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (scheduler.type == NotificationType.MESSAGE) 
                                Icons.Default.Email else Icons.Default.Notifications,
                            contentDescription = null,
                            tint = harmonyHavenGreen.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (scheduler.type == NotificationType.MESSAGE) "Mesaj" else "Hatırlatma",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                    
                    // Time info
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = harmonyHavenGreen.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        // Format time to show only HH:mm
                        val timeString = scheduler.preferredTime.split(":").take(2).joinToString(":")
                        Text(
                            text = "Saat: $timeString",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }

                    // Days info
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = harmonyHavenGreen.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Günler: ${formatDaysOfWeek(scheduler.daysOfWeek)}",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.fillMaxWidth(0.9f) // Limit width to prevent overflow
                        )
                    }
                }
                
                // Delete/status column fixed width 
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 2.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    if (isDeleting) {
                        // Show loading spinner when deleting
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.error,
                            strokeWidth = 2.dp
                        )
                    } else if (isPending) {
                        // Show loading spinner when item is pending addition
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = harmonyHavenGreen,
                            strokeWidth = 2.dp
                        )
                    } else if (showDeletionResult) {
                        // Show result icon
                        if (deletionSuccess == true) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Silindi",
                                tint = Color.Green,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Hata",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        // Show delete button normally
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Sil",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            
            // Status messages at the bottom
            if (isDeleting) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Siliniyor...",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    fontStyle = FontStyle.Italic
                )
            } else if (isPending) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ekleniyor...",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            } else if (showDeletionResult) {
                Spacer(modifier = Modifier.height(8.dp))
                if (deletionSuccess == true) {
                    Text(
                        text = "Başarıyla silindi!",
                        fontSize = 14.sp,
                        color = harmonyHavenDarkGreenColor,
                        fontStyle = FontStyle.Italic
                    )
                } else {
                    Text(
                        text = errorMessage ?: "Silme işlemi başarısız oldu",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            
            // Visual tag for notification type
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (scheduler.type == NotificationType.MESSAGE) 
                            harmonyHavenGreen.copy(alpha = 0.6f)
                        else 
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
                    )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSchedulerDialog(
    onDismiss: () -> Unit,
    onConfirm: (NotificationSchedulerDto) -> Unit
) {
    val context = LocalContext.current
    var selectedDefinedType by remember { mutableStateOf<NotificationDefinedType?>(null) }
    var selectedType by remember { mutableStateOf<NotificationType?>(null) }
    var customSubject by remember { mutableStateOf("") }
    var predefinedMessageSubject by remember { mutableStateOf<PredefinedMessageSubject?>(null) }
    var predefinedReminderSubject by remember { mutableStateOf<PredefinedReminderSubject?>(null) }
    
    // Time selection
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:00") // Force seconds to 00
    
    // Day selection
    val selectedDays = remember { mutableStateListOf<DayOfWeek>() }
    val scrollState = rememberScrollState()
    
    // Map Turkish day names for better UI
    val dayNames = mapOf(
        DayOfWeek.MONDAY to "Pazartesi",
        DayOfWeek.TUESDAY to "Salı",
        DayOfWeek.WEDNESDAY to "Çarşamba",
        DayOfWeek.THURSDAY to "Perşembe",
        DayOfWeek.FRIDAY to "Cuma",
        DayOfWeek.SATURDAY to "Cumartesi",
        DayOfWeek.SUNDAY to "Pazar"
    )
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Allow us to use our own width
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .heightIn(max = 640.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(scrollState)
            ) {
                // Title with icon and close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = harmonyHavenGreen,
                            modifier = Modifier
                                .size(28.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "Yeni Bildirim Zamanlama",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = harmonyHavenDarkGreenColor
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F5F5))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Kapat",
                                tint = harmonyHavenGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
                
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = Color(0xFFEEEEEE),
                    thickness = 1.dp
                )
                
                // Notification Type Section with Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF9F9F9)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Section title
                        Text(
                            text = "Bildirim Türü",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        // Notification Type Selection
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectableGroup()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            FilterChip(
                                selected = selectedType == NotificationType.MESSAGE,
                                onClick = { selectedType = NotificationType.MESSAGE },
                                label = { 
                                    Text(
                                        "Mesaj", 
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) 
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = harmonyHavenGreen,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            
                            FilterChip(
                                selected = selectedType == NotificationType.REMINDER,
                                onClick = { selectedType = NotificationType.REMINDER },
                                label = { 
                                    Text(
                                        "Hatırlatma",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) 
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = harmonyHavenGreen,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Template type selection
                        Text(
                            text = "Tanımlama Tipi",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectableGroup(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            FilterChip(
                                selected = selectedDefinedType == NotificationDefinedType.DEFAULT,
                                onClick = { selectedDefinedType = NotificationDefinedType.DEFAULT },
                                label = { 
                                    Text(
                                        "Hazır Şablonlar",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) 
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = harmonyHavenGreen,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            
                            FilterChip(
                                selected = selectedDefinedType == NotificationDefinedType.CUSTOM,
                                onClick = { selectedDefinedType = NotificationDefinedType.CUSTOM },
                                label = { 
                                    Text(
                                        "Kendin Oluştur",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) 
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = harmonyHavenGreen,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                
                // Content Selection based on choices
                AnimatedVisibility(
                    visible = selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.MESSAGE,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F9F9)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Hazır Mesaj Seçin",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = harmonyHavenDarkGreenColor,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            Column(modifier = Modifier.selectableGroup()) {
                                PredefinedMessageSubject.values().forEach { subject ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (predefinedMessageSubject == subject) Color(0xFFE8F5E9) 
                                                else Color.Transparent
                                            )
                                            .selectable(
                                                selected = predefinedMessageSubject == subject,
                                                onClick = { predefinedMessageSubject = subject }
                                            )
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = predefinedMessageSubject == subject,
                                            onClick = null, // null because the parent is selectable
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = harmonyHavenGreen,
                                                unselectedColor = Color(0xFFBDBDBD)
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = getMessageSubjectText(subject),
                                            fontSize = 16.sp,
                                            color = if (predefinedMessageSubject == subject) 
                                                harmonyHavenDarkGreenColor else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                AnimatedVisibility(
                    visible = selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.REMINDER,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F9F9)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Hazır Hatırlatıcı Seçin",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = harmonyHavenDarkGreenColor,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            Column(modifier = Modifier.selectableGroup()) {
                                PredefinedReminderSubject.values().forEach { subject ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (predefinedReminderSubject == subject) Color(0xFFE8F5E9) 
                                                else Color.Transparent
                                            )
                                            .selectable(
                                                selected = predefinedReminderSubject == subject,
                                                onClick = { predefinedReminderSubject = subject }
                                            )
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = predefinedReminderSubject == subject,
                                            onClick = null, // null because the parent is selectable
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = harmonyHavenGreen,
                                                unselectedColor = Color(0xFFBDBDBD)
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = getReminderSubjectText(subject),
                                            fontSize = 16.sp,
                                            color = if (predefinedReminderSubject == subject) 
                                                harmonyHavenDarkGreenColor else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                AnimatedVisibility(
                    visible = selectedDefinedType == NotificationDefinedType.CUSTOM,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F9F9)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Özel Bildirim Metni",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = harmonyHavenDarkGreenColor,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            OutlinedTextField(
                                value = customSubject,
                                onValueChange = { customSubject = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                placeholder = { 
                                    Text(
                                        if (selectedType == NotificationType.REMINDER)
                                            "Örnek: bana toplantımı hatırlat"
                                        else
                                            "Örnek: beni motive eden bir mesaj yaz"
                                    ) 
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = harmonyHavenGreen,
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedLabelColor = harmonyHavenGreen,
                                    cursorColor = harmonyHavenGreen,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }
                
                // Time and Day Selection Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF9F9F9)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Time Selection
                        Text(
                            text = "Bildirim Saati",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        // Show only hours and minutes in the button (format HH:mm)
                        val displayTime = "${selectedTime.hour.toString().padStart(2, '0')}:${selectedTime.minute.toString().padStart(2, '0')}"
                        
                        Button(
                            onClick = {
                                TimePickerDialog(
                                    context,
                                    { _, hour: Int, minute: Int ->
                                        // Set seconds to 0 when selecting time
                                        selectedTime = LocalTime.of(hour, minute, 0)
                                    },
                                    selectedTime.hour,
                                    selectedTime.minute,
                                    true
                                ).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = harmonyHavenDarkGreenColor
                            ),
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add, // Replace with a clock icon if available
                                    contentDescription = null,
                                    tint = harmonyHavenGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = displayTime,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Day Selection - Horizontal and Scrollable
                        Text(
                            text = "Bildirim Günleri",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        val daysScrollState = rememberScrollState()
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(daysScrollState)
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DayOfWeek.values().sortedBy { it.value }.forEach { day ->
                                val isSelected = selectedDays.contains(day)
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) harmonyHavenGreen else Color.White
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = if (isSelected) harmonyHavenGreen else Color(0xFFE0E0E0),
                                                shape = CircleShape
                                            )
                                            .clickable {
                                                if (isSelected) {
                                                    selectedDays.remove(day)
                                                } else {
                                                    selectedDays.add(day)
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day.getDisplayName(java.time.format.TextStyle.SHORT, Locale("tr", "TR")).first().toString(),
                                            color = if (isSelected) Color.White else Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.height(4.dp))
                                    
                                    Text(
                                        text = dayNames[day]?.substring(0, 2) ?: "",
                                        fontSize = 12.sp,
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Submit Button
                Button(
                    onClick = {
                        if (isFormValid(
                                selectedDefinedType,
                                selectedType,
                                customSubject,
                                predefinedMessageSubject,
                                predefinedReminderSubject,
                                selectedDays
                            )
                        ) {
                            val scheduler = NotificationSchedulerDto(
                                definedType = selectedDefinedType!!,
                                type = selectedType!!,
                                customSubject = if (selectedDefinedType == NotificationDefinedType.CUSTOM) customSubject else null,
                                predefinedMessageSubject = if (selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.MESSAGE) predefinedMessageSubject else null,
                                predefinedReminderSubject = if (selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.REMINDER) predefinedReminderSubject else null,
                                preferredTime = selectedTime.format(timeFormatter), // Use the formatter with seconds set to 00
                                daysOfWeek = selectedDays.toList()
                            )
                            onConfirm(scheduler)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        disabledContentColor = Color(0xFF9E9E9E)
                    ),
                    enabled = isFormValid(
                        selectedDefinedType,
                        selectedType,
                        customSubject,
                        predefinedMessageSubject,
                        predefinedReminderSubject,
                        selectedDays
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Bildirim Planla",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val horizontalGapPx = 0
        val verticalGapPx = 0
        
        val rows = mutableListOf<MutableList<Placeable>>()
        val rowWidths = mutableListOf<Int>()
        val rowHeights = mutableListOf<Int>()
        
        var currentRow = mutableListOf<Placeable>()
        var currentRowWidth = 0
        var currentRowHeight = 0
        
        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)
            
            if (currentRowWidth + placeable.width > constraints.maxWidth) {
                rows.add(currentRow)
                rowWidths.add(currentRowWidth)
                rowHeights.add(currentRowHeight)
                
                currentRow = mutableListOf<Placeable>()
                currentRowWidth = 0
                currentRowHeight = 0
            }
            
            currentRow.add(placeable)
            currentRowWidth += placeable.width + horizontalGapPx
            currentRowHeight = maxOf(currentRowHeight, placeable.height)
        }
        
        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
            rowWidths.add(currentRowWidth)
            rowHeights.add(currentRowHeight)
        }
        
        val width = rowWidths.maxOrNull() ?: 0
        val height = rowHeights.sumOf { it } + verticalGapPx * (rows.size - 1)
        
        layout(width, height) {
            var y = 0
            
            rows.forEachIndexed { rowIndex, placeables ->
                var x = 0
                
                placeables.forEach { placeable ->
                    placeable.placeRelative(x, y)
                    x += placeable.width + horizontalGapPx
                }
                
                y += rowHeights[rowIndex] + verticalGapPx
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isFormValid(
    selectedDefinedType: NotificationDefinedType?,
    selectedType: NotificationType?,
    customSubject: String,
    predefinedMessageSubject: PredefinedMessageSubject?,
    predefinedReminderSubject: PredefinedReminderSubject?,
    selectedDays: List<DayOfWeek>
): Boolean {
    if (selectedDefinedType == null || selectedType == null || selectedDays.isEmpty()) {
        return false
    }
    
    return when {
        selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.MESSAGE -> 
            predefinedMessageSubject != null
        selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.REMINDER -> 
            predefinedReminderSubject != null
        selectedDefinedType == NotificationDefinedType.CUSTOM -> 
            customSubject.isNotBlank()
        else -> false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDaysOfWeek(days: List<DayOfWeek>): String {
    return days.joinToString(", ") { 
        it.getDisplayName(TextStyle.SHORT, Locale("tr", "TR"))
    }
}

private fun getMessageSubjectText(subject: PredefinedMessageSubject?): String {
    return when (subject) {
        PredefinedMessageSubject.GOOD_MORNING -> "Günaydın"
        PredefinedMessageSubject.GOOD_EVENING -> "İyi Akşamlar"
        PredefinedMessageSubject.MOTIVATION -> "Motivasyon"
        null -> ""
    }
}

private fun getReminderSubjectText(subject: PredefinedReminderSubject?): String {
    return when (subject) {
        PredefinedReminderSubject.WATER_DRINK -> "Su İçme"
        PredefinedReminderSubject.SLEEP_TIME -> "Uyku Saati"
        PredefinedReminderSubject.EXERCISE -> "Egzersiz"
        null -> ""
    }
} 