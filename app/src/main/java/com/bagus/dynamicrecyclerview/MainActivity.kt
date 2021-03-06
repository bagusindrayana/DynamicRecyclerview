package com.bagus.dynamicrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import com.bagus.dynamicrecyclerview.adapter.CustomRecyclerViewAdapter
import com.bagus.dynamicrecyclerview.model.ImageCard
import com.bagus.dynamicrecyclerview.utils.setVerticalLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mFeaturedAdapter = CustomRecyclerViewAdapter(R.layout.card_item
        , onBind = { view: View, featured: ImageCard, _: Int ->
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_undraw_photo_4yb9)
                .centerCrop()
            Glide.with(this@MainActivity)
                .load(featured.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .signature(ObjectKey(System.currentTimeMillis().toString()))
                .apply(requestOptions)
                .into(view.findViewById(R.id.imageView))

            view.findViewById<TextView>(R.id.title).text = featured.title
            view.findViewById<TextView>(R.id.description).text = featured.description

        })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImage("any")

        findViewById<Button>(R.id.loadAny).setOnClickListener{
            loadImage("any")
        }

        findViewById<Button>(R.id.loadNature).setOnClickListener{
            loadImage("nature")
        }

        findViewById<SearchView>(R.id.data_search).setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mFeaturedAdapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun loadImage(type : String){
        val url = "https://placeimg.com/640/480/${type}"
        val datas : ArrayList<ImageCard> = arrayListOf()

        val total  = 1..10
        for(i in total){
            datas.add(ImageCard(url,"Title for $type $i","Descriptiopn for $type $i"))
        }

        mFeaturedAdapter.clearItems()
        mFeaturedAdapter.addItems(datas)

        recyclerView.apply {
            setVerticalLayout()
            adapter = mFeaturedAdapter
        }


    }


}
