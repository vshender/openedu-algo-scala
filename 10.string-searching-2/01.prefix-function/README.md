# Префикс-функция

Постройте префикс-функцию для всех непустых префиксов заданной строки ![s](https://latex.codecogs.com/svg.latex?s).

### Формат входного файла

Первая строка входного файла содержит ![s](https://latex.codecogs.com/svg.latex?s) (![1 \leqslant |s| \leqslant 10^6](https://latex.codecogs.com/svg.latex?1%20\leqslant%20|s|%20\leqslant%2010^6)).  Строка состоит из букв латинского алфавита.

### Формат выходного файла

Выведите значения префикс-функции для всех префиксов строки ![s](https://latex.codecogs.com/svg.latex?s) длиной ![1, 2, \ldots, |s|](https://latex.codecogs.com/svg.latex?1,2,\ldots,|s|) в указанном порядке.

### Примеры

`input.txt`
```
aaaAAA
```

`output.txt`
```
0 1 2 0 0 0
```

`input.txt`
```
abacaba
```

`output.txt`
```
0 0 1 0 1 2 3
```

### Решение

[PrefixFunction.scala](PrefixFunction.scala)
