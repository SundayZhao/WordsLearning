package com.MainView.Personal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.R;


public class PersonalFragment extends Fragment {

    private View rootView; //这是个fragment，没有完整生命周期，需要有一个rootView一般别动
    private static boolean isFirstLoad  = true;//这是个bug，如果不加完成标志，fragment会重复加载，导致部分数据重复初始化

    /*
组件
 */
    private TextView testView=null;

    public PersonalFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View thisView=inflater.inflate(R.layout.fragment_personal,null);
        initView(thisView);
        return thisView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 如果有需要加载的数据，就在这里调用
            //加载数据()
            isFirstLoad = false;
        }
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    protected void initView(View view) {
        testView=(TextView)view.findViewById(R.id.textView2);
        testView.setText("这就是第二个fragment");
    }


}
