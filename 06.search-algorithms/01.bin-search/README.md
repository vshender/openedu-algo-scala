# Двоичный поиск

Дан массив из ![n](https://latex.codecogs.com/svg.latex?n) элементов, упорядоченный в порядке неубывания, и ![m](https://latex.codecogs.com/svg.latex?m) запросов: найти первое и последнее вхождение некоторого числа в массив.  Требуется ответить на эти запросы.

### Формат входного файла

В первой строке входного файла содержится одно число ![n](https://latex.codecogs.com/svg.latex?n) -- размер массива (![1 \leqslant n \leqslant 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20n%20\leqslant%2010^5)).  Во второй строке находятся ![n](https://latex.codecogs.com/svg.latex?n) чисел в порядке неубывания -- элементы массива.  В третьей строке находится число ![m](https://latex.codecogs.com/svg.latex?m) -- число запросов (![1 \leqslant m \leqslant 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20m%20\leqslant%2010^5)).  В следующей строке находятся ![m](https://latex.codecogs.com/svg.latex?m) чисел -- запросы.  Элементы массива и запросы являются целыми числами, неотрицательны и не превышают ![10^9](https://latex.codecogs.com/svg.latex?10^9).

### Формат выходного файла

Для каждого запроса выведите в отдельной строке номер (индекс) первого и последнего вхождения этого числа в массив.  Ecли числа в массиве нет, выведите два раза -1.

### Пример

`input.txt`
```
5
1 1 2 2 2
3
1 2 3
```

`output.txt`
```
1 2
3 5
-1 -1
```

### Решение

[BinSearch.scala](BinSearch.scala)
