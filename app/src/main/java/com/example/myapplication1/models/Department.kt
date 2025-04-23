package com.example.myapplication1.models
import android.os.Parcel
import android.os.Parcelable

data class Department(
    val name: String="",
    val resolvedComplaints: Int=0

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(resolvedComplaints)
    }

    override fun describeContents(): Int=0

    companion object CREATOR : Parcelable.Creator<Department> {
        override fun createFromParcel(parcel: Parcel): Department {
            return Department(parcel)
        }

        override fun newArray(size: Int): Array<Department?> {
            return arrayOfNulls(size)
        }
    }
}