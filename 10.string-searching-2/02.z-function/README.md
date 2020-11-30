# Z-функция

Постройте Z-функцию для заданной строки ![s](https://latex.codecogs.com/svg.latex?s).

### Формат входного файла

Первая строка входного файла содержит ![s](https://latex.codecogs.com/svg.latex?s) (![2 \leqslant |s| \leqslant 10^6](https://latex.codecogs.com/svg.latex?2%20\leqslant%20|s|%20\leqslant%2010^6)).  Строка состоит из букв латинского алфавита.

### Формат выходного файла

Выведите значения Z-функции для всех индексов ![2, 3, \ldots, |s|](https://latex.codecogs.com/svg.latex?2,3,\ldots,|s|) строки ![s](https://latex.codecogs.com/svg.latex?s) в указанном порядке.

### Примеры

`input.txt`
```
aaaAAA
```

`output.txt`
```
2 1 0 0 0
```

`input.txt`
```
abacaba
```

`output.txt`
```
0 1 0 3 0 1
```

### Решение

[ZFunction.scala](ZFunction.scala)
