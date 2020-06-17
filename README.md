# DynamicRecyclerview
Simple Custom/Dynamic Recyclerview Kotlin

```
private var mFeaturedAdapter = CustomRecyclerViewAdapter<ImageCard>(R.layout.card_item  
  , onBind = { view: View, featured: ImageCard, i: Int ->  
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
  ```

```
mFeaturedAdapter.addItems(datas)  
  
recyclerView.apply {  
  setVerticalLayout()  
    adapter = mFeaturedAdapter  
}
```
