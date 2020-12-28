package com.MainView.Personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.MainView.MainActivity;
import com.R;
import com.Unit.DiffCollection;
import com.Unit.User;
import com.Unit.Word;
import com.UserView.LoginActivity;
import com.dao.RemotoDatabase;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;


import java.util.ArrayList;


public class PersonalFragment extends Fragment {


    private View rootView; //这是个fragment，没有完整生命周期，需要有一个rootView一般别动
    private static boolean isFirstLoad = true;//这是个bug，如果不加完成标志，fragment会重复加载，导致部分数据重复初始化

    /*
组件
 */
    private QMUIRadiusImageView profilePhotoView = null;//圆形头像框
    private QMUIBottomSheet.BottomListSheetBuilder profilePhotoSelectorBuilder = null;//下面弹出那个选择头像的dialog
    private GridView utilsGridView=null;//功能菜单
    private TextView nameView=null;//名字的文本框
    private QMUIPullRefreshLayout qmuiPullRefreshLayout=null;//下拉刷新

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fragment_personal, null);
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
        RemotoDatabase.getInstance(getContext());
        profilePhotoView = (QMUIRadiusImageView) view.findViewById(R.id.personalProfilePhoto);
        profilePhotoSelectorBuilder = new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
        utilsGridView=(GridView)view.findViewById(R.id.personal_gridview);
        nameView=(TextView)view.findViewById(R.id.textview_pesrsonal_nickname);
        qmuiPullRefreshLayout=(QMUIPullRefreshLayout)view.findViewById(R.id.refreshlayout_personal);
        //初始化头像框相关的东西
        initprofilePhotoView();
        //初始化下方的列表
        initutilsGridView();
        //初始化下拉刷新
        initFreshlayout();
    }

    private void initFreshlayout() {
        qmuiPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                qmuiPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO:下拉刷新事件
                        User.getInstance(getContext()).logIn("zhanghao","123456");
//                        User.getInstance(getContext()).getWrongCollection().addWord(new Word("good"));
//                        User.getInstance(getContext()).getWrongCollection().addWord(new Word("well"));
//                        User.getInstance(getContext()).getWrongCollection().addWord(new Word("nice"));
                        initprofilePhotoView();
                        qmuiPullRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });
    }

    private void initutilsGridView() {
        ArrayList<GridListItem> gridListItems=new ArrayList<GridListItem>( 3);
        gridListItems.add(new GridListItem("错词本",GridListCallBack.STATE_WRONG_COLLECTION,R.drawable.wrongcollectionnormal,R.drawable.wrongcollectionselected));
        gridListItems.add(new GridListItem("生词本",GridListCallBack.STATE_DIFF_COLLECTION,R.drawable.diffcollectionnormal,R.drawable.diffcollectionselected));
        gridListItems.add(new GridListItem("学习计划",GridListCallBack.STATE_LEARNPLANS,R.drawable.learnplannormal,R.drawable.learnplanselected));
        gridListItems.add(new GridListItem("学习设置",GridListCallBack.STATE_LEARNOPTIONS,R.drawable.optionsnormal,R.drawable.optionsselected));
        gridListItems.add(new GridListItem("学习数据",GridListCallBack.STATE_LEARN_DATA,R.drawable.learndatanormal,R.drawable.learndataselected));
        gridListItems.add(new GridListItem("个人设置",GridListCallBack.STATE_PREFERENCE,R.drawable.personalinformationnormal,R.drawable.personalinformationselected));
        //gridListItems.add(new GridListItem("隐私政策",R.drawable.wrongcollectionnormal,R.drawable.wrongcollectionselected));
        gridListItems.add(new GridListItem("用户协议",GridListCallBack.STATE_USER_AGREEMENT,R.drawable.useragreementnormal,R.drawable.useragreementselected));
        gridListItems.add(new GridListItem("关于我们",GridListCallBack.STATE_ABOUT_US,R.drawable.aboutusnormal,R.drawable.aboutusselected));
        gridListItems.add(new GridListItem("给个好评",GridListCallBack.STATE_FAVORATE,R.drawable.favoratenormal,R.drawable.favorateselected));
        GvJJAdapter gvJJAdapter =new GvJJAdapter(getContext(),gridListItems,new GridListCallBack(getContext()));
        utilsGridView.setAdapter(gvJJAdapter);

        //TODO：每个item的点击事件

    }


    private void initprofilePhotoView() {
        if(User.getInstance(getContext()).isLogged()){
            nameView.setText(User.getInstance(getContext()).getNickName());
            profilePhotoView.setImageBitmap(User.getInstance(getContext()).getHeadImage());
        }
        else{
            nameView.setText("点击头像登录");
        }

        profilePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getInstance(getContext()).isLogged()){
                    //已经登录了，选头像
                    //TODO:选择头像后的持久化
                    PictureSelector
                            .create(PersonalFragment.this, PictureSelector.SELECT_REQUEST_CODE)
                            .selectPicture(false);
                }else{
                    //TODO:打开登录界面
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    profilePhotoView.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                    User.getInstance(getContext()).setHeadImage(pictureBean.getPath());
                } else {
                    profilePhotoView.setImageURI(pictureBean.getUri());
                    User.getInstance(getContext()).setHeadImage(pictureBean.getUri().getPath());
                }

                //使用 Glide 加载图片
                /*Glide.with(this)
                        .load(pictureBean.isCut() ? pictureBean.getPath() : pictureBean.getUri())
                        .apply(RequestOptions.centerCropTransform()).into(mIvImage);*/
            }
        }
    }
}


