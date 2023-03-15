import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(color = androidx.compose.ui.graphics.Color.White),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 16.dp)
        ) {

        }
        
        Column(
            modifier = Modifier
                .sizeIn(maxWidth = 480.dp)
                .padding(all = 12.dp),
        ) {
            Text(
                text = "도움말",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "아래 가이드에 따라 가능한 여러가지 방법을 시도해 보세요.",
                fontSize = 14.sp,
                modifier = Modifier.paddingFromBaseline(top = 24.dp),
                textAlign = TextAlign.Left
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "인터넷 속도가 느려요, 자꾸 끊겨요",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.paddingFromBaseline(top = 40.dp),
                textAlign = TextAlign.Left
            )
            Text(
                text = "1. Soongsil_WIFI 연결을 끊어주세요.",
                fontSize = 14.sp,
                modifier = Modifier.paddingFromBaseline(top = 24.dp)
            )
            Text(
                text = "2. 인터넷이 되는 다른 와이파이(또는 모바일 데이터)에 연결하세요.",
                fontSize = 14.sp,
                modifier = Modifier.paddingFromBaseline(top = 16.dp)
            )
            Text(
                text = "3. 숭파이 로그인 버튼을 눌러 로그인해주세요.",
                fontSize = 14.sp,
                modifier = Modifier.paddingFromBaseline(top = 16.dp)
            )
            Text(
                text = "4. 다시 Soongsil_WIFI에 연결하여 인터넷 속도를 확인해주세요.",
                fontSize = 14.sp,
                modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 56.dp)
            )
        }

    }

}

@Preview
@Composable
fun InfoScreenPreview() {
    InfoScreen()
}
