package tudai.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CuentaPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Duration time;
    private WebElement btnCrearCuenta, btnModificarCuenta, btnEliminarCuenta, inputFiltro;

    public CuentaPage(WebDriver driver) {
        this.driver = driver;
        time = Duration.ofMillis(9000);
        wait = new WebDriverWait(driver, time);
    }

    /**
     * Abre el formulario para crear una nueva cuenta luego de hacer click en el botón "Nueva cuenta"
     */
    public void abrirDialogNuevaCuenta() {
        By elementLocator = By.id("btn-nueva-cuenta");
        btnCrearCuenta = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        btnCrearCuenta.click();
    }

    /**
     * Abre el formulario para editar una cuenta existente, con sus datos precargados,
     * luego de hacer click en uno de los botones de editar cuenta
     * @param numeroCuenta, número de la cuenta que se desea editar
     */
    public void abrirDialogModificarCuenta(String numeroCuenta){
        By btnLocator = By.id("btn-editar-cuenta-" + numeroCuenta);
        btnModificarCuenta = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
        btnModificarCuenta.click();
    }

    /**
     * Elimina una cuenta existente
     * @param numeroCuenta, número de cuenta que se desea eliminar
     */
    public void eliminarCuenta(String numeroCuenta){
        By btnLocator = By.id("btn-eliminar-cuenta-" + numeroCuenta);
        btnEliminarCuenta = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
        btnEliminarCuenta.click();
    }

    /**
     * Completa el formulario de crear cuenta de manera automatizada con los datos recibidos por parámetro
     * @param entidad, nombre de la entidad bancaria
     * @param numero, numero de cuenta
     * @param saldo, saldo de la cuenta
     * @param titular, id del titular de la cuenta
     */
    public void completarFormulario(String entidad, String numero, String saldo, String titular) {
        driver.findElement(By.id("entidad_bancaria")).sendKeys(entidad);
        driver.findElement(By.id("numero_cuenta")).sendKeys(numero);
        driver.findElement(By.id("saldo")).sendKeys(saldo);
        driver.findElement(By.id("id_titular")).sendKeys(titular);
    }

    /**
     * Borra los datos de entidad bancaria y saldo y los completa con los nuevos valores recibidos por parámetro
     * @param entidad, nombre de la entidad bancaria
     * @param saldo, saldo de la cuenta
     */
    public void completarFormularioModificar(String entidad, String saldo) {
        driver.findElement(By.id("entidad_bancaria")).clear();
        driver.findElement(By.id("entidad_bancaria")).sendKeys(entidad);
        driver.findElement(By.id("saldo")).clear();
        driver.findElement(By.id("saldo")).sendKeys(saldo);
    }

    /**
     * Confirma la creación / modificación de la cuenta, haciendo click sobre el botón "confirmar"
     */
    public void confirmarCreacionCuenta() {
        driver.findElement(By.id("btn-confirmar-cuenta")).click();
    }

    /**
     * Verifica si existe una cuenta con el número de cuenta recibido por parámetro mediante el xpath
     * @param numeroCuenta, numero de cuenta
     * @return boolean
     */
    public boolean existeCuenta(String numeroCuenta) {
        try {
            By cuentaLocator = By.xpath("//td[text()='" + numeroCuenta + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(cuentaLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean esperarCuentaDesaparecida(String numeroCuenta, int timeoutSegundos) {
        try {
            By cuentaLocator = By.xpath("//td[text()='" + numeroCuenta + "']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSegundos));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(cuentaLocator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void filtarCuenta(String numeroCuenta) {
        By inputFiltroLocator = By.id("input-filtro");
        inputFiltro = wait.until(ExpectedConditions.elementToBeClickable(inputFiltroLocator));
        inputFiltro.click();
        inputFiltro.sendKeys(numeroCuenta);
    }
}
