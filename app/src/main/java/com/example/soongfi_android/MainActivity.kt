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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.soongfi_android.ui.theme.SoongfiAndroidTheme
import com.example.soongfi_android.ui.theme.grey

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
                    Column (
                        modifier = Modifier.fillMaxWidth()
                            ){
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, true)) {

                            // Soongfi login portal button
                            Button(onClick = {
                                openLoginPortal(this@MainActivity)
                            },
                                shape = RoundedCornerShape(30),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("숭파이 로그인")
                            }
                        }
                        Column() {
                            HelpMessage("위 와이파이 버튼을 눌러\n숭실대학교 로그인 페이지 호출 시도해보세요.")
                            // Soongfi login portal button
                            Button(onClick = { openRouterPrivatePortal(this@MainActivity) },
                                shape = RoundedCornerShape(30),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("로그인 페이지 호출 실패")
                            }
                        }
                    }

                }
            }
        }
    }
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