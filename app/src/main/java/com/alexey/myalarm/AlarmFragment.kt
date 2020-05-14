package com.alexey.myalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TimePicker
import com.google.android.material.snackbar.Snackbar
//import android.support.design.widget.Snackbar
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
//было бы неплохо сделать snackbar
//передать millisecond в setRepeating вторым параметром
//назначить интервал 5 мин=300000 милисекунд
//нужна ли кнопка обновления?
//сделать будильник со звуком
class AlarmFragment : Fragment() {

    private lateinit var intent: Intent
    private lateinit var pendingIntent: PendingIntent
    private  lateinit var viewGroup:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_time, container, false)
        viewGroup=view
        try {
            val context: Context = this.activity as Context
            val buttonCancel = view.findViewById<Button>(R.id.cancel)
            val buttonInstallTime = view.findViewById<Button>(R.id.buttonInstallTime)
            intent = Intent(context, Receiver::class.java)
            this.pendingIntent = PendingIntent.getBroadcast(context, 0, this.intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val imageView=view.findViewById<ImageView>(R.id.image)
            val anim=AnimationUtils.loadAnimation(context,R.anim.animation_two)


            buttonCancel.setOnClickListener {
                cancelAlarm(context)
                imageView.startAnimation(anim)
            }
            buttonInstallTime.setOnClickListener {
                setTime(context)
                Log.i("teg",System.currentTimeMillis().toString())
            }
        } catch (e: NullPointerException) {
            val simpleDateFormat = SimpleDateFormat("HH:mm")
            Log.e("error", simpleDateFormat.format(Date()))
        }
        return view
    }

    private fun setAlarm(context: Context, calendar: Calendar) {
        if (context == null) {
            return
        }
        Log.i("flag2",calendar.timeInMillis.toString())
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setRepeating(
            AlarmManager.RTC_WAKEUP,/*
            calendar.timeInMillis*/calendar.timeInMillis,
            300000,
            this.pendingIntent)
    }

    private fun setTime(context: Context) {
        val objCalendar:Calendar = GregorianCalendar.getInstance()
        val time = object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                ////objCalendar = Calendar.getInstance().apply {
                            objCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                            objCalendar.set(Calendar.MINUTE,minute)
                            objCalendar.set(Calendar.SECOND,0)
                            objCalendar.set(Calendar.MILLISECOND,0)
                //}
                //val cal1=Calendar.getInstance()
                Log.i("flag3",objCalendar.timeInMillis.toString())
                //Log.i("flag",cal1.timeInMillis.toString())
                Log.i("flag", hourOfDay.toString())
                Log.i("flag", minute.toString())
                //Log.i("flag", millisecond.toString())
                setAlarm(context,objCalendar)
                //val rootElem=view!!.findViewById<View>(R.id.activity)
                Snackbar.make(viewGroup,"Будильник установлен на $hourOfDay:$minute",Snackbar.LENGTH_LONG).show()
                    //Snackbar.make(rootElem,"Будильник установлен на $hourOfDay:$minute",Snackbar.LENGTH_INDEFINITE).show()

            }
        }
        TimePickerDialog(context, time, 0, 0, true).show()
    }

    private fun cancelAlarm(context: Context) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(this.pendingIntent)
    }
}
