package be.jornedesmedt.kittylauncher.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import be.jornedesmedt.kittylauncher.R
import be.jornedesmedt.kittylauncher.model.AppInfo

@Composable
fun AppGrid(
    apps: Map<Pair<Int, Int>, AppInfo>,
    rows: Int = 4,
    columns: Int = 5,
) {
    BoxWithConstraints {
        val constraints = ConstraintSet {
            val horizontalGuides = (0 until columns).map {
                createGuidelineFromTop(fraction = it.toFloat() / columns)
            }

            val verticalGuides = (0 until rows).map {
                createGuidelineFromAbsoluteLeft(fraction = it.toFloat() / rows)
            }

            val launchIcons = apps.map { Pair(it.key, createRefFor(it.key)) }

            launchIcons.forEach {
                constrain(it.second) {
                    top.linkTo(horizontalGuides[it.first.first])
                    absoluteLeft.linkTo(verticalGuides[it.first.second])

                    width = Dimension.percent(1f / columns)
                    height = Dimension.percent(1f / rows)
                }
            }
        }
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier.fillMaxSize()
        ) {
            apps.forEach {
                AppLaunchIcon(
                    appInfo = it.value,
                    modifier = Modifier
                        .layoutId(it.key)
                )
            }
        }
    }
}

@Preview
@Composable
fun AppGridPreview(){val context = LocalContext.current
    val appInfo = AppInfo(
        "Super App",
        packageName = "com.super.app",
        icon = context.getDrawable(R.drawable.ic_launcher_background)!!,
    )

    val apps = mapOf(
        Pair(Pair(0,0), appInfo),
        Pair(Pair(1,1), appInfo),
        Pair(Pair(2,2), appInfo),
        Pair(Pair(3,3), appInfo),
        Pair(Pair(4,2), appInfo),
    )

    AppGrid(apps)
}