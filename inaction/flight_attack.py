# coding=utf-8

status_empty = 0
status_body = 1
status_head = 2

x_max = 10
y_max = 10


def init_map():
    """
    地图初始化
    """
    ret = []
    for i in range(0, x_max):
        row = [0 for j in range(0, y_max)]
        ret.append(row)
    return ret


def print_map(map):
    """
    打印最终地图
    :type map: list
    """
    print('--------------------')
    for y in range(0, y_max):
        line = ''
        for x in range(0, x_max):
            dot = map[x][y]
            if dot == status_body:
                line += ' B'
            elif dot == status_head:
                line += ' H'
            else:
                line += ' .'
        print(line)
    print('--------------------')


def print_flight(flight):
    """
    打印飞机
    :param flight: 飞机的点阵
    """
    map = init_map()
    place_flight(flight, map)
    print_map(map)


def is_flight_can_place(flight, map):
    """
    尝试放置飞机
    :type map: list
    :param flight: 目标飞机
    :return: True: 成功放下, False: 失败, 不能放下
    """
    return all(0 <= x <= 9 and 0 <= y <= 9 and map[x][y] == status_empty for (x, y) in flight)


def get_up_flight(head):
    x = head[0]
    y = head[1]
    ret = [head]

    for i in range(x - 2, x + 2 + 1):
        ret.append((i, y + 1))
    for i in range(x - 1, x + 1 + 1):
        ret.append((i, y + 3))
    for j in range(y + 1, y + 3 + 1):
        ret.append((x, j))
    return ret


def get_down_flight(head):
    x = head[0]
    y = head[1]
    ret = [head]

    for i in range(x - 2, x + 2 + 1):
        ret.append((i, y - 1))
    for i in range(x - 1, x + 1 + 1):
        ret.append((i, y - 3))
    for j in range(y - 3, y - 1 + 1):
        ret.append((x, j))
    return ret


def get_left_flight(head):
    x = head[0]
    y = head[1]
    ret = [head]

    for j in range(y - 2, y + 2 + 1):
        ret.append((x + 1, j))
    for j in range(y - 1, y + 1):
        ret.append((x + 3, y - 1))
    for i in range(x + 1, x + 3 + 1):
        ret.append((i, y))
    return ret


def get_right_flight(head):
    x = head[0]
    y = head[1]
    ret = [head]

    for j in range(y - 2, y + 2 + 1):
        ret.append((x - 1, j))
    for j in range(y - 1, y + 1 + 1):
        ret.append((x - 3, j))
    for i in range(x - 3, x - 1 + 1):
        ret.append((i, y))
    return ret


def list_flight_by_head(head, map):
    """
    枚举该点上所有的飞机
    :param map:
    :param head:
    :return: 该点上所有的飞机
    """
    all_flight = [get_up_flight(head), get_down_flight(head), get_left_flight(head), get_right_flight(head)]
    return list(filter(lambda f: is_flight_can_place(f, map), all_flight))


def list_all_flight(map):
    """
    枚举所有可能的飞机
    :type map: list
    :return: 所有飞机
    """
    ret = []
    for y in range(0, y_max):
        for x in range(0, x_max):
            flights = list_flight_by_head((x, y), map)
            if flights:
                for f in flights:
                    ret.append(f)
    return ret


def place_flight(flight, map):
    """
    放置飞机
    :param flight:
    :return:
    """
    head = flight[0]
    map[head[0]][head[1]] = status_head

    body = flight[1:]
    for dot in body:
        map[dot[0]][dot[1]] = status_body


def place_flights():
    """
    在棋盘上放置飞机
    :param num: 飞机数量
    """
    map = init_map()
    for first in list_all_flight(map):
        place_flight(first, map)
        for second in list_all_flight(map):
            place_flight(second, map)
            for third in list_all_flight(map):
                place_flight(third, map)
                print_map(map)
                map = init_map()


def main():
    init_map()
    place_flights()


if __name__ == "__main__":
    main()
