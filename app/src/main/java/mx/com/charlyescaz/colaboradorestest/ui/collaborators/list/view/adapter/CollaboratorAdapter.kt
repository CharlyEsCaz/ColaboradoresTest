package mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ItemCollaboratorBinding
import mx.com.charlyescaz.colaboradorestest.models.Collaborator

class CollaboratorAdapter(private val collaborators: List<Collaborator>,
                          private val onClick: (Collaborator) -> Unit) :
    RecyclerView.Adapter<CollaboratorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborator, parent, false)
        )

    override fun getItemCount(): Int = collaborators.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collaborator = collaborators[position]
        holder.vBind.collaborator = collaborator
        holder.vBind.clItem.setOnClickListener { onClick(collaborator) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vBind = ItemCollaboratorBinding.bind(itemView)
    }
}