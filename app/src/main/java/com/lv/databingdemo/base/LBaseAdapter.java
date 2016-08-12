package com.lv.databingdemo.base;

import android.animation.Animator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.lv.databingdemo.anim.BaseAnimation;
import com.lv.databingdemo.anim.SlideInBottomAnimation;
import com.lv.databingdemo.util.ArrayUtils;
import com.lv.databingdemo.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 吕勇
 * Date: 2016-03-01
 * Time: 8:39
 * Description:可以爲RecyclerView添加HeaderView
 */

public  class LBaseAdapter<T> extends RecyclerView.Adapter<LBaseHolder> {

    private boolean mOpenAnimationEnable = true;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;
    private BaseAnimation mBaseAnimation;
    private View mHeaderView;
    private View mFooterView;
    private EmptyView mEmptyView;

    protected int mLayoutResId;
    protected int mBindingId;
    protected List<T> mDatas;
    protected static final int HEADER_VIEW = 0x00000111;
    protected static final int FOOTER_VIEW = 0x00000333;
    protected static final int EMPTY_VIEW = 0x00000555;
    protected boolean mFirstOnlyEnable = true;
    protected RelativeLayout.LayoutParams layoutParams;

    private OnRecyclerItemChildClickListener mChildClickListener;

    /**
     * 设置每个Item中子View的点击事件
     */
    public void setOnRecyclerItemChildClickListener(OnRecyclerItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public interface OnRecyclerItemChildClickListener {
        void onItemChildClick(View view, int position);
    }

    public class OnItemChildClickListener implements View.OnClickListener {
        public int position;

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null)
                mChildClickListener.onItemChildClick(v, position - getHeaderViewsCount());
        }
    }

    public LBaseAdapter(int layoutResId) {
        this(layoutResId, 0);
    }
    public LBaseAdapter(int layoutResId, int bindingId) {
        this(layoutResId, bindingId,new ArrayList<T>());
    }


    public LBaseAdapter(int layoutResId,int bindingId, List<T> datas) {
        this.mDatas = datas;
        this.mLayoutResId = layoutResId;
        this.mBindingId=bindingId;
        this.mBaseAnimation = new SlideInBottomAnimation();
    }


    public void addItems(List<T> items) {
        if (null != items) {
            int oldCont = mDatas.size();
            mDatas.addAll(items);
            if (oldCont == 0)
                notifyDataSetChanged();
            else
                notifyItemRangeChanged(oldCont, mDatas.size());
        }
    }

    public void addItem(T item) {
        mDatas.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items, boolean isRefresh) {
        if (isRefresh) {
            mDatas.clear();
            notifyDataSetChanged();
        }
        addItems(items);
    }


    public T getItem(int position) {
        return mDatas.get(position);
    }

    public int getHeaderViewsCount() {
        return mHeaderView == null ? 0 : 1;
    }

    public int getFooterViewsCount() {
        return mFooterView == null ? 0 : 1;
    }

    public int getmEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        int count = mDatas.size() + getHeaderViewsCount() + getFooterViewsCount();
        if ((getHeaderViewsCount() == 1 && count == 1) || count == 0)
            count += getmEmptyViewCount();
        return count;
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return HEADER_VIEW;
        } else if (mEmptyView != null && mDatas.size() == 0) {
            return EMPTY_VIEW;
        } else if (position == mDatas.size() + getHeaderViewsCount()) {
            return FOOTER_VIEW;
        }
        return getDefItemViewType(position);
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public LBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LBaseHolder baseViewHolder;
        switch (viewType) {
            case HEADER_VIEW:
                baseViewHolder = new LBaseHolder(mHeaderView);
                break;
            case EMPTY_VIEW:
                baseViewHolder = new LBaseHolder(mEmptyView);
                break;
            case FOOTER_VIEW:
                baseViewHolder = new LBaseHolder(mFooterView);
                break;
            default:
                baseViewHolder = onCreateDefViewHolder(parent,viewType);
        }
        return baseViewHolder;

    }


    @Override
    public void onBindViewHolder(LBaseHolder baseHolder, int positions) {
        switch (baseHolder.getItemViewType()) {
            case EMPTY_VIEW:
                if (layoutParams == null && mHeaderView != null && mHeaderView.getParent() instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) mHeaderView.getParent();
                    layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, recyclerView.getHeight() - mHeaderView.getHeight());
                    mEmptyView.setLayoutParams(layoutParams);
                }
                mEmptyView.showEmptyView();
                break;
            case HEADER_VIEW:
            case FOOTER_VIEW:
                break;
            default:
                int pos = getRealPosition(baseHolder);
                baseHolder.itemView.setTag(pos);
                if (0 != mBindingId)
                    baseHolder.getDataBinding().setVariable(mBindingId, mDatas.get(pos));
                onBindItem(baseHolder, pos, mDatas.get(pos));
                addAnimation(baseHolder);
                break;
        }

    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        return holder.getLayoutPosition() - getHeaderViewsCount();
    }

    protected LBaseHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }

    protected LBaseHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        LBaseHolder baseViewHolder = new LBaseHolder(parent,layoutResId);
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object vTag = view.getTag();
                if (vTag != null && vTag instanceof Integer)
                    onItemClick(mDatas.get(Integer.valueOf(vTag.toString())));
            }
        });
        return baseViewHolder;
    }

    protected void onItemClick(T t) {

    }


    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        this.mHeaderView = header;
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        this.mFooterView = footer;
        this.notifyDataSetChanged();
    }

    public void setEmptyView(EmptyView emptyView) {
        mEmptyView = emptyView;
    }

    public void setFirstOnlyEnable(boolean firstOnlyEnable) {
        mFirstOnlyEnable = firstOnlyEnable;
    }

    public EmptyView getEmptyView() {
        return mEmptyView;
    }


    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                for (Animator anim : mBaseAnimation.getAnimators(holder.itemView)) {
                    anim.setDuration(mDuration).start();
                    anim.setInterpolator(mInterpolator);
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }




    public void setBaseAnimation(BaseAnimation baseAnimation) {
        this.mOpenAnimationEnable = true;
        this.mBaseAnimation = baseAnimation;
    }

    public void setOpenAnimationEnable(boolean openAnimationEnable) {
        mOpenAnimationEnable = openAnimationEnable;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    public  void onBindItem(LBaseHolder baseHolder, int realPosition, T t){

    }

    //需要处理瀑布流的时候再放开
    /*@Override
    public void onViewAttachedToWindow(LBaseViewHolder baseHolder) {
        super.onViewAttachedToWindow(baseHolder);
        ViewGroup.LayoutParams lp = baseHolder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            int position=baseHolder.getLayoutPosition();
            if( (isHeaderView(position) || isBottomView(position))){
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isBottomView(position) || ArrayUtils.isEmpty(mDatas)) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return getHeaderViewsCount() != 0 && position < getHeaderViewsCount();
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return getFooterViewsCount() != 0 && position >= (getHeaderViewsCount() + mDatas.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isEmpty() {
        return ArrayUtils.isEmpty(mDatas);
    }

    public void clearDatas() {
        if (!isEmpty()) {
            mDatas.clear();
            notifyDataSetChanged();
        }

    }

    public boolean isLast(int position) {
        return (mDatas.size() - 1) == position;
    }

}