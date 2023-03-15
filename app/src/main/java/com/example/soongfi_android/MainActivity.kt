package com.example.soongfi_android

import InfoScreen
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soongfi_android.ui.theme.SoongfiAndroidTheme
import com.example.soongfi_android.ui.theme.grey
import com.example.soongfi_android.ui.theme.grey_background
import com.example.soongfi_android.ui.theme.primary
import com.soongfi.soongfi_android.R
import java.net.Inet4Address
import kotlin.random.Random
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*




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
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("숭파이", color = grey, fontWeight = FontWeight.Bold)

            // Info button
            // NavController 객체를 생성
            // https://developer.android.com/jetpack/compose/navigation
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "HomeScreen"
            ) {
                composable("HomeScreen") {
                }
                composable("InfoScreen") {
                    InfoScreen()
                }
            }
            //navController.navigate() 메서드를 사용하여 다른 화면으로 이동
            IconButton(onClick = {navController.navigate("InfoScreen")}
            ) {
                Icon(Icons.Rounded.Info, contentDescription = "info", tint = grey)
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


fun openRouterPrivatePortal(context: Context){
    val url = "http://192.168.0.1"
    openChromeCustomTab(context, url)
}

fun openLoginPortal(context: Context){

    // get login portal URL
    val url = getLoginPortalURL(getIpAddress(),getMacAddress(), getVlanTag())

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
    ipAddress: String,
    macAddress: String,
    vlangtag: String
): String {
    var url = "http://auth.soongsil.ac.kr/login/login.do?"

    // add parameter string about ipaddress
    url += "ipaddress=" + "$ipAddress" + "&macaddress=" + "$macAddress" + "&vlantag=" + "$vlangtag" + "&sysid=0001&btype=014&scode=&back_url=192.168.0.1/login/login.cgi"
    Log.i("url", url)
    return url

}

fun getIpAddress(): String {
    //NetworkInterface.getNetworkInterfaces() : 네트워크 인터페이스 목록 두개를 가져옴 (루프백,실제네트워크)
    val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())

        for (intf in interfaces) {
            // IP주소 목록 반환
            val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
            for (addr in addrs) {
                // IPv4 주소 가져오기(루프백 주소 필터링)
                if (!addr.isLoopbackAddress) {
                    val hostAddress: String = if (addr is Inet4Address) {
                        addr.hostAddress
                    } else {
                        // Ignore IPv6 addresses
                        continue
                    }
                        return hostAddress
                }
            }
        }

    return "111.111.111.111"
}



// cannot get MacAddress with android API version >= 31
// https://developer.android.com/training/articles/user-data-ids?hl=ko#mac-addresses
// Create random MacAddress
fun getMacAddress(): String{

    val macAdd = ByteArray(6)
    Random.nextBytes(macAdd)

    val sb = StringBuilder(18)

    for (i: Int in 0..5) {
        if (sb.length > 0) sb.append(":")
        sb.append(String.format("%02x", macAdd[i]))
    }

    return sb.toString()
}

fun getVlanTag() : String{
    return "0110"
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