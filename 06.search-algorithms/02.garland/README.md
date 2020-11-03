# Гирлянда

Гирлянда состоит из ![n](https://latex.codecogs.com/svg.latex?n) лампочек на общем проводе.  Один её конец закреплен на заданной высоте ![A](https://latex.codecogs.com/svg.latex?A) мм (![h_1 = A](https://latex.codecogs.com/svg.latex?h_1=A)).  Благодаря силе тяжести гирлянда
 прогибается: высота каждой неконцевой лампы на 1 мм меньше, чем средняя высота ближайших соседей (![h_i = \frac{h_{i-1} + h_{i+1}}{2} - 1](https://latex.codecogs.com/svg.latex?h_i=\frac{h_{i-1}+h_{i+1}}{2}-1) для ![1 < i < n](https://latex.codecogs.com/svg.latex?1<i<n)).

Требуется найти минимальную высоту второго конца ![B](https://latex.codecogs.com/svg.latex?B) (![B = h_n](https://latex.codecogs.com/svg.latex?B=h_n)) при условии, что лишь одна лампочка может касаться земли, а для остальных выполняется условие ![h_i > 0](https://latex.codecogs.com/svg.latex?h_i>0).

Подсказка: для решения этой задачи можно использовать двоичный поиск (метод дихотомии).

![garland.png](garland.png)

### Формат входного файла

В первой строке входного файла содержится два числа ![n](https://latex.codecogs.com/svg.latex?n) и ![A](https://latex.codecogs.com/svg.latex?A) (![3 \leqslant n \leqslant 1000](https://latex.codecogs.com/svg.latex?3%20\leqslant%20n%20\leqslant%201000), ![n](https://latex.codecogs.com/svg.latex?n) -- целое, ![10 \leqslant A \leqslant 1000](https://latex.codecogs.com/svg.latex?10%20\leqslant%20A%20\leqslant%201000), ![A](https://latex.codecogs.com/svg.latex?A) -- вещественное и дано не более чем с тремя знаками после десятичной точки).

### Формат выходного файла

Выведите одно вещественное число ![B](https://latex.codecogs.com/svg.latex?B) -- минимальную высоту второго конца.  Ваш ответ будет засчитан, если он будет отличаться от правильного не более, чем на ![10^{-6}](https://latex.codecogs.com/svg.latex?10^{-6}).

### Примеры

`input.txt`
```
8 15
```

`output.txt`
```
9.75
```

`input.txt`
```
692 532.81
```

`output.txt`
```
446113.34434782615
```

### Решение

[Garland.scala](Garland.scala)
