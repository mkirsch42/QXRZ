package org.amityregion5.qxrz.common.control;

public enum NetworkInputMasks
{
	W(0), A(1), S(2), D(3), M1(4), R(5), SPACE(6), COMMA(7), PERIOD(8), E(9), Q(
			10), M2(11);

	private int maskIndex;

	private NetworkInputMasks(int mIndex)
	{
		maskIndex = mIndex;
	}

	public int getMaskIndex()
	{
		return maskIndex;
	}
}
