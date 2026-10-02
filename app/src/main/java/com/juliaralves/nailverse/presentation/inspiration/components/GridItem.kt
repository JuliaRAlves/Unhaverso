package com.juliaralves.nailverse.presentation.inspiration.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.toBitmap
import com.juliaralves.nailverse.R
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.presentation.core.theme.TranslucentBlack
import com.juliaralves.nailverse.presentation.core.theme.White2
import com.juliaralves.nailverse.presentation.inspiration.InspirationPictureAction


@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    item: InspirationPictureVO,
    onAction: (InspirationPictureAction) -> Unit
) {
    var showOptions by rememberSaveable { mutableStateOf(false) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 16.dp))
            .clickable(enabled = true, onClick = {
                showOptions = !showOptions
            })
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.imageUrl,
            placeholder = painterResource(id = R.drawable.bg_broken),
            onSuccess = {
                bitmapImage = it.result.image.toBitmap()
            }
        )
        if (showOptions) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = TranslucentBlack)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onAction(InspirationPictureAction.Download(bitmapImage)) }) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = null,
                            tint = White2
                        )
                    }
                    IconButton(onClick = { onAction(InspirationPictureAction.Share(item)) }) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = White2
                        )
                    }
                }
            }
        }
    }
}