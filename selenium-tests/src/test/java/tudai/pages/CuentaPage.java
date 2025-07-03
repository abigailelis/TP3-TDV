package tudai.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CuentaPage {
    private WebDriver driver;

    public CuentaPage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrirDialogNuevaCuenta() {
        Duration time= Duration.ofMillis(9000);
        WebDriverWait wait = new WebDriverWait(driver, time);
        By elementLocator = By.id("btn-nueva-cuenta");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        element.click();
    }

    public void completarFormulario(String entidad, String numero, String saldo, String titular) {
        driver.findElement(By.id("entidad_bancaria")).sendKeys(entidad);
        driver.findElement(By.id("numero_cuenta")).sendKeys(numero);
        driver.findElement(By.id("saldo")).sendKeys(saldo);
        driver.findElement(By.id("id_titular")).sendKeys(titular);
    }

    public void confirmarCreacionCuenta() {
        driver.findElement(By.id("btn-confirmar-cuenta")).click();
    }

    public void limpiarFiltros() {
        driver.findElement(By.id("btn-limpiar-filtros-cuenta")).click();
    }

    public boolean existeCuenta(String numeroCuenta) {
        try {
            // Busca por XPath una celda de la tabla que contenga exactamente el número de cuenta
            driver.findElement(By.xpath("//td[text()='" + numeroCuenta + "']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
