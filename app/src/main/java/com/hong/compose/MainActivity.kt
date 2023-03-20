package com.hong.compose

import android.os.Build
import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hong.compose.bean.IndexArticle
import com.hong.compose.ui.theme.Test_composeTheme
import com.hong.compose.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setDecorFitsSystemWindows(false)

        setContent {
            Test_composeTheme {
                rememberSystemUiController().setStatusBarColor(
                    Color.Transparent,
                    darkIcons = false
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHostTest(LocalContext.current)
                }
            }
        }
    }

}


@Composable
fun MainScreen( statusBarHeight: Dp) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val items = listOf("主页", "公账号", "我的")
    var selectedItem by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().height(75.dp)
                    .background(MaterialTheme.colors.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "标题",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = statusBarHeight, bottom = 10.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        },
                        label = {
                            Text(item)
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (selectedItem) {
                0 -> {
                    searchEdit()
                    initData(mainViewModel)
                }
                1 -> Text("测试界面1")
                2 -> Text("测试界面2")
            }
        }
    }
}

@Composable
fun initData(viewModel: MainViewModel) {
    val article = viewModel.article.observeAsState()
    article.value?.let {
        it.onSuccess { articles ->
            articleItem(articles)
        }
        it.onFailure {
            Toast.makeText(LocalContext.current, "数据请求失败", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun articleItem(articles: IndexArticle) {
    LazyColumn {
        items(articles.datas) { article ->
            var isLove by remember { mutableStateOf(false) }
            Column {
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                        .background(Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                    ) {
                        Text(
                            article.title,
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = article.niceDate,
                                style = MaterialTheme.typography.body2,
                                color = Color.Gray
                            )

                            Image(
                                painterResource(
                                    if (isLove)
                                        R.mipmap.icon_article_loved
                                    else
                                        R.mipmap.icon_article_love
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(27.dp).clickable {
                                    isLove = !isLove
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun searchEdit() {
    var value by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                value = it
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            modifier = Modifier.fillMaxWidth()
                .height(45.dp)
                .border(
                    1.dp, Color.Gray,
                    shape = RoundedCornerShape(10.dp)
                ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painterResource(R.mipmap.icon_search),
                        contentDescription = null,
                        modifier = Modifier.size(27.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier.fillMaxHeight()
                            .fillMaxWidth(0.9f)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "请输入内容",
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                    if (value.isNotEmpty()) {
                        Image(
                            painterResource(R.mipmap.icon_close),
                            contentDescription = null,
                            modifier = Modifier.size(27.dp)
                                .clickable {
                                    value = ""
                                }
                        )
                    }
                }
            }
        )
    }
}
