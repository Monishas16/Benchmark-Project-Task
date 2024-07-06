package objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginObj {
	
	@FindBy (xpath="//input[@placeholder='Enter Your Email']")
	public static WebElement username;

	@FindBy (xpath="//input[@id='password']")
	public static WebElement password;
	
	@FindBy(xpath="//button[@type='submit']")
	public static WebElement sigin;
	
	@FindBy(xpath="//button[@aria-label='Open menu']")
	public static WebElement menubtn;

	@FindBy(xpath="//span[text()='Logout']")
	public static WebElement LogoutOpt;

	@FindBy(xpath="//span[text()='It Admin Nims Dxb']")
	public static WebElement AdminHeadTxt;

	@FindBy(xpath="//span[@class='ng-binding']")
	public static WebElement UserHead;
	
	@FindBy(xpath="//i[text()=' Welcome ']//following::span[@class='ng-binding'][1]")
	public static WebElement DashHead;
	
}
