package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationDefinedType
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationType
import com.erdemserhat.harmonyhaven.data.api.notification.PredefinedMessageSubject
import com.erdemserhat.harmonyhaven.data.api.notification.PredefinedReminderSubject
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
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
    viewModel: NotificationSchedulerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.getSchedulers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bildirim Zamanlaması", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = harmonyHavenGreen
                )
            )
        },
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
                .padding(16.dp)
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
                        SchedulerItem(
                            scheduler = scheduler,
                            onDelete = { viewModel.deleteScheduler(scheduler.id ?: "") }
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
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    color = harmonyHavenDarkGreenColor
                )

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Sil",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Bildirim Tipi: ${if (scheduler.type == NotificationType.MESSAGE) "Mesaj" else "Hatırlatma"}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Saat: ${scheduler.preferredTime}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Günler: ${formatDaysOfWeek(scheduler.daysOfWeek)}",
                fontSize = 16.sp
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
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    
    // Day selection
    val selectedDays = remember { mutableStateListOf<DayOfWeek>() }
    val scrollState = rememberScrollState()
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 560.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Yeni Bildirim Zamanlama",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = harmonyHavenDarkGreenColor
                    )
                    
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Kapat",
                            tint = harmonyHavenGreen
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Defined Type Selection
                Text(
                    text = "Tanımlama Tipi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = harmonyHavenDarkGreenColor
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedDefinedType == NotificationDefinedType.DEFAULT,
                        onClick = { selectedDefinedType = NotificationDefinedType.DEFAULT },
                        label = { Text("Hazır Şablonlar") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = harmonyHavenGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                    
                    FilterChip(
                        selected = selectedDefinedType == NotificationDefinedType.CUSTOM,
                        onClick = { selectedDefinedType = NotificationDefinedType.CUSTOM },
                        label = { Text("Özel") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = harmonyHavenGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Notification Type Selection
                Text(
                    text = "Bildirim Türü",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = harmonyHavenDarkGreenColor
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedType == NotificationType.MESSAGE,
                        onClick = { selectedType = NotificationType.MESSAGE },
                        label = { Text("Mesaj") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = harmonyHavenGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                    
                    FilterChip(
                        selected = selectedType == NotificationType.REMINDER,
                        onClick = { selectedType = NotificationType.REMINDER },
                        label = { Text("Hatırlatma") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = harmonyHavenGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Content Selection based on choices
                AnimatedVisibility(visible = selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.MESSAGE) {
                    Column {
                        Text(
                            text = "Hazır Mesaj Seçin",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor
                        )
                        
                        Column(modifier = Modifier.selectableGroup()) {
                            PredefinedMessageSubject.values().forEach { subject ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = predefinedMessageSubject == subject,
                                            onClick = { predefinedMessageSubject = subject }
                                        )
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = predefinedMessageSubject == subject,
                                        onClick = null, // null because the parent is selectable
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = harmonyHavenGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(getMessageSubjectText(subject))
                                }
                            }
                        }
                    }
                }
                
                AnimatedVisibility(visible = selectedDefinedType == NotificationDefinedType.DEFAULT && selectedType == NotificationType.REMINDER) {
                    Column {
                        Text(
                            text = "Hazır Hatırlatıcı Seçin",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor
                        )
                        
                        Column(modifier = Modifier.selectableGroup()) {
                            PredefinedReminderSubject.values().forEach { subject ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = predefinedReminderSubject == subject,
                                            onClick = { predefinedReminderSubject = subject }
                                        )
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = predefinedReminderSubject == subject,
                                        onClick = null, // null because the parent is selectable
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = harmonyHavenGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(getReminderSubjectText(subject))
                                }
                            }
                        }
                    }
                }
                
                AnimatedVisibility(visible = selectedDefinedType == NotificationDefinedType.CUSTOM) {
                    Column {
                        Text(
                            text = "Özel Bildirim Metni",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = harmonyHavenDarkGreenColor
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        OutlinedTextField(
                            value = customSubject,
                            onValueChange = { customSubject = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Örnek: beni motive eden bir mesaj yaz") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = harmonyHavenGreen,
                                focusedLabelColor = harmonyHavenGreen,
                                cursorColor = harmonyHavenGreen
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Time Selection
                Text(
                    text = "Bildirim Saati",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = harmonyHavenDarkGreenColor
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedButton(
                    onClick = {
                        TimePickerDialog(
                            context,
                            { _, hour: Int, minute: Int ->
                                selectedTime = LocalTime.of(hour, minute, 0)
                            },
                            selectedTime.hour,
                            selectedTime.minute,
                            true
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = harmonyHavenGreen
                    )
                ) {
                    Text(selectedTime.format(timeFormatter))
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Day Selection
                Text(
                    text = "Bildirim Günleri",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = harmonyHavenDarkGreenColor
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DayOfWeek.values().forEach { day ->
                        val isSelected = selectedDays.contains(day)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    selectedDays.remove(day)
                                } else {
                                    selectedDays.add(day)
                                }
                            },
                            label = {
                                Text(
                                    text = day.getDisplayName(
                                        TextStyle.SHORT,
                                        Locale("tr", "TR")
                                    )
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = harmonyHavenGreen,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
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
                                preferredTime = selectedTime.format(timeFormatter),
                                daysOfWeek = selectedDays.toList()
                            )
                            onConfirm(scheduler)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen,
                        contentColor = Color.White
                    ),
                    enabled = isFormValid(
                        selectedDefinedType,
                        selectedType,
                        customSubject,
                        predefinedMessageSubject,
                        predefinedReminderSubject,
                        selectedDays
                    )
                ) {
                    Text("Bildirim Planla")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
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
        
        val rows = mutableListOf<MutableList<androidx.compose.ui.layout.Placeable>>()
        val rowWidths = mutableListOf<Int>()
        val rowHeights = mutableListOf<Int>()
        
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0
        var currentRowHeight = 0
        
        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)
            
            if (currentRowWidth + placeable.width > constraints.maxWidth) {
                rows.add(currentRow)
                rowWidths.add(currentRowWidth)
                rowHeights.add(currentRowHeight)
                
                currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
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