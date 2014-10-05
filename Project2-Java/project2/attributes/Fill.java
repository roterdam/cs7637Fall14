package project2.attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fill implements Attribute<Fill> {

	private boolean fillQuadrant1;
	private boolean fillQuadrant2;
	private boolean fillQuadrant3;
	private boolean fillQuadrant4;

	@SuppressWarnings("unused")
	private Fill() {
	}

	public Fill(String value) {
		List<String> list = new ArrayList<String>(Arrays.asList(value
				.split(",")));
		for (String val : list) {
			if (val.equals("no")) {
				setFillQuadrant1(false);
				setFillQuadrant2(false);
				setFillQuadrant3(false);
				setFillQuadrant4(false);
				return;
			} else if (value.equals("yes")) {
				setFillQuadrant1(true);
				setFillQuadrant2(true);
				setFillQuadrant3(true);
				setFillQuadrant4(true);
				return;
			} else if (value.startsWith("top") && value.endsWith("right")) {
				setFillQuadrant1(true);
			} else if (value.startsWith("top") && value.endsWith("left")) {
				setFillQuadrant2(true);
			} else if (value.startsWith("bottom") && value.endsWith("left")) {
				setFillQuadrant3(true);
			} else if (value.startsWith("top") && value.endsWith("right")) {
				setFillQuadrant4(true);
			}
		}
	}

	public String toString() {
		return "fill:" + (isFillQuadrant1() ? "x" : ".")
				+ (isFillQuadrant2() ? "x" : ".")
				+ (isFillQuadrant3() ? "x" : ".")
				+ (isFillQuadrant4() ? "x" : ".");
	}

	public boolean isFillQuadrant1() {
		return fillQuadrant1;
	}

	public void setFillQuadrant1(boolean fillQuadrant1) {
		this.fillQuadrant1 = fillQuadrant1;
	}

	public boolean isFillQuadrant2() {
		return fillQuadrant2;
	}

	public void setFillQuadrant2(boolean fillQuadrant2) {
		this.fillQuadrant2 = fillQuadrant2;
	}

	public boolean isFillQuadrant3() {
		return fillQuadrant3;
	}

	public void setFillQuadrant3(boolean fillQuadrant3) {
		this.fillQuadrant3 = fillQuadrant3;
	}

	public boolean isFillQuadrant4() {
		return fillQuadrant4;
	}

	public void setFillQuadrant4(boolean fillQuadrant4) {
		this.fillQuadrant4 = fillQuadrant4;
	}

	@Override
	public int matchTo(Fill o) {
		int match = 0;
		if (o.isFillQuadrant1() == this.isFillQuadrant1())
			match += 25;
		if (o.isFillQuadrant2() == this.isFillQuadrant2())
			match += 25;
		if (o.isFillQuadrant3() == this.isFillQuadrant3())
			match += 25;
		if (o.isFillQuadrant4() == this.isFillQuadrant4())
			match += 25;
		return match;
	}

	public static boolean canLoad(String name) {
		return name.equals("fill");
	}

	@Override
	public double getScore() {
		return ((fillQuadrant1 ? 1 : 0) + (fillQuadrant2 ? 1 : 0)
				+ (fillQuadrant3 ? 1 : 0) + (fillQuadrant4 ? 1 : 0)) / 4;
	}

	@Override
	public int getOrder() {
		return AttributeOrder.fill.ordinal();
	}

}
