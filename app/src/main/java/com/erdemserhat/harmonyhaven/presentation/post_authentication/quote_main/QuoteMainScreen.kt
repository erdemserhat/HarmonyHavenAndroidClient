package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.Manifest
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Density
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.presentation.navigation.CommentBottomModalSheetActions
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.PostFlow
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.CategoryPickerModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentViewModel
import kotlinx.coroutines.launch

/**
 * Main screen for displaying quotes and handling user interactions.
 * This screen manages the quote feed, comments, and category selection.
 *
 * @param modifier Modifier for customizing the layout
 * @param navController Navigation controller for handling navigation
 * @param viewModel ViewModel for managing quote-related data and operations
 * @param volumeControllerViewModel Optional ViewModel for controlling audio volume
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun QuoteMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: QuoteMainViewModel,
    volumeControllerViewModel: VolumeControlViewModel? = null
) {
    QuoteMainContent(
        volumeControllerViewModel = volumeControllerViewModel,
        modifier = modifier,
        viewmodel = viewModel,
        navController = navController,
    )
}

/**
 * Content composable for the QuoteMainScreen.
 * Handles the main UI layout and state management for quotes, comments, and categories.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuoteMainContent(
    modifier: Modifier = Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null,
    volumeControllerViewModel: VolumeControlViewModel? = null,
    commentViewModel: CommentViewModel = hiltViewModel()
) {
    // State management
    val shouldShowUxDialog1 = viewmodel.shouldShowUxDialog1.collectAsState()
    var permissionGranted by remember { mutableStateOf(viewmodel.isPermissionGranted()) }
    var shouldShowCategoryBottomModal by remember { mutableStateOf(false) }
    var shouldShowCommentBottomModal by remember { mutableStateOf(false) }
    var postID by rememberSaveable { mutableIntStateOf(-1) }

    // UI controllers
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    // Sheet states
    val categorySheetState = SheetState(skipPartiallyExpanded = false, density = Density(context))
    val commentSheetState = SheetState(skipPartiallyExpanded = true, density = Density(context))

    // Notification permission handling
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            viewmodel.updatePermissionStatus(isGranted)
        }
    )

    // Request notification permission on first launch
    LaunchedEffect(Unit) {
        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    // Handle comment modal visibility
    LaunchedEffect(shouldShowCommentBottomModal) {
        if (shouldShowCommentBottomModal && postID != -1) {
            if (commentViewModel.lastPostId.value != postID) {
                commentViewModel.loadComments(postID)
            } else {
                commentViewModel.loadFromCache()
            }
        } else if (!shouldShowCommentBottomModal) {
            keyboardController?.hide()
            if (postID != -1) {
                commentViewModel.setLastPostId(postID)
            }
            commentViewModel.resetList()
            commentViewModel.commitApiCallsWithoutDelay()
        }
    }

    // Handle back button press
    BackHandler {
        when {
            commentSheetState.isVisible -> {
                coroutineScope.launch {
                    commentSheetState.hide()
                    shouldShowCommentBottomModal = false
                }
            }
            categorySheetState.isVisible -> {
                coroutineScope.launch { categorySheetState.hide() }
            }
        }
    }

    // Main content
    Box(modifier = modifier.fillMaxSize().background(Color.Black)) {
        // Category picker modal
        if (shouldShowCategoryBottomModal) {
            CategoryPickerModalBottomSheet(
                onDismissRequest = { shouldShowCategoryBottomModal = false },
                sheetState = categorySheetState,
                actions = CommentBottomModalSheetActions(
                    onShouldFilterQuotes = { selectedCategories ->
                        viewmodel.loadCategorizedQuotes(selectedCategories)
                    },
                    onSaveCategorySelection = { model ->
                        viewmodel.saveCategorySelection(model)
                    },
                    onGetCategorySelectionModel = viewmodel.getCategorySelection()
                )
            )
        }

        // Comment modal
        AnimatedVisibility(
            visible = shouldShowCommentBottomModal,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            CommentModalBottomSheet(
                onDismissRequest = { shouldShowCommentBottomModal = false },
                sheetState = commentSheetState,
                modifier = Modifier.align(Alignment.BottomCenter),
                postId = postID,
                viewModel = commentViewModel
            )
        }

        // Main quote feed
        PostFlow(
            volumeControllerViewModel = volumeControllerViewModel,
            modifier = Modifier,
            viewmodel = viewmodel,
            navController = navController,
            onCommentsClicked = { postId ->
                postID = postId
                shouldShowCommentBottomModal = true
            },
            onCategoryClicked = {
                coroutineScope.launch {
                    shouldShowCategoryBottomModal = true
                }
            }
        )

        // UX tutorial dialog
        if (shouldShowUxDialog1.value) {
            UxScrollInformer(
                modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.markScrollTutorialDone(false)
                }
            )
        }
    }
}







