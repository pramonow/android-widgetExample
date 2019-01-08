package pramonow.com.widgetexample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.content.ComponentName




/**
 * Implementation of App Widget functionality.
 */
class SampleWidget : AppWidgetProvider() {

    private val ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGETSAMPLE"

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            Log.d("BANIWIDGET","update")
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("BANIWIDGET","enabled")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d("BANIWIDGET","disabled")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("BANIWIDGET","received")
        if (ACTION_SIMPLEAPPWIDGET.equals(intent.action)) {
            Log.d("BANIWIDGET","action!")
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.sample_widget)
            views.setTextViewText(R.id.widget_button, "HIT ME AGAIN!")
            // This time we dont have widgetId. Reaching our widget with that way.
            val appWidget = ComponentName(context, SampleWidget::class.java!!)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.sample_widget)
        views.setTextViewText(R.id.appwidget_text, "BANI WAS HERE")

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntent)

        // Construct an Intent which is pointing this class.
        val intentTwo = Intent(context, SampleWidget::class.java)
        intentTwo.action = ACTION_SIMPLEAPPWIDGET
        // And this time we are sending a broadcast with getBroadcast
        val pendingIntentTwo = PendingIntent.getBroadcast(context, 0, intentTwo, PendingIntent.FLAG_UPDATE_CURRENT)

        //views.setOnClickPendingIntent(R.id.widget_button, pendingIntent)
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntentTwo)


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

