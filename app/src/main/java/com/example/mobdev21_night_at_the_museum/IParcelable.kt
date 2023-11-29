package com.example.mobdev21_night_at_the_museum

import android.os.Parcelable

interface IParcelable: Parcelable, Cloneable {

    override fun describeContents(): Int {
        return 0
    }

    override fun clone(): Any {
        return super.clone()
    }
}
