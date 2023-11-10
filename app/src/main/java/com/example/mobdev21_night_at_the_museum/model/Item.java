package com.example.mobdev21_night_at_the_museum.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String name;
    private String thumbnail;
    private String time;
    private String description;
    private GeoPoint streetView;
    private String model3d;
    private String collectionId;
    private String collection;
    private String creatorId;
    private String creatorName;
    private String creator;
//    @Deprecated("Use fun safeIsLiked() key instead")
    private Boolean isLiked;
    private List<ItemDetail> details;

    public Item() {
    }

    public Item(String name, String thumbnail, String creatorName, String creator, String description, String collection, List<ItemDetail> details, String model3d) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.creatorName = creatorName;
        this.creator = creator;
        this.collection = collection;
        this.creatorId = creatorId;
        this.description = description;
        this.details = details;
        this.model3d = model3d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeoPoint getStreetView() {
        return streetView;
    }

    public void setStreetView(GeoPoint streetView) {
        this.streetView = streetView;
    }

    public String getModel3d() {
        return model3d;
    }

    public void setModel3d(String model3d) {
        this.model3d = model3d;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public List<ItemDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ItemDetail> details) {
        this.details = details;
    }

    public boolean safeIsLiked() {
        if (isLiked == null) {
            mapIsLiked();
        }
        return isLiked != null ? isLiked : false;
    }

    public void mapIsLiked() {
//        isLiked = AppPreferences.getUserInfo().getFitems().contains(key);
    }
    public Item mockItem() {
        List<ItemDetail> itemDetails = new ArrayList<>();
        itemDetails.add(new ItemDetail("Title", "Dying Knight or The Partisan"));
        itemDetails.add(new ItemDetail("Creator", "Venanzo Crocetti"));
        itemDetails.add(new ItemDetail("Date", "1947"));
        itemDetails.add(new ItemDetail("Location", "The Crocetti Museum, Rome"));
        itemDetails.add(new ItemDetail("Physical Dimensions", "cm. 8x24x20"));
        itemDetails.add(new ItemDetail("Type", "sculpture"));
        itemDetails.add(new ItemDetail("Medium", "bronze"));

        return new Item(
            "10m² 10m²",
            "https://lh3.googleusercontent.com/ci/ALr3YSF4G3eBiXPdVvmWbiFBGlKDIBX7U7x4m5L4XZJZtWadrX6WUHjLuyQB8B2MExRheK_8k-W3MQ",
            "Venanzo Crocetti",
            "1947",
            "The clear tribute to the Dying Galata, bronze sculpture by the hellenic sculptor Epigonus which was part of the monumental complex of the Group of Attalus in Pergamon (230-220 B.C.) today known thanks to its roman marble copy (Rome, Musei Capitolini) is referred by Crocetti to the partisan struggle during the Resistance, the civil war that took place in Italy which, after the announced armistice on September 8th 1943, concludes the second world war.\n" +
                    "In the gesture of the surrendering it’s implied the drama of the figuration: it’s not just a physical defeat but also an abandonment of the awareness, a deep and moral defeat that feels more like a voluntary act than a destiny occurrence.",
            "Foundation Venanzo Crocetti",
            itemDetails,
            null
        );
    }
}
