package com.beyole.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewContainer extends ViewGroup {

	public CustomViewContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomViewContainer(Context context) {
		super(context);
	}

	// 决定该viewgroup的layoutParams为系统的MarginLayoutParams
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	// 决定childview的测量模式及测量值，以及设置自己的宽度和高度
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 获得viewgroup上级容器推荐的宽度和高度，和测量模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		// 计算出所有的childview的宽度和高度
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		// 如果是wrap_content设置的宽度和高度
		int width = 0;
		int height = 0;
		int cWidth = 0;
		int cHeight = 0;
		int count = getChildCount();
		MarginLayoutParams params = null;
		// 计算左边两个childview的高度
		int lHeight = 0;
		// 计算右边两个childview的高度，最终高度取两者最大值
		int rHeight = 0;
		// 计算上面两个childview的宽度
		int tWidth = 0;
		// 计算下面两个childview的宽度，最终宽度取两者最大值
		int bWidth = 0;
		// 根据计算出的childview的宽和高以及设置的MarginLayoutParams的值，主要用于容器是wrap_content时
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			cWidth = childView.getMeasuredWidth();
			cHeight = childView.getMeasuredHeight();
			params = (MarginLayoutParams) childView.getLayoutParams();
			// 上面两个childview
			if (i == 0 || i == 1) {
				tWidth += cWidth + params.leftMargin + params.rightMargin;
			}
			// 下面两个childview
			if (i == 2 || i == 3) {
				bWidth += cWidth + params.leftMargin + params.rightMargin;
			}
			// 左边两个childview
			if (i == 0 || i == 2) {
				lHeight += cHeight + params.topMargin + params.bottomMargin;
			}
			// 右边两个childview
			if (i == 1 || i == 3) {
				rHeight += cHeight + params.topMargin + params.bottomMargin;
			}
		}
		width = Math.max(tWidth, bWidth);
		height = Math.max(lHeight, rHeight);
		// 如果是wrap_content则设置计算的值,否则直接设置容器设置的值
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : Math.min(width, widthSize), heightMode == MeasureSpec.EXACTLY ? heightSize : Math.min(height, heightSize));
	}

	// 对所有的childview进行定位
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 获取子view的个数
		int count = getChildCount();
		// childview宽度
		int cWidth = 0;
		// childview高度
		int cHeight = 0;
		MarginLayoutParams params = null;
		// 遍历所有的childview对其进行布局
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			cWidth = childView.getMeasuredWidth();
			cHeight = childView.getMeasuredHeight();
			params = (MarginLayoutParams) childView.getLayoutParams();
			int cl = 0, cr = 0, ct = 0, cb = 0;
			switch (i) {
			case 0:
				cl = params.leftMargin;
				ct = params.topMargin;
				break;
			case 1:
				cl = getWidth() - cWidth - params.rightMargin;
				ct = params.topMargin;
				break;
			case 2:
				cl = params.leftMargin;
				ct = getHeight() - cHeight - params.bottomMargin;
				break;
			case 3:
				cl = getWidth() - cWidth - params.rightMargin;
				ct = getHeight() - cHeight - params.bottomMargin;
				break;
			}
			cr = cl + cWidth;
			cb = ct + cHeight;
			childView.layout(cl, ct, cr, cb);
		}
	}

}
