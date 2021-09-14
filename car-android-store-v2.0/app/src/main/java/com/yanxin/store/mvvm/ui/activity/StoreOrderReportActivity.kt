package com.yanxin.store.mvvm.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.bigkoo.pickerview.builder.TimePickerBuilder
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
import com.yanxin.store.databinding.ActivityStoreOrderReportBinding
import com.yanxin.store.mvvm.entity.RespStoreReportEntity
import com.yanxin.store.mvvm.viewmodel.StoreOrderReportViewModel
import java.util.*

/**
 *
 * <p>现场支持
 * Date: 2021-07-27
 * Company: xmotion
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * Author: will
 */
class StoreOrderReportActivity : BaseActivity<ActivityStoreOrderReportBinding, StoreOrderReportViewModel>() {
    private var orderChart: PieChart? = null
    private var incomeChart: PieChart? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_store_order_report
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.amountDataChange.observe(this){
            it?.let {
                setData(it, orderChart)
            }
        }

        viewModel.countDataChange.observe(this){
            it?.let {
                setData(it, incomeChart)
            }
        }

        viewModel.showDatePick.observe(this){
            TimePickerBuilder(
                MineWalletActivity@this
            ) { date, v ->
                viewModel.setTimeText(date)
            }.setType(booleanArrayOf(true, true, false, false, false, false))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build().show()
        }

    }

    override fun initData() {
        super.initData()
        orderChart = findViewById(R.id.chart_order)
        incomeChart = findViewById(R.id.chart_income)
        initChart(orderChart)
        initChart(incomeChart)
    }

    private fun initChart(chart: PieChart?) {
        chart!!.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 5f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.holeRadius = 60f
        chart.transparentCircleRadius = 60f
        chart.setDrawCenterText(true)
        chart.rotationAngle = 30F
        chart.setDrawRoundedSlices(true)
        chart.setDrawEntryLabels(false)
        // enable rotation of the chart by touch

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);
        chart.animateY(1000, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.textSize = 14f
        l.formSize = 10f
        l.setDrawInside(false)
        l.xEntrySpace = 50f
        l.yEntrySpace = 20f
        l.xOffset = 10f

        val ll = chart.legendRenderer

        // entry label styling
//        chart.setEntryLabelColor(Color.WHITE)
//        chart.setEntryLabelTextSize(12f)
    }

    private fun setData(data: RespStoreReportEntity, chart: PieChart?) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        var goodsTotal = data.goodsTotal.toFloat()
        var stationTotal = data.stationTotal.toFloat()
        var groupbuyTotal = data.groupbuyTotal.toFloat()
        if (goodsTotal ==0F&&goodsTotal ==0F&&goodsTotal ==0F){
            goodsTotal = 1F
            stationTotal = 1F
            groupbuyTotal = 1F
        }

        entries.add(PieEntry(goodsTotal,"商品订单      ${data.goodsTotal}  "))
        entries.add(PieEntry(stationTotal,"工位订单      ${data.stationTotal}    "))
        entries.add(PieEntry(groupbuyTotal,"拼团订单      ${data.groupbuyTotal}    "))

        val dataSet = PieDataSet(entries, "")
        dataSet.formLineWidth = 10F
        dataSet.setDrawIcons(false)
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
        data.setDrawValues(false)
        data.setValueTextColor(Color.WHITE)
        chart!!.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }
}