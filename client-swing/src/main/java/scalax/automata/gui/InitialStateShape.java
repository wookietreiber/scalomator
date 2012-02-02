package main.java.scalax.automata.gui;

import java.awt.BasicStroke;
import java.awt.Rectangle;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxEllipseShape;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

public class InitialStateShape extends mxEllipseShape {

	@Override
	public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {
		// draw an ellipse
		super.paintShape(canvas, state);
		
		// calculate some offset for the initial state indicator
		int offset = (int) Math.round((mxUtils.getFloat(state.getStyle(),
				mxConstants.STYLE_STROKEWIDTH, 1) + 5)
				* canvas.getScale());
		
		// calculate array coordinates
		Rectangle rect = state.getRectangle();
		int xMin = rect.x - 4*offset;
		int xArrowMin = rect.x - offset;
		int xArrowMax = rect.x - 2*offset;
		int xMax = rect.x;
		int y = rect.y + rect.height/2;
		int yMin = y - offset;
		int yMax = y + offset;
		
		// calculate larger bounding box
		mxRectangle bound = state.getBoundingBox();
		bound.setX(bound.getX() - 4*offset);
		bound.setWidth(bound.getWidth() + 4*offset);
		state.setBoundingBox(bound);
		
		// draw an arrow
		int[] xPoints = {xMax, xArrowMax, xArrowMin, xArrowMax};
		int[] yPoints = {y, yMax, y, yMin};
		canvas.getGraphics().setStroke(new BasicStroke(2f));
		canvas.getGraphics().drawLine(xMin, y, xArrowMin, y);
		canvas.getGraphics().fillPolygon(xPoints, yPoints, 4);
	}
}
