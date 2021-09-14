package com.yanxin.store.mvvm.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.will.habit.base.BaseActivity
import com.yanxin.store.BR
import com.yanxin.store.R
import com.yanxin.store.databinding.ActivityOnSiteSupportBinding
import com.yanxin.store.mvvm.entity.RespOnSiteSupportAmountEntity
import com.yanxin.store.mvvm.viewmodel.OnSiteSupportViewModel
import com.yanxin.store.widget.MyMarkerView
import java.util.*

class OnSiteSupportActivity : BaseActivity<ActivityOnSiteSupportBinding, OnSiteSupportViewModel>() {
    private var chartMoney: LineChart? = null
    private var chartCount: LineChart? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_on_site_support
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        chartCount = findViewById(R.id.chart2)
        chartMoney = findViewById(R.id.chart1)
        initChart(chartCount,0)
        initChart(chartMoney,1)
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.amountData.observe(this){
            it?.let {
                chartMoney?.data = LineData(generateDataLine(it,"交易金额"))
                chartMoney?.invalidate()
            }
        }

        viewModel.totalData.observe(this){
            it?.let {
                chartCount?.data = LineData(generateDataLine(it,"交易订单"))
                chartCount?.invalidate()
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

    private fun initChart(chart: LineChart?,type:Int) {
        if (type ==0){
            val mv = MyMarkerView(this, R.layout.custom_marker_view_order)
            mv.chartView = chart // For bounds control
            chart!!.marker = mv // Set the marker to the chart
        }else{
            val mv = MyMarkerView(this, R.layout.custom_marker_view_money)
            mv.chartView = chart // For bounds control
            chart!!.marker = mv // Set the marker to the chart
        }

        // if disabled, scaling can be done on x- and y-axis separately
//        chart.setPinchZoom(false);
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = 6
        val formatter: ValueFormatter = DataFormat()
        xAxis.valueFormatter = formatter
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        val leftAxis = chart.axisLeft
        leftAxis.setLabelCount(5, false)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
    }

    private fun generateDataLine(cnt: List<RespOnSiteSupportAmountEntity>,title:String): ArrayList<ILineDataSet> {
        val values1 = ArrayList<Entry>()
        for (i in cnt.indices) {
            values1.add(Entry(i.toFloat(), cnt[i].totalNum.toFloat()))
        }
        val d1 = LineDataSet(values1, title)
        d1.lineWidth = 2.5f
        d1.circleRadius = 4.5f
        d1.highLightColor = Color.rgb(244, 117, 117)
        d1.setDrawValues(true)
        val sets = ArrayList<ILineDataSet>()
        sets.add(d1)
        return sets
    }

    internal inner class DataFormat : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return super.getFormattedValue(value)
        }
    }
}