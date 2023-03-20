package com.hong.compose.test

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hong.compose.R
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hong.compose.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    context: Context,
    navHostController: NavHostController
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.mipmap.login_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(R.mipmap.ic_account),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 100.dp)
                        .size(90.dp)
                )
                //登录账号与密码框
                userNameAndPasswordEdit(mainViewModel)
                //登录按钮
                LoginBtn() {
                    if (mainViewModel.username.value.text.isEmpty() || mainViewModel.password.value.text.isEmpty()) {
                        Toast.makeText(context, "输入的账号或密码不能为空", Toast.LENGTH_SHORT).show()
                        return@LoginBtn
                    }
                    if (mainViewModel.username.value.text == "admin" && mainViewModel.password.value.text == "123") {
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                        navHostController.navigate("MainPage")
                    } else {
                        Toast.makeText(context, "登录失败:账号或密码错误", Toast.LENGTH_SHORT).show()
                    }
                }

                Register({
                    Toast.makeText(context, "你点击的是用户注册", Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(context, "你点击的是忘记密码", Toast.LENGTH_SHORT).show()
                })

                otherLoginMethod()
            }
        }
    }
}

@Composable
fun userNameAndPasswordEdit(mainViewModel: MainViewModel) {
    var pwdShowState by remember { mutableStateOf(false) }
    val userNameModifier =
        Modifier.padding(start = 50.dp, end = 50.dp, top = 50.dp).fillMaxWidth().height(56.dp)
    val passwordModifier =
        Modifier.padding(start = 50.dp, end = 50.dp, top = 10.dp).fillMaxWidth().height(56.dp)

    //用户名
    OutlinedTextField(
        value = mainViewModel.username.value,
        onValueChange = {
            mainViewModel.username.value = it
        },
        label = {
            Text(
                text = "请输入手机号码",
                fontSize = 13.sp
            )
        },
        leadingIcon = {
            Icon(
                painterResource(R.mipmap.ic_account_login_user_name),
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.White,
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedLabelColor = Color.White,
            leadingIconColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White
        ),
        singleLine = true,
        modifier = userNameModifier,
        textStyle = TextStyle(
            fontSize = 13.sp
        )
    )

    //密码
    OutlinedTextField(
        value = mainViewModel.password.value,
        onValueChange = {
            mainViewModel.password.value = it
        },
        modifier = passwordModifier,
        label = {
            Text(
                text = "输入密码",
                fontSize = 13.sp
            )
        },
        leadingIcon = {
            Icon(
                painterResource(R.mipmap.ic_account_login_password),
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.White,
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedLabelColor = Color.White,
            leadingIconColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White
        ),
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 13.sp
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    pwdShowState = !pwdShowState
                },
            ) {
                Icon(
                    painterResource(if (pwdShowState) R.mipmap.ic_password_view else R.mipmap.ic_password_view_off),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (pwdShowState) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun LoginBtn(loginClick: () -> Unit) {
    Button(
        onClick = loginClick,
        modifier = Modifier.padding(
            start = 50.dp,
            end = 50.dp,
            top = 21.dp
        ).fillMaxWidth().height(45.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.DarkGray,
        ),
    ) {
        Text(
            text = "登录",
            fontSize = 14.sp
        )
    }
}

@Composable
fun Register(registerClick: () -> Unit, forgetClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(top = 21.dp)
    ) {
        Text(
            text = "用户注册",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.clickable {
                registerClick()
            }
        )

        Text(
            text = "找回密码",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.clickable {
                forgetClick()
            }
        )
    }
}

@Composable
fun otherLoginMethod() {
    val modifier =
        Modifier.clip(CircleShape).background(MaterialTheme.colors.background).padding(10.dp)
            .size(30.dp)
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp, top = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(R.mipmap.ic_action_share_qq_grey),
            contentDescription = null,
            modifier = modifier,
        )

        Icon(
            painterResource(R.mipmap.ic_action_share_wechat_grey),
            contentDescription = null,
            modifier = modifier
        )

        Icon(
            painterResource(R.mipmap.ic_action_share_weibo_grey),
            contentDescription = null,
            modifier = modifier
        )
    }
}
