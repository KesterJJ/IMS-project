import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.qa.ims.IMS;

public class IMSTest {

	
	IMS ims = new IMS();
	
	@Test
	public void testIMS() {
		assertTrue(ims instanceof IMS);
	}
}
