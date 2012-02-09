/* **************************************************************************
 *                                                                          *
 *  Copyright (C)  2011-2012  Peter Kossek, Nils Foken, Christian Krause    *
 *                                                                          *
 *  Peter Kossek     <peter.kossek@it2009.ba-leipzig.de>                    *
 *  Nils Foken       <nils.foken@it2009.ba-leipzig.de>                      *
 *  Christian Krause <christian.krause@it2009.ba-leipzig.de>                *
 *                                                                          *
 ****************************************************************************
 *                                                                          *
 *  This file is part of 'scalomator'.                                      *
 *                                                                          *
 *  This project is free software: you can redistribute it and/or modify    *
 *  it under the terms of the GNU General Public License as published by    *
 *  the Free Software Foundation, either version 3 of the License, or       *
 *  any later version.                                                      *
 *                                                                          *
 *  This project is distributed in the hope that it will be useful,         *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 *  GNU General Public License for more details.                            *
 *                                                                          *
 *  You should have received a copy of the GNU General Public License       *
 *  along with this project. If not, see <http://www.gnu.org/licenses/>.    *
 *                                                                          *
 ****************************************************************************/

package scalax.automata.gui;

import java.awt.BasicStroke;
import java.awt.Rectangle;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxDoubleEllipseShape;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

public class MultiStateShape extends mxDoubleEllipseShape {

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
