Основное задание

Данное задание должно представлять из себя имитацию игры «Змея» и реализовываться как application 
и поставляться в виде jar файла. Т.о. после запуска приложения должно появиться окно с игровым 
полем и элементами управления. 

Игровое поле представляет из себя матрицу клеток фиксированного размера M x N 
(M и N передаются, как параметры приложению из командной строки). Клетка поля в зависимости 
от своего состояния должны отображаться нижеперечисленными графическими примитивами либо 
использовать заранее нарисованный image, загружаемый из gif файла.

Состояние			Вид клетки на экране

Пустая				квадрат черного цвета
Голова змеи			круг желтого цвета (радиус = размер клетки / 2)
Тело змеи			круг желтого цвета (радиус = размер клетки / 3)
Хвост змеи			круг желтого цвета (радиус = размер клетки / 4)
Лягушка				круг зеленого цвета (радиус = размер клетки / 3)

К элементам управления относятся: индикатор – счет игры, который увеличивается на 1 
при съедании змеей лягушки, а также 2 кнопки: Start (начать игру) и Stop (закончить игру). 
В один момент времени может быть enabled только одна из кнопок.

В исходном состоянии змея размещается в левом верхнем углу игрового поля, начальное значение 
длины передается, как параметр приложению из командной строки. Лягушки расставляются 
случайным образом в пустые клетки (число лягушек передается, как параметр приложению из 
командной строки). Приложение должно проверять входные параметры, при наличие ошибок 
необходимо корректировать входные данные и выдавать соответствующее сообщение. Игра начинается 
при нажатии кнопки Start.

Змея непрерывно движется вперед сама по себе, управляясь мышью, а лягушки перемещаются по полю 
случайным образом. Объекты змея и лягушка должны перемещаются в отдельных потоках. 

Змея, как и лягушка, может перемещаться в четырех направлениях в соседние клетки – перемещение 
по диагонали запрещено. Сдвинувшись на одну позицию, змея должна засыпать на некоторое время 
(задается как параметр приложению из командной строки). При нажатии на правую кнопку мыши она 
меняет направление движения, сворачивая направо, при нажатии на левую кнопку – сворачивает налево. 
Если змея попадает на клетку, где стоит лягушка, лягушка уничтожается, т.о. змея поедает лягушку 
и порождается новая лягушка в случайно выбранной свободной клетке поля. При этом длина змеи, 
а также счет игры увеличиваются на 1.

Если змея сталкивается с границей поля, с собой или нажимается кнопка Stop, игра заканчивается. 
При этом змея и лягушки убиваются, изображения лягушек и змеи останавливаются, а счет 
отображает набранное кол-во баллов.

Лягушка перемещается в четырех направлениях на свободные клетки. Сдвинувшись на одну позицию, 
лягушка должна засыпать на некоторое время (задается константой), причем это время должно быть 
больше, чем у змеи в несколько раз. Таким образом змее будет легче поймать лягушку. Если 
свободных клеток для прыжка нет, лягушка остается на месте.

Следует учитывать, что при перемещении лягушек и змеи  должна перерисовываться только 
необходимая область. Т.е. затраты на отрисовку изменения положения объекта должны быть минимальны 
и не в коем случае не перерисовывать всю игровую область – только минимально необходимую область.

При проектировании приложения должен использоваться шаблон проектирования 
Model-View-Controller (MVC).

Данное задание должно корректно работать на jdk 8. Кодировка исходных файлов должна быть UTF-8.

Дополнительные задания

1. Ввести два типа лягушек – зеленые и красные. При столкновении с зелеными лягушками длина змеи 
и счет увеличиваются на 1.  При столкновении с красными лягушками длина змеи уменьшается на 1, 
а счет игры увеличивается на 2. Минимальной длиной змеи в этом случае считается ее начальное 
значение. 

2. Реализовать осмысленное поведение лягушек, при котором они стремятся упрыгать от змеи. 
При этом лягушки должны перемещаться в клетку, наиболее удовлетворяющую критерию удаленности 
с некоторой вероятностью (по умолчанию 0.8).

3. Добавить третью кнопку Pause (пауза), при нажатии на которую приостанавливаются все объекты. 
Исполнение программы из состояния Pause может быть либо продолжено кнопкой Start, либо 
закончено кнопкой Stop, убивающей потоки.

4. Обеспечить прокрутку поля в случае, если размер поля больше размера отображаемого фрейма 
(при помощи скроллеров).

5. Реализовать «прозрачные стены» для игрового поля – т.е. когда змея подходит к границе поля, 
она не умирает, а выходит с противоположной стороны поля, то же самое происходит с лягушками. 

6. Ввести третий тип лягушек – синего цвета, при столкновении с которыми змея умирает.

7. Ввести третий тип объектов - камни. Столкнувшись с камнем змея умирает.
