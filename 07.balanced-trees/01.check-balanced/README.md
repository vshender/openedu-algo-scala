# Проверка сбалансированности

АВЛ-дерево является сбалансированным в следующем смысле: для любой вершины высота ее левого поддерева отличается от высоты ее правого поддерева не больше, чем на единицу.

Введем понятие *баланса вершины*: для вершины дерева ![V](https://latex.codecogs.com/svg.latex?V) ее баланс ![B(V)](https://latex.codecogs.com/svg.latex?B(V)) равен разности высоты правого поддерева и высоты левого поддерева.  Таким образом, свойство АВЛ-дерева, приведенное выше, можно сформулировать следующим образом: для любой ее вершины ![V](https://latex.codecogs.com/svg.latex?V) выполняется следующее неравенство:

  ![-1 \leqslant B(V) \leqslant 1](https://latex.codecogs.com/svg.latex?-1%20\leqslant%20B(V)%20\leqslant%201)

**Обратите внимание, что, по историческим причинам, определение баланса в этой и последующих задачах этой недели "зеркально отражено" по сравнению с определением баланса в лекциях!**  Надеемся, что этот факт не доставит Вам неудобств.  В литературе по алгоритмам -- как российской, так и мировой -- ситуация, как правило, примерно та же.

Дано двоичное дерево поиска.  Для каждой его вершины требуется определить ее баланс.

### Формат входного файла

Входной файл содержит описание двоичного дерева.  В первой строке файла находится число ![N](https://latex.codecogs.com/svg.latex?N) (![1 \leqslant N \leqslant 2 \cdot 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20N%20\leqslant%202\cdot10^5)) -- число вершин в дереве.  В последующих ![N](https://latex.codecogs.com/svg.latex?N) строках файла находятся описания вершин дерева.  В ![(i + 1)](https://latex.codecogs.com/svg.latex?(i+1))-ой строке файла (![1 \leqslant i leqslant N](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20N)) находится описание ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины, состоящее из трех чисел ![K_i](https://latex.codecogs.com/svg.latex?K_i), ![L_i](https://latex.codecogs.com/svg.latex?L_i), ![R_i](https://latex.codecogs.com/svg.latex?R_i), разделенных пробелами -- ключа в ![i](https://latex.codecogs.com/svg.latex?i)-ой вершине (![|K_i| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|K_i|%20\leqslant%2010^9)), номера левого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < L_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20L_i%20\leqslant%20N) или ![L_i = 0](https://latex.codecogs.com/svg.latex?L_i=0), если левого ребенка нет) и номера правого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < R_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20R_i%20\leqslant%20N) или ![R_i = 0](https://latex.codecogs.com/svg.latex?R_i=0), если правого ребенка нет).

Все ключи различны.  Гарантируется, что данное дерево является деревом поиска.

### Формат выходного файла

Для ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины в ![i](https://latex.codecogs.com/svg.latex?i)-ой строке выведите одно число -- баланс данной вершины.

### Пример

`input.txt`
```
6
-2 0 2
8 4 3
9 0 0
3 6 5
6 0 0
0 0 0
```

`output.txt`
```
3
-1
0
0
0
0
```

### Решение

[CheckBalanced.scala](CheckBalanced.scala)
