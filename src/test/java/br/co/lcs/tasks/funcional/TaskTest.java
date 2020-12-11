package br.co.lcs.tasks.funcional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TaskTest {

	public WebDriver acessarAPP() throws MalformedURLException {
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://169.254.210.8:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.0.102:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarerfaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAPP();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String msg = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", msg);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarerfaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAPP();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String msg = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", msg);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarerfaSemData() throws MalformedURLException {
		WebDriver driver = acessarAPP();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("saveButton")).click();
			String msg = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", msg);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void nadoDeveSalvarTarerfaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAPP();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			driver.findElement(By.id("saveButton")).click();
			String msg = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", msg);
		} finally {
			driver.quit();
		}
	}
}
