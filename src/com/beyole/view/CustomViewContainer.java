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

	// ������viewgroup��layoutParamsΪϵͳ��MarginLayoutParams
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	// ����childview�Ĳ���ģʽ������ֵ���Լ������Լ��Ŀ�Ⱥ͸߶�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// ���viewgroup�ϼ������Ƽ��Ŀ�Ⱥ͸߶ȣ��Ͳ���ģʽ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		// ��������е�childview�Ŀ�Ⱥ͸߶�
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		// �����wrap_content���õĿ�Ⱥ͸߶�
		int width = 0;
		int height = 0;
		int cWidth = 0;
		int cHeight = 0;
		int count = getChildCount();
		MarginLayoutParams params = null;
		// �����������childview�ĸ߶�
		int lHeight = 0;
		// �����ұ�����childview�ĸ߶ȣ����ո߶�ȡ�������ֵ
		int rHeight = 0;
		// ������������childview�Ŀ��
		int tWidth = 0;
		// ������������childview�Ŀ�ȣ����տ��ȡ�������ֵ
		int bWidth = 0;
		// ���ݼ������childview�Ŀ�͸��Լ����õ�MarginLayoutParams��ֵ����Ҫ����������wrap_contentʱ
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			cWidth = childView.getMeasuredWidth();
			cHeight = childView.getMeasuredHeight();
			params = (MarginLayoutParams) childView.getLayoutParams();
			// ��������childview
			if (i == 0 || i == 1) {
				tWidth += cWidth + params.leftMargin + params.rightMargin;
			}
			// ��������childview
			if (i == 2 || i == 3) {
				bWidth += cWidth + params.leftMargin + params.rightMargin;
			}
			// �������childview
			if (i == 0 || i == 2) {
				lHeight += cHeight + params.topMargin + params.bottomMargin;
			}
			// �ұ�����childview
			if (i == 1 || i == 3) {
				rHeight += cHeight + params.topMargin + params.bottomMargin;
			}
		}
		width = Math.max(tWidth, bWidth);
		height = Math.max(lHeight, rHeight);
		// �����wrap_content�����ü����ֵ,����ֱ�������������õ�ֵ
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : Math.min(width, widthSize), heightMode == MeasureSpec.EXACTLY ? heightSize : Math.min(height, heightSize));
	}

	// �����е�childview���ж�λ
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// ��ȡ��view�ĸ���
		int count = getChildCount();
		// childview���
		int cWidth = 0;
		// childview�߶�
		int cHeight = 0;
		MarginLayoutParams params = null;
		// �������е�childview������в���
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
