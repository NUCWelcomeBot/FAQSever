package GsonModel
import GsonModel.ExtractKeyword
import com.google.gson.Gson
import okhttp3.ConnectionPool
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
@Service
class DemoOkhttp {

    companion object {
        var client= OkHttpClient()
        fun post(url:String,header:Map<String,String>?=null):ExtractKeyword{
            val builder = client.newBuilder()
            builder.readTimeout(Companion.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            //连接超时
            builder.connectTimeout(Companion.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS);
            //写入超时
            builder.writeTimeout(Companion.WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS);
            builder.connectionPool(ConnectionPool(32, 5, TimeUnit.MINUTES))
            client = builder.build()
            val formBodyBuilder = FormBody.Builder()
            header?.forEach{
                formBodyBuilder.add(
                    it.key,it.value
                )
            }
            val formBody = formBodyBuilder.build()
            val requestBody = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
            val responseBody = client.newCall(requestBody)
                .execute().body()?.string()
            return  Gson().fromJson(responseBody,ExtractKeyword::class.java);
        }
        private const val READ_TIMEOUT = 100;
        private const val CONNECT_TIMEOUT = 60;
        private const val WRITE_TIMEOUT = 60;
    }
}