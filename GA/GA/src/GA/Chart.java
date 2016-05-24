package GA;

import java.awt.Font;
import java.awt.Rectangle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

class Chart {
	DefaultCategoryDataset dataset;
	JFreeChart chart;
	
	Chart(){
		dataset = new DefaultCategoryDataset();
		chart = ChartFactory.createLineChart("基因演算法music", // 主標題名稱
				"迭代次數",// X軸
				"適應值",// Y軸
				dataset, // 圖標顯示
				PlotOrientation.VERTICAL, // 圖像顯示方式
				true,// 是否顯示子標題
				true,// 是否生成提示的標籤
				false); //是否生成URL標籤

		chart.getTitle().setFont(new Font("標楷體", Font.BOLD, 18));
		chart.getLegend().setItemFont(new Font("標楷體", Font.BOLD, 15));
		
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();	
		// X軸對象
		CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
		// Y軸對象
		NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
		
		
		categoryAxis.setTickLabelFont(new Font("標楷體", Font.BOLD, 15));
		categoryAxis.setLabelFont(new Font("標楷體", Font.BOLD, 10));
	
		numberAxis.setTickLabelFont(new Font("標楷體", Font.BOLD, 15));		
		numberAxis.setLabelFont(new Font("標楷體", Font.BOLD, 15));
		
		// 處理Y軸上的刻度
		numberAxis.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(500);
		numberAxis.setTickUnit(unit);
		
		// 召喚繪圖區
		LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
		
		// 圖形上顯示文字
		lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		lineAndShapeRenderer.setBaseItemLabelFont(new Font("標楷體", Font.BOLD, 15));
		// 轉折點
		Rectangle shape = new Rectangle(10, 10);
		lineAndShapeRenderer.setSeriesShape(0, shape);
		lineAndShapeRenderer.setSeriesShapesVisible(0, true);

		// 使用ChartFrame對象顯示圖像
		ChartFrame frame = new ChartFrame("基因演算法", chart);
		frame.setVisible(true);
		frame.pack();
	}

	//放資料
	void putData(int[] fit){
		
		for(int i=0;i<fit.length;i++){
			String a =i+"";
			dataset.addValue(fit[i],"母體",a);
		}	
	}
}