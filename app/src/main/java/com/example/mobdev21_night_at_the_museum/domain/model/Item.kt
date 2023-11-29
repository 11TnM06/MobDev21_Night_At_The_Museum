package com.example.mobdev21_night_at_the_museum.domain.model

import com.google.firebase.firestore.GeoPoint
import com.example.mobdev21_night_at_the_museum.AppPreferences
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Item(

    var name: String? = null,

    var thumbnail: String? = null,

    var time: String? = null,

    var description: String? = null,

    var streetView: @RawValue GeoPoint? = null,

    var model3d: String? = null,

    var collectionId: String? = null,

    var collection: MCollection? = null,

    var creatorId: String? = null,

    var creatorName: String? = null,

    var creator: Creator? = null,

    @Deprecated("Use fun safeIsLiked() key instead")
    var isLiked: Boolean? = null,

    var details: List<ItemDetail>? = null

    ) : MuseumModel() {

    fun safeIsLiked(): Boolean {
        if (isLiked == null) {
            mapIsLiked()
        }
        return isLiked ?: false
    }

    fun mapIsLiked() {
        isLiked = AppPreferences.getUserInfo().fitems?.contains(key) ?: false
    }
}

fun mockItem(): Item {
    return Item(
        name = "Dying Knight or The Partisan",
        thumbnail = "https://i.pinimg.com/236x/df/8f/41/df8f41ee25b7e0c335fe896cbe0b8cc6.jpg",
        creator = Creator(name = "Venanzo Crocetti"),
        time = "1947",
        description = "The clear tribute to the Dying Galata, bronze sculpture by the hellenic sculptor Epigonus which was part of the monumental complex of the Group of Attalus in Pergamon (230-220 B.C.) today known thanks to its roman marble copy (Rome, Musei Capitolini) is referred by Crocetti to the partisan struggle during the Resistance, the civil war that took place in Italy which, after the announced armistice on September 8th 1943, concludes the second world war.\n" +
                "In the gesture of the surrendering it’s implied the drama of the figuration: it’s not just a physical defeat but also an abandonment of the awareness, a deep and moral defeat that feels more like a voluntary act than a destiny occurrence.",
        collection = MCollection(name = "Foundation Venanzo Crocetti", thumbnail = "https://lh3.googleusercontent.com/ci/AJFM8rwoMyTaZQlAnIFidU5hpjH3VbXVKGpvkZrQRet7fEBW98CY2yMZpT2tIdLe2fR0NtkxIwOj"),
        details = listOf(
            ItemDetail(title = "Title", description = "Dying Knight or The Partisan"),
            ItemDetail(title = "Creator", description = "Venanzo Crocetti"),
            ItemDetail(title = "Date", description = "1947"),
            ItemDetail(title = "Location", description = "The Crocetti Museum, Rome"),
            ItemDetail(title = "Physical Dimensions", description = "cm. 8x24x20"),
            ItemDetail(title = "Type", description = "sculpture"),
            ItemDetail(title = "Medium", description = "bronze"),
        ),
        model3d = "table.glb",
    )
}
