# Сортировка целых чисел

В этой задаче Вам нужно будет отсортировать много неотрицательных целых чисел.

Вам даны два массива, ![A](https://latex.codecogs.com/svg.latex?A) и ![B](https://latex.codecogs.com/svg.latex?B), содержащие соответственно ![n](https://latex.codecogs.com/svg.latex?n) и ![m](https://latex.codecogs.com/svg.latex?m) элементов.

Числа, которые нужно будет отсортировать, имеют вид ![A_i \cdot B_j](https://latex.codecogs.com/svg.latex?A_i%20\cdot%20B_j), где ![1 \leqslant i \leqslant n](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20n) и ![1 \leqslant j \leqslant m](https://latex.codecogs.com/svg.latex?1%20\leqslant%20j%20\leqslant%20m).  Иными словами, каждый элемент первого массива нужно умножить на каждый элемент второго массива.

Пусть из этих чисел получится отсортированная последовательность ![С](https://latex.codecogs.com/svg.latex?C) длиной ![n \cdot m](https://latex.codecogs.com/svg.latex?n%20\cdot%20m).  Выведите сумму каждого десятого элемента этой последовательности (то есть, ![C_1 + C_{11} + C_{21} + \ldots](https://latex.codecogs.com/svg.latex?C_1+C_{11}+C_{21}+\ldots)).

### Формат входного файла

В первой строке содержатся числа ![n](https://latex.codecogs.com/svg.latex?n) и ![m](https://latex.codecogs.com/svg.latex?m) (![1 \leqslant n, m \leqslant 6000](https://latex.codecogs.com/svg.latex?1%20\leqslant%20n,m%20\leqslant%206000)) -- размеры массивов.  Во второй строке содержится ![n](https://latex.codecogs.com/svg.latex?n) чисел -- элементы массива ![A](https://latex.codecogs.com/svg.latex?A).  Аналогично, в третьей строке содержится ![m](https://latex.codecogs.com/svg.latex?m) чисел -- элементы массива ![B](https://latex.codecogs.com/svg.latex?B).  Элементы массива неотрицательны и не превосходят 40000.

### Формат выходного файла

Выведите одно число -- сумму каждого десятого элемента последовательности, полученной сортировкой попарных произведенй элементов массивов ![A](https://latex.codecogs.com/svg.latex?A) и ![B](https://latex.codecogs.com/svg.latex?B).

### Примеры

`input.txt`
```
4 4
7 1 4 9
2 7 8 11
```

`output.txt`
```
51
```

#### Пояснение к примеру

Неотсортированная последовательность ![C](https://latex.codecogs.com/svg.latex?C) выглядит следующим образом:
```
[14, 2, 8, 18, 49, 7, 28, 63, 56, 8, 32, 72, 77, 11, 44, 99]
```

Отсортировав ее, получим:
```
[*2*, 7, 8, 8, 11, 14, 18, 28, 32, 44, *49*, 56, 63, 72, 77, 99]
```

Жирным выделены первый и одиннадцатый элементы последовательности, при этом двадцать первого элемента в ней нет.  Их сумма -- 51 -- и будет ответом.


### Решение

[IntNumSort.scala](IntNumSort.scala)
