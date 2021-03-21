package mx.com.charlyescaz.colaboradorestest.utils

import com.google.gson.Gson
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.database.models.CollaboratorDB

object ModelExtensions {
    val gson = Gson()

    fun CollaboratorDB.toCollaborator() = toModel(this, Collaborator::class.java)

    fun <T> Any.toModel(ob: Any, cl: Class<T>): T {
        val data = gson.toJsonTree(ob).asJsonObject
        return gson.fromJson(data, cl)
    }

    public fun <T> List<Any>.toListModel(model: Class<T>): List<T> {
        val list = mutableListOf<T>()
        this.forEach { list.add(it.toModel(it, model)) }
        return list
    }
}