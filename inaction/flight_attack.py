
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
        
def print_flight(positions):
    return 
    print('--------------------')
    for y in range(0,y_max):
        line = ''
        for x in range(0,x_max):
            position = (x,y)
            if position in positions:
                line+=' H'
            else:
                line+=' *'
        print(line)
    print('--------------------')

def is_position_in_map(position):
    return position[0]>=0 and position[0]<=9 and position[1]>=0 and position[1]<=9

def is_above_top_head_valid(head_position):
    x = head_position[0]
    y = head_position[1]

    front_left = (x-2, y+1)
    if not is_position_in_map(front_left):
        # print("front left is out of map")
        return False
    
    front_right = (x+2, y+1)
    if not is_position_in_map(front_right):
        # print("front right is out of map")
        return False

    back_left = (x-1, y+3)
    if not is_position_in_map(back_left):
        # print("back left is out of map")
        return False

    back_right = (x+1, y+3)
    if not is_position_in_map(back_right):
        # print("back right is out of map")
        return False
    # print("above")
    print_flight([head_position,front_left,front_right,back_left,back_right])
    return True

def is_below_bottom_head_valid(head_position):
    x = head_position[0]
    y = head_position[1]

    front_left = (x-2, y-1)
    if not is_position_in_map(front_left):
        # print("front left is out of map")
        return False
    
    front_right = (x+2, y-1)
    if not is_position_in_map(front_right):
        # print("front right is out of map")
        return False

    back_left = (x-1, y-3)
    if not is_position_in_map(back_left):
        # print("back left is out of map")
        return False

    back_right = (x+1, y-3)
    if not is_position_in_map(back_right):
        # print("back right is out of map")
        return False
    # print("below")
    print_flight([head_position,front_left,front_right,back_left,back_right])
    return True

def is_left_head_valid(head_position):
    x = head_position[0]
    y = head_position[1]

    front_left = (x+1,y+2)
    if not is_position_in_map(front_left):
        # print("front left is out of map")
        return False
    
    front_right = (x+1,y-2)
    if not is_position_in_map(front_right):
        # print("front right is out of map")
        return False

    back_left = (x+3, y+1)
    if not is_position_in_map(back_left):
        # print("back left is out of map")
        return False

    back_right = (x+3, y-1)
    if not is_position_in_map(back_right):
        # print("back right is out of map")
        return False
    # print("left")
    print_flight([head_position,front_left,front_right,back_left,back_right])
    return True

def is_right_head_valid(head_position):
    x = head_position[0]
    y = head_position[1]

    front_left = (x-1,y-2)
    if not is_position_in_map(front_left):
        # print("front left is out of map")
        return False
    
    front_right = (x-1,y+2)
    if not is_position_in_map(front_right):
        # print("front right is out of map")
        return False

    back_left = (x-3, y-1)
    if not is_position_in_map(back_left):
        # print("back left is out of map")
        return False

    back_right = (x-3, y+1)
    if not is_position_in_map(back_right):
        # print("back right is out of map")
        return False
    # print("right")
    print_flight([head_position,front_left,front_right,back_left,back_right])
    return True

def get_head_count(positoin):
    count = 0
    if is_above_top_head_valid(positoin):
        count+=1
    if is_below_bottom_head_valid(positoin):
        count+=1
    if is_left_head_valid(positoin):
        count+=1
    if is_right_head_valid(positoin):
        count+=1
    return count

def get_all_head_position():
    valid_head=[]
    head_cnt=0
    for y in range(0,y_max):
        for x in range(0,x_max):
            head=(x,y)
            cnt = get_head_count(head)
            if cnt>3:
                head_cnt+=1
                print(str(head)+": "+str(cnt))
            else:
                pass
    # print(str(valid_head))
    print('total cnt:'+str(head_cnt))


def main():
    get_all_head_position()

if __name__ == "__main__":
    main()