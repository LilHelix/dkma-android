package com.helix.dontkillmyapp.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
fun <A, B, C, R> CoroutineScope.mergeThree(
    channelOne: ReceiveChannel<A>,
    channelTwo: ReceiveChannel<B>,
    channelThree: ReceiveChannel<C>,
    combinator: (A?, B?, C?) -> R
) : ReceiveChannel<R> {
    return produce {

        var value1 : A? = null
        var value2 : B? = null
        var value3 : C? = null

        fun actualProduce() {
            combinator.invoke(value1, value2, value3)
        }

        launch {
            channelOne.consumeEach {
                value1 = it
                actualProduce()
            }
            channelTwo.consumeEach {
                value2 = it
                actualProduce()
            }
            channelThree.consumeEach {
                value3 = it
                actualProduce()
            }
        }
    }
}