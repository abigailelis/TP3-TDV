package tudai.tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tudai.pages.CuentaPage;
import tudai.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import java.util.Random;
import org.testng.annotations.*;
import org.testng.Assert;
import tudai.utils.DriverFactory.Navegador;

public class CuentaTest {

    static WebDriver driver;
    static CuentaPage cuentaPage;
    static String numeroCuenta;
    static String idTitular;

    @Parameters("navegador")
    @BeforeClass
    public void setUp(@Optional("EDGE") String navegadorParam) {
        Navegador navegador = Navegador.valueOf(navegadorParam.toUpperCase());
        driver = DriverFactory.getDriver(navegador);
        driver.get("http://localhost:3000");
        cuentaPage = new CuentaPage(driver);
        numeroCuenta = "";
        idTitular = "";
    }

    @Test (description = "Crear cuenta exitosamente", priority = 1, invocationCount = 3)
    public void testCrearCuentaExito() {
        numeroCuenta = generarRandom();
        idTitular = generarRandom();
        cuentaPage.abrirDialogNuevaCuenta();
        cuentaPage.completarFormulario("Mercado Pago", numeroCuenta, "11000", idTitular);
        cuentaPage.confirmarCreacionCuenta();

        Assert.assertTrue(cuentaPage.existeCuenta(numeroCuenta), "La cuenta debería aparecer en la tabla");
    }

    @Test (description = "Modificar cuenta exitosamente", priority = 2)
    public void testModificarCuentaExito() {
        cuentaPage.abrirDialogModificarCuenta(numeroCuenta);
        cuentaPage.completarFormularioModificar("Brubank", "18000");
        cuentaPage.confirmarCreacionCuenta();

        Assert.assertTrue(cuentaPage.existeCuenta(numeroCuenta), "La cuenta debería aparecer en la tabla");
    }

    @Test (description = "Borrar cuenta exitosamente", priority = 3)
    public void testBorrarCuentaExito() {
        cuentaPage.eliminarCuenta(numeroCuenta);

        Assert.assertFalse(cuentaPage.existeCuenta(numeroCuenta), "La cuenta no deberia aparecer en la tabla");
    }


    private String generarRandom() {
        return String.valueOf(10000 + new Random().nextInt(90000));
    }
}

