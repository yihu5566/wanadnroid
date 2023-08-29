package test.juyoufuli.com.myapplication.mvp

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import org.jetbrains.annotations.TestOnly

/**
 * @Author : dongfang
 * @Created Time : 2023-08-28  09:27
 * @Description:
 */

class TestActivity {
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
}