# k-ая порядковая статистика

Дан массив из ![n](https://latex.codecogs.com/svg.latex?n) элементов.  Какие числа являются ![k_1](https://latex.codecogs.com/svg.latex?k_1)-ым, ![(k_1 + 1)](https://latex.codecogs.com/svg.latex?(k_1+1))-ым, ..., ![k_2](https://latex.codecogs.com/svg.latex?k_2)-ым в порядке неубывания в этом массиве?

### Формат входного файла

В первой строке входного файла содержатся три числа: ![n](https://latex.codecogs.com/svg.latex?n) -- размер массива, а также границы интервала ![k_1](https://latex.codecogs.com/svg.latex?k_1) и ![k_2](https://latex.codecogs.com/svg.latex?k_2), при этом ![2 leqslant n \leqslant 4 \cdot 10^7](https://latex.codecogs.com/svg.latex?2%20\leqslant%20n%20\leqslant4%20\cdot%2010^7), ![1 \leqslant k_1 \leqslant k_2 \leqslant n, k_2 - k_1 < 200](https://latex.codecogs.com/svg.latex?1%20\leqslant%20k_1%20\leqslant%20k_2%20\leqslant%20n,%20k_2%20-%20k_1%20<%20200).

Во второй строке находятся числа ![A](https://latex.codecogs.com/svg.latex?A), ![B](https://latex.codecogs.com/svg.latex?B), ![C](https://latex.codecogs.com/svg.latex?C), ![a_1](https://latex.codecogs.com/svg.latex?a_1), ![a_2](https://latex.codecogs.com/svg.latex?a_2), по модулю не превосходящие ![10^9](https://latex.codecogs.com/svg.latex?10^9).  Вы должны получить элементы массива, начиная с третьего, по формуле: ![a_i = A \cdot a_{i - 2} + B \cdot a_{i - 1} + C](https://latex.codecogs.com/svg.latex?a_i=A%20\cdot%20a_{i-2}+B%20\cdot%20a_{i-1}+C).  Все вычисления должны производится в 32-битном знаковом типе, переполнения должны игнорироваться.

Обращаем Ваше внимание, что массив из 32-битных целых чисел занимает в памяти **160 мегабайт**!  Будьте аккуратны!

Подсказка: эту задачу лучше всего решать модификацией быстрой сортировки.  Однако сортировка массива целиком по времени, скорее всего, не пройдет, поэтому нужно подумать, как модифицировать быструю сортировку, чтобы не сортировать те части массива, которые не нужно сортировать.

Эту задачу, скорее всего, **нельзя решить ни на Python, ни на PyPy**.  Мы не нашли способа сгенерировать 32-битных целых чисел и при этом уложиться в ограничение по времени.  Если у Вас тоже не получается, попробуйте другой язык программирования!

### Формат выходного файла

В первой и единственной строке выходного файла выведите ![k_1](https://latex.codecogs.com/svg.latex?k_1)-ое, ![(k_1 + 1)](https://latex.codecogs.com/svg.latex?k_1+1)-ое, ..., ![k_2](https://latex.codecogs.com/svg.latex?k_2)-ое в порядке неубывания числа в массиве ![a](https://latex.codecogs.com/svg.latex?a).  Числа разделяйте одним пробелом.

### Примеры

`input.txt`
```
5 3 4
2 3 5 1 2
```

`output.txt`
```
13 48
```

`input.txt`
```
5 3 4
200000 300000 5 1 2
```

`output.txt`
```
2 800005
```

#### Примечание

Во втором примере элементы ![a](https://latex.codecogs.com/svg.latex?a) массива равны:
```
[1, 2, 800005, −516268571, 1331571109]
```

### Решение

[KOrderStatistic.scala](KOrderStatistic.scala)
