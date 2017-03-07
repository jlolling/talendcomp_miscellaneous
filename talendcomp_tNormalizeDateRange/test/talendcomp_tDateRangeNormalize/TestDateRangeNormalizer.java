package talendcomp_tDateRangeNormalize;

import java.text.SimpleDateFormat;

import de.cimt.talendcomp.daterangenormalize.DateRangeNormalizer;

public class TestDateRangeNormalizer {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws Exception {
		DateRangeNormalizer n = new DateRangeNormalizer();
		n.setDateRange(sdf.parse("2015-12-25"), sdf.parse("2016-01-07"));
		while (n.next()) {
			System.out.println(n.getNextDate());
		}
	}

}
