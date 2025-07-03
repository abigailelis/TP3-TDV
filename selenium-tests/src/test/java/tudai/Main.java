package tudai;

import org.openqa.selenium.WebDriver;
import tudai.pages.CuentaPage;
import tudai.utils.DriverFactory;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            driver.get("http://localhost:3000");
            CuentaPage cuentaPage = new CuentaPage(driver);

            cuentaPage.abrirDialogNuevaCuenta();
            cuentaPage.completarFormulario("Banco Maíz", "987654321", "15000", "555");
            cuentaPage.confirmarCreacionCuenta();

            if (cuentaPage.existeCuenta("987654321")) {
                System.out.println("✅ Cuenta creada con éxito.");
            } else {
                System.out.println("❌ La cuenta no se visualiza en la tabla.");
            }
        } finally {

            //driver.quit();
        }
    }
}
