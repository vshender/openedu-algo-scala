# Куча ли?

Структуру данных "куча", или, более конкретно, "неубывающая пирамида", можно реализовать на основе массива.

Для этого должно выполнятся основное свойство неубывающей пирамиды, которое заключается в том, что для каждого ![1 \leqslant i \leqslant n](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20n) выполняются условия:

- если ![2i \leqslant n](https://latex.codecogs.com/svg.latex?2i%20\leqslant%20n), то ![a[i] \leqslant a[2i]](https://latex.codecogs.com/svg.latex?a[i]%20\leqslant%20a[2i]);
- если ![2i + 1 \leqslant n](https://latex.codecogs.com/svg.latex?2i+1%20\leqslant%20n), то ![a[i] \leqslant a[2i + 1]](https://latex.codecogs.com/svg.latex?a[i]%20\leqslant%20a[2i+1]).

Дан массив целых чисел.  Определите, является ли он неубывающей пирамидой.

### Формат входного файла

Первая строка входного файла содержит целое число ![n](https://latex.codecogs.com/svg.latex?n) (![1 \leqslant n \leqslant 10^6](https://latex.codecogs.com/svg.latex?1%20\leqslant%20n%20\leqslant%2010^6)).  Вторая строка содержит ![n](https://latex.codecogs.com/svg.latex?n) целых чисел, по модулю не превосходящих ![2 \cdot 10^9](https://latex.codecogs.com/svg.latex?2%20\cdot%2010^9).

### Формат выходного файла

Выведите "YES", если массив является неубывающей пирамидой, и "NO" в противном случае.

### Примеры

`input.txt`
```
5
1 0 1 2 0
```

`output.txt`
```
NO
```

`input.txt`
```
5
1 3 2 5 4
```

`output.txt`
```
YES
```

### Решение

[IsHeap.scala](IsHeap.scala)
