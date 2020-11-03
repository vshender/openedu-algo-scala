# Удаление поддеревьев

Дано некоторое двоичное дерево поиска.  Также даны запросы на удаление из него вершин, имеющих заданные ключи, причем вершины удаляются целиком вместе со своими поддеревьями.

После каждого запроса на удаление выведите число оставшихся вершин в дереве.

В вершинах данного дерева записаны ключи -- целые числа, по модулю не превышающие ![10^9](https://latex.codecogs.com/svg.latex?10^9).  Гарантируется, что данное дерево является двоичным деревом поиска, в частности, для каждой вершины дерева ![V](https://latex.codecogs.com/svg.latex?V) выполняется следующее условие:
- все ключи вершин из левого поддерева меньше ключа вершины ![V](https://latex.codecogs.com/svg.latex?V);
- все ключи вершин из правого поддерева больше ключа вершины ![V](https://latex.codecogs.com/svg.latex?V).

**Высота дерева не превосходит 25**, таким образом, можно считать, что оно сбалансировано.

### Формат входного файла

Входной файл содержит описание двоичного дерева и описание запросов на удаление.

В первой строке файла находится число ![N](https://latex.codecogs.com/svg.latex?N) (![1 \leqslant N \leqslant 2 \cdot 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20N%20\leqslant%202\cdot10^5)) -- число вершин в дереве.  В последующих ![N](https://latex.codecogs.com/svg.latex?N) строках файла находятся описания вершин дерева.  В ![(i + 1)](https://latex.codecogs.com/svg.latex?(i+1))-ой строке файла (![1 \leqslant i \leqslant N](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20N)) находится описание ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины, состоящее из трех чисел ![K_i](https://latex.codecogs.com/svg.latex?K_i), ![L_i](https://latex.codecogs.com/svg.latex?L_i), ![R_i](https://latex.codecogs.com/svg.latex?R_i), разделенных пробелами -- ключа в ![i](https://latex.codecogs.com/svg.latex?i)-ой вершине (![|K_i| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|K_i|\leqslant10^9)), номера левого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < L_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20L_i%20\leqslant%20N) или ![L_i = 0](https://latex.codecogs.com/svg.latex?L_i=0), если левого ребенка нет) и номера правого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < R_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20R_i%20\leqslant%20N) или ![R_i = 0](https://latex.codecogs.com/svg.latex?R_i=0), если правого ребенка нет).

Все ключи различны.  Гарантируется, что данное дерево является деревом поиска.

В следующей строке находится число ![M](https://latex.codecogs.com/svg.latex?M) (![1 \leqslant M \leqslant 2 \cdot 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20M%20\leqslant%202\cdot10^5)) -- число запросов на удаление.  В следующей строке находятся ![M](https://latex.codecogs.com/svg.latex?M) чисел, разделенных пробелами -- ключи, вершины с которыми (вместе с их поддеревьями) необходимо удалить.  Все эти числа не превосходят ![10^9](https://latex.codecogs.com/svg.latex?10^9) по абсолютному значению.  Вершина с таким ключом не обязана существовать в дереве -- в этом случае дерево изменять не требуется.  Гарантируется, что корень дерева никогда не будет удален.

### Формат выходного файла

Выведите ![M](https://latex.codecogs.com/svg.latex?M) строк.  На ![i](https://latex.codecogs.com/svg.latex?i)-ой строке требуется вывести число вершин, оставшихся в дереве после выполнения ![i](https://latex.codecogs.com/svg.latex?i)-го запроса на удаление.

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
4
6 9 7 8
```

`output.txt`
```
5
4
4
1
```

### Решение

[SubtreesDelete.scala](SubtreesDelete.scala)
