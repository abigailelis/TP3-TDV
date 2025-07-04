package tudai;

import tudai.tests.CuentaTest;

public class Main {
    public static void main(String[] args) {
        CuentaTest cuentaTest = new CuentaTest();
        cuentaTest.setUp("EDGE");
        cuentaTest.testCrearCuentaExito();
    }
}
