package mx.com.charlyescaz.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collaborator")
data class CollaboratorDB(
    @PrimaryKey(autoGenerate = true)
    val idLocalDB: Long? = null,

    val id: Long? = null,

    val name: String? = null,

    val mail: String? = null,

    @ColumnInfo(name = "in_cloud")
    var inCloud: Boolean = false,

    @Embedded
    val location: LocationDB? = null
)