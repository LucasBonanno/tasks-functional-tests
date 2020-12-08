package br.co.lcs.tasks.funcional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TaskTest {

	public WebDriver acessarAPP() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarerfaComSucesso() {
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
	public void naoDeveSalvarTarerfaSemDescricao() {
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
	public void naoDeveSalvarTarerfaSemData() {
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
	public void nadoDeveSalvarTarerfaComDataPassada() {
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
