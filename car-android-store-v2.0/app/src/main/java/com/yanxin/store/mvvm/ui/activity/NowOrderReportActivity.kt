package com.yanxin.store.mvvm.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.will.habit.base.BaseActivity
import com.yanxin.store.BR
import com.yanxin.store.R
import com.yanxin.store.databinding.ActivityStoreOrderNowBinding
import com.yanxin.store.mvvm.viewmodel.NowOrderReportViewModel
import java.util.*

class NowOrderReportActivity : BaseActivity<ActivityStoreOrderNowBinding, NowOrderReportViewModel>() {
    private var orderChart: PieChart? = null
    private var incomeChart: PieChart? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_store_order_now
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        orderChart = findViewById(R.id.chart_order)
        incomeChart = findViewById(R.id.chart_income)
        initChart(orderChart)
        initChart(incomeChart)
        setData(4, 2f, orderChart)
        setData(4, 2f, incomeChart)
    }

    private fun initChart(chart: PieChart?) {
        chart!!.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f
        chart.setDrawCenterText(true)
        chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);
        chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.textSize = 28f
        l.formSize = 40f
        l.setDrawInside(false)
        l.xEntrySpace = 200f
        l.yEntrySpace = 100f
        l.yOffset = 0f
        l.xOffset = 100f

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
    }

    private fun setData(count: Int, range: Float, chart: PieChart?) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(PieEntry((Math.random() * range + range / 5).toFloat()))
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.valueLinePart1Length = 0f
        dataSet.valueLinePart2Length = 0f
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.JOYFUL_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.COLORFUL_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.LIBERTY_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.PASTEL_COLORS) {
            colors.add(c)
        }
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart!!.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }
}