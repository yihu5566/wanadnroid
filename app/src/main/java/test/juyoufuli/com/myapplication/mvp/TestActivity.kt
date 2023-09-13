package test.juyoufuli.com.myapplication.mvp

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withTimeout
import org.jetbrains.annotations.TestOnly

/**
 * @Author : dongfang
 * @Created Time : 2023-08-28  09:27
 * @Description:
 */

class TestActivity : ComponentActivity() {
    @TestOnly
    suspend fun text() {

        val withTimeout = withTimeout(200) {
            repeat(3) {
                delay(300)
            }
            300
        }
        LogUtils.d(withTimeout)
        LogUtils.d("end")
    }

    fun shareFlowTest() {
        val stateIn = flowOf("p", 'P').stateIn(lifecycleScope, SharingStarted.Eagerly, "d")
        stateIn.onEach {

        }
    }
}