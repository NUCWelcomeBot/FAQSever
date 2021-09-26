package GsonModel

import com.google.gson.annotations.SerializedName

data class ExtractKeyword(@SerializedName("msg")
                         val msg: String = "",
                         @SerializedName("code")
                         val code: Int = 0,
                         @SerializedName("data")
                         val data: List<String>?)