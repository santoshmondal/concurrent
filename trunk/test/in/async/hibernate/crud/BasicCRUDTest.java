package in.async.hibernate.crud;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BasicCRUDTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testInsert() {
		BasicCRUDBean obj = new BasicCRUDBean();
		BasicPK pk = new BasicPK(3, "java");
		obj.setId(pk);
		obj.setEmail("java@javaaaa.com");
		obj.setCtime(new Date());

		boolean result = BasicCRUD.insert(obj);
		Assert.assertTrue("TRUE::", result);

		BasicCRUDBean findObj = BasicCRUD.find(pk);
		Assert.assertEquals("Operation::", obj.getEmail(), findObj.getEmail());
	}

	@Test
	@Ignore
	public void testUpdate() {
	}

}
