package mx.com.charlyescaz.colaboradorestest.utils.files

import android.content.Context
import android.util.Log
import java.io.*
import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object FileUtils {

    fun unzip(zipFile: File, context: Context): List<File>? {
        val results = ArrayList<File>()
        try {
            val fin = FileInputStream(zipFile)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry? = null
            while (zin.nextEntry.also { ze = it } != null) {

                File.createTempFile(ze!!.name, null, context.cacheDir)
                val file = File(context.cacheDir, ze!!.name)

                val fout = FileOutputStream(file)
                val baos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var count: Int
                while (zin.read(buffer).also { count = it } != -1) {
                    baos.write(buffer, 0, count)
                    val bytes: ByteArray = baos.toByteArray()
                    fout.write(bytes)
                    baos.reset()
                }
                fout.close()
                zin.closeEntry()
                results.add(file)
            }
            zin.close()
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.stackTraceToString(), e)
            return null
        }
        return results
    }

    fun loadJSONFromAsset(file: File): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = FileInputStream(file)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}