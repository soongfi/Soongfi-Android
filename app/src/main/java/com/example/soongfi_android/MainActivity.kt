package com.example.soongfi_android

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.soongfi_android.ui.theme.SoongfiAndroidTheme
import com.example.soongfi_android.ui.theme.grey
import com.example.soongfi_android.ui.theme.grey_background
import com.example.soongfi_android.ui.theme.primary
import com.soongfi.soongfi_android.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoongfiAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(context = this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(context: Context){
    val navController = rememberNavController()

    Column (
        // set minsize to 480dp
        // - https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes?hl=ko
        modifier = Modifier
            .sizeIn(maxWidth = 480.dp)
            .padding(all = 12.dp),
    ) {
        // header
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("숭파이", color = grey, fontWeight = FontWeight.Bold)
            // help button
            IconButton(onClick = {}
            ) {
                Icon(Icons.Rounded.Settings, contentDescription = "help", tint = grey)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Soongfi login portal button
            Button(
                onClick = {
                    openLoginPortal(context)
                },
                modifier = Modifier
                    .shadow(elevation = 12.dp, RoundedCornerShape(100.dp))
                    .width(200.dp)
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(100.dp)),
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                // soongfi logo
                Image(
                    painter = painterResource(id = R.drawable.soongfi),
                    contentDescription = stringResource(id = R.string.image_description_soongfi),
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
            }
        }
        Column() {
            HelpMessage("위 와이파이 버튼을 눌러\n숭실대학교 로그인 페이지 호출 시도해보세요.")
            // Soongfi login portal button
            Button(
                onClick = { openRouterPrivatePortal(context) },
                // DO NOT USE THIS CASE
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = grey_background,
                    contentColor = primary
                )
            ) {
                Text("로그인 페이지 호출 실패")
            }
        }
    }
}

@Composable
fun TestScreen(){

}

fun openRouterPrivatePortal(context: Context){
    val url = "http://192.168.0.1"
    openChromeCustomTab(context, url)
}

fun openLoginPortal(context: Context){

    // get login portal URL
    val url = getLoginPortalURL()

    // open login portal with ChromeCustomTab
    openChromeCustomTab(context, url)
}

fun openChromeCustomTab(context: Context, url: String){
    // if not a valid URL
    if (!URLUtil.isValidUrl(url)){
        return
    }

    // create ChromeCustomTab
    // - https://developer.chrome.com/docs/android/custom-tabs/
    val builder = CustomTabsIntent.Builder()
    val customBuilder = builder.build()
    customBuilder.launchUrl(context, Uri.parse(url))
}

// return valid Soongsil_WIFI Login portal URL (String)
fun getLoginPortalURL(
    ipAddress: String = "",
    macAddress: String = "",
    vlangtag: String = ""
): String {
    var url = "http://auth.soongsil.ac.kr/login/login.do?"

    // add parameter string about ipaddress
    url += "ipaddress=" + "111.111.111.111" + "&macaddress=" + "12:34:56:78:90:AB" + "&vlantag=" + "0110" + "&sysid=0001&btype=014&scode=&back_url=192.168.0.1/login/login.cgi"

    return url
}

fun getIpAddress(){

}

fun getMacAddress(){

}

fun getVlangTag(){

}

@Composable
fun HelpMessage(message: String){
    Text("$message",
        modifier = Modifier.fillMaxWidth(),
        color = grey,
        textAlign = TextAlign.Center,
        fontSize = 14.sp
        )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoongfiAndroidTheme {
    }
}