package project1.attributes;

public class Size implements Attribute<Size> {

	private SizeType type;
	private int pct;

	@SuppressWarnings("unused")
	private Size() {
	};

	public Size(String name) {
		type = SizeType.find(name);
		setPct(type.pct);
	}

	public String toString() {
		return "size:" + type.name() + "-" + pct;
	}

	public int getPct() {
		return pct;
	}

	public void setPct(int pct) {
		this.pct = pct;
	}

	public static boolean canLoad(String name) {
		return name.equals("size");
	}

	@Override
	public int matchTo(Size o) {
		int diff = getPct() - o.getPct();
		if (diff == 0) {
			return 100;
		} else {
			return 50;
		}
	}

}
