package id.andreasdimas.traveloka.utils

import id.andreasdimas.traveloka.BuildConfig


/**
 * Created by Andreas Dimas Setyoko on 2021-03-15.
 */

object Const {
    init {
        System.loadLibrary("native-lib")
    }

    private val environmentStage = BuildConfig.STAGE_TYPE
    private external fun appUrl(environmentStage: Int): String

    val appUrl = appUrl(environmentStage)


}
