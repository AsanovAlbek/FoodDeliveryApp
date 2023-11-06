package albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter

import albek.petprojects.fooddeliveryapp.databinding.BannerItemBinding
import albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter.diff.BannerDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BannerAdapter(
    private val bannerImageUrls: MutableList<String> = mutableListOf(),
    private val click: () -> Unit = {}
): RecyclerView.Adapter<BannerAdapter.BannerHolder>() {
    inner class BannerHolder(
        private val bannerItemBinding: BannerItemBinding
    ): RecyclerView.ViewHolder(bannerItemBinding.root) {
        fun bindItem(url: String) {
            Glide.with(bannerItemBinding.bannerImage).load(url).into(bannerItemBinding.bannerImage)
            bannerItemBinding.root.setOnClickListener { click() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder =
        BannerHolder(
            BannerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = bannerImageUrls.size
    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.bindItem(bannerImageUrls[position])
    }

    fun update(banners: List<String>) {
        val callback = BannerDiffCallback(oldList = bannerImageUrls, newList = banners)
        val diff = DiffUtil.calculateDiff(callback)
        bannerImageUrls.clear()
        bannerImageUrls.addAll(banners)
        diff.dispatchUpdatesTo(this)
    }
}