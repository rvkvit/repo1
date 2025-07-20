package pages;

import org.openqa.selenium.support.PageFactory;

import supportlibraries.*;


/**
 * MasterPage Abstract class
 * @author LT
 */
abstract class MasterPage extends ReusableLibrary
{	
	
	/**
	 * Constructor to initialize the functional library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	protected MasterPage(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
		
		PageFactory.initElements(driver, this);
	}
	
	
}