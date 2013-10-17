package net.thewaffleshop.passwd.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 *
 * @author Robert Hollencamp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext-jdbc-test.xml", "/spring/applicationContext-dao.xml", "/spring/applicationContext-api.xml"})
abstract public class TestBase
{
}
