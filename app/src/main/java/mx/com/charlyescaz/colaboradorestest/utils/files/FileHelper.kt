package mx.com.charlyescaz.colaboradorestest.utils.files

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.net.URL

class FileHelper(
    private val urlFile: String,
    private val name: String,
    private val context: Context) {

    @SuppressLint("CheckResult")
    fun attemptDownload(cb: (File?) -> Unit) {
        Single.fromCallable { startDownload() }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cb(it) },
                { cb(null) }
            )
    }

    private fun startDownload(): File? {
        try {
            var count = 0;
            val url = URL(urlFile)
            val connection = url.openConnection()
            connection.connect()

            val input: InputStream = BufferedInputStream(url.openStream())
            File.createTempFile(name, "zip", context.cacheDir)
            val file = File(context.cacheDir, name)

            val output = FileOutputStream(file)
            val fd = output.fd

            val data = ByteArray(1024)
            var total: Long = 0

            while (input.read(data).also { count = it } !== -1) {
                total += count
                output.write(data, 0, count)
            }

            output.flush()
            output.close()
            input.close()

            return file
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.stackTraceToString(), e)
            return null
        }
    }
}