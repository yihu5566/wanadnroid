package test.juyoufuli.com.myapplication.mvp

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
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

    /** 复杂数据流**/
    //复杂数据流 https://mp.weixin.qq.com/s/vJbU9GFcvbG1zCfNquEfAA
    // 并行组
    private val parallelList = listOf(
        Request("parallel1", 1600, 10),
        Request("parallel2", 2900, 20),
        Request("parallel3", 2000, 11),
        Request("parallel4", 3000, 30),
        Request("parallel5", 5000, 50),
        Request("parallel6", 10000, 60),
    )

    // 串行流（包含底价）
    private val sequenceList = listOf(
        Request("sequence1", 2100, 30, 30),
        Request("sequence2", 1100, 19, 20),
        Request("sequence3", 2000, 15, 16),
        Request("sequence4", 2200, 7, 10),
        Request("sequence5", 3000, 6, 5),
        Request("sequence6", 2000, 5, 5),
        Request("sequence7", 400, 5, 5),
    )
    val sequenceFlow = sequenceList.asFlow().map {
        val isDone = it.run { price >= bottomPrice }
        RequestSwitch(load(it).price, isDone)
    }.transformWhile<RequestSwitch, RequestSwitch> {
        emit(RequestSwitch(it.price, false))
        !it.isDone
    }

    val parallelFlow = flow {
        parallelList.asFlow()
            .flatMapMerge { request -> flow { emit(load(request).price) } }
            .reduce { accumulator, value ->
                if (value > accumulator) value else accumulator
            }.also { emit(RequestSwitch(it, true)) }
    }

    // 获取价格
    private suspend fun load(request: Request): Request {
        delay(request.delay)
        return request
    }

    fun filterFlow() {
        // 消费合流
        lifecycleScope.launch {
            val maxPrice = flowOf(sequenceFlow, parallelFlow)//串并合流
                .flattenMerge()
                .transformWhile {
                    LogUtils.d("比价中-->${it.price}--${it.isDone}")
                    emit(it.price) // 总是将上游价格转发至下游
                    !it.isDone // 这一行为true表示终止合流
                }
                .reduce { max, value -> if (value > max) value else max }
            LogUtils.d("最大值$maxPrice")
        }
    }

}

data class Request(
    val name: String, // 请求名称
    val delay: Long, // 响应时延
    val price: Int, // 价格
    val bottomPrice: Int = 0 // 底价
)

data class RequestSwitch(val price: Int, val isDone: Boolean)