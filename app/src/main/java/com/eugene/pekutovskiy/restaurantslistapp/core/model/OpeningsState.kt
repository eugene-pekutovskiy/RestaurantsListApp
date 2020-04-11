package com.eugene.pekutovskiy.restaurantslistapp.core.model

import com.google.gson.annotations.SerializedName

enum class OpeningsState {
    @SerializedName("open")
    OPEN,
    @SerializedName("order ahead")
    ORDER_AHEAD,
    @SerializedName("closed")
    CLOSED
}