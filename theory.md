question1:
Многопоточность. Интерфейсы Callable, Runnable, Future. Класс Thread.

Потоки являются способом выполнения процесса. Главный поток выполняет метод main и завершается. При выполнении процесса могут создаваться дочерние потоки. В Java работа программы завершается тогда, когда завершается работа последнего её потока.

Поток представлен в Java классом Thread с методом run(), в котором описана логика работы потока. Поток запускается с помощью метода start().
Для создания своей реализации метода run() необходимо создать класс, расширяющий класс Thread, и переопределить в нём метод run()
```java
public class MyThread extends Thread {
    public void run() {
        long sum = 0;
        for (int i = 0; i < 1000; ++i) {
            sum += i;
        }
        System.out.println(sum);
    }
}

MyThread t = new MyThread();
t.start();
```
или сделать всё то же самое, имплементировав интерфейс.

интерфейсы Callabel и Runnable различаются по возвращаемым данным. Runnable не возвращает ничего и не выбрасывает исключения, Callable - возвращает результат и выбрасывает исключение.

```java
Runnable r = new MyRunnable() { () ->
    System.out.println(“Hello!”);
}

Thread t = new Thread(r);
t.start();
```

Future используется для получения результата из Callable.
```java
Callable<String> callThreadName = () -> {
                Thread.sleep(Duration.ofSeconds(10).toMillis());
                return Thread.currentThread().getName();
            };

            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<String> futureThreadName = executorService.submit(callThreadName);
```
В этом коде создаётся анонимный класс, имплементирующий Callable, с единственным методом, который спит 10 секунд и возвращает название потока.
объект анонимного класса посылается в пул потоков ExecutorService, после чего Future будет ожидать получения из него результата в виде String.


question2:
Неконтроллируемые ресурсы в Java. Интерфейс Autocloseable. try-with-resources. Метод finalize.


интерфейс AutoCloseable позволяет генерировать исключения любого типа с помощью метода close(), в котором описывается логика освобождения ресурсов.


try-with-resources 
синтаксический сазар, который "самосотоятельно" закрывает ресурсы, добавляя блок finally и метод close() для ресурсов, объявленных в круглых скобках в try.
```java
try (Object object = new Object())
{
     //magic!
} catch (//something) {
    //no magic
}
```
для этого кода компилятор сам добавит finally, где объект object будет закрыт (close())
объект object должен реализовывать инетрфейс AutoCloseable


метод finalize() не рекомендован к использованию руками программиста. В нём описываются действия, которые должны быть совершены перед тем, как над соовтетствующим объектом отработает GC 