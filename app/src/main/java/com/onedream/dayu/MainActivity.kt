package com.onedream.dayu

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
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
import com.onedream.dayu_uploader_service.DaYuUploaderService

class MainActivity : ComponentActivity() {
    val message = mutableStateOf("Android")
    val uploadBtnText = mutableStateOf("uploadCrashLogFile")

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
                        PlayBugButton(btnTitle= "getLastExceptionBugLog", onClick = {
                            getLastExceptionBugLog()
                        })
                        //
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle= uploadBtnText.value, onClick = {
                            uploadCrashLogFile()
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
        Toast.makeText(this, "默认配置白名单中：遇到空指针异常App不崩溃", Toast.LENGTH_SHORT).show()
    }

    private fun throwException(errMsg:String){
        Handler(Looper.getMainLooper()).post {
            throw Exception(errMsg)
        }
    }

    private fun throwCustomerException(errMsg:String){
        Handler(Looper.getMainLooper()).post {
            throw CustomerException(errMsg)
            Toast.makeText(this, "内部代码：任务执行前发生异常，配置异常白名单后还是不能继续执行", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, "外部代码：任务执行前发生异常，配置异常白名单后还能继续执行", Toast.LENGTH_SHORT).show()
    }

    private fun protectCustomerException(){
        DaYu.writeWhiteList(arrayListOf(DaYuExceptionWhiteListModel("CustomerException")), true)
    }

    private fun getLastExceptionBugLog(){
        val crashLogFile = DaYu.getCrashLogFile(this@MainActivity)
        var crashLog = ""
        crashLogFile?.forEachLine { line ->
            crashLog = "$crashLog \n $line"
        }
        message.value = crashLog
    }

    private fun uploadCrashLogFile(){
        uploadBtnText.value = "上传中"
        val crashLogFile = DaYu.getCrashLogFile(this@MainActivity)
        DaYuUploaderService.uploadLogFile(crashLogFile, success = {
            uploadBtnText.value = "上传成功"
            getLastExceptionBugLog()
        }, error = {
            uploadBtnText.value = it
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name",
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