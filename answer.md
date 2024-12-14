# 1 Вопрос
1. Наследование - создание новых классов, на основе существующих. С возможностью: расширения функциональности, использование кода родительного класса повторно.
Конструкторы вызываются от родительского класса к дочернему.
Множественного наследования нет, но можно реализовать множество интерфейсов.
Для наследования применяется ключевое слово extends
Ключевое слово final, запрещает дальнейшее наследование.
2. Пример:
```java
class Animal {
    void eat() {
        System.out.println("Animal eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}
```
2. Инкапсуляция - скрывает внутренние данные класса и реализации, обспечивает безопасный доступ к ним.
Имеет 3 модификатора доступа:
   1. Public - поля и методы могут использоваться всеми
   2. Protected - поля и методы могут использоваться наследниками класса, а так же классами в том же пакете
   3. Private - поля и методы могут использоваться только внутри класса.
   4. Default - package private для классов, и public для интерфейса.\
4 - используется в случае, если модификатор доступа не указан.
3. Полиморфизм - это способность разных классов, реализовывать один и тот же метод по разному.
К полиморфизму в Java относится, переопределение методов или перегрузка методов.
Переопределение методов используется с аннотацией @Override. Перезгрузка же методов, одно название функции, разные входные значения.
Пример:
```java
class Animal {
    void eat() {
        System.out.println("Animal eating");
    }
}
class Dog extends Animal {
    
    @Override
    void eat() {
        System.out.println("Dog eating");
    }
}

//перегрузка
class Animal {
    void eat() {
        System.out.println("Animal eating");
    }
    
    void eat(String food) {
        System.out.printf("Animal eating %s%n", food);
    }
}
```
Так же чтобы, к примеру, вызвать конструктор родителя, можно использовать ключевое слово **super**
```java
class Animal {
    protected final String food;
    Animal(String food) {
        this.food = food;
    }
    
    public String getFood() {
        return food;
    }
    
}
class Dog extends Animal {
    Dog(String food) {
        super(food);
    }
}
```
# Вопрос 2
Память в Java условно можно разделить на:
1. Heap
2. Non-heap
3. Stack

Heap - это основной сегмент памяти , где хранятся объекты. Имеет 2 подсегмента Old Generation, New generation. New generation на Eden и Survivor.

Non-heap разделяется: Permanent Generation и Code cache. Permanent Generation - содержит набор метаинформации о классах, например, в Reflection.

Stack - это память, своего рода "оперативная память метода", работающая по схеме LIFO. Когда вызывается метод, то для него в памяти стека создаётся новый блок, называемый фрэймом
Он содержит
Примитивные типы, ссылки на другие объекты.