package be.jornedesmedt.kittylauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import be.jornedesmedt.kittylauncher.model.AppInfo
import be.jornedesmedt.kittylauncher.ui.theme.KittyLauncherTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter

class MainActivity : ComponentActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		val apps = getApps()
		setContent {
			KittyLauncherTheme {
				// A surface container using the 'background' color from the theme
				Log.d("Test", "${MaterialTheme.colorScheme.background}")
				Surface(modifier = Modifier.fillMaxSize(),
						color = MaterialTheme.colorScheme.background
				) {
					Column(Modifier.verticalScroll(rememberScrollState())) {
						for(app in apps){
							Row {
								Image(painter = rememberDrawablePainter(drawable = app.icon), contentDescription = null)
								Text(text = app.label.toString())
							}
						}
					}
				}
			}
		}
	}

	fun getApps(): List<AppInfo>
	{
		val pm = packageManager
		val intent = Intent(Intent.ACTION_MAIN, null)
		intent.addCategory(Intent.CATEGORY_LAUNCHER)
		val list = pm.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0))

		
		return list.map {
			AppInfo(
				label = it.loadLabel(pm),
				packageName = it.activityInfo.packageName,
				icon = it.loadIcon(pm),
			)
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier)
{
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
	KittyLauncherTheme {
		Greeting("Android")
	}
}