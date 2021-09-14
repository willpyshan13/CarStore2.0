package com.yanxin.store.mvvm.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
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
import com.will.habit.binding.command.BindingAction
import com.will.habit.binding.command.BindingCommand
import com.will.habit.binding.command.BindingConsumer
import com.yanxin.store.BR
import com.yanxin.store.R
import com.yanxin.store.databinding.ActivityMineOrderBinding
import com.yanxin.store.databinding.ActivityMineWalletBinding
import com.yanxin.store.mvvm.entity.ReqWithDrawEntity
import com.yanxin.store.mvvm.entity.RespStatisticsDataEntity
import com.yanxin.store.mvvm.ui.dialog.WithDrawDialog
import com.yanxin.store.mvvm.viewmodel.MineOrderViewModel
import com.yanxin.store.mvvm.viewmodel.MineWalletViewModel
import java.text.SimpleDateFormat
import java.util.ArrayList

class MineWalletActivity :  BaseActivity<ActivityMineWalletBinding, MineWalletViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_wallet
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()

        viewModel.withDrawEvent.observe(this){
            it?.let {
                WithDrawDialog(this,it,bindingCommand).show()
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

        viewModel.initPie.observe(this){
            it?.let {
                setData(it,binding.chartOrder)
            }
        }
    }

    val bindingCommand = BindingCommand<ReqWithDrawEntity>(object :BindingConsumer<ReqWithDrawEntity>{
        override fun call(t: ReqWithDrawEntity) {
            viewModel.startWithDraw(t)
        }
    })

    override fun initData() {
        initChart(binding.chartOrder)
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
        chart.legend.isEnabled = false
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

    private fun setData(data:List<RespStatisticsDataEntity>, chart: PieChart?) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        val minValue = data.maxOf { it.data }.toFloat()
        for (i in data.indices) {
            if (minValue ==0F){
                entries.add(PieEntry(1F, ""))
            }else {
                entries.add(PieEntry(data[i].data.toFloat(), ""))
            }
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.formLineWidth = 10F
        dataSet.setDrawIcons(false)
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#366FEF"))
        colors.add(Color.parseColor("#64D695"))
        colors.add(Color.parseColor("#EAAB40"))
        colors.add(Color.parseColor("#E52E7E"))
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