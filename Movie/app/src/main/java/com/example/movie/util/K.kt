package com.example.movie.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movie.movie.data.remote.models.Category
import com.example.movie.movie.data.remote.models.Country
import com.example.movie.movie.domain.model.CompileTypeItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.serialization.json.Json

object K {
    val WebClientID: String="472172044602-bktscvncqgvjrm6kojmvfuvm30h37pnh.apps.googleusercontent.com"
    val baseUrl:String = "https://phimapi.com/"


    val imgUrl:String ="https://phimimg.com/"

    val jsonCategory ="[{\"_id\":\"9822be111d2ccc29c7172c78b8af8ff5\",\"name\":\"H\\u00e0nh \\u0110\\u1ed9ng\",\"slug\":\"hanh-dong\"},{\"_id\":\"d111447ee87ec1a46a31182ce4623662\",\"name\":\"Mi\\u1ec1n T\\u00e2y\",\"slug\":\"mien-tay\"},{\"_id\":\"0c853f6238e0997ee318b646bb1978bc\",\"name\":\"Tr\\u1ebb Em\",\"slug\":\"tre-em\"},{\"_id\":\"f8ec3e9b77c509fdf64f0c387119b916\",\"name\":\"L\\u1ecbch S\\u1eed\",\"slug\":\"lich-su\"},{\"_id\":\"3a17c7283b71fa84e5a8d76fb790ed3e\",\"name\":\"C\\u1ed5 Trang\",\"slug\":\"co-trang\"},{\"_id\":\"1bae5183d681b7649f9bf349177f7123\",\"name\":\"Chi\\u1ebfn Tranh\",\"slug\":\"chien-tranh\"},{\"_id\":\"68564911f00849030f9c9c144ea1b931\",\"name\":\"Vi\\u1ec5n T\\u01b0\\u1edfng\",\"slug\":\"vien-tuong\"},{\"_id\":\"4db8d7d4b9873981e3eeb76d02997d58\",\"name\":\"Kinh D\\u1ecb\",\"slug\":\"kinh-di\"},{\"_id\":\"1645fa23fa33651cef84428b0dcc2130\",\"name\":\"T\\u00e0i Li\\u1ec7u\",\"slug\":\"tai-lieu\"},{\"_id\":\"2fb53017b3be83cd754a08adab3e916c\",\"name\":\"B\\u00ed \\u1ea8n\",\"slug\":\"bi-an\"},{\"_id\":\"4b4457a1af8554c282dc8ac41fd7b4a1\",\"name\":\"Phim 18+\",\"slug\":\"phim-18\"},{\"_id\":\"bb2b4b030608ca5984c8dd0770f5b40b\",\"name\":\"T\\u00ecnh C\\u1ea3m\",\"slug\":\"tinh-cam\"},{\"_id\":\"a7b065b92ad356387ef2e075dee66529\",\"name\":\"T\\u00e2m L\\u00fd\",\"slug\":\"tam-ly\"},{\"_id\":\"591bbb2abfe03f5aa13c08f16dfb69a2\",\"name\":\"Th\\u1ec3 Thao\",\"slug\":\"the-thao\"},{\"_id\":\"66c78b23908113d478d8d85390a244b4\",\"name\":\"Phi\\u00eau L\\u01b0u\",\"slug\":\"phieu-luu\"},{\"_id\":\"252e74b4c832ddb4233d7499f5ed122e\",\"name\":\"\\u00c2m Nh\\u1ea1c\",\"slug\":\"am-nhac\"},{\"_id\":\"a2492d6cbc4d58f115406ca14e5ec7b6\",\"name\":\"Gia \\u0110\\u00ecnh\",\"slug\":\"gia-dinh\"},{\"_id\":\"01c8abbb7796a1cf1989616ca5c175e6\",\"name\":\"H\\u1ecdc \\u0110\\u01b0\\u1eddng\",\"slug\":\"hoc-duong\"},{\"_id\":\"ba6fd52e5a3aca80eaaf1a3b50a182db\",\"name\":\"H\\u00e0i H\\u01b0\\u1edbc\",\"slug\":\"hai-huoc\"},{\"_id\":\"7a035ac0b37f5854f0f6979260899c90\",\"name\":\"H\\u00ecnh S\\u1ef1\",\"slug\":\"hinh-su\"},{\"_id\":\"578f80eb493b08d175c7a0c29687cbdf\",\"name\":\"V\\u00f5 Thu\\u1eadt\",\"slug\":\"vo-thuat\"},{\"_id\":\"0bcf4077916678de9b48c89221fcf8ae\",\"name\":\"Khoa H\\u1ecdc\",\"slug\":\"khoa-hoc\"},{\"_id\":\"2276b29204c46f75064735477890afd6\",\"name\":\"Th\\u1ea7n Tho\\u1ea1i\",\"slug\":\"than-thoai\"},{\"_id\":\"37a7b38b6184a5ebd3c43015aa20709d\",\"name\":\"Ch\\u00ednh K\\u1ecbch\",\"slug\":\"chinh-kich\"},{\"_id\":\"268385d0de78827ff7bb25c35036ee2a\",\"name\":\"Kinh \\u0110i\\u1ec3n\",\"slug\":\"kinh-dien\"}]"

    val jsonCountry = "[{\"_id\":\"f6ce1ae8b39af9d38d653b8a0890adb8\",\"name\":\"Việt Nam\",\"slug\":\"viet-nam\"},{\"_id\":\"3e075636c731fe0f889c69e0bf82c083\",\"name\":\"Trung Quốc\",\"slug\":\"trung-quoc\"},{\"_id\":\"cefbf1640a17bad1e13c2f6f2a811a2d\",\"name\":\"Thái Lan\",\"slug\":\"thai-lan\"},{\"_id\":\"dcd5551cbd22ea2372726daafcd679c1\",\"name\":\"Hồng Kông\",\"slug\":\"hong-kong\"},{\"_id\":\"92f688188aa938a03a61a786d6616dcb\",\"name\":\"Pháp\",\"slug\":\"phap\"},{\"_id\":\"24a5bf049aeef94ab79bad1f73f16b92\",\"name\":\"Đức\",\"slug\":\"duc\"},{\"_id\":\"41487913363f08e29ea07f6fdfb49a41\",\"name\":\"Hà Lan\",\"slug\":\"ha-lan\"},{\"_id\":\"8dbb07a18d46f63d8b3c8994d5ccc351\",\"name\":\"Mexico\",\"slug\":\"mexico\"},{\"_id\":\"61709e9e6ca6ca8245bc851c0b781673\",\"name\":\"Thụy Điển\",\"slug\":\"thuy-dien\"},{\"_id\":\"77dab2f81a6c8c9136efba7ab2c4c0f2\",\"name\":\"Philippines\",\"slug\":\"philippines\"},{\"_id\":\"208c51751eff7e1480052cdb4e26176a\",\"name\":\"Đan Mạch\",\"slug\":\"dan-mach\"},{\"_id\":\"69e561770d6094af667b9361f58f39bd\",\"name\":\"Thụy Sĩ\",\"slug\":\"thuy-si\"},{\"_id\":\"c338f80e38dd2381f8faf9eccb6e6c1c\",\"name\":\"Ukraina\",\"slug\":\"ukraina\"},{\"_id\":\"05de95be5fc404da9680bbb3dd8262e6\",\"name\":\"Hàn Quốc\",\"slug\":\"han-quoc\"},{\"_id\":\"74d9fa92f4dea9ecea8fc2233dc7921a\",\"name\":\"Âu Mỹ\",\"slug\":\"au-my\"},{\"_id\":\"aadd510492662beef1a980624b26c685\",\"name\":\"Ấn Độ\",\"slug\":\"an-do\"},{\"_id\":\"445d337b5cd5de476f99333df6b0c2a7\",\"name\":\"Canada\",\"slug\":\"canada\"},{\"_id\":\"8a40abac202ab3659bb98f71f05458d1\",\"name\":\"Tây Ban Nha\",\"slug\":\"tay-ban-nha\"},{\"_id\":\"4647d00cf81f8fb0ab80f753320d0fc9\",\"name\":\"Indonesia\",\"slug\":\"indonesia\"},{\"_id\":\"59317f665349487a74856ac3e37b35b5\",\"name\":\"Ba Lan\",\"slug\":\"ba-lan\"},{\"_id\":\"3f0e49c46cbde0c7adf5ea04a97ab261\",\"name\":\"Malaysia\",\"slug\":\"malaysia\"},{\"_id\":\"fcd5da8ea7e4bf894692933ee3677967\",\"name\":\"Bồ Đào Nha\",\"slug\":\"bo-dao-nha\"},{\"_id\":\"b6ae56d2d40c99fc293aefe45dcb3b3d\",\"name\":\"UAE\",\"slug\":\"uae\"},{\"_id\":\"471cdb11e01cf8fcdafd3ab5cd7b4241\",\"name\":\"Châu Phi\",\"slug\":\"chau-phi\"},{\"_id\":\"cc85d02a69f06f7b43ab67f5673604a3\",\"name\":\"Ả Rập Xê Út\",\"slug\":\"a-rap-xe-ut\"},{\"_id\":\"d4097fbffa8f7149a61281437171eb83\",\"name\":\"Nhật Bản\",\"slug\":\"nhat-ban\"},{\"_id\":\"559fea9881e3a6a3e374b860fa8fb782\",\"name\":\"Đài Loan\",\"slug\":\"dai-loan\"},{\"_id\":\"932bbaca386ee0436ad0159117eabae4\",\"name\":\"Anh\",\"slug\":\"anh\"},{\"_id\":\"45a260effdd4ba38e861092ae2a1b96a\",\"name\":\"Quốc Gia Khác\",\"slug\":\"quoc-gia-khac\"},{\"_id\":\"8931caa7f43ee5b07bf046c8300f4eba\",\"name\":\"Thổ Nhĩ Kỳ\",\"slug\":\"tho-nhi-ky\"},{\"_id\":\"2dbf49dd0884691f87e44769a3a3a29e\",\"name\":\"Nga\",\"slug\":\"nga\"},{\"_id\":\"435a85571578e419ed511257881a1e75\",\"name\":\"Úc\",\"slug\":\"uc\"},{\"_id\":\"42537f0fb56e31e20ab9c2305752087d\",\"name\":\"Brazil\",\"slug\":\"brazil\"},{\"_id\":\"a30878a7fdb6a94348fce16d362edb11\",\"name\":\"Ý\",\"slug\":\"y\"},{\"_id\":\"638f494a6d33cf5760f6e95c8beb612a\",\"name\":\"Na Uy\",\"slug\":\"na-uy\"},{\"_id\":\"5c35522c5074ad8e4c229a91f68eb464\",\"name\":\"Namh\",\"slug\":\"namh\"},{\"_id\":\"268385d0de78827ff7bb25c35036ee2a\",\"name\":\"Kinh Điển\",\"slug\":\"kinh-dien\"}]"
    val categories: List<Category> = Json.decodeFromString(jsonCategory)
    val countries: List<Country> = Json.decodeFromString(jsonCountry)

    var userCurrent = FirebaseAuth.getInstance().currentUser


    @Composable
    fun getRoute (navController:NavController) : String? {
        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = currentBackStackEntry?.destination?.route
        return currentRoute
    }

    val compileTypeItems = listOf(
        CompileTypeItem(title = "Phim Bộ", slug = "phim-bo"),
        CompileTypeItem(title = "Phim Lẻ", slug = "phim-le"),
        CompileTypeItem(title = "TV Shows", slug = "tv-shows"),
        CompileTypeItem(title = "Hoạt Hình", slug = "hoat-hinh"),
        CompileTypeItem(title = "Phim Vietsub", slug = "phim-vietsub"),
        CompileTypeItem(title = "Phim Thuyết Minh", slug = "phim-thuyet-minh"),
        CompileTypeItem(title = "Phim Lồng Tiếng", slug = "phim-long-tieng")
    )

    val history = listOf(
    "Hậu Duệ Mặt Trời", "Vincenzo", "Itaewon Class", "Hạ Cánh Nơi Anh",
    "Khách Sạn Ánh Trăng", "Điên Thì Có Sao", "Hoa Du Ký", "Penthouse: Cuộc Chiến Thượng Lưu",
    "Lời Hồi Đáp 1988", "Người Thừa Kế"
    )

    val REQUEST_CODE_RECORD_AUDIO = 14524

}