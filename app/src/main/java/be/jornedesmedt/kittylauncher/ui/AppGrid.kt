package be.jornedesmedt.kittylauncher.ui

import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    margin: Dp = 20.dp,
) {
    BoxWithConstraints {
        val constraints = ConstraintSet {
            val horizontalGuides = (0 .. columns).map {
                val fraction = it.toFloat() / columns
                Log.d("AppGrid", "HorizontalGuide $it: $fraction")
                createGuidelineFromTop(fraction = fraction)
            }

            val verticalGuides = (0 .. rows).map {
                createGuidelineFromAbsoluteLeft(fraction = it.toFloat() / rows)
            }

            val launchIcons = apps.map { Pair(it.key, createRefFor(it.key)) }

            launchIcons.forEach {
                constrain(it.second) {
                    val topGuide = horizontalGuides[it.first.first]
                    val bottomGuide = horizontalGuides[it.first.first + 1]
                    linkTo(topGuide, bottomGuide, topMargin = margin, bottomMargin = margin)

                    val leftGuide = verticalGuides[it.first.second]
                    val rightGuide = verticalGuides[it.first.second + 1]
                    linkTo(leftGuide, rightGuide, startMargin = margin, margin)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
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
fun AppGridPreview(){
    val context = LocalContext.current
    val icon = context.getDrawable(R.drawable.ic_launcher_background)!!


    fun testAppInfo(x: Int, y: Int): Pair<Pair<Int, Int>, AppInfo> {
        val appInfo = AppInfo(
            "$x, $y",
            packageName = "com.super.app",
            icon = icon,
        )
        return Pair(Pair(x,y), appInfo)
    }

    val apps = mapOf(
        testAppInfo(0, 0),
        testAppInfo(1, 1),
        testAppInfo(2, 2),
        testAppInfo(3, 3),
        testAppInfo(4, 2),
        testAppInfo(1, 0),
        testAppInfo(1, 2),
        testAppInfo(1, 3),
    )

    AppGrid(apps)
}