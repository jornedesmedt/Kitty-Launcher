package be.jornedesmedt.kittylauncher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import be.jornedesmedt.kittylauncher.R
import be.jornedesmedt.kittylauncher.model.AppInfo
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun AppLaunchIcon(
    appInfo: AppInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(painter = rememberDrawablePainter(drawable = appInfo.icon), contentDescription = null)
        Text(text = appInfo.label.toString())
    }
}

@Preview
@Composable
fun AppLaunchIconPreview(){
    val context = LocalContext.current
    val appInfo = AppInfo(
        "Super App",
        packageName = "com.super.app",
        icon = context.getDrawable(R.drawable.ic_launcher_background)!!,
    )

    AppLaunchIcon(appInfo)
}