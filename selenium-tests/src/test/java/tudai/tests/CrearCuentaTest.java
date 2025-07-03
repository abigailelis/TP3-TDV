package tudai.tests;

import tudai.pages.CuentaPage;
import tudai.utils.DriverFactory;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrearCuentaTest {

    private WebDriver driver;
    private CuentaPage cuentaPage;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("http://localhost:3000"); // URL del frontend React
        cuentaPage = new CuentaPage(driver);
    }

    @Test
    @Order(1)
    public void testCrearCuentaExito() {
        cuentaPage.abrirDialogNuevaCuenta();
        cuentaPage.completarFormulario("Banco Rio", "123456789", "35000", "987");
        cuentaPage.confirmarCreacionCuenta();

        Assertions.assertTrue(
                cuentaPage.existeCuenta("123456789"),
                "La cuenta debería aparecer en la tabla"
        );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
