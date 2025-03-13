package com.onedream.dayu

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onedream.dayu.protector.whitelist.model.DaYuExceptionWhiteListModel
import com.onedream.dayu.ui.theme.DaYuTheme

class MainActivity : ComponentActivity() {
    val message = mutableStateOf("Android")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaYuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column( modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle= "throwNullPointerException", onClick = {
                            throwNullPointerException()
                        })
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle= "throwException", onClick = {
                            throwException("抛出异常")
                        })
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle= "throwCustomerException", onClick = {
                            throwCustomerException("抛出自定义的异常")
                        })
                        //
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle= "protectCustomerException", onClick = {
                            protectCustomerException()
                        })
                        //
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle= "getLastExceptionBug", onClick = {
                            getLastExceptionBug()
                        })

                        Greeting(message.value)
                    }
                }
            }
        }
    }

    private fun throwNullPointerException(){
        Handler(Looper.getMainLooper()).post {
            throw NullPointerException()
        }
    }

    private fun throwException(errMsg:String){
        Handler(Looper.getMainLooper()).post {
            throw Exception(errMsg)
        }
    }

    private fun throwCustomerException(errMsg:String){
        Handler(Looper.getMainLooper()).post {
            throw CustomerException(errMsg)
        }
    }

    private fun protectCustomerException(){
        DaYu.writeWhiteList(arrayListOf(DaYuExceptionWhiteListModel("CustomerException")), true)
    }

    private fun getLastExceptionBug(){
        val crashLogFile = DaYu.getCrashLogFile(this@MainActivity)
        var crashLog = ""
        crashLogFile?.forEachLine { line ->
            crashLog = "$crashLog \n $line"
        }
        message.value = crashLog
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun PlayBugButton(btnTitle: String,onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(1.0f)
    ) {
        Text(
            text = btnTitle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DaYuTheme {
        Greeting("Android")
    }
}