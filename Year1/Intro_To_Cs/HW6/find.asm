read
store x
load one
store count
LOOP:
    read
    gotoz 15
    sub x
    gotoz 12
    load count
    add one
    store count
    goto 04
load count
write
stop
load zero
sub one
write
stop