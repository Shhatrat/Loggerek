@file:OptIn(ExperimentalResourceApi::class)

package com.shhatrat.loggerek.base.cache

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.GeocacheMock
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.LogType.COMMENT
import com.shhatrat.loggerek.api.model.LogType.FOUND
import com.shhatrat.loggerek.api.model.LogType.NOT_FOUND
import com.shhatrat.loggerek.api.model.isFound
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.MoveToLogCache
import com.shhatrat.loggerek.base.composable.MultiTextField
import com.shhatrat.loggerek.base.composable.MultiTextFieldModel
import loggerek.base.cache.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalResourceApi
@Composable
fun CacheItem(
    geocache: Geocache,
    move: MoveToLogCache?,
    iconContent: @Composable BoxScope.() -> Unit = {
        TypeCircle(geocache.type.iconRes, geocache.isFound())
    }
) {
    var selectedCache by remember { mutableStateOf<String?>(null) }

    if (selectedCache != null) {
        move?.invoke(geocache.code)
    }

    Box(
        modifier = Modifier
            .clickable { selectedCache = geocache.code }
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary))
    {
        Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colors.background).padding(4.dp).size(45.dp)
            ) {
                iconContent()
            }
            Text(
                modifier = Modifier.padding(4.dp),
                color = MaterialTheme.colors.background, text = geocache.code
            )
            Text(
                modifier = Modifier.padding(4.dp),
                color = MaterialTheme.colors.background, text = geocache.name
            )
        }
    }
}

@Composable
fun BoxScope.TypeCircle(icon: DrawableResource, isFound: Boolean) {
    Image(
        modifier = Modifier.Companion.align(Alignment.Center).padding(4.dp).size(50.dp),
        contentDescription = "cache type",
        painter = painterResource(icon)
    )
    if (isFound) {
        Box(
            modifier = Modifier.Companion
                .align(Alignment.BottomEnd)
                .size(25.dp)
                .clip(RoundedCornerShape(100))
                .background(MaterialTheme.colors.background),
        )
        Image(
            modifier = Modifier.Companion.align(Alignment.BottomEnd).size(25.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            painter = rememberAsyncImagePainter(Res.getUri("drawable/checkDone.svg")),
            contentDescription = "cache found",
        )
    }
}

@Composable
fun BoxScope.LogTypeCircle(icon: DrawableResource, logType: LogType) {
    Image(
        modifier = Modifier.Companion.align(Alignment.Center).padding(4.dp).size(50.dp),
        contentDescription = "cache type",
        painter = painterResource(icon)
    )
    Box(
        modifier = Modifier.Companion
            .align(Alignment.BottomEnd)
            .size(25.dp)
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colors.background),
    )
    Image(
        modifier = Modifier.Companion.align(Alignment.BottomEnd).size(25.dp),
        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
        painter = rememberAsyncImagePainter(
            when (logType) {
                COMMENT -> Res.getUri("drawable/checkComment.svg")
                FOUND -> Res.getUri("drawable/checkDone.svg")
                NOT_FOUND -> Res.getUri("drawable/checkCancel.svg")
                else -> Res.getUri("drawable/checkDone.svg")
            }
        ),
        contentDescription = "cache log type",
    )
}

data class HolderModel(
    val geocache: Geocache,
    val enabled: Boolean,
    val logType: LogType,
    val selected: Boolean,
    val onSelectedClicked: () -> Unit,
    val date: String,
    val comment: MultiTextFieldModel
)

@Composable
fun Holder(holderModel: HolderModel) {
    Column(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.background)
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.Companion
                    .size(50.dp)
                    .clickable { holderModel.onSelectedClicked.invoke() }
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colors.primary)
            ) {
                if (holderModel.selected)
                    Image(
                        modifier = Modifier.Companion.align(Alignment.Center).size(45.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.background),
                        painter = rememberAsyncImagePainter(Res.getUri("drawable/checkDone.svg")),
                        contentDescription = "cache found",
                    )
            }
            CacheItem(
                geocache = holderModel.geocache,
                move = {},
                iconContent = {
                    LogTypeCircle(holderModel.geocache.type.iconRes, holderModel.logType)
                })
        }
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        {
            Column(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.primary)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.background,
                        text = holderModel.date
                    )
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 50.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = holderModel.comment.text,
                        onValueChange = holderModel.comment.onChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        }

    }
}

@Composable
@Preview
fun ddd() {
    LoggerekTheme {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            Holder(
                HolderModel(
                    geocache = GeocacheMock().tranditional(),
                    enabled = true,
                    logType = FOUND,
                    selected = true,
                    onSelectedClicked = {},
                    date = "2121",
                    comment = MultiTextFieldModel("great")
                )
            )
            Holder(
                HolderModel(
                    geocache = GeocacheMock().tranditional(),
                    enabled = true,
                    logType = FOUND,
                    selected = true,
                    onSelectedClicked = {},
                    date = "2121",
                    comment = MultiTextFieldModel("great")
                )
            )
        }
    }
}