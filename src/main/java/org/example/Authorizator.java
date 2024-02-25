package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Authorizator{
    private WebDriver driver;
    private String login;
    private String password;
    public Authorizator(WebDriver driver, String login, String password){
        this.driver = driver;
        this.login = login;
        this.password = password;
    }
    public void authorization(){
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='pass']"));

        emailInput.click();
        emailInput.sendKeys(login);

        passwordInput.click();
        passwordInput.sendKeys(password, Keys.ENTER);
    }
}
