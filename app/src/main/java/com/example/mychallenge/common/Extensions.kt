@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.mychallenge.common


import android.util.Log
import android.util.Patterns
import com.example.mychallenge.common.Keys.Companion.FORMAT_DATE
import com.example.mychallenge.common.Keys.Companion.FORMAT_TIME
import com.example.mychallenge.domain.module.IPCData
import com.example.mychallenge.domain.module.TopTens
import com.google.android.material.textfield.TextInputLayout
import org.joda.time.DateTime


object Extensions {

    fun String.isEmailValid(): StatusUser {
       return if(Patterns.EMAIL_ADDRESS.matcher(this).matches())
           StatusUser.INFO_SUCCESS
        else
            StatusUser.MAIL_ERROR
    }

    fun String.isPasswordValid(): StatusUser{
        return if(this.length > 8)
            StatusUser.INFO_SUCCESS
        else
            StatusUser.PASSWORD_ERROR
    }

    fun String.isNameValid(): StatusUser{
        return if(this.isNotBlank())
            StatusUser.INFO_SUCCESS
        else
            StatusUser.NAME_ERROR
    }

    fun String.isEqualPassword(password: String): StatusUser {
        return if(this == password)
            StatusUser.INFO_SUCCESS
        else
            StatusUser.PASSWORD_NOT_THE_SAME
    }

    fun TextInputLayout.getShowError(errorString: String){
        this.error = errorString
    }
    
    fun List<IPCData>.getOrderInfo(): MutableMap<Long, Float>{
        val mutableXY: MutableMap<Long, Float> = mutableMapOf()
        this.forEach { ipcData ->
            val date = DateTime(ipcData.date.toString())
            mutableXY[date.millis] = ipcData.price!!.toFloat()
        }

        val listOrder = mutableXY.toList().sortedBy { it.first}.toMutableList()

        listOrder.forEach { (s, fl) ->
            val date = DateTime(s)
            Log.d("logTable", "getOrderInfo: ${date.dayOfMonth} - ${date.monthOfYear} - ${date.year} - ${date.hourOfDay}: ${date.minuteOfHour}: ${date.secondOfMinute}  -  $fl")
        }

        return mutableXY
    }

    fun MutableSet<Long>.getDate(): String{
        val listTimeOrder = this.sortedBy{it}
        val startDate = DateTime(listTimeOrder.first()).toLocalDate().toString(FORMAT_DATE)
        val endDate = DateTime(listTimeOrder.last()).toLocalDate().toString(FORMAT_DATE)

        return if(startDate == endDate) startDate else "${startDate} - ${endDate}"
    }

    fun MutableSet<Long>.getTime(): List<String>{
        val listTime = arrayListOf<String>()
        this.forEach{ date ->
            listTime.add(DateTime(date).toLocalTime().toString(FORMAT_TIME))
        }
        return listTime
    }


    fun List<TopTens>.getOrderList(): List<Any>{
        val listByGroup: MutableMap<Int, ArrayList<TopTens>>? = mutableMapOf()
        val listOrderByType = this.sortedBy { it.riseLowTypeId }
        listOrderByType.forEach { topTen ->
            topTen.riseLowTypeId?.let { type ->
                if(listByGroup!!.containsKey(type))
                    listByGroup[type]?.add(topTen)
                else
                    listByGroup.put(type, arrayListOf(topTen))
            }
        }

        val items: ArrayList<Any> = ArrayList()
        listByGroup?.forEach { (type, topTen) ->
            items.add(type)
            items.addAll(topTen)
        }

        return items
    }
}