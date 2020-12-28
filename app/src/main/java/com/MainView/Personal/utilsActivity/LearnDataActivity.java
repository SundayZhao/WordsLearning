package com.MainView.Personal.utilsActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.R;
import com.Unit.User;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.renderer.AxesRenderer;
import lecho.lib.hellocharts.view.LineChartView;

public class LearnDataActivity  extends Activity {
    private LineChartView lineChartView = null;
    private TextView sumLearnWord=null;
    private TextView lastUpdate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learndata);
        //开始初始化界面
        initViews();
    }

    private void initViews() {
        //实例化控件
        lineChartView = (LineChartView) findViewById(R.id.chart_leardata);
        sumLearnWord=(TextView)findViewById(R.id.leardata_learnwordnumber);
        lastUpdate=(TextView)findViewById(R.id.leardata_lastupdate);

        //初始化折线图
        initChartItems();
    }

    private void initChartItems() {
        //TODO:更新一下标题
        sumLearnWord.setText("我学过的单词总数:"+ String.valueOf(User.getInstance(getApplicationContext()).getLearnPlan().getHasLearned()));
        ArrayList<String> clockInList=User.getInstance(getApplicationContext()).getLearnPlan().getClockInDate();
        ArrayList<String> studyLog=User.getInstance(getApplicationContext()).getLearnPlan().getStudyLog();
        lastUpdate.setText("上次更新日期："+clockInList.get(clockInList.size()-1));
        studyLog.subList(Math.max(studyLog.size() - 10, 0),studyLog.size());
        //TODO：获取过去10天的学习单词记录，然后在折线图上面画出来
        //坐标点
        ArrayList<PointValue> mPointValues = new ArrayList<PointValue>();
        ArrayList<PointValue> mPointValues_allword = new ArrayList<PointValue>();
        //x轴
        ArrayList<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

        //插入点
        int step=0;
        for(String logs:studyLog){
            //logs =  data，allwords correctWords
            String []log=logs.split(",");
            mAxisXValues.add(new AxisValue(step).setLabel(log[0]));
            mPointValues.add(new PointValue(step, Integer.parseInt(log[1])));
            mPointValues_allword.add(new PointValue(step, Integer.parseInt(log[2])));
            step++;
        }

        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）


        Line line_allword = new Line(mPointValues_allword).setColor(Color.parseColor("#4B0082"));
        line_allword.setShape(ValueShape.SQUARE);
        line_allword.setCubic(true);
        line_allword.setFilled(false);
        line_allword.setHasLabels(true);
        line_allword.setHasLines(true);
        line_allword.setHasPoints(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(line_allword);
        LineChartData data = new LineChartData();
        data.setLines(lines);


        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setMaxLabelChars(6);
        List<AxisValue> values = new ArrayList<>();
        values.add(new AxisValue(20));
        values.add(new AxisValue(30));
        values.add(new AxisValue(40));
        values.add(new AxisValue(50));
        values.add(new AxisValue(60));
        axisY.setValues(values);
        data.setAxisYLeft(axisY);  //Y轴设置在左边

        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChartView.setInteractive(false);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setMaxZoom((float) 1);//最大方法比例
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.setLineChartData(data);
        lineChartView.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.top = 60;
        v.bottom= 20;
        lineChartView.setMaximumViewport(v);
        lineChartView.setCurrentViewport(v);
    }

}
