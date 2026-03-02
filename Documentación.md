Sistema de Gestión de Redes Inteligentes (Smart Grid)

Para este proyecto que se trata de un sistema un poco complejo ya que se necesita de datos en tiempo real, se escogieron las variantes más adecuadas teniendo en cuenta que inicialmente no se usará framework para demostrar manualmente las variantes

1. Patrón Singleton

El sistema requiere un gestor central que:

- Controle el registro de dispositivos
- Supervise el consumo total
- Coordine el monitoreo en tiempo real
- Mantenga coherencia global del sistema

Este componente no debe tener múltiples instancias, ya que generaría:

- Datos inconsistentes
- Duplicación de información
- Errores en el cálculo del consumo

✅ Variante seleccionada: Enum Singleton

Se seleccionó la variante Enum Singleton porque es la implementación más segura y robusta en Java.

1. Garantiza una única instancia en toda la aplicación.
2. Es thread-safe por defecto.
3. Es inmune a problemas de serialización.
4. Es inmune a ataques por reflexión.
5. Es más simple y menos propensa a errores.
6. Es recomendada oficialmente por Joshua Bloch (Effective Java).

❌ Razones por las que no se eligen las otras 3 variantes:

1. Eager Initialization: No es eficiente si el objeto es pesado y puede generar consumo innecesario de memoria

2. Lazy Initialization: Puede afectar el rendimiento en sistemas con múltiples accesos, como el SmartGrid

3.  Double-Checked Locking: Implementación más compleja y puede generar más errores


2. Patrón Factory Method

El sistema debe crear distintos tipos de dispositivos energéticos:

- Medidores inteligentes
- Paneles solares
- Turbinas eólicas

Si se instanciaran directamente con new, el sistema quedaría:

- Altamente acoplado
- Difícil de escalar
- Difícil de mantener

✅ Variante seleccionada: Factory Method con parámetros

Se seleccionó esta variante porque permite crear dispositivos dinámicamente según el tipo solicitado.

Aunque esta variante tiene la desventaja de que viola el OCP, no es algo grave en este caso ya que no se usa framework donde las clases se configuran externamente

1. Permite centralizar la creación de objetos.
2. Reduce el acoplamiento entre clases.
3. Facilita la escalabilidad (agregar nuevos dispositivos).
4. Es clara y fácil de entender.

❌ Razones por las que no se elige la otra variante:

- Mayor complejidad.
- Posibles errores en tiempo de ejecución.
- No es necesaria para el nivel actual del sistema.