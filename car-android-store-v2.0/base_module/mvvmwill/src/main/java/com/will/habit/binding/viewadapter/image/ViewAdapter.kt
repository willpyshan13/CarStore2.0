package com.will.habit.binding.viewadapter.image

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.util.Util
import com.will.habit.base.BaseApplication
import com.will.habit.utils.DpUtil
import com.will.habit.utils.Utils
import com.will.habit.utils.image.RoundedCornersTransform

@BindingMethods(
        BindingMethod(type = ImageView::class, attribute = "imageUrl", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "imageKeyName", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "viewRound", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "colorPlaceholder", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "imagePlaceholder", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "isCircle", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "isCrossFade", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "isPlaceholderRound", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "isPlaceholderCircle", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "viewRoundTopLeft", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "viewRoundTopRight", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "viewRoundBottomLeft", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "viewRoundBottomRight", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "resDrawable", method = "setImageUrl"),
        BindingMethod(type = ImageView::class, attribute = "priority", method = "setImageUrl")
)
class ViewAdapter

/**
 * Desc: ??????Glide?????????????????????????????????
 *
 *
 * Author: will
 * Date: 2020-07-11
 *
 * @param imageUrl             ????????????
 * @param imageKeyName         ??????keyName
 * @param viewRound            ??????????????????
 * @param colorPlaceholder     ???????????????
 * @param imagePlaceholder     ???????????????
 * @param isCircle             ????????????????????????
 * @param isCrossFade          ??????????????????
 * @param isPlaceholderRound   ?????????????????????????????????
 * @param isPlaceholderCircle  ?????????????????????????????????
 * @param viewRoundTopLeft     ?????????????????????
 * @param viewRoundTopRight    ?????????????????????
 * @param viewRoundBottomLeft  ?????????????????????
 * @param viewRoundBottomRight ??????you???????????????
 * @param resDrawable          res????????????
 * @param priority             ???????????????
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@BindingAdapter(value = ["imageUrl", "imageKeyName", "viewRound", "colorPlaceholder", "imagePlaceholder", "isCircle", "isCrossFade", "isPlaceholderRound", "isPlaceholderCircle", "viewRoundTopLeft", "viewRoundTopRight", "viewRoundBottomLeft", "viewRoundBottomRight", "resDrawable", "priority"], requireAll = false)
fun ImageView.setImageUrl(
        imageUrl: String? = "",
        imageKeyName: String? = "",
        viewRound: Int = 0,
        colorPlaceholder: Int = 0,
        imagePlaceholder: Drawable? = null,
        isCircle: Boolean = false,
        isCrossFade: Boolean = false,
        isPlaceholderRound: Boolean = false,
        isPlaceholderCircle: Boolean = false,
        viewRoundTopLeft: Boolean = false,
        viewRoundTopRight: Boolean = false,
        viewRoundBottomLeft: Boolean = false,
        viewRoundBottomRight: Boolean = false,
        resDrawable: Drawable? = null,
        priority: Priority? = null) {
    val context = context
    if (context is Activity && context.isDestroyed) {
        return
    }
    var placeholder: Drawable? = null
    if (colorPlaceholder != 0) {
        // ?????????????????????
        val drawable = GradientDrawable()
        drawable.setColor(Color.parseColor(String.format("#%x", colorPlaceholder)))
        drawable.cornerRadius = DpUtil.dp2px(viewRound.toFloat()).toFloat()
        placeholder = drawable
    } else if (imagePlaceholder != null) {
        // ?????????????????????
        if (isPlaceholderCircle || isPlaceholderRound) {
            val drawable = imagePlaceholder as BitmapDrawable
            val roundDrawable = RoundedBitmapDrawableFactory.create(Utils.getContext().resources, drawable.bitmap)
            roundDrawable.paint.isAntiAlias = true
            val cornerRadius = if (isPlaceholderRound) DpUtil.dp2px(viewRound.toFloat()).toFloat() else drawable.bitmap.width / 2f
            roundDrawable.cornerRadius = cornerRadius
            placeholder = roundDrawable
        } else {
            placeholder = imagePlaceholder
        }
    }
    // ?????????null??????????????????
    if (resDrawable == null && TextUtils.isEmpty(imageUrl)) {
        Glide.with(this).clear(this)
        this.setImageDrawable(placeholder)
        return
    }

    var builder: RequestBuilder<Drawable?>
    var requestOptions: RequestOptions
    // ??????image url???image Key
    builder = when {
        resDrawable != null -> { // ??????res??????
            Glide.with(this).load(resDrawable)
        }
        TextUtils.isEmpty(imageKeyName) -> { // ??????????????????
            Glide.with(this).load(imageUrl)
        }
        else ->  Glide.with(this).load(imageUrl)
    }
    val isAllRound = !(viewRoundTopLeft || viewRoundTopRight || viewRoundBottomLeft || viewRoundBottomRight)

    builder = builder.apply(RequestOptions.placeholderOf(placeholder))

//    // ???????????????????????????
    requestOptions = if (isCircle) {
        RequestOptions.bitmapTransform(CircleCrop())
    } else if (viewRound > 0) {
        if (isAllRound) {
            RequestOptions().transforms(CenterCrop(), RoundedCorners(DpUtil.dp2px(viewRound.toFloat())))
        } else {
            val transform = RoundedCornersTransform(Utils.getContext(), DpUtil.dp2px(viewRound.toFloat()).toFloat())
            transform.setNeedCorner(viewRoundTopLeft, viewRoundTopRight, viewRoundBottomLeft, viewRoundBottomRight)
            RequestOptions().transforms(CenterCrop(), transform)
        }
    } else {
        RequestOptions()
    }
    if (isCrossFade) { //??????300ms
        builder = builder.transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true)))
    }
    requestOptions = if (priority != null) {
        requestOptions.priority(priority)
    } else {
        requestOptions.priority(Priority.NORMAL)
    }
    builder.apply(requestOptions).into(this)
}
