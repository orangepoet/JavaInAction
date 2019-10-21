
map = []

status_unknown=-1
status_empty=0
status_body=1
status_head=2

x_max=10
y_max=10

def init_map():
    for i in range(0,x_max):
        row = []
        for j in range(0,y_max):
            row.append((i,j))
        
def print_flight(dots):
    return 
    print('--------------------')
    for y in range(0,y_max):
        line = ''
        for x in range(0,x_max):
            dot = (x,y)
            if dot in dots:
                line+=' H'
            else:
                line+=' *'
        print(line)
    print('--------------------')

def is_position_in_map(dots):
    for dot in dots:
        if dot[0]>=0 and dot[0]<=9 and dot[1]>=0 and dot[1]<=9:
            return False
    return True

def get_up_flight(head):
    x = head[0]
    y = head[1]

    ret = []

    for i in range(x-2,x+2+1):
        ret.append((i,y+1))
    for i in range(x-1,x+1+1):
        ret.append((i,y+3))
    for j in range(y+1,y+3+1):
        ret.append((x,j))
    return ret

def get_down_flight(head):
    x = head[0]
    y = head[1]
    ret = []

    for i in range(x-2,x+2+1):
        ret.append((i,y-1))
    for i in range(x-1,x+1+1):
        ret.append((i,y-3))
    for j in range(y-3,y-1+1):
        ret.append((x,j))
    return ret

def get_left_flight(head):
    x = head[0]
    y = head[1]

    ret = []

    for j in range(y-2,y+2+1):
        ret.append((x+1,j))
    for j in range(y-1,y+1):
        ret.append(x+3,y-1)
    for i in range(x+1, x+3+1):
        ret.append(i,y)
    return ret

def get_right_flight(head):
    x = head[0]
    y = head[1]

    ret = []

    for j in range(y-2,y+2+1):
        ret.append((x-1,j))
    for j in range(y-1,y+1+1):
        ret.append((x-3,j))
    for i in range(x-3,x-1+1):
        ret.append((i,y))
    return ret

def get_flight_cnt(head):
    count = 0
    flight0 = get_up_flight(head)
    if is_position_in_map(flight0):
        count+=1

    flight1 = get_down_flight(head)
    if is_position_in_map(flight1):
        count+=1

    flight2 = get_left_flight(head)
    if is_position_in_map(flight2):
        count+=1
    
    fligt3 = get_right_flight(head)
    if is_position_in_map(fligh3):
        count+=1
    return count

def get_all_head():
    valid_head=[]
    head_cnt=0
    for y in range(0,y_max):
        for x in range(0,x_max):
            head=(x,y)
            cnt = get_flight_cnt(head)
            if cnt>0:
                head_cnt+=1
                print(str(head)+": "+str(cnt))
            else:
                pass
    # print(str(valid_head))
    print('total cnt:'+str(head_cnt))


def main():
    get_all_head()

if __name__ == "__main__":
    main()