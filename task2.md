Класс Object, его методы, правила переопределения методов, использование класса.

Класс  Object это базовый класс для всех классов, а также массивов

Обладает следующим набором методов:
Object clone() - создает копию клонируемого объекта
equals() - проверяет объекты на равенство
final Class<?> getClass() - получает класс
String toString() - преобраз

void finalize() - вызывается перед удалением неиспользуемого объекта

int hashCode() - возвращает хэш
final void notify() - возобновляет испольнение потока, ожидающего вызывающего объекта
final void notifyAll() - Возобновляет исполнение всех потоков, ожидающих вызывающий объект

final void wait() - ожидает другого потока исполнения
final void wait(long ms)
final void wait(long ms, int ns)

пример переопределения equals
class MyClass {
    @Override
    public boolean equals(Object obj) {
        // переопределение
    }
}