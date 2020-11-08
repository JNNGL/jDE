package com.jnngl.jde.system.applications.components;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jnngl.jde.Desktop;
import com.jnngl.jde.system.appearance.jCursor;

public class jSample extends jWindow{

	public jSample() {
		super(400,400,"Very-Very Long label",100,10,"System::jSample::578356", false);
	}
	
	jButton button = new jButton(this);
	jIconButton ibutton = new jIconButton(this);
	
	public void render(Graphics2D g, int yoffset) {
		g.addRenderingHints(Map.of(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		
		jLabel label = new jLabel(this);
		label.setText("Hello, world!\nThis is a test");
		
		button.setAction(() -> {
			new jSample();
		});
		button.setPosition(200, 200);
		button.setText("Press me!");
		button.render(g, yoffset);
		label.render(g, yoffset);
		
		ibutton.setPosition(300, 300);
		ibutton.setAction(() -> {
			System.err.println("Icon pressed");
		});
		ibutton.setSize(60, 60);
		ibutton.render(g, yoffset);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if(Desktop.getjParent()==this)
			jCursor.changeCursor(Cursor.MOVE_CURSOR);
	}

	List<Boolean> results = new ArrayList<Boolean>();
	@Override
	public void mouseMoved(MouseEvent e) {
		results.clear();
		results.add(button.mouseMoved(e));
		results.add(ibutton.mouseMoved(e));
		if(!results.contains(true))
			jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		jCursor.changeCursor(Cursor.DEFAULT_CURSOR);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		button.mouseClicked(e);
		ibutton.mouseClicked(e);
	}


}
