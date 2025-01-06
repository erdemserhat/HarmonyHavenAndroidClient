package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CustomModalBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

    // Bottom sheet
    var offsetY by remember { mutableFloatStateOf(0f) }
    Box(modifier = Modifier.fillMaxSize()) {

        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .offset { IntOffset(0, offsetY.roundToInt()) }
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            Log.d("dasdasdsa", offsetY.toString())
                            offsetY = (offsetY + delta).coerceAtLeast(0f)
                        },
                        onDragStopped = {
                            if (offsetY > 120f) { // Belirli bir mesafede sheet'i kapat
                                onDismiss()
                            } else {
                                offsetY = 0f // Geri eski yerine oturt
                            }
                        }
                    )
                    .padding(16.dp)
            ) {
                content()
            }

        }


    }


}

@Composable
fun CustomBottomSheetDemo() {
    var isSheetVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { isSheetVisible = !isSheetVisible },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Show Bottom Sheet")
        }

        CustomModalBottomSheet(
            isVisible = isSheetVisible,
            onDismiss = { isSheetVisible = false }
        ) {
            Column {
                Text("This is a custom bottom sheet!", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { isSheetVisible = false }) {
                    Text("Close")
                }
                Column {
                    Text("This is a custom bottom sheet!", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { isSheetVisible = false }) {
                        Text("Close")
                    }
                }
                Column {
                    Text("This is a custom bottom sheet!", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { isSheetVisible = false }) {
                        Text("Close")
                    }
                }
            }


        }
    }
}

@Preview
@Composable
private fun dasdsa() {
    CustomBottomSheetDemo()

}




