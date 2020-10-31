# Число инверсий

*Инверсией* в последовательности чисел ![A](https://latex.codecogs.com/svg.latex?A) называется такая ситуация, когда ![i < j](https://latex.codecogs.com/svg.latex?i<j), а ![A_i > A_j](https://latex.codecogs.com/svg.latex?A_i>A_j).

Дан массив целых чисел.  Ваша задача -- подсчитать число инверсий в нем.

Подсказка: чтобы сделать это быстрее, можно воспользоваться модификацией сортировки слиянием.

### Формат входного файла

В первой строке входного файла содержится число ![n](https://latex.codecogs.com/svg.latex?n) (![1 \leqslant n \leqslant 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20n%20\leqslant%2010^5)) -- число элементов в массиве.  Во второй строке находятся ![n](https://latex.codecogs.com/svg.latex?n) целых чисел, по модулю не превосходящих ![10^9](https://latex.codecogs.com/svg.latex?10^9).

### Формат выходного файла

В выходной файл надо вывести число инверсий в массиве.

### Пример

`input.txt`
```
10
1 8 2 1 4 7 3 2 3 6
```

`output.txt`
```
17
```

### Решение

[InversionCount.scala](InversionCount.scala)
