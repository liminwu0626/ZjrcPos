package com.whty.zjrcpos.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whty.zjrcpos.R;


/**
* @function BaseFragment
* @author wulimin
* @time 2017/3/14 下午4:11
*/

public abstract  class BaseFragment<T> extends Fragment {
    private View view ;
    private LayoutInflater inflater;
    private  ViewGroup container ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater ;
        this.container = container ;
        return super.onCreateView(inflater,container,savedInstanceState);
    }
    /**
     * 设置显示右侧返回按钮
     */
    public void setBackView(){
        View backView = view.findViewById(R.id.back_view);
        if (backView==null){
            return;
        }
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    /**
     * 设置显示标题
     * @param txt
     */
    public void setTitle(String txt){
        TextView title = (TextView) view.findViewById(R.id.title);
        if (title==null){
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(txt);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取控件id
     * @param id
     * @return
     */
    public View findViewById(int id){
        return view.findViewById(id) ;
    }

    private OnReloadDataListener onReloadDataListener;

    public void setOnReloadDataListener(OnReloadDataListener onReloadDataListener) {
        this.onReloadDataListener = onReloadDataListener;
    }

    public interface OnReloadDataListener{
        void request(boolean isRefresh);
    }
}
